package dev.lksh.minecraft.plugins.timer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;
import dev.lksh.minecraft.plugins.timer.types.TimeResult;
import dev.lksh.minecraft.plugins.timer.utils.TimeComparator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class GetEventFirstTimesCommand implements CommandExecutor {
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
    int page = 1;
    if (args.length >= 2) {
      try {
        page = Integer.parseInt(args[1]);
      } catch (Exception e) {
        page = 0;
      }
    }

    int start = (page - 1) * 10;

    var times = plugin.getDatabase().getFirstTimes(eventId);

    if (times.size() == 0) {
      player.sendMessage("No times have been recorded for event " + eventId);
      return true;
    }

    if ((start + 1) > times.size()) {
      player.sendMessage("The specified page" + page + "does not exist");
      return true;
    }

    int end = Math.min(start + 10, times.size());

    times.sort(new TimeComparator());

    for (int i = start; i < end; i++) {
      TimeResult timeResult = times.get(i);
      String rankSuffix = "th";
      int rank = i + 1;
      if (rank % 10 == 1 && rank % 100 != 11) {
        rankSuffix = "st";
      } else if (rank % 10 == 2 && rank % 100 != 12) {
        rankSuffix = "nd";
      } else if (rank % 10 == 3 && rank % 100 != 13) {
        rankSuffix = "rd";
      }
      player
          .sendMessage(rank + rankSuffix + ": " + timeResult.username + ", " + plugin.formatTime(timeResult.timeInMS));
    }

    if (end < (times.size() - 1)) {
      player.sendMessage(Component.text().content("There are more results to view.").color(NamedTextColor.GREEN));
      player.sendMessage(
          Component.text().content("/geteventfirsttimes " + eventId + " " + page + 1).color(NamedTextColor.AQUA));
    }

    return true;
  }

  public GetEventFirstTimesCommand(IndigestionTimer plugin) {
    this.plugin = plugin;
  }
}
