package com.duong.SpringLinhTinh.exception;

import com.duong.SpringLinhTinh.dto.request.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandle{
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> hanlingRuntimeException(RuntimeException exception){
        ApiResponse apiResponse = new ApiResponse();
        //ErrorResponse là một class tạo ra để trả về thông điệp lỗi
        //ma Loi
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> hanlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        //ErrorResponse là một class tạo ra để trả về thông điệp lỗi
        //ma Loi
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> hanlingAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity
                .status(ErrorCode.UNAUTHORIZED.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
                );
    }


    //Phương thức hanlingRuntimeException tạo một đối tượng ResponseEntity
    // với mã trạng thái 400 (Bad Request)
    // và nội dung là thông điệp của ngoại lệ (exception.getMessage())
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> hanlingMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();
        //Chuyển đổi thông điệp lỗi thành ErrorCode
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try{

            errorCode = errorCode.valueOf(enumKey);

        }catch (IllegalArgumentException e){

        }
        //sử dụng enum ErrorCode giúp quản lý và duy trì các mã lỗi và thông điệp lỗi một cách dễ dàng và nhất quán.

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
    //@ExceptionHandler(MethodArgumentNotValidException.class) giúp bạn tùy chỉnh phản hồi
    // lỗi khi validation không thành công, cung cấp cho client thông tin cụ thể về lỗi để họ có thể khắc phục.
    //getFieldError().getDefaultMessage()); : lấy thông điệp mặc định của lỗi,
    // thông điệp này được định nghĩa trong @Size(min = 8,message = "Password nust be at least 8 characters")
}
