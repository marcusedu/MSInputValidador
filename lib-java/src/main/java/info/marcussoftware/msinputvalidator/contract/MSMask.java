package info.marcussoftware.msinputvalidator.contract;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 15/06/2018.
 */
public interface MSMask {
    String getCustomRegex();

    MSMask setCustomRegex(String customRegex);

    String getCustomMask();

    MSMask setCustomMask(String customMask);

    String getGroupTag();

    MSMask setGroupTag(String groupTag);

    String getMessageIfError();

    MSMask setMessageIfError(String messageIfError);

    int getDelay();

    MSMask setDelay(int delay);

    boolean isValidInput();

    String getUnmaskedText();

    MSMask setUnmaskedText(String unmaskedText);

    String getMaskedText();
}
