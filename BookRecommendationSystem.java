import java.sql.*;
import java.util.*;

public class BookRecommendationSystem {
    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "your_password";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();

        List<String> recommendedBooks = getRecommendations(userId);

        System.out.println("\nRecommended Books:");
        if (recommendedBooks.isEmpty()) {
            System.out.println("No recommendations available.");
        } else {
            for (String book : recommendedBooks) {
                System.out.println("- " + book);
            }
        }

        scanner.close();
    }

    public static List<String> getRecommendations(int userId) {
        List<String> recommendations = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Step 1: Get user interests and genres from borrowing history
            String genreQuery = "SELECT genre FROM interests WHERE user_id=? " +
                                "UNION " +
                                "SELECT b.genre FROM borrow_history bh " +
                                "JOIN books b ON bh.book_id = b.book_id WHERE bh.user_id=?";
            PreparedStatement genreStmt = conn.prepareStatement(genreQuery);
            genreStmt.setInt(1, userId);
            genreStmt.setInt(2, userId);
            ResultSet genreRs = genreStmt.executeQuery();

            Set<String> genres = new HashSet<>();
            while (genreRs.next()) {
                genres.add(genreRs.getString("genre"));
            }

            // Step 2: Recommend books in those genres not already borrowed
            String bookQuery = "SELECT DISTINCT title FROM books WHERE genre = ? AND book_id NOT IN " +
                               "(SELECT book_id FROM borrow_history WHERE user_id = ?)";
            PreparedStatement bookStmt = conn.prepareStatement(bookQuery);
            for (String genre : genres) {
                bookStmt.setString(1, genre);
                bookStmt.setInt(2, userId);
                ResultSet bookRs = bookStmt.executeQuery();
                while (bookRs.next()) {
                    String title = bookRs.getString("title");
                    if (!recommendations.contains(title)) {
                        recommendations.add(title);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recommendations;
    }
}