package operations.operators;

public class Histogram {

    private int[] histogramGrayLevel = new int[256],
            histogram = new int[256],
            histogramC = new int[256],
            lut = new int[256];
    private float[] histogramN = new float[256],
            histogramCN = new float[256];   //Between 0 to 1
    private byte[][] imageGray;
    private float sizeOfImage = 0;

    public void scaleto256() {
        float min = histogramN[0];
        float max = histogramN[0];

        for (int i = 0; i < histogramN.length; i++) {
            if (histogramN[i] > max) {
                max = histogramN[i];
            } else if (histogramN[i] < min) {
                min = histogramN[i];
            }
        }

        float range = max - min;
        for (int i = 0; i < histogramN.length; i++) {
            histogramGrayLevel[i] = (int) (((histogramN[i] - min) / range) * 255);
        }
    }

    public void calcHist() {
        for (int i = 0; i < imageGray.length; i++) {
            for (int j = 0; j < imageGray[0].length; j++) {
                histogram[imageGray[i][j] & 0xFF]++;
            }
        }
        for (int i = 0; i < histogram.length; i++) {
            sizeOfImage += histogram[i];
        }
    }

    public void calcHistN() {
        sizeOfImage /= (float) 1.0;
        for (int i = 0; i < histogram.length; i++) {
            histogramN[i] = histogram[i] * sizeOfImage;
        }
    }

    public void calcHistE() {
        for (int i = 0; i < histogram.length; i++) {
            if (i == 0) {
                histogramC[i] = histogram[i];
            } else {
                histogramC[i] = histogramC[i - 1] + histogram[i];
            }
            histogramCN[i] = histogramC[i] / sizeOfImage;
        }
        for (int i = 0; i < lut.length; i++) {
            lut[i] = (int) Math.floor(255 * histogramCN[i]);
        }
    }

    public int[] getHistogramGrayLevel() {
        return histogramGrayLevel;
    }

    public int[] getLut() {
        return lut;
    }

    public void setImageGray(byte[][] imageGray) {
        this.imageGray = imageGray;
    }
}
