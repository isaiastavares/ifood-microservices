package br.com.ifood.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifood.enums.TrackGenre;

public class TrackList {

	private TrackGenre genre;
	private List<TrackDescription> tracks;

	public TrackList(TrackListBuilder builder) {
		this.genre = builder.genre;
		this.tracks = builder.tracks;
	}

	public TrackList(TrackGenre genre, List<TrackDescription> tracks) {
		this.genre = genre;
		this.tracks = tracks;
	}

	public static class TrackListBuilder {

		private final TrackGenre genre;
		private List<TrackDescription> tracks;

		public TrackListBuilder(TrackGenre genre) {
			this.genre = genre;
			tracks = new ArrayList<>();
		}

		public TrackListBuilder tracks(TrackDescription... tracks) {
			for (TrackDescription track : tracks) {
				this.tracks.add(track);
			}
			return this;
		}

		public TrackListBuilder build() {
			return this;
		}
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TrackList [name=").append(genre.name()).append(", tracks=").append(getTracksString()).append("]");
		return sb.toString();
	}

	@JsonIgnore
	public String getTracksString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TrackDescription [");
		for (TrackDescription track : tracks) {
			sb.append("{artist=").append(track.getArtist()).append(", album=").append(track.getAlbum())
					.append(", trackName=").append(track.getTrackName()).append("}, ");
		}
		sb.append("]");
		return sb.toString();
	}
}
