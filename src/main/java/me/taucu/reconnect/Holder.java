package me.taucu.reconnect;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Holder {
    
    private final ServerQueue parent;
    private AtomicBoolean wait;
    
    protected Holder(ServerQueue parent, AtomicBoolean wait) {
        this.parent = parent;
        this.wait = wait;
    }
    
    public synchronized void unlock() {
        if (wait != null) {
            synchronized (wait) {
                wait.set(false);
                wait.notifyAll();
            }
        }
        wait = null;
    }
    
    public long getHoldTime(TimeUnit unit) {
        return TimeUnit.NANOSECONDS.convert(parent.getCurrentHoldTime(), unit);
    }
    
    public long getWaitTime(TimeUnit unit) {
        return TimeUnit.NANOSECONDS.convert(parent.getCurrentWaitTime(), unit);
    }
    
}
