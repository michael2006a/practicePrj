package com.trendmicro.skyaid.pdftostandardhtml;

import java.io.*;

public class Convert {

    public static String pdf2htmlApi(String inputPDF, String outBase, int tranferType, float zoomFloat) {

        File iPDF = new File(inputPDF);


        String inputName = iPDF.getName();
        String outputPath = outBase + inputName.replace(".pdf", "-").concat(String.valueOf(tranferType)).concat("/");
        String outHTML = inputName.replace(".pdf", ".html");


        String outputFileName = outputPath.concat(outHTML);

        int type = tranferType;
        float zoom = zoomFloat;

        File file = new File(outputPath);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        } else {
            return inputPDF;
        }

//        File files = new File("C:\\Directory2\\Sub2\\Sub-Sub2");
//        if (!files.exists()) {
//            if (files.mkdirs()) {
//                System.out.println("Multiple directories are created!");
//            } else {
//                System.out.println("Failed to create multiple directories!");
//            }
//        }

        try {
            HtmlFile htmlFile = new HtmlFile(outputFileName, type, zoom);
            htmlFile.convertPdfToHtml(inputPDF, outputPath);
            htmlFile.closeFile();
        } catch (Exception e) {
            System.err.println("Filed to convert Pdf to Html.");
            e.printStackTrace();
        }
        System.out.println("Done");
        return inputPDF;

    }


    public static void main(String[] args) {


//        pdf2htmlApi("D:/03datafolder/diamond ring/manual-pdf/manual-1005.pdf","D:/03datafolder/diamond ring/manual-html/",1,1.0f);

        String folderStr = "D:/03datafolder/diamond ring/manual-pdf2/";
        String outFolder = "D:/03datafolder/diamond ring/manual-html/";
        File mfolder = new File(folderStr);
        File[] filesArray = mfolder.listFiles();
        System.out.println(filesArray.length);

        for (int i = 0; i < filesArray.length; i++) {
            String inputPdf = filesArray[i].getAbsolutePath();

            System.out.println(pdf2htmlApi(inputPdf, outFolder, 0, 1.0f) + " has been transferred!");
//            try {
//                Thread.sleep(1000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

//		if (args.length != 4) {
//			System.out.println("Invalid arguments");
//			System.out.println("Use: java -jar PDF-to-standard-HTML.jar path/to/pdf/file.pdf path/to/output/file.html type zoom");
//			System.out.println("type is an int:");
//			System.out.println("0 for the simplest method");
//			System.out.println("1 to group letters by word");
//			System.out.println("2 to group letters by line");
//			System.out.println("3 to group letters by line using a cache");
//			System.exit(1);
//		}
//
//
//		String pdfFileName = args[0];
//
//		String outputFileName =  args[1];
//		int type =  Integer.parseInt(args[2]);
//		float zoom = Float.parseFloat(args[3]);
//
//		try {
//			HtmlFile htmlFile = new HtmlFile(outputFileName, type, zoom);
//			htmlFile.convertPdfToHtml(pdfFileName);
//			htmlFile.closeFile();
//		} catch (Exception e) {
//            System.err.println( "Filed to convert Pdf to Html." );
//			e.printStackTrace();
//		}
//		System.out.println("Done");
    }
}
