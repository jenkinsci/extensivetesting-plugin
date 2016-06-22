package xtc.jenkins.extensiveTesting.tools;

import hudson.Extension;
import hudson.model.PeriodicWork;
import xtc.jenkins.extensiveTesting.webservices.RestRequest;

import java.lang.reflect.Method;

/**
 * Created by Blaise Cador on 21/06/2016.
 */
@Extension
public class Looper extends PeriodicWork{
    private RestRequest restRequest;
    private Integer count = 0;
    //private Method method;

    public Looper(RestRequest restRequest) {
        this.restRequest = restRequest;
    }

    public Looper() {

    }

    @Override
    public long getRecurrencePeriod() {
        return 1000;
    }

    @Override
    protected void doRun() throws Exception {
        System.out.println("loop " + getCount());
        setCount(getCount() + 1);
        /*
        Integer testStatus;
        do {
            testStatus = restRequest.testStatus();
        } while (testStatus != 1);
        */
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
