package cn.archangel;

import org.apache.solr.core.DirectoryFactory;
import org.apache.solr.core.NRTCachingDirectoryFactory;

public class StaticFunction {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		kk();
		int mb = NRTCachingDirectoryFactory.DEFAULT_MAX_CACHED_MB;
		System.out.println(mb);
		DirectoryFactory directoryFactory=null;
	}
	
	
	private static void kk(){
		System.out.println("a");
		System.out.println("a");
		System.out.println("a");
		System.out.println("a");
	}

}
