//package com.example.Medicine.Project.Configuration;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfiguration {
//@Bean
//    public SecurityFilterChain SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
//
//        return httpSecurity
//                .formLogin(form -> form
//                        .loginPage("/index")
//                        .defaultSuccessUrl("/home")
//                        .permitAll()
//                )
//                .authorizeHttpRequests(access -> access
//                        .requestMatchers("capture","dotpat",
//                                "/about",
//                                "/service",
//                                "/patient",
//                                "/doctor",
//                                "/contact","/css_files/**", "/js_files/**","/PatientMed").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .logout(logout -> {logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID");
//                })
//                .build();
//
//}
//
//
//
//
//
//}
