package operations.operators;

public class Convolution {

    public void chooseHandler(byte[][] input, byte[][] output, float[][] MASK){
        if(MASK.length % 2 == 0 && MASK[0].length % 2 == 0){
            convolveEven(input, output, MASK);
        }else{
            convolve(input, output, MASK);
        }
    }
    
    private void handleBorder(byte[][] input, byte[][] output, int hmask, int vmask) {
        int h = input.length;
        int w = input[0].length;

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

    private void convolve(byte[][] input, byte[][] output, float[][] hMask) {
        float sum;

        // Decide border handling regions
        int h = (int) Math.floor((hMask.length / 2));
        int v = (int) Math.floor((hMask[0].length / 2));

        // Handle borders:
        handleBorder(input, output, h, v);

        // Convolution:
        int m2 = (int) Math.floor((hMask.length / 2));
        int n2 = (int) Math.floor((hMask[0].length / 2));

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

    private void handleEvenBorder(byte[][] input, byte[][] output, int hmask, int vmask) {
        int h = input.length;
        int w = input[0].length;

        for (int i = h - hmask; i < h; i++) {
            for (int j = 0; j < w; j++) {
                output[i][j] = input[i][j];
            }
        }

        for (int i = 0; i < h; i++) {
            for (int j = w - vmask; j < w; j++) {
                output[i][j] = input[i][j];
            }
        }
    }

    private void convolveEven(byte[][] input, byte[][] output, float[][] hMask) {
        float sum = 0;

        // Decide border handling regions
        int h = (int) Math.floor((hMask.length - 1));
        int v = (int) Math.floor((hMask[0].length - 1));

        //Border Handle
        handleEvenBorder(input, output, h, v);

        //Convolution
        int m2 = (int) Math.floor((hMask.length - 1));
        int n2 = (int) Math.floor((hMask[0].length - 1));

        for (int i = 0; i < output.length - m2; i++) {
            for (int j = 0; j < output[0].length - n2; j++) {
                sum = 0.0f;
                for (int x = 0; x < hMask.length; x++) {
                    for (int y = 0; y < hMask[0].length; y++) {
                        sum += hMask[0 + m2][0 + n2] * (input[i + x][j + y] & 0xff);
                    }
                }
                sum = ImageIo.clip(Math.abs(sum));
                output[i][j] = (byte) sum;
            }
        }
    }
}
