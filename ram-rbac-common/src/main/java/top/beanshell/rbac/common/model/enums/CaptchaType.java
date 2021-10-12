package top.beanshell.rbac.common.model.enums;


import org.beetl.sql.annotation.entity.EnumValue;
import top.beanshell.common.model.enu.EnumCode;

/**
 * 图形校验码类型
 * @author binchao
 */
public enum CaptchaType implements EnumCode {

    ALPHABET(0, "字母表"),
    FORMULA(1, "算术式")
    ;

    @EnumValue
    @com.baomidou.mybatisplus.annotation.EnumValue
    private Integer code;

    private String text;

    CaptchaType(Integer code, String text) {
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
