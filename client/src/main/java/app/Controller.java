package app;

import app.models.CloudCore;
import app.models.FileData;
import app.services.EncriptService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class Controller implements Initializable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean authorized;
    private String login;
    //private ObservableList<String> clientsList;
    private ObservableList<FileData> fileList;
    private CloudCore core;
    FileChooser fileChooser = new FileChooser();

    @FXML
    TextField loginField;

    @FXML
    PasswordField passField;

    @FXML
    HBox authPanel, fileTablePanel;

    @FXML
    TableView<FileData> fileTable;

    @FXML
    private TableColumn<FileData, String> fileNameColumn;
    @FXML
    private TableColumn<FileData, String> fileSizeColumn;

    @FXML
    Button btnAddFile;

    @FXML
    Button btnDelFile;

    @FXML
    private void initialize() {
        fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFileName());
        fileSizeColumn.setCellValueFactory(cellData -> cellData.getValue().getSize());
    }


    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
        if (this.authorized) {
            authPanel.setVisible(false);
            authPanel.setManaged(false);
            fileTable.setVisible(true);
            fileTable.setManaged(true);
        } else {
            authPanel.setVisible(true);
            authPanel.setManaged(true);
            fileTable.setVisible(false);
            fileTable.setManaged(false);
            login = "";
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthorized(false);
        fileList = FXCollections.observableArrayList();
        fileTable.setItems(fileList);
        fileChooser.setTitle("Выбрать файлы...");
    }

//    public void sendMsg() {
//        try {
//              out.writeUTF(msgField.getText());
//              msgField.clear();
//              msgField.requestFocus();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void sendCustomMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFile(){
        List<File> list = fileChooser.showOpenMultipleDialog(app.MainApp.getPrimaryStage());
        if (list != null) {
            for (File file : list) {
                //openFile(file);
                UUID id = UUID.randomUUID();
                String size = String.valueOf(file.length()/1024);
                FileData fileData = new FileData(id.toString(), file.getName(), size);
                fileList.add(fileData);
                core.putFile(file);
                //fileTable.setItems(fileList);
            }
        }
    }

    public void deleteFile(){
        //fileTable.getFocusModel().getFocusedItem()
        //core.removeFile();
    }


    public void autorization() {
        try {
            socket = new Socket("localhost", 9999);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/authok ")) {
                                login = str.split(" ")[1];
                                setAuthorized(true);
                                System.out.println("авторизация прошла успешно");
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        showAlert("Произошло отключение от сервера");
                        setAuthorized(false);
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void connect() {
        autorization();
        try {
            socket = new Socket("localhost", 9999);
            CloudCore core = new CloudCore(socket);
            this.core = core;
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void sendAuth(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            String passEncript = EncriptService.generate(passField.getText());
            out.writeUTF("/auth " + loginField.getText() + " " + passField.getText());
            loginField.clear();
            passField.clear();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Невозможно отправить сообщение, проверьте сетевое соединение...");
        }
    }

    public void showAlert(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
            alert.showAndWait();
        });
    }

    public void clickClientsList(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
//            String str = fileTable.getSelectionModel().getSelectedItem();
//            msgField.setText("/w " + str + " ");
//            msgField.requestFocus();
//            msgField.selectEnd();
        }
    }
}
