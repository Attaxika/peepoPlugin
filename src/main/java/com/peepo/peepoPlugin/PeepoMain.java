package com.peepo.peepoPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class PeepoMain extends JavaPlugin {
private static int errCode = 0;
private static PeepoMain plugin;

    @Override
    public void onEnable() {

        //Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        //Register Event Listeners
        System.out.println("Loaded PeepoPlugin");
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        //Commands
        getCommand("PeepoMain").setExecutor(new Commands());
        getCommand("PeepoMain").setTabCompleter(new CommandsCompleter());
    }

    @Override
    public void onDisable() {
        System.out.println("PeepoPlugin Disabled, view error code/reason below");
        System.out.println("Error Code: " + getErrCode());
        switch(getErrCode()) {
            default:
                break;
            case 0:
                System.out.println("Default shutdown");
            case 1:
                System.out.println("Shutdown via command.");
            case 2:
                System.out.println("Unknown shutdown.");
        }
    }

    public static void setErrCode(int e) {
        errCode = e;
    }

    public static int getErrCode() {
        return errCode;
    }

    public static PeepoMain getPlugin() {
        return plugin;
    }

}
