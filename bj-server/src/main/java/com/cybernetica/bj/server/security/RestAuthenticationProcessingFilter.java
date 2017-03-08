package com.cybernetica.bj.server.security;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cybernetica.bj.common.JsonUtils;
import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.SpringSession;
import com.cybernetica.bj.server.models.User;
import com.cybernetica.bj.server.services.SessionService;
import com.cybernetica.bj.server.services.UserService;

/**
 * Rest authentication servlet filter
 * @author dmitri
 *
 */
public class RestAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationProcessingFilter.class);


	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	public RestAuthenticationProcessingFilter() {
		super("/session/login");
    	//setContinueChainBeforeSuccessfulAuthentication(true);
	}
	

	@Override
	public void afterPropertiesSet() {
		setAuthenticationManager(authenticationManager);
		ProviderManager manager = (ProviderManager) authenticationManager;
		for(AuthenticationProvider prov:manager.getProviders())
			if(prov instanceof DaoAuthenticationProvider){
				DaoAuthenticationProvider authenticationProvider = (DaoAuthenticationProvider) prov;
				authenticationProvider.setHideUserNotFoundExceptions(false);
			}
		

		setAuthenticationSuccessHandler(authenticationSuccessHandler);
		super.afterPropertiesSet();
	}



	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if(needAuthenticate((HttpServletRequest)req,(HttpServletResponse) res)){
			doAutenticate((HttpServletRequest)req,(HttpServletResponse) res,chain);
		}
		super.doFilter(req, res, chain);
	}


	/**
	 * re-auth using token
	 * @param req
	 * @param res
	 * @param chain
	 */
	private void doAutenticate(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
		Authentication authentication=null;
		String authToken = getAuthToken(req, res);
		logger.trace("Authenticating using ",authToken);
		SpringSession session;
		try {
			session = sessionService.findBySession(authToken);
		} catch (ServiceException e) {
			logger.error("session retrieve failed for "+authToken,e);
			throw new AuthenticationServiceException("error.auth.session.read");
		}
		if(session==null){
			logger.debug("SESSION NOT FOUND {}",authToken);
			//throw new BadCredentialsException("error.auth.session.not-exists");
			return;
		}
		
		User user;
		try {
			user = userService.findByUsername(session.getUsername());
		} catch (ServiceException e) {
			logger.error("session retrieve failed for "+session.getUsername(),e);
			throw new AuthenticationServiceException("error.auth.user.read");
		}
		if(user==null){
			logger.debug("USER NOT FOUND {}",authToken);
			throw new AuthenticationServiceException("error.auth.user.not-exists");
		}
		String password=user.getPassword();
		authentication = autenticate(session.getUsername(), password, req);
		logger.debug("Session {} authenticated",authToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	private Authentication autenticate(String username,String password,HttpServletRequest request){
		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		
		try {
			return this.getAuthenticationManager().authenticate(authRequest);
		} catch(UsernameNotFoundException notFoundEx){
			try {
				userService.create(username, password);
			} catch (ServiceException e) {
				logger.error("error.user.create.failed {}",username);
				throw new AuthenticationServiceException(
						"Authentication failed: " + request.getMethod(),e);
			}
			
			
			return this.getAuthenticationManager().authenticate(authRequest);
		}
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}
		
		String body;
		try {
			body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException e) {
			logger.error("error.request.read",e);
			throw new AuthenticationServiceException(
					"Authentication failed: " + request.getMethod(),e);
		}
		
		logger.trace("Authenticating with {}",body);
		LoginRequestDTO data=JsonUtils.fromString(body, LoginRequestDTO.class);

		String username = data.getUsername();
		String password = data.getPassword();

		return autenticate(username, password, request);

	}


	private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
		
	}

	/**
	 * return auth token
	 * @param req
	 * @param res
	 * @return
	 */
	public static String getAuthToken(HttpServletRequest req, HttpServletResponse res){
		String header = req.getHeader("X-Auth-Token");
		if(header==null)
			return null;
		return header;
	}

	/**
	 * check auth token
	 * @param req
	 * @param res
	 * @return
	 */
	protected boolean needAuthenticate(HttpServletRequest req, HttpServletResponse res) {
		if(getAuthToken(req,res)==null)
			return false;
		return true;
	}
	
	
}
