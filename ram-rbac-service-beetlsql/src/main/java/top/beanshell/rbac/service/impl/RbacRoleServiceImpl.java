package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.mapper.BaseMapper;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import top.beanshell.beetlsql.service.impl.CRUDServiceImpl;
import top.beanshell.beetlsql.util.PageUtil;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.model.vo.AntTreeNodeVO;
import top.beanshell.rbac.common.exception.RbacPermissionException;
import top.beanshell.rbac.common.exception.RbacRoleException;
import top.beanshell.rbac.common.exception.code.RbacPermissionStatusCode;
import top.beanshell.rbac.common.exception.code.RbacRoleStatusCode;
import top.beanshell.rbac.common.model.bo.TicketInfoBO;
import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.mapper.RbacRoleMapper;
import top.beanshell.rbac.model.dto.*;
import top.beanshell.rbac.model.pojo.RbacRole;
import top.beanshell.rbac.model.query.RbacRolePermissionQuery;
import top.beanshell.rbac.model.query.RbacRoleQuery;
import top.beanshell.rbac.model.query.RbacRoleUserQuery;
import top.beanshell.rbac.service.RbacRolePermissionService;
import top.beanshell.rbac.service.RbacRoleService;
import top.beanshell.rbac.service.RbacRoleUserService;
import top.beanshell.rbac.service.RbacTicketService;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色管理业务实现
 * @author binchao
 */
@Slf4j
@Service
public class RbacRoleServiceImpl extends CRUDServiceImpl<RbacRoleDTO, RbacRole> implements RbacRoleService {

    @Resource
    private RbacRoleMapper roleMapper;

    @Resource
    private RbacRoleUserService roleUserService;

    @Resource
    private RbacRolePermissionService rolePermissionService;

    @Resource
    private RbacTicketService ticketService;

    /**
     * 刷新token信息异步线程
     */
    private static SimpleAsyncTaskExecutor refreshTicketInfoThreads;

    static {
        refreshTicketInfoThreads = new SimpleAsyncTaskExecutor("Refresh-TicketInfo-Thread");
        refreshTicketInfoThreads.setConcurrencyLimit(10);
    }

    @Override
    protected BaseMapper<RbacRole> getDao() {
        return roleMapper;
    }

    @Override
    public boolean grantUser(Long roleId, Long userId) {
        log.info("grant role user: roleId = {}, userId = {}", roleId, userId);
        RbacRoleUserDTO roleUser = RbacRoleUserDTO.builder()
                .roleId(roleId)
                .userId(userId)
                .build();
        boolean result = roleUserService.saveEntity(roleUser);
        // 刷新用户ticket
        if (result) {
            updateUserPermissionCacheByUserId(userId);
        }
        return result;
    }

    @Override
    public boolean revokeUser(Long roleId, Long userId) {
        log.info("revoke role user: roleId = {}, userId = {}", roleId, userId);
        boolean result = roleUserService.removeByUniqueKey(roleId, userId);
        if (result) {
            updateUserPermissionCacheByUserId(userId);
        }
        return result;
    }


    @Override
    public boolean grantPermissionAfter(Long roleId, List<Long> permissionIds, PermissionType permissionType) {
        log.info("grant role permission: roleId = {}, permissionIds = {}, permissionType = {}",
                roleId, permissionIds, permissionType);

        // 刷新ticket缓存
        List<Long> userIds = roleUserService.findRoleUserIds(roleId);
        userIds.forEach(userId -> refreshTicketInfoThreads.execute(() -> updateUserPermissionCacheByUserId(userId)));

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean grantPermissionBefore(Long roleId, List<Long> permissionIds, PermissionType permissionType) {

        // 先删除旧的授权信息
        rolePermissionService.removeByRoleId(roleId, permissionType);

        if (null != permissionIds && !permissionIds.isEmpty()) {
            List<RbacRolePermissionDTO> batch = new ArrayList<>(permissionIds.size());
            for (Long pid : permissionIds) {
                RbacRolePermissionDTO rp = RbacRolePermissionDTO.builder()
                        .roleId(roleId)
                        .permissionId(pid)
                        .permissionType(permissionType)
                        .build();
                batch.add(rp);
            }
            rolePermissionService.insertBatch(batch);
        }
        return true;
    }

    @Override
    public List<String> findRoleGrantedPermission(Long roleId, PermissionType permissionType) {
        return rolePermissionService.findRoleGrantedPermission(roleId, permissionType);
    }

    @Override
    public RbacRoleDTO getByCode(String roleCode) {
        Assert.hasText(roleCode, "roleCode必填");
        LambdaQuery<RbacRole> query = roleMapper.createLambdaQuery();
        query.andEq(RbacRole::getRoleCode, roleCode);
        RbacRole rbacRole = query.singleSimple();
        if (null != rbacRole) {
            return BeanUtil.toBean(rbacRole, RbacRoleDTO.class);
        }
        return null;
    }

    @Override
    public PageResultDTO<RbacRoleDTO> page(PageQueryDTO<RbacRoleQuery> pageQuery) {
        RbacRoleQuery params = pageQuery.getParams();
        LambdaQuery<RbacRole> lQuery = roleMapper.createLambdaQuery();

        if (null != params) {
            if (StringUtils.hasText(params.getRoleCode())) {
                lQuery.andLike(RbacRole::getRoleCode, params.getRoleCode() + "%");
            }
            if (StringUtils.hasText(params.getRoleName())) {
                lQuery.andLike(RbacRole::getRoleName, params.getRoleName() + "%");
            }
        }

        PageResult<RbacRole> page = lQuery.page(pageQuery.getCurrent(), pageQuery.getPageSize());
        return PageUtil.getPageResult(page, RbacRoleDTO.class);
    }

    @Override
    public List<String> findUserRoleCode(Long userId) {
        return roleMapper.findUserRoleCode(userId);
    }

    @Override
    public List<String> findUserPermissionCode(Long userId) {
        return roleMapper.findUserPermissionCode(userId);
    }


    @Override
    public boolean updateUserPermissionCacheByUserId(@NotNull Long userId) {

        List<String> userTicketList = ticketService.findUserAvailableTicket(userId);

        List<String> permissionList = findUserPermissionCode(userId);

        userTicketList.forEach(ticket -> refreshTicketInfoThreads.execute(() -> {
            log.info("Prepare to update Ticket info: userId = {}, ticket = {}", userId, ticket);
            TicketInfoBO ticketInfoBO = ticketService.get(ticket);
            UserDetailBO userDetailBO = ticketInfoBO.getUserDetail();
            userDetailBO.setAccessList(permissionList);
            ticketService.updateTicketInfo(ticketInfoBO);
        }));
        return true;
    }

    @Override
    public AntTreeNodeVO findRolePermissionTree(RbacRolePermissionQuery query) {

        List<RbacRolePermissionCheckedDTO> list = roleMapper
                .findRolePermission(query.getRoleId(), query.getPermissionType());

        // 找出根节点
        RbacRolePermissionCheckedDTO rootNode = null;
        for (RbacRolePermissionCheckedDTO p : list) {
            if (null == p.getPid()) {
                rootNode = p;
                break;
            }
        }
        if (null == rootNode) {
            throw new RbacPermissionException(RbacPermissionStatusCode.PERMISSION_ROOT_NODE_ERROR);
        }

        AntTreeNodeVO treeNodeVO = AntTreeNodeVO.builder()
                .title(rootNode.getPermissionName())
                .key(rootNode.getId().toString())
                .checked(rootNode.getChecked())
                .extra(rootNode.getPermissionCode())
                .build();

        int guessLength = list.size() / 5;
        List<String> checkedList = new ArrayList<>(guessLength);
        if (Boolean.TRUE.equals(treeNodeVO.getChecked())) {
            checkedList.add(treeNodeVO.getKey());
        }

        findChildren(treeNodeVO, list, checkedList);

        treeNodeVO.setCheckedList(checkedList);

        return treeNodeVO;
    }

    @Override
    public PageResultDTO<RbacRoleUserCheckedDTO> authRolePage(PageQueryDTO<RbacRoleUserQuery> pageQuery) {
        PageRequest pageRequest = DefaultPageRequest.of(pageQuery.getCurrent(), pageQuery.getPageSize());
        PageResult<RbacRoleUserCheckedDTO> result = roleMapper.authRolePage(pageQuery.getParams(), pageRequest);
        return PageUtil.getPageResult(result, RbacRoleUserCheckedDTO.class);
    }


    /**
     * 组装子节点
     * 空间换时间
     * @param rootNode
     * @param permissionList
     * @return
     */
    private AntTreeNodeVO findChildren(AntTreeNodeVO rootNode, List<RbacRolePermissionCheckedDTO> permissionList, List<String> checkedList) {
        // 找出子节点
        List<RbacRolePermissionCheckedDTO> children = permissionList.stream()
                .filter(permission -> Long.valueOf(rootNode.getKey()).equals(permission.getPid()))
                .collect(Collectors.toList());

        List<AntTreeNodeVO> childrenNode = new ArrayList<>(children.size());
        if (!children.isEmpty()) {
            // 转换节点，并递归查找子节点
            childrenNode = children.stream().map(permission -> {
                AntTreeNodeVO cNode = AntTreeNodeVO.builder()
                        .title(permission.getPermissionName())
                        .key(permission.getId().toString())
                        .checked(permission.getChecked())
                        .extra(permission.getPermissionCode())
                        .build();
                if (Boolean.TRUE.equals(cNode.getChecked())) {
                    checkedList.add(permission.getId().toString());
                }
                findChildren(cNode, permissionList, checkedList);
                return cNode;
            }).collect(Collectors.toList());
        }
        rootNode.setChildren(childrenNode);
        return rootNode;
    }


    @Override
    public boolean removeById(Long id) {

        // 先确认授权信息
        long authUserCount = roleUserService.countRoleUserAuth(id);

        long authPermissionCount = rolePermissionService.countRolePermission(id);

        if (authUserCount > 0 || authPermissionCount > 0) {
            throw new RbacRoleException(RbacRoleStatusCode.ROLE_IS_AUTH);
        }

        return super.removeById(id);
    }
}
