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
        LambdaQuery<RbacUser> lQuery = createLambdaQuery();
        lQuery.select(RbacUser::getId, RbacUser::getAccount, RbacUser::getAccountState,
                RbacUser::getEmail, RbacUser::getPhoneNumber, RbacUser::getNickName);
        if (null != params) {
            if (StringUtils.hasText(params.getAccount())) {
                lQuery.andLike(RbacUser::getAccount, params.getAccount() + "%");
            }
            if (StringUtils.hasText(params.getEmail())) {
                lQuery.andLike(RbacUser::getEmail, params.getEmail() + "%");
            }
            if (StringUtils.hasText(params.getPhoneNumber())) {
                lQuery.andLike(RbacUser::getPhoneNumber, params.getPhoneNumber() + "%");
            }
            if (null != params.getAccountState()) {
                lQuery.andEq(RbacUser::getAccountState, params.getAccountState());
            }
            if (StringUtils.hasText(params.getNickname())) {
                lQuery.andEq(RbacUser::getNickName, params.getNickname());
            }
        }

        PageResult<RbacUser> page = lQuery.page(pageQuery.getCurrent(), pageQuery.getPageSize());
        return PageUtil.getPageResult(page, RbacUserDTO.class);
    }

    @Override
    public boolean checkUserIfAvailable(RbacUserDTO userDTO) {
        LambdaQuery<RbacUser> userLambdaQuery = createLambdaQuery();

        userLambdaQuery.andEq(RbacUser::getAccount, userDTO.getAccount());

        if (StringUtils.hasText(userDTO.getEmail())) {
            userLambdaQuery.orEq(RbacUser::getEmail, userDTO.getEmail());
        }
        if (StringUtils.hasText(userDTO.getPhoneNumber())) {
            userLambdaQuery.orEq(RbacUser::getPhoneNumber, userDTO.getPhoneNumber());
        }

        return userLambdaQuery.count() == 0;
    }

    @Override
    public RbacUserDTO getUserByUniqueKey(String account) {
        LambdaQuery<RbacUser> query = createLambdaQuery();
        query.andEq(RbacUser::getAccount, account)
                .orEq(RbacUser::getEmail, account)
                .orEq(RbacUser::getPhoneNumber, account);

        RbacUser rbacUser = query.single();
        if (null != rbacUser) {
            return BeanUtil.toBean(rbacUser, RbacUserDTO.class);
        }
        return null;
    }
}
