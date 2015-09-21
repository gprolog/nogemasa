package com.nogemasa.util.system;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 收集系统负载信息
 * <br/>create at 15-5-18
 *
 * @author liufl
 * @since 1.0.0
 */
public class SystemInfo {
    private static Logger logger = LoggerFactory.getLogger(SystemInfo.class);

    static void addGetterIfAvaliable(Object obj, String getter, Map<String, Object> info) {
        // This is a 1.6 function, so lets do a little magic to *try* to make it work
        try {
            String n = Character.toUpperCase(getter.charAt(0)) + getter.substring(1);
            Method m = obj.getClass().getMethod("get" + n);
            m.setAccessible(true);
            Object v = m.invoke(obj, (Object[]) null);
            if (v != null) {
                info.put(getter, v);
            }
        } catch (Exception ignored) {
        } // don't worry, this only works for 1.6
    }

    private static String execute(String cmd) {
        InputStream in;
        Process process = null;

        try {
            process = Runtime.getRuntime().exec(cmd);
            in = process.getInputStream();
            // use default charset from locale here, because the command invoked also uses the default locale:
            return IOUtils.toString(new InputStreamReader(in, Charset.defaultCharset()));
        } catch (Exception ex) {
            // ignore - log.warn("Error executing command", ex);
            return "(error executing: " + cmd + ")";
        } catch (Error err) {
            if (err.getMessage() != null && (err.getMessage().contains("posix_spawn") || err.getMessage()
                    .contains("UNIXProcess"))) {
                logger.warn("Error forking command: " + err.getMessage());
                return "(error executing: " + cmd + ")";
            }
            throw err;
        } finally {
            if (process != null) {
                IOUtils.closeQuietly(process.getOutputStream());
                IOUtils.closeQuietly(process.getInputStream());
                IOUtils.closeQuietly(process.getErrorStream());
            }
        }
    }

    public static Map<String, Object> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();

        OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
        info.put("name", os.getName());
        info.put("version", os.getVersion());
        info.put("arch", os.getArch());
        info.put("systemLoadAverage", os.getSystemLoadAverage());

        // com.sun.management.OperatingSystemMXBean
        addGetterIfAvaliable(os, "committedVirtualMemorySize", info);
        addGetterIfAvaliable(os, "freePhysicalMemorySize", info);
        addGetterIfAvaliable(os, "freeSwapSpaceSize", info);
        addGetterIfAvaliable(os, "processCpuTime", info);
        addGetterIfAvaliable(os, "totalPhysicalMemorySize", info);
        addGetterIfAvaliable(os, "totalSwapSpaceSize", info);

        // com.sun.management.UnixOperatingSystemMXBean
        addGetterIfAvaliable(os, "openFileDescriptorCount", info);
        addGetterIfAvaliable(os, "maxFileDescriptorCount", info);

        try {
            if (!os.getName().toLowerCase(Locale.ROOT).startsWith("windows")) {
                // Try some command line things
                info.put("uname", execute("uname -a"));
                info.put("uptime", execute("uptime"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return info;
    }


    private static final long ONE_KB = 1024;
    private static final long ONE_MB = ONE_KB * ONE_KB;
    private static final long ONE_GB = ONE_KB * ONE_MB;

    /**
     * Return good default units based on byte size.
     */
    private static String humanReadableUnits(long bytes, DecimalFormat df) {
        String newSizeAndUnits;

        if (bytes / ONE_GB > 0) {
            newSizeAndUnits = String.valueOf(df.format((float) bytes / ONE_GB)) + " GB";
        } else if (bytes / ONE_MB > 0) {
            newSizeAndUnits = String.valueOf(df.format((float) bytes / ONE_MB)) + " MB";
        } else if (bytes / ONE_KB > 0) {
            newSizeAndUnits = String.valueOf(df.format((float) bytes / ONE_KB)) + " KB";
        } else {
            newSizeAndUnits = String.valueOf(bytes) + " bytes";
        }

        return newSizeAndUnits;
    }

    /**
     * Get JVM Info - including memory info
     */
    public static Map<String, Object> getJvmInfo() {
        Map<String, Object> jvm = new HashMap<>();

        final String javaVersion = System.getProperty("java.specification.version", "unknown");
        final String javaVendor = System.getProperty("java.specification.vendor", "unknown");
        final String javaName = System.getProperty("java.specification.name", "unknown");
        final String jreVersion = System.getProperty("java.version", "unknown");
        final String jreVendor = System.getProperty("java.vendor", "unknown");
        final String vmVersion = System.getProperty("java.vm.version", "unknown");
        final String vmVendor = System.getProperty("java.vm.vendor", "unknown");
        final String vmName = System.getProperty("java.vm.name", "unknown");

        // Summary Info
        jvm.put("version", jreVersion + " " + vmVersion);
        jvm.put("name", jreVendor + " " + vmName);

        // details
        Map<String, Object> java = new HashMap<>();
        java.put("vendor", javaVendor);
        java.put("name", javaName);
        java.put("version", javaVersion);
        jvm.put("spec", java);
        Map<String, Object> jre = new HashMap<>();
        jre.put("vendor", jreVendor);
        jre.put("version", jreVersion);
        jvm.put("jre", jre);
        Map<String, Object> vm = new HashMap<>();
        vm.put("vendor", vmVendor);
        vm.put("name", vmName);
        vm.put("version", vmVersion);
        jvm.put("vm", vm);


        Runtime runtime = Runtime.getRuntime();
        jvm.put("processors", runtime.availableProcessors());

        // not thread safe, but could be thread local
        DecimalFormat df = new DecimalFormat("#.#", DecimalFormatSymbols.getInstance(Locale.ROOT));

        Map<String, Object> mem = new HashMap<>();
        Map<String, Object> raw = new HashMap<>();
        long free = runtime.freeMemory();
        long max = runtime.maxMemory();
        long total = runtime.totalMemory();
        long used = total - free;
        double percentUsed = ((double) (used) / (double) max) * 100;
        raw.put("free", free);
        mem.put("free", humanReadableUnits(free, df));
        raw.put("total", total);
        mem.put("total", humanReadableUnits(total, df));
        raw.put("max", max);
        mem.put("max", humanReadableUnits(max, df));
        raw.put("used", used);
        mem.put("used", humanReadableUnits(used, df) +
                " (%" + df.format(percentUsed) + ")");
        raw.put("used%", percentUsed);

        mem.put("raw", raw);
        jvm.put("memory", mem);

        // JMX properties -- probably should be moved to a different handler
        Map<String, Object> jmx = new HashMap<>();
        try {
            RuntimeMXBean mx = ManagementFactory.getRuntimeMXBean();
            jmx.put("bootclasspath", mx.getBootClassPath());
            jmx.put("classpath", mx.getClassPath());

            // the input arguments passed to the Java virtual machine
            // which does not include the arguments to the main method.
            jmx.put("commandLineArgs", mx.getInputArguments());

            jmx.put("startTime", new Date(mx.getStartTime()));
            jmx.put("upTimeMS", mx.getUptime());

        } catch (Exception e) {
            logger.warn("Error getting JMX properties", e);
        }
        jvm.put("jmx", jmx);
        return jvm;
    }
}
