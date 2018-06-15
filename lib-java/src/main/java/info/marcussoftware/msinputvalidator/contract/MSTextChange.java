package info.marcussoftware.msinputvalidator.contract;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 15/06/2018.
 */
public interface MSTextChange extends MSMask {
    boolean addMSOnTextChange(MSOnTextChange msOnTextChange);

    boolean removeMSOnTextChenge(MSOnTextChange msOnTextChange);
}
