package models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by grofers on 13/09/17.
 */
@Setter
@Builder
@Getter
public class ResumeScores {

    String filename;
    int score;
    List<String> matchedWords;

    @Override
    public String toString() {
        return filename + '\'' +
                ", " + score +
                ", " + matchedWords +
                "\n";
    }
}
