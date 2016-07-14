package com.trendmicro.skyaid.common;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Created by herbert_yin on 2016/6/12.
 */
public class DataFormatUtil {


    public static void main(String[] args) throws IOException {

        try {
            int[] catIndex = {0, 1};
            int[] Index0 = {0};
            int[] Index1 = {1};
            int[] cluster = {15,30, 50, 100, 150};
            int[] clusterall = {0, 15, 30, 50, 100, 150};
            List<String> category = FileUtils.readLines(new File("D:\\03datafolder\\data\\input\\0711\\category.csv"));
            List<String> delete = FileUtils.readLines(new File("D:\\03datafolder\\data\\input\\0711\\delete.csv"));
            String gccalldata = "D:\\03datafolder\\data\\input\\0711\\result-callin-complete.csv";
            String gccidfsOut = "D:\\03datafolder\\data\\input\\0711\\gccallidfs.csv";
            String gccidfsfolder = "D:\\03datafolder\\data\\input\\0711\\sepfolder";
            String alltermsidfs = "D:\\03datafolder\\data\\output\\0711\\alltermsidfs.csv";
            String index_category = "D:\\03datafolder\\data\\input\\0711\\index_category.csv";
            String userItemScore = "D:\\03datafolder\\data\\output\\0711\\userItemScore.csv";
            String userItemScorePIC = "D:\\03datafolder\\data\\output\\0711\\userItemScorePIC.csv";
            //step 1: keep top category
//            extractLinesByStrList(gccalldata, category);
//            removeLinesByStr(gccalldata,delete);

            //step 2: transfer to idfs
//            word2idfsCovert(gccalldata, gccidfsOut, alltermsidfs, index_category, 3000);
            //step 3: transfer category to index
//            zipWithUniqueCat(userItemScore, userItemScorePIC, index_category, catIndex,true);
//            seperateOrcombineFile(gccidfsfolder, gccidfsOut, true);

            //step4: PIC clusterId replace category
//            int[] cluster = {50, 100, 150, 200, 250, 300};

            for (int i = 0; i < cluster.length; i++) {
                String clusterCatpath = "D:\\03datafolder\\data\\output\\0711\\pic_" + cluster[i] + ".csv";
                String outclusterCatpath = "D:\\03datafolder\\data\\input\\0711\\gccallidfs_" + cluster[i] + ".csv";

//                zipWithUniqueCat(gccidfsOut, outclusterCatpath, clusterCatpath, Index0, false," ");
//                removeLinesByStr(outclusterCatpath, "DEFAULT_ID");
            }


            //step5: remove "DEFAULT_ID"
            int[] s = {1, 2, 3, 5, 6, 7, 8};
            for (int j = 0; j < s.length; j++) {
                String gccseperate = "D:\\03datafolder\\data\\input\\0711\\gcclsa\\combine\\gccalldata_" + s[j] + ".csv";
//                extractLinesByStrList(gccseperate,category);
            }


            //step 6:
            String folderStr = "D:\\03datafolder\\data\\input\\0711\\originaldata";
            String lsatrain = "D:\\03datafolder\\data\\input\\0711\\gcclsa\\lsatrain.csv";
            String lsatest = "D:\\03datafolder\\data\\input\\0711\\gcclsa\\lsatest.csv";
//  seperateOrcombineFile(folderStr, lsatrain, false);

//            extractLinesByStrList(lsatest,category);
            //step7 :
            for (int i = 0; i < cluster.length; i++) {
                int tt = cluster[i];
                String clusterCatpath = "D:\\03datafolder\\data\\output\\0711\\pic_" + cluster[i] + ".csv";
                String clusteroutpath = "D:\\03datafolder\\data\\output\\0711\\pic_cat" + cluster[i] + ".csv";

//                zipWithUniqueCat(clusterCatpath,clusteroutpath, index_category, Index0, false);
            }


//            extractLinesByStrList(lsatrain,category);
//            extractLinesByStrList(lsatest,category);
            for (int i = 0; i < cluster.length; i++) {
//                String clusterCatpath = "D:\\03datafolder\\data\\output\\0711\\pic" + cluster[i] + ".csv";
                String clusteroutpath = "D:\\03datafolder\\data\\output\\0711\\pic_cat" + cluster[i] + ".csv";
                String lsatrainpic = "D:\\03datafolder\\data\\input\\0711\\gcclsa\\lsatrain_" + cluster[i] + ".csv";
                String lsatestpic = "D:\\03datafolder\\data\\input\\0711\\gcclsa\\lsadata_" + cluster[i] + ".csv";
//                zipWithUniqueCat(gccalldata, lsatestpic, clusteroutpath, Index1, false);
//                for (int j = 0; j < s.length; j++) {
//                    String tt = String.valueOf(cluster[i]);
//                    String gccseperate = "D:\\03datafolder\\data\\input\\0711\\gcclsa\\combine\\gccalldata_" + s[j] + ".csv";
//                    String gccseperateout = "D:\\03datafolder\\data\\input\\0711\\gcclsa\\combine\\" + tt + "\\gccalldata_" + s[j] + ".csv";
//                    String gccpicfolder = "D:\\03datafolder\\data\\input\\0711\\gcclsa\\combine\\" + tt;
//                    String lsapictrain = "D:\\03datafolder\\data\\input\\0711\\gcclsa\\lsatrain_" + tt + ".csv";
//                    zipWithUniqueCat(gccseperate, gccseperateout, clusteroutpath, Index1, false);
//                    seperateOrcombineFile(gccpicfolder, lsapictrain, false);
//                }
            }
            String lsatrain0 = "D:\\03datafolder\\data\\input\\0711\\gcclsa\\lsatrain_0.csv";
//            zipWithUniqueCat(gccalldata, lsatrain0, index_category, Index1, true);

            for (int i = 0; i < clusterall.length; i++) {
                String lsatrainpic = "D:\\03datafolder\\data\\input\\0711\\gcclsa\\lsadata_" + clusterall[i] + ".csv";
                String lsatarinFold = "D:\\03datafolder\\data\\input\\0711\\lsadata_" + clusterall[i];
//                seperateOrcombineFile(lsatarinFold, lsatrainpic, true, 230066);
            }

//            countlrtop3("D:\\03datafolder\\data\\output\\0711\\lrResallidfs_0.csv");
//            countlrtop3("D:\\03datafolder\\data\\output\\0711\\lrResallidfs_15.csv");
//            countlrtop3("D:\\03datafolder\\data\\output\\0711\\lrResallidfs_30.csv");
//            countlrtop3("D:\\03datafolder\\data\\output\\0711\\lrResallidfs_50.csv");
//            countlrtop3("D:\\03datafolder\\data\\output\\0711\\lrResallidfs_100.csv");
//            countlrtop3("D:\\03datafolder\\data\\output\\0711\\lrResallidfs_150.csv");


//            int[] collomExt = {6, 15};
//            HashMap<Integer, String> regHashMap = new HashMap<>();
//            regHashMap.put(15, "(<　http(s)?://esupport[^>]*>)");
//            String inputBase = "E:\\workFolder\\00DataAnalysis\\aawSpark\\data\\gcc";
//            String httpoutput = "E:\\GCCData\\round1\\2015httpHttps.csv";
//            File ffold = new File(inputBase);
//            File[] fileList = ffold.listFiles();
//            for (int i = 1; i < fileList.length; i++) {
//                extractLinesByStr(fileList[i].getAbsolutePath(), httpoutput, collomExt, regHashMap, true);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
  5.75E+03	1    575E01
1.60E+03	31   160E01
1.56E+02	5   156E00
   */
    protected static final String[][] PATTERN_NUM_MAPPING = new String[][]{
            {"575E01", "5.75E+03"},
            {"160E01", "1.60E+03"},
            {"156E00", "1.56E+02"},
    };

    protected static final String[][] PATTERN_WORD_MAPPING = new String[][]{
            {"TROJ_", "TROJ-"},
            {"TROJSAMPLE", "(TROJ_+[A-Z0-9|.|-|_]+)"},
            {"UPLOADFILE", "Cons_[?0-9]{14}_[?a-zA-Z0-9]{8}-[?a-zA-Z0-9]{4}-[?a-zA-Z0-9]{4}-[?a-zA-Z0-9]{4}-[?a-zA-Z0-9]{12}.zip"},
            {"GUID", "[?a-zA-Z0-9]{8}-[?a-zA-Z0-9]{4}-[?a-zA-Z0-9]{4}-[?a-zA-Z0-9]{4}-[?a-zA-Z0-9]{12}"},
            {"EMAIL", "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"},
            {"CASEID", "[?0-9]{1}-[?0-9]{1}-[?0-9]{9,10}"},
            {"FAX", "[?0-9]{3,4}-[?0-9]{2,3}-[?0-9]{3,4}"},
//            {"SN", "[?a-zA-Z]{4}-[?0-9]{4}-[?0-9]{4}-[?0-9]{4}-[?0-9]{4}"},
//            {"SN", "[?a-zA-Z]{4}-[?0-9]{4}-[?0-9]{4}-[?0-9]{4}"},
            {"MacOS otherwise", "MacOS 縺昴・莉・0"},
            {"MacOS otherwise", "MacOS 縺昴・莉・1"},
            {"MacOS otherwise", "MacOS縺吶∋縺ｦ"},
            {"Windows otherwise", "Windows 縺昴・莉・0"},
            {"Windows otherwise", "Windows 縺昴・莉・1"},
            {"Windows otherwise", "Windows縺吶∋縺ｦ"},
            {"Windows 7 64-bit", "Windows7縲\u008064bit"},
    };


    public static void seperateOrcombineFile(String folderStr, String fileStr, boolean seperateFlag) throws Exception {

        int defaultSeperateLines = 50000;
        String defaultEncoding = "UTF-8";

        File mfile = new File(fileStr);


        if (seperateFlag) {
            List<String> inline = FileUtils.readLines(mfile);

            String[] filename = fileStr.split("\\.");
            int i = 0;
            int count = 1;
            while (i < inline.size()) {
                String newFilename = filename[0] + "_" + count + "." + filename[1];
                File newFile = new File(newFilename);

                List<String> mtempList = new ArrayList<String>();
                if (i + defaultSeperateLines < inline.size()) {
                    mtempList = inline.subList(i, i + defaultSeperateLines);
                } else {
                    mtempList = inline.subList(i, inline.size() - 1);
                }
                FileUtils.writeLines(newFile, mtempList, false);
                i = i + defaultSeperateLines;
                count++;
            }
        } else {
            File mfolder = new File(folderStr);
            File[] filesArray = mfolder.listFiles();
            List<String> outline = new ArrayList<>(0);

            for (int i = 0; i < filesArray.length; i++) {
                outline = FileUtils.readLines(filesArray[i]);
                FileUtils.writeLines(mfile, outline, true);
            }

        }

    }

    public static void seperateOrcombineFile(String folderStr, String fileStr, boolean seperateFlag, int lines) throws Exception {

        int defaultSeperateLines = lines;
        String defaultEncoding = "UTF-8";

        File mfile = new File(fileStr);


        if (seperateFlag) {
            List<String> inline = FileUtils.readLines(mfile);

            String[] filename = fileStr.split("\\.");
            int i = 0;
            int count = 1;
            while (i < inline.size()) {
                String newFilename = filename[0] + "_" + count + "." + filename[1];
                File newFile = new File(newFilename);

                List<String> mtempList = new ArrayList<String>();
                if (i + defaultSeperateLines < inline.size()) {
                    mtempList = inline.subList(i, i + defaultSeperateLines);
                } else {
                    mtempList = inline.subList(i, inline.size() - 1);
                }
                FileUtils.writeLines(newFile, mtempList, false);
                i = i + defaultSeperateLines;
                count++;
            }
        } else {
            File mfolder = new File(folderStr);
            File[] filesArray = mfolder.listFiles();
            List<String> outline = new ArrayList<>(0);

            for (int i = 0; i < filesArray.length; i++) {
                outline = FileUtils.readLines(filesArray[i]);
                FileUtils.writeLines(mfile, outline, true);
            }

        }

    }

    /**
     * @param originPath
     * @param outputPath
     * @param arraylenth
     */
    public static void getCategoryList(String originPath, String outputPath, int arraylenth) {

        System.out.println(originPath + " getCategoryList to File :" + outputPath + " Start!");
        File infile = new File(originPath);
        File outfile = new File(outputPath);

        try {
            List<String> inline = FileUtils.readLines(infile);
            List<String> outline = new ArrayList<>(0);

            for (int i = 0; i < inline.size(); i++) {
//                outline.add(inline.get(i).replaceFirst(".*?(?=\\,)", ""));
                if (inline.get(i).split(",").length > arraylenth) {
                    outline.add(inline.get(i).replaceFirst(".*?(\\,)", ""));
                }
            }
            FileUtils.writeLines(outfile, outline, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(originPath + " getCategoryList to File :" + outputPath + " End!");

    }

    /**
     * replace lines by String,extractLinesByStrList
     *
     * @param fpath
     * @param exlist
     */
    public static void extractLinesByStr(String fpath, String outpath, int[] exlist) {
        try {
            int channellen = 4;
            File file = new File(fpath);
            File ofile = new File(outpath);
            List<String> lines = FileUtils.readLines(file);
            List<String> outlines = new ArrayList<String>();

            for (int i = 0; i < lines.size(); i++) {
                String newStr = "";
                boolean emptyFlag = false;

                String tempLine = lines.get(i);
                String[] ta = regReplace(tempLine, PATTERN_NUM_MAPPING).split(",");
//                if (tempLine.contains("E+0") || tempLine.contains("160E01"))
//                    System.out.println("dirty Data at line: " + i + ", " + regReplace(tempLine, PATTERN_NUM_MAPPING));

                for (int j = 0; j < exlist.length && ta.length > exlist[j]; j++) {
                    String tempcoll = ta[exlist[j]];
                    if (tempcoll.isEmpty() || tempcoll.equals(" ") || tempcoll.equals("----")) {
                        emptyFlag = true;
                        break;
                    }


                    //just for sn
//                    if (exlist[j] == 7) {
//                        if (tempcoll.length() < channellen) {
//                            emptyFlag = true;
//                            break;
//                        }

//                        tempcoll = tempcoll.substring(0, channellen);
//                    }
                    newStr = newStr.concat(tempcoll);
                    if (j < exlist.length - 1)
                        newStr = newStr.concat(",");
                }
                if (!emptyFlag) {
                    outlines.add(newStr);
                } else {
                    System.out.println("dirty Data at line: " + i + ", " + tempLine);
                }

            }

            if (ofile.exists()) {
                ofile.delete();
                System.out.println(outpath + " deleted!");
            }

            System.out.println("the dirty data lines countor: " + (lines.size() - outlines.size()));

            FileUtils.writeLines(ofile, outlines, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * replace lines by String,extractLinesByStrList
     *
     * @param fpath
     * @param outpath
     * @param exlist
     * @param pattenStr
     * @param appenFlag
     */
    public static void extractLinesByStr(String fpath, String outpath, int[] exlist, HashMap<Integer, String> pattenStr, boolean appenFlag) {
        try {
            File file = new File(fpath);
            File ofile = new File(outpath);

            List<String> lines = FileUtils.readLines(file);
            List<String> outlines = new ArrayList<>();

            for (int i = 0; i < lines.size(); i++) {
                String newStr = "";
                boolean emptyFlag = false;

                String tempLine = lines.get(i);
                String[] ta = regReplace(tempLine, PATTERN_NUM_MAPPING).split(",");

                for (int j = 0; j < exlist.length && ta.length > exlist[j]; j++) {
                    String tempcoll = ta[exlist[j]];
                    if (pattenStr.containsKey(exlist[j])) {
                        List<String> regList = regSubStrList(tempcoll, pattenStr.get(exlist[j]));
                        if (regList.isEmpty()) {
                            emptyFlag = true;
                            break;
                        } else {
                            tempcoll = "";
                            for (int k = 0; k < regList.size(); k++) {
                                tempcoll = tempcoll.concat(",").concat(regList.get(k).replaceAll("[\\<　\\<\\　>\\>]", ""));
                            }
                        }
                    }

                    newStr = newStr.concat(tempcoll);
//                    if (j < exlist.length - 1)
//                        newStr = newStr.concat(",");
                }
                if (!emptyFlag && newStr.length() > 10) {
                    outlines.add(newStr);
                }
//                else {
//                    System.out.println("dirty Data at line: " + i + ", " + tempLine);
//                }
                if (i % 1000 == 0) {
                    System.out.println(i + " lines has been converted! ");
                }
            }

            if (!appenFlag && ofile.exists()) {
                ofile.delete();
                System.out.println(outpath + " deleted!");
            }

            System.out.println("the dirty data lines countor: " + (lines.size() - outlines.size()));

            FileUtils.writeLines(ofile, outlines, appenFlag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * replace lines by String
     *
     * @param fpath
     * @param replacelist
     */
    public static void modifyLinesByStr(String fpath, String replacelist) {
        try {
            int countor = 0;
            File file = new File(fpath);
            File osfile = new File(replacelist);
            List<String> lines = FileUtils.readLines(file);
            List<String> oslines = FileUtils.readLines(osfile);
            for (int i = 0; i < lines.size(); i++) {
                for (int j = 0; j < oslines.size(); j++) {
                    String[] temp = oslines.get(j).split(",");
                    String tempLine = lines.get(i);
                    if (tempLine.contains(temp[0])) {
                        lines.set(i, tempLine.replaceAll(temp[0], temp[1]));
                        countor++;
                        if (countor % 1000 == 0)
                            System.out.println(fpath + " replaced lines: " + countor);
                        break;
                    }
                }
            }

            System.out.println(fpath + " replaced lines: " + countor);
            FileUtils.writeLines(file, lines, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete lines by String
     *
     * @param fpath
     * @param filteredStr
     */
    public static void removeLinesByStr(String fpath, String filteredStr) {
        try {
            File file = new File(fpath);
            List<String> lines = FileUtils.readLines(file);
            List<String> updatedLines = lines.stream().filter(s -> !s.contains(filteredStr)).collect(Collectors.toList());

            System.out.println(fpath + " delete lines: " + (lines.size() - updatedLines.size()));
            FileUtils.writeLines(file, updatedLines, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete lines by List<String>
     *
     * @param fpath
     * @param filteredStrList
     */
    public static void removeLinesByStr(String fpath, List<String> filteredStrList) {
        System.out.println(fpath + " removeLinesByStr Start!");
        try {
            File file = new File(fpath);
            List<String> lines = FileUtils.readLines(file);
            List<String> updatedLines = lines.stream().filter(s -> !strContainsbyList(s, filteredStrList)).collect(Collectors.toList());

            System.out.println(fpath + " delete lines: " + (lines.size() - updatedLines.size()));
            FileUtils.writeLines(file, updatedLines, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fpath + " removeLinesByStr End!");
    }

    /**
     * keep lines by List<String>
     *
     * @param fpath
     * @param filteredStrList
     */
    public static void extractLinesByStrList(String fpath, List<String> filteredStrList) {
        System.out.println(fpath + " extractLinesByStrList Start!");
        try {
            File file = new File(fpath);
            List<String> lines = FileUtils.readLines(file);
            List<String> updatedLines = lines.stream().filter(s -> strContainsbyList(s, filteredStrList)).collect(Collectors.toList());

            System.out.println(fpath + " orginal lines: " + lines.size());
            System.out.println(fpath + " keep lines: " + updatedLines.size());
            System.out.println(fpath + " delete lines: " + (lines.size() - updatedLines.size()));
            FileUtils.writeLines(file, updatedLines, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fpath + " extractLinesByStrList End!");
    }

    public static void extractLinesByStrList(String fpath, String opath, List<String> filteredStrList, boolean appendFlag) {
        System.out.println(fpath + " extractLinesByStrList Start!");
        try {
            File file = new File(fpath);
            File outfile = new File(opath);
            List<String> lines = FileUtils.readLines(file);
            List<String> updatedLines = lines.stream().filter(s -> strContainsbyList(s, filteredStrList)).collect(Collectors.toList());

            System.out.println(fpath + " orginal lines: " + lines.size());
            System.out.println(fpath + " delete lines: " + (lines.size() - updatedLines.size()));
            System.out.println(opath + " keep lines: " + updatedLines.size());

            FileUtils.writeLines(outfile, updatedLines, appendFlag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(opath + " extractLinesByStrList End!");
    }


    /**
     * @param originPath
     * @param outputPath
     * @param idfsPath
     * @param docIds
     * @param topWords
     */
    public static void word2idfsCovert(String originPath, String outputPath, String idfsPath, String docIds, int topWords) {
        System.out.println(originPath + " convert to File :" + outputPath + " Start!");
        boolean idfsFlag = false;
        try {
            File infile = new File(originPath);
            File outfile = new File(outputPath);
            File idfsfile = new File(idfsPath);
            File docsidfile = new File(docIds);
            List<String> inline = FileUtils.readLines(infile);
            List<String> idfsline = FileUtils.readLines(idfsfile);
            List<String> docsidline = FileUtils.readLines(docsidfile);

            if (outfile.exists()) {
                outfile.delete();
                System.out.println("delete file: " + outputPath);
            }

            int vectorSize = -1;
            List<String> outline = new ArrayList<>(0);
            List<String> topNidfs = new ArrayList<>(0);
            HashMap<String, String> docsidMap = new HashMap<>();
            if (topWords < idfsline.size()) {
                vectorSize = topWords;
                for (int i = 0; i < vectorSize; i++) {
                    topNidfs.add(i, idfsline.get(i));
                }
            } else {
                vectorSize = idfsline.size();
                topNidfs = idfsline;
            }

            //            int vectorSize = (topWords < idfsline.size())?topWords: idfsline.size();
            for (int i = 0; i < docsidline.size(); i++) {
                String[] docsid = docsidline.get(i).split("\\,");
                docsidMap.put(docsid[1], docsid[0]);
            }


            System.out.println("input Lines: " + inline.size());
            for (int i = 0; i < inline.size(); i++) {
                idfsFlag = false;
                String in = inline.get(i);
                String[] wordString = in.split("\\,");
                if (wordString.length >= 6) {
                    String newStr = docsidMap.getOrDefault(wordString[1], "DEFAULT_ID");
                    ArrayList<String> sw = GccJpTextSegmentation.wordSegmentationForJPWords(wordString[5]);
                    for (int v = 0; v < vectorSize; v++) {
                        String idfsScore = topNidfs.get(v);
                        if (strContainsbyList(idfsScore, sw)) {
                            newStr = newStr.concat(" ").concat(String.valueOf(v + 1)).concat(":").concat(idfsScore.split("\\,")[1]);
                            idfsFlag = true;
                        }
                    }
                    if (idfsFlag) {
                        outline.add(newStr);
                    }
                }
            }
            System.out.println("output Lines: " + outline.size());
            FileUtils.writeLines(outfile, outline, false);
            System.out.println(originPath + " convert to File :" + outputPath + " completely!");
            removeLinesByStr(outputPath, "DEFAULT_ID");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param originPath
     * @param outputPath
     * @param docIds
     */
    public static void zipWithUniqueCat(String originPath, String outputPath, String docIds, int[] index, boolean mapReverse) {
        System.out.println(originPath + " zipWithUniqueCat to File :" + outputPath + " Start!");
        File infile = new File(originPath);
        File outfile = new File(outputPath);
        File docsidfile = new File(docIds);

        try {
            List<String> inline = FileUtils.readLines(infile);
            List<String> docsidline = FileUtils.readLines(docsidfile);

            if (outfile.exists()) {
                outfile.delete();
                System.out.println("delete file: " + outputPath);
            }

            List<String> outline = new ArrayList<>(0);
            List<String> topNidfs = new ArrayList<>(0);
            HashMap<String, String> docsidMap = new HashMap<>();

            //            int vectorSize = (topWords < idfsline.size())?topWords: idfsline.size();
            for (int i = 0; i < docsidline.size(); i++) {
                String[] docsid = docsidline.get(i).split("\\,");
//                docsidMap.put(docsid[1], docsid[0]);
                if (mapReverse) {
                    docsidMap.put(docsid[1], docsid[0]);
                } else {
                    docsidMap.put(docsid[0], docsid[1]);
                }
            }


            System.out.println("input Lines: " + inline.size());
            for (int i = 0; i < inline.size(); i++) {
                String in = inline.get(i);
                String[] wordString = in.split("\\,");
                String newStr = "";

                for (int j = 0; j < index.length && index[j] < wordString.length; j++) {
                    wordString[index[j]] = docsidMap.getOrDefault(wordString[index[j]], "DEFAULT_ID");
                }

                for (int k = 0; k < wordString.length; k++) {
                    if (k == wordString.length - 1)
                        newStr = newStr.concat(wordString[k]);
                    else
                        newStr = newStr.concat(wordString[k]).concat(",");
                }

                if (i % 1000 == 0) {
                    System.out.println(i + " Lines completed");
                }
                outline.add(newStr);
            }
            System.out.println("output Lines: " + outline.size());
            FileUtils.writeLines(outfile, outline, false);
            removeLinesByStr(outputPath, "DEFAULT_ID");
            System.out.println(originPath + " zipWithUniqueCat to File :" + outputPath + " completely!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param originPath
     * @param outputPath
     * @param docIds
     */
    public static void zipWithUniqueCat(String originPath, String outputPath, String docIds, int[] index, boolean mapReverse, String delideter) {
        System.out.println(originPath + " zipWithUniqueCat to File :" + outputPath + " Start!");
        File infile = new File(originPath);
        File outfile = new File(outputPath);
        File docsidfile = new File(docIds);

        try {
            List<String> inline = FileUtils.readLines(infile);
            List<String> docsidline = FileUtils.readLines(docsidfile);

            if (outfile.exists()) {
                outfile.delete();
                System.out.println("delete file: " + outputPath);
            }

            List<String> outline = new ArrayList<>(0);
            List<String> topNidfs = new ArrayList<>(0);
            HashMap<String, String> docsidMap = new HashMap<>();

            //            int vectorSize = (topWords < idfsline.size())?topWords: idfsline.size();
            for (int i = 0; i < docsidline.size(); i++) {
                String[] docsid = docsidline.get(i).split("\\,");
//                docsidMap.put(docsid[1], docsid[0]);
                if (mapReverse) {
                    docsidMap.put(docsid[1], docsid[0]);
                } else {
                    docsidMap.put(docsid[0], docsid[1]);
                }
            }


            System.out.println("input Lines: " + inline.size());
            for (int i = 0; i < inline.size(); i++) {
                String in = inline.get(i);
                String[] wordString = in.split(delideter);
                String newStr = "";

                for (int j = 0; j < index.length && index[j] < wordString.length; j++) {
                    wordString[index[j]] = docsidMap.getOrDefault(wordString[index[j]], "DEFAULT_ID");
                }

                for (int k = 0; k < wordString.length; k++) {
                    if (k == wordString.length - 1)
                        newStr = newStr.concat(wordString[k]);
                    else
                        newStr = newStr.concat(wordString[k]).concat(delideter);
                }

                if (i % 1000 == 0) {
                    System.out.println(i + " Lines completed");
                }
                outline.add(newStr);
            }
            System.out.println("output Lines: " + outline.size());
            FileUtils.writeLines(outfile, outline, false);
            removeLinesByStr(outputPath, "DEFAULT_ID");
            System.out.println(originPath + " zipWithUniqueCat to File :" + outputPath + " completely!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipWithUniqueCat(String originPath, String docIds, int[] index, boolean mapReverse) {
        System.out.println(originPath + " zipWithUniqueCategory to File :" + " Start!");
        File infile = new File(originPath);
        File docsidfile = new File(docIds);

        try {
            List<String> inline = FileUtils.readLines(infile);
            List<String> docsidline = FileUtils.readLines(docsidfile);

            List<String> outline = new ArrayList<>(0);
            HashMap<String, String> docsidMap = new HashMap<>();

            //            int vectorSize = (topWords < idfsline.size())?topWords: idfsline.size();
            for (int i = 0; i < docsidline.size(); i++) {
                String[] docsid = docsidline.get(i).split("\\,");
                if (mapReverse) {
                    docsidMap.put(docsid[1], docsid[0]);
                } else {
                    docsidMap.put(docsid[0], docsid[1]);
                }
            }

            System.out.println("input Lines: " + inline.size());
            for (int i = 0; i < inline.size(); i++) {
                String in = inline.get(i);
                String[] wordString = in.split("\\,");
                String newStr = "";

                for (int j = 0; j < index.length && index[j] < wordString.length; j++) {
                    wordString[index[j]] = docsidMap.getOrDefault(wordString[index[j]], "DEFAULT_ID");
                }

                for (int k = 0; k < wordString.length; k++) {
                    if (k == wordString.length - 1)
                        newStr = newStr.concat(wordString[k]);
                    else
                        newStr = newStr.concat(wordString[k]).concat(",");
                }
                outline.add(newStr);
            }
            System.out.println("output Lines: " + outline.size());
            FileUtils.writeLines(infile, outline, false);
            removeLinesByStr(originPath, "DEFAULT_ID");
            System.out.println(originPath + " zipWithUniqueCategory to File :" + " completely!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param originPath
     * @param outputPath
     * @param docIds
     */
    public static void zipWithUniqueCategory(String originPath, String outputPath, String docIds) {
        System.out.println(originPath + " zipWithUniqueCategory to File :" + outputPath + " Start!");
        File infile = new File(originPath);
        File outfile = new File(outputPath);
        File docsidfile = new File(docIds);

        try {
            List<String> inline = FileUtils.readLines(infile);
            List<String> docsidline = FileUtils.readLines(docsidfile);

            if (outfile.exists()) {
                outfile.delete();
                System.out.println("delete file: " + outputPath);
            }

            int vectorSize = -1;
            List<String> outline = new ArrayList<>(0);
            List<String> topNidfs = new ArrayList<>(0);
            HashMap<String, String> docsidMap = new HashMap<>();

            //            int vectorSize = (topWords < idfsline.size())?topWords: idfsline.size();
            for (int i = 0; i < docsidline.size(); i++) {
                String[] docsid = docsidline.get(i).split("\\,");
                docsidMap.put(docsid[1], docsid[0]);
            }


            System.out.println("input Lines: " + inline.size());
            for (int i = 0; i < inline.size(); i++) {
                String in = inline.get(i);
                String[] wordString = in.split("\\,");
                String newStr = docsidMap.getOrDefault(wordString[1], "DEFAULT_ID").concat(",").concat(wordString[5]);
                outline.add(newStr);
            }
            System.out.println("output Lines: " + outline.size());
            FileUtils.writeLines(outfile, outline, false);
            System.out.println(originPath + " zipWithUniqueCategory to File :" + outputPath + " completely!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * check each line whether contains special str
     *
     * @param str
     * @param filterList
     * @return
     */
    public static Boolean strContainsbyList(String str, List<String> filterList) {
        Boolean strFounded = false;
        for (int i = 0; i < filterList.size(); i++) {
            if (str.contains(filterList.get(i))) {
                strFounded = true;
                break;
            }
//System.out.println(list.get(i));
        }
        return strFounded;
    }

    public static void compair2list(List<String> trainlist, List<String> testlist) {
        int co1 = 0;
        int co2 = 0;
        for (int i = 0; i < testlist.size(); i++) {
            co1++;
            if (!trainlist.contains(testlist.get(i))) {
                co2++;
                System.out.println("TestList not inculde: " + testlist.get(i));
            }
        }
        System.out.println("co1: " + co1);
        System.out.println("co2: " + co2);
    }

    public static void countlrtop3(String filep) {
        try {
            System.out.println("countlr Start: "+ filep);
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
            System.out.println("countlr End: "+ filep);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sortCategoryArray(String filep, int splitSize, int[] sortList) {
        try {
            File infile = new File((filep));
            List<String> cateList = FileUtils.readLines(infile);
            List<String> outList = new ArrayList<>(0);
            int listSize = cateList.size();
            String[][] catearray = new String[cateList.size()][splitSize];

            for (int i = 0; i < listSize; i++) {
//                catearray[i] = cateList.get(i).split("\t");
                catearray[i] = cateList.get(i).split(",");
            }
//            sort(catearray, new int[]{1, 2, 3});
            sort(catearray, sortList);

//            for (int i = 0; i < catearray.length; i++) {
//                for (int j = 0; j < catearray[i].length; j++) {
//                    System.out.print(catearray[i][j]);
//                    System.out.print(",");
//                }
//                System.out.println();
//            }

            for (int i = 0; i < cateList.size(); i++) {
                outList.add(catearray[i][0] + "," + catearray[i][1]);
            }
            System.out.println("output Lines: " + outList.size());
            FileUtils.writeLines(infile, outList, false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sort(String[][] ob, final int[] order) {
        Arrays.sort(ob, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                String[] one = (String[]) o1;
                String[] two = (String[]) o2;
                for (int i = 0; i < order.length; i++) {
                    int k = order[i];
                    if (one[k].equals(two[k]))
                        continue; //如果按一条件比较结果相等，就使用第二个条件进行比较。
                    else
                        return one[k].compareTo(two[k]);
                }
                return 0;
            }
        });
    }

    public static void sort(int[][] ob, final int[] order) {
        Arrays.sort(ob, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                int[] one = (int[]) o1;
                int[] two = (int[]) o2;
                for (int i = 0; i < order.length; i++) {
                    int k = order[i];
                    if (one[k] > two[k]) {
                        return 1;
                    } else if (one[k] < two[k]) {
                        return -1;
                    } else {
                        continue;  //如果按一条件比较结果相等，就使用第二个条件进行比较。
                    }
                }
                return 0;
            }
        });
    }

    public static void sparseVector2Desnse(String inputP, String outputPath) {

        File outfile = new File(outputPath);

        try {

            List<String> inline = FileUtils.readLines(new File(inputP));
            List<String> outline = new ArrayList<>(0);

            if (outfile.exists()) {
                outfile.delete();
                System.out.println("delete file: " + outputPath);
            }

            for (int i = 0; i < inline.size(); i++) {
                String[] input = inline.get(i).split(" ");
                outline.add(sparseArray2Str(input));
                if ((i % 1000) == 0) {
                    System.out.println(i + " lines has been converted!");
                }
            }


            FileUtils.writeLines(outfile, outline, false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String sparseArray2Str(String[] inArray) {
        String outStr = "";
        int mindex = 0;
        for (int i = 1; i < inArray.length; i++) {
            String[] dataA = inArray[i].split("\\:");
            //the idfs file input index started from 1
            while (mindex < Integer.valueOf(dataA[0]) - 1) {
                mindex++;
                outStr = outStr.concat("0,");
            }
            mindex++;
            outStr = outStr.concat(dataA[1]).concat(",");
        }
        while (mindex < 3000) {
            mindex++;
            outStr = outStr.concat("0,");
        }
        outStr = outStr.concat(inArray[0]);
        return outStr;
    }


    /**
     * @param oString
     * @param pattenStr "(<　http(s)?[^>]*>)"
     * @return
     */
    public static List<String> regSubStrList(String oString, String pattenStr) {
        Pattern p = Pattern.compile(pattenStr);
        Matcher m = p.matcher(oString);
        List<String> result = new ArrayList<String>();
        while (m.find()) {
            result.add(m.group());
        }
//        for (String s1 : result) {
//            System.out.println(s1);
//        }
        return result;
    }

    /**
     * 正则替换
     *
     * @param line
     * @return
     */
    protected static String regReplace(String line) {
        String tmp = line;
        for (String[] arr : PATTERN_WORD_MAPPING) {
            // URL 替换， 因为不能普通正则搞定 所以。。。
            tmp = URLMatch.match(tmp);

            // 标点符号替换， 普通正则不会写~ 奇怪的问题
            String[] cjkMarks = {"。", ",", "\"", "'", "：", "；", "？", "《", "》", "、", "?"};
            String[] cjkReplace = {"", "", "", "", "", "", "", "", "", "", ""};
            tmp = StringUtils.replaceEach(tmp, cjkMarks, cjkReplace);

            // 普通正则替换
            String word = arr[0];
            String reg = arr[1];
            tmp = tmp.replaceAll(reg, word);
        }
        // 删除

        return tmp;
    }

    protected static String regReplace(String line, String[][] regArray) {
        String tmp = line;
//        System.out.println("orginal String: " + tmp);
        for (String[] arr : regArray) {
            // 普通正则替换
            String word = arr[0];
            String reg = arr[1];
            tmp = tmp.replace(reg, word);
        }
        // 删除
//        System.out.println("out String: " + tmp);
        return tmp;
    }

    /**
     * 判断文件的编码格式
     *
     * @param fileName :file
     * @return 文件编码格式
     * @throws Exception
     */
    public static String codeString(String fileName) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(
                new FileInputStream(fileName));
        int p = (bin.read() << 8) + bin.read();
        String code = null;
        //其中的 0xefbb、0xfffe、0xfeff、0x5c75这些都是这个文件的前面两个字节的16进制数
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            case 0x5c75:
                code = "ANSI|ASCII";
                break;
            default:
                code = "GBK";
        }

        System.out.println(fileName + ": " + p + " # " + code);

        return code;
    }

    /**
     * @param iStr
     */
    public static void pythonUTF8Convert(String iStr, String charSet) {

        try {
            java.io.BufferedReader rd = new java.io.BufferedReader(
                    new java.io.InputStreamReader(new java.io.FileInputStream(
                            iStr), charSet));
            String line = null;
            java.io.PrintWriter wr = new java.io.PrintWriter(new java.io.File(
                    iStr));
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
                wr.println(line);
            }
            wr.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getUrlCategoryList(String originPath, String outputPath) {

        System.out.println(originPath + " zipWithUniqueCategory to File :" + outputPath + " Start!");
        File infile = new File(originPath);
        File outfile = new File(outputPath);
        HashMap<String, HashSet<String>> urlCat = new HashMap<>(0);

        try {
            List<String> inline = FileUtils.readLines(infile);
            List<String> outline = new ArrayList<>(0);
            for (int i = 0; i < inline.size(); i++) {
                String[] tt = inline.get(i).split(",");
                for (int j = 1; j < tt.length; j++) {
                    if (urlCat.containsKey(tt[j])) {
                        HashSet<String> mt = urlCat.get(tt[j]);
                        mt.add(tt[0]);
                        urlCat.put(tt[j], mt);
                    } else {
                        HashSet<String> mt = new HashSet<>();
                        mt.add(tt[0]);
                        urlCat.put(tt[j], mt);
                    }
                }
            }

            for (Map.Entry<String, HashSet<String>> entry : urlCat.entrySet()) {
                String catStr = "";
                for (String value : entry.getValue()) {
                    catStr = catStr + "," + value;
                }
                outline.add(entry.getKey() + catStr);

//                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
            FileUtils.writeLines(outfile, outline, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}