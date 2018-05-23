package example;

import example.models.CloudCore;
import example.models.FileTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by romanpauesov on 25.02.17.
 */
public class ClientFrame extends JFrame{
    JTable table = null;
    CloudCore core;

    public ClientFrame(final CloudCore core){
        super("Web Saver");
        this.core = core;
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton addButton = new JButton("Add File");
        JButton deleteButton = new JButton("Remove File");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700,500);
        this.add(panel1);
        FileTableModel model = new FileTableModel(core);
        table = new JTable(model);
        table.setAutoscrolls(true);
        table.setPreferredSize(new Dimension(600,300));
        JScrollPane pane = new JScrollPane(table);
        panel2.add(pane);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        panel2.add(buttonPanel);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser filechooser = new JFileChooser();
                int ret = filechooser.showOpenDialog(null);
                if (ret == JFileChooser.APPROVE_OPTION){
                    File files = filechooser.getSelectedFile();
                    core.putFile(files);
                    table.repaint();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                File file = core.localFiles.get(row);
                core.removeFile(file);
                table.repaint();
            }
        });
        this.add(panel2);
        this.pack();
    }

}
