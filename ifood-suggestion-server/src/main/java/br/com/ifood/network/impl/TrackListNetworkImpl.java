package br.com.ifood.network.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ifood.constants.HTTPConstants;
import br.com.ifood.domain.TrackDescription;
import br.com.ifood.domain.TrackList;
import br.com.ifood.domain.spotify.Artist;
import br.com.ifood.domain.spotify.AuthenticationToken;
import br.com.ifood.domain.spotify.Tracks;
import br.com.ifood.enums.TrackGenre;
import br.com.ifood.exception.NetworkException;
import br.com.ifood.network.TrackListNetwork;
import br.com.ifood.properties.SpotifyProperties;

@Component
public class TrackListNetworkImpl implements TrackListNetwork {
	
	@Autowired
	private SpotifyProperties spotifyProperties;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	@Cacheable("tracksForGenre")
	public TrackList suggestTracksForGenre(TrackGenre genre) throws NetworkException {
		Tracks suggestedTracks = suggestTracksForGenre(genre.description());

		List<TrackDescription> descriptions = suggestedTracks.getTracks().stream()
				.map(t -> new TrackDescription(
						t.getArtists().stream().findFirst().orElse(new Artist()).getName(),
						t.getAlbum().getName(),
						t.getName()))
				.collect(Collectors.toList());

		return new TrackList(genre, descriptions);
	}
	
    /**
     * Building the authentication key in order to perform new quests
     * <p>
     * Since the Spotify open API changes its authentication token from time to
     * time, we cannot store this authentication key in a static way.
     *
     * @return a String with the authentication key
     */
    private String getApiKey() {
    	String encodedToken = new String(Base64.encodeBase64((
    			spotifyProperties.getClientID() + ":" + spotifyProperties.getClientSecret()).getBytes()));

    	HttpHeaders headers = new HttpHeaders();
    	headers.add(HttpHeaders.AUTHORIZATION, HTTPConstants.BASIC_AUTHORIZATION + encodedToken);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(HTTPConstants.GRANT_TYPE, HTTPConstants.CLIENT_CREDENTIALS);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(spotifyProperties.getTokenUrl(), HttpMethod.POST, entity, AuthenticationToken.class).getBody()
            .getAccessToken();
    }
    
	/**
	 * Suggest a list of tracks based on a given genre
	 *
	 * @param genre the track's genre
	 * @return a {@link TrackDescription} object with the tracks
	 * @throws NetworkException whenever something went wrong while calling the Spotify API
	 */
	public Tracks suggestTracksForGenre(String genre) throws NetworkException {
		Tracks tracks = null;
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(spotifyProperties.getRecommendationsUrl());
			builder.queryParam("seed_genres", genre);

			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.add(HttpHeaders.AUTHORIZATION, HTTPConstants.BEARER_AUTHORIZATION + getApiKey());
			HttpEntity<String> entity = new HttpEntity<>(headers);
			
			tracks = restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET, entity, Tracks.class).getBody();
		} catch (Exception e) {
			throw new NetworkException(e);
		}
		return tracks;
	}

}
