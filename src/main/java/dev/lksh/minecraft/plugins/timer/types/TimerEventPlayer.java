package dev.lksh.minecraft.plugins.timer.types;

import org.bukkit.entity.Player;

public class TimerEventPlayer {
  public final String eventId;
  public final Player player;
  public final long currentTimeStart;
  public final String firstTimeId;
  public final String bestTimeId;

  public TimerEventPlayer(String eventId, Player player) {
    this.eventId = eventId;
    this.player = player;
    this.currentTimeStart = 0;
    this.firstTimeId = null;
    this.bestTimeId = null;
  }

  public TimerEventPlayer(
      String eventId, Player player, long currentTimeStart, String firstTimeId, String bestTimeId) {
    this.eventId = eventId;
    this.player = player;
    this.currentTimeStart = currentTimeStart;
    this.firstTimeId = firstTimeId;
    this.bestTimeId = bestTimeId;
  }
}
