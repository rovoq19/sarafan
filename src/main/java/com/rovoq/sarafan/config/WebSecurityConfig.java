package com.rovoq.sarafan.config;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.rovoq.sarafan.domain.User;
import com.rovoq.sarafan.repo.UserDetailsRepo;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/**")
	        .authorizeRequests()
	        .antMatchers("/", "/login**", "/js/**", "/error**").permitAll() //разрешить все запросы по текущим адресам и их дочерним
	        .anyRequest().authenticated()
	        .and().logout().logoutSuccessUrl("/").permitAll()
	        .and()
	        .csrf().disable();
	}

	@Bean
	public PrincipalExtractor principalExtractor(UserDetailsRepo userDetailsRepo) { //Создание авторизованного пользователя
		return map -> {
			String id = (String) map.get("sub");//Получение id пользователя для поиска в бд
			
			User user = userDetailsRepo.findById(id).orElseGet(()->{ //Поиск пользователя в бд. Если не найден - создать нового
				User newUser = new User();
				
				newUser.setId(id);
				newUser.setName((String) map.get("name"));
				newUser.setEmail((String) map.get("email"));
				newUser.setGender((String) map.get("gender"));
				newUser.setLocale((String) map.get("locale"));
				newUser.setUserpic((String) map.get("picture"));
				
				return newUser;
			});
			
			user.setLastVisit(LocalDateTime.now());
			
			return userDetailsRepo.save(user);
		};
	}
}
