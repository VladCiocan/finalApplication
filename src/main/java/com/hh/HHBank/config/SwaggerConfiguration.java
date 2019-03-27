package com.hh.HHBank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.hh.HHBank.controllers"))
				.paths(PathSelectors.any())
				.build()
				.pathMapping("");
	}
	
	private ApiInfo getApiInfo() {
		Contact contact = new Contact("Contact: ", "Site: ", "Email: ");
		return new ApiInfoBuilder()
			.title("Harte Hanks Bank APIS")
			.description("All apies for this application listed with swagger")
			.version("1.0.0")
			.license("Apache 2.0")
			.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
			.contact(contact)
			.build();
	}
}

