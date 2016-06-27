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
package xtc.jenkins.extensivetesting.entities;

import com.google.gson.annotations.SerializedName;


public class Test {
    @SerializedName("test-id")
    private String testId;
    @SerializedName("project-name")
    private String projectName;
    @SerializedName("test-result")
    private String testResult;
    @SerializedName("test-status")
    private String testStatus;
    @SerializedName("test-report")
    private String testReport;
    private String testPath;
    private String login;
    private String password;
    private String testVerdict;


    public Test() {
    }

    public Test(String testPath, String projectName, String login, String password) {
        this.testPath = testPath;
        this.projectName = projectName;
        this.login = login;
        this.password = password;
    }

    public String getTestId() {
        return testId;
    }
    public void setTestId(String testId) {
        this.testId = testId;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getTestResult() {
        return testResult;
    }
    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }
    public String getTestStatus() {
        return testStatus;
    }
    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }
    public String getTestReport() {
        return testReport;
    }
    public void setTestReport(String testReport) {
        this.testReport = testReport;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getTestPath() {
        return testPath;
    }
    public void setTestPath(String testPath) {
        this.testPath = testPath;
    }

    public String getTestVerdict() {
        return testVerdict;
    }

    public void setTestVerdict(String testVerdict) {
        this.testVerdict = testVerdict;
    }
}
