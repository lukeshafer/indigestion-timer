package dev.lksh.minecraft.plugins.timer;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dev.lksh.minecraft.plugins.timer.db.Database;
import dev.lksh.minecraft.plugins.timer.db.SQLite;

public class IndigestionTimer extends JavaPlugin implements Listener {
  private Database db;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);

    db = new SQLite(this);
    db.load();
  }

  public Database getDatabase() {
    return this.db;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
  }

}
