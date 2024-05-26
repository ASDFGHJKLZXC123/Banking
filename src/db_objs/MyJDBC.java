package db_objs;

import java.sql.*;

/*
    JDBC class is used to interact with our MySQL Database to perform activties such as retrieving and updating database.
 */
public class MyJDBC {
    // database configurations
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/bankapp";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "TTTasshole2333";

    public static User validateLogin(String username, String password){
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
            );

            // replace the ? with values
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // execute query and store into a result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // next() returns true or false
            // true - query returned data and result set now points to the first row
            // false - query returned no data and result set equals
            if(resultSet.next()){

            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        //not valid user
        return null;
    }

    // registers new user to the database
    // true - register success
    // false - register fails
    public static boolean register(String username, String password){
        try{
            // need to check if the username is taken by others
            if(!checkUser(username)){
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO users(username, password) " +
                                "VALUES(?, ?)"
                );

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                preparedStatement.executeUpdate();
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // if the username already exists in the database return true else false
    private static boolean checkUser(String username){
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            // if the resultSet return nothing it means the username is available
            if(!resultSet.next()){
                return false;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return true;
    }

}
