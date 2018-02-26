package apps_x.com.mystockapp.ui.api.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("phone")
    String phone;
    @SerializedName("username")
    String userName;
    @SerializedName("firstname")
    String firstName;
    @SerializedName("lastname")
    String lastName;
    @SerializedName("email")
    String email;
    @SerializedName("balance")
    String balance;
    @SerializedName("verified")
    String verified;
    @SerializedName("date_created")
    String dateCreated;
    @SerializedName("last_login")
    String lastLogin;

    public User(String phone, String userName, String firstName, String lastName, String email, String balance, String verified, String dateCreated, String lastLogin) {
        this.phone = phone;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.balance = balance;
        this.verified = verified;
        this.dateCreated = dateCreated;
        this.lastLogin = lastLogin;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getBalance() {
        return balance;
    }

    public String getVerified() {
        return verified;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getLastLogin() {
        return lastLogin;
    }
}
