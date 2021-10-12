package top.beanshell.rbac.controller;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.beanshell.common.annotation.Authorization;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.model.vo.AntSimpleTreeNodeVO;
import top.beanshell.rbac.controller.request.RbacPermissionSaveRequest;
import top.beanshell.rbac.controller.request.RbacPermissionUpdateRequest;
import top.beanshell.rbac.model.dto.RbacPermissionDTO;
import top.beanshell.rbac.model.query.RbacPermissionQuery;
import top.beanshell.rbac.service.RbacPermissionService;
import top.beanshell.web.controller.BaseController;
import top.beanshell.web.controller.request.PrimaryKeyRequest;
import top.beanshell.web.vo.BaseRequest;
import top.beanshell.web.vo.BaseResponse;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理接口
 * @author binchao
 */
@RestController
@RequestMapping("/rbac/permission")
public class RbacPermissionController extends BaseController {

    @Resource
    private RbacPermissionService permissionService;

    /**
     * 保存权限
     * @param request
     * @return
     */
    @PostMapping("/save")
    @Authorization("api_rbac_permission_save")
    public BaseResponse save(@Valid @RequestBody BaseRequest<RbacPermissionSaveRequest> request) {
        RbacPermissionDTO dto = BeanUtil.toBean(request.getData(), RbacPermissionDTO.class);
        boolean result = permissionService.saveEntity(dto);
        return baseResponse(result);
    }

    /**
     * 更新权限信息
     * @param request
     * @return
     */
    @PostMapping("/updateById")
    @Authorization("api_rbac_permission_updateById")
    public BaseResponse updateById(@Valid @RequestBody BaseRequest<RbacPermissionUpdateRequest> request) {
        RbacPermissionDTO dto = BeanUtil.toBean(request.getData(), RbacPermissionDTO.class);
        boolean result = permissionService.updateEntityById(dto);
        return baseResponse(result);
    }

    /**
     * 通过ID查询权限信息
     * @param request
     * @return
     */
    @PostMapping("/getById")
    @Authorization("api_rbac_permission_getById")
    public BaseResponse<RbacPermissionDTO> getById(@Valid @RequestBody BaseRequest<PrimaryKeyRequest> request) {
        RbacPermissionDTO dto = permissionService.getById(request.getData().getId());
        return successResponse(dto);
    }

    /**
     * 通过ID删除权限信息
     * @param request
     * @return
     */
    @PostMapping("/removeById")
    @Authorization("api_rbac_permission_removeById")
    public BaseResponse removeById(@Valid @RequestBody BaseRequest<PrimaryKeyRequest> request) {
        boolean result = permissionService.removeById(request.getData().getId());
        return baseResponse(result);
    }

    /**
     * 分页查询
     * @param request
     * @return
     */
    @PostMapping("/page")
    @Authorization("api_rbac_permission_page")
    public BaseResponse page(@Valid @RequestBody BaseRequest<PageQueryDTO<RbacPermissionQuery>> request) {
        PageResultDTO<RbacPermissionDTO> result = permissionService.page(request.getData());
        return successResponse(result);
    }


    /**
     * 权限树列表
     * @param request
     * @return
     */
    @PostMapping("/simpleTree")
    @Authorization("api_rbac_permission_simpleTree")
    public BaseResponse<List<AntSimpleTreeNodeVO>> simpleTree(@Valid @RequestBody BaseRequest<RbacPermissionQuery> request) {
        List<RbacPermissionDTO> list = permissionService.list(request.getData());

        List<AntSimpleTreeNodeVO> treeList = new ArrayList<>();
        if (!list.isEmpty()) {
            for (RbacPermissionDTO p : list) {
                AntSimpleTreeNodeVO treeNode = new AntSimpleTreeNodeVO();
                treeNode.setId(p.getId().toString());
                treeNode.setValue(p.getId().toString());
                treeNode.setPid(p.getPid() == null ? null : p.getPid().toString());
                treeNode.setLabel(p.getPermissionName());
                treeNode.setExtra(p.getPermissionCode());
                treeList.add(treeNode);
            }
        }

        return successResponse(treeList);
    }
}
