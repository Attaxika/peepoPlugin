package com.peepo.peepoPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class PeepoMain extends JavaPlugin {
private static int errCode = 0;
private static PeepoMain plugin;

    @Override
    public void onEnable() {
        plugin = this;
        //Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        //Register Event Listeners
        System.out.println("Loaded PeepoPlugin");
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        //Commands
        getCommand("PeepoPlugin").setExecutor(new Commands());
        getCommand("PeepoPlugin").setTabCompleter(new CommandsCompleter());
    }

    @Override
    public void onDisable() {
        System.out.println("PeepoPlugin Disabled");
    }

    public static PeepoMain getPlugin() {
        return plugin;
    }

}
