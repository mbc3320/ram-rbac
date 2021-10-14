package top.beanshell.rbac.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import top.beanshell.AppBootstrapTest;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.rbac.common.model.bo.TicketInfoBO;
import top.beanshell.rbac.common.model.enums.ClientType;
import top.beanshell.rbac.common.model.enums.LoginType;
import top.beanshell.rbac.model.dto.RbacTicketDTO;
import top.beanshell.rbac.model.dto.RbacUserDTO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;
import top.beanshell.rbac.model.query.RbacTicketQuery;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * ticket凭证管理单元测试
 */
public class RbacTicketServiceTest extends AppBootstrapTest {

    @Resource
    private RbacTicketService ticketService;

    @Before
    public void init() {
        RbacUserDTO userDTO = getUserDTO();
        boolean result = userService.saveEntity(userDTO);
        Assert.assertTrue(result);
    }

    @Test
    public void ticketFlow() {

        TicketInfoBO ticketInfoBO = getTicketInfoBO();

        // 获取ticket信息
        TicketInfoBO ticketInfo = ticketService.get(ticketInfoBO.getTicket());
        Assert.assertEquals(ticketInfoBO.getTicket(), ticketInfo.getTicket());

        // 查询当前用户有效ticket列表
        List<String> list = ticketService.findUserAvailableTicket(STATIC_ID);
        Assert.assertTrue(list.size() >= 1);

        // 更新ticket信息
        Date refreshTime = new Date();
        ticketInfo.setLastRefreshTime(refreshTime);
        boolean updateResult = ticketService.updateTicketInfo(ticketInfo);
        Assert.assertTrue(updateResult);

        // 验证更新后的ticket信息
        TicketInfoBO updateTicketInfo = ticketService.get(ticketInfoBO.getTicket());
        Assert.assertTrue(DateUtil.between(refreshTime, updateTicketInfo.getLastRefreshTime(), DateUnit.MS) == 0);

        // 刷新ticket有效期
        boolean refreshResult = ticketService.refresh(ticketInfoBO.getTicket());
        Assert.assertTrue(refreshResult);

        // 销毁ticket
        boolean result = ticketService.destroy(ticketInfoBO.getTicket());
        Assert.assertTrue(result);
    }

    private TicketInfoBO getTicketInfoBO() {
        UserLoginFormDTO formDTO = getLoginFormDTO();
        TicketInfoBO ticketInfoBO = ticketService.create(formDTO);
        Assert.assertNotNull(ticketInfoBO);
        return ticketInfoBO;
    }

    private UserLoginFormDTO getLoginFormDTO() {
        // 创建ticket
        UserLoginFormDTO formDTO = UserLoginFormDTO.builder()
                .account(ACCOUNT)
                .accountAuth(getPassword())
                .loginType(LoginType.ACCOUNT)
                .clientType(ClientType.WEB)
                .ipAddress("127.0.0.1")
                .userAgent("Chrome 88")
                .build();
        return formDTO;
    }

    @Test
    public void page() {

        TicketInfoBO ticketInfoBO = getTicketInfoBO();

        Assert.assertNotNull(ticketInfoBO);

        PageQueryDTO<RbacTicketQuery> pageQuery = new PageQueryDTO<>();
        RbacTicketQuery query = RbacTicketQuery.builder().account(ACCOUNT).build();
        pageQuery.setParams(query);

        PageResultDTO<RbacTicketDTO> pageResultDTO = ticketService.page(pageQuery);
        Assert.assertTrue(pageResultDTO.getTotal() >= 1);

        ticketService.destroy(ticketInfoBO.getTicket());
    }

    @After
    public void done() {
        boolean result = userService.removeById(STATIC_ID);
        Assert.assertTrue(result);
    }
}
