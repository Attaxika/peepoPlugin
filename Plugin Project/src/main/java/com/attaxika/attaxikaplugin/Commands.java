package com.attaxika.attaxikaplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Commands implements CommandExecutor {
    String[] commandList = {"disable","slimerate <percentage>","accelerate <percentage>"};
    String badUsage = "Hmm... Seems like you forgot something. Check out /AttaxikaPlugin for help with the plugin's commands!";
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Plugin p = sender.getServer().getPluginManager().getPlugin("AttaxikaPlugin");
            if(args[0] == "" || args[0] == null || args[0].isEmpty() || args[0].length() <= 0 || args[0].toString() == "null" || args[0].toString() == "") {
                return true;
            }
            switch(args[0].toLowerCase()) {
                default:
                    break;
                case "slimerate":
                    if(args[1] == "" || args[1] == null || args[1].isEmpty() || args[1].length() <= 0 || args[1].toString() == "null" || args[1].toString() == "") {
                        sender.sendMessage(badUsage);
                        return true;
                    }
                    sender.sendMessage("SlimeRate adjusted to: " + args[1]);
                    p.getConfig().set("SlimeRate", Integer.parseInt(args[1]));
                    p.saveConfig();
                    return true;
                case "accelerate":
                    if(args[1] == "" || args[1] == null || args[1].isEmpty() || args[1].length() <= 0 || args[1].toString() == "null" || args[1].toString() == "") {
                        sender.sendMessage(badUsage);
                        return true;
                    }
                    sender.sendMessage("Accelerate adjusted to: " + args[1]);
                    p.getConfig().set("Accelerate", Integer.parseInt(args[1]));
                    p.saveConfig();
                    return true;
                case "disable":
                    sender.getServer().getPluginManager().disablePlugin(sender.getServer().getPluginManager().getPlugin("AttaxikaPlugin"));
                    return true;
            }
        } catch (org.bukkit.command.CommandException e) {
            sender.sendMessage("Fatal exception, probably don't do that again (:");
            e.printStackTrace();
        }
        return true;
    }
}