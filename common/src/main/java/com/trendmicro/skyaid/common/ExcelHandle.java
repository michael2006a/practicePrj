package com.trendmicro.skyaid.common;

import jxl.*;
import jxl.format.UnderlineStyle;
import jxl.read.biff.RowRecord;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.Boolean;
import jxl.Cell;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by herbert_yin on 2016/6/12.
 */

public class ExcelHandle {

    public ExcelHandle() {

    }

    /**
     * 读取Excel
     *
     * @param filePath
     */
    public static void readExcel(String filePath) {

        try {

            InputStream is = new FileInputStream(filePath);

            Workbook rwb = Workbook.getWorkbook(is);

            //这里有两种方法获取sheet表:名字和下标（从0开始）

            //Sheet st = rwb.getSheet("original");

            Sheet st = rwb.getSheet(0);

            /**

             //获得第一行第一列单元的值
             Cell c00 = st.getCell(0,0);

             //通用的获取cell值的方式,返回字符串
             String strc00 = c00.getContents();

             //获得cell具体类型值的方式
             if(c00.getType() == CellType.LABEL)
             {
             LabelCell labelc00 = (LabelCell)c00;
             strc00 = labelc00.getString();
             }

             //输出
             System.out.println(strc00);*/

            //Sheet的下标是从0开始
            //获取第一张Sheet表
            Sheet rst = rwb.getSheet(0);

            //获取Sheet表中所包含的总列数
            int rsColumns = rst.getColumns();

            //获取Sheet表中所包含的总行数
            int rsRows = rst.getRows();

            //获取指定单元格的对象引用
            for (int i = 0; i < rsRows; i++) {
                for (int j = 0; j < rsColumns; j++) {
                    Cell cell = rst.getCell(j, i);
                    System.out.print(cell.getContents() + " ");
                }
                System.out.println();
            }

            //关闭
            rwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出Excel
     *
     * @param os
     */
    public static void writeExcel(OutputStream os) {
        try {
            /** 只能通过API提供的 工厂方法来创建Workbook，而不能使用WritableWorkbook的构造函数，因为类WritableWorkbook的构造函数为 protected类型：方法一：直接从目标文件中读取 WritableWorkbook wwb = Workbook.createWorkbook(new File(targetfile));方法 二：如下实例所示 将WritableWorkbook直接写入到输出流*/

            WritableWorkbook wwb = Workbook.createWorkbook(os);

            //创建Excel工作表 指定名称和位置
            WritableSheet ws = wwb.createSheet("Test Sheet 1", 0);

            /**************往工作表中添加数据*****************/

            //1.添加Label对象

            Label label = new Label(0, 0, "测试");

            ws.addCell(label);

            //添加带有字型Formatting对象

            WritableFont wf = new WritableFont(WritableFont.TIMES, 18, WritableFont.BOLD, true);

            WritableCellFormat wcf = new WritableCellFormat(wf);

            Label labelcf = new Label(1, 0, "this is a label test", wcf);

            ws.addCell(labelcf);

            //添加带有字体颜色的Formatting对象

            WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,

                    UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.DARK_YELLOW);

            WritableCellFormat wcfFC = new WritableCellFormat(wfc);

            Label labelCF = new Label(1, 0, "Ok", wcfFC);

            ws.addCell(labelCF);


            //2.添加Number对象

            Number labelN = new Number(0, 1, 3.1415926);

            ws.addCell(labelN);

            //添加带有formatting的Number对象

            NumberFormat nf = new NumberFormat("#.##");

            WritableCellFormat wcfN = new WritableCellFormat(nf);

            Number labelNF = new jxl.write.Number(1, 1, 3.1415926, wcfN);

            ws.addCell(labelNF);


            //3.添加Boolean对象

            Boolean labelB = new jxl.write.Boolean(0, 2, true);

            ws.addCell(labelB);

            Boolean labelB1 = new jxl.write.Boolean(1, 2, false);

            ws.addCell(labelB1);

            //4.添加DateTime对象

            jxl.write.DateTime labelDT = new jxl.write.DateTime(0, 3, new java.util.Date());

            ws.addCell(labelDT);


            //5.添加带有formatting的DateFormat对象

            DateFormat df = new DateFormat("dd MM yyyy hh:mm:ss");

            WritableCellFormat wcfDF = new WritableCellFormat(df);

            DateTime labelDTF = new DateTime(1, 3, new java.util.Date(), wcfDF);

            ws.addCell(labelDTF);

            //6.添加图片对象,jxl只支持png格式图片

            File image = new File("f:\\1.png");

            WritableImage wimage = new WritableImage(0, 4, 6, 17, image);

            ws.addImage(wimage);

            //7.写入工作表

            wwb.write();

            wwb.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将file1拷贝后,进行修改并创建输出对象file2
     * 单元格原有的格式化修饰不能去掉，但仍可将新的单元格修饰加上去，
     * 以使单元格的内容以不同的形式表现
     *
     * @param file1
     * @param file2
     */
    public static void modifyExcel(File file1, File file2) {
        try {
            Workbook rwb = Workbook.getWorkbook(file1);
            WritableWorkbook wwb = Workbook.createWorkbook(file2, rwb);//copy

            WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);

            WritableCellFormat wcfFC = new WritableCellFormat(wfc);
            WritableSheet ws = wwb.getSheet(0);
            WritableCell wc = ws.getWritableCell(0, 0);

            //判断单元格的类型,做出相应的转换
            if (wc.getType() == CellType.LABEL) {
                Label labelCF = new Label(0, 0, "人物（新）", wcfFC);
                ws.addCell(labelCF);
                //Label label = (Label)wc;
                //label.setString("被修改");
            }
            wwb.write();
            wwb.close();
            rwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param orginalFile
     * @param modifiedFile
     */
    public static void modifyGccXLS(File orginalFile, File modifiedFile) {
        try {
            Workbook rwb = Workbook.getWorkbook(orginalFile);

            WritableWorkbook wwb = Workbook.createWorkbook(modifiedFile, rwb);//copy

            WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,

                    UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);

            WritableCellFormat wcfFC = new WritableCellFormat(wfc);

            WritableSheet ws = wwb.getSheet(0);

            //获取Sheet表中所包含的总列数
            int rsColumns = ws.getColumns();
            //获取Sheet表中所包含的总行数
            int rsRows = ws.getRows();

            System.out.println(orginalFile + "lines: " + rsRows);
            //获取指定单元格的对象引用
            for (int i = 0; i < rsRows; i++) {
                for (int j = 2; j < rsColumns; j++) {
                    Cell cell = ws.getWritableCell(j, i);
//                System.out.println("OriginalRow " + i + ": " + cell.getContents() + " ");
//                System.out.println("ReplacedRow " + i + ": " + cell.getContents().replaceAll("[\\t\\n\\r]", "").replaceAll("[\\pP‘’“”]", ""));
                    //判断单元格的类型, 做出相应的转化
                    if (cell.getType() == CellType.LABEL) {
                        Label l = (Label) cell;
                        l.setString(DataFormatUtil.regReplace(cell.getContents()).replaceAll("[\\t\\n\\r]", "。"));
//                        l.setString(regReplace(cell.getContents()).replaceAll("[\\n\\r]", ""));
                    }
                }
                if (i % 1000 == 0)
                    System.out.println(i + " lines completed!");
            }

            wwb.write();
            wwb.close();
            rwb.close();
            System.out.println(modifiedFile + "has been created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param orginalFile
     * @param modifiedFile
     */
    public static void extractGccXLS(File orginalFile, File modifiedFile, HashMap<Integer, String> filterMap, boolean keepFlag) {
        try {
            boolean cellFlag = true;

            Workbook rwb = Workbook.getWorkbook(orginalFile);

            WritableWorkbook wwb = Workbook.createWorkbook(modifiedFile, rwb);//copy

            WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,

                    UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);

            WritableCellFormat wcfFC = new WritableCellFormat(wfc);

            WritableSheet ws = wwb.getSheet(0);

            //获取Sheet表中所包含的总列数
            int rsColumns = ws.getColumns();
            //获取Sheet表中所包含的总行数
            int rsRows = ws.getRows();

            System.out.println(orginalFile + "lines: " + rsRows);
            //获取指定单元格的对象引用
            for (int i = 0; i < rsRows; i++) {

                for (Map.Entry<Integer, String> entry : filterMap.entrySet()) {
                    Integer key = entry.getKey();
                    String value = entry.getValue();

                    Cell cell = ws.getWritableCell(key, i);
                    Label tmpcell = (Label) cell;
                    if (!cell.getContents().equalsIgnoreCase(value)) {
                        cellFlag = false;
                        break;
                    }

//                    System.out.println("key =" + key + " value = " + value);
                }

                for (int j = 0; j < rsColumns; j++) {
                    Cell cell = ws.getWritableCell(j, i);
//                System.out.println("OriginalRow " + i + ": " + cell.getContents() + " ");
//                System.out.println("ReplacedRow " + i + ": " + cell.getContents().replaceAll("[\\t\\n\\r]", "").replaceAll("[\\pP‘’“”]", ""));
                    //判断单元格的类型, 做出相应的转化
                    if (cell.getType() == CellType.LABEL) {
                        Label l = (Label) cell;
                        l.setString(DataFormatUtil.regReplace(cell.getContents()).replaceAll("[\\t\\n\\r]", "。"));
//                        l.setString(regReplace(cell.getContents()).replaceAll("[\\n\\r]", ""));
                    }
                }
                if (i % 1000 == 0)
                    System.out.println(i + " lines completed!");
            }

            wwb.write();
            wwb.close();
            rwb.close();
            System.out.println(modifiedFile + "has been created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试
    public static void main(String args[]) {
//        try {
            //读EXCEL
//            ExcelHandle.readExcel("D:/3collom.xls");

            //输出EXCEL
//            File filewrite = new File("F:/红楼人物2.xls");
//            filewrite.createNewFile();
//            OutputStream os = new FileOutputStream(filewrite);
//            ExcelHandle.writeExcel(os);

            //修改EXCEL
//            ExcelHandle.modifyExcel(new File("F:/红楼人物.xls"), new File("F:/红楼人物3.xls"));

//            ExcelHandle.modifyGccXLS(new File("D:\\taskArchieve\\trainoriginal.xls"),new File("D:\\trainModified2.xls"));

//            ExcelHandle.modifyGccXLS(new File("D:\\taskArchieve\\gcc2015.xls"), new File("D:\\gcc2015modified2.xls"));



//            for (int i = 1; i <= 1; i++) {
//                String inputBase = "E:\\GCCData\\2015";
//                String outputBase = "E:\\GCCData\\round1\\1round2015";
//                String appendStr = "";
//                if (i < 10) {
//                    appendStr = "0".concat(String.valueOf(i)).concat(".xls");
//                } else {
//                    appendStr = String.valueOf(i).concat(".xls");
//                }
//
//                inputBase = inputBase.concat(appendStr);
//                outputBase = outputBase.concat(appendStr);
//                ExcelHandle.modifyGccXLS(new File(inputBase), new File(outputBase));
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //Java 简单高效处理字符串-删除所有标点
        //http://blog.csdn.net/welcome000yy/article/details/7824429
        String str = ",.!?？，，D_NAME。！；‘’”“《》**dfs  #$%^&()-+1431" +
                "221中国123漢字かどうかのjavaを決定";
        System.out.println(str);
        String str1 = str.replaceAll("[\\‘’“”]", "");
        System.out.println(str1);

        String str21 = str.replace("[\\(]", "");
        System.out.println(str21);

        String str2 = str.replaceAll("[\\()]", "");
        System.out.println(str2);

        String str3 = str.replaceAll("[\\pC]", "");
        System.out.println("str3: "+str3);

        String tttt = "aaaa\n\rbbb";
        System.out.println(tttt);

    }

}
/*

String str = ",.!，，D_NAME。！；‘’”“《》**dfs  #$%^&()-+1431221中国123漢字かどうかのjavaを決定";
str = str.replaceAll("[\\pP‘’“”]", "");
System.out.println(str);

Unicode 编码并不只是为某个字符简单定义了一个编码，而且还将其进行了归类。

        \pP 其中的小写 p 是 property 的意思，表示 Unicode 属性，用于 Unicode 正表达式的前缀。

        大写 P 表示 Unicode 字符集七个字符属性之一：标点字符。

        其他六个是

        L：字母；
        M：标记符号（一般不会单独出现）；
        Z：分隔符（比如空格、换行等）；
        S：符号（比如数学符号、货币符号等）；
        N：数字（比如阿拉伯数字、罗马数字等）；
        C：其他字符

        上面这七个是属性，七个属性下还有若干个子属性，用于更进一步地进行细分。

        Java 中用于 Unicode 的正则表达式数据都是由 Unicode 组织提供的。

        Unicode 正则表达式标准（可以找到所有的子属性）
        http://www.unicode.org/reports/tr18/

        各 Unicode 字符属性的定义，可以用一看看某个字符具有什么属性。
        http://www.unicode.org/Public/UNIDATA/UnicodeData.txt

        这个文本文档一行是一个字符，第一列是 Unicode 编码，第二列是字符名，第三列是 Unicode 属性，
        以及其他一些字符信息。


1)replace的参数是char和CharSequence,即可以支持字符的替换,也支持字符串的替换(CharSequence即字符串序列的意思,说白了也就是字符串);
2)replaceAll的参数是regex,即基于规则表达式的替换,比如,可以通过replaceAll("\\d", "*")把一个字符串所有的数字字符都换成星号;
相同点:
都是全部替换,即把源字符串中的某一字符或字符串全部换成指定的字符或字符串,如果只想替换第一次出现的,可以使用 replaceFirst(),这个方法也是基于规则表达式的替换,但与replaceAll()不同的是,只替换第一次出现的字符串;
另外,如果replaceAll()和replaceFirst()所用的参数据不是基于规则表达式的,则与replace()替换字符串的效果是一样的,即这两者也支持字符串的操作;
还有一点注意:
执行了替换操作后,源字符串的内容是没有发生改变的.
        */
