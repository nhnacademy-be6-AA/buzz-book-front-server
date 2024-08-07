package store.buzzbook.front.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;
import store.buzzbook.front.common.interceptor.CartInterceptor;

@Configuration
@EnableWebMvc
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    private CartInterceptor cartInterceptor;

    @Lazy
    @Autowired
    public void setCartInterceptor(CartInterceptor cartInterceptor) {
        this.cartInterceptor = cartInterceptor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        // 콜솔에 No mapping for GET /favicon.ico 경고 해제하기
        // spring boot 는 자동으로 resources/static/favicon.ico 를 불러오기 때문에 custom 경로를 이용할 시 수동 설정해줘야 함
        registry.addResourceHandler("/favicon.ico")
            .addResourceLocations("classpath:/static/buzz_bee_icon/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cartInterceptor).addPathPatterns("/cart/**");
        registry.addInterceptor(cartInterceptor).addPathPatterns("/order/**");
    }
}

