package ObjectModels;

public class LoginModel {
    private AccountModel account;

    public LoginModel() {
    }

    public LoginModel(String username, String password) {
        AccountModel accountM = new AccountModel();
        accountM.setPassword(password);
        accountM.setUsername(username);

        this.account = accountM;
    }

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }
}