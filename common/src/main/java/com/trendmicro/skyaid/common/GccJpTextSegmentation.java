package com.trendmicro.skyaid.common;


import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 处理的步骤参考：<br>
 * https://gitlab.tw.trendnet.org/consumer-data-analysis-group/support_data_analyze/wikis/how-to-handle-jp-raw-data <br>
 * 其中第一步已经手动完成<br>
 *
 * @author wenjun_yang
 */
public class GccJpTextSegmentation {

    private static String jpstoppath = "D:\\03datafolder\\data\\input\\jpstopwords.txt";
    public static List<String> jpstoplist = getjpstoplist(jpstoppath);

    /**
     * @param jpfilepath
     * @return
     */
    private static List<String> getjpstoplist(String jpfilepath) {
        List<String> jplist = null;
        try {
            File jpfile = new File(jpfilepath);
            jplist = FileUtils.readLines(jpfile);
        } catch (IOException e) {

        }
        return jplist;
    }


    /**
     * @param line
     * @return
     * @throws IOException
     */
    public static ArrayList<String> wordSegmentationForJPWords(String line) throws IOException {
        JapaneseAnalyzer analyzer = new JapaneseAnalyzer(); // kuromoji, mecab
        return displayAllTokenInfo(line, analyzer);
    }

    /**
     * @param str
     * @param a
     * @return
     */
    private static ArrayList<String> displayAllTokenInfo(String str, Analyzer a) {

        ArrayList<String> wordArrayList = new ArrayList<>();
        try {
            TokenStream stream = a.tokenStream("content", new StringReader(str));
            PositionIncrementAttribute pis = stream.addAttribute(PositionIncrementAttribute.class);
            OffsetAttribute oa = stream.addAttribute(OffsetAttribute.class);
            CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
            TypeAttribute ta = stream.addAttribute(TypeAttribute.class);
            stream.reset();
            int lastOffset = -1;
            while (stream.incrementToken()) {
                if (oa.startOffset() < lastOffset) continue;
                lastOffset = oa.endOffset();
//            	System.out.print("["+cta+"]");
                Boolean stopFlag = false;
                for (Iterator iter = jpstoplist.iterator(); iter.hasNext(); ) {
                    String strstop = (String) iter.next();
//                    System.out.println(strstop);
                    if (cta.toString().equalsIgnoreCase(strstop)) {
                        stopFlag = true;
                        break;
                    }
                }
                if (!stopFlag)
                    wordArrayList.add(cta.toString());
            }
//            System.out.println();
            stream.end();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordArrayList;
    }



    public static void main(String[] args) throws IOException {

        String sampleStr = "お世話になります。 WindowsVistaを使用しています。 "
                + "11/27にパソコンを立ち上げたところ、ウィルスバスターバージ"
                + "ョンアップ（アップデート？）のポップが出たので、そのまま続"
                + "行しました。 前のバージョンを削除するという画面が表示されイ"
                + "ンストール画面まで進みましたが、エラーが出てインストールで"
                + "きませんでした。 （この時、ウィルスバスタークラウドのアイコ"
                + "ンがデスクトップからなくなっていました。） Vistaには対応して"
                + "いないということで、8月のセキュリティ更新プログラムKB2918614"
                + "をアンインストール、ウィルスバスタークラウドをアンインストール、"
                + "手動でクラウドをインストールと進みましたが、やはりエラーが出て"
                + "しまいます。 クラウドのアイコンがなくなっており、すべてのプログ"
                + "ラムからも消えているので、今はパソコンには何もセキュリティがさ"
                + "れていない状態なのでしょうか？ このままネットに接続すると危険"
                + "でしょうか？ また、クラウドをインストールする方法は他にもありま"
                + "すでしょうか？ ご回答いただければ幸いです。 お手数をおかけしま"
                + "すが、どうぞよろしくお願い致します。";

        String sampleStr2 = "昨日の夕方５時頃から、ウィルスバスターのバージョンアップの案内が来たので、やっているのですが、現在に至っても、「コンピュータをチェックしています」となっています。 "
                + "OSはビスタなのですが、こんなに時間がかかるのでしょうか?また、どのような対応をすべきでしょうか?";

        String sampleStr3 = "このメールは『JP-33F-99-F』（imagio MP 2550）から送信されたものです。問い合わせ先";

        ArrayList result = wordSegmentationForJPWords(sampleStr3);
//        System.out.println(result);



        // test stoplist
//        for (Iterator iter = jpstoplist.iterator(); iter.hasNext(); ) {
//            String str = (String) iter.next();
//            System.out.println(str);
//        }

        // test words
//        List<String> inputList = FileUtils.readLines(new File("D:\\testModifiednobom.csv"));
//        ArrayList<String> outputList = Lists.newArrayListWithCapacity(inputList.size());
//        for (String line : inputList) {
//            String[] cells = line.split("\\,");
//            ArrayList<String> temp = wordSegmentationForJPWords(cells[5]);
//            outputList.add(cells[5] + " : " + temp);
//        }
//        FileUtils.writeLines(new File("D:\\out.tsv"), outputList);

    }
}
