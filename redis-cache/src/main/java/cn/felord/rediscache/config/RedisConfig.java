package cn.felord.rediscache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dax
 * @since 2019/9/2 21:26
 */
@Configuration
@EnableCaching
public class RedisConfig {
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;


    /**
     * Redis template redis template.
     *
     * @return the redis template
     */
    @Bean
    @Primary
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = initJacksonSerializer();
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * Cache manager cache manager.
     *
     * @param redisConnectionFactory the redis connection factory
     * @return the cache manager
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        System.out.println("+++++");

        return RedisCacheManager.RedisCacheManagerBuilder.fromCacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                // 默认策略，未配置的 key 会使用这个
                .cacheDefaults(redisConfig(60))
                // 自定义 key 策略
                .withInitialCacheConfigurations(redisCacheConfigurationMap()).build();
    }


    /**
     * Gets redis cache configuration map.
     *
     * @return the redis cache configuration map
     * @see CacheNameEnum
     */
    private Map<String, RedisCacheConfiguration> redisCacheConfigurationMap() {
        Class<CacheNameEnum> cacheNameEnumClass = CacheNameEnum.class;
        final Enum<?>[] enums = cacheNameEnumClass.getEnumConstants();
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>(100);
        for (Enum<?> e : enums) {
            CacheNameEnum cacheEnumer = (CacheNameEnum) e;
            redisCacheConfigurationMap.put(cacheEnumer.cacheName(), this.redisConfig(cacheEnumer.ttlSecond()));
        }
        return redisCacheConfigurationMap;
    }

    /**
     * Gets redis cache configuration with ttl.
     *
     * @param seconds the seconds
     * @return the redis cache configuration with ttl
     */
    private RedisCacheConfiguration redisConfig(Integer seconds) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = initJacksonSerializer();
        RedisSerializationContext.SerializationPair<Object> objectSerializationPair = RedisSerializationContext
                .SerializationPair
                .fromSerializer(jackson2JsonRedisSerializer);

        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(objectSerializationPair)
                // 缓存ttl
                .entryTtl(Duration.ofSeconds(seconds));
    }


    private Jackson2JsonRedisSerializer<Object> initJacksonSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //bugFix Jackson2反序列化数据处理LocalDateTime类型时出错
        om.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }


}
