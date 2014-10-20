package org.apache.solr.handler.dataimport.scheduler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
		
		ServletContext servletContext = servletContextEvent.getServletContext();

		// get our timer from the context
		Timer timer = (Timer) servletContext.getAttribute("timer");
		Timer fullImportTimer = (Timer) servletContext
				.getAttribute("fullImportTimer");

		// cancel all active tasks in the timers queue
		if (timer != null)
			timer.cancel();
		if (fullImportTimer != null)
			fullImportTimer.cancel();

		// remove the timer from the context
		servletContext.removeAttribute("timer");
		servletContext.removeAttribute("fullImportTimer");

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		
		SolrDataImportProperties solrDataImportProperties =null;
		try{
			//获取zk的地址和端口设置到环境对象中
			solrDataImportProperties = new SolrDataImportProperties();
			String inCloud = solrDataImportProperties.getProperty(SolrDataImportProperties.INCLOUD);
			if(Integer.parseInt(inCloud)==1){
				//cloud模式下运行
				//1.设置zkHost
				logger.info("solr run in cloud mode");
				String zkHost = solrDataImportProperties.getProperty(SolrDataImportProperties.ZKHOST);
				System.setProperty("zkHost", zkHost);
				logger.info("zkHost is "+zkHost);
				String isUpdater = solrDataImportProperties.getProperty(SolrDataImportProperties.ISUPDATER);
				if(Integer.parseInt(isUpdater)==1){
					//是主动更新节点
					logger.info("i'm updater!");
					String isDeltaImport = solrDataImportProperties.getProperty(SolrDataImportProperties.ISDELTAIMPORTRUN);
					if(Integer.parseInt(isDeltaImport)==1){
						addDeltaImportTask(servletContextEvent);
					}
					String isFullImport = solrDataImportProperties.getProperty(SolrDataImportProperties.ISFULLIMPORTRUN);
					if(Integer.parseInt(isFullImport)==1){
						addFullImportTask(servletContextEvent);
					}
				}else{
					logger.info("i'm follower!");
					//如果是被动更新节点只需要设置zkHost
					return ;
				}
			}else{
				//单机模式运行
				logger.info("run in standalone mode is updater");
				String isDeltaImport = solrDataImportProperties.getProperty(SolrDataImportProperties.ISDELTAIMPORTRUN);
				if(Integer.parseInt(isDeltaImport)==1){
					addDeltaImportTask(servletContextEvent);
				}
				String isFullImport = solrDataImportProperties.getProperty(SolrDataImportProperties.ISFULLIMPORTRUN);
				if(Integer.parseInt(isFullImport)==1){
					addFullImportTask(servletContextEvent);
				}
				//添加操作,任务在程序启动3min钟后自动执行全量导入
				final String fullURL = solrDataImportProperties.getProperty(SolrDataImportProperties.FULLIMPORTURL);
				ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
				executor.schedule(new Runnable() {
					@Override
					public void run() {
						HttpURLConnection conn = null;
						try {
							URL url = new URL(fullURL);
							conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("POST");
							conn.setRequestProperty("type", "submit");
							conn.setDoOutput(true);
							conn.connect();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (ProtocolException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							conn.disconnect();					
						}
					}
				}, 3L, TimeUnit.MINUTES);
				logger.info("First full import start 3 minutes  after solr start");
				executor.shutdown();
			}
			
		}catch(Exception e){
			logger.error("set zkHost to system propertie fail check scheduler-dataimport.properties for detail !");
			logger.error("tips: blank is forbidden set 0 or 1 instead");
		}

	}
	
	
	private void addDeltaImportTask(ServletContextEvent servletContextEvent) throws Exception{
		ServletContext servletContext = servletContextEvent.getServletContext();
		// 增量更新任务计划
					// create the timer and timer task objects
					Timer timer = new Timer();
					DeltaImportHTTPPostScheduler task = new DeltaImportHTTPPostScheduler(
							servletContext.getServletContextName(), timer);
					// get our interval from HTTPPostScheduler
					int interval = task.getIntervalInt();
					// get a calendar to set the start time (first run)
					Calendar calendar = Calendar.getInstance();
					// set the first run to now + interval (to avoid fireing while the
					// app/server is starting)
					calendar.add(Calendar.MINUTE, interval);
					Date startTime = calendar.getTime();
					String startinfo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime);
					logger.info("delta import task run at:"+startinfo+" period is:	"+interval+" minutes !");
					// schedule the task
					timer.scheduleAtFixedRate(task, startTime, 1000 * 60 * interval);
					// save the timer in context
					servletContext.setAttribute("timer", timer);
	}
	
	private void addFullImportTask(ServletContextEvent servletContextEvent) throws Exception{
		ServletContext servletContext = servletContextEvent.getServletContext();
		// 重做索引任务计划
					Timer fullImportTimer = new Timer();
					FullImportHTTPPostScheduler fullImportTask = new FullImportHTTPPostScheduler(
							servletContext.getServletContextName(), fullImportTimer);
					int reBuildIndexInterval = fullImportTask
							.getReBuildIndexIntervalInt();
					if (reBuildIndexInterval <= 0) {
						logger.warn("Full Import Schedule disabled");
						return;
					}
					Calendar fullImportCalendar = Calendar.getInstance();
					Date beginDate = fullImportTask.getReBuildIndexBeginTime();
					if(beginDate.after(new Date())){
						//如果是未来的某一时刻
						fullImportCalendar.setTime(beginDate);
					}else{
						//跳过前N个时间段直到本次时间为未来的某个时间段,防止任务积压!
						Date date = new Date();
						while(fullImportCalendar.getTime().before(date)){
							fullImportCalendar.add(Calendar.MINUTE, reBuildIndexInterval);
						}
					}
					Date fullImportStartTime = fullImportCalendar.getTime();
					String startinfo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fullImportStartTime);
					logger.info("full import task run at:"+startinfo+" period is:	"+reBuildIndexInterval+" minutes !");
					// schedule the task
					fullImportTimer.scheduleAtFixedRate(fullImportTask,
							fullImportStartTime, 1000 * 60 * reBuildIndexInterval);
					// save the timer in context
					servletContext.setAttribute("fullImportTimer", fullImportTimer);
	}

}