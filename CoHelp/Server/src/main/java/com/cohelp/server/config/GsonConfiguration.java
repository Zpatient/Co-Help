package com.cohelp.server.config;

/**
 * @author jianping5
 * @createDate 2022/10/24 0:32
 */

import com.google.gson.*;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author
 * @Date: 2020-12-04 14:42
 * @Description Gson的LocalDateTime和String转化
 */
@Configuration
public class GsonConfiguration {
    //序列化
    final static JsonSerializer<LocalDateTime> jsonSerializerDateTime = (localDateTime, type, jsonSerializationContext)
            -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    final static JsonSerializer<LocalDate> jsonSerializerDate = (localDate, type, jsonSerializationContext)
            -> new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    //反序列化
    final static JsonDeserializer<LocalDateTime> jsonDeserializerDateTime = (jsonElement, type, jsonDeserializationContext)
            -> LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    final static JsonDeserializer<LocalDate> jsonDeserializerDate = (jsonElement, type, jsonDeserializationContext)
            -> LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Bean
    public Gson create() {
        return new GsonBuilder()
                .setPrettyPrinting()
                /* 更改先后顺序没有影响 */
                .registerTypeAdapter(LocalDateTime.class, jsonSerializerDateTime)
                .registerTypeAdapter(LocalDate.class, jsonSerializerDate)
                .registerTypeAdapter(LocalDateTime.class, jsonDeserializerDateTime)
                .registerTypeAdapter(LocalDate.class, jsonDeserializerDate)
                .create();
    }
}


