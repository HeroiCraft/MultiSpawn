package com.herocc.bukkit.multispawn.commands;

import com.herocc.bukkit.multispawn.MultiSpawn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetSpawn implements CommandExecutor {
  private final MultiSpawn plugin = MultiSpawn.getPlugin();

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player){
      Player p = (Player) sender;
      String spawnName = "default";
      if (p.hasPermission("multispawn.setspawn")){
        if (args.length >= 1) { spawnName = args[0]; }
        plugin.setSpawn(p.getLocation(), spawnName);
        p.sendMessage(ChatColor.GREEN + "Set spawn " + ChatColor.BOLD  + spawnName + ChatColor.RESET + ChatColor.GREEN + " to your current location");
        return true;
      } else {
        sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
      }
    } else {
      sender.sendMessage(ChatColor.RED + "Sorry, CONSOLE can't set spawn locations!");
    }
    return false;
  }
}

