package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.mapper.BaseMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.beanshell.beetlsql.service.impl.CRUDServiceImpl;
import top.beanshell.beetlsql.util.PageUtil;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.utils.JSON;
import top.beanshell.rbac.common.constant.RamRbacConst;
import top.beanshell.rbac.mapper.RbacConfigMapper;
import top.beanshell.rbac.model.bo.RbacSysGlobalConfigBO;
import top.beanshell.rbac.model.dto.RbacConfigDTO;
import top.beanshell.rbac.model.pojo.RbacConfig;
import top.beanshell.rbac.model.query.RbacConfigQuery;
import top.beanshell.rbac.service.RbacConfigService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 系统kv字典业务实现
 * @author binchao
 */
@Slf4j
@Service
public class RbacConfigServiceImpl extends CRUDServiceImpl<RbacConfigDTO, RbacConfig> implements RbacConfigService {

    private static final String CACHE_KEY_PREFIX = "rbac:config:keyCode:";

    @Resource
    private RbacConfigMapper rbacConfigMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected BaseMapper<RbacConfig> getDao() {
        return rbacConfigMapper;
    }

    @Override
    public boolean saveEntity(RbacConfigDTO dto) {
        cleanCacheByCode(dto);
        return super.saveEntity(dto);
    }

    @Override
    public boolean updateEntityById(RbacConfigDTO dto) {
        cleanCacheByCode(dto);
        RbacConfigDTO db = getById(dto.getId());
        BeanUtil.copyProperties(dto, db, "id", "keyCode", "createTime");
        return super.updateEntityById(db);
    }

    @Override
    public boolean removeById(Long id) {
        RbacConfig config = rbacConfigMapper.single(id);
        if (null != config) {
            cleanCacheByCode(BeanUtil.toBean(config, RbacConfigDTO.class));
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
        RbacConfigQuery params = pageQuery.getParams();
        LambdaQuery<RbacConfig> lQuery = createLambdaQuery();
        if (null != params) {
            if (StringUtils.hasText(params.getKeyCode())) {
                lQuery.andLike(RbacConfig::getKeyCode, params.getKeyCode() + "%");
            }
        }
        // 剔除系统字典数据
        lQuery.andNotLike(RbacConfig::getKeyCode, "_system%");
        PageResult<RbacConfig> page = lQuery.page(pageQuery.getCurrent(), pageQuery.getPageSize());
        return PageUtil.getPageResult(page, RbacConfigDTO.class);
    }

    @Override
    public RbacConfigDTO getByKeyCode(String keyCode) {

        if (Boolean.TRUE.equals(redisTemplate.hasKey(getCacheKey(keyCode)))) {
            return (RbacConfigDTO) redisTemplate.opsForValue()
                    .get(getCacheKey(keyCode));
        }

        LambdaQuery<RbacConfig> lQuery = createLambdaQuery();
        lQuery.andEq(RbacConfig::getKeyCode, keyCode);
        RbacConfig config = lQuery.single();

        RbacConfigDTO dto = null;

        if (null != config) {
            dto = BeanUtil.toBean(config, RbacConfigDTO.class);
        }

        // 不管存不存在都缓存
        redisTemplate.opsForValue().set(getCacheKey(keyCode), dto, 7, TimeUnit.DAYS);

        return dto;
    }

    @Override
    public RbacSysGlobalConfigBO getGlobalConfig() {
        RbacConfigDTO config = getByKeyCode(RamRbacConst.DEFAULT_SYS_GLOBAL_CONFIG_KEY);
        if (null != config) {
            return JSON.parse(config.getKeyValue(), RbacSysGlobalConfigBO.class);
        }
        return null;
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
