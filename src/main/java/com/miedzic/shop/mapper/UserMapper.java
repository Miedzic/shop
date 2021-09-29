package com.miedzic.shop.mapper;

import com.miedzic.shop.domain.dao.User;
import com.miedzic.shop.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
     @Mapping(target = "password", ignore = true )
     UserDto userToUserDto(User user);
     User userDtoToUser(UserDto user);
}
