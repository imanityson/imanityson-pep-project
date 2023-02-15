package DAO;

import java.util.List;
import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    //TODO: checks to see if inputed username and password meets requirements 
    public boolean accountCheck(String username, String password){
        //booleans used to verify input
        boolean passwordCheck = true;
        boolean usernameCheck = true;
        boolean usernameInDb = false;

        
        //checks to see if password is longer than 4 characters
        if(password.length() < 4){
            passwordCheck = false;
        }
        //checks to see if username is empty
        if(username.length() == 0){
            usernameCheck = false;
        }

        try {Connection connection = ConnectionUtil.getConnection();
            String sql = "select * from account where username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                usernameInDb = true;
            }
    
    } catch(SQLException e){
        System.out.print(e.getMessage());
    }

    //checks to see if all conditions are met
    if(passwordCheck == true && usernameCheck == true && usernameInDb == true){
        return true;
    } else{
        return false;
    }
    

}
    //TODO: Create Account
    public Account createAccount(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        Account newAccount = new Account(); 
        
        try {
            String sql = "insert into Account (username, password ) values (?,?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int generated_account_id = (int) rs.getLong(1);
                newAccount = new Account(rs.getInt(generated_account_id), 
                rs.getString("username"),
                rs.getString("password"));
            }
           

        } catch(SQLException e) {
            System.out.print(e.getMessage());

        }
        return newAccount;
    }
    //TODO login into account
    public Account loginAccount(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        Account account = new Account();
        try {
            String sql = "select * from account where username = ? and password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                account = new Account(rs.getInt("account_id"), 
                rs.getString("username"), 
                rs.getString("password"));}
           
        
    } catch(SQLException e){
        System.out.print(e.getMessage());

    }return account;
    }
    
}
