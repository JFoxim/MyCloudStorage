package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import model.NetCore;
import common.messages.*;
import auth.services.EncriptService;



public class AuthController implements Initializable {
    @FXML
    HBox  btnPanel;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button btnLogin;

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private NetCore netCore;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void showMessageAuthWrong(){
        Alert alert = new Alert(Alert.AlertType.ERROR, "Пароль или логин не верны", ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Внимание ошибка");
        alert.show();
    }

    private void openMainWindow() {
        Parent mainRoot = null;
        try {
            mainRoot = FXMLLoader.load(getClass().getClassLoader().getResource("MainView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene mainWindowScene = new Scene(mainRoot, 800, 600);

        Stage mainWindowStage = new Stage();
        mainWindowStage.setTitle("Облачное хранилище");
        mainWindowStage.setScene(mainWindowScene);
        mainWindowStage.show();

        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }

    private boolean authService(){
        boolean result = false;
        Object obj = null;
        try {
            obj = in.readObject();
            if (obj instanceof CommandMessage) {
                CommandMessage cm = (CommandMessage)obj;
                if (cm.getType() == CommandMessage.CMD_MSG_AUTH_OK){
                    Platform.runLater(() -> openMainWindow());
                    result = true;
                }else if(cm.getType() == CommandMessage.CMD_MSG_AUTH_WRONG){
                    Platform.runLater(() -> showMessageAuthWrong());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

   private void login(){
       try {
           netCore = new NetCore();
           netCore.initSocket();
           socket = netCore.getSocket();
           out = netCore.getOut();
           in = netCore.getIn();
           Thread t = new Thread(() -> {
               try {
                   while (true) {
                       if(authService())
                           break;
                   }
               }catch (Exception e){
                   e.printStackTrace();
                   try {
                       socket.close();
                   } catch (IOException er) {
                       er.printStackTrace();
                   }
                   try {
                       in.close();
                   } catch (IOException er) {
                       er.printStackTrace();
                   }
                   try {
                       out.close();
                   } catch (IOException er) {
                       er.printStackTrace();
                   }
               }
           });
           t.setDaemon(true);
           t.start();
       } catch (Exception e){
           e.printStackTrace();
       }
   }

   public void tryToAuthorize(ActionEvent actionEvent) {
       if (socket == null || socket.isClosed()){
           login();
       }
       AuthMessage am = null;
       try {
           am = new AuthMessage(loginField.getText(), EncriptService.encript(passField.getText()));
       } catch (Exception e) {
           e.printStackTrace();
       }
       sendMsg(am);
    }

    public void sendMsg(AbstractMessage am) {
        try{
            out.writeObject(am);
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void openRegistrationWindow(ActionEvent actionEvent) {
        Parent mainRoot = null;
        try {
            mainRoot = FXMLLoader.load(getClass().getClassLoader().getResource("RegistrationView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene mainWindowScene = new Scene(mainRoot, 300, 200);

        Stage mainWindowStage = new Stage();
        mainWindowStage.setTitle("Регистрация нового пользователя");
        mainWindowStage.setScene(mainWindowScene);
        mainWindowStage.getIcons().add(new Image("/images/key_orange.png"));
        mainWindowStage.showAndWait();
    }
}
