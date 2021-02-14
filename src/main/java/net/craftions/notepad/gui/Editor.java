/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.notepad.gui;

import net.craftions.notepad.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Editor {

    public static JFrame frame = null;
    public static JEditorPane editorPane = null;

    public static File currentFile = null;

    public static void showEditor(){
        frame = new JFrame("Craftions Editor | v1.0");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel p = new JPanel();
        JMenuBar bar = new JMenuBar();
        JMenu m_file = new JMenu();
        JMenuItem save = new JMenuItem("Speichern...");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        JMenuItem open = new JMenuItem("Öffnen...");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(frame);
                File t = chooser.getSelectedFile();
                loadFile(t);
            }
        });
        JMenuItem about = new JMenuItem("Über...");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Name: Craftions Edtitor\nVersion: 1.0-SNAPSHOT\nAutor: Craftions.net Development Team (MCTzOCK)\nLicense: GNU GPLv3\nCopyright (c) 2020-2021 Craftions.", "Über Craftions Editor", JOptionPane.INFORMATION_MESSAGE, null);
            }
        });
        JMenuItem exit = new JMenuItem("Verlassen...");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        m_file.setText("Datei");
        m_file.add(save);
        m_file.add(open);
        m_file.add(about);
        m_file.add(exit);
        bar.add(m_file);
        frame.setJMenuBar(bar);

        editorPane = new JEditorPane();
        editorPane.setEditable(true);
        editorPane.setContentType("text/plain");
        editorPane.setFont(new Font("Arial", 0, 20));
        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        p.setLayout(new GridLayout(1,1));
        p.add(scrollPane);
        frame.setSize((int) screenSize.getWidth() / 2, (int) screenSize.getHeight() / 2);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
    }

    public static void loadFile(File f){
        try {
            byte[] c = Files.readAllBytes(Path.of(f.getAbsolutePath()));
            String cn = new String(c, StandardCharsets.UTF_8);
            editorPane.setText(cn);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void save(){
        if(currentFile == null){
            JFileChooser chooser = new JFileChooser();
            chooser.showSaveDialog(frame);
            File target = chooser.getSelectedFile();
            FileWriter w = null;
            try {
                w = new FileWriter(target);
                w.write(editorPane.getText());
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            FileWriter w = null;
            try {
                w = new FileWriter(currentFile);
                w.write(editorPane.getText());
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void exit(){
        int x = JOptionPane.showConfirmDialog(null, "Willst du vor dem Schließen speichern?", "Speichern?", JOptionPane.YES_NO_CANCEL_OPTION);
        if(x == 1){
            System.exit(0);
        }else if(x == 0){
            save();
            System.exit(0);
        }
    }
}

