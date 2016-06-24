package xtc.jenkins.extensiveTesting.tools;

/**
 * Created by Blaise Cador on 20/06/2016.
 */
public class Const {
    // Strings TODO : Version EN/FR
    public static final String PLUGIN_NAME = "Launch eXtensive Testing job";
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
    public static final String NOTRUNNING = "not-running";
    public static final String SESSION_ID = "session_id";
    public static final String MESSAGE = "message";
    public static final String PASS = "pass";
    public static final String SUCCESS = "SUCCESS";
    public static final String AUTHFAILED = "Authentification failed, please check your credentials";
    public static final String LOGINFAILED = "login problem";
    public static final String LOGGERFAILED = "Logger does not exist";
    public static final String CONTENT = "Content-Type";
    public static final String CONTENT_TYPE = "application/json";
    public static final String SESSION_COOKIE = "Cookie";
    public static final String UTF8 = "UTF-8";
    public static final String DATEFORMAT = "yyyyMMdd_HHmmss";
    public static final String REPORT_SUFIX = "_report.html";
    public static final String LOG_NAME = "/log.txt";
    public static final String FILE_ERR = "Error reading file : ";
    public static final String EXCEPT_URL = "URL exception";
    public static final String EXCEPT_ENCODING = "Encoding exception";
    public static final String EXCEPT_PROTOCOL = "Protocol exception";
    public static final String EXCEPT_IO = "IO exception";
    public static final String KO = "KO";
    public static final String HTTPERR = "Failed : HTTP error code : ";
    public static final String ACCEPT = "accept";
    public static final String SERVOUT = "Output from Server .... \n";




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
