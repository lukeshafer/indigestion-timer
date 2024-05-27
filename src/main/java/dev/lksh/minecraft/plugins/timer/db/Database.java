package dev.lksh.minecraft.plugins.timer.db;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;
import dev.lksh.minecraft.plugins.timer.types.TimeResult;
import dev.lksh.minecraft.plugins.timer.types.TimerPosition;
// YOU MUST IMPORT THE CLASS ERROR, AND ERRORS!!!
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.entity.Player;

public abstract class Database {
  IndigestionTimer plugin;
  Connection connection;
  public int tokens = 0;

  public Database(IndigestionTimer instance) {
    plugin = instance;
  }

  protected abstract Connection getSQLConnection();

  public abstract void load();

  public void initialize() {
    connection = getSQLConnection();
    try {
      PreparedStatement ps = connection.prepareStatement("SELECT * FROM events WHERE event_name = ?");
      ResultSet rs = ps.executeQuery();
      close(ps, rs);

    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
    }
  }

  public void createTimerEvent(String eventId) {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
      conn = getSQLConnection();
      ps = conn.prepareStatement("INSERT INTO events (event_id) VALUES (?)");
      ps.setString(1, eventId);
      ps.executeUpdate();
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return;
  }

  public void setEventName(String eventId, String eventName) {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
      conn = getSQLConnection();
      ps = conn.prepareStatement("UPDATE events SET event_name = ? WHERE event_id = ?");
      ps.setString(1, eventName);
      ps.setString(2, eventId);
      ps.executeUpdate();
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return;
  }

  public String getEventName(String eventId) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = getSQLConnection();
      ps = conn.prepareStatement("SELECT event_name from events WHERE event_id = ?");
      ps.setString(1, eventId);
      rs = ps.executeQuery();
      while (rs.next()) {
        if (rs.getString("event_name") != null) {
          return rs.getString("event_name");
        } else {
          return "";
        }
      }
      return null;
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return null;
  }

  public void setStartPosition(String eventId, int posX, int posY, int posZ, int margin) {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
      String statement = """
            UPDATE events
            SET start_x = ?,
                start_y = ?,
                start_z = ?,
                start_margin = ?
            WHERE event_id = ?
          """;

      conn = getSQLConnection();
      ps = conn.prepareStatement(statement.trim());
      ps.setInt(1, posX);
      ps.setInt(2, posY);
      ps.setInt(3, posZ);
      ps.setInt(4, margin);
      ps.setString(5, eventId);
      ps.executeUpdate();
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return;
  }

  public TimerPosition getStartPosition(String eventId) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      String statement = """
          SELECT start_x, start_y, start_z, start_margin
          FROM events
          WHERE event_id = ?""";

      conn = getSQLConnection();
      ps = conn.prepareStatement(statement.trim());
      ps.setString(1, eventId);
      rs = ps.executeQuery();

      while (rs.next()) {
        return new TimerPosition(
            rs.getInt("start_x"),
            rs.getInt("start_y"),
            rs.getInt("start_z"),
            rs.getInt("start_margin"));
      }
      return null;
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return null;
  }

  public void setEndPosition(String eventId, int posX, int posY, int posZ, int margin) {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
      String statement = """
            UPDATE events
            SET end_x = ?,
                end_y = ?,
                end_z = ?,
                end_margin = ?
            WHERE event_id = ?
          """;

      conn = getSQLConnection();
      ps = conn.prepareStatement(statement.trim());
      ps.setInt(1, posX);
      ps.setInt(2, posY);
      ps.setInt(3, posZ);
      ps.setInt(4, margin);
      ps.setString(5, eventId);
      ps.executeUpdate();
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return;
  }

  public TimerPosition getEndPosition(String eventId) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      String statement = """
          SELECT end_x, end_y, end_z, end_margin
          FROM events
          WHERE event_id = ?""";

      conn = getSQLConnection();
      ps = conn.prepareStatement(statement.trim());
      ps.setString(1, eventId);
      rs = ps.executeQuery();

      while (rs.next()) {
        return new TimerPosition(
            rs.getInt("end_x"),
            rs.getInt("end_y"),
            rs.getInt("end_z"),
            rs.getInt("end_margin"));
      }
      return null;
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return null;
  }

  public final boolean checkEventPlayerExists(String eventId, Player player) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = getSQLConnection();
      ps = conn.prepareStatement("SELECT player_uuid FROM event_players WHERE event_id = ? AND player_uuid = ?");
      ps.setString(1, eventId);
      ps.setString(2, player.getUniqueId().toString());
      rs = ps.executeQuery();
      return rs.next();
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return false;
  }

  public final void createEventPlayer(String eventId, Player player) {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
      conn = getSQLConnection();
      ps = conn.prepareStatement("INSERT INTO event_players (event_id, player_uuid, player_username) VALUES (?, ?, ?)");
      ps.setString(1, eventId);
      ps.setString(2, player.getUniqueId().toString());
      ps.setString(3, player.getName());
      ps.executeUpdate();
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return;
  }

  public final void updatePlayerUsername(Player player) {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
      conn = getSQLConnection();
      ps = conn.prepareStatement("UPDATE event_players SET player_username = ? WHERE player_uuid = ?");
      ps.setString(1, player.getName());
      ps.setString(2, player.getUniqueId().toString());
      ps.executeUpdate();
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return;
  }

  public final void setPlayerCurrentTimeStart(String eventId, Player player, long time) {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
      conn = getSQLConnection();
      ps = conn.prepareStatement("""
            UPDATE event_players
            SET current_time_start = ?
            WHERE event_id = ? AND player_uuid = ?
          """);
      ps.setLong(1, time);
      ps.setString(2, eventId);
      ps.setString(3, player.getUniqueId().toString());
      ps.executeUpdate();
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return;
  }

  public final long getPlayerCurrentTimeStart(String eventId, Player player) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = getSQLConnection();
      ps = conn.prepareStatement("SELECT current_time_start FROM event_players WHERE event_id = ? AND player_uuid = ?");
      ps.setString(1, eventId);
      ps.setString(2, player.getUniqueId().toString());
      rs = ps.executeQuery();
      while (rs.next()) {
        return rs.getLong("current_time_start");
      }
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return 0;
  }

  public final UUID createEventTime(String eventId, Player player, long start, long end) {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
      conn = getSQLConnection();
      ps = conn.prepareStatement(
          "INSERT INTO event_times (event_id, player_uuid, time_id, start_time, end_time) VALUES (?, ?, ?, ?, ?)");
      ps.setString(1, eventId);
      ps.setString(2, player.getUniqueId().toString());
      UUID id = UUID.randomUUID();
      ps.setString(3, id.toString());
      ps.setLong(4, start);
      ps.setLong(5, end);
      ps.executeUpdate();
      return id;
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    plugin.getLogger().severe("An error occurred while creating the event time.");
    return null;
  }

  public final void setPlayerBestTime(String eventId, Player player, String time_id) {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
      conn = getSQLConnection();
      ps = conn.prepareStatement("UPDATE event_players SET best_time_id = ? WHERE event_id = ? AND player_uuid = ?");
      ps.setString(1, time_id);
      ps.setString(2, eventId);
      ps.setString(3, player.getUniqueId().toString());
      ps.executeUpdate();
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return;
  }

  public final void setPlayerFirstTime(String eventId, Player player, String time_id) {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
      conn = getSQLConnection();
      ps = conn.prepareStatement("UPDATE event_players SET first_time_id = ? WHERE event_id = ? AND player_uuid = ?");
      ps.setString(1, time_id);
      ps.setString(2, eventId);
      ps.setString(3, player.getUniqueId().toString());
      ps.executeUpdate();
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return;
  }

  public final void removePlayerTimeStart(String eventId, Player player) {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
      conn = getSQLConnection();
      ps = conn
          .prepareStatement("UPDATE event_players SET current_time_start = ? WHERE event_id = ? AND player_uuid = ?");
      ps.setNull(1, java.sql.Types.INTEGER);
      ps.setString(2, eventId);
      ps.setString(3, player.getUniqueId().toString());
      ps.executeUpdate();
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return;
  }

  public final long getPlayerBestTimeInMS(String eventId, Player player) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      String statement = """
                SELECT start_time, end_time
                FROM event_times
                INNER JOIN event_players ON event_times.time_id = event_players.best_time_id
                WHERE event_players.event_id = ? AND event_players.player_uuid = ?
          """;

      conn = getSQLConnection();
      ps = conn.prepareStatement(statement);
      ps.setString(1, eventId);
      ps.setString(2, player.getUniqueId().toString());
      rs = ps.executeQuery();
      while (rs.next()) {
        var start = rs.getLong("start_time");
        var end = rs.getLong("end_time");
        return end - start;
      }
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return 0;
  }

  public final long getPlayerFirstTimeInMS(String eventId, Player player) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      String statement = """
                SELECT start_time, end_time
                FROM event_times
                INNER JOIN event_players ON event_times.time_id = event_players.first_time_id
                WHERE event_players.event_id = ? AND event_players.player_uuid = ?
          """;

      conn = getSQLConnection();
      ps = conn.prepareStatement(statement);
      ps.setString(1, eventId);
      ps.setString(2, player.getUniqueId().toString());
      rs = ps.executeQuery();
      while (rs.next()) {
        var start = rs.getLong("start_time");
        var end = rs.getLong("end_time");
        return end - start;
      }
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return 0;
  }

  public final List<TimeResult> getBestTimes(String eventId) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<TimeResult> times = new ArrayList<>();
    try {
      String statement = """
                SELECT start_time, end_time, player_username
                FROM event_times
                INNER JOIN event_players ON event_times.time_id = event_players.best_time_id
                WHERE event_players.event_id = ?
          """;

      conn = getSQLConnection();
      ps = conn.prepareStatement(statement);
      ps.setString(1, eventId);
      rs = ps.executeQuery();
      while (rs.next()) {
        var start = rs.getLong("start_time");
        var end = rs.getLong("end_time");
        var username = rs.getString("player_username");

        var result = new TimeResult(eventId, username, end - start);

        times.add(result);
      }
      return times;
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return times;
  }

  public final List<TimeResult> getFirstTimes(String eventId) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<TimeResult> times = new ArrayList<>();
    try {
      String statement = """
                SELECT start_time, end_time, player_username
                FROM event_times
                INNER JOIN event_players ON event_times.time_id = event_players.first_time_id
                WHERE event_players.event_id = ?
          """;

      conn = getSQLConnection();
      ps = conn.prepareStatement(statement);
      ps.setString(1, eventId);
      rs = ps.executeQuery();
      while (rs.next()) {
        var start = rs.getLong("start_time");
        var end = rs.getLong("end_time");
        var username = rs.getString("player_username");

        var result = new TimeResult(eventId, username, end - start);

        times.add(result);
      }
      return times;
    } catch (SQLException ex) {
      plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    } finally {
      try {
        if (ps != null)
          ps.close();
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
      }
    }
    return times;
  }

  public void close(PreparedStatement ps, ResultSet rs) {
    try {
      if (ps != null)
        ps.close();
      if (rs != null)
        rs.close();
    } catch (SQLException ex) {
      Error.close(plugin, ex);
    }
  }
}
