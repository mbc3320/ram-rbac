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
import top.beanshell.rbac.dao.RbacTicketDaoService;
import top.beanshell.rbac.mapper.RbacTicketMapper;
import top.beanshell.rbac.model.dto.RbacTicketDTO;
import top.beanshell.rbac.model.pojo.RbacTicket;
import top.beanshell.rbac.model.query.RbacTicketQuery;

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
        LambdaQueryWrapper<RbacTicket> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbacTicket::getTicket, ticketInfo.getTicket());
        return update(ti, wrapper);
    }

    @Override
    public PageResultDTO<RbacTicketDTO> page(PageQueryDTO<RbacTicketQuery> pageQuery) {
        RbacTicketQuery params = pageQuery.getParams();
        LambdaQueryWrapper<RbacTicket> wrapper = new LambdaQueryWrapper<>();

        if (null != params) {
            if (StringUtils.hasText(params.getAccount())) {
                wrapper.likeRight(RbacTicket::getAccount, params.getAccount());
            }
            if (StringUtils.hasText(params.getEmail())) {
                wrapper.likeRight(RbacTicket::getEmail, params.getEmail());
            }
            if (StringUtils.hasText(params.getPhoneNumber())) {
                wrapper.likeRight(RbacTicket::getPhoneNumber, params.getPhoneNumber());
            }
            if (null != params.getUserId()) {
                wrapper.eq(RbacTicket::getUserId, params.getUserId());
            }
            if (null != params.getUserFrom()) {
                wrapper.eq(RbacTicket::getUserFrom, params.getUserFrom());
            }
            if (StringUtils.hasText(params.getTicket())) {
                wrapper.eq(RbacTicket::getTicket, params.getTicket());
            }
            if (StringUtils.hasText(params.getIpAddress())) {
                wrapper.eq(RbacTicket::getIpAddress, params.getIpAddress());
            }
            if (StringUtils.hasText(params.getNickName())) {
                wrapper.likeRight(RbacTicket::getNickName, params.getNickName());
            }
        }
        wrapper.orderByDesc(RbacTicket::getCreateTime);

        Page<RbacTicket> page = PageUtil.getPage(pageQuery);
        page(page, wrapper);
        return PageUtil.getPageResult(page, RbacTicketDTO.class);
    }

    @Override
    public List<String> findUserAvailableTicket(Long userId) {
        return baseMapper.findUserAvailableTicket(userId);
    }
}
