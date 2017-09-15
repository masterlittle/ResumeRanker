/**
 * Created by grofers on 13/09/17.
 */
public interface ConvertToTextFactory {

    ConvertToText getConverter(String filename, String keywordFile);
}
