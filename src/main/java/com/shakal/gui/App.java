package com.shakal.gui;

import com.shakal.PDFUtils;
import com.shakal.exceptions.IncorrectURL;
import com.shakal.exceptions.ParseError;
import com.shakal.sources.*;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class App {
    private JPanel panel1;
    private JButton downloadButton;
    private JTextField textField1;
    private JButton findButton;
    private JButton ath;
    private JLabel pathLabel;
    private JScrollPane asd;
    private JPanel pan;
    private JRadioButton chaptersRadioButton;
    private JRadioButton volumesRadioButton;
    private JRadioButton allRadioButton;
    private JLabel rusName;
    private JLabel engName;
    private JLabel status;
    private JLabel downloadBar;
    private JTree tree1;
    private JFileChooser fileChooser;
    private JOptionPane optionPane;
    private Site site;
    private JCheckBoxTree cbt;

    public App() {
        fileChooser = new JFileChooser();
        pathLabel.setText(fileChooser.getCurrentDirectory().getAbsolutePath());
        setFindButton();
        setAth();
        setDownloadButton();
    }

    private void setDownloadButton() {
        downloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        downloadBar.setText("0 / ?");
                        ArrayList<Chapter> chapters = new ArrayList<Chapter>();
                        int progress = 0;

                        if (cbt != null) {
                            for (TreePath treePath : cbt.getCheckedPaths()) {
                                Chapter chapter = findChapter(treePath.getLastPathComponent().toString());
                                if (chapter != null) {
                                    chapters.add(chapter);
                                }
                            }

                            downloadBar.setText(progress + " / " + chapters.size());
                            String folder = rusName.getText() + " - " + engName.getText();
                            boolean res = new File(pathLabel.getText() + File.separator + folder + File.separator).mkdir();

                            if (!res) {
                                System.out.println(pathLabel.getText() + File.separator + folder);
                                downloadBar.setText("Error!");
                                return;
                            }

                            for (Chapter ch : chapters) {
                                try {
                                    ArrayList<BufferedImage> images = site.getImages(ch.getUrl());
                                    PDDocument pdDocument = PDFUtils.imagesToPDF(images);
                                    pdDocument.save(pathLabel.getText() + File.separator + folder + File.separator + ch.getVolAndChap().replace(".", " ") + " - " + ch.getName() + ".pdf");
                                    progress += 1;
                                    downloadBar.setText(progress + " / " + chapters.size());
                                } catch (ParseError parseError) {
                                    parseError.printStackTrace();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            downloadBar.setText("Заверешно!");

                        }
                    }
                }).start();
            }
        });
    }

    public static void main(String[] args) {
        App app = new App();
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
                        findButton.setEnabled(false);
                        status.setText("Working....");
                        String res;
                        try {
                            site = new Mangalib(new URL(textField1.getText()));
                            rusName.setText(site.getMangaName(ParseManga.Language.RUS));
                            engName.setText(site.getMangaName(ParseManga.Language.ENG));
                            buildTree();
                            res = "Успешно найдено!";
                        } catch (IncorrectURL incorrectURL) {
                            incorrectURL.printStackTrace();
                            res = "Не правильный URL к манге";
                        } catch (ParseError parseError) {
                            parseError.printStackTrace();
                            res = "Ошибка парсинга сайта, набейте лицо разработчику";
                        } catch (MalformedURLException xe) {
                            xe.printStackTrace();
                            res = "Не верный тип URL";
                        }
                        status.setText(res);
                        findButton.setEnabled(true);
                    }
                }).start();
            }
        });
    }

    private void buildTree() {
        clearTree();
        DefaultMutableTreeNode manga = null;
        DefaultMutableTreeNode volume = null;
        int tempVol = -1;
        try {
            manga = new DefaultMutableTreeNode(site.getMangaName(ParseManga.Language.RUS));
        } catch (ParseError parseError) {
            parseError.printStackTrace();
        }
        for (Chapter chapter : site.getChapters()) {
            if (tempVol != chapter.getVolume()) {
                if (volume != null && manga != null) {
                    manga.add(volume);
                }
                tempVol = chapter.getVolume();
                volume = new DefaultMutableTreeNode("Toм " + chapter.getVolume());

            }
            if (volume != null) {
                DefaultMutableTreeNode chpt = new DefaultMutableTreeNode(chapter.getVolAndChap());
                volume.add(chpt);
            }
        }
        if (manga != null) {
            manga.add(volume);
        }
        TreeModel selectionModel = new DefaultTreeModel(manga);
        cbt = new JCheckBoxTree();
        cbt.setModel(selectionModel);
        pan.add(cbt);
    }

    private void clearTree() {
        pan.removeAll();
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

    private Chapter findChapter(String volAndChap) {
        for (Chapter chapter : site.getChapters()) {
            if (chapter.getVolAndChap().equals(volAndChap)) {
                return chapter;
            }
        }
        return null;
    }
}
