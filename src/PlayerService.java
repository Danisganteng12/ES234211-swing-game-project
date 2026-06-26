import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PlayerService {

    // Login: cek username & password di database
    public Player login(String username, String password) {
        String sql = "SELECT * FROM players WHERE username = ? AND password = ?";
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String uname = rs.getString("username");
                int wins = rs.getInt("wins");
                int losses = rs.getInt("losses");
                int draws = rs.getInt("draws");
                int score = rs.getInt("score");
                return new Player(id, uname, wins, losses, draws, score);
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    // Update statistik setelah game selesai
    public void updateStatistics(Player player, String result) {
        int additionalScore = 0;
        String sql = "";

        if (result.equalsIgnoreCase("WIN")) {
            additionalScore = 10;
            sql = "UPDATE players SET wins = wins + 1, score = score + ? WHERE id = ?";
        } else if (result.equalsIgnoreCase("LOSE")) {
            additionalScore = 0;
            sql = "UPDATE players SET losses = losses + 1, score = score + ? WHERE id = ?";
        } else if (result.equalsIgnoreCase("DRAW")) {
            additionalScore = 3;
            sql = "UPDATE players SET draws = draws + 1, score = score + ? WHERE id = ?";
        }

        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, additionalScore);
            stmt.setInt(2, player.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Update statistics error: " + e.getMessage());
        }
    }

    // Ambil data player terbaru dari database (untuk StatisticsFrame)
    public Player getPlayerById(int id) {
        String sql = "SELECT * FROM players WHERE id = ?";
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Player player = new Player(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getInt("wins"),
                    rs.getInt("losses"),
                    rs.getInt("draws"),
                    rs.getInt("score")
                );
                conn.close();
                return player;
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("GetPlayer error: " + e.getMessage());
        }
        return null;
    }

    // Ambil Top 5 scorer dari database
    public ArrayList<Player> getTopFiveScorers() {
        ArrayList<Player> list = new ArrayList<>();
        String sql = "SELECT * FROM players ORDER BY score DESC, wins DESC LIMIT 5";
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Player(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getInt("wins"),
                    rs.getInt("losses"),
                    rs.getInt("draws"),
                    rs.getInt("score")
                ));
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("TopScorers error: " + e.getMessage());

        }
        return list;
    }
}
