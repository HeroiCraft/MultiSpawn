package com.herocc.bukkit.multispawn;

import com.herocc.bukkit.multispawn.commands.*;
import com.herocc.bukkit.multispawn.events.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;


public class MultiSpawn extends JavaPlugin  {
  private static MultiSpawn instance;
  protected Random random = new Random();

  public static final MultiSpawn getPlugin() { return instance; }

  @Override
  public void onEnable(){
    instance = this;

    getConfig().options().copyDefaults(true);
    this.saveConfig();

    this.getCommand("spawn").setExecutor(new Spawn());
    this.getCommand("setspawn").setExecutor(new SetSpawn());
    this.getCommand("removespawn").setExecutor(new RemoveSpawn());
    this.getCommand("listspawns").setExecutor(new ListSpawns());

    this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    this.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);

    initMetrics();
  }

  @Override
  public void onDisable(){
    this.saveConfig();
    instance = null;
    random = null;
  }

  public Location getSpawnLocation(String name){
    String[] loc = getConfig().getString("spawns." + name + ".loc").split("\\,");
    World w = Bukkit.getWorld(loc[0]);
    Double x = Double.parseDouble(loc[1]);
    Double y = Double.parseDouble(loc[2]);
    Double z = Double.parseDouble(loc[3]);
    float yaw = Float.parseFloat(loc[4]);
    float pitch = Float.parseFloat(loc[5]);
    return new Location(w, x, y, z, yaw, pitch);
  }

  public void setSpawn(Location loc, String name) {
    String location = loc.getWorld().getName() + "," + loc.getX() + "," + (loc.getY() + 0.6) + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
    this.getConfig().set("spawns." + name + ".loc", location);
    this.saveConfig();
  }

  public void removeSpawn(String name){
    this.getConfig().set("spawns." + name, null);
    this.saveConfig();
  }

  public Set<String> getSpawns(){
    Set<String> spawns = getConfig().getConfigurationSection("spawns").getKeys(false);
    if (spawns.size() == 0) { return null; }
    return spawns;
  }

  public ArrayList<String> getSpawnsAsArray(){
    ArrayList<String> spawns = new ArrayList<>();
    for (String name : getSpawns()) {
      spawns.add(name);
    }
    return spawns;
  }

  public String getRandomSpawn(Player p){
    ArrayList<String> allowedSpawns = new ArrayList<>();
    for (String name : getSpawns()) {
      if (p.hasPermission("multispawn.spawn." + name)){ allowedSpawns.add(name); }
    }
    Collections.shuffle(allowedSpawns, random);
    return allowedSpawns.get(0);
  }

  public int getNumberOfSpawns(){
    try { getConfig().getConfigurationSection("spawns").getKeys(false); } catch (NullPointerException e) { return 0; }
    return getConfig().getConfigurationSection("spawns").getKeys(false).size();
  }

  public void sendPlayerToSpawn(Player p, String spawn){ p.teleport(getSpawnLocation(spawn)); }

  public void sendPlayerToSpawn(Player p){
    if (getNumberOfSpawns() == 0) { return; }
    sendPlayerToSpawn(p, getRandomSpawn(p));
  }

  public String getSpawn(int index){
    return getSpawnsAsArray().get(index);
  }

  private void initMetrics() {
    try {
      new Metrics(this).start();
    } catch (IOException e) {
      this.getLogger().log(Level.FINER, "Metrics init failed!");
    }
  }

}

