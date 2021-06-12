import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

import java.awt.*;

public class SeamCarver {
    private Picture picture;
    private RGB[][] pixelArray;
    private double[][] energyArray;
    private double[][] totalMinEnergy;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.pixelArray = toPixelArray(picture);
        this.energyArray = calEnergyArray(pixelArray);
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }
    // width of current picture
    public int width() {
        return picture.width();
        // return 5;
    }
    // height of current picture
    public int height() {
        return picture.height();
        // return 3;
    }
    // energy of pixel at column x and row y
    public  double energy(int x, int y) {
        return deltaSquareX(x, y) + deltaSquareY(x, y);
    }
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        energyArray = transpose(energyArray);
        totalMinEnergy = calTotalMinEnergy(energyArray);
        int W = totalMinEnergy.length;
        int H = totalMinEnergy[0].length;
        int[] indexes = helperFindVerticalSeam(W, H);
        energyArray = transpose(energyArray);
        return indexes;
    }
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        totalMinEnergy = calTotalMinEnergy(energyArray);
        int W = totalMinEnergy.length;
        int H = totalMinEnergy[0].length;
        int[] indexes = helperFindVerticalSeam(W, H);
        return indexes;
    }

    private int[] helperFindVerticalSeam(int W, int H) {
        Stack<Integer> indexes = new Stack<>();
        int bottomIndex = Integer.MAX_VALUE;
        double minTotalEnergy = Integer.MAX_VALUE;
        for (int i = 0; i < W; i += 1) {
            if (totalMinEnergy[i][H - 1] < minTotalEnergy) {
                minTotalEnergy = totalMinEnergy[i][H - 1];
                bottomIndex = i;
            }
        }
        if (H == 1) {
            return new int[]{bottomIndex};
        }
        indexes.push(bottomIndex);
        for (int j = H - 1; j > 0; j -= 1) {
            int lastColIndex = indexes.peek();
            for (int x : adjX(lastColIndex, W)) {
                if (totalMinEnergy[lastColIndex][j]
                        == energyArray[lastColIndex][j] + totalMinEnergy[x][j - 1]) {
                    indexes.push(x);
                    break;
                }
            }
        }
        int[] output = new int[H];
        for (int i = 0; i < H; i += 1) {
            output[i] = indexes.pop();
        }
        return output;
    }
    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
        this.pixelArray = toPixelArray(picture);
        this.energyArray = calEnergyArray(pixelArray);

    }
    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        picture = SeamRemover.removeVerticalSeam(picture, seam);
        this.pixelArray = toPixelArray(picture);
        this.energyArray = calEnergyArray(pixelArray);

    }
    /**
     * Helper classes and functions
     */

    // A class for the pixel.
    private static class RGB {
        private int red;
        private int green;
        private int blue;

        RGB(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }
    // Transform the picture into the pixelArray.
    private RGB[][] toPixelArray(Picture picture) {
        int numOfX = picture.width();
        int numOfY = picture.height();
        RGB[][] pixelArray = new RGB[numOfX][numOfY];
        for (int i = 0; i < numOfX; i += 1) {
            for (int j = 0; j < numOfY; j += 1) {
                Color c = picture.get(i, j);
                pixelArray[i][j] = new RGB(c.getRed(), c.getGreen(), c.getBlue());
            }
        }
        return pixelArray;
    }

    // Give the correct adjacent index considering the boundry.
    private int xIndex(int x) {
        if (x >= 0 && x < width()) {
            return x;
        }
        if (x == -1) {
            return width() - 1;
        }
        if (x == width()) {
            return 0;
        }
        throw new IndexOutOfBoundsException("xIndex is out of bound.");
    }

    private int yIndex(int y) {
        if (y >= 0 && y < height()) {
            return y;
        }
        if (y == -1) {
            return height() - 1;
        }
        if (y == height()) {
            return 0;
        }
        throw new IndexOutOfBoundsException("yIndex is out of bound.");
    }

    // The second gradient int the direction of x.
    private int deltaSquareX(int x, int y) {
        RGB adjLeft = pixelArray[xIndex(x - 1)][yIndex(y)];
        RGB adjRight = pixelArray[xIndex(x + 1)][yIndex(y)];
        int Rx = adjLeft.red - adjRight.red;
        int Gx = adjLeft.green - adjRight.green;
        int Bx = adjLeft.blue - adjRight.blue;
        return Rx * Rx + Gx * Gx + Bx * Bx;
    }
    // The second gradient int the direction of y.
    private int deltaSquareY(int x, int y) {
        RGB adjDown = pixelArray[xIndex(x)][yIndex(y - 1)];
        RGB adjUp = pixelArray[xIndex(x)][yIndex(y + 1)];
        int Ry = adjDown.red - adjUp.red;
        int Gy = adjDown.green - adjUp.green;
        int By = adjDown.blue - adjUp.blue;
        return Ry * Ry + Gy * Gy + By * By;
    }

    // Give the pixelArray calculate the energyArray.
    private double[][] calEnergyArray(RGB[][] pixelArray) {
        double[][] energyArray = new double[pixelArray.length][pixelArray[0].length];
        for (int i = 0; i < energyArray.length; i += 1) {
            for (int j = 0; j < energyArray[0].length; j += 1) {
                energyArray[i][j] = energy(i, j);
            }
        }
        return energyArray;
    }

    // Give the picture calculate the energyArray.
    private double[][] calEnergyArray(Picture picture) {
        return calEnergyArray(toPixelArray(picture));
    }

    // Give the correct adjacent x indexes of a pixel, blocked the boundary.
    private int[] adjX(int x) {
        return adjX(x, width());
    }
    private int[] adjX(int x, int W) {
        if (x > 0 && x < W - 1) {
            return new int[] {x - 1, x, x + 1};
        }
        if (x == 0) {
            return new int[] {x, x + 1};
        }
        if (x == W - 1) {
            return new int[] {x - 1, x};
        }
        throw new IndexOutOfBoundsException("adjX is out of bound.");
    }
    /* Given the energyArray, calculate the minimum total energy of a pixel,
       which is the end of a certain seam path, from the top to the bottom.
     */
    private double[][] calTotalMinEnergy(double[][] energyArray) {
        int W = energyArray.length;
        int H = energyArray[0].length;
        double[][] totalMinEnergy = new double[W][H];
        for (int i = 0; i < W; i += 1) {
            totalMinEnergy[i][0] = energyArray[i][0];
        }
        for (int j = 1; j < H; j += 1) {
            for (int i = 0; i < W; i += 1) {
                double minEnergy = Double.MAX_VALUE;
                for (int x : adjX(i, W)) {
                    minEnergy = Math.min(minEnergy, totalMinEnergy[x][j - 1]);
                }
                totalMinEnergy[i][j] = minEnergy + energyArray[i][j];
            }
        }
        return totalMinEnergy;
    }
    /** *
     * Transpose the matrix.
     */
    private double[][] transpose(double[][] matrix) {
        int W = matrix.length;
        int H = matrix[0].length;
        double[][] t = new double[H][W];
        for (int i = 0; i < H; i += 1) {
            for (int j = 0; j < W; j += 1) {
                t[i][j] = matrix[j][i];
            }
        }
        return t;
    }
}
