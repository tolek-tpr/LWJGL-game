package org.bnb.utils;

import java.io.*;

public class LWGUtil {

    public static float[][] addFloatEntry(float[][] originalArray, float[] newEntry) {
        int originalRows = originalArray.length;
        float[][] newArray = new float[originalRows + 1][];

        System.arraycopy(originalArray, 0, newArray, 0, originalRows);
        newArray[originalRows] = newEntry;

        return newArray;
    }

    /**
     * Utility function to flatten a 2D float array into a 1D float array.
     *
     * @param array The 2D array to flatten.
     * @return The flattened 1D array.
     */
    public static float[] flattenFloatArray(float[][] array) {
        int totalLength = 0;
        for (float[] row : array) {
            totalLength += row.length;
        }

        float[] flattened = new float[totalLength];
        int index = 0;
        for (float[] row : array) {
            for (float value : row) {
                flattened[index++] = value;
            }
        }

        return flattened;
    }

    public static InputStream getResourceAsInputStream(String filePath) {
        ClassLoader classLoader = LWGUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);

        return inputStream;
    }

    /**
     *
     * @param in InputStream of the file you want to read
     * @return String, the entire file in one string.
     * @throws IOException
     */
    public static String readFile(InputStream in) throws IOException {
        final StringBuffer sBuffer = new StringBuffer();
        final BufferedReader br = new BufferedReader(new InputStreamReader(in));
        final char[] buffer = new char[1024];

        int cnt;
        while ((cnt = br.read(buffer, 0, buffer.length)) > -1) {
            sBuffer.append(buffer, 0, cnt);
        }
        br.close();
        in.close();
        return sBuffer.toString();
    }

    public static InputStream asInputStream(File f) throws FileNotFoundException {
        return new FileInputStream(f);
    }

}
