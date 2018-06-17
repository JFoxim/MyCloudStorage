package controllers;

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
import model.NetCore;
import common.messages.*;
import common.services.FilePartitionService;


public class MainController implements Initializable {
    @FXML
    HBox  actionPanel1, actionPanel2;

    @FXML
    ListView<File> cloudList, localList;

    @FXML
    ProgressBar operationProgress;

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private NetCore netCore;
    private ObservableList<File> cloudFilesList;
    private ObservableList<File> localFilesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cloudFilesList = FXCollections.observableArrayList();
        cloudList.setItems(cloudFilesList);
        localFilesList = FXCollections.observableArrayList();
        localList.setItems(localFilesList);
        socket = NetCore.getSocket();
        out = NetCore.getOut();
        in = NetCore.getIn();
        refreshLocalList();
        connect();

        localList.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
            @Override
            public ListCell<File> call(ListView<File> param) {
                return new ListCell<File>(){
                 @Override
                 protected void updateItem(File item, boolean empty) {
                     super.updateItem(item, empty);
                     if (!empty) {
                         if (item.isFile()){
                             Rectangle rect = new Rectangle(1, 1, 6, 6);
                             rect.setFill(Color.ORANGE);
                             setGraphic(rect);
                         }else{
                             Rectangle rect = new Rectangle(1, 1, 6, 6);
                             rect.setFill(Color.BLUE);
                             setGraphic(rect);
                         }
                         setText(String.format("%30s      %d bytes", item.getName(), item.length()));
                     } else{
                         setGraphic(null);
                         setText(null);
                     }

                     localList.setOnDragOver(event-> {
                         if(event.getGestureSource() != localList && event.getDragboard().hasFiles()){
                             event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                         }
                         event.consume();
                     });

//                     localList.setOnDragEntered(new EventHandler<DragEvent>() {
//                         @Override
//                         public void handle(DragEvent event) {
//                             event.getDragboard().getFiles().add(new File("client/local_storage/234.txt"));
//
//                         }
//                     });

                     localList.setOnDragDropped(event -> {
                         Dragboard db = event.getDragboard();
                         boolean success = false;
                         if (db.hasFiles()){
                             for (int i=0; i <db.getFiles().size(); i++){

                                     try {
                                         Files.copy(Paths.get(db.getFiles().get(i).getAbsolutePath()),
                                                 Paths.get("client/local_storage/"+db.getFiles().get(i).getName()),
                                                 StandardCopyOption.REPLACE_EXISTING);
                                     } catch (IOException e) {
                                         e.printStackTrace();
                                     }
                             }
                             refreshLocalList();
                             success = true;
                         }
                         event.setDropCompleted(success);
                         event.consume();
                     });
                 }
                };
            }
        });
    }

    public void connect(){
        try {
            Thread t = new Thread(() -> {
                try {
                    while (true) {
                        Object obj = in.readObject();
                        if (obj instanceof FileListMessage) {
                            FileListMessage flm = (FileListMessage) obj;
                            Platform.runLater(() -> {
                                cloudFilesList.clear();
                                cloudFilesList.addAll(flm.getFiles());
                            });
                        }
                        if (obj instanceof FileMessage) {
                            FileMessage fm = (FileMessage) obj;
                            FilePartitionService.receiveFile(in, "client/local_storage", fm, operationProgress);
                            Platform.runLater(() -> refreshLocalList());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
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
            });
            t.setDaemon(true);
            t.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnSendFile(ActionEvent actionEvent) {
      try{
          FilePartitionService.sendFile(Paths.get(localList.getSelectionModel().getSelectedItem().getAbsolutePath()), out, operationProgress);
      }catch (Exception e){
          e.printStackTrace();
      }
    }


    public void sendMsg(AbstractMessage am) {
        try{
            out.writeObject(am);
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void requestFileDownload(ActionEvent actionEvent) {
        if (cloudList != null && cloudList.getSelectionModel() != null && cloudList.getSelectionModel().getSelectedItem() != null) {
            File file = cloudList.getSelectionModel().getSelectedItem();
            CommandMessage cm = new CommandMessage(CommandMessage.CMD_MSG_REQUES_FILE_DOWNLOAD, file);
            sendMsg(cm);
        }
        else{
           Alert alert = new Alert(Alert.AlertType.INFORMATION, "Выберите файл", ButtonType.OK);
           alert.setTitle("Внимание");
           alert.setHeaderText(null);
           alert.show();
        }
    }

    public void refreshLocalList(){
        try {
            localFilesList.clear();
            localFilesList.addAll(Files.list(Paths.get("client/local_storage"))
                    .map(Path::toFile).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnLocalDeleteFile(ActionEvent actionEvent) {
        try {
            Files.delete(Paths.get(localList.getSelectionModel().getSelectedItem().getAbsolutePath()));
            refreshLocalList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnLocalRefresh(ActionEvent actionEvent) {
        refreshLocalList();
    }

    public void btnCloudRefresh(ActionEvent actionEvent) {
        CommandMessage cm = new CommandMessage(CommandMessage.CMD_MSG_REQUEST_FILES_LIST);
        sendMsg(cm);
    }

    public void requestCloudDelteFile(ActionEvent actionEvent) {
        CommandMessage cm = new CommandMessage(CommandMessage.CMD_MSG_REQUEST_SERVER_DELETE_FILE,
                cloudList.getSelectionModel().getSelectedItem().getName());
        sendMsg(cm);
    }
}
