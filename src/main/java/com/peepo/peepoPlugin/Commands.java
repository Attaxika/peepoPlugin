package com.peepo.peepoPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Commands implements CommandExecutor {

    /* Also, my commands are SUPER scuffed...
     * /peepoPlugin causes some sort of internal error
     * Error-handling is near non-existent (You can see at the bottom, the catch statement tries to catch it, but nothing happens)
     * Furthermore, even for checking for arguments, it doesn't work. Not sure why that is.
     * Planning on fixing at some other point
     */

    String badUsage = "Hmm... Seems like you forgot something. Check out /PeepoMain for help with the plugin's commands!";
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Plugin p = PeepoMain.getPlugin();
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
                    sender.getServer().getPluginManager().disablePlugin(p);
                    return true;
            }
        } catch (org.bukkit.command.CommandException e) {
            sender.sendMessage("Fatal exception, probably don't do that again (:");
            e.printStackTrace();
        }
        return true;
    }
}
