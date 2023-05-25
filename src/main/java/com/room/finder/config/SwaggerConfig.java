
package com.room.finder.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.room.finder.constant.AppConstant;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

	private ApiKey apiKeys() {
		return new ApiKey("JWT", AppConstant.AUTHORIZATION_HEADER, "header");

	}

	private List<SecurityContext> securityContexts() {
		return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
	}

	private List<SecurityReference> sf() {
		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
		return Arrays.asList(new SecurityReference("scope", new AuthorizationScope[] { scope }));
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys())).select()
				.apis(RequestHandlerSelectors.basePackage("com.room.finder")).paths(PathSelectors.ant("/**")).build();
	}

	private ApiInfo getInfo() {
		return new ApiInfo("Room-Finder Application", "This api is developed by Abishek Khadka", "1.0",
				"Terms of Servie", new Contact("Abishek", null, "trekkieabishek94@gmail.com"), "License of APIS",
				"API license URL", Collections.emptyList());
	}
}
