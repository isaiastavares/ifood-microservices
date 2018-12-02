package br.com.ifood.domain.spotify;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Track {

    @JsonProperty("album")
    private Album album;
    
    @JsonProperty("artists")
    private List<Artist> artists = null;
    
    @JsonProperty("available_markets")
    private List<String> availableMarkets = null;
    
    @JsonProperty("disc_number")
    private Integer discNumber;
    
    @JsonProperty("duration_ms")
    private Integer durationMs;
    
    @JsonProperty("explicit")
    private Boolean explicit;
    
    @JsonProperty("external_ids")
    private ExternalIds externalIds;
    
    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;
    
    @JsonProperty("href")
    private String href;
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("popularity")
    private Integer popularity;
    
    @JsonProperty("preview_url")
    private String previewUrl;
    
    @JsonProperty("track_number")
    private Integer trackNumber;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("uri")
    private String uri;

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public List<String> getAvailableMarkets() {
		return availableMarkets;
	}

	public void setAvailableMarkets(List<String> availableMarkets) {
		this.availableMarkets = availableMarkets;
	}

	public Integer getDiscNumber() {
		return discNumber;
	}

	public void setDiscNumber(Integer discNumber) {
		this.discNumber = discNumber;
	}

	public Integer getDurationMs() {
		return durationMs;
	}

	public void setDurationMs(Integer durationMs) {
		this.durationMs = durationMs;
	}

	public Boolean getExplicit() {
		return explicit;
	}

	public void setExplicit(Boolean explicit) {
		this.explicit = explicit;
	}

	public ExternalIds getExternalIds() {
		return externalIds;
	}

	public void setExternalIds(ExternalIds externalIds) {
		this.externalIds = externalIds;
	}

	public ExternalUrls getExternalUrls() {
		return externalUrls;
	}

	public void setExternalUrls(ExternalUrls externalUrls) {
		this.externalUrls = externalUrls;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPopularity() {
		return popularity;
	}

	public void setPopularity(Integer popularity) {
		this.popularity = popularity;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public Integer getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(Integer trackNumber) {
		this.trackNumber = trackNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}