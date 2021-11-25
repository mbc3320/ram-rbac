package top.beanshell.rbac.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 验证码信息
 * @author binchao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RbacCaptchaDTO implements Serializable {

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 图形校验码ID
     */
    private String id;

    /**
     * 图形校验码base64数据
     */
    private String base64Data;

    /**
     * 扩展信息
     */
    private String extJson;
}
