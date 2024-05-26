package dev.lksh.minecraft.plugins.timer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;

public class SetEventStartPositionCommand implements CommandExecutor {
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

    Player player = (Player) sender;
    int posX = (int) Math.floor(player.getPlayer().getLocation().getX());
    int posY = (int) Math.floor(player.getPlayer().getLocation().getY());
    int posZ = (int) Math.floor(player.getPlayer().getLocation().getZ());
    int margin = 1;
    if (args.length >= 2) {
      margin = Integer.parseInt(args[1]);
    }

    String eventId = args[0];
    plugin.getDatabase().setStartPosition(eventId, posX, posY, posZ, margin);

    sender.sendMessage("Set start position to " + posX + " " + posY + " " + posZ + ", margin = " + margin);
    return true;
  }

  public SetEventStartPositionCommand(IndigestionTimer plugin) {
    this.plugin = plugin;
  }
}
