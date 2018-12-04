package br.com.ifood.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifood.domain.TrackList;
import br.com.ifood.exception.BusinessException;
import br.com.ifood.exception.CityNotFoundException;
import br.com.ifood.service.SuggestionsTracksService;
import br.com.ifood.validation.InRange;
import io.swagger.annotations.ApiOperation;

@RestController
@Validated
@RequestMapping("/api/suggestions/tracks")
public class SuggestionsController {

	@Autowired
	private SuggestionsTracksService suggestionsTracksService;

	/**
	 * (200) In case everything was OK, it returns a {@link TrackList} object with
	 * the tracks </br>
	 * (400) In case anything was wrong with the request </br>
	 * (404) In case the location was not found </br>
	 * (500) In case of an Internal Server Error
	 *
	 * @param name the name of the city to get the tracks based on the city
	 * @throws CityNotFoundException
	 * @throws BusinessException
	 * @returns a {@link ResponseEntity} object wrapping the response result
	 */
	@ApiOperation(value = "Suggest a list of tracks based on a city name")
	@GetMapping(path = "/city", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<TrackList> getTracksBasedByCityName(
			@Valid @NotBlank(message = "The city (name) could not be null or empty") @RequestParam String name)
			throws BusinessException, CityNotFoundException {

		TrackList tracks = suggestionsTracksService.suggestTracksByCityName(name);

		return new ResponseEntity<>(tracks, HttpStatus.OK);
	}

	/**
	 * (200) In case everything was OK, it returns a {@link TrackList} object with
	 * the tracks </br>
	 * (400) In case anything was wrong with the request </br>
	 * (404) In case the location was not found </br>
	 * (500) In case of an Internal Server Error
	 * 
	 * @param lat the latitude of the city
	 * @param lon the longitude of the city
	 * @throws BusinessException
	 * @returns a {@link ResponseEntity} object wrapping the response result
	 */
	@ApiOperation(value = "Suggest a list of tracks based on a lat/long coordinate")
	@GetMapping(path = "/location", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<TrackList> getTracksBasedByCoordinates(
			@Valid @NotNull(message = "The latitude (lat) could not be null") @InRange(min = -90, max = 90, message = "The latitude (lat) must be between -90 and 90") @RequestParam Double lat,
			@Valid @NotNull(message = "The longitude (lon) could not be null") @InRange(min = -180, max = 180, message = "The longitude (lon) must be between -180 and 180") @RequestParam Double lon)
			throws BusinessException {

		TrackList tracks = suggestionsTracksService.suggestTracksByLocation(lat, lon);

		return new ResponseEntity<>(tracks, HttpStatus.OK);
	}

}
