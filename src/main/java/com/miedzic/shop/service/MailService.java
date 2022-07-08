package com.miedzic.shop.service;


import org.springframework.scheduling.annotation.Async;

public interface MailService {
    @Async
    void sendEmail(String email);
}
