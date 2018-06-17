package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

public class RegistrationController implements Initializable {
    @FXML
    HBox  btnPanel;

    @FXML
    private TextField loginField, emailField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button btnСonfirm;

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private NetCore netCore;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void showMsgRegisterWrong(){
        Alert alert = new Alert(Alert.AlertType.ERROR, "Логин или email уже зарегестрированы в системе, пожалуйста укажите другой логин" +
                " или email", ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Внимание ошибка");
        alert.show();
    }

    private void showMsgRegisterSucsess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Вы успешно зарегестрированы", ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Внимание");
        alert.show();
        Stage stage = (Stage) btnСonfirm.getScene().getWindow();
        stage.close();
    }

    private boolean registerSuccess(){
        boolean result = false;
        Object obj = null;
        try {
            obj = in.readObject();
            if (obj instanceof CommandMessage) {
                CommandMessage cm = (CommandMessage)obj;
                if (cm.getType() == CommandMessage.CMD_MSG_USER_CREATE_OK){
                    Platform.runLater(() -> showMsgRegisterSucsess());
                    result = true;
                }else if(cm.getType() == CommandMessage.CMD_MSG_USER_CREATE_WRONG){
                    Platform.runLater(() -> showMsgRegisterWrong());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void connectServer(){
        try {
            netCore = new NetCore();
            netCore.initSocket();
            socket = netCore.getSocket();
            out = netCore.getOut();
            in = netCore.getIn();
            Thread t = new Thread(() -> {
                try {
                       while (true) {
                         if (registerSuccess())
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

    public void tryToСonfirm(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()){
            connectServer();
        }
        RegistMessage registMessage = null;
        try {
            registMessage = new RegistMessage(loginField.getText(), EncriptService.encript(passField.getText()), emailField.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendMsg(registMessage);

    }


    public void sendMsg(AbstractMessage am) {
        try{
            out.writeObject(am);
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
