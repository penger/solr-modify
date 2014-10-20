package cn.archangel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ABC {

	public static void main(String[] args) {
		
		final String fullURL ="http://localhost:8080/msi";
//		final String fullURL ="http://10.6.159.96:9085/solr/dataimport?command=full-import&clean=true&commit=true";
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
					Thread.sleep(1000);
					int responseCode = conn.getResponseCode();
					System.out.println(responseCode);
					String responseMessage = conn.getResponseMessage();
					System.out.println(responseMessage);
					OutputStream outputStream = conn.getOutputStream();
					System.out.println(outputStream.toString());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (ProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					conn.disconnect();					
				}
			}
		}, 2L, TimeUnit.SECONDS);
//		logger.info("First full import start 3 minutes  after solr start");
		executor.shutdown();

	}

}
