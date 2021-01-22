package com.herocc.bukkit.multispawn.events;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMockFactory;
import com.herocc.bukkit.multispawn.GenericMultiSpawnTest;
import org.bukkit.Location;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PlayerDeathTest extends GenericMultiSpawnTest {
  public final String TP_ON_DEATH_CONFIG_PATH = "teleportOnDeath";
  
  @Test
  public void testPlayerCorrectRespawnBehavior() {
    PlayerMock p = new PlayerMockFactory(server).createRandomPlayer();
    Location start = p.getLocation();
    server.addPlayer(p);
    p.setLocation(start);
    
    plugin.getConfig().set(TP_ON_DEATH_CONFIG_PATH, true);
    
    p.setHealth(0);
    assertEquals(start, p.getLocation()); // Player has not respawned yet, so should be same
    System.out.println("respaqwning");
    p.respawn();
    assertEquals(defaultSpawn, p.getLocation()); // Should respawn at spawn -- enabled, and no permission otherwise
  }
  
  @Test
  public void testRespawnNoPermission() {
    PlayerMock p = new PlayerMockFactory(server).createRandomPlayer();
    Location start = p.getLocation();
    server.addPlayer(p);
    p.setLocation(start);
  
    plugin.getConfig().set(TP_ON_DEATH_CONFIG_PATH, true);
    p.addAttachment(plugin).setPermission("multispawn.noteleportondeath", true);
    
    p.setHealth(0);
    p.respawn();
    assertNotEquals(defaultSpawn, p.getLocation()); // Enabled in config, but player has exception permission
  }
  
  @Test
  public void testRespawnConfigDisabled() {
    PlayerMock p = new PlayerMockFactory(server).createRandomPlayer();
    Location start = p.getLocation();
    server.addPlayer(p);
    p.setLocation(start);
  
    plugin.getConfig().set(TP_ON_DEATH_CONFIG_PATH, false);
  
    p.setHealth(0);
    p.respawn();
    assertNotEquals(defaultSpawn, p.getLocation()); // Disabled in config, so no go
  }
  
}
