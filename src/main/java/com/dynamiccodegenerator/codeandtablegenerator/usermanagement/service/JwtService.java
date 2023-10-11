package com.dynamiccodegenerator.codeandtablegenerator.usermanagement.service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {

	public static final String SECRET =
//			"MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMNYz6cd9tQzysrFfDEj/D40mZopiQz0\n"
//			+ "eTbVJwUZ1/kjNZXYUCJKsBnKIZkYdtBh9nDprnP/RkE6wdX4ljXHy9kCAwEAAQ==";
//	"5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
			"13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084095";
	

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}
	
//	 static Cookie generateCookie(String username) {
//	        // Create a new JWT token string, with the username embedded in the payload
////	        Instant now = Instant.now();
////	        String token = JWT.create()
////	            .withIssuedAt(now)
////	            .withExpiresAt(now.plusSeconds(MAX_AGE_SECONDS))
////	            // A "claim" is a single payload value which we can set
////	            .withClaim("username", username)
////	            .sign(JWT_ALGORITHM);
//
//	        // Create a cookie with the value set as the token string
//	        Cookie jwtCookie = new Cookie(COOKIE_NAME, token);
//	        jwtCookie.setMaxAge(MAX_AGE_SECONDS);
//	        return jwtCookie;
//	    }

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 36_00_000)) 	//1000 * 60 * 30
				.signWith(getSignKey(), SignatureAlgorithm.HS512).compact();
	
	}
	static boolean first;
	 static  byte[] keyBytes;
	 static  Key key ;
	private Key getSignKey() {
//		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
//		return ; //hmacShaKeyFor(SignatureAlgorithm.HS512); //keyBytes);
	     byte[] keyBytes= Decoders.BASE64.decode(SECRET);
	        return Keys.hmacShaKeyFor(keyBytes);
		 // Generate a 512-bit key for HS512 algorithm
      
//        
//        if( !first )
//        { first = true; key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512); }//.getEncoded();}
//return key;
        // Convert the keyBytes to a base64 encoded string
//        String base64Key = java.util.Base64.getEncoder().encodeToString(keyBytes);

	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}


}
