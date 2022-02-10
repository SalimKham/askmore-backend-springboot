package io.khaminfo.askmore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import io.khaminfo.askmore.services.CostumUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtAuthanticationEntryPoint jwtAuthEntryPoint;
	@Autowired
	private CostumUserDetailsService costumUserDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean
	public JWTAuthFilter authFilter () { return new JWTAuthFilter();}
  
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);    
        firewall.setUnsafeAllowAnyHttpMethod(true);
       firewall.setAllowSemicolon(true);
        return firewall;
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
    	
        //@formatter:off
        super.configure(web);
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
  
    }
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(costumUserDetailsService)
				.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().headers()
				.frameOptions().sameOrigin().and().authorizeRequests()
				.antMatchers("/",SecurityConstants.H2_URL, SecurityConstants.SIGN_UP_URLS, SecurityConstants.DOWNLOAD_URL,"/favicon.ico", "/**/*.th.png", "/**/*.png" ,"/**/*.pdf",
						"/**/*.gif", "/**/*.html", "/**/*.jpg", "/**/*.css", "/**/*.js")
				.permitAll().anyRequest().authenticated();
		http.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);

	}

}
