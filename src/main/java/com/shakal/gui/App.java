package com.shakal.gui;

import com.shakal.exceptions.IncorrectURL;
import com.shakal.exceptions.ParseError;
import com.shakal.sources.Mangalib;
import com.shakal.sources.Site;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

public class App {
    private JPanel panel1;
    private JButton downloadButton;
    private JTextField textField1;
    private JButton findButton;
    private JButton ath;
    private JLabel pathLabel;
    private JScrollPane asd;
    private JPanel pan;
    private JTree tree1;
    private JFileChooser fileChooser;
    private JOptionPane optionPane;
    private Site site;

    public App() {
        fileChooser = new JFileChooser();
        pathLabel.setText(fileChooser.getCurrentDirectory().getAbsolutePath());
        setFindButton();
        setAth();

        asd.add(new Button("2342342"));
    }

    public static void main() {
        App app = new App();
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Anime");
        TreeModel selectionModel = new DefaultTreeModel(node);
        final JCheckBoxTree cbt = new JCheckBoxTree();
        cbt.setModel(selectionModel);
        app.pan.add(cbt);
        JFrame frame = new JFrame("Mangach");
        frame.setContentPane(app.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    private void setFindButton() {
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        pathLabel.setText("Working....");
                        String res = "";
                        try {
                            site = new Mangalib(new URL(textField1.getText()));
                        } catch (IncorrectURL incorrectURL) {
                            optionPane = new JOptionPane("Test");
                            optionPane.setVisible(true);
                            res = incorrectURL.getMessage();
                        } catch (ParseError parseError) {
                            res = parseError.getMessage();
                        } catch (MalformedURLException xe) {
                            res = (xe.getMessage());
                        }
                        pathLabel.setText(res);
                    }
                }).start();
            }
        });
    }

    private void setAth() {
        ath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Choose path ^_^");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = fileChooser.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    pathLabel.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
    }

}
