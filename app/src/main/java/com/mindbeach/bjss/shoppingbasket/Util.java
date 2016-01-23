package com.mindbeach.bjss.shoppingbasket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ken on 23/01/2016.
 */
public class Util {

    public static String convertInputStreamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            sb.append(line);
            sb.append('\n');
            line = bufferedReader.readLine();
        }
        return sb.toString();
    }

    public static String getContent(URL url) {
        String content = null;
        InputStream is = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");

            conn.connect();
            is = conn.getInputStream();
            return Util.convertInputStreamToString(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }
}
