package info.marcussoftware.msinputvalidator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;

import info.marcussoftware.msinputvalidator.R;
import info.marcussoftware.msinputvalidator.contract.MSOnTextChange;
import info.marcussoftware.msinputvalidator.contract.MSTextChange;
import info.marcussoftware.msinputvalidator.utils.Mask;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 13/06/2018.
 */
public class MSEditText extends AppCompatEditText implements MSTextChange {
    private final String TAG = "MSEditText";
    private String customRegex;
    private String customMask;
    private String unmaskedText = "";
    private String maskedText = "";
    private String groupTag;
    private String messageIfError;
    private int delay;
    private boolean validInput = false;
    private info.marcussoftware.msinputvalidator.utils.Mask mMaskHelper;
    private ArrayList<MSOnTextChange> msOnTextChangeArrayList = new ArrayList<>();

    private boolean isMyChange = false;
    private Runnable postError;
    private Handler mHandler;

    public MSEditText(Context context) {
        super(context);
    }

    public MSEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(attrs);
    }

    public MSEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(attrs);
    }

    private void init() {
    }

    private void initAttrs(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attributeSet
                , R.styleable.MSEditText
                , 0
                , 0);
        try {
//            setCustomMask(typedArray.getString(R.styleable.MSEditText_mask));
            setCustomMask(typedArray.getString(R.styleable.MSEditText_customMask));
//            setCustomRegex(typedArray.getString(R.styleable.MSEditText_regex));
            setDelay(typedArray.getInt(R.styleable.MSEditText_delayInMilli, 0));
            setCustomRegex(typedArray.getString(R.styleable.MSEditText_customRegex));
            setGroupTag(typedArray.getString(R.styleable.MSEditText_groupTag));
            setMessageIfError(typedArray.getString(R.styleable.MSEditText_messageIfError));
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public String getCustomRegex() {
        return customRegex;
    }

    @Override
    public MSEditText setCustomRegex(String customRegex) {
        Log.d(TAG, "setCustomRegex: " + customRegex);
        this.customRegex = customRegex;
        return this;
    }

    @Override
    public String getCustomMask() {
        return customMask;
    }

    @Override
    public MSEditText setCustomMask(String customMask) {
        Log.d(TAG, "setCustomMask: " + customMask);
        this.customMask = customMask;
        if (mMaskHelper != null && !getUnmaskedText().isEmpty())
            unmaskedText = this.mMaskHelper.removeMask(getUnmaskedText());
        this.mMaskHelper = new Mask(this.customMask);
        applyMask(getUnmaskedText());
        return this;
    }

    @Override
    public String getGroupTag() {
        return groupTag;
    }

    @Override
    public MSEditText setGroupTag(String groupTag) {
        Log.d(TAG, "setGroupTag: " + groupTag);
        this.groupTag = groupTag;
        return this;
    }

    @Override
    public String getMessageIfError() {
        return messageIfError;
    }

    @Override
    public MSEditText setMessageIfError(String messageIfError) {
        Log.d(TAG, "setMessageIfError: " + messageIfError);
        this.messageIfError = messageIfError;
        return this;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public MSEditText setDelay(int delay) {
        Log.d(TAG, "setDelay: " + delay);
        this.delay = delay;
        if (delay > 0) mHandler = new Handler();
        return this;
    }

    @Override
    public boolean isValidInput() {
        return validInput;
    }

    @Override
    public String getUnmaskedText() {
        return unmaskedText;
    }

    @Override
    public MSEditText setUnmaskedText(String unmaskedText) {
        Log.d(TAG, "setUnmaskedText: " + unmaskedText);
        this.unmaskedText = unmaskedText;
        setText(unmaskedText);
        return this;
    }

    @Override
    public String getMaskedText() {
        return maskedText;
    }

    @Override
    public boolean addMSOnTextChange(MSOnTextChange msOnTextChange) {
        return msOnTextChangeArrayList.add(msOnTextChange);
    }

    @Override
    public boolean removeMSOnTextChenge(MSOnTextChange msOnTextChange) {
        return msOnTextChangeArrayList.remove(msOnTextChange);
    }

    private void notifyTextChange() {
        for (MSOnTextChange m : msOnTextChangeArrayList) {
            m.onTextChange(isValidInput(), getUnmaskedText(), getMaskedText());
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (isMyChange) {
            isMyChange = false;
        } else if (lengthBefore < lengthAfter) {
            maskedText = applyMask(text);
        } else if (getUnmaskedText() != null && getUnmaskedText().length() > 1) {
            maskedText = applyMask(unmaskedText.subSequence(0, unmaskedText.length() - 1));
        }
        super.onTextChanged(getMaskedText(), start, lengthBefore, lengthAfter);
    }

    private void validate(String text) {
        if (getCustomRegex() == null) return;
        validInput = text.matches(getCustomRegex());
        if (getDelay() > 0 && mHandler != null) {
            mHandler.removeCallbacks(postError);
            postError = new Runnable() {
                @Override
                public void run() {
                    setError();
                }
            };
            mHandler.postDelayed(postError, getDelay());
        } else {
            setError();
        }
    }

    private void setError() {
        setError(validInput ? null : getMessageIfError());
    }

    private String applyMask(CharSequence text) {
        if (text == null || mMaskHelper == null || text.length() == 0) return "";
        unmaskedText = mMaskHelper.removeMask(text.toString());
        isMyChange = true;
        validate(getUnmaskedText());
        maskedText = mMaskHelper.applyMask(getUnmaskedText());
        setText(getMaskedText());
        setSelection(getMaskedText().length());
        notifyTextChange();
        return getMaskedText();
    }

/*    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        super.addTextChangedListener(watcher);
    }

    @Override
    public void removeTextChangedListener(TextWatcher watcher) {
        super.removeTextChangedListener(watcher);
    }*/
}
