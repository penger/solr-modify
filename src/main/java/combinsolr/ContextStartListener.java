package combinsolr;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class ContextStartListener
 *
 */
public class ContextStartListener  implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ContextStartListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    	System.setProperty("myname","gongpeng");
    	System.out.println("…Ë÷√myname");
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
	
}
