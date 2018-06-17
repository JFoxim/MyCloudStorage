package auth;

import auth.services.EncriptService;

public class Token implements java.security.Principal, java.io.Serializable {

    private String name;
    private String password;

    public Token(String name, String pass) {
        if (name == null || password == null)
            throw new NullPointerException("недопустимый ввод");

        this.name = name;
        this.password = pass;
    }

    public String getName() {
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String toString() {
        return("auth.Token:  " + name);
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof Token))
            return false;
          Token that = (Token)o;

        if (this.getName().equals(that.getName()) && this.password.equals(that.password))
            return true;
        return false;
    }

    public int hashCode() {
        return name.hashCode();
    }
}

