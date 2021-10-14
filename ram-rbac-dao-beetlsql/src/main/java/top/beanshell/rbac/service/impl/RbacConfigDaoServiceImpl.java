package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.core.query.LambdaQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.beanshell.beetlsql.service.impl.CRUDDaoServiceImpl;
import top.beanshell.beetlsql.util.PageUtil;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.rbac.dao.RbacConfigDaoService;
import top.beanshell.rbac.mapper.RbacConfigMapper;
import top.beanshell.rbac.model.dto.RbacConfigDTO;
import top.beanshell.rbac.model.pojo.RbacConfig;
import top.beanshell.rbac.model.query.RbacConfigQuery;

/**
 * KV字典管理DAO实现--beetlsql
 * @author binchao
 */
@Service
public class RbacConfigDaoServiceImpl extends CRUDDaoServiceImpl<RbacConfigDTO, RbacConfig, RbacConfigMapper> implements RbacConfigDaoService {

    @Override
    public RbacConfigDTO getByCode(String keyCode) {
        LambdaQuery<RbacConfig> lQuery = createLambdaQuery();
        lQuery.andEq(RbacConfig::getKeyCode, keyCode);
        RbacConfig config = lQuery.single();
        if (null != config) {
            return BeanUtil.toBean(config, RbacConfigDTO.class);
        }
        return null;
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
}
