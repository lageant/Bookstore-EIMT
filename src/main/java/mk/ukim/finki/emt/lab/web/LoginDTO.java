package mk.ukim.finki.emt.lab.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDTO {
    @NotNull
    @Size(min = 3,max = 30)
    private String username;
    @NotNull
    @Size(min = 3,max = 30)
    private String password;
    @NotNull
    @Size(min = 3,max = 30)
    private String confirm_password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }
}
