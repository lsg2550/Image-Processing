package operations.operators;

public class Rotate {

    public static byte[][] rotateBackward(byte[][] inputArray, byte[][] outputArray, float angle) {
        int oldI, oldJ;
        float cosTerm = (float) Math.cos(Math.toRadians(angle)),
                sinTerm = (float) Math.sin(Math.toRadians(angle));

        for (int i = 0; i < outputArray.length; i++) {
            for (int j = 0; j < outputArray[0].length; j++) {
                oldI = (int) (i * cosTerm - j * sinTerm);
                oldJ = (int) (i * sinTerm + j * cosTerm);
                if (oldI > 0 && oldI < outputArray.length && oldJ > 0 && oldJ < outputArray[0].length) {
                    outputArray[i][j] = inputArray[oldI][oldJ];
                } else {
                    outputArray[i][j] = 0;
                }
            }
        }
        return outputArray;
    }

    public static byte[][] rotateForward(byte[][] inputArray, byte[][] outputArray, float angle) {
        int newI, newJ;
        float cosTerm = (float) Math.cos(Math.toRadians(angle)),
                sinTerm = (float) Math.sin(Math.toRadians(angle));

        for (int i = 0; i < outputArray.length; i++) {
            for (int j = 0; j < outputArray[0].length; j++) {
                newI = (int) (i * cosTerm + j * sinTerm);
                newJ = (int) (-i * sinTerm + j * cosTerm);
                if (newI > 0 && newI < outputArray.length && newJ > 0 && newJ < outputArray[0].length) {
                    outputArray[newI][newJ] = inputArray[i][j];
                }
            }
        }
        return outputArray;
    }

    public static byte[][] rotateForwardFixed(byte[][] inputArray, byte[][] outputArray, float angle) {
        int newI, newJ;

        outputArray = new byte[2 * outputArray.length][2 * outputArray[0].length];

        int shiftH = outputArray.length / 2;
        int shiftW = outputArray[0].length / 2;

        float cosTerm = (float) Math.cos(Math.toRadians(angle)),
                sinTerm = (float) Math.sin(Math.toRadians(angle));

        for (int i = 0; i < outputArray.length; i++) {
            for (int j = 0; j < outputArray[0].length; j++) {

                newI = (int) (((i - shiftH) * cosTerm) - (j - shiftW) * sinTerm);
                newJ = (int) (((i - shiftH) * sinTerm) + (j - shiftW) * cosTerm);

                if ((newI + 2 * shiftH) > 0 && (newI + shiftH) < 2 * outputArray.length && (newJ + 2 * shiftW) > 0 && (newJ + shiftW) < 2 * outputArray[0].length) {
                    outputArray[newI + 2 * shiftH][newJ + 2 * shiftW] = inputArray[i][j];
                }

                /*
                 newI = (int) (i * cosTerm + j * sinTerm);
                 newJ = (int) (-i * sinTerm + j * cosTerm);
                 if (newI > 0 && newI < outputArray.length && newJ > 0 && newJ < outputArray[0].length) {
                 outputArray[newI][newJ] = inputArray[i][j];
                 }
                 */
            }
        }
        return outputArray;
    }
}
