package com.herocc.bukkit.multispawn.events;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMockFactory;
import com.herocc.bukkit.multispawn.GenericMultiSpawnTest;
import org.bukkit.Location;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PlayerJoinTest extends GenericMultiSpawnTest {
  @Test
  public void testPlayerMovesOnJoin() {
    PlayerMock p = new PlayerMockFactory(server).createRandomPlayer();
    Location start = p.getLocation();
    server.addPlayer(p);
    
    assertNotEquals(start, p.getLocation());
  }
  
  @Test
  public void testPlayerWithPermissionIsEventImmune() {
    PlayerMock p = new PlayerMockFactory(server).createRandomPlayer();
    Location start = p.getLocation();
    p.addAttachment(plugin).setPermission("multispawn.noteleport", true);
    server.addPlayer(p);
    
    assertEquals(start, p.getLocation());
  }
}
