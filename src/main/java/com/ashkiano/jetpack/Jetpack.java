package com.ashkiano.jetpack;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

//TODO přidat plamínky když se použije
//TODO udělat možnost aby jetpack spotřebovával třeba lávu nebo něco jiného
//TODO možnost craftitelného paliva se speciálním lore, které bude spotřebovávat
public class Jetpack extends JavaPlugin {

    private String permission;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        permission = config.getString("command_permission", "jetpack");
        getCommand("jetpack").setExecutor(new JetpackCommand(permission));
        getServer().getPluginManager().registerEvents(new JetpackListener(this), this);

        Metrics metrics = new Metrics(this, 19172);
    }
}