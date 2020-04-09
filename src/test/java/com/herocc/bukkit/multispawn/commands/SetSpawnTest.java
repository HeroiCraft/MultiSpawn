package com.herocc.bukkit.multispawn.commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.herocc.bukkit.multispawn.GenericMultiSpawnTest;
import org.bukkit.Location;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class SetSpawnTest extends GenericMultiSpawnTest {
  @Test
  public void setSpawnCommandPlayer() {
    PlayerMock p = server.addPlayer();
    p.addAttachment(plugin).setPermission("multispawn.setspawn", true);
    
    p.setLocation(new Location(server.getWorld("world"), 9, 9, 9));
    server.dispatchCommand(p, "setspawn");
    assertEquals(p.getEyeLocation(), plugin.getSpawnUtils().getSpawnLocation("default"));
    assertEquals(1, plugin.getSpawnUtils().getNumberOfSpawns());
    
    p.setLocation(new Location(server.getWorld("world"), 7, 7, 7));
    server.dispatchCommand(p, "setspawn newSpawn");
    assertEquals(p.getEyeLocation(), plugin.getSpawnUtils().getSpawnLocation("newSpawn"));
    assertEquals(2, plugin.getSpawnUtils().getNumberOfSpawns());
  }
  
  @Test
  public void setSpawnCommandNoPermission() {
    PlayerMock p = server.addPlayer();
    p.setLocation(new Location(server.getWorld("world"), 9, 9, 9));
    
    server.dispatchCommand(p, "setspawn");
    assertNotEquals(p.getEyeLocation(), plugin.getSpawnUtils().getSpawnLocation("default"));
    
    server.dispatchCommand(p, "setspawn newSpawn");
    assertNotEquals(p.getEyeLocation(), plugin.getSpawnUtils().getSpawnLocation("newSpawn"));
    assertNotEquals(2, plugin.getSpawnUtils().getNumberOfSpawns());
  }
  
  @Test
  public void setSpawnCommandRandomReserved() {
    PlayerMock p = server.addPlayer();
    
    server.dispatchCommand(p, "setspawn random");
    assertFalse(plugin.getSpawnUtils().getSpawns().contains("random"));
  }
}
