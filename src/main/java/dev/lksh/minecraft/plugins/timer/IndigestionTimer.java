package dev.lksh.minecraft.plugins.timer;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dev.lksh.minecraft.plugins.timer.commands.CreateEventCommand;
import dev.lksh.minecraft.plugins.timer.commands.GetBestTimeCommand;
import dev.lksh.minecraft.plugins.timer.commands.GetEventBestTimesCommand;
import dev.lksh.minecraft.plugins.timer.commands.GetEventFirstTimesCommand;
import dev.lksh.minecraft.plugins.timer.commands.GetFirstTimeCommand;
import dev.lksh.minecraft.plugins.timer.commands.SetEventNameCommand;
import dev.lksh.minecraft.plugins.timer.commands.SetEventStartPositionCommand;
import dev.lksh.minecraft.plugins.timer.commands.StartTimerCommand;
import dev.lksh.minecraft.plugins.timer.commands.StopTimerCommand;
import dev.lksh.minecraft.plugins.timer.commands.UpdateUsernameCommand;
import dev.lksh.minecraft.plugins.timer.commands.GetEventNameCommand;
import dev.lksh.minecraft.plugins.timer.commands.SetEventEndPositionCommand;
import dev.lksh.minecraft.plugins.timer.db.Database;
import dev.lksh.minecraft.plugins.timer.db.SQLite;

public class IndigestionTimer extends JavaPlugin implements Listener {
  private Database db;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
    getCommand("createtimerevent").setExecutor(new CreateEventCommand(this));
    getCommand("settimereventname").setExecutor(new SetEventNameCommand(this));
    getCommand("gettimereventname").setExecutor(new GetEventNameCommand(this));
    getCommand("settimereventstart").setExecutor(new SetEventStartPositionCommand(this));
    getCommand("settimereventend").setExecutor(new SetEventEndPositionCommand(this));
    getCommand("starttimer").setExecutor(new StartTimerCommand(this));
    getCommand("stoptimer").setExecutor(new StopTimerCommand(this));
    getCommand("getbesttime").setExecutor(new GetBestTimeCommand(this));
    getCommand("getfirsttime").setExecutor(new GetFirstTimeCommand(this));
    getCommand("geteventbesttimes").setExecutor(new GetEventBestTimesCommand(this));
    getCommand("geteventfirsttimes").setExecutor(new GetEventFirstTimesCommand(this));
    getCommand("updatetimerusername").setExecutor(new UpdateUsernameCommand(this));

    db = new SQLite(this);
    db.load();
  }

  public Database getDatabase() {
    return this.db;
  }

  public final String formatTime(long timeMS) {
    var timeSEC = Math.floorDiv(timeMS, 1000);
    var remMS = timeMS % 1000;
    var timeMIN = Math.floorDiv(timeSEC, 60);
    var remSEC = timeSEC % 60;
    var stringMIN = String.format("%2s", timeMIN).replace(' ', '0');
    var stringSEC = String.format("%2s", remSEC).replace(' ', '0');
    var stringMS = String.format("%3s", remMS).replace(' ', '0');

    return stringMIN + ":" + stringSEC + "." + stringMS;
  }
}
