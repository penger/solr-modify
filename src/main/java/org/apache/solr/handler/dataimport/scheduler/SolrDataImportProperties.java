package org.apache.solr.handler.dataimport.scheduler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.solr.core.SolrResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SolrDataImportProperties {
	private Properties properties;

	//是不是solrCloud模式下运行
	public static final String INCLOUD="inCloud";
	//zk 主机 格式为   host1:2181;host2:3455;host3:7899
	public static final String ZKHOST="zkHost";
	//是不是更新节点
	public static final String ISUPDATER="isUpdater";
	//增量导入是否运行
	public static final String ISDELTAIMPORTRUN="isDeltaImportRun";
	//全量导入是否运行
	public static final String ISFULLIMPORTRUN="isFullImportRun";
	//全量更新的全url
	public static final String FULLIMPORTURL="fullImportUrl";
	
	public static final String SYNC_ENABLED = "syncEnabled";
	public static final String SYNC_CORES = "syncCores";
	public static final String SERVER = "server";
	public static final String PORT = "port";
	public static final String WEBAPP = "webapp";
	public static final String PARAMS = "params";
	public static final String INTERVAL = "interval";

	public static final String REBUILDINDEXPARAMS = "reBuildIndexParams";
	public static final String REBUILDINDEXBEGINTIME = "reBuildIndexBeginTime";
	public static final String REBUILDINDEXINTERVAL = "reBuildIndexInterval";

	private static final Logger logger = LoggerFactory
			.getLogger(SolrDataImportProperties.class);

	public SolrDataImportProperties() {
		 loadProperties(true);
	}

	/**
	 * 读取core instatnce 下面的properties文件!
	 * @param force
	 */
	public void loadProperties(boolean force) {
//		loadProperties();
		try {
			logger.info("load properties from core instances");
			@SuppressWarnings("resource")
			SolrResourceLoader loader = new SolrResourceLoader(null);
			logger.info("Instance dir is :"+loader.getInstanceDir());
			String configDir = loader.getConfigDir();
			configDir = SolrResourceLoader.normalizeDir(configDir);
			
			
			if (force || properties == null) {
				String dataImportPropertiesPath=configDir+"scheduler-dataimport.properties";
				FileInputStream fileInputStream = new FileInputStream(dataImportPropertiesPath);
				properties = new Properties();
				properties.load(fileInputStream);
			}
		} catch (FileNotFoundException fnfe) {
			logger.error(
					"Error locating DataImportScheduler scheduler-dataimport.properties file",
					fnfe);
		} catch (IOException ioe) {
			logger.error(
					"Error reading DataImportScheduler scheduler-dataimport.properties file",
					ioe);
		} catch (Exception e) {
			logger.error("Error loading DataImportScheduler properties", e);
		}
	}
	
	/**
	 * 读取类路径下的properties文件
	 */
	public void loadProperties(){
		logger.info("load properties from class path ");
		ClassLoader classLoader = SolrDataImportProperties.class.getClassLoader();
		InputStream in  = classLoader.getResourceAsStream("scheduler-dataimport.properties");
		properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}