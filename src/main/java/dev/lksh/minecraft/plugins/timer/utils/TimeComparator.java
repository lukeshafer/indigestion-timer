package dev.lksh.minecraft.plugins.timer.utils;

import dev.lksh.minecraft.plugins.timer.types.TimeResult;

public class TimeComparator implements java.util.Comparator<TimeResult> {
  @Override
  public int compare(TimeResult o1, TimeResult o2) {
    return (int) (o1.timeInMS - o2.timeInMS);
  }
}
