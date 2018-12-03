package br.com.ifood.properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "singleton")
public class OpenWeatherProperties {

	@Autowired
	private Environment environment;

	private String url;
	private String units;
	private String appId;

	@PostConstruct
	public void init() {
		url = environment.getProperty("open-weather.api.url");
		units = environment.getProperty("open-weather.api.units");
		appId = environment.getProperty("open-weather.api.app-id");
	}

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
