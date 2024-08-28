package com.example.richpanel;

import com.example.richpanel.security.ApiKeyFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class RichpanelApplication {

	public static void main(String[] args) {
		SpringApplication.run(RichpanelApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<ApiKeyFilter> customApiKeyFilter() {
		FilterRegistrationBean<ApiKeyFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new ApiKeyFilter());
		registrationBean.addUrlPatterns("/posts/*");
		return registrationBean;
	}
}

