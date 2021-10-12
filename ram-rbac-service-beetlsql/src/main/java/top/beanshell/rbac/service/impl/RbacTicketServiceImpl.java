package top.beanshell.rbac.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.mapper.BaseMapper;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import top.beanshell.beetlsql.service.impl.CRUDServiceImpl;
import top.beanshell.beetlsql.util.PageUtil;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.rbac.common.constant.RamRbacConst;
import top.beanshell.rbac.common.exception.RbacTicketException;
import top.beanshell.rbac.common.exception.code.RbacTicketStatusCode;
import top.beanshell.rbac.common.model.bo.TicketInfoBO;
import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.mapper.RbacTicketMapper;
import top.beanshell.rbac.model.bo.RbacSysGlobalConfigBO;
import top.beanshell.rbac.model.dto.RbacTicketDTO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;
import top.beanshell.rbac.model.pojo.RbacTicket;
import top.beanshell.rbac.model.query.RbacTicketQuery;
import top.beanshell.rbac.service.RbacConfigService;
import top.beanshell.rbac.service.RbacTicketService;
import top.beanshell.rbac.service.RbacUserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 令牌管理业务实现
 * @author binchao
 */
@Slf4j
@Service
public class RbacTicketServiceImpl extends CRUDServiceImpl<RbacTicketDTO, RbacTicket> implements RbacTicketService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RbacUserService userService;

    @Resource
    private RbacTicketMapper ticketMapper;

    @Resource
    private RbacConfigService configService;

    private static SimpleAsyncTaskExecutor ticketUpdateThreads;

    static {
        ticketUpdateThreads = new SimpleAsyncTaskExecutor("Ticket-Update-Thread");
        ticketUpdateThreads.setConcurrencyLimit(20);
    }

    @Override
    protected BaseMapper<RbacTicket> getDao() {
        return ticketMapper;
    }

    @Override
    public TicketInfoBO get(String ticket) {
        Assert.hasText(ticket, "ticket必填");
        TicketInfoBO ticketInfo = (TicketInfoBO) redisTemplate.opsForValue().get(getCacheKey(ticket));
        if (null == ticketInfo) {
            throw new RbacTicketException(RbacTicketStatusCode.TICKET_IS_NOT_EXIST);
        }
        return ticketInfo;
    }

    @Override
    public boolean refresh(String ticket) {
        log.info("Prepare to refresh ticket: ticket = {}", ticket);
        return refreshTicket(ticket);
    }

    /**
     * ticket延期
     * @param ticket
     */
    private boolean refreshTicket(String ticket) {

        String ticketKey = getCacheKey(ticket);

        TicketInfoBO ticketInfo = get(ticket);

        if (null != ticketInfo) {
            Date lastRefreshTime = new Date();
            ticketInfo.setLastRefreshTime(lastRefreshTime);
            redisTemplate.expire(ticketKey, getTicketTimeout(), TimeUnit.MINUTES);

            ticketUpdateThreads.execute(() -> {
                RbacTicket ti = new RbacTicket();
                ti.setTicket(ticket);
                ti.setLastRefreshTime(lastRefreshTime);
                DateTime expiryTime = DateUtil.offset(lastRefreshTime, DateField.MINUTE, Integer.valueOf(getTicketTimeout() + ""));
                ti.setTimeExpire(expiryTime);
                ti.setUpdateTime(new Date());

                LambdaQuery<RbacTicket> query = createLambdaQuery();
                query.andEq(RbacTicket::getTicket, ticket);
                query.updateSelective(ti);
            });
            return true;
        } else {
            log.error("Expire token failed: token = {}", ticket);
            throw new RbacTicketException(RbacTicketStatusCode.TICKET_TIME_OUT);
        }
    }

    @Override
    public boolean destroy(String ticket) {

        log.info("Prepare to destroy ticket: ticket = {}", ticket);

        boolean result = redisTemplate.delete(getCacheKey(ticket));

        if (result) {
            RbacTicket ti = new RbacTicket();
            ti.setTicket(ticket);
            ti.setTimeExpire(new Date());

            LambdaQuery<RbacTicket> query = createLambdaQuery();
            query.andEq(RbacTicket::getTicket, ticket);
            query.updateSelective(ti);
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TicketInfoBO create(UserLoginFormDTO loginForm) {

        UserDetailBO userDetailBO = userService.login(loginForm);
        if (null != userDetailBO) {
            String ticket = createTicket();

            Date now = new Date();

            TicketInfoBO ticketInfo = new TicketInfoBO();
            ticketInfo.setTicket(ticket);
            ticketInfo.setCreateTime(now);
            ticketInfo.setLastRefreshTime(now);
            ticketInfo.setUserDetail(userDetailBO);

            // 存库
            RbacTicketDTO ti = new RbacTicketDTO();
            ti.setTicket(ticket);
            ti.setAccount(loginForm.getAccount());
            ti.setClientType(loginForm.getClientType());
            ti.setLoginType(loginForm.getLoginType());
            ti.setUserAgent(loginForm.getUserAgent());
            ti.setIpAddress(loginForm.getIpAddress());

            ti.setUserId(userDetailBO.getId());
            ti.setEmail(userDetailBO.getEmail());
            ti.setPhoneNumber(userDetailBO.getPhoneNumber());
            ti.setNickName(userDetailBO.getNickName());
            ti.setCreateTime(new Date());
            ti.setLastRefreshTime(ticketInfo.getLastRefreshTime());

            DateTime  expiryTime = DateUtil.offset(now, DateField.MINUTE, Integer.valueOf(getTicketTimeout() + ""));
            ti.setTimeExpire(expiryTime);

            saveEntity(ti);

            // 缓存ticket
            redisTemplate.opsForValue().set(getCacheKey(ticket), ticketInfo, getTicketTimeout(), TimeUnit.MINUTES);

            return ticketInfo;
        }
        throw new RbacTicketException(RbacTicketStatusCode.TICKET_CREATE_FAILED);
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
        }
        lQuery.desc(RbacTicket::getCreateTime);
        PageResult<RbacTicket> page = lQuery.page(pageQuery.getCurrent(), pageQuery.getPageSize());
        return PageUtil.getPageResult(page, RbacTicketDTO.class);
    }

    @Override
    public List<String> findUserAvailableTicket(Long userId) {
        return ticketMapper.findUserAvailableTicket(userId);
    }

    @Override
    public boolean updateTicketInfo(TicketInfoBO ticketInfoBO) {
        redisTemplate.opsForValue().set(getCacheKey(ticketInfoBO.getTicket()), ticketInfoBO, getTicketTimeout(), TimeUnit.MINUTES);
        // 刷新一次
        refresh(ticketInfoBO.getTicket());
        return true;
    }

    /**
     * 生成ticket
     * @return
     */
    private String createTicket() {
        String ticket = IdUtil.simpleUUID();
        log.info("Ticket created, ticket = {}", ticket);
        return ticket;
    }

    private String getCacheKey(String ticket) {
        return String.format(RamRbacConst.DEFAULT_TICKET_KEY_FORMAT, ticket);
    }

    @Override
    public long getTicketTimeout() {
        RbacSysGlobalConfigBO globalConfig = configService.getGlobalConfig();

        // 默认120分钟
        long tokenTimeout = 120;

        //全局设定存在，则按全局设定的取
        if (null != globalConfig && globalConfig.getTicketTimeout() != null) {
            tokenTimeout = globalConfig.getTicketTimeout();
        }

        return tokenTimeout;
    }

    @Override
    public boolean kickOutUserTickets(Long userId) {
        ticketUpdateThreads.execute(() -> {
            List<String> userTickets = findUserAvailableTicket(userId);
            userTickets.forEach(this::destroy);
        });
        return true;
    }
}
