package com.wwd.file;

import com.wwd.file.util.DifferentFileExtractionUtil;

import java.io.IOException;

/**
 * 程序主入口，程序用以抽取出两个目录的补集文件至输出目录中。
 */
public class DiffFileExtactMain {

    public static void main(String[] args) throws IOException {

        String baseDir = "C:\\Users\\User\\Desktop\\repo_default";
        String fullDir = "C:\\Users\\User\\Desktop\\repo";
        String outputDir = "C:\\Users\\User\\Desktop\\output";

        if (args != null && args.length >= 3) {
            baseDir = args[0];
            fullDir = args[1];
            outputDir = args[2];
        }

        DifferentFileExtractionUtil.extractDifferentFile(baseDir, fullDir, outputDir);
    }
}
