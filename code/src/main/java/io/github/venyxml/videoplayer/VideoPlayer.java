package io.github.venyxml.videoplayer;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class VideoPlayer extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("VidPlayer initializing...");
        this.getCommand("im-url").setExecutor(new CommandDisplayImage());
        this.getCommand("im-loc").setExecutor(new CommandDisplayLocalImage());
        //this.getCommand("vid-start").setExecutor(new CommandVidDisplay());
        this.getCommand("vid-start").setExecutor(new CommandFrameDisplay());
        this.getCommand("vid-stop").setExecutor(new CommandVidDisplayStop());
        Bukkit.getPluginManager().registerEvents(new CommandFrameDisplay(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("VidPlayer disabling...");
    }
}
