package br.com.ifood.network.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.ifood.domain.openweather.Wheater;
import br.com.ifood.exception.CityNotFoundException;
import br.com.ifood.exception.NetworkException;
import br.com.ifood.network.WeatherNetwork;

@Component
public class WeatherNetworkImpl implements WeatherNetwork {
	
	private static final String INVALID_CITY_NAME = "Invalid city name";
	
	private static final float KELVIN_BASE_TEMPERATURE = 273.15f;
	
	private static final String CITY_TEMPERATURE_API_URL = "http://api.openweathermap.org/data/2.5/weather?APPID=%s&q=%s";
	private static final String COORDINATE_TEMPERATURE_API_URL = "http://api.openweathermap.org/data/2.5/weather?APPID=%s&lat=%f&lon=%f";
	
	@Value("${open-weather.app-id}")
	private String apiKey;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Float getTemperatureFromCity(String cityName) throws NetworkException, CityNotFoundException {
		Float temperature = null;
		try {
			final String uri = String.format(CITY_TEMPERATURE_API_URL, apiKey, cityName);

			ResponseEntity<Wheater> wheater = restTemplate.getForEntity(uri, Wheater.class);

			temperature = convertKelvinToCelsius(wheater.getBody().getMain().getTemp());
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
			final String uri = String.format(COORDINATE_TEMPERATURE_API_URL, apiKey, latitude, longitude);

			ResponseEntity<Wheater> wheater = restTemplate.getForEntity(uri, Wheater.class);

			temperature = convertKelvinToCelsius(wheater.getBody().getMain().getTemp());
		} catch (Exception e) {
			throw new NetworkException(e);
		}
		return temperature;
	}

	/**
	 * Converts the temperature in Kelvin to Celsius
	 *
	 * @param kelvinTemperature the temperature in Kelvin
	 * @return the temperature in Celsius
	 */
	protected float convertKelvinToCelsius(float kelvinTemperature) {
		return kelvinTemperature - KELVIN_BASE_TEMPERATURE;
	}

}
