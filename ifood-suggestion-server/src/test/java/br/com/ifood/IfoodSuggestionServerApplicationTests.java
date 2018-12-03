package br.com.ifood;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = {
		"open-weather.api.url=http://api.openweathermap.org/data/2.5/weather",
		"open-weather.api.units=metric",
		"open-weather.api.app-id=867129315bff9e2983effe4f0fabf04c",
		"spotify.api.url.token=https://accounts.spotify.com/api/token",
		"spotify.api.url.recommendations=https://api.spotify.com/v1/recommendations",
		"spotify.api.client.id=f8b486e527c2410c804e56b7f7dbc584",
		"spotify.api.client.secret=d887068aa5954cefb9dea84088908964" })
public class IfoodSuggestionServerApplicationTests {

	@Test
	public void contextLoads() {
		
	}

}
