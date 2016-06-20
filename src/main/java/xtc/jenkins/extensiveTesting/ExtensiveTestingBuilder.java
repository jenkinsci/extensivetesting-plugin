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
package xtc.jenkins.extensiveTesting;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import xtc.jenkins.extensiveTesting.Entities.Session;
import xtc.jenkins.extensiveTesting.Entities.Test;
import xtc.jenkins.extensiveTesting.Tools.Compressor;
import xtc.jenkins.extensiveTesting.Tools.Const;
import xtc.jenkins.extensiveTesting.Tools.Hasher;
import xtc.jenkins.extensiveTesting.Tools.Logger;
import xtc.jenkins.extensiveTesting.WebServices.Requester;
import xtc.jenkins.extensiveTesting.WebServices.RestRequest;

import javax.servlet.ServletException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Sample {@link Builder}.
 * When the user configures the project and enables this builder,
 * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked
 * and a new {@link ExtensiveTestingBuilder} is created. The created
 * instance is persisted to the project configuration XML by using
 * XStream, so this allows you to use instance fields (like {@link #testPath})
 * to remember the configuration.
 * <p>
 * <p>
 * When a build is performed, the {@link #perform} method will be invoked.
 */
public class ExtensiveTestingBuilder extends Builder implements SimpleBuildStep {

    private final String testPath;
    private final String login;
    private final String password;
    private final String serverUrl;
    private final String testId;
    private final String projectName;
    private final String hostUrl;
    private final Boolean debug;

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public ExtensiveTestingBuilder(String testPath, String login, String password, String serverUrl, String testId, String projectName, String hostUrl, Boolean debug) {
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
     * We'll use this from the <tt>config.jelly</tt>.
     *
     * @return
     */
    public String getName() {
        return testPath;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getTestId() {
        return testId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public String getTestPath() {
        return testPath;
    }

    public Boolean getDebug() {
        return debug;
    }

    /**
     * This is where you 'build' the project
     *
     * @param build     Project Build
     * @param workspace Project workspace
     * @param launcher  Project launcher
     * @param listener  Project listener
     *                  <p>
     *                  TODO : Il serait possible de creer un job particulier pour le test
     *                  TODO : qui serait lance apres le job de build de l'application
     */
    @Override
    public void perform(Run<?, ?> build, FilePath workspace, Launcher launcher, TaskListener listener) {

        File logFile = new File(workspace + Const.LOG_NAME);
        String jobName = build.getParent().getName().replace(" ", "%20"); // replace job name spaces for html link
        DateFormat dateFormat = new SimpleDateFormat(Const.DATEFORMAT); // get current date time with Calendar()
        Calendar calendar = Calendar.getInstance(); // get current date time with Calendar()
        String now = dateFormat.format(calendar.getTime()); // get current date time with Calendar()
        String reportName = now + Const.REPORT_SUFIX; // html report file name
        String encryptedPassword = Hasher.sha1(password); // hash password with sha1

        // TODO : Régler le problème de logger | Actuellement le singleton ne fonctionne pas
        //Logger logger = Logger.instance(logFile, listener, debug); // Singleton
        Logger logger = new Logger(logFile, listener, debug); // Test

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
        sleep(1);
        org.json.JSONObject jsonLogin = new org.json.JSONObject(login);
        session.setSession_id(jsonLogin.getString(Const.SESSION_ID));
        logger.write(login, debugmod); // Json brut


        if (login == null || session.getSession_id().isEmpty() || "".equals(session.getSession_id())) {
            logger.write(Const.AUTHFAILED, debugmod);
        } else {
            restRequest.setSessionID(session.getSession_id());

            // Launch test

            testRun = restRequest.testRun();
            sleep(1);
            org.json.JSONObject jsonLaunchTest = new org.json.JSONObject(testRun);
            test.setTestId(jsonLaunchTest.getString(Const.TEST_ID));
            logger.write(testRun, debugmod); // Json brut


            // Get test status

            do {
                testStatus = restRequest.testStatus();
                sleep(1);
            } while (testStatus != 1);
            test.setTestStatus(testStatus);


            // Get test verdict

            testVerdict = restRequest.testVerdict();
            sleep(1);
            org.json.JSONObject jsonTestVerdict = new org.json.JSONObject(testVerdict);
            test.setTestVerdict(jsonTestVerdict.getString(Const.TEST_RESULT));
            logger.write(testVerdict, debugmod); // Json brut


            // Get test report

            testReport = restRequest.testReport();
            sleep(1);
            org.json.JSONObject jsonTestReport = new org.json.JSONObject(testReport);
            report = jsonTestReport.getString(Const.TEST_REPORT);
            test.setTestReport(report);
            logger.write(testReport, debugmod); // Json brut


            // Logout

            logout = restRequest.logout();
            sleep(1);
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


        /** SET BUILD STATUS **/
        if (!Const.PASS.equals(test.getTestVerdict())) {
            Result result = Result.FAILURE;
            build.setResult(result);
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


    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    /**
     * Descriptor for {@link ExtensiveTestingBuilder}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     * <p>
     * <p>
     * See <tt>src/main/resources/hudson/plugins/hello_world/ExtensiveTestingBuilder/*.jelly</tt>
     * for the actual HTML fragment for the configuration screen.
     */
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        /**
         * To persist global configuration information,
         * simply store it in a field and call save().
         * <p>
         * <p>
         * If you don't want fields to be persisted, use <tt>transient</tt>.
         */
        private boolean useFrench;

        /**
         * In order to load the persisted global configuration, you have to
         * call load() in the constructor.
         */
        public DescriptorImpl() {
            load();
        }

        /**
         * Performs on-the-fly validation of the form field 'name'.
         *
         * @param value This parameter receives the value that the user has typed.
         * @return Indicates the outcome of the validation. This is sent to the browser.
         * <p>
         * Note that returning {@link FormValidation#error(String)} does not
         * prevent the form from being saved. It just means that a message
         * will be displayed to the user.
         */
        public FormValidation doCheckName(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error("Please set a name");
            if (value.length() < 4)
                return FormValidation.warning("Isn't the name too short?");
            return FormValidation.ok();
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "eXtensive Testing";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            // To persist global configuration information,
            // set that to properties and call save().
            useFrench = formData.getBoolean("useFrench");
            // ^Can also use req.bindJSON(this, formData);
            //  (easier when there are many fields; need set* methods for this, like setUseFrench)
            save();
            return super.configure(req, formData);
        }

        /**
         * This method returns true if the global configuration says we should speak French.
         * <p>
         * The method name is bit awkward because global.jelly calls this method to determine
         * the initial state of the checkbox by the naming convention.
         */
        public boolean getUseFrench() {
            return useFrench;
        }
    }
}

