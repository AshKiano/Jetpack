package com.ashkiano.jetpack;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class JetpackListener implements Listener {

    private final JavaPlugin plugin;

    public JetpackListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            checkForJetpack(player);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        checkForJetpack(player);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        checkForJetpack(player);
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (event.isSneaking() && isWearingJetpack(player)) {
            player.setVelocity(player.getLocation().getDirection().multiply(2).setY(1));
            spawnFlameParticles(player);
        }
    }

    private void spawnFlameParticles(Player player) {
        new BukkitRunnable() {
            public void run() {
                if(player.isSneaking() && isWearingJetpack(player)) {
                    player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 10, 0.1, 0.1, 0.1, 0.1);
                } else {
                    this.cancel(); // stop task when player isn't sneaking or not wearing a jetpack
                }
            }
        }.runTaskTimer(plugin, 0L, 1L); // run every tick
    }

    private void checkForJetpack(Player player) {
        ItemStack chestplate = player.getInventory().getChestplate();

        if (chestplate != null && chestplate.getType() == Material.LEATHER_CHESTPLATE) {
            ItemMeta meta = chestplate.getItemMeta();

            if (meta == null) return;
            if (meta.hasLore() && meta.getLore().contains(JetpackCommand.JETPACK_LORE)) {
                return;
            }
        }
    }

    private boolean isWearingJetpack(Player player) {
        ItemStack chestplate = player.getInventory().getChestplate();

        if (chestplate != null && chestplate.getType() == Material.LEATHER_CHESTPLATE) {
            ItemMeta meta = chestplate.getItemMeta();

            if (meta == null) return false;
            if (meta.hasLore() && meta.getLore().contains(JetpackCommand.JETPACK_LORE)) {
                return true;
            }
        }
        return false;
    }
}
