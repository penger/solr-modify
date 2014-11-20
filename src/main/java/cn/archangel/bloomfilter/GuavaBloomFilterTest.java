package cn.archangel.bloomfilter;

import java.math.BigInteger;
import java.nio.charset.Charset;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.PrimitiveSink;

/**
 * http://www.javacodegeeks.com/2012/12/google-guava-bloomfilter.html
 * @author Esc_penger
 *
 */
public class GuavaBloomFilterTest {

	public static void main(String[] args) {
		Funnel<byte[]> funnel = Funnels.byteArrayFunnel();
		
		BloomFilter<byte[]> bloomFilter = BloomFilter.create(funnel, 1000);

		//Create the custom filter
		class BigIntegerFunnel implements Funnel<BigInteger> {
		        @Override
		        public void funnel(BigInteger from, Sink into) {
		            into.putBytes(from.toByteArray());
		        }
		    }

		//Creating the BloomFilter
		BloomFilter bloomFilter = BloomFilter.create(new BigIntegerFunnel(), 1000);

		//Putting elements into the filter
		//A BigInteger representing a key of some sort
		bloomFilter.put(bigInteger);

		//Testing for element in set
		boolean mayBeContained = bloomFilter.mayContain(bitIntegerII);

	}
	


}


