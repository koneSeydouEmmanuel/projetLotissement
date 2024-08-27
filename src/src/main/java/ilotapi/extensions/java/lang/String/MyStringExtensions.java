package ilotapi.extensions.java.lang.String;

import com.ilot.utils.Utilities;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.text.ParseException;

@Extension
public class MyStringExtensions {
    /**
     * Returns a string that is a substring of this string. (ext)
     *
     * @param thiz
     * @param atBegin
     * @param size
     * @return
     */
    public static String substr(@This String thiz, boolean atBegin, int size) {
        return Utilities.substring(thiz, atBegin, size);
    }

    /**
     * Compare to a string (ext)
     * @param thiz
     * @param toCompare
     * @return
     */
    public static boolean areEquals(@This String thiz, String toCompare) {
        return Utilities.areEquals(thiz, toCompare);
    }

    public static boolean areNotEquals(@This String thiz, String toCompare) {
        return Utilities.areNotEquals(thiz, toCompare);
    }

    /**
     * Change date string in other format (ext)
     * @param date the date in string. Example: "dd/MM/yyyy HH:mm:ss"
     * @param format the new format. Example: "yyyy-MM-dd"
     * @return
     * @throws ParseException
     */
    public static String formatDate(@This String date, String format) throws ParseException {
        return Utilities.formatDate(date, format);
    }

}