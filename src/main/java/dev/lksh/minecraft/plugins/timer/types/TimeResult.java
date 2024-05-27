package dev.lksh.minecraft.plugins.timer.types;

public class TimeResult {
  public final long timeInMS;
  public final String username;
  public final String eventId;

  public TimeResult (String eventId, String username, long timeInMS) {
    this.timeInMS = timeInMS;
    this.username = username;
    this.eventId = eventId;
  }
}
