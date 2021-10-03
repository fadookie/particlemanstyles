package com.eliotlash.playerparticleman;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO add permission wrapper
        PlayerParticleMan.getInstance().reload();
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pp reload");
        return true;
    }
}
