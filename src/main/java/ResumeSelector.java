import models.ResumeScores;
import org.apache.commons.io.FileUtils;
import utils.UnzipUtility;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by grofers on 13/09/17.
 */
public class ResumeSelector {

    public static void main(String args[]) throws IOException {
        checkForRuntimeArguments(args);

        String zipFilePath = args[0];
        String keywordFile = args[1];

        String destDirectory = "/tmp";
        UnzipUtility unzipper = new UnzipUtility();
        List<String> filePaths = unzipper.unzip(zipFilePath, destDirectory);
        List<ResumeScores> resumeScores= filePaths.stream().map(filename -> {
            try {
                ConvertToText converter;
                if (filename.endsWith(".pdf")) {
                    converter = PdfToText.convertToText.getConverter(filename, keywordFile);
                } else if (filename.endsWith(".doc") || filename.endsWith(".docx")) {
                    System.out.println(filename);
                    converter = DocToText.convertToText.getConverter(filename, keywordFile);
                } else {
                    throw new IllegalArgumentException("There was a file whose extension is not pdf, dco or docx. Filename is " + filename);
                }
                return converter.extractText();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        })
        .filter(Objects::nonNull)
        .sorted((o1, o2) -> o2.getScore() - o1.getScore())
        .collect(Collectors.toList());

        FileUtils.writeLines(new File("Resume_Output.txt"), resumeScores);

        cleanFiles(filePaths);
    }

    private static void checkForRuntimeArguments(String args[]) {
        if (args[0] == null || args[0].isEmpty())
            throw new IllegalArgumentException("Specify proper zip path");
        if (args[1] == null || args[1].isEmpty())
            throw new IllegalArgumentException("Specify proper keyword file");
    }

    private static void cleanFiles(List<String> filePaths) {
        filePaths.forEach(filename -> FileUtils.deleteQuietly(new File(filename)));
    }

}
