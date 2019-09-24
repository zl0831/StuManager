package zl.app.utility;

/**
 * Created by lotus on 2015/6/25.
 */
public class CInteger {

    public static Integer tryParse(String obj) {

        Integer retVal;
        try {
            retVal = Integer.parseInt(obj);
        } catch (NumberFormatException nfe) {
            retVal = 0; // or null if that is your preference
        }
        return retVal;
    }

}
