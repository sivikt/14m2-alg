package algs.assignments.seamCarving;

import edu.princeton.cs.introcs.Picture;

import java.awt.Color;

import static java.lang.Math.abs;
import static java.lang.System.arraycopy;
import static java.util.Arrays.fill;

/**
 * @author Serj Sintsov
 */
public class SeamCarver {

    private static enum Orientation {
        VERTICAL, HORIZONTAL;

        public Orientation reverse() {
            if (this == HORIZONTAL)
                return VERTICAL;
            else
                return HORIZONTAL;
        }
    }

    private static class Dimension {
        public final int width;
        public final int height;

        private Dimension(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public static Dimension direct(int[][] array2D) {
            int height = array2D.length;
            int width = height > 0 ? array2D[0].length : 0;
            return new Dimension(width, height);
        }

        public static Dimension reverse(int[][] array2D) {
            Dimension direct = direct(array2D);
            return new Dimension(direct.height, direct.width);
        }
    }

    private interface EnergyFunction {
        /**
         * Pixels energies for new picture. Energies row corresponds to pixels row.
         */
        int[][] calculate(int[][] pixels);

        /**
         * Pixels energies for new picture based on oldEnergies and excluded pixels.
         * Energies row corresponds to pixels row.
         * Seam represents excluded pixels: one in each row.
         * Thus, {@code pixels.length == oldEnergies.length == seam.length} and
         * {@code pixels[0].length+1 == oldEnergies[0].length}
         */
        int[][] calculateWithExclusion(int[][] pixels, int[][] oldEnergies, int[] seam);
    }

    private static final Orientation ORIGIN_ORIENTATION = Orientation.HORIZONTAL;
    private static final EnergyFunction DEFAULT_ENERGY_FUNCTION = new DualGradientFunction();

    private int width;
    private int height;

    private int[][] pixels;
    private int[][] energies;

    private Orientation currOrient;
    private final EnergyFunction energyFunc;

    /**
     * create a seam carver object based on the given picture
     */
    public SeamCarver(Picture picture) {
        checkNotNull(picture);

        width  = picture.width();
        height = picture.height();
        pixels = toRowPixels(picture);
        currOrient = ORIGIN_ORIENTATION;
        energyFunc = DEFAULT_ENERGY_FUNCTION;
        energies = energyFunc.calculate(pixels);
    }

    /**
     * current picture
     */
    public Picture picture() {
        return toPicture();
    }

    /**
     * weight of current picture
     */
    public int width() {
        return width;
    }

    /**
     * height of current picture
     */
    public int height() {
        return height;
    }

    /**
     * energy of pixel at column x and row y
     */
    public double energy(int x, int y) {
        checkXYBounds(x, y);
        return origEntry(energies, y, x);
    }

    /**
     * sequence of indices for horizontal seam
     */
    public int[] findHorizontalSeam() {
        transposePictureTo(Orientation.VERTICAL);
        return new TopologicalPathSearch(energies).seam();
    }

    /**
     * sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        transposePictureTo(Orientation.HORIZONTAL);
        return new TopologicalPathSearch(energies).seam();
    }

    /**
     * remove horizontal seam from current picture
     */
    public void removeHorizontalSeam(int[] seam) {
        removeSeam(seam, Orientation.HORIZONTAL);
    }

    /**
     * remove vertical seam from current picture
     */
    public void removeVerticalSeam(int[] seam) {
        removeSeam(seam, Orientation.VERTICAL);
    }

    private void checkPictureSizeBeforeShrink() {
        if (width <= 1 || height <= 1)
            throw new IllegalArgumentException("Picture size is too small");
    }

    /**
     * Always removes vertical pixels and do transposition before that (read array by rows).
     * Note: to remove vertical seam keep pixels in horizontal orientation;
     * to remove horizontal seam keep pixels in vertical orientation.
     */
    private void removeSeam(int[] seam, Orientation seamOrient) {
        checkPictureSizeBeforeShrink();
        checkIsValidSeam(seam, seamOrient);

        if (seam.length == 0)
            return;

        transposePictureTo(seamOrient.reverse());
        pixels = shrinkPixels(seam);
        energies = energyFunc.calculateWithExclusion(pixels, energies, seam);

        if (seamOrient == Orientation.HORIZONTAL)
            height -= 1;
        else
            width -= 1;
    }

    private int[][] shrinkPixels(int[] seam) {
        Dimension dim = Dimension.direct(pixels);
        int[][] outPixels = new int[dim.height][dim.width - 1];

        for (int y = 0; y < dim.height; y++) {
            arraycopy(pixels[y], 0, outPixels[y], 0, seam[y]);

            if (seam[y] < dim.width - 1)
                arraycopy(pixels[y], seam[y] + 1, outPixels[y], seam[y], dim.width - seam[y] - 1);
        }

        return outPixels;
    }

    private int[][] toRowPixels(Picture picture) {
        int[][] pixels = new int[picture.height()][picture.width()];

        for (int y = 0; y < picture.height(); y++)
            for (int x  = 0; x < picture.width(); x++)
                pixels[y][x] = picture.get(x, y).getRGB();

        return pixels;
    }

    private Picture toPicture() {
        transposePictureTo(ORIGIN_ORIENTATION);

        Picture pic = new Picture(width, height);

        for (int y = 0; y < height; y++)
            for (int x  = 0; x < width; x++)
                pic.set(x, y, new Color(origEntry(pixels, y, x)));

        return pic;
    }

    /**
     * TODO maybe its better to make implicit transposition without recreating a new array.
     */
    private void transposePictureTo(Orientation orient) {
        if (currOrient == orient)
            return;

        Dimension transposeDim = Dimension.reverse(pixels);

        int[][] transposePixels = new int[transposeDim.height][transposeDim.width];
        int[][] transposeEnergies = new int[transposeDim.height][transposeDim.width];

        for (int x = 0; x < transposeDim.width; x++)
            for (int y = 0; y < transposeDim.height; y++) {
                transposePixels[y][x] = pixels[x][y];
                transposeEnergies[y][x] = energies[x][y];
            }

        pixels = transposePixels;
        energies = transposeEnergies;
        currOrient = orient;
    }

    private int origEntry(int[][] src, int row, int col) {
        if (currOrient == Orientation.HORIZONTAL)
            return src[row][col];
        else
            return src[col][row];
    }

    private static void checkNotNull(Object o) {
        if (o == null)
            throw new NullPointerException("input is null");
    }

    private void checkXYBounds(int x, int y) {
        if (x < 0 || x >= width)
            throw new IndexOutOfBoundsException("X has to be between 0 and " + width);
        if (y < 0 || y >= height)
            throw new IndexOutOfBoundsException("Y has to be between 0 and " + height);
    }

    private void checkIsValidSeam(int[] seam, Orientation seamOrient) {
        checkNotNull(seam);

        if (seam.length == 0)
            return;

        int expectedSeamLength;
        int maxSeamEntry;

        if (seamOrient == Orientation.HORIZONTAL) {
            expectedSeamLength = width;
            maxSeamEntry = height;
        }
        else {
            expectedSeamLength = height;
            maxSeamEntry = width;
        }

        if (seam.length > expectedSeamLength)
            throw new IllegalArgumentException("Seam has incorrect length ");

        for (int i = 0; i < seam.length - 1; i++)
            if ((seam[i] < 0 || seam[i] >= maxSeamEntry) || (abs(seam[i] - seam[i + 1]) > 1))
                throw new IllegalArgumentException("Seam has incorrect entries");
    }


    private static class TopologicalPathSearch {

        private static final int INFINITY = Integer.MAX_VALUE;

        private final int[][] energies;
        private final Dimension dim;

        private final int[] pathTo;
        private final int[] distTo;

        public TopologicalPathSearch(int[][] energies) {
            this.energies = energies;
            this.dim = Dimension.direct(energies);

            int V = dim.height*dim.width;
            this.pathTo = new int[V];
            this.distTo = new int[V];

            search();
        }

        private void search() {
            fill(distTo, INFINITY);

            pathTo[0] = 0;
            distTo[0] = 0;

            // first pixels row
            for (int x = 0; x < dim.width; x++) {
                pathTo[x] = x;
                distTo[x] = energies[0][x];
            }

            // rest of pixels
            for (int y = 0; y < dim.height-1; y++)
                for (int x = 0; x < dim.width; x++)
                    relax(x, y);
        }

        private void relax(int x, int y) {
            int destY = y+1;
            int flatSrc = toFlatIndex(x, y);

            if (x > 0)
                relax(flatSrc, x-1, destY);

            if (x < dim.width-1)
                relax(flatSrc, x+1, destY);

            relax(flatSrc, x, destY);
        }

        private void relax(int flatSrc, int destX, int destY) {
            int flatDest = toFlatIndex(destX, destY);
            int destDist = distTo[flatSrc] + energies[destY][destX];

            if (destDist < distTo[flatDest]) {
                distTo[flatDest] = destDist;
                pathTo[flatDest] = flatSrc;
            }
        }

        private int toFlatIndex(int x, int y) {
            return dim.width*y + x;
        }

        private int toX(int flatIndex) {
            return flatIndex % dim.width;
        }

        public int[] seam() {
            int[] seam = new int[dim.height];

            int lastPixel = findLastPixelInMinPath();
            int y = dim.height - 1;
            seam[y] = toX(lastPixel);

            while (pathTo[lastPixel] != lastPixel) {
                lastPixel = pathTo[lastPixel];
                y -= 1;
                seam[y] = toX(lastPixel);
            }

            return seam;
        }

        private int findLastPixelInMinPath() {
            int minPixel = toFlatIndex(0, dim.height - 1);
            int minDist = distTo[minPixel];

            for (int i = minPixel + 1; i < distTo.length; i++)
                if (minDist > distTo[i]) {
                    minPixel = i;
                    minDist = distTo[i];
                }

            return minPixel;
        }
    }


    private static class DualGradientFunction implements EnergyFunction {

        private static final int BORDER_ENERGY = 255*255 + 255*255 + 255*255;

        @Override
        public int[][] calculate(int[][] pixels) {
            Dimension dim = Dimension.direct(pixels);
            int height = dim.height - 1;
            int width  = dim.width - 1;

            int[][] energies = new int[height + 1][width + 1];

            for (int x = 0; x <= width; x++) {
                energies[0][x] = BORDER_ENERGY;
                energies[height][x] = BORDER_ENERGY;
            }

            for (int y = 1; y < height; y++) {
                energies[y][0] = BORDER_ENERGY;
                energies[y][width] = BORDER_ENERGY;
            }

            for (int y = 1; y < height; y++)
                for (int x = 1; x < width; x++)
                    energies[y][x] = deltaX(pixels, x, y) + deltaY(pixels, x, y);

            return energies;
        }

        @Override
        public int[][] calculateWithExclusion(int[][] pixels, int[][] oldEnergies, int[] seam) {
            // todo recalculate only changed energies
            return calculate(pixels);
        }

        private int deltaX(int[][] pixels, int x, int y) {
            return rgbDelta(pixels[y][x - 1], pixels[y][x + 1]);
        }

        private int deltaY(int[][] pixels, int x, int y) {
            return rgbDelta(pixels[y-1][x], pixels[y+1][x]);
        }

        private int rgbDelta(int p1, int p2) {
            Color c1 = new Color(p1);
            Color c2 = new Color(p2);
            int deltaR = c1.getRed()   - c2.getRed();
            int deltaG = c1.getGreen() - c2.getGreen();
            int deltaB = c1.getBlue()  - c2.getBlue();
            return deltaR*deltaR + deltaG*deltaG + deltaB*deltaB;
        }
    }

}
