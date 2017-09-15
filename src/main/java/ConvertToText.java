import models.ResumeScores;

import java.io.IOException;

/**
 * Created by grofers on 13/09/17.
 */
public interface ConvertToText {

    public ResumeScores extractText() throws IOException;
}
