package com.fdmgroup.routePlanner.data;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

public class TestLineDataReader {
	private LineDataReader reader;
	private String filename = "LondonUnderground.xml";
	
	@BeforeClass
	public static void initialize() {
		SpringBeanRetriever.init("beans.xml");
	}
	@AfterClass
	public static void cleanup() {
		SpringBeanRetriever.close();
	}
	
	@Before
	public void init() throws RoutePlannerException {
		reader = (LineDataReader) SpringBeanRetriever.getBean("lineDataReader");
	}
	
	@SuppressWarnings("unused")
	@Test(expected=IOException.class)
	public void testInputNotExists() throws RoutePlannerException, IOException {
		Map<String, List<String>> stationmap = null;
		stationmap = reader.getNetworkData(new File("Idontexist"));	
	}
	@SuppressWarnings("unused")
	@Test(expected=RoutePlannerException.class)
	public void testInvalidInput() throws RoutePlannerException, IOException {
		Map<String, List<String>> stationmap = null;
		stationmap = reader.getNetworkData(new File("pom.xml"));	
	}
	
	@Test
	public void testMap() {
		Map<String, List<String>> stationmap = null;
		try {
			stationmap = reader.getNetworkData(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(filename));
		} catch (RoutePlannerException e) {
			fail();
		} catch (IOException e) {
			fail();
		}
		Object[] keys = stationmap.keySet().toArray();
		assertTrue(keys[0].toString().equals("District (Ealing-Upminster)"));
		
		
		List<String> nwstations = stationmap.get(keys[0]);

		
		assertTrue(nwstations.get(0).equals("Ealing Broadway"));
		assertTrue(nwstations.get(1).equals("Ealing Common"));
		assertTrue(nwstations.get(2).equals("Acton Town"));

		
	}
}
