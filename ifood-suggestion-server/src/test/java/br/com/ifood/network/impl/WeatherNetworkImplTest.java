package br.com.ifood.network.impl;

import org.junit.Test;

import junit.framework.TestCase;

public class WeatherNetworkImplTest extends TestCase {
	
	private WeatherNetworkImpl weatherNetworkImpl;
	
	@Override
	protected void setUp() throws Exception {
		this.weatherNetworkImpl = new WeatherNetworkImpl();
	}
	
	@Test
	public void test_convert_kelvin_to_celsius() throws Exception {
		assertEquals(0f, weatherNetworkImpl.convertKelvinToCelsius(273.15f), 0);
	}

}
