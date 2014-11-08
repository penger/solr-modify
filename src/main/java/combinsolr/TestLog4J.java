package combinsolr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;


public class TestLog4J {
	private static Logger logger=LoggerFactory.getLogger(TestLog4J.class);

	public static void main(String[] args) {
//		StaticLoggerBinder staticLoggerBinder = new StaticLoggerBinder();
//		Log4jLoggerAdapter log4jLoggerAdapter = new Log4jLoggerAdapter();
		logger.info("xx");
		logger.trace("test file");
		logger.debug("debug message");
	}

}
