package com.herocc.bukkit.multispawn.commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.herocc.bukkit.multispawn.GenericMultiSpawnTest;
import org.bukkit.ChatColor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RemoveSpawnTest extends GenericMultiSpawnTest {
  @Test
  public void removeSpawnCommand() {
    PlayerMock p = server.addPlayer();
    p.addAttachment(plugin).setPermission("multispawn.removespawn", true);
    
    server.dispatchCommand(p, "removespawn default");
    assertEquals(0, plugin.getSpawnUtils().getNumberOfSpawns());
  }
  
  @Test
  public void removeSpawnCommandNoPermission() {
    PlayerMock p = server.addPlayer();
  
    server.dispatchCommand(p, "removespawn default");
    assertEquals(defaultSpawn, plugin.getSpawnUtils().getSpawnLocation("default"));
  }
  
  @Test
  public void missingTargetSpawn() {
    PlayerMock p = server.addPlayer();
    p.addAttachment(plugin).setPermission("multispawn.removespawn", true);
  
    server.dispatchCommand(p, "removespawn");
    assertEquals(defaultSpawn, plugin.getSpawnUtils().getSpawnLocation("default"));
    assertEquals(1, plugin.getSpawnUtils().getNumberOfSpawns());
  }
  
  @Test
  public void invalidTargetSpawn() {
    PlayerMock p = server.addPlayer();
    p.addAttachment(plugin).setPermission("multispawn.removespawn", true);
  
    assertNull(plugin.getSpawnUtils().getSpawnLocation("fakenews"));
    server.dispatchCommand(p, "removespawn fakenews");
    assertTrue(p.nextMessage().contains(ChatColor.RED.toString()));
    assertNull(plugin.getSpawnUtils().getSpawnLocation("fakenews"));
    
    assertEquals(defaultSpawn, plugin.getSpawnUtils().getSpawnLocation("default"));
    assertEquals(1, plugin.getSpawnUtils().getNumberOfSpawns());
  }
}
