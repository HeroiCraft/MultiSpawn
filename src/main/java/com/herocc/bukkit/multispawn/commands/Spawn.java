package com.herocc.bukkit.multispawn.commands;

import com.herocc.bukkit.multispawn.MultiSpawn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {
  private final MultiSpawn plugin = MultiSpawn.getPlugin();

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (plugin.getNumberOfSpawns() == 0){ sender.sendMessage(ChatColor.RED + "There are no configured spawns!"); return true;}
    if (sender instanceof Player){
      Player p = (Player) sender;
      if (args.length == 1){
        String spawn = args[0];
        if (p.hasPermission("multispawn.bycommand") && p.hasPermission("multispawn.spawn." + spawn)){
          plugin.sendPlayerToSpawn(p, spawn);
          return true;
        }
      } else if (args.length == 0){
        plugin.sendPlayerToSpawn(p);
        return true;
      }
    } else {
      sender.sendMessage(ChatColor.RED + "Silly " + sender.getName() + ", you can't do that!");
    }
    return false;
  }
}
