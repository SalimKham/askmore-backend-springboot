package io.khaminfo.askmore.security;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.khaminfo.askmore.domain.Person;
import io.khaminfo.askmore.exceptions.AccessException;

@Component
public class JWTTokenProvider {

	public String generateToken(Authentication authentication) {
		Person user = (Person) authentication.getPrincipal();
		Date now = new Date(System.currentTimeMillis());
		Date expriryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);
		String userId = Long.toString(user.getId());
		Map<String, Object> claimsMap = new HashMap<>();
		claimsMap.put("id", userId);
		claimsMap.put("username", user.getUsername());
		claimsMap.put("type", user.getType());
		claimsMap.put("photo", user.getUserInfo().getPhoto());
		
		switch (user.getUser_state()) {
		case 5:
			throw new AccessException("Please Confirm your Account");

		case 4:
			throw new AccessException("Account Not Confirmed By Admin");

		case 3:
			throw new AccessException("Account Blocked By Admin ");

		default:
			break;
		}


		return Jwts.builder().setSubject(userId).setClaims(claimsMap).setIssuedAt(now).setExpiration(expriryDate)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET).compact();
	}

	public boolean validateToken(String token) {
		try {

			Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			System.out.println("Invalid Sigature JWT");
		} catch (MalformedJwtException ex) {
			System.out.println("Invalid JWT Token");
		} catch (ExpiredJwtException ex) {
			System.out.println("Expired JWT Token");
		} catch (UnsupportedJwtException ex) {
			System.out.println("Unsupported JWT Token");
		} catch (IllegalArgumentException ex) {
			System.out.println("JWT claims string is empty");
		}
		return false;

	}

	public long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
		long id = Long.parseLong((String) claims.get("id"));
		return id;
	}

}
