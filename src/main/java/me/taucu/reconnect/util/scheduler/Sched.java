package me.taucu.reconnect.util.scheduler;

import me.taucu.reconnect.Reconnect;
import net.md_5.bungee.api.ProxyServer;

import java.util.concurrent.TimeUnit;

public class Sched {
    
    /**
     * Schedules a task and executes it asynchronously.
     *
     * @param runnable The Runnable that should be executed asynchronously after the
     *                 specified time.
     * @param time     The amount of time the task should be scheduled.
     * @param timeUnit The {@link TimeUnit} of the time parameter.
     */
    public static void scheduleAsync(final Reconnect instance, final Runnable runnable, long time, TimeUnit timeUnit) {
        ProxyServer.getInstance().getScheduler().schedule(instance, () -> ProxyServer.getInstance().getScheduler().runAsync(instance, runnable), time, timeUnit);
    }
    
}
