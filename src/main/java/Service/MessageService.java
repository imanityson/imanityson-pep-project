package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;
import java.util.ArrayList;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messagedao){
        this.messageDAO = messageDAO;
    }

    // TODO: @return list of all messages
   public List<Message> getAllMessages(){
    return messageDAO.getAllMessages();

   }


    //TODO: @return created message
    public Message postCreatedMessage(String message_text){
        boolean messageCheck = this.messageDAO.messageCheck(message_text);
        if(messageCheck == false){
            return null;
        } else {
            return this.messageDAO.createMessage(message_text);
        }
    }

    //TODO: retrieve message by Author ID
    public List <Message> retrieveAllMessageByAccount(int author_id){
        List<Message> messages = new ArrayList<>();
        messages = this.messageDAO.retrieveMessagesByAccountId(author_id);
        return messages;

    }

    //TODO: retrieve message by Message id;
    public Message retrieveMessagebyMessageId(int message_id){
        return this.messageDAO.retrieveMessageByMessageId(message_id);
    }

    //TODO: Update message by id
    public Message updateMessageById(String updatedText, int message_id){
        return this.messageDAO.updateMessageById(updatedText, message_id);
    }

    //TODO: Delete message by ID
    public Message deleteMessageById(int id){
        return this.messageDAO.deleteMessage(id);
    }


}

