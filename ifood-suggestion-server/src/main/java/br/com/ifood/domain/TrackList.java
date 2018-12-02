package br.com.ifood.domain;

import java.util.List;

import br.com.ifood.enums.TrackGenre;

public class TrackList {
	
	private TrackGenre genre;
	private List<TrackDescription> tracks;

	public TrackList(TrackGenre genre, List<TrackDescription> tracks) {
		this.genre = genre;
		this.tracks = tracks;
	}

	public TrackGenre getGenre() {
		return genre;
	}

	public void setGenre(TrackGenre genre) {
		this.genre = genre;
	}

	public List<TrackDescription> getTracks() {
		return tracks;
	}

	public void setTracks(List<TrackDescription> tracks) {
		this.tracks = tracks;
	}
}
