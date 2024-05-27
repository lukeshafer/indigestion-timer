package dev.lksh.minecraft.plugins.timer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class UpdateUsernameCommand implements CommandExecutor {
  private final IndigestionTimer plugin;

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player player;
    if (args.length > 0) {
      var username = args[0];
      player = plugin.getServer().getPlayer(username);
      if (player == null) {
        sender.sendMessage(Component.text()
            .content("Player " + username + " is not online, or does not exist.")
            .color(NamedTextColor.RED));
        return true;
      }
    } else if ((sender instanceof Player)) {
      player = (Player) sender;
    } else {
      sender.sendMessage("You must be a player to use this command.");
      return true;
    }

    plugin.getDatabase().updatePlayerUsername(player);

    player.sendMessage("Updated player username to " + player.getName());
    return true;
  }

  public UpdateUsernameCommand(IndigestionTimer plugin) {
    this.plugin = plugin;
  }
}
