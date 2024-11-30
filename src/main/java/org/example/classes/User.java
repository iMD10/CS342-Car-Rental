package org.example.classes;

public class User {
    private int id;
    private String fName, lName, email,password ;
    private String  phoneNumber = null; // phone number is optional, so its null if user dont want to add his phone

    public User(int id, String fName, String lName, String email, String password) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
    }

    /** Takes user object 'userInfo' and load his info into user DB
     */
    public void signIn(User userInfo){

    }

    /** Takes user email and password, checks if they match in user db.
     *  return true if match, false otherwise
     * */
    public boolean login(String email, String password){



        // wrong email or password
        return false;
    }

}
