package cn.cat.platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author linlt
 * @CreateTime 2020/3/20 11:38
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {
    /**
     * 配置静态资源访问路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations("C:\\Users\\linlt\\Desktop\\cloud-platform\\uploads");
    }
}
