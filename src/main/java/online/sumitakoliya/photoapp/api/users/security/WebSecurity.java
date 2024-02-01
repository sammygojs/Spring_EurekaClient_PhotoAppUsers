package online.sumitakoliya.photoapp.api.users.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
//		http.csrf().disable();
//		http.cs
		http.csrf((csrf)->csrf.disable());
		
		
//		http.aut
		http.
		authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests
				.requestMatchers(HttpMethod.POST, "/users").permitAll()
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
