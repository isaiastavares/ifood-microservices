package br.com.ifood.properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "singleton")
public class SpotifyProperties {

	@Autowired
	private Environment environment;

	private String tokenUrl;
	private String recommendationsUrl;
	private String clientID;
	private String clientSecret;

	@PostConstruct
	public void init() {
		tokenUrl = environment.getProperty("spotify.api.url.token");
		recommendationsUrl = environment.getProperty("spotify.api.url.recommendations");
		clientID = environment.getProperty("spotify.api.client.id");
		clientSecret = environment.getProperty("spotify.api.client.secret");
	}

	public String getTokenUrl() {
		return tokenUrl;
	}

	public String getRecommendationsUrl() {
		return recommendationsUrl;
	}

	public String getClientID() {
		return clientID;
	}

	public String getClientSecret() {
		return clientSecret;
	}

}
