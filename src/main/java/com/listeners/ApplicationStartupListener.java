/**
 * 
 */
package com.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 */
@WebListener
public class ApplicationStartupListener implements ServletContextListener {

	protected static final Log log = LogFactory.getLog(ApplicationStartupListener.class);
	@Value("deploy.edition")
	private String edition;
	@Value("cache.expired.time.organ")
	private String oscacheExpiredTime;
	@Value("socket.io.port")
	private String socketIoPort;
	/**
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		if (log.isDebugEnabled()) {
			log.debug("Servlet context destroyed");
		}
	}

	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		if (log.isDebugEnabled()) {
			log.debug("Servlet context initialized");
		}
		ServletContext context = event.getServletContext();
		initEdition(context);
	}

	/**
	 * 
	 * @param context
	 */
	private void initEdition(ServletContext context) {
		/*context.setAttribute("edition", edition);
		context.setAttribute("oscacheExpiredTime", oscacheExpiredTime);
		context.setAttribute("serverip", LocalIpUtils.INTERNET_IP);
		context.setAttribute("socketioPort", socketIoPort);*/
	}
}
