package br.com.ifood.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpotifyProperties {

	@Value("${spotify.api.url.token}")
	private String tokenUrl;
	@Value("${spotify.api.url.recommendations}")
	private String recommendationsUrl;
	@Value("${spotify.api.client.id}")
	private String clientID;
	@Value("${spotify.api.client.secret}")
	private String clientSecret;

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
