package top.beanshell.rbac.service;


import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.model.dto.RbacUserDTO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;
import top.beanshell.rbac.model.query.RbacUserQuery;

/**
 * 用户管理业务接口
 * @author binchao
 */
public interface RbacUserService extends ServiceI<RbacUserDTO> {

    /**
     * 用户登录
     * @param loginForm
     * @return
     */
    UserDetailBO login(UserLoginFormDTO loginForm);

    /**
     * 修改用户密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    boolean changeCurrentUserPassword(Long userId, String oldPwd, String newPwd);

    /**
     * 分页查询
     * @param pageQuery
     * @return
     */
    PageResultDTO<RbacUserDTO> page(PageQueryDTO<RbacUserQuery> pageQuery);


    /**
     * 通过主键查询用户是否存在
     * account、email、phoneNumber
     * @param userDTO
     * @return
     */
    boolean checkUserIfAvailable(RbacUserDTO userDTO);

    /**
     * 通过主键查询用户信息
     * @param account     用户名/邮箱/电话号码
     * @return
     */
    RbacUserDTO getUserByUniqueKey(String account);

    /**
     * 组装角色和权限信息
     * @param userDetailBO
     * @return
     */
    UserDetailBO getUserRoleAndPermission(UserDetailBO userDetailBO);

    /**
     * 修改用户密码
     * @param userId
     * @param newPwd
     * @return
     */
    boolean changeUserPassword(Long userId, String newPwd);
}
