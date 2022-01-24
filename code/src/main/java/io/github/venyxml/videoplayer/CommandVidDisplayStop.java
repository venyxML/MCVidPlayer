package io.github.venyxml.videoplayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;

public class CommandVidDisplayStop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        File key = new File("./plugins/depot/run.venyx");
        key.delete();
        return true;

    }
}
