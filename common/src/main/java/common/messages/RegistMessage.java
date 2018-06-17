package common.messages;

public class RegistMessage extends AbstractMessage  {

        private String login;
        private String pass;
        private String email;

        public RegistMessage(String login, String pass, String email) {
            this.login = login;
            this.pass = pass;
            this.email = email;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
