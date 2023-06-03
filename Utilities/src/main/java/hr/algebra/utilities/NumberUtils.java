package hr.algebra.utilities;

public class NumberUtils {
    public static boolean isNumber(String str) {
        if (str == null || str.isEmpty() || str.isBlank()) return false;
        if (str.charAt(0) == '-')
            str = str.substring(1);
        
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        
        return true;
    }
}
