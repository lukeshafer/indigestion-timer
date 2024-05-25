package dev.lksh.minecraft.plugins.timer.types;

public class TimerEvent {
  public final String eventId;
  public String eventName;
  public int startX;
  public int startY;
  public int startZ;
  public int startMargin;
  public int endX;
  public int endY;
  public int endZ;
  public int endMargin;

  public TimerEvent(
      String eventId,
      String eventName,
      int startY,
      int startZ,
      int startMargin,
      int endX,
      int endY,
      int endZ,
      int endMargin) {
    this.eventId = eventId;
  }

  public void setStartPosition(int x, int y, int z, int margin) {
    this.startX = x;
    this.startY = y;
    this.startZ = z;
    this.startMargin = margin;
  }

  public void setEndPosition(int x, int y, int z, int margin) {
    this.endX = x;
    this.endY = y;
    this.endZ = z;
    this.endMargin = margin;
  }
}
