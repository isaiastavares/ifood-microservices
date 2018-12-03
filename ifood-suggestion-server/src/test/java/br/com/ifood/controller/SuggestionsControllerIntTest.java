package br.com.ifood.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.ifood.service.SuggestionsTracksService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = {
		"open-weather.api.url=http://api.openweathermap.org/data/2.5/weather",
		"open-weather.api.units=metric",
		"open-weather.api.app-id=867129315bff9e2983effe4f0fabf04c",
		"spotify.api.url.token=https://accounts.spotify.com/api/token",
		"spotify.api.url.recommendations=https://api.spotify.com/v1/recommendations",
		"spotify.api.client.id=f8b486e527c2410c804e56b7f7dbc584",
		"spotify.api.client.secret=d887068aa5954cefb9dea84088908964" })
@AutoConfigureMockMvc
public class SuggestionsControllerIntTest {

	private static final String LONGITUDE_49_25 = "-49.25";
	private static final String LATITUDE_16_68 = "-16.68";
	private static final String CITY_GOIANIA = "Goiânia";

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private SuggestionsController suggestionsController;

	@Mock
	private SuggestionsTracksService suggestionsTracksService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_suggest_tracks_by_city_success() throws Exception {
		mockMvc.perform(get("/api/suggestions/tracks/city")
					.param("name", CITY_GOIANIA))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$").value(hasKey("genre")))
				.andExpect(jsonPath("$.tracks", hasSize(20)))
				.andExpect(jsonPath("$.tracks[0]").value(hasKey("artist")))
				.andExpect(jsonPath("$.tracks[0]").value(hasKey("album")))
				.andExpect(jsonPath("$.tracks[0]").value(hasKey("trackName")));
	}

	@Test
	public void test_suggest_tracks_by_location_success() throws Exception {
		mockMvc.perform(get("/api/suggestions/tracks/location")
					.param("lat", LATITUDE_16_68)
					.param("lon", LONGITUDE_49_25))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$").value(hasKey("genre")))
				.andExpect(jsonPath("$.tracks", hasSize(20)))
				.andExpect(jsonPath("$.tracks[0]").value(hasKey("artist")))
				.andExpect(jsonPath("$.tracks[0]").value(hasKey("album")))
				.andExpect(jsonPath("$.tracks[0]").value(hasKey("trackName")));
	}
	
	@Test
	public void test_suggest_tracks_by_city_fail_city_missing() throws Exception {
		mockMvc.perform(get("/api/suggestions/tracks/city"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").value(hasKey("timestamp")))
				.andExpect(jsonPath("$.message", is("Required String parameter 'name' is not present")))
				.andExpect(jsonPath("$.details", is("uri=/api/suggestions/tracks/city")));
	}

	@Test
	public void test_suggest_tracks_by_city_fail_city_empty() throws Exception {
		mockMvc.perform(get("/api/suggestions/tracks/city")
					.param("name", ""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").value(hasKey("timestamp")))
				.andExpect(jsonPath("$.message", is("The city (name) could not be null or empty")))
				.andExpect(jsonPath("$.details", is("uri=/api/suggestions/tracks/city")));
	}
	
	@Test
	public void test_suggest_tracks_by_location_fail_latitude_missing() throws Exception {
		mockMvc.perform(get("/api/suggestions/tracks/location")
					.param("lon", LONGITUDE_49_25))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").value(hasKey("timestamp")))
				.andExpect(jsonPath("$.message", is("Required Double parameter 'lat' is not present")))
				.andExpect(jsonPath("$.details", is("uri=/api/suggestions/tracks/location")));
	}
	
	@Test
	public void test_suggest_tracks_by_location_fail_longitude_missing() throws Exception {
		mockMvc.perform(get("/api/suggestions/tracks/location")
					.param("lat", LATITUDE_16_68))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").value(hasKey("timestamp")))
				.andExpect(jsonPath("$.message", is("Required Double parameter 'lon' is not present")))
				.andExpect(jsonPath("$.details", is("uri=/api/suggestions/tracks/location")));
	}
	
	@Test
	public void test_suggest_tracks_by_location_fail_latitude_empty() throws Exception {
		mockMvc.perform(get("/api/suggestions/tracks/location")
					.param("lat", "")
					.param("lon", LONGITUDE_49_25))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").value(hasKey("timestamp")))
				.andExpect(jsonPath("$.message", is("The latitude (lat) could not be null")))
				.andExpect(jsonPath("$.details", is("uri=/api/suggestions/tracks/location")));
	}
	
	@Test
	public void test_suggest_tracks_by_location_fail_longitude_empty() throws Exception {
		mockMvc.perform(get("/api/suggestions/tracks/location")
					.param("lat", LATITUDE_16_68)
					.param("lon", ""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").value(hasKey("timestamp")))
				.andExpect(jsonPath("$.message", is("The longitude (lon) could not be null")))
				.andExpect(jsonPath("$.details", is("uri=/api/suggestions/tracks/location")));
	}
	
	@Test
	public void test_suggest_tracks_by_location_fail_latitude_range_max_invalid() throws Exception {
		mockMvc.perform(get("/api/suggestions/tracks/location")
					.param("lat", "90.1")
					.param("lon", LONGITUDE_49_25))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").value(hasKey("timestamp")))
				.andExpect(jsonPath("$.message", is("The latitude (lat) must be between -90 and 90")))
				.andExpect(jsonPath("$.details", is("uri=/api/suggestions/tracks/location")));
	}
	
	@Test
	public void test_suggest_tracks_by_location_fail_latitude_range_min_invalid() throws Exception {
		mockMvc.perform(get("/api/suggestions/tracks/location")
					.param("lat", "-90.1")
					.param("lon", LONGITUDE_49_25))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").value(hasKey("timestamp")))
				.andExpect(jsonPath("$.message", is("The latitude (lat) must be between -90 and 90")))
				.andExpect(jsonPath("$.details", is("uri=/api/suggestions/tracks/location")));
	}
	
	@Test
	public void test_suggest_tracks_by_location_fail_longitude_range_max_invalid() throws Exception {
		mockMvc.perform(get("/api/suggestions/tracks/location")
					.param("lat", LATITUDE_16_68)
					.param("lon", "180.1"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").value(hasKey("timestamp")))
				.andExpect(jsonPath("$.message", is("The longitude (lon) must be between -180 and 180")))
				.andExpect(jsonPath("$.details", is("uri=/api/suggestions/tracks/location")));
	}
	
	@Test
	public void test_suggest_tracks_by_location_fail_longitude_range_min_invalid() throws Exception {
		mockMvc.perform(get("/api/suggestions/tracks/location")
					.param("lat", LATITUDE_16_68)
					.param("lon", "-180.1"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").value(hasKey("timestamp")))
				.andExpect(jsonPath("$.message", is("The longitude (lon) must be between -180 and 180")))
				.andExpect(jsonPath("$.details", is("uri=/api/suggestions/tracks/location")));
	}

}
