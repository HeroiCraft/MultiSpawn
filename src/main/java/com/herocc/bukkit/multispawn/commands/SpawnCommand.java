package com.herocc.bukkit.multispawn.commands;

import com.herocc.bukkit.multispawn.MultiSpawn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
  private final MultiSpawn plugin = MultiSpawn.getPlugin();

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (plugin.getSpawnUtils().getNumberOfSpawns() == 0) {
      // Break if there are no spawns
      sender.sendMessage(ChatColor.RED + "There are no configured spawns!");
      return true;
    }
    if (args.length != 2 && !(sender instanceof Player)) {
      sender.sendMessage(ChatColor.RED + "Robots can't run that!");
      return true;
    }

    if (args.length == 0) {
      // Sender must be player if none is specified
      Player p = (Player) sender;
      if (p.hasPermission("multispawn.bycommand")){
        plugin.getSpawnUtils().sendPlayerToSpawn(p);
      } else {
        p.sendMessage(ChatColor.RED + "You don't have permission to do that!");
      }
      return true;
    } else if (args.length == 1) { // Sender must be player
      Player p = (Player) sender;
      String spawn = args[0]; // Spawn is first arg
      if (p.hasPermission("multispawn.bycommand." + spawn)){
        // If sender has permission to use command and specified spawn
        plugin.getSpawnUtils().sendPlayerToSpawn(p, spawn);
      } else {
        p.sendMessage(ChatColor.RED + "You don't have permission for that spawn!");
      }
      return true;
    } else if (args.length == 2) { // Assuming a spawn and player are specified
      String spawn = args[0];
      @SuppressWarnings("deprecation") // For some reason getting player by name is deprecated
      Player p = plugin.getServer().getPlayer(args[1]);
      if (p == null) { // If target is offline break
        sender.sendMessage(ChatColor.RED + "Player " + args[1] + " isn't online!");
        return true;
      }
      if (sender.hasPermission("multispawn.bycommand" + spawn) && sender.hasPermission("multispawn.others")){
        // If sender has permission to use command, to TP others, and specific spawn
        plugin.getSpawnUtils().sendPlayerToSpawn(p, spawn);
        sender.sendMessage(p.getDisplayName() + ChatColor.GREEN + " sent to '" + spawn + "'");
        return true;
      }
    } else {
      sender.sendMessage(ChatColor.RED + "Silly " + sender.getName() + ", you can't do that!");
      return true;
    }
    return false;
  }
}
