package operations.operators;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/*
 *
 * Code from https://stackoverflow.com/questions/15777821/how-can-i-pixelate-a-jpg-with-java
 */
public class Pixelate {

    private byte[][] rByteData, gByteData, bByteData;

    public BufferedImage pixelate(BufferedImage imageToPixelate, int pixelSize) {
        BufferedImage pixelateImage = new BufferedImage(
                imageToPixelate.getWidth(),
                imageToPixelate.getHeight(),
                imageToPixelate.getType());

        for (int y = 0; y < imageToPixelate.getHeight(); y += pixelSize) {
            for (int x = 0; x < imageToPixelate.getWidth(); x += pixelSize) {
                BufferedImage croppedImage = getCroppedImage(imageToPixelate, x, y, pixelSize, pixelSize);
                Color dominantColor = getDominantColor(croppedImage);
                for (int yd = y; (yd < y + pixelSize) && (yd < pixelateImage.getHeight()); yd++) {
                    for (int xd = x; (xd < x + pixelSize) && (xd < pixelateImage.getWidth()); xd++) {
                        pixelateImage.setRGB(xd, yd, dominantColor.getRGB());
                    }
                }
            }
        }

        return pixelateImage;
    }

    public BufferedImage getCroppedImage(BufferedImage image, int startx, int starty, int width, int height) {
        if (startx < 0) {
            startx = 0;
        }
        if (starty < 0) {
            starty = 0;
        }
        if (startx > image.getWidth()) {
            startx = image.getWidth();
        }
        if (starty > image.getHeight()) {
            starty = image.getHeight();
        }
        if (startx + width > image.getWidth()) {
            width = image.getWidth() - startx;
        }
        if (starty + height > image.getHeight()) {
            height = image.getHeight() - starty;
        }
        return image.getSubimage(startx, starty, width, height);
    }

    public Color getDominantColor(BufferedImage image) {
        Map<Integer, Integer> colorCounter = new HashMap<>(100);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int currentRGB = image.getRGB(x, y);
                int count = colorCounter.getOrDefault(currentRGB, 0);
                colorCounter.put(currentRGB, count + 1);
            }
        }
        return getDominantColor(colorCounter);
    }

    private Color getDominantColor(Map<Integer, Integer> colorCounter) {
        int dominantRGB = colorCounter.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();
        return new Color(dominantRGB);
    }

    public void blurImage(byte[][] rByteData, byte[][] gByteData, byte[][] bByteData, int blockSize, int subBlockSize) {
        int sumR = 0, sumG = 0, sumB = 0;
        this.rByteData = rByteData;
        this.gByteData = gByteData;
        this.bByteData = bByteData;

        for (int i = 0; i < rByteData.length; i += blockSize) {
            for (int j = 0; j < rByteData[0].length; j += blockSize) {
                sumR += (rByteData[i][j] & 0xFF);
                sumG += (gByteData[i][j] & 0xFF);
                sumB += (bByteData[i][j] & 0xFF);
                for (int iD = i; (iD < i + blockSize) && (iD < rByteData.length); iD++) {
                    for (int jD = i; (jD < i + blockSize) && (jD < rByteData.length); jD++) {
                        this.rByteData[iD][jD] = (byte) calcMeanForSubBlock(sumR, blockSize);
                        this.gByteData[iD][jD] = (byte) calcMeanForSubBlock(sumG, blockSize);
                        this.bByteData[iD][jD] = (byte) calcMeanForSubBlock(sumB, blockSize);
                    }
                }
            }
        }
    }

    private int calcMeanForSubBlock(int color, int blockSize) {
        color /= blockSize;
        return color;
    }

    public byte[][] getrByteData() {
        return rByteData;
    }

    public byte[][] getgByteData() {
        return gByteData;
    }

    public byte[][] getbByteData() {
        return bByteData;
    }
}
