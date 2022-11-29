import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbName, user, pass);

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
    
    // ! Database e username ve email icin kosul eklenecek. 1 den fazla ayni usernamede veya emailde kullanici olamaz.
    public void addUser(User user) {

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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String username, String password) {

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
