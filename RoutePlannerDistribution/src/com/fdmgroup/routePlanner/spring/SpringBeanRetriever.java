package com.fdmgroup.routePlanner.spring;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
/**
 * This will allow us to use beans to instantiate objects, 
 * instead of hard coding our code with the keyword new
 * @author Binoub.Rizk, Egzon.Zuta
 *
 */
public class SpringBeanRetriever {
	private static ApplicationContext context;
	
	/**
	 * Our static init method will create an Class path XML
	 * Application Context.
	 * @param beanPath
	 */
	public static void init(String beanPath) {
		context = new ClassPathXmlApplicationContext(beanPath);
	}
	
	/**
	 * This method will go into our xml file and read the beanName parameter
	 * and try to find the same bean in our xml file so that we can instantiate
	 * the object.
	 * @param beanName name of the bean that we are trying to instantiate
	 * @return the object needed
	 * @throws RoutePlannerException 
	 */
	public static Object getBean(String beanName) throws RoutePlannerException {
		try {
			Object bean = context.getBean(beanName);
			return bean;
		} catch (NoSuchBeanDefinitionException e) {
			
		}
		throw new RoutePlannerException("No bean instantiated");
	}
	
	public static void setContext(ApplicationContext context){
		SpringBeanRetriever.context = context;
	}
	
	public static void close() {
		((ConfigurableApplicationContext) context).close();
		context = null;
	}
}
