package com.herocc.bukkit.multispawn.commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.herocc.bukkit.multispawn.GenericMultiSpawnTest;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class SpawnCommandTest extends GenericMultiSpawnTest {
  
  // spawn
  @Test
  public void spawnCommandMoves() {
    PlayerMock p = server.addPlayer();
    p.addAttachment(plugin).setPermission("multispawn.bycommand", true);
    
    Location l = new Location(defaultSpawn.getWorld(), 90, 90, 90);
    p.setLocation(l);
    
    server.dispatchCommand(p, "spawn");
    assertNotEquals(l, p.getLocation());
    assertEquals(defaultSpawn, p.getLocation());
  }
  
  @Test
  public void spawnCommandPermissionCheck() {
    PlayerMock p = server.addPlayer();
    // No permission to use /spawn
    Location l = new Location(defaultSpawn.getWorld(), 90, 90, 90);
    p.setLocation(l);
    
    server.dispatchCommand(p, "spawn");
    assertTrue(p.nextMessage().contains(ChatColor.RED.toString()));
    assertEquals(l, p.getLocation());
  }
  
  // spawn <name>
  
  // spawn <name> <player>
  
}
