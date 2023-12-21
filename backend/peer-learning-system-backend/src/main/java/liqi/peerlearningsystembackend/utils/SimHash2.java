package liqi.peerlearningsystembackend.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.recognition.impl.StopRecognition;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * 完成SimHash的计算，将每个词向量的权重由idf来决定
 *
 * @author Ove
 *
 */
public class SimHash2 {
    private int hashbits = 64; // 分词后的hash数;
    public Map<String, Double> IDF;

    public SimHash2(){
        IDF = getIDF();
    }

    public SimHash2(int hashbits) {
        this.hashbits = hashbits;
    }

    // 读取本地文件，获得对应词的idf值
    public Map<String, Double> getIDF(){
        Map<String, Double> result = new HashMap<String, Double>();
        try {
            // 打开Resource/data/idf.txt
            FileInputStream fis = new FileInputStream("src/main/resources/data/idf.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                result.put(split[0], Double.valueOf(split[1]));
            }
            // 关闭文件
            br.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 清除html标签
     *
     * @param content
     * @return
     */
    private String cleanResume(String content) {
        // 若输入为HTML,下面会过滤掉所有的HTML的tag
        content = Jsoup.clean(content, Whitelist.none());
        content = StringUtils.lowerCase(content);
        String[] strings = { " ", "\n", "\r", "\t", "\\r", "\\n", "\\t", "&nbsp;" };
        for (String s : strings) {
            content = content.replaceAll(s, "");
        }
        return content;
    }

    /**
     * 这个是对整个字符串进行hash计算
     *
     * @return
     */
    private BigInteger simHash(String tokens) {

        tokens = cleanResume(tokens); // cleanResume 删除一些特殊字符

        int[] v = new int[this.hashbits];

        Result ansjList = wordAnalyzer(tokens);
        // System.out.println(ansjList);

        // 标识该文档中每个词出现的次数
        Map<String, Integer> wordCount = new HashMap<String, Integer>();

        Integer count = 0;
        for (Term term : ansjList) {
            count = wordCount.get(term.getName());
            if (count == null) {
                wordCount.put(term.getName(), 1);
            } else {
                wordCount.put(term.getName(), count + 1);
            }
        }

        int len = wordCount.size();
        String word = "";
        for (Term term : ansjList) {
            word = term.getName(); // 分词字符串
            // 2、将每一个分词hash为一组固定长度的数列.比如 64bit 的一个整数.
            BigInteger t = this.hash(word);
            for (int i = 0; i < this.hashbits; i++) {
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);
                // 3、建立一个长度为64的整数数组(假设要生成64位的数字指纹,也可以是其它数字),
                // 对每一个分词hash后的数列进行判断,如果是1000...1,那么数组的第一位和末尾一位加1,
                // 中间的62位减一,也就是说,逢1加1,逢0减1.一直到把所有的分词hash数列全部判断完毕.
                double tf = (double) wordCount.get(word) / len;
                if(IDF.get(word)==null) continue;
                Double weight = 100 * tf * IDF.get(word); // 添加权重，权重应改为出现次数，而不是根据词性来指定。
//				if(weight==null) continue;
                // if (wordCount.containsKey(word)) {
                // weight = wordCount.get(word);
                // }
                if (t.and(bitmask).signum() != 0) {
                    // 这里是计算整个文档的所有特征的向量和
                    v[i] += weight;
                } else {
                    v[i] -= weight;
                }
            }
        }

        BigInteger fingerprint = new BigInteger("0");
        for (int i = 0; i < this.hashbits; i++) {
            if (v[i] >= 0) {
                fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
            }
        }
        return fingerprint;
    }

    /**
     * 对单个的分词进行hash计算;
     *
     * @param source
     * @return
     */
    private BigInteger hash(String source) {
        if (source == null || source.length() == 0) {
            return new BigInteger("0");
        } else {
            /**
             * 当sourece 的长度过短，会导致hash算法失效，因此需要对过短的词补偿
             */
            while (source.length() < 3) {
                source = source + source.charAt(0);
            }
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(new BigInteger("1"));
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }

    /**
     * 计算海明距离,海明距离越小说明越相似;
     */
    public int hammingDistance(String s1, String s2) {
        BigInteger one = this.simHash(s1);
        BigInteger two = this.simHash(s2);
        BigInteger m = new BigInteger("1").shiftLeft(this.hashbits).subtract(new BigInteger("1"));
        BigInteger x = one.xor(two).and(m);
        int tot = 0;
        while (x.signum() != 0) {
            tot += 1;
            x = x.and(x.subtract(new BigInteger("1")));
        }
        return tot;
    }

    /**
     * 计算文本相似度
     *
     * @param s1
     * @param s2
     * @return
     */
    public double getSemblance(String s1, String s2) {
        double i = (double) this.hammingDistance(s1, s2);
        return 1 - i / this.hashbits;
    }

    /**
     * 对单行文本进行分词，分词后每个词以空格相隔开
     *
     * @param line
     * @return
     */
    public static Result wordAnalyzer(String line) {

        StopRecognition filter = new StopRecognition();

        filter.insertStopNatures("w"); // 过滤标点
        filter.insertStopNatures("null"); // 过滤空格
        filter.insertStopNatures("m"); // 过滤数词,会将 半数 该词当做是数词进行过滤
        String[] stopWords = { "如图所示", "的", "中", "下列", "说法", "正确", "是", "若", "为", "则", "在" };
        filter.insertStopWords(stopWords); // 过滤单个词

        Result fliterContent = ToAnalysis.parse(line).recognition(filter);

        return fliterContent;
    }

    public static void main(String[] args) {
        String s1 = "如图所示,电源电压保持不变，闭合开关 S 0,滑动变阻器R的滑片向右移动的过程中，下列说法正确的是 A．闭合开关S,若甲、乙均为电压表,则两表示数均变小 B．断开开关S,若甲、乙均为电流表,则两表示数均变大 C．闭合开关S,若甲、乙均为电压表,则甲示数不变,乙示数变大 D．断开开关S,若甲、乙均为电流表,则乙示数不变,甲示数变小";
        String s2 = "小明今天去吃肯德基";
        SimHash2 ob = new SimHash2();
        System.out.println(ob.getSemblance(s1, s2));
        // System.out.println(new BigInteger("1000003"));
    }

}
