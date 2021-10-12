package top.beanshell.rbac.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.beanshell.rbac.interceptor.PermissionInterceptor;
import top.beanshell.rbac.resolver.TicketInfoMethodArgumentResolver;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限配置
 * @author beanshell
 */
@Configuration
public class PermissionConfig implements WebMvcConfigurer {

    @Resource
    private TicketInfoMethodArgumentResolver ticketInfoMethodArgumentResolver;

    @Resource
    private PermissionInterceptor permissionInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(ticketInfoMethodArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html").addResourceLocations("classpath:/static/index.html");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/static/");
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
    }
}
