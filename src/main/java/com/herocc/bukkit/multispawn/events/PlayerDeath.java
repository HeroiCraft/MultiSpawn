package com.herocc.bukkit.multispawn.events;

import com.herocc.bukkit.multispawn.MultiSpawn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
  private final MultiSpawn plugin = MultiSpawn.getPlugin();

  @EventHandler
  @SuppressWarnings("unused")
  public void onPlayerDeath(PlayerDeathEvent ev) {
    final Player p = ev.getEntity();
    if (p.hasPermission("multispawn.noteleport") // If player is excluded
        || plugin.getSpawnUtils().getSpawns(p, false) == null // If spawns are null
        || plugin.getSpawnUtils().getSpawns(p, false).isEmpty() // If spawns are empty
        || plugin.getConfig().getBoolean("useDefaultAsFallback")) return; // Stop if we don't use default spawn
    
    plugin.getSpawnUtils().sendPlayerToSpawn(p); // Teleport player if spawn list isn't empty
  }
}
