package com.zhuangjy.framework.utils;

/**
 * Created by johnny on 16/4/15.
 */
/**
 *
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class ShellUtil {

    private static Log logger = LogFactory.getLog(ShellUtil.class);

    public static boolean isLinux() {
        if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) {
            return true;
        }
        return false;
    }

    public static int runShell(String command) {

        if (!isLinux()) {
            logger.info(command);
            return -1;
        }

        Process p = null;

        InputStreamReader is = null;
        LineNumberReader input = null;

        InputStreamReader es = null;
        LineNumberReader error = null;

        try {
            logger.info("execute command: " + command);

            p = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
            is = new InputStreamReader(p.getInputStream());
            input = new LineNumberReader(is);
            es = new InputStreamReader(p.getErrorStream());
            error = new LineNumberReader(es);

            p.waitFor();

            return p.exitValue();

        } catch (Exception e) {
            logger.error("run shell error", e);
            return -1;

        } finally {
            try {

                if (input != null)
                    input.close();

                if (is != null)
                    is.close();

                if (p != null)
                    p.destroy();

            } catch (Exception e) {
                // do nothing
            }

        }

    }

    public static int runShell(String[] command) {

        if (!isLinux()) {
            return -1;
        }

        Process p = null;

        InputStreamReader is = null;
        LineNumberReader input = null;

        InputStreamReader es = null;
        LineNumberReader error = null;

        try {
            p = Runtime.getRuntime().exec(command);
            is = new InputStreamReader(p.getInputStream());
            input = new LineNumberReader(is);
            es = new InputStreamReader(p.getErrorStream());
            error = new LineNumberReader(es);

            p.waitFor();

            return p.exitValue();

        } catch (Exception e) {
            logger.error("run shell error", e);
            return -1;

        } finally {
            try {

                if (input != null)
                    input.close();

                if (is != null)
                    is.close();

                if (p != null)
                    p.destroy();

            } catch (Exception e) {
                // do nothing
            }

        }

    }

    /**
     * 获取执行结果
     *
     * @param cmd
     * @return
     */
    public static String runShell4Result(String cmd) {
        if (!isLinux()) {
            return "";
        }
        String result = "";
        Process p = null;
        BufferedReader in = null;
        InputStreamReader isr = null;
        InputStream is = null;

        try {
            p = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd});
            is = p.getInputStream();
            isr = new InputStreamReader(is);
            in = new BufferedReader(isr);
            String line = in.readLine();
            while (line != null) {
                result += line + "\n";
                line = in.readLine();
            }
            p.waitFor();

        } catch (Exception e) {
            logger.error("", e);

        } finally {
            try {

                if (is != null)
                    is.close();

                if (isr != null)
                    isr.close();

                if (in != null)
                    in.close();

                if (p != null)
                    p.destroy();

            } catch (Exception e) {
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(runShell4Result("ls ~/"));
    }
}
