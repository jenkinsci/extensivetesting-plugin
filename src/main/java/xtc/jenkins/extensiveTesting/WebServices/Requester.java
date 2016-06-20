/**
 * The MIT License
 * <p>
 * Copyright (c) 2016, Gfi Informatique, Inc., Blaise Cador
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package xtc.jenkins.extensiveTesting.WebServices;

import xtc.jenkins.extensiveTesting.Tools.Const;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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
     */
    public String httpRequest(String server, String params, String method, String sessionID) {

        URL url = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            url = new URL(server);

            method = method.toUpperCase();

            HttpURLConnection httpURLConnection =
                    (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setRequestProperty(Const.CONTENT, Const.CONTENT_TYPE);
            if (null != sessionID) {
                httpURLConnection.setRequestProperty(Const.COOKIE, sessionID);
            }


            if (Const.POST.equals(method)) {
                // Create the form content
                OutputStream out = httpURLConnection.getOutputStream();
                Writer writer = new OutputStreamWriter(out, Const.UTF8);
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
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            httpURLConnection.disconnect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return stringBuilder.toString();
        }

    }
}
