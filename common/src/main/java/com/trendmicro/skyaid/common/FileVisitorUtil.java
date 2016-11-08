package com.trendmicro.skyaid.common;

/**
 * Created by herbert_yin on 2016/9/6.
 */

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileVisitorUtil {
    public static void main(String[] args) throws IOException {

        Path startingDir = Paths.get("D:\\03datafolder\\Tombo\\01_VBC");

        FindFileVisitor findJavaVisitor = new FindFileVisitor(".txt");

        Files.walkFileTree(startingDir, findJavaVisitor);

        for (String name : findJavaVisitor.getFilenameList()) {
//            System.out.println(getFileName(name));
            System.out.println(ReplaceUtil.regReplace(getFileName(name).toUpperCase(),ReplaceUtil.PATTERN_TOMBO_MAPPING,true));
//            System.out.println(ReplaceUtil.regReplace(getFileName(name).toUpperCase(),ReplaceUtil.PATTERN_TOMBO_MAPPING,true).replaceAll("^1.", "").replaceAll("^2.","").replaceAll("^3.",""));
        }
    }


    public static String getFileName(String pathName){
        String[] nameArray = pathName.split("\\\\");
        return nameArray[nameArray.length - 1];
    }
}

class FindFileVisitor extends SimpleFileVisitor<Path> {

    private List<String> filenameList = new ArrayList<String>();

    private String fileSuffix = null;

    public FindFileVisitor(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

        if (file.toString().endsWith(fileSuffix)) {
            filenameList.add(file.toString());
        }
        return FileVisitResult.CONTINUE;
    }

    public List<String> getFilenameList() {
        return filenameList;
    }

    public void setFilenameList(List<String> filenameList) {
        this.filenameList = filenameList;
    }


}