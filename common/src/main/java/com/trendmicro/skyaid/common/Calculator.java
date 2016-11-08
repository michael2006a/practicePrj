package com.trendmicro.skyaid.common;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by herbert_yin on 2016/9/8.
 */
public class Calculator {
    public static void main(String[] args) throws IOException {

        String tomboFolder = "D:\\03datafolder\\data\\output\\0914\\result_tombo_0914.csv";
        String calculatedOut = "D:\\03datafolder\\data\\output\\0914\\tombo_0914_c3.5.csv";
        String tfidfOut = "D:\\03datafolder\\data\\output\\0914\\tombo_0914_TfIdf3.5.csv";
        Double mThresh = 3.5;
        try {
            counterForTombo(tomboFolder,calculatedOut,mThresh);
            tfidfForTombo(tomboFolder,tfidfOut,mThresh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tfidfForTombo(String folderStr, String outputPath, Double thresholdNum) throws Exception {

        System.out.println(folderStr + " counterForTombo to File :" + outputPath + " Start!");

        File outfile = new File(outputPath);

        try {

            List<String> outline = new ArrayList<>(0);
            HashMap<String, Double> tomboCountMap = new HashMap<>();

            Path startingDir = Paths.get(folderStr);
            FindFileVisitor findJavaVisitor = new FindFileVisitor(".csv");
            Files.walkFileTree(startingDir, findJavaVisitor);

            for (String originPath : findJavaVisitor.getFilenameList()) {
                File infile = new File(originPath);
                List<String> inline = FileUtils.readLines(infile);

                System.out.println(originPath + "lines is: " + inline.size());

                for (int i = 0; i < inline.size(); i++) {
                    String mLineStr = inline.get(i);

                    String[] titleScores = mLineStr.split(",");

                    for (int j = 3; j < titleScores.length; j += 2) {
                        if (Double.valueOf(titleScores[j]) > thresholdNum) {
                            String mTomboTitle = titleScores[j - 1].replace(".txt","");
                            if (tomboCountMap.containsKey(mTomboTitle))
                                tomboCountMap.put(mTomboTitle, (tomboCountMap.get(mTomboTitle) + Double.valueOf(titleScores[j])));
                            else
                                tomboCountMap.put(mTomboTitle, Double.valueOf(titleScores[j]));
                        } else
                            break;
                    }
                    if(i%1000 ==0){
                        System.out.println(originPath + " has been calculated lines: " + i);
                    }
                }
            }
            for (Map.Entry<String, Double> entry : tomboCountMap.entrySet()) {
                String mOutStr = entry.getKey().concat(",").concat(entry.getValue().toString());
                outline.add(mOutStr);
            }

            FileUtils.writeLines(outfile, outline, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(folderStr + " counterForTombo to File :" + outputPath + " End!");

    }

    /**
     * @param folderStr
     * @param outputPath
     * @param thresholdNum
     * @throws Exception
     */
    public static void counterForTombo(String folderStr, String outputPath, Double thresholdNum) throws Exception {

        System.out.println(folderStr + " counterForTombo to File :" + outputPath + " Start!");

        File outfile = new File(outputPath);

        try {

            List<String> outline = new ArrayList<>(0);
            HashMap<String, Long> tomboCountMap = new HashMap<>();

            Path startingDir = Paths.get(folderStr);
            FindFileVisitor findJavaVisitor = new FindFileVisitor(".csv");
            Files.walkFileTree(startingDir, findJavaVisitor);

            for (String originPath : findJavaVisitor.getFilenameList()) {
                File infile = new File(originPath);
                List<String> inline = FileUtils.readLines(infile);

                System.out.println(originPath + "lines is: " + inline.size());

                for (int i = 0; i < inline.size(); i++) {
                    String mLineStr = inline.get(i);

                    String[] titleScores = mLineStr.split(",");

                    for (int j = 3; j < titleScores.length; j += 2) {
                        if (Double.valueOf(titleScores[j]) > thresholdNum) {
                            String mTomboTitle = titleScores[j - 1].replace(".txt","");
                            if (tomboCountMap.containsKey(mTomboTitle))
                                tomboCountMap.put(mTomboTitle, (tomboCountMap.get(mTomboTitle) + 1));
                            else
                                tomboCountMap.put(mTomboTitle, 1L);
                        } else
                            break;
                    }
                    if(i%1000 ==0){
                        System.out.println(originPath + " has been calculated lines: " + i);
                    }
                }
            }
            for (Map.Entry<String, Long> entry : tomboCountMap.entrySet()) {
                String mOutStr = entry.getKey().concat(",").concat(entry.getValue().toString());
                outline.add(mOutStr);
            }

            FileUtils.writeLines(outfile, outline, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(folderStr + " counterForTombo to File :" + outputPath + " End!");

    }

    public static void countlrtop3(String filep) {
        try {
            System.out.println("countlr Start: " + filep);
            List<String> cateList = FileUtils.readLines(new File(filep));
            int listSize = cateList.size();
            int top1 = 0;
            int top3 = 0;

            for (int i = 0; i < listSize; i++) {
                String[] a = cateList.get(i).replaceAll("[\\()]", "").split(",");
                if (Math.abs(Double.valueOf(a[0]) - Double.valueOf(a[1])) < 0.0001)
                    top1 += 1;
                if (Math.abs(Double.valueOf(a[0]) - Double.valueOf(a[1])) <= 1.0001)
                    top3 += 1;
            }

            System.out.println(top1 * 1.0 / listSize);
            System.out.println(top3 * 1.0 / listSize);
            System.out.println("countlr End: " + filep);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void counterFunction(String originPath, String outputPath) throws Exception {

        System.out.println(originPath + " counterFunction to File :" + outputPath + " Start!");
        File infile = new File(originPath);
        File outfile = new File(outputPath);

        try {
            List<String> inline = FileUtils.readLines(infile);
            List<String> outline = new ArrayList<>(0);

            HashMap<String, Integer> kbMap = new HashMap<>();
            for (int i = 0; i < inline.size(); i++) {
                String mLineStr = inline.get(i);
                if (kbMap.containsKey(mLineStr))
                    kbMap.put(mLineStr, (kbMap.get(mLineStr) + 1));
                else
                    kbMap.put(mLineStr, 1);
            }

            for (Map.Entry<String, Integer> entry : kbMap.entrySet()) {
                String mOutStr = entry.getKey().concat(",").concat(entry.getValue().toString());
                outline.add(mOutStr);
            }

            FileUtils.writeLines(outfile, outline, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(originPath + " counterFunction to File :" + outputPath + " End!");

    }

}
