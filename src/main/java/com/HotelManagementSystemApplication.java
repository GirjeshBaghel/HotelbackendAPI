package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@CrossOrigin("*")
@EnableAutoConfiguration
@SpringBootApplication
public class HotelManagementSystemApplication extends SpringBootServletInitializer {

//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
//	{
//		return application.sources(HotelManagementSystemApplication.class);
//	}
	
//	@Bean
//	public WebMvcConfigurer corsConfigurer()
//	{
//		return new WebMvcConfigurer() {
//			public void add
//			
//		};
//	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(HotelManagementSystemApplication.class, args);
		System.out.println("Running");
		
	}
	
	
	//girjesh@123456789
	
	//97.74.80.178
	//apimoteligo
	//2995JE8BOVjJokp
	
	//tar -zxvf jdk-20_linux-x64_bin.tar.gz
	//export JAVA_HOME=/opt/jdk-20_linux-64_bin
	//source ~/.bashrc
	///usr/lib/jvm/java-1.8.0-openjdk-1.8.0.372.b07-1.el7_9.x86_64/jre/bin/java
	//Ngrok
	//https://bbba-14-98-228-18.ngrok-free.app/booking/getAllBooking    
	
	//Swagger
	//http://localhost:2020/swagger-ui/index.html
	
	
	// AWS DB 
	//Username 
	// scrizadb
	
	//password 
	//scriza123	
	
	//endpoints
	//hotelmanagement.ction0oldpnc.eu-north-1.rds.amazonaws.com
	
	//port - 3306
	
}
