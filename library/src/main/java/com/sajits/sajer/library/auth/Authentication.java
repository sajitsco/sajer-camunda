package com.sajits.sajer.library.auth;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import com.sajits.sajer.core.auth.AuthInterface;
import com.sajits.sajer.core.auth.AuthProviderInterface;
import com.sajits.sajer.core.auth.User;
import com.sajits.sajer.core.auth.UserServiceInterface;
import com.sajits.sajer.core.auth.Visitor;

import io.jsonwebtoken.*;

public class Authentication implements AuthInterface {
	// For handling preserving state while login
	class TempLoginInfo {
		public String userId;
		public Date date;
	}

	private Map<String, TempLoginInfo> tempLoginInfo = new HashMap<String, TempLoginInfo>();

	public String addTempLoginInfo(String value) {
		UUID uuid = UUID.randomUUID();
		String key = uuid.toString();
		TempLoginInfo tli = new TempLoginInfo();
		tli.userId = value;
		tli.date = new Date();
		tempLoginInfo.put(key, tli);
		return key;
	}

	public String getTempLoginInfo(String key) {
		if (tempLoginInfo.containsKey(key)) {
			String userId = tempLoginInfo.get(key).userId;
			tempLoginInfo.remove(key);
			return userId;
		} else {
			return null;
		}
	}

	public String tokenFromTempKey(String tempk) {
		String userName = getTempLoginInfo(tempk);
		JwtBuilder jb = Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + expDuration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret);
		String token = jb.compact();

		return token;
	}
	// --------------------------------------

	Map<String, AuthProviderInterface> authProviders = new HashMap<String, AuthProviderInterface>();
	// private AuthRepositoryInterface authRepository;

	private static final Logger logger = Logger.getLogger(Authentication.class.getName());
	private String jwtSecret = "someTemporarySecret";
	private Long expDuration = (long) 6000000;
	private UserServiceInterface userService;

	public String getToken(String userName) {
		JwtBuilder jb = Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + expDuration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret);
		String token = jb.compact();

		return token;
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.warning("Invalid JWT signature: " + e.getMessage());
		} catch (MalformedJwtException e) {
			logger.warning("Invalid JWT token: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.warning("JWT token is expired: " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.warning("JWT token is unsupported: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.warning("JWT claims string is empty: " + e.getMessage());
		}
		return false;
	}

	public Authentication(UserServiceInterface visitor) {
		this.userService = visitor;
	}

	public Visitor visit(String id, boolean authenticated, String uuid) {
		Visitor v1 = new Visitor();
		User user = null;
		if (authenticated && userService.isThereATask(id)) {
			user = userService.userFromTask(id);
			v1.setUser(user);
			v1.setId(id);
			v1.setUuid(uuid);
			v1.setLastVisit(new Date());
			v1.setToken(this.getToken(id));
			v1.setExp(new Date((new Date()).getTime() + expDuration));
		} else {
			String validUuid = validateUUID(uuid);
			if (userService.isThereATask(validUuid)) {
				user = userService.userFromTask(validUuid);
			} else {
				user = userService.createTask(validUuid, new User());
			}
			v1.setUser(user);
			v1.setId(validUuid);
			v1.setUuid(validUuid);
			v1.setLastVisit(new Date());
			v1.setToken(this.getToken(validUuid));
			v1.setExp(new Date((new Date()).getTime() + expDuration));
		}
		return v1;
	}

	private String validateUUID(String uuid) {
		try {
			UUID.fromString(uuid);
			return uuid;
		} catch (IllegalArgumentException exception) {
			return UUID.randomUUID().toString();
		}

	}

	public Visitor populateVisitor(String id) {
		Visitor v1 = new Visitor();
		User user = userService.userFromTask(id);
		if (user == null)
			return null;
		v1.setUser(user);
		v1.setId(id);
		return v1;
	}

	@Override
	public String redirect(String code, String state, String type) {
		Visitor v1 = new Visitor();
		User user;
		try {
			user = authProviders.get(type).redirect(code, state);
		} catch (IOException e) {
			logger.warning("Unsuccessful login " + e.getMessage());
			return state + "?userId=";
		}

		String userId = user.getEmail();

		if (!userService.isThereAUser(userId)) {
			userService.registerUser(user);
		}

		if (userService.isThereATask(userId)) {
			user = userService.userFromTask(userId);
		} else {
			user = userService.createTask(userId, user);
		}
		v1.setUser(user);
		v1.setId(userId);
		v1.setUuid("");
		v1.setLastVisit(new Date());
		v1.setToken(this.getToken(userId));
		v1.setExp(new Date((new Date()).getTime() + expDuration));

		state = state.substring(0, state.indexOf("-ciid-"));

		return state + "?userId=" + userId;
	}

	@Override
	public String login(String state, String type) {
		return authProviders.get(type).login(state);
	}

	public void addAuthProvider(String providerName, AuthProviderInterface authProvider) {
		authProviders.put(providerName, authProvider);
	}

	@Override
	public Object chat(String id) {
		return userService.chat(id);
	}

	public Object sendComment(String id, String comment) {
		return userService.sendComment(id, comment);
	}

	public String loginWithToken(User user, String token) {
		Visitor v1 = new Visitor();
		v1.setUser(user);
		try {
			if (authProviders.get("google").validateToken(token) == 200) {

				if (!userService.isThereAUser(user.getEmail())) {
					userService.registerUser(user);
				}

				if (userService.isThereATask(user.getEmail())) {
					user = userService.userFromTask(user.getEmail());
				} else {
					user = userService.createTask(user.getEmail(), user);
				}

				return this.getToken(user.getEmail());

			} else {
				return "token is not valid";
			}
		} catch (IOException e) {
			logger.warning("token is not valid " + e.getMessage());
			return "token is not valid";
		}

	}

	public User updateProfile(String id, User user) {
		return userService.updateProfile(id, user);
	}

	@Override
	public UserServiceInterface getUserService() {
		return this.userService;
	}

}
