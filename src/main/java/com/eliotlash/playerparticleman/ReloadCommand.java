package com.eliotlash.playerparticleman;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO add permission wrapper
        PlayerParticleMan.getInstance().reload();
        return true;
    }
}
