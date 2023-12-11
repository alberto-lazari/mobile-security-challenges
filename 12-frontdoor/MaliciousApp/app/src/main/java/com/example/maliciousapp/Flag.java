package com.mobiotsec.frontdoor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
/* loaded from: classes3.dex */
public class Flag {
    private static boolean debug = false;
    private static final String sUrl = "http://10.0.2.2:8085";

    Flag() {
    }

    public static String getFlag(String username, String password) throws Exception {
        String urlParameters;
        if (debug) {
            urlParameters = "username=testuser&password=passtestuser123";
        } else {
            urlParameters = "username=" + username + "&password=" + password;
        }
        int postDataLength = urlParameters.getBytes(StandardCharsets.UTF_8).length;
        HttpURLConnection conn = (HttpURLConnection) new URL("http://10.0.2.2:8085?" + urlParameters).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder content = new StringBuilder();
        while (true) {
            String line = rd.readLine();
            if (line == null) {
                return content.toString();
            }
            content.append(line).append("\n");
        }
    }
}
