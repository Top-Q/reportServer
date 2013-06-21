package il.co.topq.systems.report.contextLoader;

import il.co.topq.systems.report.common.infra.log.ReportLogger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ServletContextListener class that initializes the application context and beanFactory
 */
public class BaseContextListener implements ServletContextListener {

	private Logger logger = ReportLogger.getInstance().getLogger(this.getClass());

	public AbstractApplicationContext beanFactory;

	public BaseContextListener() {
		System.out.println("in c'tor");
	}

	public void contextDestroyed(ServletContextEvent event) {
		logger.info("========================================================");
		logger.info("============= BASE SERVER IS SHUTTING DOWN =============");
		logger.info("========================================================");

		beanFactory.close();

		logger.info("========================================================");
		logger.info("============= BASE SERVER HAS SHUT DOWN ================");
		logger.info("========================================================");

	}

	public void contextInitialized(ServletContextEvent event) {
		logger.info("========================================================");
		logger.info("============= BASE SERVER IS STARTING UP ===============");
		logger.info("========================================================");

		beanFactory = new ClassPathXmlApplicationContext("/applicationContext.xml");

		logger.info("========================================================");
		logger.info("============= BASE SERVER IS UP ========================");
		logger.info("========================================================");
	}

}