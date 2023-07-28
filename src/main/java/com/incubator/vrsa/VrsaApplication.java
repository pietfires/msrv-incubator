package com.incubator.vrsa;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(info = @Info(title = "MSRV Api - VRSA Incubator"))
@SpringBootApplication
public class VrsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VrsaApplication.class, args);
	}

}
