package top.beanshell.rbac.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录验证码配置元数据
 * @author binchao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RbacSysLoginCaptchaMetaBO implements Serializable {

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 名称
     */
    private String captchaMetaName;

    /**
     * 验证码服务名
     */
    private String captchaServiceName;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 验证码高度
     */
    private Integer height;

    /**
     * 其他配置参数
     */
    private String extJson;
}
