package br.com.ifood.enums;

public enum TrackGenre {
	
	PARTY("electronic"), 
	POP("pop"), 
	ROCK("rock"), 
	CLASSICAL("classical");

	private String description;

	private TrackGenre(String description) {
		this.description = description;
	}

	public String description() {
		return this.description;
	}
}
