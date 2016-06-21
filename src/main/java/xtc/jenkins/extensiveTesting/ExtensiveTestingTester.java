package xtc.jenkins.extensiveTesting;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AsyncAperiodicWork;
import hudson.model.PeriodicWork;
import hudson.model.Run;
import hudson.model.TaskListener;
import xtc.jenkins.extensiveTesting.entities.Session;
import xtc.jenkins.extensiveTesting.entities.Test;
import xtc.jenkins.extensiveTesting.tools.*;
import xtc.jenkins.extensiveTesting.webservices.Requester;
import xtc.jenkins.extensiveTesting.webservices.RestRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Blaise Cador on 20/06/2016.
 */
public class ExtensiveTestingTester {
    private String testPath;
    private String login;
    private String password;
    private String serverUrl;
    private String testId;
    private String projectName;
    private String hostUrl;
    private Boolean debug;


    public ExtensiveTestingTester(String testPath, String login, String password, String serverUrl,
                                  String testId, String projectName, String hostUrl, Boolean debug) {
        this.testPath = testPath;
        this.login = login;
        this.password = password;
        this.serverUrl = serverUrl;
        this.testId = testId;
        this.projectName = projectName;
        this.hostUrl = hostUrl;
        this.debug = debug;
    }

    /**
     * Execute the extensive testing job
     *
     * @param build
     * @param workspace
     * @param launcher
     * @param listener
     * @return
     */
    public Boolean perform(Run<?, ?> build, FilePath workspace, Launcher launcher, TaskListener listener) {

        File logFile = new File(workspace + Const.LOG_NAME);
        String jobName = build.getParent().getName().replace(" ", "%20"); // replace job name spaces for html link
        DateFormat dateFormat = new SimpleDateFormat(Const.DATEFORMAT); // get current date time with Calendar()
        Calendar calendar = Calendar.getInstance(); // get current date time with Calendar()
        String now = dateFormat.format(calendar.getTime()); // get current date time with Calendar()
        String reportName = now + Const.REPORT_SUFIX; // html report file name
        String encryptedPassword = Hasher.sha1(password); // hash password with sha1

        Logger logger = Logger.instance(logFile, listener, debug); // Singleton


        Test test = new Test(testPath, projectName, login, encryptedPassword);
        final Requester requester = new Requester(); // Http requester
        final RestRequest restRequest = new RestRequest(serverUrl, test, requester); // Rest requester
        Session session = new Session();

        // API Results Methods
        String login = null;
        String testRun = null;
        Integer testStatus;
        String testVerdict = null;
        String testReport = null;
        String logout = null;
        String report = null;
        String printReport = "#### " + testPath + "###\n";
        Boolean printable = true;
        Boolean debugmod = false;


        // Initialize logger first line
        logger.write(printReport, debugmod);
        /***** Starts the test and stores the results *****/
        // Login
        login = restRequest.login();
        //sleep(1);
        org.json.JSONObject jsonLogin = new org.json.JSONObject(login);
        session.setSession_id(jsonLogin.getString(Const.SESSION_ID));
        logger.write(login, debugmod); // Json brut


        if (login == null || session.getSession_id().isEmpty() || "".equals(session.getSession_id())) {
            logger.write(Const.AUTHFAILED, debugmod);
        } else {
            restRequest.setSessionID(session.getSession_id());

            // Launch test

            testRun = restRequest.testRun();
            //sleep(1);
            org.json.JSONObject jsonLaunchTest = new org.json.JSONObject(testRun);
            test.setTestId(jsonLaunchTest.getString(Const.TEST_ID));
            logger.write(testRun, debugmod); // Json brut


            // Get test status
            // TODO : PeriodicWork


            Looper getStatus = new Looper(restRequest);
            getStatus.run();

            /*
            do {
                testStatus = restRequest.testStatus();
                sleep(1);
            } while (testStatus != 1);
            test.setTestStatus(testStatus);
            */

            // Get test verdict

            testVerdict = restRequest.testVerdict();
            //sleep(1);
            org.json.JSONObject jsonTestVerdict = new org.json.JSONObject(testVerdict);
            test.setTestVerdict(jsonTestVerdict.getString(Const.TEST_RESULT));
            logger.write(testVerdict, debugmod); // Json brut


            // Get test report

            testReport = restRequest.testReport();
            //sleep(1);
            org.json.JSONObject jsonTestReport = new org.json.JSONObject(testReport);
            report = jsonTestReport.getString(Const.TEST_REPORT);
            test.setTestReport(report);
            logger.write(testReport, debugmod); // Json brut


            // Logout

            logout = restRequest.logout();
            //sleep(1);
            org.json.JSONObject jsonLogout = new org.json.JSONObject(logout);
            session.setMessage(jsonLogout.getString(Const.MESSAGE));
            logger.write(logout, debugmod); // Json brut

        }


        /***** store the results in html report file *****/
        // Write HTML Report File
        String htmlContent = Compressor.inflater(report);
        File file = new File(workspace + "/" + reportName);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(htmlContent);
            fileWriter.close();
        } catch (IOException exception) {
            System.out.println(Const.FILE_ERR + exception.getMessage());
        }


        /***** store the results in String*****/
        printReport += "Logging" + "\n";
        printReport += "Session = " + test.getTestId() + "\n";
        printReport += "Launching test" + "\n";
        printReport += "Status = " + test.getTestStatus() + "\n";
        printReport += "Getting test verdict" + "\n";
        printReport += "Verdict = " + test.getTestVerdict() + "\n";
        printReport += "Getting test report" + "\n";
        printReport += "Build duration = " + build.getDurationString() + "\n";
        printReport += "Message = " + session.getMessage() + "\n";
        printReport += "Test Report : " + "\n";
        printReport += hostUrl + "job/" + jobName + "/ws/" + reportName + "\n";
        printReport += "Log file : " + "\n";
        printReport += hostUrl + "job/" + jobName + "/ws/" + "log.txt" + "\n" + "\n";


        /***** Print results and log pages *****/
        logger.write(printReport, printable);

        if (Const.PASS.equals(test.getTestVerdict())) {
            return true;
        }else{
            return false;
        }


    }
    /**
     * Temporary stop the program
     *
     * @param timer
     */
    private static void sleep(Integer timer) {
        timer = timer * 1000;
        try {
            Thread.sleep(timer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
