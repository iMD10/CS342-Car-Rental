package org.example.classes;

public class User {
    private int id;
    private String Name, email,password ;
    private String  phoneNumber ; // phone number is optional, so its null if user dont want to add his phone

    public User(int id, String Name, String email, String password, String phoneNumber) {
        this.id = id;
        this.Name = Name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }


    public void signIn(User userInfo){

    }


    public boolean login(String email, String password){



        // wrong email or password
        return false;
    }

}
