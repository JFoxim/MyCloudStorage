import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AuthController implements Initializable {
    @FXML
    HBox authPanel;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passField;


    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean authorized;
    private ObservableList<File> cloudFilesList;
    private ObservableList<File> localFilesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthorized(false);
        cloudFilesList = FXCollections.observableArrayList();
        localFilesList = FXCollections.observableArrayList();
    }

    private void showMessageAuthWrong(){
        Alert alert = new Alert(Alert.AlertType.ERROR, "Пароль или логин не верны", ButtonType.OK);
        alert.show();
    }



   public void tryToAuthorize(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()){
  //           connect();
        }
       AuthMessage am = null;
       try {
           am = new AuthMessage(loginField.getText(), EncriptService.run(passField.getText()));
       } catch (Exception e) {
           e.printStackTrace();
       }
       sendMsg(am);
         passField.setText(null);
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
        authPanel.setManaged(!this.authorized);
        authPanel.setVisible(!this.authorized);
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
