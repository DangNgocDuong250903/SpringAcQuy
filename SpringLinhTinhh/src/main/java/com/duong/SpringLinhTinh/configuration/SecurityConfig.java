package com.duong.SpringLinhTinh.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS={"/users","/auth/token","/auth/introspect"};
        // Cac endpoint public
    @Value("${jwt.signerKey}")
    private String SigningKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests(requests ->
                        requests.requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).permitAll()
        // Cho phep tat ca cac request POST den cac endpoint PUBLIC_ENDPOINTS
                                .anyRequest().authenticated());
        // Đối với mọi yêu cầu khác, yêu cầu người dùng phải được xác thực

        httpSecurity.oauth2ResourceServer(oath2 ->
        //Cấu hình để sử dụng OAuth2 Resource Server.
                oath2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
        // Cấu hình OAuth2 Resource Server để sử dụng JWT, và thiết lập bộ giải mã JWT
                );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        // Disable CSRF neu khong thi khong the hien thi du lieu tren trinh duyet

        return httpSecurity.build();
    }


    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(SigningKey.getBytes(), "HS512"); // Tao ra mot doi tuong SecretKeySpec de tao ra doi tuong JwtDecoder
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
        // Tao ra mot doi tuong JwtDecoder de giai ma token
    }

    @Bean
    PasswordEncoder  passwordEncoder(){
        return  new BCryptPasswordEncoder(10);
    }
    // Ma hoa password de dung nhieu lan
}


//Các endpoint công khai có thể được truy cập mà không cần xác thực.
//Các yêu cầu khác yêu cầu xác thực.
//Sử dụng OAuth2 Resource Server với JWT để xác thực.
//Vô hiệu hóa CSRF để tránh lỗi khi hiển thị dữ liệu trên trình duyệt.
//Sử dụng khóa ký từ file cấu hình và giải mã JWT sử dụng thuật toán HS512.