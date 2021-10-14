package top.beanshell.rbac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.model.vo.AntTreeNodeVO;
import top.beanshell.common.service.impl.CRUDServiceImpl;
import top.beanshell.rbac.common.exception.RbacPermissionException;
import top.beanshell.rbac.common.exception.RbacRoleException;
import top.beanshell.rbac.common.exception.code.RbacPermissionStatusCode;
import top.beanshell.rbac.common.exception.code.RbacRoleStatusCode;
import top.beanshell.rbac.common.model.bo.TicketInfoBO;
import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.dao.RbacRoleDaoService;
import top.beanshell.rbac.model.dto.*;
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
public class RbacRoleServiceImpl extends CRUDServiceImpl<RbacRoleDTO, RbacRoleDaoService> implements RbacRoleService {

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
    public boolean refreshRoleUserPermission(Long roleId) {
        log.info("refresh role user permission: roleId = {}", roleId);

        // 刷新ticket缓存
        List<Long> userIds = roleUserService.findRoleUserIds(roleId);
        log.info("role user ids = {}", userIds);
        userIds.forEach(userId -> refreshTicketInfoThreads.execute(() -> updateUserPermissionCacheByUserId(userId)));

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean grantPermission(Long roleId, List<Long> permissionIds, PermissionType permissionType) {

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
        return daoService.getByCode(roleCode);
    }

    @Override
    public PageResultDTO<RbacRoleDTO> page(PageQueryDTO<RbacRoleQuery> pageQuery) {
        return daoService.page(pageQuery);
    }

    @Override
    public List<String> findUserRoleCode(Long userId) {
        Assert.notNull(userId, "userId必填");
        return daoService.findUserRoleCode(userId);
    }

    @Override
    public List<String> findUserPermissionCode(Long userId) {
        return daoService.findUserPermissionCode(userId);
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

        List<RbacRolePermissionCheckedDTO> list = daoService
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
    public List<RbacRolePermissionCheckedDTO> findRolePermission(Long roleId, PermissionType permissionType) {
        Assert.notNull(roleId, "roleId必填");
        Assert.notNull(permissionType, "permissionType必填");
        return daoService.findRolePermission(roleId, permissionType);
    }

    @Override
    public PageResultDTO<RbacRoleUserCheckedDTO> authRolePage(PageQueryDTO<RbacRoleUserQuery> pageQuery) {
        return daoService.authRolePage(pageQuery);
    }

    @Override
    public boolean revokeUserAllRole(Long userId) {
        Assert.notNull(userId, "userId必填");
        return roleUserService.removeByUserId(userId);
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

        return daoService.removeById(id);
    }
}
