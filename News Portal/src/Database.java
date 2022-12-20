import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Optional;

public class Database {

    private static Database db = null;
    private static Connection cursor = null;
    
    private Database() {

    }

    public static Database getDb() {

        if(db == null) {

            db = new Database();
            cursor = getConnection();
        }
            

        return db;
    }

    public static Connection getConnection() {

        Connection conn = null;

        String dbName = "NewsPortal";
        String user = "postgres";
        String pass = "postgre_1789";

        try {

            conn = connectToDb(dbName, user, pass);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    private static Connection connectToDb(String dbName, String user, String pass) {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://db.aestech.me:5432/" + dbName, user, pass);

            if(conn != null)
                System.out.println("Connection established successfully !");

            else
                System.out.println("Connection failed !");
        }

        catch(SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public boolean addUser(User user) {

        boolean userCreated = false;

        if(!userAlreadyExists(user)) {
            PreparedStatement statement = null;

            String name = user.getName();
            String surname = user.getSurname();
            String email = user.getEmail();
            int age = user.getAge();
            String username = user.getUsername();
            String password = user.getPassword();

            String query = "INSERT INTO users (name, surname, email, age, username, password) VALUES(?, ?, ?, ?, ?, ?)";

            try {
                statement = cursor.prepareStatement(query);
                statement.setString(1, name);
                statement.setString(2, surname);
                statement.setString(3, email);
                statement.setInt(4, age);
                statement.setString(5, username);
                statement.setString(6, password);
                statement.executeUpdate();
                System.out.println("User has been registered to database successfully.");
                userCreated = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userCreated;
    }

    private boolean userAlreadyExists(User user) {

        PreparedStatement statement = null;
        ResultSet rs = null;
        List<String> usernameList = new ArrayList<>();
        List<String> emailList = new ArrayList<>();

        String query = "SELECT username, email FROM users";

        try {
            statement = cursor.prepareStatement(query);
            rs = statement.executeQuery();
            while(rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                usernameList.add(username);
                emailList.add(email);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return usernameList.contains(user.getUsername()) || emailList.contains(user.getEmail());
    }

    public void updateUsersFavoriteCategory(User loggedUser) {

        PreparedStatement statement = null;
        ResultSet rs = null;

        String query = "UPDATE users SET favorite_category = ? WHERE user_id = ?";

        try {
            statement = cursor.prepareStatement(query);
            statement.setString(1, loggedUser.getFavoriteCategory());
            statement.setInt(2, loggedUser.getId());
            statement.executeUpdate();

            System.out.println("User's favorite category has been updated successfully.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean newsAlreadyInserted(String newsTitle) {

        PreparedStatement statement = null;
        ResultSet rs = null;
        List<String> newsList = new ArrayList<>();

        String query = "SELECT title FROM news";

        try {
            statement = cursor.prepareStatement(query);
            rs = statement.executeQuery();
            while(rs.next()) {
                String title = rs.getString("title");
                newsList.add(title);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return newsList.contains(newsTitle);
    }

    public void insertReadNews(String title, String image_url, String text, String category) {

        if(!newsAlreadyInserted(title)) {
            PreparedStatement statement = null;

            String query = "INSERT INTO news (title, image_url, news_text, category) VALUES(?, ?, ?, ?)";

            try {
                statement = cursor.prepareStatement(query);
                statement.setString(1, title);
                statement.setString(2, image_url);
                statement.setString(3, text);
                statement.setString(4, category);
                statement.executeUpdate();
                System.out.println("News has been registered to database successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getCommentId(String comment) {

        PreparedStatement statement = null;
        ResultSet rs = null;
        int id = -1;

        String query = "SELECT comment_id FROM comments WHERE comment_text = ?";

        try {
            statement = cursor.prepareStatement(query);
            statement.setString(1, comment);
            rs = statement.executeQuery();
            while(rs.next()) {
                id = rs.getInt("comment_id");
            }
            System.out.println("Comment ID has been fetched successfully.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    // This method will get the comments that made to news which has given id.
    public List<Comment> getComments(int newsId) {

        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Comment> comments = new ArrayList<>();

        String query = "SELECT * FROM comments WHERE news_id = ?";

        try {
            statement = cursor.prepareStatement(query);
            statement.setInt(1, newsId);
            rs = statement.executeQuery();
            while(rs.next()) {
                int commentId = rs.getInt("comment_id");
                String comment = rs.getString("comment_text");
                Timestamp commentDate = rs.getTimestamp("date_posted");
                comments.add(new Comment(commentId, comment, commentDate));
            }
            System.out.println("Comments have been fetched successfully.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    public String getMostReadCategory(User loggedUser) {
        String category = null;

        return category;
    }

    public int getReadNewsAmountByCategory(User loggedUser, String category) {
        int readCount = 0;

        PreparedStatement statement = null;
        ResultSet rs = null;

        String query = "SELECT COUNT(1) AS count FROM (SELECT * FROM user_news WHERE user_news.user_id = ?) temp JOIN news ON news.news_id = temp.news_id " +
                "GROUP BY news.category HAVING news.category = ?";

        try {
            statement = cursor.prepareStatement(query);
            statement.setInt(1, loggedUser.getId());
            statement.setString(2, category);
            rs = statement.executeQuery();
            while(rs.next()) {
                readCount = rs.getInt("count");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return readCount;
    }

    public int getNewsId(String title) {

        PreparedStatement statement = null;
        ResultSet rs = null;
        int id = -1;

        String query = "SELECT news_id FROM news WHERE title = ?";

        try {
            statement = cursor.prepareStatement(query);
            statement.setString(1, title);
            rs = statement.executeQuery();
            while(rs.next()) {
                id = rs.getInt("news_id");
            }

            System.out.println("News ID has been fetched successfully.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public void insertIntoUserComment(int userID, int commentID) {

        PreparedStatement statement = null;

        String query = "INSERT INTO user_comment (user_id, comment_id) VALUES(?, ?)";

        try {
            statement = cursor.prepareStatement(query);
            statement.setInt(1, userID);
            statement.setInt(2, commentID);
            statement.executeUpdate();
            System.out.println("User-Comment table updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoUserNews(int userID, int newsID) {

        PreparedStatement statement = null;

        String query = "INSERT INTO user_news (user_id, news_id) SELECT ?, ?" +
                " WHERE NOT EXISTS (SELECT user_id, news_id FROM user_news WHERE user_id = ? AND news_id = ?)";

        try {
            statement = cursor.prepareStatement(query);
            statement.setInt(1, userID);
            statement.setInt(2, newsID);
            statement.setInt(3, userID);
            statement.setInt(4, newsID);
            statement.executeUpdate();
            System.out.println("User-News table updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean postComment(String comment, int news_id) {

        PreparedStatement statement = null;
        boolean posted = false;

        try {
            String query = "INSERT INTO comments (comment_text, date_posted, news_id) VALUES(?, ?, ?)";
            long millis=System.currentTimeMillis();
            Timestamp date = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Timestamp(millis)));
            statement = cursor.prepareStatement(query);
            statement.setString(1, comment);
            statement.setObject(2, date);
            statement.setObject(3, news_id);
            statement.executeUpdate();
            System.out.println("Comment has been registered to database successfully.");
            posted = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return posted;
    }

    public User getUserByCommentID(int commentID) {

        PreparedStatement statement = null;
        ResultSet rs = null;
        User user = new User();

        String query = "SELECT users.user_id, users.name, users.surname, users.email, users.age, users.username, users.password, users.favorite_category FROM user_comment" +
                " JOIN users ON users.user_id = user_comment.user_id WHERE user_comment.comment_id = ?";

        try {
            statement = cursor.prepareStatement(query);
            statement.setInt(1, commentID);
            rs = statement.executeQuery();
            while(rs.next()) {
                user.setId(rs.getInt("user_id"));
                user.setAge(rs.getInt("age"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setSurname(rs.getString("surname"));
                user.setUsername(rs.getString("username"));
                user.setFavoriteCategory(rs.getString("favorite_category"));
            }

            System.out.println("User has been fetched by comment's id successfully.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return user.getName() != null ? user : null;
    }

    public User authenticateUser(String username, String password) {

        PreparedStatement statement = null;
        ResultSet rs = null;
        User user = new User();

        String query = "SELECT * FROM users WHERE username=? AND password=?";

        try {
            statement = cursor.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            rs = statement.executeQuery();

            while(rs.next()) {
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setEmail(rs.getString("email"));
                user.setAge(rs.getInt("age"));
                user.setUsername((rs.getString("username")));
                user.setPassword(rs.getString("password"));
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user.getName() != null ? user : null;
    }
}
