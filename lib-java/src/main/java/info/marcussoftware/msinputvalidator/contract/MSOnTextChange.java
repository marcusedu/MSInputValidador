package info.marcussoftware.msinputvalidator.contract;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 13/06/2018.
 */
public interface MSOnTextChange {
    public void onTextChange(boolean isValid, String unmaskText, String maskedText);
}
