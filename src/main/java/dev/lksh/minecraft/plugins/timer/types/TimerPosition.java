package dev.lksh.minecraft.plugins.timer.types;

public class TimerPosition {
  public final int x;
  public final int y;
  public final int z;
  public final int margin;

  public TimerPosition(int posX, int posY, int posZ, int margin) {
    x = posX;
    y = posY;
    z = posZ;
    this.margin = margin;
  }
}
