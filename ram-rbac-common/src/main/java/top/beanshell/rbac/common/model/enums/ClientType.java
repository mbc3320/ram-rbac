package top.beanshell.rbac.common.model.enums;


import org.beetl.sql.annotation.entity.EnumValue;
import top.beanshell.common.model.enu.EnumCode;

/**
 * 客户端类型
 * @author binchao
 */
public enum ClientType implements EnumCode {

    WEB(0, "Web浏览器"),
    ANDROID(1, "Android"),
    IOS(2, "IOS"),
    OTHERS(3, "其他")
    ;

    @EnumValue
    @com.baomidou.mybatisplus.annotation.EnumValue
    private Integer code;

    private String text;

    ClientType(Integer code, String text) {
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
