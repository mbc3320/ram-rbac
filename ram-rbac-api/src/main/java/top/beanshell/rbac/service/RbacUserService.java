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
     * @param loginForm   登录参数
     * @return            用户信息
     */
    UserDetailBO login(UserLoginFormDTO loginForm);

    /**
     * 修改用户密码
     * @param userId     用户ID
     * @param oldPwd     原密码（MD5）
     * @param newPwd     新密码
     * @return           修改结果
     */
    boolean changeCurrentUserPassword(Long userId, String oldPwd, String newPwd);

    /**
     * 分页查询
     * @param pageQuery  查询条件
     * @return           查询结果
     */
    PageResultDTO<RbacUserDTO> page(PageQueryDTO<RbacUserQuery> pageQuery);


    /**
     * 通过主键查询用户是否存在
     * account、email、phoneNumber
     * @param userDTO  用户信息
     * @return         是否可用
     */
    boolean checkUserIfAvailable(RbacUserDTO userDTO);

    /**
     * 通过主键查询用户信息
     * @param account     用户名/邮箱/电话号码
     * @return       用户信息
     */
    RbacUserDTO getUserByUniqueKey(String account);

    /**
     * 组装角色和权限信息
     * @param userDetailBO   用户信息
     * @return               带角色编码列表、权限编码列表的用户信息
     */
    UserDetailBO getUserRoleAndPermission(UserDetailBO userDetailBO);

    /**
     * 修改用户密码
     * @param userId     用户ID
     * @param newPwd     新密码
     * @return           修改结果
     */
    boolean changeUserPassword(Long userId, String newPwd);
}
