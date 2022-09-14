package com.spring.tutorial.service;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;

import reactor.core.publisher.Mono;

@Service
public class UserService {

	@Autowired
	private WebClient client;
	
	UriSpec<RequestBodySpec> uriSpec = client.post();
	
	RequestBodySpec bodySpec = uriSpec.uri("/resource");

	RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue("data");
	
	ResponseSpec responseSpec = headersSpec.header(
		    HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		  .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
		  .acceptCharset(StandardCharsets.UTF_8)
		  .ifNoneMatch("*")
		  .ifModifiedSince(ZonedDateTime.now())
		  .retrieve();
	
	Mono<String> response = headersSpec.exchangeToMono(response -> {
		  if (response.statusCode().equals(HttpStatus.OK)) {
		      return response.bodyToMono(String.class);
		  } else if (response.statusCode().is4xxClientError()) {
		      return Mono.just("Error response");
		  } else {
		      return response.createException()
		        .flatMap(Mono::error);
		  }
		});
}
