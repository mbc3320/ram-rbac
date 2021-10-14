package top.beanshell.rbac.controller;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.beanshell.common.annotation.Authorization;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.rbac.common.constant.RamRbacConst;
import top.beanshell.rbac.controller.request.RbacConfigSaveRequest;
import top.beanshell.rbac.controller.request.RbacConfigUpdateRequest;
import top.beanshell.rbac.controller.request.SysGlobalConfigSaveRequest;
import top.beanshell.rbac.model.dto.RbacConfigDTO;
import top.beanshell.rbac.model.query.RbacConfigQuery;
import top.beanshell.rbac.service.RbacConfigService;
import top.beanshell.web.controller.BaseController;
import top.beanshell.web.controller.request.PrimaryKeyRequest;
import top.beanshell.web.vo.BaseRequest;
import top.beanshell.web.vo.BaseResponse;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 系统kv字典管理接口
 * @author binchao
 */
@RestController
@RequestMapping("/rbac/config")
public class RbacConfigController extends BaseController {

    @Resource
    private RbacConfigService rbacConfigService;

    /**
     * 保存配置
     *
     * @param request  request parameter
     * @return         true or false
     */
    @PostMapping("/save")
    @Authorization("api_rbac_config_save")
    public BaseResponse save(@Valid @RequestBody BaseRequest<RbacConfigSaveRequest> request) {
        RbacConfigDTO rbacConfigDTO = BeanUtil.toBean(request.getData(), RbacConfigDTO.class);
        boolean result = rbacConfigService.saveEntity(rbacConfigDTO);
        return baseResponse(result);
    }

    /**
     * 更新配置
     *
     * @param request request parameter
     * @return        true or false
     */
    @PostMapping("/updateById")
    @Authorization("api_rbac_config_updateById")
    public BaseResponse updateById(@Valid @RequestBody BaseRequest<RbacConfigUpdateRequest> request) {
        RbacConfigDTO rbacConfigDTO = BeanUtil.toBean(request.getData(), RbacConfigDTO.class);
        boolean result = rbacConfigService.updateEntityById(rbacConfigDTO);
        return baseResponse(result);
    }

    /**
     * 获取配置详情
     *
     * @param request request parameter
     * @return        config info
     */
    @PostMapping("/getById")
    @Authorization("api_rbac_config_getById")
    public BaseResponse<RbacConfigDTO> getById(@Valid @RequestBody BaseRequest<PrimaryKeyRequest> request) {
        RbacConfigDTO configDTO = rbacConfigService.getById(request.getData().getId());
        return successResponse(configDTO);
    }

    /**
     * 删除配置
     *
     * @param request  request parameter
     * @return         true or false
     */
    @PostMapping("/removeById")
    @Authorization("api_rbac_config_removeById")
    public BaseResponse removeById(@Valid @RequestBody BaseRequest<PrimaryKeyRequest> request) {
        boolean result = rbacConfigService.removeById(request.getData().getId());
        return baseResponse(result);
    }

    /**
     * 分页查询配置信息
     *
     * @param request  request parameter
     * @return        config page info
     */
    @PostMapping("/page")
    @Authorization("api_rbac_config_page")
    public BaseResponse<PageResultDTO<RbacConfigDTO>> page(@RequestBody BaseRequest<PageQueryDTO<RbacConfigQuery>> request) {
        PageResultDTO<RbacConfigDTO> pageResult = rbacConfigService.page(request.getData());
        return successResponse(pageResult);
    }


    /**
     * 获取系统全局设定
     * @return  global settings
     */
    @PostMapping("/getGlobalConfig")
    @Authorization("api_rbac_config_getGlobalConfig")
    public BaseResponse<RbacConfigDTO> getGlobalConfig() {
        RbacConfigDTO globalConfig = rbacConfigService.getByKeyCode(RamRbacConst.DEFAULT_SYS_GLOBAL_CONFIG_KEY);
        return successResponse(globalConfig);
    }

    /**
     * 保存或更新系统全局配置
     * @param request request parameter
     * @return        true or false
     */
    @PostMapping("/saveOrUpdateGlobalConfig")
    @Authorization("api_rbac_config_saveOrUpdateGlobalConfig")
    public BaseResponse saveOrUpdateGlobalConfig(@Valid @RequestBody BaseRequest<SysGlobalConfigSaveRequest> request) {
        RbacConfigDTO systemConfig = BeanUtil.toBean(request.getData(), RbacConfigDTO.class);
        systemConfig.setKeyCode(RamRbacConst.DEFAULT_SYS_GLOBAL_CONFIG_KEY);
        boolean result;
        if (null == request.getData().getId()) {
            result = rbacConfigService.saveEntity(systemConfig);
        } else {
            result = rbacConfigService.updateEntityById(systemConfig);
        }

        return baseResponse(result);
    }
}
