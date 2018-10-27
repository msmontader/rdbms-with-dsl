package db;
/**
 * Created by muntadherinaya on 2/27/17.
 */
public class TypeCheck {
    protected static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    protected static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    protected static Object getValuesType(String value){
        if(isInteger(value)){
            return new Integer(0);
        }
        else if (isFloat(value)){
            return new Float(0.0);
        }
        else {
            return new String();
        }
    }

}
