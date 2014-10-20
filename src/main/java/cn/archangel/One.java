package cn.archangel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.servlet.SolrDispatchFilter;

public class One {
	private static final Log log = LogFactory.getLog(One.class);
public static void main(String[] args) {
	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	ScheduledFuture<?> schedule = executor.schedule(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.err.println("s");
		}
	}, 3, TimeUnit.SECONDS);
//	schedule.
	executor.shutdown();
}
}
