package br.com.ifood.service.impl;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import br.com.ifood.enums.TrackGenre;
import junit.framework.TestCase;

public class SuggestionsTracksServiceImplTest extends TestCase {
	
	private SuggestionsTracksServiceImpl suggestionsTracksServiceImpl;
	
	@Override
	protected void setUp() throws Exception {
		this.suggestionsTracksServiceImpl = new SuggestionsTracksServiceImpl();
	}
	
	@Test
	public void test_genre_by_temperature() throws Exception {
		assertEquals(TrackGenre.PARTY, suggestionsTracksServiceImpl.getGenreBasedOnTemperature(30.1f));
		assertNotEquals(TrackGenre.PARTY, suggestionsTracksServiceImpl.getGenreBasedOnTemperature(30f));
		
		assertEquals(TrackGenre.POP, suggestionsTracksServiceImpl.getGenreBasedOnTemperature(30f));
		assertNotEquals(TrackGenre.POP, suggestionsTracksServiceImpl.getGenreBasedOnTemperature(14.9f));

		assertEquals(TrackGenre.ROCK, suggestionsTracksServiceImpl.getGenreBasedOnTemperature(14f));
		assertNotEquals(TrackGenre.ROCK, suggestionsTracksServiceImpl.getGenreBasedOnTemperature(9.9f));
		
		assertEquals(TrackGenre.CLASSICAL, suggestionsTracksServiceImpl.getGenreBasedOnTemperature(9.9f));
		assertNotEquals(TrackGenre.CLASSICAL, suggestionsTracksServiceImpl.getGenreBasedOnTemperature(10f));
	}
	
}
