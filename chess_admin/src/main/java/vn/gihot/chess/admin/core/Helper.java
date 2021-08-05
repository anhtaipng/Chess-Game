package vn.gihot.chess.admin.core;

import java.util.Locale;
import java.util.ResourceBundle;

public class Helper {

    public static String getMessage(String messageKey) {
        return ResourceBundle.getBundle("messages", Locale.getDefault())
                .getString(messageKey);
    }
}
