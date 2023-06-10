package com.miedzic.shop.controller;

import com.miedzic.shop.domain.dto.ProductDto;
import com.miedzic.shop.domain.dto.UserDto;
import com.miedzic.shop.mapper.HistoryMapper;
import com.miedzic.shop.repository.ProductRepository;
import com.miedzic.shop.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor

public class HistoryController {
    private final UserRepository userRepository;
    private final HistoryMapper historyMapper;

    private final ProductRepository productRepository;

    @Operation(security = @SecurityRequirement(name = "bearer token"))
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/{id}")
    public Page<UserDto> getUserHistory(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        return userRepository.findRevisions(id, PageRequest.of(page, size)).map(historyMapper::revisionToUserDto);

    }

    @Operation(security = @SecurityRequirement(name = "bearer token"))
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/product/{id}")
    public Page<ProductDto> getProductHistory(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        return productRepository.findRevisions(id, PageRequest.of(page, size)).map(historyMapper::revisionToProductDto);

    }
}
