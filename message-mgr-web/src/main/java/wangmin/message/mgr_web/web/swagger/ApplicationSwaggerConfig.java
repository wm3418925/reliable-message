package wangmin.message.mgr_web.web.swagger;

import org.springframework.context.annotation.Bean;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by wangmin on 2016/11/30.
 */
@EnableSwagger2
public class ApplicationSwaggerConfig {

    @Bean
    public Docket addUserDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfo apiInfo = new ApiInfo(
                "API文档管理",
                "",
                "V2.0",
                "www.baidu.com",
                "王敏",
                "",
                "");
        docket.apiInfo(apiInfo);
        return docket;
    }
}
