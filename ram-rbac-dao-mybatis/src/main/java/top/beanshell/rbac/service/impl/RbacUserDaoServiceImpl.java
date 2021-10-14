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
import top.beanshell.rbac.dao.RbacUserDaoService;
import top.beanshell.rbac.mapper.RbacUserMapper;
import top.beanshell.rbac.model.dto.RbacUserDTO;
import top.beanshell.rbac.model.pojo.RbacUser;
import top.beanshell.rbac.model.query.RbacUserQuery;

/**
 * 用户管理DAO业务实现--mybatis
 * @author binchao
 */
@Service
public class RbacUserDaoServiceImpl
        extends CRUDDaoServiceImpl<RbacUserDTO, RbacUser, RbacUserMapper>
        implements RbacUserDaoService {

    @Override
    public PageResultDTO<RbacUserDTO> page(PageQueryDTO<RbacUserQuery> pageQuery) {
        RbacUserQuery params = pageQuery.getParams();
        LambdaQueryWrapper<RbacUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(RbacUser::getId, RbacUser::getAccount, RbacUser::getAccountState,
                RbacUser::getEmail, RbacUser::getPhoneNumber, RbacUser::getNickName);
        if (null != params) {
            if (StringUtils.hasText(params.getAccount())) {
                wrapper.likeRight(RbacUser::getAccount, params.getAccount());
            }
            if (StringUtils.hasText(params.getEmail())) {
                wrapper.likeRight(RbacUser::getEmail, params.getEmail());
            }
            if (StringUtils.hasText(params.getPhoneNumber())) {
                wrapper.likeRight(RbacUser::getPhoneNumber, params.getPhoneNumber());
            }
            if (null != params.getAccountState()) {
                wrapper.eq(RbacUser::getAccountState, params.getAccountState());
            }
            if (StringUtils.hasText(params.getNickname())) {
                wrapper.eq(RbacUser::getNickName, params.getNickname());
            }
        }

        Page<RbacUser> page = PageUtil.getPage(pageQuery);
        page(page, wrapper);
        return PageUtil.getPageResult(page, RbacUserDTO.class);
    }

    @Override
    public boolean checkUserIfAvailable(RbacUserDTO userDTO) {
        LambdaQueryWrapper<RbacUser> userLambdaQuery = new LambdaQueryWrapper<>();

        userLambdaQuery.eq(RbacUser::getAccount, userDTO.getAccount());

        if (StringUtils.hasText(userDTO.getEmail())) {
            userLambdaQuery.or().eq(RbacUser::getEmail, userDTO.getEmail());
        }
        if (StringUtils.hasText(userDTO.getPhoneNumber())) {
            userLambdaQuery.or().eq(RbacUser::getPhoneNumber, userDTO.getPhoneNumber());
        }

        return count(userLambdaQuery) == 0;
    }

    @Override
    public RbacUserDTO getUserByUniqueKey(String account) {
        LambdaQueryWrapper<RbacUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbacUser::getAccount, account)
                .or().eq(RbacUser::getEmail, account)
                .or().eq(RbacUser::getPhoneNumber, account);

        RbacUser rbacUser = getOne(wrapper);
        if (null != rbacUser) {
            return BeanUtil.toBean(rbacUser, RbacUserDTO.class);
        }
        return null;
    }
}
