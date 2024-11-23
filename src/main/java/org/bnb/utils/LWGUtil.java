package org.bnb.utils;

import java.io.*;

public class LWGUtil {

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
