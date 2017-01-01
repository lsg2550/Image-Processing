package pointoperations;

public class ImageNegative {

    public static byte[][] createNegative(byte[][] inArray) {
        int h = inArray.length;
        int w = inArray[0].length;
        byte[][] outArray = new byte[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                outArray[i][j] = (byte) (255 - (inArray[i][j] & 0xFF));
            }
        }
        return outArray;
    }
}
