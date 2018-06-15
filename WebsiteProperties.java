package com.svk.foxwebsite;

import java.io.IOException;
import java.util.Properties;

public class WebsiteProperties {
	private Properties prop = new Properties();

	public WebsiteProperties(String fileName) {

		try {
			// load a properties file from class path, inside static method
			prop.load(this.getClass().getClassLoader().getResourceAsStream(fileName));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getProperty(String key) {

		return prop.getProperty(key, "");

	}

}
