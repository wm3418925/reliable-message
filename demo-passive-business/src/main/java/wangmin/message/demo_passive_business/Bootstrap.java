package wangmin.message.demo_passive_business;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.util.concurrent.AbstractIdleService;

@WebListener
public class Bootstrap extends AbstractIdleService implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(Bootstrap.class);
	
	private ClassPathXmlApplicationContext context;

	public static void main(String[] args) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.startAsync();
		try {
			Object lock = new Object();
			synchronized (lock) {
				while (true) {
					lock.wait();
				}
			}
		} catch (InterruptedException ex) {
			logger.error("ignore interruption", ex);
		}
	}
	
	private void loadLog4jConfig() {
		try {
			Properties config = new Properties();
			
			InputStream configFileStream = this.getClass().getClassLoader().getResourceAsStream("log4j.properties");
			
			config.load(configFileStream);
			
			PropertyConfigurator.configure(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void startUp() throws Exception {
		loadLog4jConfig();
		logger.info("===================my-service START BEGIN==========================");
		
		Long startTime = System.nanoTime();
		context = new ClassPathXmlApplicationContext(new String[] { "config/spring/context.xml" });
		context.start();
		context.registerShutdownHook();
		Long interval = (System.nanoTime() - startTime) / 1000000000;
		logger.info("service STARTED UP successfully in "+interval+" seconds...");
		
		logger.info("===================my-service START END==========================");
	}

	@Override
	protected void shutDown() throws Exception {
		context.stop();
		logger.info("service stopped successfully");
	}

	//@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.info("demo-rpc service started ");
		try {
			startUp();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("ignore interruption ");
		}
	}

	//@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		try {
			shutDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
