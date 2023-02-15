package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountdao){
        this.accountDAO = accountDAO;
    }

    public Account createAccount(String username, String password){
        boolean registerCheck;
        if(this.accountDAO.accountCheck(username, password) == false){
            return null;
        } else{
        return accountDAO.createAccount(username, password);
        }
    }

    public Account loginAccount(String username, String password){
        return this.accountDAO.loginAccount(username, password);
    }




}
