package com.herocc.bukkit.multispawn.events;

import com.herocc.bukkit.multispawn.MultiSpawn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
  private final MultiSpawn plugin = MultiSpawn.getPlugin();

  @EventHandler
  @SuppressWarnings("unused")
  public void onPlayerJoin(PlayerJoinEvent ev) {
    final Player p = ev.getPlayer();
    if (!(p.hasPermission("multispawn.noteleport"))){
      plugin.sendPlayerToSpawn(p);
    }
  }
}
