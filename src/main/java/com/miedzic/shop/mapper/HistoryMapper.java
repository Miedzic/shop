package com.miedzic.shop.mapper;

import com.miedzic.shop.domain.dao.Product;
import com.miedzic.shop.domain.dao.User;
import com.miedzic.shop.domain.dto.ProductDto;
import com.miedzic.shop.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.history.Revision;

@Mapper(componentModel = "spring")
public interface HistoryMapper {
     @Mapping(source = "entity.id",target = "id")
     @Mapping(source = "entity.firstName", target = "firstName")
     @Mapping(source = "entity.lastName", target = "lastName")
     @Mapping(source = "entity.email", target = "email")
     @Mapping(source = "entity.password", target = "password")
     @Mapping(source = "entity.premium", target = "premium")
     @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
     UserDto revisionToUserDto(Revision<Integer, User> revision);
     @Mapping(source = "entity.id", target = "id")
     @Mapping(source = "entity.name", target = "name")
     @Mapping(source = "entity.category", target = "category")
     @Mapping(source = "entity.cost", target = "cost")
     @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
     ProductDto revisionToProductDto(Revision<Integer, Product> revision);

}
