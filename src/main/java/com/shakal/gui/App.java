package com.shakal.gui;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;

public class App extends JFrame {
    private JPanel panel1;
    private JButton button1;
    private JTextField textField1;
    private JButton button2;
    private JTree tree1;
    private JButton ath;

    public App() throws HeadlessException {
        super();
        setSize(500, 500);
        this.getContentPane().setLayout(new BorderLayout());
        final JCheckBoxTree cbt = new JCheckBoxTree();
        this.getContentPane().add(cbt);
        cbt.addCheckChangeEventListener(new JCheckBoxTree.CheckChangeEventListener() {
            public void checkStateChanged(JCheckBoxTree.CheckChangeEvent event) {
                System.out.println("event");
                TreePath[] paths = cbt.getCheckedPaths();
                for (TreePath tp : paths) {
                    for (Object pathPart : tp.getPath()) {
                        System.out.print(pathPart + ",");
                    }
                    System.out.println();
                }
            }
        });
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        App app = new App();
        app.setVisible(true);

    }

}
