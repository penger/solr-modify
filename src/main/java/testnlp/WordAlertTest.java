package testnlp;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.nlpcn.commons.lang.bloomFilter.BloomFilter;
import org.nlpcn.commons.lang.finger.FingerprintService;
import org.nlpcn.commons.lang.index.MemoryIndex;
import org.nlpcn.commons.lang.pinyin.Pinyin;
import org.nlpcn.commons.lang.pinyin.PinyinWord;
import org.nlpcn.commons.lang.standardization.WordUtil;
import org.nlpcn.commons.lang.tire.SmartGetWord;
import org.nlpcn.commons.lang.tire.domain.SmartForest;
import org.nlpcn.commons.lang.util.StringUtil;
import org.nlpcn.commons.lang.util.WordAlert;

/**
 * nlp 的测试
 * https://github.com/NLPchina/nlp-lang
 * @author Administrator
 *
 */

public class WordAlertTest {

	//隔离乱码
	   @Test
	    public void test() {
	        String str = "ａｚ ＡＺ AZ az ０９•" ;
	        char[] result = WordAlert.alertStr(str) ;
	        System.out.println(new String(result));//az az az az 09·


	        WordUtil wordUtil = new WordUtil('1', 'A');
	        System.out.println(wordUtil.str2Elements("123中国CHINA456你好!"));
	        System.out.println(Arrays.toString(wordUtil.str2Chars("123中国CHINA456你好!")));
	        System.out.println(wordUtil.str2Str("123中国CHINA456你好!"));
	    }
	   String str = "码完代码，他起身关上电脑，用滚烫的开水为自己泡制一碗腾着热气的老坛酸菜面。中国的程序员更偏爱拉上窗帘，在黑暗中享受这独特的美食。这是现代工业给一天辛苦劳作的人最好的馈赠。南方一带生长的程序员虽然在京城多年，但仍口味清淡，他们往往不加料包，由脸颊自然淌下的热泪补充恰当的盐分。他们相信，用这种方式，能够抹平思考着现在是不是过去想要的未来而带来的大部分忧伤…小李的父亲在年轻的时候也是从爷爷手里接收了祖传的代码，不过令人惊讶的是，到了小李这一代，很多东西都遗失了，但是程序员苦逼的味道保存的是如此的完整。 就在24小时之前，最新的需求从PM处传来，为了得到这份自然的馈赠，码农们开机、写码、调试、重构，四季轮回的等待换来这难得的丰收时刻。码农知道，需求的保鲜期只有短短的两天，码农们要以最快的速度对代码进行精致的加工，任何一个需求都可能在24小时之后失去原本的活力，变成一文不值的垃圾创意。";

	    // String str = "點下面繁體字按鈕進行在線轉換" ;

	    @Test
	    public void testStr2Pinyin() {
	        List<PinyinWord> parseStr = Pinyin.str2Pinyin(str);
	        System.out.println(parseStr);
	        Assert.assertEquals(parseStr.size(), str.length());
	    }
	    
	    //特征显示
	    @Test
	    public void hh(){
	    	String content = "卓尔防线继续伤筋动骨 队长梅方出场再补漏说起来卓尔队长梅方本赛季就是个“补漏”的命！在中卫与右边后卫间不停地轮换。如果不出意外，今天与广州恒大一战梅方又要换位置，这也是汉军队长连续三场比赛中的第三次换位。而从梅方的身上也可以看出，本赛季汉军防线如此“折腾”，丢球多也不奇怪了。梅方自2009赛季中乙出道便一直司职中后卫，还曾入选过布拉泽维奇国奥队，也是司职的中卫。上赛季，梅方与忻峰搭档双中卫帮助武汉卓尔队中超成功，但谁知进入本赛季后从第一场比赛开始梅方便不断因为种种“意外”而居无定所。联赛首战江苏舜天时，也是由于登贝莱受伤，朱挺位置前移，梅方临危受命客串右边后卫。第二轮主场与北京国安之战梅方仅仅打了一场中卫，又因为柯钊受罚停赛4轮而不得不再次到边路“补漏”。随着马丁诺维奇被弃用，梅方一度成为中卫首选，在与上海东亚队比赛中，邱添一停赛，梅方与忻峰再度携手，紧接着与申鑫队比赛中移至边路，本轮忻峰又停赛，梅方和邱添一成为中卫线上最后的选择。至于左右边后卫位置，卓尔队方面人选较多，罗毅、周恒、刘尚坤等人均可出战。记者马万勇原标题：卓尔防线继续伤筋动骨队长梅方出场再补漏稿源：中新网作者：";
	        String content2 = "卓尔防线继续伤筋动骨 队长梅方出场再补漏说起来卓尔队长梅方本赛季就是个“补漏”的命！在中卫与右边后卫间不停地轮换。如果不出意外，今天与广州恒大一战梅方又要换位置，这也是汉军队长连续三场比赛中的第三次换位。而从梅方的身上也可以看出，本赛季汉军防线如此“折腾”，丢球多也不奇怪了。梅方自2009赛季中乙出道便一直司职中后卫，还曾入选过布拉泽维奇国奥队，也是司职的中卫。上赛季，梅方与忻峰搭档双中卫帮助武汉卓尔队中超成功，但谁知进入本赛季后从第一场比赛开始梅方便不断因为种种“意外”而居无定所。联赛首战江苏舜天时，也是由于登贝莱受伤，朱挺位置前移，梅方临危受命客串右边后卫。第二轮主场与北京国安之战梅方仅仅打了一场中卫，又因为柯钊受罚停赛4轮而不得不再次到边路“补漏”。随着马丁诺维奇被弃用，梅方一度成为中卫首选，在与上海东亚队比赛中，邱添一停赛，梅方与忻峰再度携手，紧接着与申鑫队比赛中移至边路，本轮忻峰又停赛，梅方和邱添一成为中卫线上最后的选择。至于左右边后卫位置，卓尔队方面人选较多，罗毅、周恒、刘尚坤等人均可出战。记者马万勇";

	        System.out.println(new FingerprintService().fingerprint(content));
	        System.out.println(new FingerprintService().fingerprint(content2));
	    }
	    
	    //search suggestion
	    @Test
	    public void hh2(){
	    	   //构建一个内存型索引
	        MemoryIndex<String> mi = new MemoryIndex<String>();

	        //增加新词
	        String temp = "中国" ;

	        //生成各需要建立索引的字段
	        String quanpin = mi.str2QP(temp) ; //zhongguo
	        String jianpinpin = new String(Pinyin.str2FirstCharArr(temp)) ; //zg

	        //增加到索引中
	        mi.addItem(temp, temp ,quanpin,jianpinpin);

	        //搜索提示
	        System.out.println(mi.suggest("zg"));
	        System.out.println(mi.suggest("zhongguo"));
	        System.out.println(mi.suggest("中国"));
	        System.out.println(mi.smartSuggest("中过"));
	    }
	    
	    
	    //boolmfilter
	    @Test
	    public  void jj() throws Exception {
	    	
	    	BloomFilter bf = new BloomFilter(32); //使用32m内存过滤器。近似值
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\test\\a\\a.txt"), "UTF-8"));
	        String str = null;
	        System.out.println("begin");
	        long start = System.currentTimeMillis();
	        while ((str = br.readLine()) != null) {
	            if (bf.containsAndAdd(str)) {
	                System.out.println("containsAndAdd:" + str);
	            }
	        }

	        br.close();

	        br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\test\\a\\a.txt"), "UTF-8"));
	        System.out.println("begin-find");
	        start = System.currentTimeMillis();
	        while ((str = br.readLine()) != null) {
	            if (!bf.contains(str)) {
	                System.out.println("contains:" + str);
	            }
	        }

	        System.out.println(System.currentTimeMillis() - start);
	        br.close();
	    }
	    
	    
	    
	    @Test
	    public void testSmartForest(){
	        /**
	         * 词典的构造.一行一个词后面是参数.可以从文件读取.可以是read流.
	         */
	        long start = System.currentTimeMillis();
	        SmartForest<Integer> forest = new SmartForest<Integer>();

	        forest.add("中国", 3);

	        forest.add("android", 5);

	        forest.add("java", 3);

	        forest.add("中国人", 3);

//	        String content = " Android-java-中国人";
	        String content = " Android-java-中国";
	        content = StringUtil.rmHtmlTag(content);

	        for (int i = 0; i < 1; i++) {
	            SmartGetWord<Integer> udg = forest.getWord(content.toLowerCase().toCharArray());

	            String temp = null;
	            while ((temp = udg.getFrontWords()) != null) {
	                System.out.println(temp + "\t" + udg.getParam());
	            }
	        }
	        System.out.println(System.currentTimeMillis() - start);
	    }
	   
		}
