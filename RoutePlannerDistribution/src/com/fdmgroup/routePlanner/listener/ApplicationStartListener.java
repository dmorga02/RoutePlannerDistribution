package com.fdmgroup.routePlanner.listener;

import java.io.IOException;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.fdmgroup.routePlanner.data.LineCreator;
import com.fdmgroup.routePlanner.data.LineDataReader;
import com.fdmgroup.routePlanner.data.LineHolder;
import com.fdmgroup.routePlanner.data.NodeCreator;
import com.fdmgroup.routePlanner.data.NodeHolder;
import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

public class ApplicationStartListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		SpringBeanRetriever.init("beans.xml");

		NodeCreator nodeCreator =  null;
		
		try {
			nodeCreator = new NodeCreator();
			nodeCreator.setReader(new LineDataReader());
		} catch (RoutePlannerException e1) {
			e1.printStackTrace();
		}
		NodeHolder nodeHolder = null;
		try {
			nodeHolder = (NodeHolder) SpringBeanRetriever.getBean("nodeHolder");
			nodeHolder.setNodes(nodeCreator.makeNodes("LondonUnderground.xml"));
		} catch (RoutePlannerException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		LineHolder lineHolder = null;
		
			try {
				lineHolder = (LineHolder) SpringBeanRetriever.getBean("lineHolder");
				LineCreator lineCreator = (LineCreator) SpringBeanRetriever.getBean("lineCreator");
				lineCreator.setNodes(nodeHolder);
				lineHolder.setLines(lineCreator.makeLines("LondonUnderground.xml"));
			} catch (RoutePlannerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		
		
		nodeHolder.sortNodes();
		event.getServletContext().setAttribute("nodeHolder", nodeHolder);
		event.getServletContext().setAttribute("lineHolder", lineHolder);
	}

}