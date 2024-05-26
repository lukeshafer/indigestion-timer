package dev.lksh.minecraft.plugins.timer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;

public class GetEventNameCommand implements CommandExecutor {
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
    String eventName = plugin.getDatabase().getEventName(eventId);

    if (eventName == null) {
      sender.sendMessage("No event found for ID " + eventId);
      return true;
    }

    if (eventName.equals("")) {
      sender.sendMessage("Event name has not yet been set for " + eventId);
      return true;
    }

    sender.sendMessage(eventName);
    return true;
  }

  public GetEventNameCommand(IndigestionTimer plugin) {
    this.plugin = plugin;
  }
}
