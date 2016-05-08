package edu.csumb.vill2101.otterairways;

/**
 * Created by psycho on 5/5/16.
 */
public class Account {
    private String username;
    private String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean equals(Object obj) {
        if( obj instanceof Account ) {
            Account account = (Account) obj;
            return this.username.equals(account.username) && this.password.equals(account.password);
        }

        return false;
    }
}
