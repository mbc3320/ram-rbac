package top.beanshell.rbac.common.model.enums;

import org.beetl.sql.annotation.entity.EnumValue;
import top.beanshell.common.model.enu.EnumCode;

/**
 * 权限类型
 * @author binchao
 */
public enum PermissionType implements EnumCode {

    API(0, "接口"),
    MENU(1, "菜单"),
    FUNCTION(2, "功能");

    @EnumValue
    @com.baomidou.mybatisplus.annotation.EnumValue
    private Integer code;

    private String text;

    PermissionType(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getText() {
        return text;
    }
}
