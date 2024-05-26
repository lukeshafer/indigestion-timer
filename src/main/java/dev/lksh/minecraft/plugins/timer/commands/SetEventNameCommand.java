package dev.lksh.minecraft.plugins.timer.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;

public class SetEventNameCommand implements CommandExecutor {
  private final IndigestionTimer plugin;

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Must be a player to execute this command.");
      plugin.getLogger().warning("Must be a player to execute this command.");
      return true;
    }

    if (args.length < 2) {
      return false;
    }

    String eventId = args[0];
    String eventName = "";
    for (int i = 1; i < args.length; i++) {
      eventName += args[i] + " ";
    }
    
    plugin.getDatabase().setEventName(eventId, eventName.trim());

    sender.sendMessage("Updated event name.");
    return true;
  }

  public SetEventNameCommand(IndigestionTimer plugin) {
    this.plugin = plugin;
  }
}
