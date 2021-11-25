package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.I18nService;
import top.beanshell.common.service.impl.CRUDServiceImpl;
import top.beanshell.common.utils.JSON;
import top.beanshell.rbac.common.constant.RamRbacConst;
import top.beanshell.rbac.common.exception.RbacConfigException;
import top.beanshell.rbac.common.exception.code.RbacConfigStatusCode;
import top.beanshell.rbac.dao.RbacConfigDaoService;
import top.beanshell.rbac.model.bo.RbacSysGlobalConfigBO;
import top.beanshell.rbac.model.bo.RbacSysLoginCaptchaMetaBO;
import top.beanshell.rbac.model.bo.RbacSysLoginTypeMetaBO;
import top.beanshell.rbac.model.dto.RbacConfigDTO;
import top.beanshell.rbac.model.query.RbacConfigQuery;
import top.beanshell.rbac.service.RbacConfigService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 系统kv字典业务实现
 * @author binchao
 */
@Slf4j
@Service
public class RbacConfigServiceImpl extends CRUDServiceImpl<RbacConfigDTO, RbacConfigDaoService> implements RbacConfigService {

    private static final String CACHE_KEY_PREFIX = "rbac:config:keyCode:";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private I18nService i18nService;


    @Override
    public boolean saveEntity(RbacConfigDTO dto) {
        cleanCacheByCode(dto);
        RbacConfigDTO config = daoService.getByCode(dto.getKeyCode());
        if (null != config) {
            throw new RbacConfigException(RbacConfigStatusCode.KEY_CODE_IS_EXIST);
        }
        return super.saveEntity(dto);
    }

    @Override
    public boolean updateEntityById(RbacConfigDTO dto) {
        cleanCacheByCode(dto);
        RbacConfigDTO db = daoService.getById(dto.getId());
        BeanUtil.copyProperties(dto, db, "id", "keyCode", "createTime");
        return super.updateEntityById(db);
    }

    @Override
    public boolean removeById(Long id) {
        RbacConfigDTO config = daoService.getById(id);
        if (null != config) {
            cleanCacheByCode(config);
            return super.removeById(id);
        }
        return false;
    }

    /**
     * 根据keyCode清除缓存
     * @param config
     */
    private void cleanCacheByCode(RbacConfigDTO config) {
        try {
            redisTemplate.delete(getCacheKey(config.getKeyCode()));
        } catch (Exception e) {
            log.error("Clean Cache by Code error: {}", config.getKeyCode());
        }
    }

    @Override
    public PageResultDTO<RbacConfigDTO> page(PageQueryDTO<RbacConfigQuery> pageQuery) {
        return daoService.page(pageQuery);
    }

    @Override
    public RbacConfigDTO getByKeyCode(String keyCode) {
        Assert.hasText(keyCode, i18nService.getMessage("i18n.request.valid.common.required", "keyCode"));
        if (Boolean.TRUE.equals(redisTemplate.hasKey(getCacheKey(keyCode)))) {
            return (RbacConfigDTO) redisTemplate.opsForValue()
                    .get(getCacheKey(keyCode));
        }

        RbacConfigDTO dto = daoService.getByCode(keyCode);

        // 不管存不存在都缓存
        redisTemplate.opsForValue().set(getCacheKey(keyCode), dto, 7, TimeUnit.DAYS);

        return dto;
    }

    @Override
    public RbacSysGlobalConfigBO getGlobalConfig() {
        RbacConfigDTO config = getByKeyCode(RamRbacConst.DEFAULT_SYS_GLOBAL_CONFIG_KEY);
        if (null != config) {
            return JSON.parse(config.getKeyValue(), RbacSysGlobalConfigBO.class);
        } else {
            // 自动生成一个默认的 无需校验码、支持普通账号密码登录方式、ticket有效期120分钟
            RbacSysLoginTypeMetaBO normalLoginMeta = RbacSysLoginTypeMetaBO.builder()
                    .loginType(RamRbacConst.DEFAULT_LOGIN_TYPE_NORMAL_NAME)
                    .typeName("账号密码")
                    .enable(true)
                    .loginFactoryServiceName("normalLoginFactory")
                    .build();
            List<RbacSysLoginTypeMetaBO> metaList = Arrays.asList(normalLoginMeta);

            // 初始化验证码配置元数据
            RbacSysLoginCaptchaMetaBO captchaLineMetaBO = RbacSysLoginCaptchaMetaBO
                    .builder()
                    .enable(false)
                    .width(100)
                    .height(36)
                    .captchaServiceName("simpleLineTextCaptchaService")
                    .captchaMetaName("带横线的简单文本")
                    .build();

            RbacSysLoginCaptchaMetaBO captchaCircleMetaBO = RbacSysLoginCaptchaMetaBO
                    .builder()
                    .enable(false)
                    .width(100)
                    .height(36)
                    .captchaServiceName("simpleCircleTextCaptchaService")
                    .captchaMetaName("带圆圈的简单文本")
                    .build();

            RbacSysLoginCaptchaMetaBO captchaShearMetaBO = RbacSysLoginCaptchaMetaBO
                    .builder()
                    .enable(false)
                    .width(100)
                    .height(36)
                    .captchaServiceName("simpleShearTextCaptchaService")
                    .captchaMetaName("文字扭曲的简单文本")
                    .build();

            RbacSysLoginCaptchaMetaBO captchaGifMetaBO = RbacSysLoginCaptchaMetaBO
                    .builder()
                    .enable(false)
                    .width(100)
                    .height(36)
                    .captchaServiceName("simpleGifTextCaptchaService")
                    .captchaMetaName("Gif简单文本")
                    .build();

            List<RbacSysLoginCaptchaMetaBO> captchaMetaList = Arrays
                    .asList(captchaLineMetaBO, captchaCircleMetaBO, captchaShearMetaBO, captchaGifMetaBO);

            RbacSysGlobalConfigBO globalConfigBO = RbacSysGlobalConfigBO.builder()
                    .consoleCaptcha(false)
                    .captchaMetaList(captchaMetaList)
                    .loginServiceMetaList(metaList)
                    .ticketTimeout(120L)
                    .passwordErrorExpireTime(5L)
                    .build();
            RbacConfigDTO configDTO = RbacConfigDTO.builder()
                    .keyCode(RamRbacConst.DEFAULT_SYS_GLOBAL_CONFIG_KEY)
                    .keyValue(JSON.toJSONString(globalConfigBO))
                    .build();
            // 保存到数据库
            saveEntity(configDTO);
            return globalConfigBO;
        }
    }

    /**
     * 获取缓存键
     * @param keyName
     * @return
     */
    private String getCacheKey(String keyName) {
        return CACHE_KEY_PREFIX + keyName;
    }
}
