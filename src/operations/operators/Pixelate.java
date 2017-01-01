package operations.operators;

public class Pixelate {

    private byte[][] rByteData, gByteData, bByteData;

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
