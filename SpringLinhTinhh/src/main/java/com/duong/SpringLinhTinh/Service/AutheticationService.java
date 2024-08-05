package com.duong.SpringLinhTinh.Service;

import com.duong.SpringLinhTinh.dto.request.AuthenticationRequest;
import com.duong.SpringLinhTinh.dto.response.AuthenticationResponse;
import com.duong.SpringLinhTinh.exception.AppException;
import com.duong.SpringLinhTinh.exception.ErrorCode;
import com.duong.SpringLinhTinh.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AutheticationService {
    UserRepository userRepository;

    @NonFinal
    protected  static final String SIGNER_KEY =
            "ELjnG67maSPb0PG8ropXO1mUgoVe6q4aDJmbkVmxXv0Z+zn6HtlDts7B4p9D7uUX";

    private final ProjectInfoAutoConfiguration projectInfoAutoConfiguration;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        // Ham BCryptPasswordEncoder se ma hoa password nguoi dung nhap vao
        // Ham nay se tra ve true neu username va password dung, nguoc lai se tra ve false
       var user = userRepository.findByUsername(request.getUsername())
               .orElseThrow(() -> new AppException(ErrorCode.USERNAME_INVALID));
        // Tim user trong DB theo username nguoi dung nhap vao
        boolean authentication =  passwordEncoder.matches(request.getPassword(), user.getPassword());
        // Ham matches se so sanh password nguoi dung nhap vao voi password da duoc ma hoa trong DB
        if(!authentication)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(request.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
        }

        private String generateToken(String username){
        // Tao token JWT
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        // Tao JWSHeader de chua thong tin ve thuat toan ma hoa
            JWTClaimsSet jwtClaimsSet= new JWTClaimsSet.Builder()
                    //subject: thông tin về đối tượng mà token đại diện (trong trường hợp này là username).
                    //issuer: nơi phát hành token (ở đây là "devteria.com").
                    //issueTime: thời gian phát hành token.
                    //expirationTime: thời gian hết hạn của token (được tính là 1 giờ từ thời điểm hiện tại).
                    .subject(username)
                    .issuer("devteria.com")
                    .issueTime(new Date())
                    .expirationTime(new Date(
                            // Tao token co thoi gian song la 1h
                            Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                    ))
                    .claim("CustomerClaim", "This is a custom claim")
                    .build();
            // Tao JWTClaimsSet de chua thong tin cua token
            Payload payload = new Payload(jwtClaimsSet.toJSONObject());
            // Tao Payload de chua JWTClaimsSet
            JWSObject jwsObject = new JWSObject(header,payload);

            //Sử dụng khóa ký (SIGNER_KEY) để ký jwsObject.
            //Nếu ký thành công, trả về token dưới dạng chuỗi.
            //Nếu có lỗi trong quá trình ký, ghi lại lỗi và ném ngoại lệ RuntimeException.
            try {
                jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
                return jwsObject.serialize();
            } catch (JOSEException e) {
                log.error("Cannot create token", e);
                throw new RuntimeException(e);
            }
            // Tao JWSObject de chua Header va Payload
        }
}
