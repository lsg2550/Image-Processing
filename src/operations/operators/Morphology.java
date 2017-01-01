package operations.operators;

/**
 *
 * @author Luis
 */
public class Morphology {

    //Grows White
    public static byte[][] dilate(byte[][] input, byte[][] output) {
        int topLeft, topCenter, topRight,
                left, center, right,
                bottomLeft, bottomCenter, bottomRight;

        for (int i = 1; i < output.length - 1; i++) {
            for (int j = 1; j < output[0].length - 1; j++) {

                topLeft = input[i - 1][j - 1] & 0xFF;
                left = input[i - 1][j] & 0xFF;
                bottomLeft = input[i - 1][j + 1] & 0xFF;
                topCenter = input[i][j - 1] & 0xFF;
                center = input[i][j] & 0xFF;
                bottomCenter = input[i][j + 1] & 0xFF;
                topRight = input[i + 1][j - 1] & 0xFF;
                right = input[i + 1][j] & 0xFF;
                bottomRight = input[i + 1][j + 1] & 0xFF;

                if (topLeft == 0 && topCenter == 0 && topRight == 0
                        && left == 0 && center == 0 && right == 0
                        && bottomLeft == 0 && bottomCenter == 0 && bottomRight == 0) {
                    output[i][j] = (byte) 0;
                } else {
                    output[i][j] = (byte) 255;
                }
            }
        }
        return output;
    }

    //Grows Black
    public static byte[][] erosion(byte[][] input, byte[][] output) {
        int topLeft, topCenter, topRight,
                left, center, right,
                bottomLeft, bottomCenter, bottomRight;

        for (int i = 1; i < output.length - 1; i++) {
            for (int j = 1; j < output[0].length - 1; j++) {

                topLeft = input[i - 1][j - 1] & 0xFF;
                left = input[i - 1][j] & 0xFF;
                bottomLeft = input[i - 1][j + 1] & 0xFF;
                topCenter = input[i][j - 1] & 0xFF;
                center = input[i][j] & 0xFF;
                bottomCenter = input[i][j + 1] & 0xFF;
                topRight = input[i + 1][j - 1] & 0xFF;
                right = input[i + 1][j] & 0xFF;
                bottomRight = input[i + 1][j + 1] & 0xFF;

                if (topLeft == 255 && topCenter == 255 && topRight == 255
                        && left == 255 && center == 255 && right == 255
                        && bottomLeft == 255 && bottomCenter == 255 && bottomRight == 255) {
                    output[i][j] = (byte) 255;
                } else {
                    output[i][j] = (byte) 0;
                }
            }
        }
        return output;
    }

    public static byte[][] middleOutline(byte[][] input) {
        byte[][] outputDilate = new byte[input.length][input[0].length];
        byte[][] outputErode = new byte[input.length][input[0].length];
        byte[][] output = new byte[input.length][input[0].length];

        outputDilate = dilate(input, outputDilate);
        outputErode = erosion(input, outputErode);

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                output[i][j] = (byte) (outputDilate[i][j] - outputErode[i][j]);
            }
        }
        return output;
    }

    public static byte[][] insideOutline(byte[][] input, byte[][] output) {
        byte[][] tempErosion = erosion(input, output);

        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[0].length; j++) {
                output[i][j] = (byte) (input[i][j] - tempErosion[i][j]);
            }
        }
        return output;
    }

    public static byte[][] outsideOutline(byte[][] input, byte[][] output) {
        byte[][] tempDilate = dilate(input, output);

        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[0].length; j++) {
                output[i][j] = (byte) (tempDilate[i][j] - input[i][j]);
            }
        }
        return output;
    }

    public static byte[][] opening(byte[][] input) {
        byte[][] outputErode = new byte[input.length][input[0].length];
        byte[][] outputDilate = new byte[input.length][input[0].length];

        outputErode = erosion(input, outputErode);
        outputDilate = dilate(outputErode, outputDilate);

        return outputDilate;
    }

    public static byte[][] closing(byte[][] input) {
        byte[][] outputDilate = new byte[input.length][input[0].length];
        byte[][] outputErode = new byte[input.length][input[0].length];

        outputDilate = dilate(input, outputDilate);
        outputErode = erosion(outputDilate, outputErode);

        return outputErode;
    }
}
