package com.example.DemoAssessment.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class LoggingFilter extends OncePerRequestFilter{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);
		
		long startTime = System.currentTimeMillis();
		
		filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);
		
		long timeTaken = System.currentTimeMillis() - startTime;
		String requestBody = getStringValue(contentCachingRequestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
		String responseBody = getStringValue(contentCachingResponseWrapper.getContentAsByteArray(), response.getCharacterEncoding());
		
		if(requestBody != null && requestBody.length() > 0) {
			LOGGER.info("REQUEST LOGS : METHOD = {}; URI = {}; REQUEST BODY = {}; RESPONSE CODE = {}; TIME TAKEN = {}", 
					request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), timeTaken);		
		}
	
		if(responseBody != null && responseBody.length() > 0) {
			LOGGER.info("RESPONSE LOGS : METHOD = {}; URI = {}; RESPONSE BODY = {}; RESPONSE CODE = {}; TIME TAKEN = {}", 
					request.getMethod(), request.getRequestURI(), responseBody, response.getStatus(), timeTaken);
		}
		
		
		contentCachingResponseWrapper.copyBodyToResponse();		
	}
	
	private String getStringValue(byte[] contentAsByteArray, String charEncoding) {
		try {
			return new String(contentAsByteArray, 0, contentAsByteArray.length, charEncoding);
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
}
