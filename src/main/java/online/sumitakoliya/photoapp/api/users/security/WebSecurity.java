package online.sumitakoliya.photoapp.api.users.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	private Environment env;
	
	public WebSecurity(Environment env) {
		this.env = env;
	}
	
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
//		http.csrf().disable();
//		http.cs
		http.csrf((csrf)->csrf.disable());
		
		
//		http.aut
		http.
		authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests
				.requestMatchers(HttpMethod.POST, "/users").access(new WebExpressionAuthorizationManager("hasIpAddress('"+env.getProperty("gateway.ip")+"')"))
//				.permitAll()
				.requestMatchers(HttpMethod.GET, "/users/status").access(new WebExpressionAuthorizationManager("hasIpAddress('"+env.getProperty("gateway.ip")+"')"))
				.requestMatchers(new AntPathRequestMatcher("/h2console/**")).permitAll()
//				.and()
//				.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				);
		
		http.sessionManagement((sm)->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
				
		
		
//		http.headers().frameOptions().disable();
		http.headers((headers)->headers.frameOptions((f)->f.disable()));
		
		return http.build();
	}
}
