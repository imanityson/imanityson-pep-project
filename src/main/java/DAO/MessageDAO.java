package DAO;

import Model.Message;
import Util.ConnectionUtil;


import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class MessageDAO {

    //TODO: Checks to see if created message meets requirements
    public boolean messageCheck(String message_text){
        int messageLength = message_text.length();
        if(messageLength == 0 || messageLength > 255){
            return false;
        } else{
            return true;
        }
    }
    //TODO: Retrieve all Messages
    public List<Message>getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "select * from Message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                (rs.getInt("posted_by")),
                (rs.getString("message_text")),
                (rs.getLong("time_posted_epoch")));

                messages.add(message);
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    //TODO: Create New Message
    public Message createMessage(String message){
        Connection connection = ConnectionUtil.getConnection();
        Message newMessage = new Message();
        try {
            String sql = "insert into Message (message_test) values (?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, message);

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int generated_message_id = (int) rs.getLong(1);
                newMessage = new Message(rs.getInt(generated_message_id),
                rs.getInt("posted_by"), 
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));}
           
        } catch(SQLException e) {
            System.out.print(e.getMessage());
        }
        return newMessage;


    }
    //TODO: retrieve message by message id
    public Message retrieveMessageByMessageId(int id){
        Connection connection = ConnectionUtil.getConnection();
        Message message = new Message();
        try {
            String sql = "select * from message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                message = new Message(rs.getInt("message_id"), 
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());

        }
        return message;
    }

    //TODO: retrieve messages by account id
    public List<Message> retrieveMessagesByAccountId(int id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "select * from message where account_id = ?;";
            PreparedStatement preparedStatement  = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                messages.add(message);
            }  
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }

    //TODO: update message by ID
    public Message updateMessageById(String newMessage, int id){
        Connection connection = ConnectionUtil.getConnection();
        Message message = new Message();
        try {
            String sql = "update message set message_text = ? where message_id = ?; ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, newMessage);
            preparedStatement.setInt(2, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                message = new Message(rs.getInt("message_id"), 
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());

        }
        return message;
        
        
    }
    //TODO: delete message by ID
    public Message deleteMessage(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "delete * from Message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        }catch(SQLException e) {
            System.out.println(e.getMessage());

        }

        return null;
    }


    
}
