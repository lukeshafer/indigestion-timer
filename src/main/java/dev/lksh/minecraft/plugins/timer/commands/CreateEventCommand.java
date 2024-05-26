package dev.lksh.minecraft.plugins.timer.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;

public class CreateEventCommand implements CommandExecutor {
  private final IndigestionTimer plugin;

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Must be a player to execute this command.");
      plugin.getLogger().warning("Must be a player to execute this command.");
      return true;
    }

    if (args.length == 0) {
      sender.sendMessage("Argument required: event ID");
      return false;
    }

    String eventId = args[0];
    plugin.getDatabase().createTimerEvent(eventId);

    sender.sendMessage("Created Event '" + eventId + "'. You may want to run /setEventName, /setStartPos, and /setEndPos for the event.");
    return true;
  }

  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
    List<String> tab = new ArrayList<>();

    if (args.length != 0) {
      return tab;
    }

    tab.add("<event-id>");
    return tab;
  }

  public CreateEventCommand(IndigestionTimer plugin) {
    this.plugin = plugin;
  }
}
