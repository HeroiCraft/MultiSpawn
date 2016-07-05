package com.herocc.bukkit.multispawn.commands;

import com.herocc.bukkit.multispawn.MultiSpawn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RemoveSpawn implements CommandExecutor {
  private final MultiSpawn plugin = MultiSpawn.getPlugin();

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender.hasPermission("multispawn.removespawn")){
      if (args.length == 1) {
        if (plugin.getSpawnUtils().getSpawns().contains(args[0])) {
          plugin.getSpawnUtils().removeSpawn(args[0]);
          sender.sendMessage(ChatColor.GREEN + "Removed spawn " + ChatColor.BOLD + args[0]);
          return true;
        } else {
          sender.sendMessage(ChatColor.RED + "Hmmm... that spawn doesn't seem to exist!");
          sender.sendMessage(ChatColor.RED + "This command IS case sensitive, so please check your spawn name");
          return true;
        }
      } else {
        sender.sendMessage(ChatColor.RED + "Sorry, you don't have permissions to remove spawns!");
        return true;
      }
    } else {
      sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
      return true;
    }
  }
}
