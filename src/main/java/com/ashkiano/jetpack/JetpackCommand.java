package com.ashkiano.jetpack;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;

public class JetpackCommand implements CommandExecutor {
    public static final String JETPACK_LORE = ChatColor.GRAY + "Jetpack";

    private String permission;

    public JetpackCommand(String permission) {
        this.permission = permission;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            //TODO udělat hlášku konfigurovatelnou
            sender.sendMessage("This command can only be used by a player!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(permission)) {
            //TODO udělat hlášku konfigurovatelnou
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        ItemStack jetpack = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) jetpack.getItemMeta();
        meta.setLore(Arrays.asList(JETPACK_LORE));
        jetpack.setItemMeta(meta);

        player.getInventory().addItem(jetpack);

        return true;
    }
}
