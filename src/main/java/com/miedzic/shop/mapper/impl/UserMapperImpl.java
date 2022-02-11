//package com.miedzic.shop.mapper.impl;
//
//import com.miedzic.shop.domain.dao.User;
//import com.miedzic.shop.domain.dto.UserDto;
//import com.miedzic.shop.mapper.UserMapper;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserMapperImpl implements UserMapper {
//    public UserDto userToUserDto(User user) {
//        return UserDto.builder()
//                .id(user.getId())
//                .firstName(user.getFirstName())
//                .email(user.getEmail())
//                .premium(user.isPremium())
//                .build();
//    }
//
//    @Override
//    public User userDtoToUser(UserDto user) {
//        return User.builder()
//                .id(user.getId())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .premium(user.isPremium())
//                .password(user.getPassword())
//                .build();
//    }
//}
