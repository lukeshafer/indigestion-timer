package dev.lksh.minecraft.plugins.timer.db;

import java.util.logging.Level;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;

public class Error {
  public static void execute(IndigestionTimer plugin, Exception ex) {
    plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
  }

  public static void close(IndigestionTimer plugin, Exception ex) {
    plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
  }
}
