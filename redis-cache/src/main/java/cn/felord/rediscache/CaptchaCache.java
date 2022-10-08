package cn.felord.rediscache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * The type Captcha cache.
 *
 * @author dax
 * @since 2019 /9/2 21:31
 * @see cn.felord.rediscache.config.CacheNameEnum
 */
@Slf4j
@Component
@CacheConfig(cacheNames = {"smsCode"})
public class CaptchaCache {

    /**
     * Put string.
     *
     * @param key  the key
     * @param code the code
     * @return the string
     */
    @CachePut(key = "#key")
    public String put(String key,String code){
      log.info("执行 cachePut");
        return code;
    }

    @CacheEvict(key = "#key")
    public void  expire(String key){

    }


    @Cacheable(key = "#key")
    public String get(String key){
        return null;
    }

}
