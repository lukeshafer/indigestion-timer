package dev.lksh.minecraft.plugins.timer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;
import dev.lksh.minecraft.plugins.timer.types.TimeResult;
import dev.lksh.minecraft.plugins.timer.utils.TimeComparator;

public class GetEventBestTimesCommand implements CommandExecutor {
  private final IndigestionTimer plugin;

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("You must be a player to use this command.");
      return true;
    }
    Player player = (Player) sender;
    if (args.length < 1) {
      sender.sendMessage("" + args.length);
      return false;
    }

    String eventId = args[0];
    var bestTimes = plugin.getDatabase().getBestTimes(eventId);
    bestTimes.sort(new TimeComparator());

    for (TimeResult timeResult : bestTimes) {
      player.sendMessage(timeResult.username + ": " + plugin.formatTime(timeResult.timeInMS));
    }

    return true;
  }

  public GetEventBestTimesCommand(IndigestionTimer plugin) {
    this.plugin = plugin;
  }
}
