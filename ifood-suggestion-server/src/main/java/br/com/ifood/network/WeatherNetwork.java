package br.com.ifood.network;

import br.com.ifood.exception.CityNotFoundException;
import br.com.ifood.exception.NetworkException;

public interface WeatherNetwork {

	/**
	 * Getting the temperature in Celsius from a given city by its name
	 *
	 * @param cityName the city name
	 * @return the current city's temperature in Celsius
	 * @throws NetworkException      whenever a communication error occurs
	 * @throws CityNotFoundException whenever the city could not be found
	 */
	Float getTemperatureFromCity(String cityName) throws NetworkException, CityNotFoundException;

	/**
	 * Getting the temperature in Celsius from a given city by location
	 *
	 * @param latitude  the cooridante's latitude
	 * @param longitude the cooridante's longitude
	 * @return the current coodinate's temperature in Celsius
	 * @throws NetworkException whenever a communication error occurs
	 */
	Float getTemperatureFromLocation(Double latitude, Double longitude) throws NetworkException;

}
