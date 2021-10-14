package top.beanshell.rbac.controller;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.beanshell.common.annotation.Authorization;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.model.vo.AntTreeNodeVO;
import top.beanshell.rbac.controller.request.RbacRoleRelSaveRequest;
import top.beanshell.rbac.controller.request.RbacRoleSaveRequest;
import top.beanshell.rbac.controller.request.RbacRoleUpdateRequest;
import top.beanshell.rbac.controller.request.RbacRoleUserGrantRequest;
import top.beanshell.rbac.model.dto.RbacRoleDTO;
import top.beanshell.rbac.model.dto.RbacRoleUserCheckedDTO;
import top.beanshell.rbac.model.query.RbacRolePermissionQuery;
import top.beanshell.rbac.model.query.RbacRoleQuery;
import top.beanshell.rbac.model.query.RbacRoleUserQuery;
import top.beanshell.rbac.service.RbacRoleService;
import top.beanshell.web.controller.BaseController;
import top.beanshell.web.controller.request.PrimaryKeyRequest;
import top.beanshell.web.vo.BaseRequest;
import top.beanshell.web.vo.BaseResponse;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 角色管理接口
 * @author binchao
 */
@RestController
@RequestMapping("/rbac/role")
public class RbacRoleController extends BaseController {

    @Resource
    private RbacRoleService roleService;

    /**
     * 保存角色
     * @param request request parameter
     * @return        true or false
     */
    @PostMapping("/save")
    @Authorization("api_rbac_role_save")
    public BaseResponse save(@Valid @RequestBody BaseRequest<RbacRoleSaveRequest> request) {
        RbacRoleDTO dto = BeanUtil.toBean(request.getData(), RbacRoleDTO.class);
        boolean result = roleService.saveEntity(dto);
        return baseResponse(result);
    }

    /**
     * 更新角色信息
     * @param request   request parameter
     * @return          true or false
     */
    @PostMapping("/updateById")
    @Authorization("api_rbac_role_updateById")
    public BaseResponse updateById(@Valid @RequestBody BaseRequest<RbacRoleUpdateRequest> request) {
        RbacRoleDTO dto = BeanUtil.toBean(request.getData(), RbacRoleDTO.class);
        boolean result = roleService.updateEntityById(dto);
        return baseResponse(result);
    }

    /**
     * 通过ID查询角色信息
     * @param request  request parameter
     * @return         role info
     */
    @PostMapping("/getById")
    @Authorization("api_rbac_role_getById")
    public BaseResponse<RbacRoleDTO> getById(@Valid @RequestBody BaseRequest<PrimaryKeyRequest> request) {
        RbacRoleDTO dto = roleService.getById(request.getData().getId());
        return successResponse(dto);
    }

    /**
     * 通过ID删除角色信息
     * @param request  request parameter
     * @return         true or false
     */
    @PostMapping("/removeById")
    @Authorization("api_rbac_role_removeById")
    public BaseResponse removeById(@Valid @RequestBody BaseRequest<PrimaryKeyRequest> request) {
        boolean result = roleService.removeById(request.getData().getId());
        return baseResponse(result);
    }

    /**
     * 分页查询
     * @param request  request parameter
     * @return         permission page info
     */
    @PostMapping("/page")
    @Authorization("api_rbac_role_page")
    public BaseResponse page(@Valid @RequestBody BaseRequest<PageQueryDTO<RbacRoleQuery>> request) {
        PageResultDTO<RbacRoleDTO> result = roleService.page(request.getData());
        return successResponse(result);
    }

    /**
     * 授权用户角色
     * @param request   request parameter
     * @return          true or false
     */
    @PostMapping("/grant")
    @Authorization("api_rbac_role_grant")
    public BaseResponse grant(@Valid @RequestBody BaseRequest<RbacRoleUserGrantRequest> request) {
        boolean result = roleService.grantUser(request.getData().getRoleId(), request.getData().getUserId());
        return baseResponse(result);
    }

    /**
     * 撤销用户角色
     * @param request  request parameter
     * @return         true or false
     */
    @PostMapping("/revoke")
    @Authorization("api_rbac_role_revoke")
    public BaseResponse revoke(@Valid @RequestBody BaseRequest<RbacRoleUserGrantRequest> request) {
        boolean result = roleService.revokeUser(request.getData().getRoleId(), request.getData().getUserId());
        return baseResponse(result);
    }

    /**
     * 授权角色权限
     * @param request   request parameter
     * @return          true or false
     */
    @PostMapping("/permissionGrant")
    @Authorization("api_rbac_role_permissionGrant")
    public BaseResponse permissionGrant(@Valid @RequestBody BaseRequest<RbacRoleRelSaveRequest> request) {
        boolean resultBefore = roleService.grantPermission(request.getData().getRoleId(),
                request.getData().getPermissionIds(),
                request.getData().getPermissionType());
        if (resultBefore) {
            roleService.refreshRoleUserPermission(request.getData().getRoleId());
        }
        return baseResponse(resultBefore);
    }

    /**
     * 权限树
     * @param request  request parameter
     * @return         permission tree
     */
    @PostMapping("/findAuthPermissionTree")
    @Authorization("api_rbac_role_findAuthPermissionTree")
    public BaseResponse<AntTreeNodeVO> findAuthPermissionTree(@Valid @RequestBody BaseRequest<RbacRolePermissionQuery> request) {
        AntTreeNodeVO tree = roleService.findRolePermissionTree(request.getData());
        return successResponse(tree);
    }

    /**
     * 角色分页查询（角色授权用户）
     * @param request     request parameter
     * @return            user info page
     */
    @PostMapping("/authRolePage")
    @Authorization("api_rbac_role_authRolePage")
    public BaseResponse<PageResultDTO<RbacRoleUserCheckedDTO>> authRolePage(@Valid @RequestBody BaseRequest<PageQueryDTO<RbacRoleUserQuery>> request) {
        PageResultDTO<RbacRoleUserCheckedDTO> pageResult = roleService.authRolePage(request.getData());
        return successResponse(pageResult);
    }
}
