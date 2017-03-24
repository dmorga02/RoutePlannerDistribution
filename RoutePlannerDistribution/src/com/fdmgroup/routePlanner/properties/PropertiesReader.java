package com.fdmgroup.routePlanner.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	private Properties props;
	/**
	 * This method will read in the appropriate properties file and return it.
	 * @param input
	 * The string name of the target file
	 * @return
	 * The properties received from the properties file. 
	 */
	public Properties getProps(String input) {
		InputStream fstream = null;
		try {
			props = new Properties();
			fstream = Thread.currentThread().getContextClassLoader().getResourceAsStream(input);
			props.load(fstream);
			
			fstream.close();
		} catch (IOException e) {
			props = null;
		} 
		
		return props;
	}
}
