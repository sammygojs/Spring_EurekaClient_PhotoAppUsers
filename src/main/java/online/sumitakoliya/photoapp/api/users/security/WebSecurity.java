//package online.sumitakoliya.photoapp.api.users.security;
//
//import org.springframework.context.annotation.Bean;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import online.sumitakoliya.photoapp.api.users.service.UsersService;
//
//import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurity {
//	
//	private Environment env;
//	
//	private UsersService usersService;
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
//	
////	public WebSecurity(Environment env) {
////		this.env = env;
////	}
//	
//	
//	public WebSecurity(Environment env, UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder) {
//		this.env = env;
//		this.usersService = usersService;
//		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//	}
//
//
//	@Bean
//	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
//		
//		//configure authenticationManagereBuilder
//		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//		
//		authenticationManagerBuilder.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
//		
//		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
//		
////		http.csrf().disable();
////		http.cs
//		http.csrf((csrf)->csrf.disable());
//		
//		
////		http.aut
//		http.
//		authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests
//				.requestMatchers(HttpMethod.POST, "/users").access(new WebExpressionAuthorizationManager("hasIpAddress('"+env.getProperty("gateway.ip")+"')"))
////				.permitAll()
//				.requestMatchers(HttpMethod.GET, "/users/status").access(new WebExpressionAuthorizationManager("hasIpAddress('"+env.getProperty("gateway.ip")+"')"))
//				.requestMatchers(new AntPathRequestMatcher("/h2console/**")).permitAll()
//				);
//		
//		http.addFilter(new AuthenticationFilter( authenticationManager,usersService, env));
//		http.authenticationManager(authenticationManager);
//		
//		http.sessionManagement((sm)->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//				
//		
//		
////		http.headers().frameOptions().disable();
//		http.headers((headers)->headers.frameOptions((f)->f.disable()));
//		
//		return http.build();
//	}
//}

package online.sumitakoliya.photoapp.api.users.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import online.sumitakoliya.photoapp.api.users.service.UsersService;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	private Environment environment;
	private UsersService usersService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity(Environment environment, UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.environment = environment;
		this.usersService = usersService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
    
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
    	
    	// Configure AuthenticationManagerBuilder
    	AuthenticationManagerBuilder authenticationManagerBuilder = 
    			http.getSharedObject(AuthenticationManagerBuilder.class);
    	
    	authenticationManagerBuilder.userDetailsService(usersService)
    	.passwordEncoder(bCryptPasswordEncoder);
    	
    	AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
    	
    	// Create AuthenticationFilter
    	AuthenticationFilter authenticationFilter = 
    			new AuthenticationFilter(usersService, environment, authenticationManager);
    	authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
    	
    	http.csrf((csrf) -> csrf.disable());
  
    	http.authorizeHttpRequests((authz) -> authz
        .requestMatchers(HttpMethod.POST, "/users").permitAll()
        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll())
        .addFilter(authenticationFilter)
        .authenticationManager(authenticationManager)
        .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
 
    	http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()));
        return http.build();

    }
}

