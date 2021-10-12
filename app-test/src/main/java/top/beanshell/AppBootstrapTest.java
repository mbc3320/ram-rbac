package top.beanshell;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.beanshell.rbac.common.model.enums.AccountState;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.model.dto.RbacPermissionDTO;
import top.beanshell.rbac.model.dto.RbacRoleDTO;
import top.beanshell.rbac.model.dto.RbacUserDTO;
import top.beanshell.rbac.service.RbacPermissionService;
import top.beanshell.rbac.service.RbacRoleService;
import top.beanshell.rbac.service.RbacUserService;

import javax.annotation.Resource;

/**
 * 单元测试启动器
 * @author: binchao
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppBootstrapTest {

    protected static final Long STATIC_ID = 123456L;

    protected static final String ACCOUNT = "junit_test";

    protected static final String PASSWORD = "junit_test";

    protected static final String COL_TITLE = "Junit Test";

    protected static final String EMAIL = "junit_test@hotmail.com";

    protected static final String PHONE_NUMBER = "13800138000";

    /**
     * 根菜单节点ID
     * 注意：数据库初始化脚本已附带初始化数据，请先初始化数据库，也可按需更改ID
     */
    protected static final Long ROOT_MENU_ID = 1447415378427310082L;

    @Resource
    protected RbacUserService userService;

    @Resource
    protected RbacRoleService roleService;

    @Resource
    protected RbacPermissionService permissionService;

    @Test
    public void loadTest() {
        log.info("JUnit Test Start...");
        initAdminUser();
    }

    /**
     * 初始化管理员
     */
    protected void initAdminUser() {

        RbacUserDTO userDTO = RbacUserDTO.builder()
                .account("admin")
                .password(SecureUtil.md5("Ram2021"))
                .nickName("BeanShell.More")
                .email("mobinchao@hotmail.com")
                .accountState(AccountState.NORMAL)
                .build();
        if (userService.checkUserIfAvailable(userDTO)) {
            boolean result = userService.saveEntity(userDTO);
            Assert.assertTrue(result);
        }
    }

    /**
     * 获取用户DTO
     *
     */
    protected RbacUserDTO getUserDTO() {
        RbacUserDTO userDTO = RbacUserDTO.builder()
                .account(ACCOUNT)
                .password(getPassword())
                .nickName(COL_TITLE)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .accountState(AccountState.NORMAL)
                .build();
        userDTO.setId(STATIC_ID);
        return userDTO;
    }


    /**
     * 获取RoleDTO
     * @return
     */
    protected RbacRoleDTO getRoleDTO() {
        RbacRoleDTO roleDTO = RbacRoleDTO.builder()
                .roleCode(ACCOUNT)
                .roleName(COL_TITLE)
                .roleDefault(false)
                .build();
        roleDTO.setId(STATIC_ID);
        return roleDTO;
    }

    /**
     * 获取permissionDTO
     * @return
     */
    protected RbacPermissionDTO getPermissionDTO() {
        RbacPermissionDTO permissionDTO = RbacPermissionDTO.builder()
                .permissionCode(ACCOUNT)
                .permissionName(COL_TITLE)
                .permissionType(PermissionType.MENU)
                .pid(ROOT_MENU_ID)
                .build();
        permissionDTO.setId(STATIC_ID);
        return permissionDTO;
    }

    /**
     * 获取单元测试用户密码
     * @return
     */
    protected String getPassword() {
        return SecureUtil.md5(PASSWORD);
    }

}
