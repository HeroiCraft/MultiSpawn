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
    if (!(p.hasPermission("multispawn.noteleport"))){
      plugin.getSpawnUtils().sendPlayerToSpawn(p);
    }
  }
}
