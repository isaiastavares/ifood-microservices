package br.com.ifood.domain.openweather;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sys {

	@JsonProperty("type")
    private Integer type;
	
	@JsonProperty("id")
    private Integer id;
	
	@JsonProperty("message")
    private Float message;
	
    @JsonProperty("country")
    private String country;
    
    @JsonProperty("sunrise")
    private Integer sunrise;
    
    @JsonProperty("sunset")
    private Integer sunset;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getMessage() {
		return message;
	}

	public void setMessage(Float message) {
		this.message = message;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getSunrise() {
		return sunrise;
	}

	public void setSunrise(Integer sunrise) {
		this.sunrise = sunrise;
	}

	public Integer getSunset() {
		return sunset;
	}

	public void setSunset(Integer sunset) {
		this.sunset = sunset;
	}
    
}