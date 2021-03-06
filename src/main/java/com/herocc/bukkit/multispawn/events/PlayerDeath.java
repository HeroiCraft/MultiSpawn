package com.herocc.bukkit.multispawn.events;

import com.herocc.bukkit.multispawn.MultiSpawn;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeath implements Listener {
  private final MultiSpawn plugin = MultiSpawn.getPlugin();

  @EventHandler
  @SuppressWarnings("unused")
  public void onPlayerDeath(PlayerRespawnEvent ev) {
    final Player p = ev.getPlayer();
    if (p.hasPermission("multispawn.noteleportondeath") // If player is excluded
        || !plugin.getConfig().getBoolean("teleportOnDeath") // If disabled in config.yml
        || plugin.getSpawnUtils().getSpawns(p, false).isEmpty()) return; // If spawns are empty
      
    if (plugin.getSpawnUtils().getSpawns(p, true).size() == 1
      && plugin.getSpawnUtils().getSpawns(p, true).contains("default")
      && !plugin.getConfig().getBoolean("useDefaultAsFallback", true)) return;
    
    String spawnName = plugin.getSpawnUtils().getSpawns(p, true).get(0);
    Location spawnLoc = plugin.getSpawnUtils().getSpawnLocation(spawnName);
    ev.setRespawnLocation(spawnLoc);
  }
}
