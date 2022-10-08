package cn.felord.rediscache;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The type Cache api.
 *
 * @author dax
 * @since 2019 /9/2 21:45
 */
@RestController
@RequestMapping("/cache")
public class CacheApi {
  @Resource
  private CaptchaCache captchaCache;


    /**
     * Put code string.
     *
     * @return the string
     */
    @GetMapping("/put")
    public String putCode(){

        String code = "12324";
        captchaCache.put("myKey", code);

        return code;

    }


    /**
     * Get code string.
     *
     * @return the string
     */
    @GetMapping("/get")
    public String getCode(){

       return captchaCache.get("myKey");

    }


    /**
     * Expire.
     */
    @GetMapping("/exp")
    public void expire(){
        captchaCache.expire("myKey");
    }

}





