package top.beanshell.rbac.mapper;

import org.beetl.sql.mapper.BaseMapper;
import org.beetl.sql.mapper.annotation.Param;
import top.beanshell.rbac.model.pojo.RbacTicket;

import java.util.List;

/**
 * 凭证信息管理DAO
 * @author binchao
 */
public interface RbacTicketMapper extends BaseMapper<RbacTicket> {

    /**
     * 查询用户当前存活的凭证信息
     * @param userId
     * @return
     */
    List<String> findUserAvailableTicket(@Param("userId") Long userId);
}
