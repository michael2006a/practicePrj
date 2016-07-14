package com.trendmicro.skyaid.common;

/**
 * Created by herbert_yin on 2016/6/12.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import org.apache.commons.lang3.StringUtils;
import scala.tools.nsc.backend.icode.Opcodes;

/**
 *
 */
public class ExcelOperater {
    public static boolean logFlag = true;

    public static void log(String mstr) {
        if (logFlag)
            System.out.println(mstr);
    }

    public static void readwriteXLS(String input, String output) {

        jxl.Workbook readwb = null;
        try {
            //构建Workbook对象, 只读Workbook对象
            //直接从本地文件创建Workbook
            InputStream instream = new FileInputStream(input);
            readwb = Workbook.getWorkbook(instream);

            //Sheet的下标是从0开始
            //获取第一张Sheet表
            Sheet readsheet = readwb.getSheet(0);
            //获取Sheet表中所包含的总列数
            int rsColumns = readsheet.getColumns();
            //获取Sheet表中所包含的总行数
            int rsRows = readsheet.getRows();
            //获取指定单元格的对象引用
            for (int i = 0; i < rsRows; i++) {
                for (int j = 0; j < rsColumns; j++) {
                    Cell cell = readsheet.getCell(j, i);
                    String tmp = cell.getContents();
                    String[] tt = tmp.split("\n");
                    System.out.println(cell.getContents() + "# split length: " + tt.length);
                }
                System.out.println();
            }

            //利用已经创建的Excel工作薄,创建新的可写入的Excel工作薄
            jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File(
                    output), readwb);
            //读取第一张工作表
            jxl.write.WritableSheet ws = wwb.getSheet(0);
            //获得第一个单元格对象
            jxl.write.WritableCell wc = ws.getWritableCell(0, 0);
            //判断单元格的类型, 做出相应的转化
//            if (wc.getType() == CellType.LABEL) {
//                Label l = (Label) wc;
//                l.setString("新姓名");
//            }

            //写入Excel对象
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readwb.close();
        }
    }

    public static void readwriteXLS(String input, String output, int columnIndex) {

        log("readwriteXLS " + input + " Start!");
        jxl.Workbook readwb = null;
        try {
            //构建Workbook对象, 只读Workbook对象
            //直接从本地文件创建Workbook
            InputStream instream = new FileInputStream(input);
            readwb = Workbook.getWorkbook(instream);

            //利用已经创建的Excel工作薄,创建新的可写入的Excel工作薄
            jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File(
                    output), readwb);

            //Sheet的下标是从0开始
            //获取第一张Sheet表
            //读取第一张工作表
            jxl.write.WritableSheet ws = wwb.getSheet(0);
//            Sheet readsheet = readwb.getSheet(0);
            //获取Sheet表中所包含的总列数
            int wsColumns = ws.getColumns();
            //获取Sheet表中所包含的总行数
            int wsRows = ws.getRows();
            //获取指定单元格的对象引用
            for (int i = 0; i < wsRows; i++) {
                for (int j = 0; j < wsColumns; j++) {
                    if (j == columnIndex) {
                        jxl.write.WritableCell cell = ws.getWritableCell(j, i);
                        cell = (modifyCellByCol(cell, j));
                    }
                }
//                log(String.valueOf(i));
                if (i % 1000 == 0) {
                    System.out.println("readwriteXLS " + input + " has completed Lines: " + i);
                }
            }
            //写入Excel对象
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readwb.close();
            log("readwriteXLS " + output + " End!");
        }
    }


    public static jxl.write.WritableCell modifyCellByCol(jxl.write.WritableCell mcell, int column) {
        jxl.write.WritableCell tmpCell = mcell;
        if (column == 4) {
            if (mcell.getType() == CellType.LABEL) {
                Label l = (Label) mcell;
                String modifiedStr = extralSupporterQue(mcell.getContents());
                l.setString(modifiedStr);
                tmpCell = l;
            }
        }
        return tmpCell;

    }

    /**
     * @param mStr
     * @return
     */
    public static String extralSupporterQue(String mStr) {
        String defaultStr = "SUPPORTER_ANS_NOT_FOUND";
        String regLable = "Connected to Instant Chat";
        String supportStr = ": Thank you for contacting Trend Micro";
        String supportSt2 = ": Thank you for contacting Trend Micro Chat Support, my name is";

        String[] mArray = mStr.split("\n");
        String supportName = "Default_Supporter_Name";
        int firstColon = Integer.MIN_VALUE;
        int secondColon = Integer.MIN_VALUE;
        boolean regLableFlag = false;
        for (int i = 0; i < mArray.length-1; i++) {
            if (mArray[i].contains(supportStr)&&supportName.equals("Default_Supporter_Name")) {
//                log(i + " : " + mArray[i]);
                regLableFlag = true;
                firstColon = mArray[i].indexOf(":");
                secondColon = mArray[i].indexOf(":", firstColon + 6);
                supportName = mArray[i].substring(firstColon + 6, secondColon);
            }
            if (regLableFlag && mArray[i + 1].contains(supportName.concat(":"))) {
                defaultStr = mArray[i + 1].substring(secondColon + 1);
                break;
            }
        }
        return defaultStr;
    }

    public static void main(String[] args) {
//        readwriteXLS("D:\\03datafolder\\data\\chatsample.xls", "D:\\03datafolder\\data\\chatsample2.xls");
        String gccChatXLS = "D:\\03datafolder\\data\\GCC-U Cons Chat Inbound Jan-Jun 2016.xls";
        String gccChatExtract = "D:\\03datafolder\\data\\GCCChatInbound2016.xls";
        readwriteXLS(gccChatXLS, gccChatExtract, 4);
//        readwriteXLS("D:\\03datafolder\\data\\chatsample.xls", "D:\\03datafolder\\data\\chatsample2.xls", 4);
    }
}