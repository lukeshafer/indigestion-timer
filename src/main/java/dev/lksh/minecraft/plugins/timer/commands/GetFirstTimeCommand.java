package dev.lksh.minecraft.plugins.timer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;

public class GetFirstTimeCommand implements CommandExecutor {
  private final IndigestionTimer plugin;

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("You must be a player to use this command.");
      return true;
    }
    Player player = (Player) sender;
    if (args.length < 1) {
      return false;
    }

    String eventId = args[0];
    long firstTimeMS = plugin.getDatabase().getPlayerFirstTimeInMS(eventId, player);
    String timeString = plugin.formatTime(firstTimeMS);

    player.sendMessage("First time: " + timeString);

    return true;
  }

  public GetFirstTimeCommand(IndigestionTimer plugin) {
    this.plugin = plugin;
  }
}
