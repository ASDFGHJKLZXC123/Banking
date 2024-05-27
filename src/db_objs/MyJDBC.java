package db_objs;

import java.math.BigDecimal;
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
                return new User(
                        resultSet.getInt("id"), username, password,
                        resultSet.getBigDecimal("current_balance"));
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
                        "INSERT INTO users(username, password, current_balance) " +
                                "VALUES(?, ?, ?)"
                );

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setBigDecimal(3, new BigDecimal(0));
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

    public static boolean addTransactionToDatabase(Transaction transaction){
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement insertTransaction = connection.prepareStatement(
                    "INSERT transactions(user_id, transaction_type, transaction_amount, transaction_date) " +
                            "VALUES(?, ?, ?, NOW())"
            );

            insertTransaction.setInt(1, transaction.getUserID());
            insertTransaction.setString(2, transaction.getTransactionType());
            insertTransaction.setBigDecimal(3, transaction.getTransactionAmount());

            insertTransaction.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean updateCurrentBalance(User user){
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement updateBalance = connection.prepareStatement(
                    "UPDATE users SET current_balance = ? WHERE id = ?"
            );

            updateBalance.setBigDecimal(1, user.getCurrentBalance());
            updateBalance.setInt(2, user.getID());

            updateBalance.executeUpdate();
            return true;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    // true - transfer success
    // false - transfer fail
    public static boolean transfer(User user, String transferredUsername, float transferAmount){
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement queryUser = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );

            queryUser.setString(1, transferredUsername);
            ResultSet resultSet = queryUser.executeQuery();

            while (resultSet.next()){
                User transferredUser = new User(
                        resultSet.getInt("id"),
                        transferredUsername,
                        resultSet.getString("password"),
                        resultSet.getBigDecimal("current_balance")
                );

                // Transaction for transfer user
                Transaction transferTransaction = new Transaction(
                        user.getID(),
                        "Transfer",
                        new BigDecimal(-transferAmount),
                        null
                );

                // Transaction for receive user
                Transaction receivedTransaction = new Transaction(
                        transferredUser.getID(),
                        "Transfer",
                        new BigDecimal(transferAmount),
                        null
                );

                // update transfer user
                transferredUser.setCurrentBalance(transferredUser.getCurrentBalance().add(BigDecimal.valueOf(transferAmount)));
                updateCurrentBalance(transferredUser);

                // update user current balance
                user.setCurrentBalance(user.getCurrentBalance().subtract(BigDecimal.valueOf(transferAmount)));
                updateCurrentBalance(user);

                addTransactionToDatabase(transferTransaction);
                addTransactionToDatabase(receivedTransaction);

                return true;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
