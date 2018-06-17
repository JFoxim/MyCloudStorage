package common.services;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import common.messages.FileMessage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class FilePartitionService {

    private static final int PART_SIZE = 4;

    public static void sendFile(Path path, ObjectOutputStream out, ProgressBar progressBar){
        try {
            byte[] fileData = Files.readAllBytes(path);
            int partsCount = fileData.length / PART_SIZE;
            if (fileData.length % PART_SIZE != 0){
                partsCount++;
            }

            if (progressBar != null) {
                Platform.runLater(() -> {
                    progressBar.setVisible(true);
                    progressBar.setManaged(true);
                });
            }

            for (int i=0; i < partsCount; i++){
                int startPosition = i* PART_SIZE;
                int endPosition = (i+1)*PART_SIZE;
                if (endPosition > fileData.length){
                    endPosition = fileData.length;
                }

                if (progressBar != null) {
                    final double prog = (double)i/partsCount;
                    Platform.runLater(() -> progressBar.setProgress(prog));
                }
                FileMessage fm = new FileMessage(path.getFileName().toString(),
                        Arrays.copyOfRange(fileData, startPosition, endPosition), partsCount, i);
                out.writeObject(fm);
                out.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (progressBar != null) {
                Platform.runLater(() -> {
                    progressBar.setVisible(false);
                    progressBar.setManaged(false);
                });
            }
        }
    }

    public static void receiveFile(ObjectInputStream in, String directoryPath, FileMessage fm, ProgressBar progressBar){
        try {
            Path path = Paths.get(directoryPath+"/" + fm.getFilename());
            if (Files.exists(path)) {
                Files.delete(path);
            }
            if (progressBar != null) {
                Platform.runLater(() -> {
                    progressBar.setVisible(true);
                    progressBar.setManaged(true);
                });
            }

            Files.createFile(path);
            Files.write(path, fm.getData(), StandardOpenOption.APPEND);
            if (fm.getPartsCount() > 1){
                for (int i = 1; i< fm.getPartsCount(); i++) {
                    if (progressBar != null) {
                        final int w = i;
                        Platform.runLater(() -> progressBar.setProgress((double) w / fm.getPartsCount()));
                    }
                    FileMessage fm0 = (FileMessage)in.readObject();
                    Files.write(path, fm0.getData(), StandardOpenOption.APPEND);
                }
            }
            if (progressBar != null) {
                Platform.runLater(() -> {
                    progressBar.setVisible(false);
                    progressBar.setManaged(false);
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
