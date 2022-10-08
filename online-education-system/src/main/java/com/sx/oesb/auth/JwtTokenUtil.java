package com.sx.oesb.auth;

import com.sx.oesb.service.AdministratorService;
import com.sx.oesb.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.validity-in-minutes}")
    private long validityInMs;

    private final UserService userService;
    private final AdministratorService administratorService;

    @Autowired
    public JwtTokenUtil(UserService userService, AdministratorService administratorService) {
		super();
		this.userService = userService;
		this.administratorService = administratorService;
	}

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

   

	public String createToken(String username, boolean isAdmin) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("isAdmin", isAdmin);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMs);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
    	UserDetails userDetails = null;
    	
//    	不同的Jwt去不同的表验证
    	if((boolean) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("isAdmin"))
    		userDetails = this.administratorService.searchAdminByAdminName(getUsername(token));
    	else
    		userDetails = this.userService.searchUserByUsername(getUsername(token));
    	
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }

}
