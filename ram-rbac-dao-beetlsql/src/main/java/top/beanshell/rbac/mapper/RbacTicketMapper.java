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
     * @param userId     用户ID
     * @return           用户有效凭证列表
     */
    List<String> findUserAvailableTicket(@Param("userId") Long userId);
}
