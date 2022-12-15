package com.tcompany.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcompany.ApplicationContextLoad;
import com.tcompany.model.Users;
import com.tcompany.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAuthService {
	private static final long EXPIRATION_TIME = 172800000;

	private static final String SECRET = "SenhaExtremamenteSecreta";

	private static final String TOKEN_PREFIX = "Bearer";

	/**/
	private static final String HEADER_STRING = "Authorization";

	public void addAuthentication(HttpServletResponse response, String username) throws IOException {
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

		String token = TOKEN_PREFIX + " " + JWT;// Bearer 242343tettfefefef23r232

		response.addHeader(HEADER_STRING, token); // Authorization: Bearer 242343tettfefefef23r232

		ApplicationContextLoad.getApplicationContext().getBean(UserRepository.class).updateTokenUser(JWT,
				username);


		liberacaoCors(response);
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");

	}
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader(HEADER_STRING);
		try {
			if (token != null) {
				String tokenLimpo = token.replaceFirst(TOKEN_PREFIX, "").trim();
				String user = Jwts.parser().setSigningKey(SECRET)
						.parseClaimsJws(tokenLimpo)
						.getBody().getSubject();
				if (user != null) {
					Users users = ApplicationContextLoad.getApplicationContext().getBean(UserRepository.class).findUserByEmail(user);
					if (users != null) {
						if (tokenLimpo.equalsIgnoreCase(users.getToken())) {

							return new UsernamePasswordAuthenticationToken(users.getEmail(), users.getPassword(),
									users.getAuthorities());
						}
					}

				}
			}
		} catch (ExpiredJwtException e) {
			try {
				response.getOutputStream()
						.println("Invalid token");
			} catch (IOException e1) {
				// e1.printStackTrace();
			}
		}
		liberacaoCors(response);
		return null;
	}

	private void liberacaoCors(HttpServletResponse response) {

		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}
		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}
		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}
}
