package dev.lksh.minecraft.plugins.timer.commands;

import java.time.Instant;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;
import dev.lksh.minecraft.plugins.timer.types.TimerPosition;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class StopTimerCommand implements CommandExecutor {
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
    TimerPosition position = plugin.getDatabase().getEndPosition(eventId);

    if (position == null) {
      player.sendMessage(Component
          .text()
          .content("Error: the event " + eventId + " does not have an end position set.")
          .color(NamedTextColor.RED));
      return true;
    }

    boolean isValidX = Math.abs(player.getLocation().getX() - position.x) < (position.margin + 1);
    boolean isValidY = Math.abs(player.getLocation().getY() - position.y) < (position.margin + 1);
    boolean isValidZ = Math.abs(player.getLocation().getZ() - position.z) < (position.margin + 1);

    if (!isValidX || !isValidY || !isValidZ) {
      player.sendMessage("You must be in the correct end position to stop the timer: "
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

    long startTime = plugin.getDatabase().getPlayerCurrentTimeStart(eventId, player);

    if (startTime == 0) {
      player.sendMessage("The timer hasn't been started.");
      return true;
    }

    long endTime = Instant.now().toEpochMilli();
    long timeMS = endTime - startTime;

    var timeId = plugin.getDatabase().createEventTime(eventId, player, startTime, endTime);

    if (timeId == null) {
      player.sendMessage("An error occurred while saving this time, so it will not be recorded. Your time was "
          + plugin.formatTime(timeMS));
    }

    plugin.getDatabase().removePlayerTimeStart(eventId, player);

    player.sendMessage("Time: " + plugin.formatTime(timeMS));

    var bestTime = plugin.getDatabase().getPlayerBestTimeInMS(eventId, player);
    if (timeMS < bestTime || bestTime == 0) {
      plugin.getDatabase().setPlayerBestTime(eventId, player, timeId.toString());
    }

    var firstTime = plugin.getDatabase().getPlayerFirstTimeInMS(eventId, player);
    if (firstTime == 0) {
      plugin.getDatabase().setPlayerFirstTime(eventId, player, timeId.toString());
    }
    return true;
  }

  public StopTimerCommand(IndigestionTimer plugin) {
    this.plugin = plugin;
  }
}
