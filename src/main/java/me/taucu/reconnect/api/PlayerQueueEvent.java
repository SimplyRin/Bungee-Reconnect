package me.taucu.reconnect.api;

import com.google.common.base.Preconditions;
import me.taucu.reconnect.ServerQueue;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

import java.util.concurrent.TimeUnit;

public class PlayerQueueEvent extends Event implements Cancellable {
    
    private boolean cancelled = false;
    
    private final ServerInfo server;
    private final ServerQueue defaultQueue;
    private final ProxiedPlayer whom;
    private ServerQueue currentQueue;
    private long queueTimeout;
    private TimeUnit queueTimeoutUnit;
    
    public PlayerQueueEvent(ServerInfo server, ServerQueue queue, ProxiedPlayer whom, long queueTimeout,
            TimeUnit queueTimeoutUnit) {
        this.server = server;
        this.defaultQueue = queue;
        this.currentQueue = queue;
        this.whom = whom;
        this.queueTimeout = queueTimeout;
        this.queueTimeoutUnit = queueTimeoutUnit;
    }
    
    public ServerInfo getServer() {
        return server;
    }
    
    public ProxiedPlayer getPlayer() {
        return whom;
    }
    
    public boolean isCustomQueue() {
        return currentQueue != defaultQueue;
    }
    
    public ServerQueue getQueue() {
        return currentQueue;
    }
    
    public void setQueue(ServerQueue queue) {
        Preconditions.checkNotNull(queue);
        this.currentQueue = queue;
    }
    
    public ServerQueue getDefaultQueue() {
        return defaultQueue;
    }
    
    public long getTimeout() {
        return queueTimeout;
    }
    
    public void setTimeout(long timeout) {
        this.queueTimeout = timeout;
    }
    
    public TimeUnit getTimeoutUnit() {
        return queueTimeoutUnit;
    }
    
    public void setTimeoutUnit(TimeUnit timeoutUnit) {
        Preconditions.checkNotNull(timeoutUnit);
        this.queueTimeoutUnit = timeoutUnit;
    }
    
    @Override
    public boolean isCancelled() {
        return cancelled;
    }
    
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
    
}
