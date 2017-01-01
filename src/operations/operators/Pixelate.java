package operations.operators;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Pixelate {

    private static byte[][] rByteData, gByteData, bByteData;

    public static void blurImage(BufferedImage bufferedImage, byte[][] rByteData, byte[][] gByteData, byte[][] bByteData, int blockSize, int subBlockSize) {
        Color[][] getColor = new Color[rByteData.length][rByteData[0].length];
        ArrayList<Integer> tempR = new ArrayList<>();
        ArrayList<Integer> tempG = new ArrayList<>();
        ArrayList<Integer> tempB = new ArrayList<>();
        for (int i = 0; i < rByteData.length; i += blockSize) {
            for (int j = 0; j < rByteData[0].length; j += blockSize) {
                getColor[i][j] = new Color(bufferedImage.getRGB(i, j));
                tempR.add(getColor[i][j].getRed());
                tempG.add(getColor[i][j].getGreen());
                tempB.add(getColor[i][j].getBlue());
                for (int iD = i; (iD < i + blockSize) && (iD < rByteData.length); iD++) {
                    for (int jD = i; (jD < i + blockSize) && (jD < rByteData.length); jD++) {
                        int tempM = calcMeanForSubBlock(tempR, tempG, tempB, blockSize);
                        bufferedImage.setRGB(iD, jD, tempM);
                    }
                }
            }
        }
        Object[] byteArrays = ImageIo.getColorByteImageArray2DFromBufferedImage(bufferedImage);
        Pixelate.rByteData = (byte[][]) byteArrays[0];
        Pixelate.gByteData = (byte[][]) byteArrays[1];
        Pixelate.bByteData = (byte[][]) byteArrays[2];
    }

    /*Goes through each item on the list and sums up all the values in the list, then returns the mean*/
    private static int calcMeanForSubBlock(ArrayList<Integer> r, ArrayList<Integer> g, ArrayList<Integer> b, int blockSize) {
        Integer sumR = 0;
        Integer sumG = 0;
        Integer sumB = 0;

        for (Integer colors : r) {
            sumR += colors;
        }
        for (Integer colors : g) {
            sumG += colors;
        }
        for (Integer colors : b) {
            sumB += colors;
        }
        int rMean = sumR / blockSize - 1;
        int gMean = sumG / blockSize - 1;
        int bMean = sumB / blockSize - 1;
        return (rMean + gMean + bMean) / 3;
    }

    public static byte[][] getrByteData() {
        return rByteData;
    }

    public static byte[][] getgByteData() {
        return gByteData;
    }

    public static byte[][] getbByteData() {
        return bByteData;
    }
}
