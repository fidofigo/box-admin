package com.ygg.admin.util;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;

/**
 * @author lorabit
 * @since 16-5-9 性能 度量
 */
public class AdminMetric
{
    public static final MetricRegistry core = new MetricRegistry();
    
    public static Meter meter = null;
    
    public static Meter errMeter = null;
    
    public static Timer timer = null;
    
    static
    {
        
        // core.register("jvm.buffers", new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
        core.register("jvm.gc", new GarbageCollectorMetricSet());
        // core.register("jvm.classes", new ClassLoadingGaugeSet());
        core.register("jvm.memory", new MemoryUsageGaugeSet());
        core.register("jvm.threads", new ThreadStatesGaugeSet());
        meter = core.meter("admin");
        errMeter = core.meter("admin" + "-err");
        timer = core.timer("admin" + "-timer");
    }
}
