package com.herocc.bukkit.multispawn;

import com.herocc.bukkit.multispawn.commands.*;
import com.herocc.bukkit.multispawn.events.*;
import org.bstats.bukkit.MetricsLite;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.Random;

public class MultiSpawn extends JavaPlugin {
  private static MultiSpawn instance;
  protected Random random = new Random();
  
  // For MockBukkit
  protected MultiSpawn(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
    super(loader, description, dataFolder, file);
  }

  public static final MultiSpawn getPlugin() { return instance; }
  public SpawnUtils getSpawnUtils() { return new SpawnUtils(); }

  @Override
  public void onEnable(){
    instance = this;

    getConfig().options().copyDefaults(true);
    this.saveConfig();

    this.getCommand("spawn").setExecutor(new SpawnCommand());
    this.getCommand("setspawn").setExecutor(new SetSpawn());
    this.getCommand("removespawn").setExecutor(new RemoveSpawn());
    this.getCommand("listspawns").setExecutor(new ListSpawns());

    this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    this.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
  
    try {
      Class.forName("be.seeseemelk.mockbukkit.MockBukkit");
    } catch (ClassNotFoundException e) {
      // Only start metrics on non-mocked instances
      MetricsLite metrics = new MetricsLite(this, 1022);
    }
  }

  @Override
  public void onDisable(){
    this.saveConfig();
  }
}

