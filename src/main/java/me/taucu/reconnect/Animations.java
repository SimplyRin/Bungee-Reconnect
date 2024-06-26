package me.taucu.reconnect;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Animations {
    
    private final Reconnect instance;
    private volatile ConcurrentHashMap<String, Animation> animations = new ConcurrentHashMap<>();
    
    public Animations(Reconnect instance) {
        this.instance = instance;
    }
    
    public String animate(Reconnector connector, String string) {
        for (Animation animation : animations.values()) {
            string = animation.animate(connector, string);
        }
        return string;
    }
    
    public Animation get(String placeholder) {
        return animations.get(placeholder);
    }
    
    public void put(Animation animation) {
        animations.put(animation.getPlaceholder(), animation);
    }
    
    public boolean remove(Animation animation) {
        return remove(animation.getPlaceholder());
    }
    
    public boolean remove(String placeholder) {
        return animations.remove(placeholder) != null;
    }
    
    public void set(Map<String, Animation> map) {
        this.animations = new ConcurrentHashMap<>(map);
    }
    
    public void clear() {
        animations.clear();
    }
    
    public boolean deserialize(Configuration config) {
        boolean wasErrorless = true;
        ConcurrentHashMap<String, Animation> animations = new ConcurrentHashMap<>();
        for (String name : config.getKeys()) {
            try {
                Configuration animation = config.getSection(name);
                animations.put(name,
                        new Animation(name, animation.getInt("Delay Millis"), TimeUnit.MILLISECONDS,
                                animation.getStringList("Animation").stream()
                                        .map(string -> ChatColor.translateAlternateColorCodes('&', string))
                                        .collect(Collectors.toList())));
            } catch (Exception e) {
                instance.getLogger().log(Level.SEVERE, "Error while deserializing animation", e);
                wasErrorless = false;
            }
        }
        this.animations = animations;
        return wasErrorless;
    }
    
}
