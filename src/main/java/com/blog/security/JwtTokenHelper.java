package com.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.blog.utils.SecretKeyEncoder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;

@Component
public class JwtTokenHelper {
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 *60;

	@Value("${secret.key}")
	private String secret;
	@Autowired
	private SecretKeyEncoder secretKeyEncoder;

	//	retrive username from token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token,Claims::getSubject);
	}
	
	// retrive expiration date from token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token,Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token , Function<Claims,T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	public Claims getAllClaimsFromToken(String token) {
//		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
//		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		JwtParser parser = Jwts.parser()
				.setSigningKey(secretKeyEncoder.getSigningKey())
				.build();

		return parser.parseClaimsJws(token).getBody();
	}
	
	// Check if token expired
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	// generate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String , Object > claims = new HashMap<>();
		return doGenerateToken(claims,userDetails.getUsername());
	}
	
	private String doGenerateToken(Map<String,Object> claims,String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100))
				.signWith(SignatureAlgorithm.HS512, secretKeyEncoder.getSigningKey()).compact();
	}
	
	public Boolean validateToken(String token , UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private SecretKey generateSecreteKey() {
		SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		System.out.println(secretKey);
		return secretKey;
	}
}
