package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.beanshell.common.exception.BaseException;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.utils.IdUtil;
import top.beanshell.mybatis.impl.CRUDServiceImpl;
import top.beanshell.mybatis.util.PageUtil;
import top.beanshell.rbac.common.exception.RbacUserException;
import top.beanshell.rbac.common.exception.code.RbacUserStatusCode;
import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.mapper.RbacUserMapper;
import top.beanshell.rbac.model.dto.RbacUserDTO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;
import top.beanshell.rbac.model.pojo.RbacUser;
import top.beanshell.rbac.model.query.RbacUserQuery;
import top.beanshell.rbac.service.RbacRoleService;
import top.beanshell.rbac.service.RbacUserService;
import top.beanshell.rbac.service.custom.CustomLoginFactoryService;
import top.beanshell.rbac.util.PasswordStorage;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户管理业务实现
 * @author binchao
 */
@Slf4j
@Service
public class RbacUserServiceImpl extends CRUDServiceImpl<RbacUserDTO, RbacUser, RbacUserMapper> implements RbacUserService {

    @Resource
    private RbacUserMapper userMapper;

    @Resource
    private RbacRoleService roleService;

    @Resource
    private CustomLoginFactoryService customLoginFactoryService;

    @Override
    public UserDetailBO login(UserLoginFormDTO loginForm) {

        return customLoginFactoryService.getLoginService(loginForm.getLoginType()).login(loginForm);
    }

    @Override
    public boolean changeCurrentUserPassword(Long userId, String oldPwd, String newPwd) {
        RbacUserDTO rbacUser = getById(userId);
        try {
            if (null != rbacUser && PasswordStorage.verifyPassword(oldPwd, rbacUser.getPassword())) {
                return changePwd(newPwd, rbacUser);
            } else {
                throw new RbacUserException(RbacUserStatusCode.USER_PASSWORD_ERROR);
            }
        } catch (BaseException be) {
            throw be;
        } catch (Exception e) {
            log.error("change password error: userId = {}, msg = {}", userId, e.getMessage(), e);
        }
        return false;
    }

    /**
     * 修改用户密码
     * @param newPwd
     * @param rbacUser
     * @return
     * @throws PasswordStorage.CannotPerformOperationException
     */
    private boolean changePwd(String newPwd, RbacUserDTO rbacUser) throws PasswordStorage.CannotPerformOperationException {
        String newHashPwd = PasswordStorage.createHash(newPwd);
        RbacUser newRbacUser = new RbacUser();
        newRbacUser.setId(rbacUser.getId());
        newRbacUser.setPassword(newHashPwd);
        newRbacUser.setUpdateTime(new Date());
        return updateById(newRbacUser);
    }

    @Override
    public PageResultDTO<RbacUserDTO> page(PageQueryDTO<RbacUserQuery> pageQuery) {
        RbacUserQuery params = pageQuery.getParams();
        LambdaQueryWrapper<RbacUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(RbacUser::getId, RbacUser::getAccount, RbacUser::getAccountState,
                RbacUser::getEmail, RbacUser::getPhoneNumber, RbacUser::getNickName);
        if (null != params) {
            if (StringUtils.hasText(params.getAccount())) {
                wrapper.likeRight(RbacUser::getAccount, params.getAccount());
            }
            if (StringUtils.hasText(params.getEmail())) {
                wrapper.likeRight(RbacUser::getEmail, params.getEmail());
            }
            if (StringUtils.hasText(params.getPhoneNumber())) {
                wrapper.likeRight(RbacUser::getPhoneNumber, params.getPhoneNumber());
            }
            if (null != params.getAccountState()) {
                wrapper.eq(RbacUser::getAccountState, params.getAccountState());
            }
            if (StringUtils.hasText(params.getNickname())) {
                wrapper.eq(RbacUser::getNickName, params.getNickname());
            }
        }

        Page<RbacUser> page = PageUtil.getPage(pageQuery);
        page(page, wrapper);
        return PageUtil.getPageResult(page, RbacUserDTO.class);
    }

    @Override
    public boolean saveEntity(RbacUserDTO dto) {

        if (!checkUserIfAvailable(dto)) {
            throw new RbacUserException(RbacUserStatusCode.USER_EXIST);
        }

        Long userId = dto.getId() == null ? IdUtil.getId() : dto.getId();

        RbacUser rbacUser = new RbacUser();
        BeanUtil.copyProperties(dto, rbacUser, "password");
        rbacUser.init();
        rbacUser.setId(userId);
        try {
            String password = PasswordStorage.createHash(dto.getPassword());
            rbacUser.setPassword(password);
            userMapper.insert(rbacUser);
            return true;
        } catch (Exception e) {
            log.error("Save user error: {}", e.getMessage(), e);
            throw new RbacUserException(RbacUserStatusCode.REG_FAILED);
        }
    }

    @Override
    public boolean updateEntityById(RbacUserDTO dto) {
        // 仅能更新昵称、帐号状态
        RbacUser rbacUser = new RbacUser();
        rbacUser.setId(dto.getId());
        rbacUser.setNickName(dto.getNickName());
        rbacUser.setAccountState(dto.getAccountState());
        rbacUser.setUpdateTime(new Date());

        return updateById(rbacUser);
    }

    @Override
    public RbacUserDTO getById(Long id) {
        RbacUserDTO userDTO = super.getById(id);
        // 不输出密码
        userDTO.setPassword(null);
        return userDTO;
    }

    /**
     * 通过主键检测用户是否存在
     * @param userDTO
     * @return true: 用户可新增  false: 用户已存在
     */
    @Override
    public boolean checkUserIfAvailable(RbacUserDTO userDTO) {

        LambdaQueryWrapper<RbacUser> userLambdaQuery = new LambdaQueryWrapper<>();

        userLambdaQuery.eq(RbacUser::getAccount, userDTO.getAccount());

        if (StringUtils.hasText(userDTO.getEmail())) {
            userLambdaQuery.or().eq(RbacUser::getEmail, userDTO.getEmail());
        }
        if (StringUtils.hasText(userDTO.getPhoneNumber())) {
            userLambdaQuery.or().eq(RbacUser::getPhoneNumber, userDTO.getPhoneNumber());
        }

        return count(userLambdaQuery) == 0;
    }

    @Override
    public RbacUserDTO getUserByUniqueKey(String account) {
        LambdaQueryWrapper<RbacUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbacUser::getAccount, account)
                .or().eq(RbacUser::getEmail, account)
                .or().eq(RbacUser::getPhoneNumber, account);

        RbacUser rbacUser = getOne(wrapper);
        if (null != rbacUser) {
            return BeanUtil.toBean(rbacUser, RbacUserDTO.class);
        }
        return null;
    }

    @Override
    public UserDetailBO getUserRoleAndPermission(UserDetailBO userDetailBO) {

        List<String> roleCodeList = roleService.findUserRoleCode(userDetailBO.getId());

        List<String> permissionCodeList = roleService.findUserPermissionCode(userDetailBO.getId());

        userDetailBO.setRoleList(roleCodeList);
        userDetailBO.setAccessList(permissionCodeList);

        return userDetailBO;
    }

    @Override
    public boolean changeUserPassword(Long userId, String newPwd) {
        RbacUserDTO rbacUser = getById(userId);
        try {
            if (null != rbacUser) {
                return changePwd(newPwd, rbacUser);
            } else {
                throw new RbacUserException(RbacUserStatusCode.USER_PASSWORD_ERROR);
            }
        } catch (BaseException be) {
            throw be;
        } catch (Exception e) {
            log.error("change password error: userId = {}, msg = {}", userId, e.getMessage(), e);
        }
        return false;
    }
}
