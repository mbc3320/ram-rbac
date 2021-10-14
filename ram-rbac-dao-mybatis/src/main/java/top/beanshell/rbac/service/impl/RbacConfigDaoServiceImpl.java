package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.mybatis.impl.CRUDDaoServiceImpl;
import top.beanshell.mybatis.util.PageUtil;
import top.beanshell.rbac.dao.RbacConfigDaoService;
import top.beanshell.rbac.mapper.RbacConfigMapper;
import top.beanshell.rbac.model.dto.RbacConfigDTO;
import top.beanshell.rbac.model.pojo.RbacConfig;
import top.beanshell.rbac.model.query.RbacConfigQuery;

/**
 * KV字典管理DAO实现--Mybatis
 * @author binchao
 */
@Service
public class RbacConfigDaoServiceImpl extends CRUDDaoServiceImpl<RbacConfigDTO, RbacConfig, RbacConfigMapper> implements RbacConfigDaoService {

    @Override
    public RbacConfigDTO getByCode(String keyCode) {
        LambdaQueryWrapper<RbacConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbacConfig::getKeyCode, keyCode);
        RbacConfig config = getOne(wrapper);
        if (null != config) {
            return BeanUtil.toBean(config, RbacConfigDTO.class);
        }
        return null;
    }

    @Override
    public PageResultDTO<RbacConfigDTO> page(PageQueryDTO<RbacConfigQuery> pageQuery) {
        RbacConfigQuery params = pageQuery.getParams();
        LambdaQueryWrapper<RbacConfig> wrapper = new LambdaQueryWrapper<>();
        if (null != params) {
            if (StringUtils.hasText(params.getKeyCode())) {
                wrapper.likeRight(RbacConfig::getKeyCode, params.getKeyCode());
            }
        }
        // 剔除系统字典数据
        wrapper.notLike(RbacConfig::getKeyCode, "_system%");
        Page<RbacConfig> page = PageUtil.getPage(pageQuery);
        page(page, wrapper);
        return PageUtil.getPageResult(page, RbacConfigDTO.class);
    }
}
