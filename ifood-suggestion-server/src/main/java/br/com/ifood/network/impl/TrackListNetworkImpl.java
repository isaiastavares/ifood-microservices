package br.com.ifood.network.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.ifood.constants.HTTPConstants;
import br.com.ifood.domain.TrackDescription;
import br.com.ifood.domain.TrackList;
import br.com.ifood.domain.spotify.Artist;
import br.com.ifood.domain.spotify.AuthenticationToken;
import br.com.ifood.domain.spotify.Tracks;
import br.com.ifood.enums.TrackGenre;
import br.com.ifood.exception.NetworkException;
import br.com.ifood.network.TrackListNetwork;

@Component
public class TrackListNetworkImpl implements TrackListNetwork {
	
	private static final String AUTHENTICATION_API_URL = "https://accounts.spotify.com/api/token";
	private static final String RECOMMENDATION_API_URL = "https://api.spotify.com/v1/recommendations?seed_genres=%s";
	
	@Value("${spotify.client.id}")
	private String clientID;

	@Value("${spotify.client.secret}")
	private String clientSecret;

	@Autowired
	private RestTemplate restTemplate;

	@Override
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
        String encodedToken = new String(Base64.encodeBase64((clientID + ":" + clientSecret).getBytes()));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(HTTPConstants.GRANT_TYPE, HTTPConstants.CLIENT_CREDENTIALS);

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.AUTHORIZATION, HTTPConstants.BASIC_AUTHORIZATION + encodedToken);
        header.add(HttpHeaders.CONTENT_TYPE, HTTPConstants.XFORM_ENCODE_TYPE);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, header);

        return restTemplate.exchange(AUTHENTICATION_API_URL, HttpMethod.POST, entity, AuthenticationToken.class).getBody()
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
			final String uri = String.format(RECOMMENDATION_API_URL, genre);

			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.add(HttpHeaders.AUTHORIZATION, HTTPConstants.BEARER_AUTHORIZATION + getApiKey());
			HttpEntity<String> entity = new HttpEntity<>(headers);
			
			tracks = restTemplate.exchange(uri, HttpMethod.GET, entity, Tracks.class).getBody();
		} catch (Exception e) {
			throw new NetworkException(e);
		}
		return tracks;
	}

}
