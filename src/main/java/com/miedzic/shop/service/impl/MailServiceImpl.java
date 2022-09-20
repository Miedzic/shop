package com.miedzic.shop.service.impl;

import com.miedzic.shop.domain.dao.Template;
import com.miedzic.shop.service.MailService;
import com.miedzic.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final ITemplateEngine iTemplateEngine;
    private final TemplateService templateService;

    @Override
    public void sendEmail(final String email, String templateName, Map<String,Object> variables, String filename, byte[] file) {
        Template templateByName = templateService.getTemplateByName(templateName);
        Context context = new Context(Locale.getDefault(),variables);
        String body = iTemplateEngine.process(templateByName.getBody(), context);

        javaMailSender.send(mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(templateByName.getSubject());
            if(filename != null && file != null){
                ByteArrayResource byteArrayResource = new ByteArrayResource(file);
                mimeMessageHelper.addAttachment(filename,byteArrayResource);
            }

            mimeMessageHelper.setText(body,true);
        });
    }

}
