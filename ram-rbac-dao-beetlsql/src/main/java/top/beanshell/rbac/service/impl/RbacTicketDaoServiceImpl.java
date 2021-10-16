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
import top.beanshell.rbac.dao.RbacTicketDaoService;
import top.beanshell.rbac.mapper.RbacTicketMapper;
import top.beanshell.rbac.model.dto.RbacTicketDTO;
import top.beanshell.rbac.model.pojo.RbacTicket;
import top.beanshell.rbac.model.query.RbacTicketQuery;

import java.util.Date;
import java.util.List;

/**
 * 用户凭证管理DAO业务实现
 * @author binchao
 */
@Service
public class RbacTicketDaoServiceImpl
        extends CRUDDaoServiceImpl<RbacTicketDTO, RbacTicket, RbacTicketMapper>
        implements RbacTicketDaoService {
    @Override
    public boolean updateByTicket(RbacTicketDTO ticketInfo) {
        RbacTicket ti = BeanUtil.toBean(ticketInfo, RbacTicket.class);
        LambdaQuery<RbacTicket> wrapper = createLambdaQuery();
        wrapper.andEq(RbacTicket::getTicket, ticketInfo.getTicket());
        return wrapper.update(ti) == 1;
    }

    @Override
    public boolean updateByTicketAndUpdateTime(RbacTicketDTO ticketInfo, Date oldUpdateTime) {
        RbacTicket ti = BeanUtil.toBean(ticketInfo, RbacTicket.class);
        LambdaQuery<RbacTicket> wrapper = createLambdaQuery();
        wrapper.andEq(RbacTicket::getTicket, ticketInfo.getTicket());
        wrapper.andEq(RbacTicket::getUpdateTime, oldUpdateTime);
        return wrapper.update(ti) == 1;
    }

    @Override
    public PageResultDTO<RbacTicketDTO> page(PageQueryDTO<RbacTicketQuery> pageQuery) {
        RbacTicketQuery params = pageQuery.getParams();
        LambdaQuery<RbacTicket> lQuery = createLambdaQuery();

        if (null != params) {
            if (StringUtils.hasText(params.getAccount())) {
                lQuery.andLike(RbacTicket::getAccount, params.getAccount() + "%");
            }
            if (StringUtils.hasText(params.getEmail())) {
                lQuery.andLike(RbacTicket::getEmail, params.getEmail() + "%");
            }
            if (StringUtils.hasText(params.getPhoneNumber())) {
                lQuery.andLike(RbacTicket::getPhoneNumber, params.getPhoneNumber() + "%");
            }
            if (null != params.getUserId()) {
                lQuery.andEq(RbacTicket::getUserId, params.getUserId());
            }
            if (null != params.getUserFrom()) {
                lQuery.andEq(RbacTicket::getUserFrom, params.getUserFrom());
            }
            if (StringUtils.hasText(params.getTicket())) {
                lQuery.andEq(RbacTicket::getTicket, params.getTicket());
            }
            if (StringUtils.hasText(params.getIpAddress())) {
                lQuery.andEq(RbacTicket::getIpAddress, params.getIpAddress());
            }
            if (StringUtils.hasText(params.getNickName())) {
                lQuery.andLike(RbacTicket::getNickName, params.getNickName() + "%");
            }
        }
        lQuery.desc(RbacTicket::getCreateTime);
        PageResult<RbacTicket> page = lQuery.page(pageQuery.getCurrent(), pageQuery.getPageSize());
        return PageUtil.getPageResult(page, RbacTicketDTO.class);
    }

    @Override
    public List<String> findUserAvailableTicket(Long userId) {
        return baseMapper.findUserAvailableTicket(userId);
    }

    @Override
    public RbacTicketDTO getByTicket(String ticket) {
        LambdaQuery<RbacTicket> wrapper = createLambdaQuery();
        wrapper.andEq(RbacTicket::getTicket, ticket);
        RbacTicket ticketInfo = wrapper.single();
        return null == ticketInfo ? null : BeanUtil.toBean(ticketInfo, RbacTicketDTO.class);
    }
}
