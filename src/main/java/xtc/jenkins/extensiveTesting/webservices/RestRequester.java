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


package xtc.jenkins.extensiveTesting.webservices;

import org.json.JSONObject;
import xtc.jenkins.extensiveTesting.entities.Test;
import xtc.jenkins.extensiveTesting.tools.ApacheHttpClientGet;
import xtc.jenkins.extensiveTesting.tools.ApacheHttpClientPost;
import xtc.jenkins.extensiveTesting.tools.Const;

public class RestRequester {
    private String serverUrl;
    private Test test;
    private String sessionID;


    /**
     * Create user session
     *
     * @return session id
     */
    public String login() {
        String server = serverUrl + Const.REST_LOGIN;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Const.LOGIN, test.getLogin());
        jsonObject.put(Const.PWD, test.getPassword());
        String params = jsonObject.toString();

        return ApacheHttpClientPost.request(serverUrl, Const.REST_LOGIN, params, sessionID);
    }

    /**
     * Logout user
     *
     * @return message
     */
    public String logout() {
        String server = serverUrl + Const.REST_LOGOUT;
        return ApacheHttpClientGet.request(serverUrl, Const.REST_LOGOUT, sessionID);
    }


    /**
     * Launch test run
     *
     * @return test id
     */
    public String testRun() {
        String server = serverUrl + Const.REST_RUN;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Const.TEST_PATH, test.getTestPath());
        jsonObject.put(Const.PROJECT_NAME, test.getProjectName());
        String params = jsonObject.toString();

        return ApacheHttpClientPost.request(serverUrl, Const.REST_RUN, params, sessionID);
    }

    /**
     * Get a test status ( running , not running , complete )
     *
     * @return Test run status
     */
    public String testStatus() {
        String server = serverUrl + Const.REST_RESULTS;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Const.TEST_ID, test.getTestId());
        jsonObject.put(Const.PROJECT_NAME, test.getProjectName());
        String params = jsonObject.toString();

        return ApacheHttpClientPost.request(serverUrl, Const.REST_RESULTS, params, sessionID);
    }

    /**
     * get test report
     *
     * @return test report (gziped html)
     */
    public String testReport() {
        String server = serverUrl + Const.REST_REPORT;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Const.TEST_ID, test.getTestId());
        jsonObject.put(Const.PROJECT_NAME, test.getProjectName());
        String params = jsonObject.toString();

        return ApacheHttpClientPost.request(serverUrl, Const.REST_REPORT, params, sessionID);
    }

    /**
     * Get test verdict
     *
     * @return Verdict json string
     */
    public String testVerdict() {
        String server = serverUrl + Const.REST_VERDICT;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Const.TEST_ID, test.getTestId());
        jsonObject.put(Const.PROJECT_NAME, test.getProjectName());
        String params = jsonObject.toString();

        return ApacheHttpClientPost.request(serverUrl, Const.REST_VERDICT, params, sessionID);
    }


    // Constructor | Getters | Setters
    public RestRequester(String serverUrl, Test test) {
        this.serverUrl = serverUrl;
        this.test = test;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = Const.SESSION_ID + "=" + sessionID;
    }
}
