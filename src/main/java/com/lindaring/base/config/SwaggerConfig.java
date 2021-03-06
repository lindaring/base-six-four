package com.lindaring.base.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo()).select().paths(postPaths()).build();
    }

    private Predicate<String> postPaths() {
        return or(
                regex("/base-six-four/v1/base64.*"),
                regex("/base-six-four/v1/cache.*"),
                regex("/base-six-four/v1/general.*"),
                regex("/base-six-four/v1/user.*"),
                regex("/base-six-four/v1/cronjob.*"),
                regex("/base-six-four/v1/url.*"),
                regex("/base-six-four/secure/v1/dashboard.*")
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Base Six Four API")
                .description("Encode and decode base 64 encryption")
                .licenseUrl("license@lindaring.com").version("1.0").build();
    }

}
