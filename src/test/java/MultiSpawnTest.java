import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMockFactory;
import com.herocc.bukkit.multispawn.MultiSpawn;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MultiSpawnTest {
  private ServerMock server;
  private MultiSpawn plugin;
  private Location defaultSpawn;
  
  @Before
  public void setup() {
    server = MockBukkit.mock();
    plugin = MockBukkit.load(MultiSpawn.class);
    
    WorldMock world = server.addSimpleWorld("world");
    defaultSpawn = new Location(world, 1, 2, 3);
    plugin.getSpawnUtils().setSpawn(defaultSpawn, "default");
  }
  
  @Test
  public void setSpawnCommandPlayer() {
    PlayerMock p = server.addPlayer();
    Location loc = p.getEyeLocation();
    
    p.setLocation(new Location(server.getWorld("world"), 9, 9, 9));
    server.dispatchCommand(p, "setspawn");
    assertEquals(loc, plugin.getSpawnUtils().getSpawnLocation("default"));
    
    p.setLocation(new Location(server.getWorld("world"), 7, 7, 7));
    server.dispatchCommand(p, "setspawn newSpawn");
    assertEquals(loc, plugin.getSpawnUtils().getSpawnLocation("newSpawn"));
    assertEquals(2, plugin.getSpawnUtils().getNumberOfSpawns());
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
  
  @Test
  public void testPlayerMoves() {
    PlayerMock player = new PlayerMockFactory(server).createRandomPlayer();
    Location start = player.getLocation();
    server.addPlayer(player);
    
    plugin.getSpawnUtils().sendPlayerToSpawn(player);
    
    assertNotEquals(start, player.getLocation());
  }
  
  @After
  public void tearDown() {
    MockBukkit.unload();
  }
}
