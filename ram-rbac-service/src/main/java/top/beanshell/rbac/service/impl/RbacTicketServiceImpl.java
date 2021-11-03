package top.beanshell.rbac.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.I18nService;
import top.beanshell.common.service.impl.CRUDServiceImpl;
import top.beanshell.rbac.common.constant.RamRbacConst;
import top.beanshell.rbac.common.exception.RbacTicketException;
import top.beanshell.rbac.common.exception.code.RbacTicketStatusCode;
import top.beanshell.rbac.common.model.bo.TicketInfoBO;
import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.dao.RbacTicketDaoService;
import top.beanshell.rbac.model.bo.RbacSysGlobalConfigBO;
import top.beanshell.rbac.model.dto.RbacTicketDTO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;
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
public class RbacTicketServiceImpl extends CRUDServiceImpl<RbacTicketDTO, RbacTicketDaoService> implements RbacTicketService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RbacUserService userService;

    @Resource
    private RbacConfigService configService;

    @Resource
    private I18nService i18nService;

    private static SimpleAsyncTaskExecutor ticketUpdateThreads;

    static {
        ticketUpdateThreads = new SimpleAsyncTaskExecutor("Ticket-Update-Thread");
        ticketUpdateThreads.setConcurrencyLimit(20);
    }

    @Override
    public TicketInfoBO get(String ticket) {
        Assert.hasText(ticket, i18nService.getMessage("i18n.request.valid.common.required", "ticket"));
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
                RbacTicketDTO dbTicket = daoService.getByTicket(ticket);

                RbacTicketDTO ti = new RbacTicketDTO();
                ti.setTicket(ticket);
                ti.setLastRefreshTime(lastRefreshTime);
                DateTime expiryTime = DateUtil.offset(lastRefreshTime, DateField.MINUTE, Integer.valueOf(getTicketTimeout() + ""));
                ti.setTimeExpire(expiryTime);
                ti.setUpdateTime(new Date());

                daoService.updateByTicketAndUpdateTime(ti, dbTicket.getUpdateTime());
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

            RbacTicketDTO ti = new RbacTicketDTO();
            ti.setTicket(ticket);
            ti.setTimeExpire(new Date());
            daoService.updateByTicket(ti);
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
            ti.setClientInfo(loginForm.getClientInfo());

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
        return daoService.page(pageQuery);
    }

    @Override
    public List<String> findUserAvailableTicket(Long userId) {
        Assert.notNull(userId, i18nService.getMessage("i18n.request.valid.common.required", "userId"));
        return daoService.findUserAvailableTicket(userId);
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
        log.info("Prepare to kick out user ticket: userId = {}", userId);
        ticketUpdateThreads.execute(() -> {
            List<String> userTickets = findUserAvailableTicket(userId);
            userTickets.forEach(this::destroy);
        });
        return true;
    }

    @Override
    public boolean kickOutTicketById(Long id) {
        RbacTicketDTO ticketDTO = daoService.getById(id);
        if (null != ticketDTO) {
            return destroy(ticketDTO.getTicket());
        }
        return false;
    }
}
