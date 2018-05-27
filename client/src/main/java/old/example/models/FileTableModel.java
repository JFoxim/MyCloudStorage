package old.example.models;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by romanpauesov on 28.02.17.
 */
public class FileTableModel extends AbstractTableModel {
    public List<File> files;

    public FileTableModel(CloudCore core){
        super();
        files = core.localFiles;
    }
    public FileTableModel(){
        files = new ArrayList<File>();
    }

    public int getRowCount() {
        return files.size();
    }

    public int getColumnCount() {
        return 2;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0: return files.get(rowIndex).getName();
            case 1: return (double) files.get(rowIndex).length() / 1000;
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0 : return "Name";
            case 1 : return "Size";
            default: return "unnamed";
        }
    }
}
