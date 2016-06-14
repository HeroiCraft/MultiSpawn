package com.herocc.bukkit.multispawn.commands;

import com.herocc.bukkit.multispawn.MultiSpawn;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListSpawns implements CommandExecutor {
  private final MultiSpawn plugin = MultiSpawn.getPlugin();

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender.hasPermission("multispawn.list")){
      if (plugin.getNumberOfSpawns() == 0){ sender.sendMessage(ChatColor.RED + "There are no configured spawns!"); return true;}
      sender.sendMessage(ChatColor.GREEN + "There are " + plugin.getNumberOfSpawns() + " spawns:");
      for (int i=0; i<plugin.getNumberOfSpawns(); i++){
        String spawn = plugin.getSpawn(i);
        Location spawnLoc = plugin.getSpawnLocation(spawn);
        //TODO Color spawn name red if player doesn't have permission
        sender.sendMessage(ChatColor.GREEN + Integer.toString(i+1) + ". " + spawn + ": " +
            spawnLoc.getWorld().getName() + ", " + spawnLoc.getBlockX() + "X, " + spawnLoc.getBlockY() + "Y, " + spawnLoc.getBlockZ() + "Z");
      }
    } else {
      sender.sendMessage(ChatColor.RED + "You don't have permission to list spawns!");
    }
    return true;
  }

}
