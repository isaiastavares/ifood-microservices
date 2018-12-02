package br.com.ifood.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifood.domain.TrackList;
import br.com.ifood.enums.TrackGenre;
import br.com.ifood.exception.BusinessException;
import br.com.ifood.exception.CityNotFoundException;
import br.com.ifood.exception.NetworkException;
import br.com.ifood.network.TrackListNetwork;
import br.com.ifood.network.WeatherNetwork;
import br.com.ifood.service.SuggestionsTracksService;

@Service
public class SuggestionsTracksServiceImpl implements SuggestionsTracksService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SuggestionsTracksServiceImpl.class);

	@Autowired
	private WeatherNetwork weatherNetwork;

	@Autowired
	private TrackListNetwork trackListNetwork;

	private static final float HIGH_TEMPERATURE = 30.0f;
	private static final float MAXIMUM_COOL_TEMPERATURE = 30.0f;
	private static final float MINIMUM_COOL_TEMPERATURE = 15.0f;
	private static final float MAXIMUM_CHILL_TEMPERATURE = 14.0f;
	private static final float MINIMUM_CHILL_TEMPERATURE = 10.0f;

	@Override
	public TrackList suggestTracksByCityName(String cityName) throws BusinessException, CityNotFoundException {
		TrackList tracks = null;
		try {
			Float temperature = weatherNetwork.getTemperatureFromCity(cityName);

			TrackGenre genre = getGenreBasedOnTemperature(temperature);

			tracks = trackListNetwork.suggestTracksForGenre(genre);
		} catch (NetworkException nex) {
			throw new BusinessException(nex);
		}
		return tracks;
	}

	@Override
	public TrackList suggestTracksByLocation(Double latitude, Double longitude) throws BusinessException {
		TrackList tracks = null;

		try {
			Float temperature = weatherNetwork.getTemperatureFromLocation(latitude, longitude);

			TrackGenre genre = getGenreBasedOnTemperature(temperature);

			tracks = trackListNetwork.suggestTracksForGenre(genre);
		} catch (NetworkException nex) {
			throw new BusinessException(nex);
		}
		return tracks;
	}

	/**
	 * Getting the track genre based on the temperature
	 * <p>
	 * There are four possible genres to be chosen and each one of them are bing to
	 * a given temperature range
	 * <p>
	 * If temperature (Celsius) is above 30 degrees, it returns party music genre
	 * </br>
	 * In case temperature is above 15 and below 30 degrees, it returns pop music
	 * genre </br>
	 * If it's a bit chilly (between 10 and 14 degrees), it returns rock music genre
	 * </br>
	 * Otherwise (below 10), if it's freezing outside, and it returns classical
	 * music genre </br>
	 *
	 * @param temperature the current temperature
	 * @return a {@link TrackGenre} with the track genre
	 */
	protected TrackGenre getGenreBasedOnTemperature(float temperature) {
		TrackGenre genre = null;
		if (temperature > HIGH_TEMPERATURE) {
			genre = TrackGenre.PARTY;
		} else if (temperature >= MINIMUM_COOL_TEMPERATURE && temperature <= MAXIMUM_COOL_TEMPERATURE) {
			genre = TrackGenre.POP;
		} else if (temperature >= MINIMUM_CHILL_TEMPERATURE && temperature <= MAXIMUM_CHILL_TEMPERATURE) {
			genre = TrackGenre.ROCK;
		} else {
			genre = TrackGenre.CLASSICAL;
		}
		
		LOG.info("The temperature is {}º Celsius and the genre is {} ", temperature, genre);
		return genre;
	}

}
