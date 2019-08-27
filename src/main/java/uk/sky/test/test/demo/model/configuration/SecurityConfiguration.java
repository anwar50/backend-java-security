package uk.sky.test.test.demo.model.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

//putting the security logic into the project!
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        //encoder referenced in the webconfig file in configuration
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

        //a datasource implemented out of the boc by spring boot
        //the actual database information is stored in the properties file
    @Autowired
    private DataSource dataSource;

        //a reference to the user and role queries identified in the properties file
    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;


        //this function below is a special mechanism that gets a user based on the password encoder, data source and user and role query
    @Override
    protected  void configure(AuthenticationManagerBuilder auth)
                throws Exception {
            auth.
                    jdbcAuthentication()
                    .usersByUsernameQuery(usersQuery)
                    .authoritiesByUsernameQuery(rolesQuery)
                    .dataSource(dataSource)
                    .passwordEncoder(bCryptPasswordEncoder);
        }

        //defining antmatchers to provide access based on the role and identifying the
        //login process, success, failure and logout page!
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/admin/**").permitAll().anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/admin/home")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/access-denied");
    }
        //this function allows the spring security to know that the resources folder can be served skipping the antMarchers defined
    @Override
        public void configure(WebSecurity web) throws Exception{
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

}
