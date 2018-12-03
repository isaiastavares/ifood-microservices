package br.com.ifood.network.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ifood.domain.openweather.OpenWeather;
import br.com.ifood.exception.CityNotFoundException;
import br.com.ifood.exception.NetworkException;
import br.com.ifood.network.WeatherNetwork;
import br.com.ifood.properties.OpenWeatherProperties;

@Component
public class WeatherNetworkImpl implements WeatherNetwork {
	
	private static final String INVALID_CITY_NAME = "Invalid city name";
	
	@Autowired
	private OpenWeatherProperties openWeatherProperties;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Float getTemperatureFromCity(String cityName) throws NetworkException, CityNotFoundException {
		Float temperature = null;
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(openWeatherProperties.getUrl());

			builder.queryParam("q", cityName);
			builder.queryParam("units", openWeatherProperties.getUnits());
			builder.queryParam("APPID", openWeatherProperties.getAppId());
			
			OpenWeather wheater = restTemplate.getForObject(builder.toUriString(), OpenWeather.class);

			temperature = wheater.getMain().getTemp();
		} catch (HttpClientErrorException ex) {
			throw new CityNotFoundException(INVALID_CITY_NAME);
		} catch (Exception e) {
			throw new NetworkException(e);
		}
		return temperature;
	}

	@Override
	public Float getTemperatureFromLocation(Double latitude, Double longitude) throws NetworkException {
		Float temperature = null;
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(openWeatherProperties.getUrl());

			builder.queryParam("lat", latitude);
			builder.queryParam("lon", longitude);
			builder.queryParam("units", openWeatherProperties.getUnits());
			builder.queryParam("APPID", openWeatherProperties.getAppId());

			ResponseEntity<OpenWeather> wheater = restTemplate.getForEntity(builder.toUriString(), OpenWeather.class);

			temperature = wheater.getBody().getMain().getTemp();
		} catch (Exception e) {
			throw new NetworkException(e);
		}
		return temperature;
	}

}
