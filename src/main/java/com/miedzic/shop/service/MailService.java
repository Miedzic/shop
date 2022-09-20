package com.miedzic.shop.service;


import org.springframework.scheduling.annotation.Async;

import java.util.Map;

public interface MailService {

    @Async
    void sendEmail(String email, String templateName, Map<String,Object> variables, String filename, byte[] file);
}

