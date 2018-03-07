package com.ygg.admin.filter;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.management.LockInfo;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author lorabit
 * @since 16-5-28
 */
public class CustomThreadDump {
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final ThreadMXBean threadMXBean;

    public CustomThreadDump(ThreadMXBean threadMXBean) {
        this.threadMXBean = threadMXBean;
    }

    /**
     * Dumps all of the threads' current information to an output stream.
     * mustContainTraceStr: 堆栈信息中必须包含字符串
     * state: [NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED]
     * @See Thread.State
     */
    public void dump(OutputStream out, String mustContainTraceStr, String state) {
        ThreadInfo[] threads = this.threadMXBean.dumpAllThreads(true, true);
        final PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, UTF_8));

        if (StringUtils.isNotEmpty(mustContainTraceStr)) {
            List<ThreadInfo> threadInfoList = new ArrayList<>();
            for (int ti = threads.length - 1; ti >= 0; ti--) {
                final ThreadInfo t = threads[ti];
                final StackTraceElement[] elements = t.getStackTrace();
                for (int i = 0; i < elements.length; i++) {
                    final StackTraceElement element = elements[i];
                    if(element.getClassName().contains("CustomThreadDump")
                            || element.getClassName().contains("JvmController")) {
                        continue;
                    }
                    if (element.getClassName().contains(mustContainTraceStr)) {
                        threadInfoList.add(t);
                        break;
                    }
                }
            }
            threads = threadInfoList.toArray(new ThreadInfo[threadInfoList.size()]);
        }
        if (StringUtils.isNotEmpty(state)) {
            List<ThreadInfo> threadInfoList = new ArrayList<>();
            for (int ti = threads.length - 1; ti >= 0; ti--) {
                final ThreadInfo t = threads[ti];
                if (state.equalsIgnoreCase(t.getThreadState().toString())) {
                    threadInfoList.add(t);
                }
            }
            threads = threadInfoList.toArray(new ThreadInfo[threadInfoList.size()]);
        }

        for (int ti = threads.length - 1; ti >= 0; ti--) {
            final ThreadInfo t = threads[ti];
            writer.printf("%s id=%d state=%s",
                    t.getThreadName(),
                    t.getThreadId(),
                    t.getThreadState());
            final LockInfo lock = t.getLockInfo();
            if (lock != null && t.getThreadState() != Thread.State.BLOCKED) {
                writer.printf("%n    - waiting on <0x%08x> (a %s)",
                        lock.getIdentityHashCode(),
                        lock.getClassName());
                writer.printf("%n    - locked <0x%08x> (a %s)",
                        lock.getIdentityHashCode(),
                        lock.getClassName());
            } else if (lock != null && t.getThreadState() == Thread.State.BLOCKED) {
                writer.printf("%n    - waiting to lock <0x%08x> (a %s)",
                        lock.getIdentityHashCode(),
                        lock.getClassName());
            }

            if (t.isSuspended()) {
                writer.print(" (suspended)");
            }

            if (t.isInNative()) {
                writer.print(" (running in native)");
            }

            writer.println();
            if (t.getLockOwnerName() != null) {
                writer.printf("     owned by %s id=%d%n", t.getLockOwnerName(), t.getLockOwnerId());
            }

            final StackTraceElement[] elements = t.getStackTrace();
            final MonitorInfo[] monitors = t.getLockedMonitors();

            for (int i = 0; i < elements.length; i++) {
                final StackTraceElement element = elements[i];
                writer.printf("    at %s%n", element);
                for (int j = 1; j < monitors.length; j++) {
                    final MonitorInfo monitor = monitors[j];
                    if (monitor.getLockedStackDepth() == i) {
                        writer.printf("      - locked %s%n", monitor);
                    }
                }
            }
            writer.println();

            final LockInfo[] locks = t.getLockedSynchronizers();
            if (locks.length > 0) {
                writer.printf("    Locked synchronizers: count = %d%n", locks.length);
                for (LockInfo l : locks) {
                    writer.printf("      - %s%n", l);
                }
                writer.println();
            }
        }

        writer.println();
        writer.flush();
    }


}
