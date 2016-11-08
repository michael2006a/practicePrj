/*
package com.trendmicro.skyaid.pdf;

*/
/**
 * Created by herbert_yin on 2016/10/19.
 *//*



import java.io.*;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.util.*;

public class PDFTest {

    public static void convertPDF2txt(File input, File output) {

        PDDocument pd;
        BufferedWriter wr;
        try {
//            File input = new File(inputStr);  // The PDF file from where you would like to extract
//            File output = new File(outputStr); // The text file where you are going to store the extracted data
            pd = PDDocument.load(input);
            System.out.println(pd.getNumberOfPages());
            System.out.println(pd.isEncrypted());
            pd.setAllSecurityToBeRemoved(true);
            pd.save("CopyOfInvoice.pdf"); // Creates a copy called "CopyOfInvoice.pdf"

            PDFTextStripper stripper = new PDFTextStripper();
//            stripper.setStartPage(3); //Start extracting from page 3
//            stripper.setEndPage(5); //Extract till page 5
            wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
            stripper.writeText(pd, wr);
            if (pd != null) {
                pd.close();
            }
            // I use close() to flush the stream.
            wr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String folderStr = "D:\\03datafolder\\diamond ring\\manual-pdf";
        String outFolder = "D:\\03datafolder\\diamond ring\\manual-txt";
        File mfolder = new File(folderStr);
        File[] filesArray = mfolder.listFiles();
        System.out.println(filesArray.length);

        for (int i = 0; i < filesArray.length; i++) {
            File inputPdf = filesArray[i];
            String outName = inputPdf.getName().replace(".pdf", ".txt");
            File outTxt = new File(outFolder.concat("\\").concat(outName));

            convertPDF2txt(inputPdf, outTxt);
        }

//        convertPDF2txt(new File("D:\\03datafolder\\diamond ring\\manual-pdf\\manual-51.pdf"), new File("D:\\03datafolder\\diamond ring\\manual-txt\\manual-51.txt"));


    }
}
*/
