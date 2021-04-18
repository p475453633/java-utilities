package com.crisp.utilities;

import java.io.*;

public class WordSearch {

    private static BufferedReader br;
    private static BufferedWriter bw;

    /**
     * read file
     * @param filePath
     * @return
     * @throws IOException
     */
    public static BufferedReader readFile(String filePath) throws IOException {
        File file = new File(filePath);
        return new BufferedReader(new FileReader(file));
    }

    /**
     * write new file
     * @param filePath
     * @return
     * @throws IOException
     */
    public static BufferedWriter writeFile(String filePath) throws IOException{
        File file = new File(filePath);
        String parentPathNew;
        String fileName;
        if (file.exists()){
            // write new file in parentPath_new/fileName
            parentPathNew = file.getParent() + "_new";
            File parentPathNewFile = new File(parentPathNew);
            if(!parentPathNewFile.exists()){
                parentPathNewFile.mkdirs();
            }
            fileName = file.getName();
            file = new File(parentPathNew + "/"+fileName);
        }
        return new BufferedWriter(new FileWriter(file));
    }

    /**
     * replace file with new string
     * @param filePath
     * @param matchStrs
     * @param newStr
     * @throws IOException
     */
    public static void replaceFile(String filePath, String[] matchStrs, String newStr, boolean isReplace) throws IOException{
        if(isReplace){
            // replace strings in matchStrs with newStr
            br = readFile(filePath);
            bw = writeFile(filePath);
            String line;
            while ((line = br.readLine()) != null){
                // find file which have string reg
                for (String matchStr : matchStrs) {
                    if(line.indexOf(matchStr) != -1){
                        String format = String.format("Path have find %s: ", matchStr);
                        System.out.println(format + filePath);
                    }
                }
//                replace file's reg with newStr
                for (String reg : matchStrs) {
                    line = line.replaceAll(reg, newStr);
                }
                bw.write(line);
                bw.newLine();
                bw.flush();
            }
        } else{
            // search matchStr
            replaceFile(filePath, matchStrs);
        }
        // close br and bw
        if(br != null){
            br.close();
        }
        if(bw != null){
            bw.close();
        }
    }

    /**
     * replace file with new string
     * @param filePath
     * @param matchStrs
     * @throws IOException
     */
    public static void replaceFile(String filePath, String[] matchStrs) throws IOException{
        br = readFile(filePath);
        String line;
        while ((line = br.readLine()) != null){
            // find file which have string reg
            for (String matchStr : matchStrs) {
                if(line.indexOf(matchStr) != -1){
                    String format = String.format("Path have find %s: ", matchStr);
                    System.out.println(format + filePath);
                }
            }
        }
    }

    /**
     * process the directory
     * @param dirPath
     * @param fileFormatNeed
     * @param matchStrs
     * @param newStr
     * @throws IOException
     */
    public static void processDirectory(String dirPath, String fileFormatNeed, String[] matchStrs, String newStr, boolean isReplace) throws IOException{
        File file = new File(dirPath);
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File file1 : files) {
                processDirectory(file1.getAbsolutePath(), fileFormatNeed, matchStrs, newStr, isReplace);
            }
        } else{
            if(file.getAbsolutePath().endsWith(fileFormatNeed)){
                String filePath = file.getAbsolutePath();
                replaceFile(filePath, matchStrs, newStr, isReplace);
            }
        }
    }


    public static void main(String[] args) throws IOException{
        String[] filePaths = new String[]{
                "./utilities",
        };
        String[] sensitiveStrings = {
                "sensitive1",
                "sensitive2"
        };
        for (String filePath : filePaths) {
//            processDirectory(filePath,".txt", sensitiveStrings, "****", false);
            processDirectory(filePath,".txt", sensitiveStrings, "****", true);
        }
    }
}
