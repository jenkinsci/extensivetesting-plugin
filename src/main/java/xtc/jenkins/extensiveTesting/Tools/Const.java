package xtc.jenkins.extensiveTesting.Tools;

/**
 * Created by Blaise Cador on 20/06/2016.
 */
public class Const {
    // Strings
    public static final String PLUGIN_NAME = "eXtensive Testing";
    public static final String LOGIN = "login";
    public static final String PWD = "password";
    public static final String PROJECT_NAME = "project-name";
    public static final String TEST_LISTING = "tests-listing";
    public static final String TEST_PATH = "test-path";
    public static final String TEST_ID = "test-id";
    public static final String TEST_STATUS = "test-status";
    public static final String TEST_RESULT = "test-result";
    public static final String TEST_REPORT = "test-report";
    public static final String RUNNING = "running";
    public static final String SESSION_ID = "session_id";
    public static final String MESSAGE = "message";
    public static final String PASS = "pass";
    public static final String AUTHFAILED = "Authentification failed, please check your credentials";
    public static final String LOGINFAILED = "login problem";
    public static final String CONTENT = "Content-Type";
    public static final String CONTENT_TYPE = "application/json; charset=utf-8";
    public static final String COOKIE = "Cookie";
    public static final String UTF8 = "UTF-8";
    public static final String DATEFORMAT = "yyyyMMdd_HHmmss";
    public static final String REPORT_SUFIX = "_report.html";
    public static final String LOG_NAME = "/log.txt";
    public static final String FILE_ERR = "Erreur lors de la lecture : ";




    // REST Requests
    public static final String REST_LOGIN = "session/login";
    public static final String REST_LOGOUT = "session/logout";
    public static final String REST_LISTING = "tests/listing";
    public static final String REST_RUN = "tests/run";
    public static final String REST_RESULTS = "results/test/status";
    public static final String REST_REPORT = "results/test/report/html";
    public static final String REST_VERDICT = "results/test/verdict";


    // HTTP Methods
    public static final String GET = javax.ws.rs.HttpMethod.GET;
    public static final String POST = javax.ws.rs.HttpMethod.POST;
}
