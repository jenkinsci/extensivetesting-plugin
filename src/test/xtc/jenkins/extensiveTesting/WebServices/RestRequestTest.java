package xtc.jenkins.extensiveTesting.webservices;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Blaise Cador on 01/06/2016.
 */
public class RestRequestTest {
    private String mock = "{\"message\":\"Log message\",\"session_id\":\"Njg1ZGRiYjcxZjcxNDQ0YmE1YjM5ZmRkYjJiMjY2ZjMzY\"" +
            ",\"tests-listing\":[\"/Basics/04_CloseBrowser.tux\",\"/Basics/01_Win_OpenApp.tux\"" +
            ",\"/Basics/05_MaximizeBrowser.tux\",\"/Basics/03_OpenBrowser.tux\",\"/Basics/02_Win_CloseApp.tux\"" +
            ",\"/Basics/00_Wait.tux\"],\"test-id\":\"3030aa9cba7a7eb00051f31c1860eb27\",\"test-result\":\"pass\"" +
            ",\"project-name\":\"Common\",\"test-status\":\"complete\"" +
            ",\"test-report\":\"eNqzySjJzbGzScpPqbSzyTC0C0ktLlEoSCwuTk2x0QfybfQhUvpgdQB0Dw+i\"}";
    String serverUrl = "";
    xtc.jenkins.extensiveTesting.entities.Test test = new xtc.jenkins.extensiveTesting.entities.Test("localhost", "Common", "admin", "");
    IRequester requester = new FakeRequester();
    RestRequest restRequest = new RestRequest(serverUrl,test,requester);


    @Test
    public void login() throws Exception {
        Assert.assertEquals(mock, restRequest.login());
    }

    @Test
    public void logout() throws Exception {
        Assert.assertEquals(mock, restRequest.logout());
    }

    @Test
    public void testRun() throws Exception {
        Assert.assertEquals(mock, restRequest.testRun());
    }

    @Test
    public void testStatus() throws Exception {
        Assert.assertEquals(Integer.valueOf(1), restRequest.testStatus());
    }

    @Test
    public void testReport() throws Exception {
        Assert.assertEquals(mock, restRequest.testReport());
    }

    @Test
    public void testVerdict() throws Exception {
        Assert.assertEquals(mock, restRequest.testVerdict());
    }

}