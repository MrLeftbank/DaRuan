/**
 * @Title LocalDateTimeJacksonConfig.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月15日下午1:29:17
 */
package com.sx.oesb.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/** 
* @ClassName LocalDateTimeJacksonConfig 
* @Description 配置LocalDateTime序列化时用自定义格式，而非类定义的toString ISO-8601 格式（会插入T）。
* @author 皮龙霖
* @date 2022年7月15日 下午1:29:17 
*  
*/

@Configuration
@ConditionalOnClass(JavaTimeModule.class)
public class LocalDateTimeJacksonConfig {
	
	  /**
		 * @Title customizeLocalDateTimeFormat
	     * @author 皮龙霖
	     * @description jackson序列化LocalDate时，用自定义Serializer，format参照全局Date格式。参考：https://blog.csdn.net/qq_45740349/article/details/120805668
	     * @createdate 2022年7月15日 下午1:59:43
	     * @param dateFormat
	     * @return Jackson2ObjectMapperBuilderCustomizer
	     **/
	@Bean
    @ConditionalOnProperty("spring.jackson.date-format")
    Jackson2ObjectMapperBuilderCustomizer customizeLocalDateTimeFormat(@Value("${spring.jackson.date-format}") String dateFormat){
        return jacksonObjectMapperBuilder -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        };
    }

}
