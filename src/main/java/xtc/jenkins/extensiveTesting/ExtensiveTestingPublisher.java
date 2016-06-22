package xtc.jenkins.extensiveTesting;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import org.kohsuke.stapler.DataBoundConstructor;
import xtc.jenkins.extensiveTesting.tools.Const;

/**
 * Created by Blaise Cador on 20/06/2016.
 */
public class ExtensiveTestingPublisher extends Recorder {

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
    public ExtensiveTestingPublisher(String testPath, String login, String password, String serverUrl, String testId, String projectName, String hostUrl, Boolean debug) {
        this.testPath = testPath;
        this.login = login;
        this.password = password;
        this.serverUrl = serverUrl;
        this.testId = testId;
        this.projectName = projectName;
        this.hostUrl = hostUrl;
        this.debug = debug;
    }


    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) {

        FilePath workspace = build.getWorkspace();
        Result resultFailure = Result.FAILURE;

        if (Const.SUCCESS.equals(build.getResult().toString())) {
            ExtensiveTestingTester test = new ExtensiveTestingTester(testPath, login, password, serverUrl, testId, projectName, hostUrl, debug);
            Boolean testVerdict = test.perform(build, workspace, launcher, listener);

            if (!testVerdict) {
                build.setResult(resultFailure);
            }

        } else {
            build.setResult(resultFailure);
        }
        return true;
    }


    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    /**
     * Descriptor for {@link ExtensiveTestingPublisher}. Used as a singleton. The class is
     * marked as public so that it can be accessed from views.
     * See
     * <tt>src/main/resources/hudson/plugins/hello_world/HelloWorldBuilder/*.jelly</tt>
     * for the actual HTML fragment for the configuration screen.
     */
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        @SuppressWarnings("rawtypes")
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return Const.PLUGIN_NAME;
        }
    }

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }


}
