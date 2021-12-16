package com.miedzic.shop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import java.lang.reflect.Type;

@Configuration
public class MultipartJacksonConfig extends AbstractJackson2HttpMessageConverter {

    protected MultipartJacksonConfig(final ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }

    @Override
    public boolean canWrite(final Class<?> clazz, final MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(final Type type, final Class<?> clazz, final MediaType mediaType) {
        return false;
    }

    @Override
    protected boolean canWrite(final MediaType mediaType) {
        return false;
    }
}
