package com.duong.SpringLinhTinh.mapper;

import com.duong.SpringLinhTinh.dto.request.UserCreationRequest;
import com.duong.SpringLinhTinh.dto.request.UserUpdateRequest;
import com.duong.SpringLinhTinh.dto.response.UserResponse;
import com.duong.SpringLinhTinh.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    //Lấy giá trị fistName gán cho lastName -> trùng nhau
    //@Mapping(source="firstName",target = "lastName")
    UserResponse toUserResponse(User user);
    // @MappingTarget được dùng để chỉ định rằng một phương thức ánh xạ sẽ cập nhật đối tượng đã tồn tại (thay vì tạo một đối tượng mới).
    //khi bạn có một đối tượng A mà bạn muốn cập nhật dữ liệu từ đối tượng B,
    // @MappingTarget cho phép bạn nói rõ rằng dữ liệu từ đối tượng B sẽ được chép vào đối tượng A
    void updateUser(@MappingTarget User user, UserUpdateRequest request);  //UserCreationRequest -> User
}
