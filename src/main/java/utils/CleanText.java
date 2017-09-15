package utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by grofers on 13/09/17.
 */
public class CleanText {

    public static List<String> removePunctuationsAndSplitWords(String text){
        return Arrays.asList(text.replaceAll("\\p{P}", " ").toLowerCase().trim().split("\\s+"));
    }

    public static String removePunctuations(String text){
        return text.replaceAll("\\p{P}", " ").trim();
    }

}
