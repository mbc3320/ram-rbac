package top.beanshell.rbac.common.model.enums;


import org.beetl.sql.annotation.entity.EnumValue;
import top.beanshell.common.model.enu.EnumCode;

/**
 * 账户状态
 * @author binchao
 */
public enum AccountState implements EnumCode {

    NORMAL(0, "正常"),
    LOCKED(1, "锁定"),
    CANCELED(2, "注销");

    @EnumValue
    @com.baomidou.mybatisplus.annotation.EnumValue
    private Integer code;

    private String text;

    AccountState(Integer code, String text) {
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
