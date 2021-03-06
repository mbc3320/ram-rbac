package top.beanshell.rbac.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import top.beanshell.captcha.common.exception.CaptchaException;
import top.beanshell.captcha.common.exception.code.CaptchaStatusCode;
import top.beanshell.captcha.model.dto.CaptchaCreateDTO;
import top.beanshell.captcha.model.dto.CaptchaViewDTO;
import top.beanshell.captcha.service.CaptchaBaseService;
import top.beanshell.common.exception.BaseException;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.I18nService;
import top.beanshell.common.service.impl.CRUDServiceImpl;
import top.beanshell.rbac.common.constant.RamRbacConst;
import top.beanshell.rbac.common.exception.RbacConfigException;
import top.beanshell.rbac.common.exception.RbacTicketException;
import top.beanshell.rbac.common.exception.code.RbacConfigStatusCode;
import top.beanshell.rbac.common.exception.code.RbacTicketStatusCode;
import top.beanshell.rbac.common.model.bo.TicketInfoBO;
import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.dao.RbacTicketDaoService;
import top.beanshell.rbac.model.bo.RbacSysGlobalConfigBO;
import top.beanshell.rbac.model.bo.RbacSysLoginCaptchaMetaBO;
import top.beanshell.rbac.model.dto.RbacCaptchaDTO;
import top.beanshell.rbac.model.dto.RbacTicketDTO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;
import top.beanshell.rbac.model.query.RbacTicketQuery;
import top.beanshell.rbac.service.RbacConfigService;
import top.beanshell.rbac.service.RbacTicketService;
import top.beanshell.rbac.service.RbacUserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * ????????????????????????
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

    @Resource
    private ApplicationContext applicationContext;

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
     * ticket??????
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

            // ??????
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

            // ??????ticket
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
        // ????????????
        refresh(ticketInfoBO.getTicket());
        return true;
    }

    /**
     * ??????ticket
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

        // ??????120??????
        long tokenTimeout = 120;

        //?????????????????????????????????????????????
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

    @Override
    public RbacCaptchaDTO captchaCreate() {
        RbacCaptchaDTO captchaDTO = RbacCaptchaDTO.builder().required(false).build();

        RbacSysGlobalConfigBO configBO = configService.getGlobalConfig();
        if (configBO.getConsoleCaptcha()) {
            captchaDTO.setRequired(true);
            if (null != configBO.getCaptchaMetaList() && configBO.getCaptchaMetaList().size() >= 1) {
                try {

                    Optional<RbacSysLoginCaptchaMetaBO> captchaMetaBO = configBO.getCaptchaMetaList()
                            .stream().filter(meta -> meta.getEnable()).findFirst();

                    CaptchaBaseService captchaBaseService = applicationContext
                            .getBean(captchaMetaBO.get().getCaptchaServiceName(), CaptchaBaseService.class);

                    CaptchaCreateDTO createDTO = CaptchaCreateDTO
                            .builder()
                            .width(captchaMetaBO.get().getWidth())
                            .height(captchaMetaBO.get().getHeight())
                            .extJson(captchaMetaBO.get().getExtJson())
                            .build();

                    CaptchaViewDTO viewDTO = captchaBaseService.create(createDTO);

                    if (null != viewDTO) {
                        captchaDTO.setId(viewDTO.getId());
                        captchaDTO.setBase64Data(viewDTO.getBase64Data());
                        captchaDTO.setExtJson(viewDTO.getExtJson());
                    } else {
                        throw new CaptchaException(CaptchaStatusCode.CAPTCHA_CREATE_FAILED);
                    }

                } catch (BaseException be) {
                    throw be;
                }  catch (Exception e) {
                    log.error("Create captcha error: {}", e.getMessage(), e);
                    throw new CaptchaException(CaptchaStatusCode.UNSUPPORTED_CAPTCHA_TYPE);
                }

            } else {
                throw new RbacConfigException(RbacConfigStatusCode.GLOBAL_CONFIG_OF_CAPTCHA_ERROR);
            }
        }


        return captchaDTO;
    }
}
