package xtc.jenkins.extensivetesting.tools;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

public class ApacheHttpClientPost {

    public static String request(String url, String method, String params, String sessionCookie) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url + method);

            StringEntity input = new StringEntity(params);
            if (null != sessionCookie) {
                postRequest.addHeader(Const.SESSION_COOKIE, sessionCookie);
            }
            input.setContentType(Const.CONTENT_TYPE);
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException(Const.HTTPERR
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            System.out.println(Const.SERVOUT);
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                stringBuilder.append(output);
            }

            httpClient.getConnectionManager().shutdown();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return stringBuilder.toString();
    }

}

