package com.herocc.bukkit.multispawn;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;

public class GenericMultiSpawnTest {
  protected ServerMock server;
  protected MultiSpawn plugin;
  protected Location defaultSpawn;
  
  @Before
  public void setup() {
    server = MockBukkit.mock();
    plugin = MockBukkit.load(MultiSpawn.class);
    
    WorldMock world = server.addSimpleWorld("world");
    defaultSpawn = new Location(world, 1, 2, 3);
    plugin.getSpawnUtils().setSpawn(defaultSpawn, "default");
  }
  
  @After
  public void tearDown() {
    MockBukkit.unload();
  }
}
