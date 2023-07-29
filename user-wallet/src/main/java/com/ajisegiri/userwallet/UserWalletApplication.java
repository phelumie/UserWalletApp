package com.ajisegiri.userwallet;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling

@OpenAPIDefinition(info =
@Info(title = "USER WALLET API", version = "${springdoc.version}", description = "Documentation USER WALLET API v1.0"),servers = {},security = @SecurityRequirement(name = "Bearer Authentication"))
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)

public class UserWalletApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		SpringApplication.run(UserWalletApplication.class, args);
	}


}
