package operations.operators;

public class Gradient {

    public static byte[][] basicGradient(byte[][] inputArray, byte[][] outputArray) {
        for (int i = 0; i < outputArray.length; i++) {
            for (int j = 0; j < outputArray[0].length; j++) {
                try {
                    outputArray[i][j] = (byte) Math.sqrt((Math.pow((inputArray[i][j] - inputArray[i + 1][j]), 2)
                            + Math.pow((inputArray[i][j] - inputArray[i][j + 1]), 2)));
                } catch (ArrayIndexOutOfBoundsException e) {
                    outputArray[i][j] = 0;
                }
            }
        }
        return outputArray;
    }

    public static byte[][] basicGradient2(byte[][] inputArray, byte[][] outputArray) {
        for (int i = 0; i < outputArray.length; i++) {
            for (int j = 0; j < outputArray[0].length; j++) {
                try {
                    outputArray[i][j] = (byte) (Math.abs(inputArray[i][j] - inputArray[i + 1][j])
                            + Math.abs(inputArray[i][j] - inputArray[i][j + 1]));
                } catch (ArrayIndexOutOfBoundsException e) {
                    outputArray[i][j] = 0;
                }
            }
        }
        return outputArray;
    }

    public static byte[][] robertGradient(byte[][] inputArray, byte[][] outputArray) {
        for (int i = 0; i < outputArray.length; i++) {
            for (int j = 0; j < outputArray[0].length; j++) {
                try {
                    outputArray[i][j] = (byte) (Math.abs(inputArray[i][j] - inputArray[i + 1][j + 1])
                            + Math.abs(inputArray[i + 1][j] - inputArray[i][j + 1]));
                } catch (ArrayIndexOutOfBoundsException e) {
                    outputArray[i][j] = 0;
                }
            }
        }
        return outputArray;
    }

    public static byte[][] sobelGradient(byte[][] inputArray, byte[][] outputArray) {
        for (int i = 0; i < outputArray.length; i++) {
            for (int j = 0; j < outputArray[0].length; j++) {
                try {
                    int temp1 = Math.abs(-inputArray[i - 1][j - 1] - (2 * inputArray[i - 1][j]) - inputArray[i - 1][j + 1]
                            + inputArray[i + 1][j - 1] + (2 * inputArray[i + 1][j]) + inputArray[i + 1][j + 1]);
                    int temp2 = Math.abs(-inputArray[i - 1][j - 1] - (2 * inputArray[i][j - 1]) - inputArray[i + 1][j - 1]
                            + inputArray[i - 1][j + 1] + (2 * inputArray[i][j + 1]) + inputArray[i + 1][j + 1]);
                    outputArray[i][j] = (byte) ImageIo.clip((temp1 + temp2) & 0xFF);
                } catch (ArrayIndexOutOfBoundsException e) {
                    outputArray[i][j] = 0;
                }
            }
        }
        return outputArray;
    }

    public static byte[][] prewittGradient(byte[][] inputArray, byte[][] outputArray) {
        for (int i = 0; i < outputArray.length; i++) {
            for (int j = 0; j < outputArray[0].length; j++) {
                try {
                    int temp1 = Math.abs(-inputArray[i - 1][j - 1] - inputArray[i - 1][j] - inputArray[i - 1][j + 1]
                            + inputArray[i + 1][j - 1] + inputArray[i + 1][j] + inputArray[i + 1][j + 1]);
                    int temp2 = Math.abs(-inputArray[i - 1][j - 1] - inputArray[i][j - 1] - inputArray[i + 1][j - 1]
                            + inputArray[i - 1][j + 1] + inputArray[i][j + 1] + inputArray[i + 1][j + 1]);
                    outputArray[i][j] = (byte) ImageIo.clip((temp1 + temp2) & 0xFF);
                } catch (ArrayIndexOutOfBoundsException e) {
                    outputArray[i][j] = 0;
                }
            }
        }
        return outputArray;
    }
}
