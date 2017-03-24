package com.fdmgroup.routePlanner.logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;

/**
 * Aspect class used to log messages for the the entire Route Planner Application. 
 * It uses spring AOP annotations to define pointcuts and joinpoints.
 * @author binoub.rizk
 *
 */
@Aspect
public class RoutePlannerLogger {

	static Logger log = Logger.getLogger(RoutePlannerLogger.class);
	Properties props = new Properties();

	/**
	 * Default the constructor contain the initialization of the Logger object required 
	 * to log messages. This will run once in once spring is initialized due the bean of the 
	 * this is a singleton. 
	 */
	public RoutePlannerLogger() {
		try {
			props.load((Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("log4j.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);
	}

	/**
	 * This will log the message of an exception after it is thrown in any method in the
	 * Route Planner Application  
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "execution(* *(..))", throwing = "e")
	public void logAllExceptions(JoinPoint joinPoint, RoutePlannerException e) {
		log.warn("Method: " + joinPoint.getSignature().getName()
				+ " threw exception: " + e.getClass().getSimpleName()
				+ " with the message: " + e.getMessage());
	}
}
