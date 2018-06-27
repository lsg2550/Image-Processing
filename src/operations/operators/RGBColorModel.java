package operations.operators;

public class RGBColorModel {

    public static void drawCircle(double cx, double cy, int r, byte[][] inArrayr, byte[][] inArrayg, byte[][] inArrayb, int red, int green, int blue) {
        for (int i = 0; i < inArrayr.length; i++) {
            for (int j = 0; j < inArrayg[0].length; j++) {
                if (((i - cx) * (i - cx)) + ((j - cy) * (j - cy)) <= r) {
                    inArrayr[i][j] = (byte) red;
                    inArrayg[i][j] = (byte) green;
                    inArrayb[i][j] = (byte) blue;
                }
            }
        }
    }

    public static void addColor(int cx, int cy, int r, byte[][] inArrayr, byte[][] inArrayg, byte[][] inArrayb, int indexcolor) {
        for (int i = 0; i < inArrayr.length; i++) {
            for (int j = 0; j < inArrayg[0].length; j++) {
                if (((i - cx) * (i - cx)) + ((j - cy) * (j - cy)) <= r) {
                    if (indexcolor == 1) {
                        inArrayr[i][j] = (byte) ImageIo.clip(255);
                    } else if (indexcolor == 2) {
                        inArrayg[i][j] = (byte) ImageIo.clip(255);
                    } else if (indexcolor == 3) {
                        inArrayb[i][j] = (byte) ImageIo.clip(255);
                    }
                }
            }
        }
    }

    public static void fillColor(byte[][] inArrayr, byte[][] inArrayg, byte[][] inArrayb, int red, int green, int blue) {
        for (int i = 0; i < inArrayr.length; i++) {
            for (int j = 0; j < inArrayg[0].length; j++) {
                inArrayr[i][j] = (byte) red;
                inArrayg[i][j] = (byte) green;
                inArrayb[i][j] = (byte) blue;
            }
        }
    }
}
