package com.peepo.peepoPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandsCompleter implements TabCompleter {
    private static List<String> num = new ArrayList<>();
    private static List<String> firstArg = new ArrayList<>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        num.add("<0-100>");
        firstArgSetter();
        if(args.length == 1) {
            return firstArg;
        } else if(args.length == 2) {
            return num;
        } return null;
    }

    //Moved this down here for readability & future-usage
    private void firstArgSetter() {
        firstArg.add("Accelerate");
        firstArg.add("SlimeRate");
        firstArg.add("disable");
    }
}
