package com.peepo.peepoPlugin;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class Listeners implements Listener {
    Random rand = new Random();
    private int randInt;
    PeepoMain plugin = PeepoMain.getPlugin(); //Plugin instance

    /* Slime death handler, adds a chance (configurable via config file) to spawn an extra slime on death
     * "Split" message won't go through since I changed it from "AttaxikaPlugin" to "PeepoPlugin"
     */
    @EventHandler
    public void onSlimeDeath(EntityDeathEvent event) {
        randInt = rand.nextInt(100);
        if(event.getEntityType() == EntityType.SLIME) {
            Slime s = (Slime) event.getEntity();
            Slime slime;
            if(randInt <= plugin.getConfig().getInt("SlimeRate") && event.getEntity().getKiller() != null && s.getSize() > 1) {
                s.getKiller().sendMessage(ChatColor.GREEN + "Split!");
                Location loc = s.getLocation();
                slime = (Slime) s.getWorld().spawn(loc, Slime.class);
                slime.setSize(s.getSize() - 1);
                event.getEntity().getWorld().playSound(loc, Sound.ENTITY_SLIME_SQUISH, 1f, 1f);
            }
        }
    }

    //Headshot detection. Minecraft hit detection is garbage from a range of <5, so the function had to be modified
    @EventHandler
    public void headshotDetection(ProjectileHitEvent event) {
        Entity damaged = event.getHitEntity();
        Entity projectile = event.getEntity();
        Arrow a = (Arrow) projectile;
        Entity damager = (Entity) a.getShooter();
        double projy = a.getLocation().getY();
        double victimy = damaged.getLocation().getY();
        double projdif = projy - victimy;
        double distanceDif = damaged.getLocation().distance(damager.getLocation());
        if(projdif >= 1.40D && projdif <= 1.79D && distanceDif >= 4D && damaged instanceof Player && ((Player) damaged).getInventory().getHelmet() == null) {
            damaged.sendMessage(ChatColor.RED + "Headshot");
            a.setDamage(a.getDamage() * 2);
            damaged.getWorld().playSound(event.getHitEntity().getLocation(), Sound.BLOCK_HONEY_BLOCK_BREAK, 10f, 0.5f);
        }
    }

    /* In the works... Trying to figure out how minecraft stores NBT data, need to apply an NBT tag to the arrow
     * then in the function below, check if the entity damaged was damaged by an arrow with that NBT data.
     * Alternative could be storing & reading data to and from a file? Not sure about the impact to performance regarding this
     */
    @EventHandler
    public void accelerateProj(ProjectileLaunchEvent event) {
        Entity e = (Entity) event.getEntity().getShooter();
        Location loc = e.getLocation();
        randInt = rand.nextInt(100);
        if(e instanceof Player && randInt <=plugin.getConfig().getInt("Accelerate")) {
            e.sendMessage(ChatColor.AQUA + "Accelerate!");
            e.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, loc, 20);
            e.getWorld().playSound(loc, Sound.BLOCK_NOTE_BLOCK_BELL, 10f, 1.0f);
            event.getEntity().setGlowing(true);
            event.getEntity().setMetadata("PeepoPlugin", new FixedMetadataValue(plugin, "accelerated"));
            event.getEntity().getVelocity().multiply(3);
        }
    }

    /* See above comment
     * For more info
     */
    @EventHandler
    public void acceleratorHit(ProjectileHitEvent event) {
        Entity damaged = event.getHitEntity();
        Entity proj = event.getEntity();
        PersistentDataContainer projData = proj.getPersistentDataContainer();
        if(proj instanceof Arrow && projData.has(new NamespacedKey(PeepoMain.getPlugin(), "Accelerate"), PersistentDataType.STRING)) {
            damaged.getServer().broadcastMessage("Test");
            ((Arrow) proj).setKnockbackStrength(((Arrow) proj).getKnockbackStrength() * 3);
        }
    }

    /* Also in the works, same thing as above
     *
     */
    @EventHandler
    public void pvpLogger(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();
        if(damager instanceof Player && damaged instanceof Player) {
            damager.sendMessage(ChatColor.DARK_RED + "You have attacked someone... PvP enabled.");
            damaged.sendMessage(ChatColor.DARK_RED + "You have been attacked... PvP enabled.");
        }
    }
}
