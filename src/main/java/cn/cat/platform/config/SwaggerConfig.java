package cn.cat.platform.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linlt
 * @createTime 2020/7/12 15:14
 * @description TODO
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // api基础信息
                .apiInfo(apiInfo())
                .select()
                // 扫描展示api的路径包
                .apis(RequestHandlerSelectors.basePackage("cn.cat.platform.controller"))
                // 对所有路径进行监控
                .paths(PathSelectors.any())
                // 构建
                .build()
                // 保护
                .securitySchemes(security());

    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // api名称
                .title("云平台项目API接口文档")
                .contact(new Contact("广东工业大学C.A.T工作室", null, null))
                // api 版本
                .version("1.0")
                // 构建
                .build();
    }

    private List<ApiKey> security() {
        ArrayList<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeys;
    }

}
