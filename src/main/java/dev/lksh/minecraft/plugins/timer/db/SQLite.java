package dev.lksh.minecraft.plugins.timer.db;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SQLite extends Database {
  String dbname;

  public SQLite(IndigestionTimer instance) {
    super(instance);

    dbname =
        plugin
            .getConfig()
            .getString("SQLite.Filename", "timer_data"); // Set the table name here e.g player_kills
  }

  private final String createEventsTable =
      """
      CREATE TABLE IF NOT EXISTS events (
        event_id TEXT NOT NULL PRIMARY KEY,
        event_name TEXT,
        start_x INTEGER,
        start_y INTEGER,
        start_z INTEGER,
        start_margin INTEGER,
        end_x INTEGER,
        end_y INTEGER,
        end_z INTEGER,
        end_margin INTEGER
      );
      """;

  private final String createPlayersTable =
      """
      CREATE TABLE IF NOT EXISTS event_players (
        event_id TEXT NOT NULL,
        player_uuid TEXT NOT NULL,
        first_time_id TEXT,
        best_time_id TEXT,
        PRIMARY KEY (event_id, player_uuid),
        FOREIGN KEY (event_id)
          REFERENCES events (event_id)
          ON DELETE CASCADE
          ON UPDATE NO ACTION
      );
      """;

  private final String createPlayerTimesTable =
      """
      CREATE TABLE IF NOT EXISTS event_times (
        event_id TEXT NOT NULL,
        player_uuid TEXT NOT NULL,
        time_id TEXT NOT NULL,
        start_time INTEGER NOT NULL,
        end_time INTEGER NOT NULL,
        PRIMARY KEY (event_id, player_uuid, time_id),
        FOREIGN KEY (event_id)
          REFERENCES events (event_id)
          ON DELETE CASCADE
          ON UPDATE NO ACTION,
        FOREIGN KEY (event_id, player_uuid)
          REFERENCES event_players (event_id, player_uuid)
          ON DELETE CASCADE
          ON UPDATE NO ACTION
      );
      """;

  // SQL creation stuff, You can leave the blow stuff untouched.
  protected Connection getSQLConnection() {
    if (!plugin.getDataFolder().exists()) {
        plugin.getLogger().info("Creating data folder: " + plugin.getDataFolder().getName());
      plugin.getDataFolder().mkdirs();
    }

    File dbFile = new File(plugin.getDataFolder(), dbname + ".db");
    if (!dbFile.exists()) {
      try {
        plugin.getLogger().info("Attempting to create file: " + dbname + ".db");
        dbFile.createNewFile();
      } catch (IOException e) {
        plugin.getLogger().severe("File write error: " + dbname + ".db -- " + e.getMessage());
      }
    }
    try {
      if (connection != null && !connection.isClosed()) {
        return connection;
      }
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
      return connection;
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
    } catch (ClassNotFoundException ex) {
      plugin
          .getLogger()
          .log(Level.SEVERE, "You need the SQLite JDBC library. Google it. Put it in /lib folder.");
    }
    return null;
  }

  public void load() {
    connection = getSQLConnection();
    try {
      Statement s = connection.createStatement();
      s.executeUpdate(createEventsTable);
      s.executeUpdate(createPlayersTable);
      s.executeUpdate(createPlayerTimesTable);
      s.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    initialize();
  }
}
