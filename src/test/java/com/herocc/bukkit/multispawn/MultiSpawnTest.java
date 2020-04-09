package com.herocc.bukkit.multispawn;

import org.bukkit.Location;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MultiSpawnTest extends GenericMultiSpawnTest {
  
  @Test
  public void testSpawnUtilsRandomReserved() {
    assertFalse(plugin.getSpawnUtils().setSpawn(defaultSpawn, "random"));
    assertFalse(plugin.getSpawnUtils().getSpawns().contains("random"));
  }
  
  @Test
  public void listOfSpawnsValid() {
    assertEquals(1, plugin.getSpawnUtils().getNumberOfSpawns());
    assertEquals(defaultSpawn, plugin.getSpawnUtils().getSpawnLocation("default"));
    
    Location hell = new Location(server.getWorld("world"), 6, 6, 6);
    plugin.getSpawnUtils().setSpawn(hell, "hell");
    assertEquals(2, plugin.getSpawnUtils().getNumberOfSpawns());
    assertEquals(hell, plugin.getSpawnUtils().getSpawnLocation("hell"));
  }
  
  @Test
  public void testRemoveSpawn() {
    plugin.getSpawnUtils().removeSpawn("default");
    assertEquals(0, plugin.getSpawnUtils().getNumberOfSpawns());
  }
}
