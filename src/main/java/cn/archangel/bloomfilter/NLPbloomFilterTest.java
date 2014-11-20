package cn.archangel.bloomfilter;

import org.nlpcn.commons.lang.bloomFilter.BloomFilter;

public class NLPbloomFilterTest {

	public static void main(String[] args) throws Exception {
		BloomFilter bf = new BloomFilter(5);
//		bf.a
		bf.add("a");
		bf.add("a");
		bf.add("b");
		System.out.println(bf.containsAndAdd("a"));
		System.out.println(bf.containsAndAdd("c"));
	}

}
