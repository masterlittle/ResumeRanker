import models.ResumeScores;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import utils.CleanText;
import utils.ParseWordsAndGetScore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by grofers on 13/09/17.
 */
public class DocToText implements ConvertToText {

    private String filename;
    private String keywordFilePath;

    private DocToText(String filename, String keywordFilePath) {
        this.filename = filename;
        this.keywordFilePath = keywordFilePath;
    }

    public static ConvertToTextFactory convertToText = DocToText::new;

    public ResumeScores extractText() throws IOException {
        FileInputStream fis;
        String text;
        if (filename.substring(filename.length() - 1).equals("x")) { //is a docx
            fis = new FileInputStream(new File(filename));
            XWPFDocument doc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            text = extractor.getText();
        } else { //is not a docx
            fis = new FileInputStream(new File(filename));
            HWPFDocument doc = new HWPFDocument(fis);
            WordExtractor extractor = new WordExtractor(doc);
            text = extractor.getText();
        }
        List<String> wordList = CleanText.removePunctuationsAndSplitWords(text);
        return ParseWordsAndGetScore.parse(wordList, filename, keywordFilePath);
    }

}
