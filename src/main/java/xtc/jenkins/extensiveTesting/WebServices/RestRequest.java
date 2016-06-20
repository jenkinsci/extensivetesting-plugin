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

import org.json.JSONArray;
import org.json.JSONObject;
import xtc.jenkins.extensiveTesting.Entities.Test;
import xtc.jenkins.extensiveTesting.Tools.Const;

import java.util.List;

public class RestRequest {
    private String serverUrl;
    private Test test;
    private String sessionID;
    private IRequester requester;



    /**
     * Create user session
     *
     * @return session id
     */
    public String login(){
        String server = serverUrl + Const.REST_LOGIN;
        //String params = "{\"login\": \"" + test.getLogin() + "\", \"password\": \"" + test.getPassword() + "\"}";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Const.LOGIN, test.getLogin());
        jsonObject.put(Const.PWD, test.getPassword());
        String params = jsonObject.toString();

        return requester.httpRequest(server, params, Const.POST, sessionID);
    }

    /**
     * Logout user
     *
     * @return message
     */
    public String logout(){
        String server = serverUrl + Const.REST_LOGOUT;
        return requester.httpRequest(server, null, Const.GET, sessionID);
    }


    /**
     * Get the list of tests available on the server
     *
     * @return List of String testPath
     */
    public List<String> testList(){
        String server = serverUrl + Const.REST_LISTING;
        //String params = "{\"project-name\": \"" + test.getProjectName() + "\"}";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Const.PROJECT_NAME, test.getProjectName());
        jsonObject.put(Const.PWD, test.getPassword());
        String params = jsonObject.toString();

        String output = requester.httpRequest(server, params, Const.POST, sessionID);

        List<String> outputArray = null;
        JSONObject jsonTestList = new JSONObject(output);
        JSONArray arrayTestList = jsonTestList.getJSONArray(Const.TEST_LISTING);
        for (int i = 0; i < arrayTestList.length(); i++) {
            outputArray.add(arrayTestList.getString(i));
        }
        return outputArray;
    }

    /**
     * Launch test run
     *
     * @return test id
     */
    public String testRun(){
        String server = serverUrl + Const.REST_RUN;
        //String params = "{\"test-path\": \"" + test.getTestPath() + "\"," + "\"project-name\": \"" + test.getProjectName() + "\"}";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Const.TEST_PATH, test.getTestPath());
        jsonObject.put(Const.PROJECT_NAME, test.getProjectName());
        String params = jsonObject.toString();

        return requester.httpRequest(server, params, Const.POST, sessionID);
    }

    /**
     * Get a test status ( running , not running , complete )
     *
     * @return Test run status 1ok 0nok
     */
    public Integer testStatus(){
        String server = serverUrl + Const.REST_RESULTS;
        //String params = "{\"test-id\": \"" + test.getTestId() + "\"," + "\"project-name\": \"" + test.getProjectName() + "\"}";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Const.TEST_ID, test.getTestId());
        jsonObject.put(Const.PROJECT_NAME, test.getProjectName());
        String params = jsonObject.toString();

        String output = requester.httpRequest(server, params, Const.POST, sessionID);

        JSONObject jsonLogout = new JSONObject(output);
        String status = jsonLogout.getString(Const.TEST_STATUS);
        System.out.println(status);

        if (Const.RUNNING.equals(status)) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * get test report
     *
     * @return test report (gziped html)
     */
    public String testReport(){
        String server = serverUrl + Const.REST_REPORT;
       // String params = "{\"test-id\": \"" + test.getTestId() + "\"," + "\"project-name\": \"" + test.getProjectName() + "\"}";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Const.TEST_ID, test.getTestId());
        jsonObject.put(Const.PROJECT_NAME, test.getProjectName());
        String params = jsonObject.toString();

        return requester.httpRequest(server, params, Const.POST, sessionID);
    }

    /**
     * Get test verdict
     *
     * @return Verdict json string
     */
    public String testVerdict(){
        String server = serverUrl + Const.REST_VERDICT;
        //String params = "{\"test-id\": \"" + test.getTestId() + "\"," + "\"project-name\": \"" + test.getProjectName() + "\"}";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Const.TEST_ID, test.getTestId());
        jsonObject.put(Const.PROJECT_NAME, test.getProjectName());
        String params = jsonObject.toString();


        return requester.httpRequest(server, params, Const.POST, sessionID);
    }


    // Constructor | Getters | Setters
    public RestRequest(String serverUrl, Test test, IRequester requester) {
        this.serverUrl = serverUrl;
        this.test = test;
        this.requester = requester;
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
