package top.beanshell.rbac.common.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * 常用字符串序列化脱敏处理器
 * @author binchao
 */
public class CommonStrDesensitizeConverter extends StdConverter<String, String> {

    private static final String STAR1 = "*";
    private static final String STAR2 = "**";
    private static final String STAR3 = "***";
    private static final String STAR4 = "****";
    private static final String STAR5 = "*****";

    @Override
    public String convert(String value) {
        int length = value.length();
        StringBuilder sb = new StringBuilder(value);
        if (length < 3) {
            return sb.replace(1, 2, STAR1).toString();
        } else if (length < 6) {
            return sb.replace(2, 4, STAR2).toString();
        } else if (length < 11) {
            return sb.replace(5, 8, STAR3).toString();
        } else if (length < 15){
            return sb.replace(5, 9, STAR4).toString();
        } else {
            return sb.replace(5, 10, STAR5).toString();
        }
    }

}
