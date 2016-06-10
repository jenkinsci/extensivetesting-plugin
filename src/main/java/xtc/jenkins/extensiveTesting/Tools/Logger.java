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
package xtc.jenkins.extensiveTesting.Tools;


import hudson.model.TaskListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * TODO : Singleton
 */
public class Logger {
    private static Logger logger;
    private File log;
    private TaskListener listener;
    private Boolean debug;


    /**
     * Write Log file, Standard output and Jenkins console output
     *
     * @param message
     */
    public void write(String message, Boolean print)
    {
        //TODO: faire un mode debug avec une checkbox dans jenkins
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm"); // get current date time with Calendar()
        Calendar calendar = Calendar.getInstance(); // get current date time with Calendar()
        String now = dateFormat.format(calendar.getTime()); // get current date time with Calendar()
        message = now + "\t" + message + "\n";



        if(print){
            if(null != listener){listener.getLogger().println(message);}
            System.out.print(message);
        }else{
            if(debug) {
                if (null != listener) {listener.getLogger().println(message);}
                System.out.print(message);
            }
        }


        try {
            FileWriter fileWriter = new FileWriter(log,true);
            fileWriter.write(message);
            fileWriter.close();
        } catch (IOException exception) {
            System.out.println("Erreur lors de la lecture : " + exception.getMessage());
        }
    }

    public Logger(File log, TaskListener listener, Boolean debug) {
        this.log = log;
        this.listener = listener;
        this.debug = debug;
    }

    public static Logger instance(File logFile, TaskListener taskListener, Boolean debugMod){
        if(logger == null){
            logger = new Logger(logFile, taskListener, debugMod);
        }
        return logger;
    }
}
