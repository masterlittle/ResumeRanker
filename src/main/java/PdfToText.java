import models.ResumeScores;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import utils.CleanText;
import utils.ParseWordsAndGetScore;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by grofers on 13/09/17.
 */
public class PdfToText implements ConvertToText {

    private String filename;
    private String keywordFilePath;

    private PdfToText(String filename, String keywordFilePath) {
        this.filename = filename;
        this.keywordFilePath = keywordFilePath;
    }

    public static ConvertToTextFactory convertToText = PdfToText::new;


    @Override
    public ResumeScores extractText() throws IOException {
        PDFTextStripper pdfStripper;
        PDDocument document = PDDocument.load(new File(filename));
        pdfStripper = new PDFTextStripper();
        String parsedText = pdfStripper.getText(document);
        document.close();
        List<String> wordList = CleanText.removePunctuationsAndSplitWords(parsedText);
        return ParseWordsAndGetScore.parse(wordList, filename, keywordFilePath);
    }

}
