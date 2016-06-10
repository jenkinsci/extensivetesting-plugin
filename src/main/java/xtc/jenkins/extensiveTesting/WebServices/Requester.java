/**
 * The MIT License

 Copyright (c) 2016, Gfi Informatique, Inc., Blaise Cador

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */
package xtc.jenkins.extensiveTesting.WebServices;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class Requester implements IRequester {
    /**
     * HTTP REST Requester
     *
     * @param server    REST API Server
     * @param params    Json string
     * @param method    HTTP Method (POST, GET, ...)
     * @param sessionID session ID
     * @return
     * @throws Exception
     */
    public String httpRequest(String server, String params, String method, String sessionID) throws Exception {

        URL url = new URL(server);
        method = method.toUpperCase();

        HttpURLConnection httpURLConnection =
                (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setAllowUserInteraction(false);
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        if (null != sessionID) {
            httpURLConnection.setRequestProperty("Cookie", sessionID);
        }


        if ("POST".equals(method)) {
            // Create the form content
            OutputStream out = httpURLConnection.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(params);
            writer.close();
            out.close();
        }


        if (httpURLConnection.getResponseCode() != 200) {
            int response = httpURLConnection.getResponseCode();
            System.out.println(response);
            throw new IOException(httpURLConnection.getResponseMessage());
        }

        // Buffer the result into a string
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();

        httpURLConnection.disconnect();
        return sb.toString();

    }
}
