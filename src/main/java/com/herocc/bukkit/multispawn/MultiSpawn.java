package com.herocc.bukkit.multispawn;

import com.herocc.bukkit.multispawn.commands.*;
import com.herocc.bukkit.multispawn.events.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

public class MultiSpawn extends JavaPlugin  {
  private static MultiSpawn instance;
  Random random = new Random();

  public static final MultiSpawn getPlugin() { return instance; }
  public SpawnUtils getSpawnUtils() { return new SpawnUtils(); }

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
    random = null;
    instance = null;
  }

  private void initMetrics() {
    try {
      new Metrics(this).start();
    } catch (IOException e) {
      this.getLogger().log(Level.FINER, "Metrics init failed!");
    }
  }

}

