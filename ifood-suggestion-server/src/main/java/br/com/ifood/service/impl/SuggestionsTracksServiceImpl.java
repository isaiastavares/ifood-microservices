package br.com.ifood.service.impl;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.ifood.domain.TrackDescription;
import br.com.ifood.domain.TrackList;
import br.com.ifood.domain.TrackList.TrackListBuilder;
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
	private static final float MINIMUM_COOL_TEMPERATURE = 15.0f;
	private static final float MINIMUM_CHILL_TEMPERATURE = 10.0f;

	@HystrixCommand(fallbackMethod = "getTracksBrazil")
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

	@HystrixCommand(fallbackMethod = "getTracksBrazil")
	@Override
	public TrackList suggestTracksByLocation(Double latitude, Double longitude) throws BusinessException {
		TrackList tracks = null;

		try {
			Float temperature = weatherNetwork.getTemperatureFromLocation(getDoubleWith2DecimalPlaces(latitude),
					getDoubleWith2DecimalPlaces(longitude));

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
		} else if (temperature >= MINIMUM_COOL_TEMPERATURE) {
			genre = TrackGenre.POP;
		} else if (temperature >= MINIMUM_CHILL_TEMPERATURE) {
			genre = TrackGenre.ROCK;
		} else {
			genre = TrackGenre.CLASSICAL;
		}

		LOG.info("The temperature is {}º Celsius and the genre is {} ", temperature, genre);
		return genre;
	}

	private Double getDoubleWith2DecimalPlaces(Double value) {
		return Double.valueOf(String.format(Locale.US, "%.2f", value));
	}

	public TrackList getTracksBrazil(String name) {
		LOG.error("Calling fallback method for the arguments: {}", name);
		return getTracksBrazil();
	}

	public TrackList getTracksBrazil(Double lat, Double lon) {
		LOG.error("Calling fallback method for the arguments: {} {}", lat, lon);
		return getTracksBrazil();
	}

	public TrackList getTracksBrazil() {
		TrackDescription track1 = new TrackDescription("Lenine", "Lenine", "Hoje Eu Quero Sair Só");
		TrackDescription track2 = new TrackDescription("Pitty", "Admirável Chip Novo", "Máscara");
		TrackDescription track3 = new TrackDescription("Charlie Brown Jr.", "Bocas Ordinárias,", "Só Por Uma Noite");
		TrackDescription track4 = new TrackDescription("Criolo", "Nó Na Orelha", "Não Existe Amor Em SP");
		TrackDescription track5 = new TrackDescription("Engenheiros Do Hawaii", "A Revolta Dos Dandis", "Infinita Highway");
		TrackDescription track6 = new TrackDescription("Marisa Monte", "O Que Você Quer Saber de Verdade", "Depois");
		TrackDescription track7 = new TrackDescription("Forfun", "Nu", "Alforria");
		TrackDescription track8 = new TrackDescription("Criolo", "Brownswood Bubblers Eight (Gilles Peterson Presents)", "Linha de Frente");
		TrackDescription track9 = new TrackDescription("Zé Ramalho", "Brasil 2014 - Samba, Bossa Nova & MPB", "Chão de Giz");
		TrackDescription track10 = new TrackDescription("Legião Urbana", "Mais Do Mesmo", "Indios");
		TrackDescription track11 = new TrackDescription("Maria Gadú", "Maria Gadú", "Ne Me Quitte Pas");
		TrackDescription track12 = new TrackDescription("Raul Seixas", "As Profecias", "Tente Outra Vez");
		TrackDescription track13 = new TrackDescription("Wanessa", "Amor, Amor", "Amor, Amor");
		TrackDescription track14 = new TrackDescription("Pitty", "Admirável Chip Novo", "Admirável Chip Novo");
		TrackDescription track15 = new TrackDescription("Nando Reis", "Chill Brazil - Sun (Volume 3)", "All Star");
		TrackDescription track16 = new TrackDescription("Vanessa Da Mata", "Essa Boneca Tem Manual (Com Faixa Bônus)", "Ai, Ai, Ai...");
		TrackDescription track17 = new TrackDescription("Lucas Lucco", "O Destino (Ao Vivo)", "11 Vidas");
		TrackDescription track18 = new TrackDescription("Los Hermanos", "20+ Rock", "O Vento");
		TrackDescription track19 = new TrackDescription("Chico Buarque", "The Essential Chico Buarque", "Geni E O Zepelim");
		TrackDescription track20 = new TrackDescription("Little Joy", "Little Joy (US Edition)", "Brand New Start");
		
		return new TrackList(
				new TrackListBuilder(TrackGenre.BRAZIL)
					.tracks(track1, track2, track3, track4, track5, track6, track7, track8, track9,
						track10, track11, track12, track13, track14, track15, track16, track17, 
						track18, track19, track20)
					.build()
		);
	}

}
