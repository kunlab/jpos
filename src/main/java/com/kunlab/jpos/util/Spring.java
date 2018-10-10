package com.kunlab.jpos.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

public class Spring {
	private static Spring instance = null;
	
	private ApplicationContext context;
	
    private Spring(ApplicationContext context) {
    	this.context = context;
    }
    
    public synchronized static Spring getInstance(ServletContext servletContext) {
        if(instance == null)
        {
        	WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            instance = new Spring(context);
        }
        
        return instance;
    }
    
    public synchronized static Spring getInstance(String... configLocations) {
        if(instance == null)
        {
        	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configLocations);
            instance = new Spring(context);
        }
        
        return instance;
    }
    
    /**
     * get spring bean
     * @param bean
     * @return bean object
     */
    public Object getBean(String bean) {
    	return context.getBean(bean);
    }
}