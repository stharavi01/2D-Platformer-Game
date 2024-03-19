package utilz;


import java.sql.*;

public class ScpreDatabaseManager {
	int highestscore = 0;
	public void insertScore(int score) {
        try {
        	try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
								e.printStackTrace();
			}
        	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/game", "root", "");
        	 String sql = "INSERT INTO score (Score) VALUES (?)";
        	PreparedStatement ps = conn.prepareStatement(sql);
          
            ps.setInt(1, score);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	public int getHighestScore() {
        try {
        	try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
								e.printStackTrace();
			}
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/game", "root", "");
            Statement stmt = conn.createStatement();
            String query = "SELECT MAX(Score) AS max_score FROM score";
            ResultSet rs = stmt.executeQuery(query);

            int highestScore = 0;
            if (rs.next()) {
                highestScore = rs.getInt("max_score");
            }

            conn.close();

            return highestScore;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }
}
