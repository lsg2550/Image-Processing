package operations.parsing;

public class TryParse {

    public static boolean tryIntParse(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
