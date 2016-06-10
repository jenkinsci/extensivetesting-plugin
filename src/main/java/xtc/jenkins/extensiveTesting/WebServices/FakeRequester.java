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

public class FakeRequester implements IRequester{
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
    @Override
    public String httpRequest(String server, String params, String method, String sessionID) throws Exception {
        return "{\"message\":\"Log message\",\"session_id\":\"Njg1ZGRiYjcxZjcxNDQ0YmE1YjM5ZmRkYjJiMjY2ZjMzY\"," +
                "\"tests-listing\":[\"/Basics/04_CloseBrowser.tux\",\"/Basics/01_Win_OpenApp.tux\"," +
                "\"/Basics/05_MaximizeBrowser.tux\",\"/Basics/03_OpenBrowser.tux\",\"/Basics/02_Win_CloseApp.tux\"," +
                "\"/Basics/00_Wait.tux\"],\"test-id\":\"3030aa9cba7a7eb00051f31c1860eb27\",\"test-result\":\"pass\"," +
                "\"project-name\":\"Common\",\"test-status\":\"complete\"," +
                "\"test-report\":\"eNqzySjJzbGzScpPqbSzyTC0C0ktLlEoSCwuTk2x0QfybfQhUvpgdQB0Dw+i\"}";
    }
}
