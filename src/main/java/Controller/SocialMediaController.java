package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this:: postRegisterHandler);
        app.post("login", this:: postLoginHandler);
        app.post("messages", this:: postMessageHandler);
        app.get("messages", this:: getAllMessagesHandler);
        app.get("message/{message_id}", this:: getMessageByIdHandler);
        app.patch("messages/{message_id}", this:: patchUpdateMessageHandler);
        app.delete("message/{message_id}", this:: deleteMessageHandler);
        app.get("accounts/{account_id}/messages", this:: getMessagesByUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postRegisterHandler(Context ctx) throws JsonProcessingException, JsonMappingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        String username = account.getUsername();
        String password = account.getPassword();
        Account newAccount = accountService.createAccount(username, password);
        if (newAccount != null){
          ctx.json(mapper.writeValueAsString(newAccount));
          ctx.status(200);
        } else{
            ctx.status(400);
        }

    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException, JsonMappingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        String username = account.getUsername();
        String password = account.getPassword();
        account = accountService.loginAccount(username, password);
        if(account != null){
            ctx.json(mapper.writeValueAsString(account));
            ctx.status(200);
        } else{
            ctx.status(401);
        }
    }
    
    private void postMessageHandler(Context ctx) throws JsonProcessingException, JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        String messageText = message.getMessage_text();
        Message newMessage = messageService.postCreatedMessage(messageText);
        if(newMessage != null){
            ctx.json(mapper.writeValueAsString(newMessage));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException, JsonMappingException {
        List<Message> message = messageService.getAllMessages();
        ctx.json(message);
    }

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException,JsonMappingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message returnMessage = messageService.retrieveMessagebyMessageId(messageId);
        ctx.json(returnMessage);
    }
    private void patchUpdateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        String newMessageText = message.getMessage_text();
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message newMessage = messageService.updateMessageById(newMessageText, messageId);
        if(newMessageText != null){
            ctx.json(newMessage);
            ctx.status(200);
        } else{
            ctx.status(400);
        }
    }
    private void deleteMessageHandler(Context ctx) throws JsonProcessingException, JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(messageId);
        if(deletedMessage != null){
            ctx.json(deletedMessage);
            ctx.status(200);
        } else{
            ctx.status(400);
        }
    }
    private void getMessagesByUserHandler(Context ctx) throws JsonProcessingException,JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        Message returnMessage = messageService.retrieveMessagebyMessageId(accountId);
        ctx.json(returnMessage);
    }

}