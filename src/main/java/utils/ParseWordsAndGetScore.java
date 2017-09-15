package utils;

import models.ResumeScores;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grofers on 13/09/17.
 */
public class ParseWordsAndGetScore {

    public static ResumeScores parse(List<String> wordList, String filename, String keywordFilePath) throws IOException {
        int score=0;
        List<String> matchedWords = new ArrayList<String>();

        List<String> keywords = FileUtils.readLines(new File(keywordFilePath), "UTF-8");
        for (int i=0; i < keywords.size(); i++) {
            if (wordList.contains(keywords.get(i).trim().toLowerCase())) {
                matchedWords.add(keywords.get(i));
                score += (keywords.size() - i);
            }
        }
        return ResumeScores.builder().filename(filename.substring(filename.lastIndexOf('/') + 1)).score(score).matchedWords(matchedWords).build();
    }
}
