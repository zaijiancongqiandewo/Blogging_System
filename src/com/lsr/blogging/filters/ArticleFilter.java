package com.lsr.blogging.filters;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleFilter {

    private static final String replaceWord = "*";
    private static List<String> sensitiveWords;

    static {
        sensitiveWords = readSensitiveWordsFromFile("resources/min_word.txt");
    }

    public static String filterSensitiveWords(String text) {
        String regex = String.join("|", sensitiveWords);
        Pattern pattern = Pattern.compile(regex);//将正则表达式模式编译成一个 Pattern 对象，以便后续的匹配操作。
        Matcher matcher = pattern.matcher(text);//创建一个 Matcher 对象，用于在给定的文本 text 中进行匹配操作。
        StringBuffer sb = new StringBuffer();//创建一个 StringBuffer 对象，用于存储过滤后的文本
        while (matcher.find()) {
            String word = matcher.group();
            matcher.appendReplacement(sb, repeatReplaceChar(word.length()));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String repeatReplaceChar(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(replaceWord);
        }
        return sb.toString();
    }

    private static List<String> readSensitiveWordsFromFile(String filePath) {
        List<String> words = new ArrayList<>();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 将读取的每一行内容去除前后空格后，按逗号分割成字符串数组
                String[] sensitiveWords = line.trim().split("，"); // 使用中文逗号（全角逗号）

                // 遍历分割后的敏感词数组，并将每个敏感词添加到字符串列表中
                for (String word : sensitiveWords) {
                    words.add(word.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

//    public static void main(String[] args) {
//        String text = "这是一段包含敏感词的文本，敏感词1和敏感词2都在其中。";
//        String filteredText = filterSensitiveWords(text);
//        System.out.println(filteredText);
//    }
}