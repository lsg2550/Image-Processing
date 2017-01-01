package operations.parsing;

public class TryParse {

    public static boolean tryParse(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
