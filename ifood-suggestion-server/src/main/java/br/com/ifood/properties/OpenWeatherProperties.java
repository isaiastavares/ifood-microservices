package br.com.ifood.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenWeatherProperties {

	@Value("${open-weather.api.url}")
	private String url;
	@Value("${open-weather.api.units}")
	private String units;
	@Value("${open-weather.api.app-id}")
	private String appId;

	public String getUrl() {
		return url;
	}

	public String getUnits() {
		return units;
	}

	public String getAppId() {
		return appId;
	}

}
