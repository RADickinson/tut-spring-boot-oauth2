/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SsoPocController{

  private static final Logger logger = LoggerFactory.getLogger(SsoPocController.class);
  
  @Value("${resource.eclipse.url:eclipse_url_not_set}")
  String eclipseUrl;
  
  @Autowired
  OAuth2ClientContext clientContext;
  
  @Autowired
  OAuth2RestOperations oauthRestOps;
  
	@RequestMapping("/user")
	public Map<String,Object> user(Principal principal) {
	  logger.info("Principal in SSO APP: {}", principal);
	  logger.info("Access token is {}", clientContext.getAccessToken().getValue());
	  logger.info("Additional info {}", clientContext.getAccessToken().getAdditionalInformation());
	  Map<String, Object> userInfo = new HashMap<>();
	  userInfo.put("username", principal.getName());
	  OAuth2Authentication oauth = (OAuth2Authentication) principal;
	  userInfo.put("authorities", oauth.getAuthorities());
	  userInfo.putAll(clientContext.getAccessToken().getAdditionalInformation());
	  userInfo.put("expiration", clientContext.getAccessToken().getExpiration());
	  userInfo.put("scopes", clientContext.getAccessToken().getScope());
	  userInfo.put("token", clientContext.getAccessToken().getValue());
	  OAuth2RefreshToken refreshToken = clientContext.getAccessToken().getRefreshToken();
	  String refreshTokenValue = refreshToken != null ? refreshToken.getValue() : "NO_REFRESH_TOKEN";
	  userInfo.put("refreshToken", refreshTokenValue);
	  Jwt jwt =JwtHelper.decode(clientContext.getAccessToken().getValue());
	  logger.info("Access token as Jwt: {}",jwt);
	  oauthRestOps.getAccessToken();
		return userInfo; 
	}

	@RequestMapping(path="/refresh", method=RequestMethod.PUT)
	public void refresh() {
	  oauthRestOps.getAccessToken();
	}
	
  @RequestMapping(path="/revoke", method=RequestMethod.DELETE)
	public void revoke() {
//    OAuth2ProtectedResourceDetails details = oauthRestOps.getResource();
//    logger.info("Resource details: {}",details);
//    MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
//    // This feels a bit hacky but lets see hey...
//    String creds = details.getClientId() + ":" + details.getClientSecret();
//    byte[] encodedCreds = Base64.getEncoder().encode(creds.getBytes());
//    headers.add("Authorization", "Basic " + new String(encodedCreds));
//    HttpEntity<?> request = new HttpEntity<>(headers);
//  oauthRestOps.exchange("http://localhost:8080/eclipse/oauth/token/{token}", HttpMethod.DELETE, request, Object.class,clientContext.getAccessToken().getValue());
    
    String refreshToken = clientContext.getAccessToken().getRefreshToken() != null ? clientContext.getAccessToken().getRefreshToken().getValue() : null;
    if (refreshToken != null) {
      oauthRestOps.delete("http://localhost:8080/eclipse/rest/oauth/refreshToken/{token}",refreshToken);
    }
	}
  
  @RequestMapping(path="/person/{searchTerm}", method=RequestMethod.GET)
  @ResponseBody
  public List<Person> personData(@PathVariable(name="searchTerm") String searchTerm) {

    logger.info("Eclipse URL is {}", eclipseUrl);
    logger.info("Search term is {}", searchTerm);
    Map<String, String> params = new HashMap<>();
    params.put("nameOrId", searchTerm);
    JSONObject personsFromEclipse = oauthRestOps.getForEntity(eclipseUrl+"/rest/person?appendWildcard=true&nameOrId={nameOrId}&pageSize=20", JSONObject.class, params).getBody();
    logger.info("Persons from Eclipse: {}", personsFromEclipse);
 
    List<Person> eclipsePersons = new ArrayList<>();

    List<?> resultsArray = (List<?>) personsFromEclipse.get("results");
    logger.info("Persons results array: {}", resultsArray);
    resultsArray.forEach(e -> {
      @SuppressWarnings("unchecked")
      Map<String,?> map = (Map<String,?>)e;
      String identifier = (String)map.get("personIdentifier");
      String name = (String)map.get("name");
      Object ageObj = map.get("age");
      int age = 0;
      if (ageObj != null) {
        age = ((Integer)ageObj).intValue();
      }
      Person eclipsePerson = new Person(identifier, name, age);
      eclipsePersons.add(eclipsePerson);
    });

    return eclipsePersons;
  }
  
  @RequestMapping(path="/permissions", method=RequestMethod.GET)
  @ResponseBody
  public List<Permission> permissions() {
    List<?> permissionsFromEclipse = oauthRestOps.getForEntity(eclipseUrl+"/rest/oauth/aggregatedPermission", List.class).getBody();
    logger.info("Response class: {}, Permissions from Eclipse: {}",permissionsFromEclipse.getClass().getSimpleName(), permissionsFromEclipse);
 
    List<Permission> eclipsePermissions = new ArrayList<>();

    permissionsFromEclipse.forEach(p -> {
      @SuppressWarnings("unchecked")
      Map<String, ?> map = (Map<String, ?>)p;
      String domain = (String)map.get("domain");
      List<?> permissions = (List<?>)map.get("permissions");
      List<String> perms = permissions.stream()
          .map(perm -> (String)perm)
          .collect(Collectors.toList());
      Permission permission = new Permission(domain, perms);
      eclipsePermissions.add(permission);
    });
    eclipsePermissions.sort(Comparator.comparing(Permission::getDomain));
    return eclipsePermissions;
  }
  
}
