package dev.lksh.minecraft.plugins.timer.commands;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;
import dev.lksh.minecraft.plugins.timer.types.TimerEventPlayer;
import dev.lksh.minecraft.plugins.timer.types.TimerPosition;
import java.time.Instant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartTimerCommand implements CommandExecutor {
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
    TimerPosition position = plugin.getDatabase().getStartPosition(eventId);

    if (position == null) {
      player.sendMessage(
          Component.text()
              .content("Error: the event " + eventId + " does not have a start position set.")
              .color(NamedTextColor.RED));
      return true;
    }

    if (plugin.getDatabase().getEndPosition(eventId) == null) {
      player.sendMessage(
          Component.text()
              .content("Warning: the event " + eventId + " does not have an end position set.")
              .color(NamedTextColor.YELLOW));
    }

    boolean isValidX = Math.abs(player.getLocation().getX() - position.x) < (position.margin + 1);
    boolean isValidY = Math.abs(player.getLocation().getY() - position.y) < (position.margin + 1);
    boolean isValidZ = Math.abs(player.getLocation().getZ() - position.z) < (position.margin + 1);

    if (!isValidX || !isValidY || !isValidZ) {
      player.sendMessage(
          "You must be in the correct start position for the event: "
              + position.x
              + ", "
              + position.y
              + ", "
              + position.z);
      return true;
    }

    if (!plugin.getDatabase().checkEventPlayerExists(eventId, player)) {
      plugin.getDatabase().createEventPlayer(eventId, player);
    }

    // check if timer is started, and if there is already a first time
    // if there is no first time, but the time has already started, don't restart
    TimerEventPlayer playerInfo = plugin.getDatabase().getPlayerInfo(eventId, player);
    if (playerInfo.currentTimeStart > 0 && playerInfo.firstTimeId == null) {
      player.sendMessage(
          Component.text()
              .content("Can't restart your first time - Reach the finish first!")
              .color(NamedTextColor.YELLOW));
      return true;
    }

    long now = Instant.now().toEpochMilli();
    plugin.getDatabase().setPlayerCurrentTimeStart(eventId, player, now);

    if (playerInfo.currentTimeStart > 0) {
      player.sendMessage(
          Component.text().content("Restarted timer!").color(NamedTextColor.GRAY));
    } else {
      player.sendMessage(
          Component.text().content("Started timer!").color(NamedTextColor.GRAY));
    }
    return true;
  }

  public StartTimerCommand(IndigestionTimer plugin) {
    this.plugin = plugin;
  }
}
