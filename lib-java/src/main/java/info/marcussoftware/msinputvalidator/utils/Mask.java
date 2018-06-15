package info.marcussoftware.msinputvalidator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 13/06/2018.
 */
public class Mask {
    private String mask;
    private String currencySymbol;
    private String decimalSeparator;
    private String thousands;
    private boolean isCurrency = false;

    public Mask(String mask) {
        this.mask = mask;
        initCurrency();
    }

    public String applyMask(String text) {
        if (isCurrency) {
            return currencyMask(text);
        } else {
            return nonCurrencyMask(text);
        }
    }

    public String removeMask(String text) {
        String mask = this.mask.replaceAll("#", "").replaceAll("-", "\\\\-");
        if (mask.isEmpty())
            return text;
        else
            return text.replaceAll("[" + mask + "]", "").trim();
    }

    private void initCurrency() {
        String currencyRegex = "([R$€]{1,3}) #{0,3}([\\.,]?)#{0,3}([\\.,]?)#{0,3}([\\.,]?)#{1,3}";
        Pattern pattern = Pattern.compile(currencyRegex);
        Matcher matcher = pattern.matcher(mask);
        while (matcher.find()) {
            isCurrency = true;
            currencySymbol = matcher.group(1);
            if (!matcher.group(4).isEmpty()) {
                decimalSeparator = matcher.group(4);
                thousands = matcher.group(3);
            } else if (!matcher.group(3).isEmpty()) {
                decimalSeparator = matcher.group(3);
                thousands = matcher.group(2);
            } else if (!matcher.group(2).isEmpty()) {
                decimalSeparator = matcher.group(2);
                thousands = "";
            }
        }
    }

    private String nonCurrencyMask(String text) {
        String newText = text.replace("#", "§");
        StringBuilder res = new StringBuilder(mask);
        for (int i = 0; i < newText.length(); i++) {
            if (res.toString().contains("#")) {
                res = new StringBuilder(res.toString().replaceFirst("#", String.valueOf(newText.charAt(i))));
            } else
                res.append(text.charAt(i));
        }
        return res.toString().split("#")[0].replace("§", "#");
    }

    private String currencyMask(String text) {
        String mText = text.replaceAll("^0+","");
        while (mText.length() < 3) mText = "0" + mText;
        String mDecimal = mText.substring(mText.length() - 2, mText.length());
        StringBuilder mInteger = new StringBuilder(mText.substring(0, mText.length() - 2));
        int repeats = mInteger.length() / 3;
        for (int i = 0; i < repeats; i++) {
            mInteger.insert(mInteger.length() - (3 + (i * 4)), thousands);
        }
        return currencySymbol + " " + mInteger.toString().replaceAll("^\\.|\\.$", "") + decimalSeparator + mDecimal;
    }
}
