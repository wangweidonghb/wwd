package com.wwd.file.util;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @author wwd
 * 提取两个目录中差异的文件。该类为文件提供工具类
 */
public class DifferentFileExtractionUtil {

    /**
     *
     * @param baseDirectory  基准文件目录， 里面文件为基准集
     * @param fullFileDirectory  全文件目录， 里面文件为全集
     * @param outputDirectory  补集文件输出目录，  Cu'baseDirectory' = {x| x∈fullFileDirectory, 且x∉baseDirectory}
     */
    public static void extractDifferentFile(String baseDirectory, String fullFileDirectory, String outputDirectory) throws IOException {

        if (baseDirectory == null || baseDirectory.length() == 0 || fullFileDirectory == null || fullFileDirectory.length() == 0 || outputDirectory == null || outputDirectory.length() == 0) {
            throw new IllegalArgumentException("File path can not be null");
        }

        File baseDir = openExistDirectory(baseDirectory);
        File fullDir = openExistDirectory(fullFileDirectory);
        File outputDir = openExistDirectory(outputDirectory);

        List<String> baseDirFiles = recursiveListFiles(baseDir);
        List<String> fullDirFiles = recursiveListFiles(fullDir);
        List<String> complementFiles = complement(baseDirFiles, fullDirFiles);

        if (complementFiles != null) {
            for (String complementFile : complementFiles) {
                copyFile(fullFileDirectory, complementFile, outputDirectory);
            }
        }

    }

    private static void copyFile(String contextDir, String filepath, String outputContext) throws IOException {

        // 获取需要的路径
        File file = new File(filepath);
        String contextAbsDir = openExistDirectory(contextDir).getAbsolutePath();
        String fileAbsPath = file.getAbsolutePath();
        String fileRelativePath = fileAbsPath.substring(contextAbsDir.length() + 1);
        String fileName = file.getName();
        String outputAbsDir = outputContext + File.separator + fileRelativePath.substring(0, fileRelativePath.indexOf(fileName));

        // 创建目录
        File outputAbsDirFile = new File(outputAbsDir);
        if (!outputAbsDirFile.exists()) {
            outputAbsDirFile.mkdirs();
        }

        realCopyFile(filepath, outputAbsDir + File.separator + fileName);

    }

    private static void realCopyFile(String sourceFilePath, String targetFilePath) throws IOException {

        if (sourceFilePath == null || targetFilePath == null) {
            return ;
        }

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(sourceFilePath));
            bos = new BufferedOutputStream(new FileOutputStream(targetFilePath));

            byte[] cache = new byte[8192];
            int length = -1;
            while ((length = bis.read(cache)) != -1) {
                bos.write(cache, 0, length);
            }

        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }

                if (bos != null) {
                    bos.close();
                }
            } catch (Exception ex) {
                System.out.println("Close input stream or output stream failed, reason: " + ex.getMessage());
            }
        }
    }


    private static List<String> complement(List<String> basesFiles, List<String> fullFiles) {
        if (basesFiles == null || fullFiles == null) {
            return null;
        }
        List<String> complementFiles = new ArrayList<String> ();
        for (String fullFile : fullFiles) {
            if (!basesFiles.contains(fullFile)) {
                complementFiles.add(fullFile);
            }
        }

        return complementFiles;
    }

    /**
     *
     * @param dir
     * @return
     */
    public static List<String> recursiveListFiles(String dir) {
        return recursiveListFiles(openExistDirectory(dir));
    }

    /**
     * 递归列举文件目录下所有文件, 返回的列表为文件的绝对路径
     * @param dir
     * @return
     */
    public static List<String> recursiveListFiles(File dir) {

        if (dir == null) {
            return null;
        }

        List<String> filePaths = new ArrayList<String>();

        if (!dir.isDirectory()) {
            filePaths.add(dir.getAbsolutePath());
        } else {
            Queue<File> subdirs = new LinkedTransferQueue<File>();
            subdirs.offer(dir);
            while(subdirs.size() > 0) {
                dir = subdirs.poll();
                File[] children = dir.listFiles();
                if (children != null && children.length !=0) {
                    for (File child : children) {
                        if (child.isFile()) {
                            filePaths.add(child.getAbsolutePath());
                        } else {
                            subdirs.offer(child);
                        }
                    }
                }
            }
        }

        return filePaths;
    }

    private static File openExistDirectory(String filepath) {

        File f = new File(filepath);

        if (!f.exists() || !f.isDirectory()) {
            throw new IllegalArgumentException("File path must be directory");
        }
        return f;
    }
}
