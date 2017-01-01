package operations;

public class Convolution {

    public void handleBorder(byte[][] input, byte[][] output, int hmask, int vmask) {
        int argb, r, g, b, a;
        int h = input.length;
        int w = input[0].length;
        //
        for (int i = 0; i < hmask; i++) {
            for (int j = 0; j < w; j++) {
                output[i][j] = input[i][j];
            }
        }

        for (int i = h - hmask; i < h; i++) {
            for (int j = 0; j < w; j++) {
                output[i][j] = input[i][j];
            }
        }

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < vmask; j++) {
                output[i][j] = input[i][j];
            }
        }
        for (int i = 0; i < h; i++) {
            for (int j = w - vmask; j < w; j++) {
                output[i][j] = input[i][j];
            }
        }
    }

    public void convolve(byte[][] input, byte[][] output, float[][] hMask) {
        float sum;
        /*Check you mask
        for (int i = 0; i < hMask.length; i++) {
            for (int j = 0; j < hMask[0].length; j++) {
                System.out.print(" " + hMask[i][j]);
            }
            System.out.println("\n------------------------------");
        }*/

        // Decide border handling regions
        int h = (int) Math.floor((hMask.length / 2));
        int v = (int) Math.floor((hMask[0].length / 2));
        //System.out.println("h = :" + h);

        // Handle borders:
        handleBorder(input, output, h, v);
        //System.out.println("Mask has: row size =" + hMask.length + " column size= " + hMask[0].length);

        // Convolution
        int m2 = (int) Math.floor((hMask.length / 2));
        int n2 = (int) Math.floor((hMask[0].length / 2));
        //System.out.println("m2 = :" + m2 + " n2 = " + n2);

        for (int i = m2; i < output.length - m2; i++) {
            for (int j = n2; j < output[0].length - n2; j++) {
                sum = 0.0f;
                for (int x = -m2; x <= m2; x++) {
                    for (int y = -n2; y <= n2; y++) {
                        sum += hMask[x + m2][y + n2] * (input[i + x][j + y] & 0xff);
                    }
                }
                sum = ImageIo.clip(Math.abs(sum));
                output[i][j] = (byte) sum;
            }
        }
    }
}
