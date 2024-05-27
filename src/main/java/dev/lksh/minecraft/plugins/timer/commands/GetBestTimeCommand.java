package dev.lksh.minecraft.plugins.timer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;

public class GetBestTimeCommand implements CommandExecutor {
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
    long bestTimeMS = plugin.getDatabase().getPlayerBestTimeInMS(eventId, player);
    String timeString = plugin.formatTime(bestTimeMS);

    player.sendMessage("Best time: " + timeString);

    return true;
  }

  public GetBestTimeCommand(IndigestionTimer plugin) {
    this.plugin = plugin;
  }
}
