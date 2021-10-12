package top.beanshell.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
