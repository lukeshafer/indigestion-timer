package dev.lksh.minecraft.plugins.timer.db;

import dev.lksh.minecraft.plugins.timer.IndigestionTimer;
import dev.lksh.minecraft.plugins.timer.types.TimerPosition;
// YOU MUST IMPORT THE CLASS ERROR, AND ERRORS!!!
import dev.lksh.minecraft.plugins.timer.types.TimerEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public abstract class Database {
  IndigestionTimer plugin;
  Connection connection;
  // The name of the table we created back in SQLite class.
  private final String eventsTable = "events";
  private final String playersTable = "event_players";
  private final String timesTable = "event_times";
  public int tokens = 0;

  public Database(IndigestionTimer instance) {
    plugin = instance;
  }

  protected abstract Connection getSQLConnection();

  public abstract void load();

  public void initialize() {
    connection = getSQLConnection();
    try {
      PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + eventsTable + " WHERE event_name = ?");
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
      int i = 0;
      while (rs.next()) {
        plugin.getLogger().info("in while loop, " + i++);
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

      int i = 0;
      while (rs.next()) {
        plugin.getLogger().info("in while loop, " + i++);
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

      int i = 0;
      while (rs.next()) {
        plugin.getLogger().info("in while loop, " + i++);
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

  // These are the methods you can use to get things out of your database. You of
  // course can make new ones to return different things in the database.
  // This returns the number of people the player killed.
  // public Integer getTokens(String string) {
  // Connection conn = null;
  // PreparedStatement ps = null;
  // ResultSet rs = null;
  // try {
  // conn = getSQLConnection();
  // ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = '" +
  // string + "';");
  //
  // rs = ps.executeQuery();
  // while (rs.next()) {
  // if (rs.getString("player").equalsIgnoreCase(string.toLowerCase())) { // Tell
  // database to
  // search for the player
  // // you sent into the
  // method. e.g
  // // getTokens(sam) It
  // will look for sam.
  // return rs.getInt("kills"); // Return the players ammount of kills. If you
  // wanted to get
  // total (just a
  // // random number for an example for you guys) You would
  // change this to total!
  // }
  // }
  // } catch (SQLException ex) {
  // plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
  // } finally {
  // try {
  // if (ps != null)
  // ps.close();
  // if (conn != null)
  // conn.close();
  // } catch (SQLException ex) {
  // plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
  // }
  // }
  // return 0;
  // }
  //
  //// Exact same method here, Except as mentioned above i am looking for total!
  // public Integer getTotal(String string) {
  // Connection conn = null;
  // PreparedStatement ps = null;
  // ResultSet rs = null;
  // try {
  // conn = getSQLConnection();
  // ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = '" +
  // string + "';");
  //
  // rs = ps.executeQuery();
  // while (rs.next()) {
  // if (rs.getString("player").equalsIgnoreCase(string.toLowerCase())) {
  // return rs.getInt("total");
  // }
  // }
  // } catch (SQLException ex) {
  // plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
  // } finally {
  // try {
  // if (ps != null)
  // ps.close();
  // if (conn != null)
  // conn.close();
  // } catch (SQLException ex) {
  // plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
  // }
  // }
  // return 0;
  // }
  //
  //// Now we need methods to save things to the database
  // public void setTokens(Player player, Integer tokens, Integer total) {
  // Connection conn = null;
  // PreparedStatement ps = null;
  // try {
  // conn = getSQLConnection();
  // ps = conn.prepareStatement("REPLACE INTO " + table + " (player,kills,total)
  // VALUES(?,?,?)");
  // // IMPORTANT. In
  //
  // // SQLite class, We
  //
  // // made 3 colums.
  //
  // // player, Kills,
  //
  // // Total.
  // ps.setString(1, player.getName().toLowerCase()); // YOU MUST put these into
  // this line!! And
  // depending on how many
  // // colums you put (say you made 5) All 5
  // need to be in the
  // // brackets
  // // Seperated with comma's (,) AND there
  // needs to be the same
  // // amount of
  // // question marks in the VALUES brackets.
  // Right now i only have 3
  // // colums
  // // So VALUES (?,?,?) If you had 5 colums
  // VALUES(?,?,?,?,?)
  //
  // ps.setInt(2, tokens); // This sets the value in the database. The colums go
  // in order. Player
  // is ID 1,
  // // kills is ID 2, Total would be 3 and so on. you can use
  // // setInt, setString and so on. tokens and total are just variables
  // sent in, You
  // // can manually send values in as well. p.setInt(2, 10) <-
  // // This would set the players kills instantly to 10. Sorry about the
  // variable
  // // names, It sets their kills to 10 i just have the variable called
  // // Tokens from another plugin :/
  // ps.setInt(3, total);
  // ps.executeUpdate();
  // return;
  // } catch (SQLException ex) {
  // plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
  // } finally {
  // try {
  // if (ps != null)
  // ps.close();
  // if (conn != null)
  // conn.close();
  // } catch (SQLException ex) {
  // plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
  // }
  // }
  // return;
  // }

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
