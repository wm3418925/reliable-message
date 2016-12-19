package wangmin.message.test_service;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wangmin.message.core.entity.MyUserEntity;
import wangmin.message.core.remote.MyRemoteServiceInterface;

@WebListener
public class Test {
	private static final Logger logger = Logger.getLogger(Test.class);
	
	private ClassPathXmlApplicationContext context;

	
	public void test() {
		MyRemoteServiceInterface service = context.getBean(MyRemoteServiceInterface.class);
		MyUserEntity result = service.getUserById("1");
		System.out.println("result = " + result);
	}
	
	public static void main(String[] args) throws Exception {
		Test test = new Test();
		
		test.startUp();
		test.test();
		test.shutDown();
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

	protected void startUp() throws Exception {
		loadLog4jConfig();
		logger.info("===================test START BEGIN==========================");
		
		Long startTime = System.nanoTime();
		context = new ClassPathXmlApplicationContext(new String[] { "config/spring/context.xml" });
		context.start();
		context.registerShutdownHook();
		Long interval = (System.nanoTime() - startTime) / 1000000000;
		logger.info("service STARTED UP successfully in "+interval+" seconds...");
		
		logger.info("===================test START END==========================");
	}

	protected void shutDown() throws Exception {
		context.stop();
		logger.info("test stopped successfully");
	}
}
