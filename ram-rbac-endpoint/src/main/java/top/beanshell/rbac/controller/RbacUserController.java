package top.beanshell.rbac.controller;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.beanshell.common.annotation.Authorization;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.rbac.controller.request.RbacChangeUserPasswordRequest;
import top.beanshell.rbac.controller.request.RbacUserSaveRequest;
import top.beanshell.rbac.controller.request.RbacUserUpdateRequest;
import top.beanshell.rbac.model.dto.RbacUserDTO;
import top.beanshell.rbac.model.query.RbacUserQuery;
import top.beanshell.rbac.service.RbacTicketService;
import top.beanshell.rbac.service.RbacUserService;
import top.beanshell.web.controller.BaseController;
import top.beanshell.web.controller.request.PrimaryKeyRequest;
import top.beanshell.web.vo.BaseRequest;
import top.beanshell.web.vo.BaseResponse;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户管理接口
 * @author binchao
 */
@RestController
@RequestMapping("/rbac/user")
public class RbacUserController extends BaseController {

    @Resource
    private RbacUserService userService;

    @Resource
    private RbacTicketService ticketService;

    /**
     * 保存用户
     * @param request
     * @return
     */
    @PostMapping("/save")
    @Authorization("api_rbac_user_save")
    public BaseResponse save(@Valid @RequestBody BaseRequest<RbacUserSaveRequest> request) {
        RbacUserDTO user = BeanUtil.toBean(request.getData(), RbacUserDTO.class);
        boolean result = userService.saveEntity(user);
        return baseResponse(result);
    }

    /**
     * 更新用户信息
     * @param request
     * @return
     */
    @PostMapping("/updateById")
    @Authorization("api_rbac_user_updateById")
    public BaseResponse updateById(@Valid @RequestBody BaseRequest<RbacUserUpdateRequest> request) {
        RbacUserDTO user = BeanUtil.toBean(request.getData(), RbacUserDTO.class);
        boolean result = userService.updateEntityById(user);
        return baseResponse(result);
    }

    /**
     * 通过ID查询用户信息
     * @param request
     * @return
     */
    @PostMapping("/getById")
    @Authorization("api_rbac_user_getById")
    public BaseResponse<RbacUserDTO> getById(@Valid @RequestBody BaseRequest<PrimaryKeyRequest> request) {
        RbacUserDTO user = userService.getById(request.getData().getId());
        return successResponse(user);
    }

    /**
     * 通过ID删除用户信息
     * @param request
     * @return
     */
    @PostMapping("/removeById")
    @Authorization("api_rbac_user_removeById")
    public BaseResponse removeById(@Valid @RequestBody BaseRequest<PrimaryKeyRequest> request) {
        boolean result = userService.removeById(request.getData().getId());
        return baseResponse(result);
    }

    /**
     * 分页查询
     * @param request
     * @return
     */
    @PostMapping("/page")
    @Authorization("api_rbac_user_page")
    public BaseResponse page(@Valid @RequestBody BaseRequest<PageQueryDTO<RbacUserQuery>> request) {
        PageResultDTO<RbacUserDTO> result = userService.page(request.getData());
        return successResponse(result);
    }

    /**
     * 修改当前用户密码
     * @param request
     * @return
     */
    @PostMapping("/changeUserPassword")
    @Authorization("api_rbac_user_changeUserPassword")
    public BaseResponse changeUserPassword(@Valid @RequestBody BaseRequest<RbacChangeUserPasswordRequest> request) {
        Long userId = request.getData().getUserId();
        boolean result = userService.changeUserPassword(userId,
                request.getData().getNewPwd());
        // 剔除当前用户所有有效ticket
        if (result) {
            ticketService.kickOutUserTickets(userId);
        }
        return successResponse(result);
    }

}
