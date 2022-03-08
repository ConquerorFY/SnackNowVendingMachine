
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.commons.io.FilenameUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public class AdminUpdateVendingMachineForm extends javax.swing.JFrame {

    /**
     * Creates new form AdminUpdateVendingMachineForm
     */
    private String saveDir, imageFileName, sid;
    private String snackPrefix = "Sn";
    private int newSnackID;
    private ArrayList<String> tmpSnackNames = new ArrayList<String>();
    private String selectedSnackName;

    private int mouseX;
    private int mouseY;

    public AdminUpdateVendingMachineForm() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(246, 131, 112));

        newSnackNameText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().snackNameValidation((JTextField) input);
            }
        });
        newSnackQuantityText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().snackQuantityValidation((JTextField) input);
            }
        });
        newSnackPriceText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().snackPriceValidation((JTextField) input);
            }
        });
        /* existingSnackNameText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().snackNameValidation((JTextField) input);
            }
        });
        existingSnackQuantityText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().snackQuantityValidation((JTextField) input);
            }
        });
        existingSnackPriceText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().snackPriceValidation((JTextField) input);
            }
        }); */

        try {
            saveDir = System.getProperty("user.dir") + "/StaffInfo/";
            File cache = new File(saveDir + "Cache.txt");

            Scanner inputFile = new Scanner(cache);
            while (inputFile.hasNext()) {
                String entry = inputFile.nextLine();
                String[] matchedID = entry.split(":");
                usernameLbl.setText(matchedID[1]);
            }
            inputFile.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        displaySnackList();

        snackItemsList.addListSelectionListener(
                new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    selectedSnackName = tmpSnackNames.get(snackItemsList.getSelectedIndex());
                    saveDir = System.getProperty("user.dir") + "/SnacksInfo/";
                    File snacks = new File(saveDir + "Snacks.txt");

                    try {
                        Scanner inputFile = new Scanner(snacks);
                        String[] matchedID = null;

                        while (inputFile.hasNext()) {
                            String entry = inputFile.nextLine();
                            matchedID = entry.split(">");

                            if (matchedID[1].equals(selectedSnackName)) {
                                existingSnackNameText.setText(selectedSnackName);
                                existingSnackQuantityText.setText(matchedID[2]);
                                existingSnackPriceText.setText(matchedID[3]);
                                
                                existingSnackNameText.setBackground(new Color(102, 102, 102));
                                existingSnackQuantityText.setBackground(new Color(102, 102, 102));
                                existingSnackPriceText.setBackground(new Color(102, 102, 102));
                                break;
                            }

                        }
                        inputFile.close();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }

            }
        }
        );

    }

    private void displaySnackList() {
        try {
            tmpSnackNames.clear();
            saveDir = System.getProperty("user.dir") + "/SnacksInfo/";
            File snacks = new File(saveDir + "Snacks.txt");
            if (!snacks.exists()) {
                snacks.createNewFile();
            }

            Scanner inputFile = new Scanner(snacks);
            while (inputFile.hasNext()) {
                tmpSnackNames.add((inputFile.nextLine().split(">")[1]));
            }
            inputFile.close();

            String[] snackNames = new String[tmpSnackNames.size()];
            for (int i = 0; i < tmpSnackNames.size(); i++) {
                snackNames[i] = tmpSnackNames.get(i);
            }
            snackItemsList.setListData(snackNames);
            snackItemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        } catch (Exception e) {

        }
    }

    private void highlightEmpty() {
        if ("".equals(newSnackNameText.getText())) {
            newSnackNameLbl.setForeground(Color.yellow);
        }
        if ("".equals(newSnackQuantityText.getText())) {
            newSnackQuantityLbl.setForeground(Color.yellow);
        }
        if ("".equals(newSnackPriceText.getText())) {
            newSnackPriceLbl.setForeground(Color.yellow);
        }
    }

    private void deHighlightEmpty() {
        newSnackNameLbl.setForeground(Color.white);
        newSnackQuantityLbl.setForeground(Color.white);
        newSnackQuantityLbl.setForeground(Color.white);
    }

    private void emptyFields() throws Exception {
        if ("".equals(newSnackNameText.getText())) {
            throw new Exception("Empty new snack name");
        }
        if ("".equals(newSnackQuantityText.getText())) {
            throw new Exception("Empty new snack quantity");
        }
        if ("".equals(newSnackPriceText.getText())) {
            throw new Exception("Empty new snack price");
        }
    }

    private void snackIncrementor() {
        String matchedID[] = null;
        boolean hasRecord = false;
        try {
            saveDir = System.getProperty("user.dir") + "/SnacksInfo/";
            File snackstxt = new File(saveDir + "Snacks.txt");
            if (!snackstxt.exists()) {
                snackstxt.createNewFile();
            }

            Scanner inputFile;
            try {
                inputFile = new Scanner(snackstxt);
                while (inputFile.hasNext()) {
                    String entry = inputFile.nextLine();
                    matchedID = entry.split(">");
                    matchedID[0] = matchedID[0].replace("Sn", "");
                    hasRecord = true;
                }
                inputFile.close();

                if (!hasRecord) {
                    JOptionPane.showMessageDialog(null, "No snack(s) was found!", "Snack database is empty", JOptionPane.ERROR_MESSAGE);
                    newSnackID = 1;
                } else {
                    newSnackID = Integer.parseInt(matchedID[0]) + 1;
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e);
            JOptionPane.showMessageDialog(null, "Invalid input! Snack ID can only consist of numbers", "Invalid input type", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean cacheClear() {
        try {
            saveDir = System.getProperty("user.dir") + "/StaffInfo/";
            File cache = new File(saveDir + "Cache.txt");
            if (cache.exists()) {
                cache.delete();
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    private String insertImage(String imageFileSelected) throws IOException {
        File file = new File(imageFileSelected);
        String saveDir = System.getProperty("user.dir");
        String destination = saveDir + "\\SnackImages\\";
        String filename = FilenameUtils.getName(imageFileSelected);
        String newFileName = destination + filename;

        File newFile = new File(newFileName);

        try {
            if (newFile.exists()) {
                throw new java.io.IOException("File already exists");
            }
            Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "[!!Error Filename!!]";
        }
    }

    private boolean snackNameValidator(JTextField text) {
        String saveDir = System.getProperty("user.dir") + "/SnacksInfo/";
        File snacks = new File(saveDir + "Snacks.txt");
        String[] matchedID = null;
        boolean nameTaken = false;

        try {
            Scanner input = new Scanner(snacks);
            while (input.hasNext()) {
                String entry = input.nextLine();
                matchedID = entry.split(">");
                String snackName = matchedID[1];
                if (snackName.equals(text.getText())) {
                    nameTaken = true;
                }
            }
            input.close();

            if (nameTaken && !snackItemsList.getSelectedValue().equals(text.getText())) {
                return true;
            } else if (nameTaken && snackItemsList.getSelectedValue().equals(text.getText())) {
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jLabel1 = new javax.swing.JLabel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        useVendingMachineBtn = new javax.swing.JButton();
        accountHomePageBtn = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        copyrightLbl = new javax.swing.JLabel();
        enquiryLbl = new javax.swing.JLabel();
        headerPanel = new javax.swing.JPanel();
        snackNowLbl = new javax.swing.JLabel();
        welcomeLbl = new javax.swing.JLabel();
        usernameLbl = new javax.swing.JLabel();
        logoutBtn = new javax.swing.JButton();
        customizePanel = new javax.swing.JPanel();
        existingSnackQuantityText = new javax.swing.JTextField();
        existingSnackPriceLbl = new javax.swing.JLabel();
        existingSnackPriceText = new javax.swing.JTextField();
        customizeLbl = new javax.swing.JLabel();
        existingSnackNameText = new javax.swing.JTextField();
        existingSnackQuantityLbl = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        snackItemsList = new javax.swing.JList<>();
        existingSnackNameLbl = new javax.swing.JLabel();
        snackItemsLbl = new javax.swing.JLabel();
        existingDeleteBtn = new javax.swing.JButton();
        existingConfirmBtn = new javax.swing.JButton();
        existingResetBtn = new javax.swing.JButton();
        newSnackItemsPanel = new javax.swing.JPanel();
        newSnackItemsLbl = new javax.swing.JLabel();
        newSnackNameLbl = new javax.swing.JLabel();
        newSnackQuantityLbl = new javax.swing.JLabel();
        newSnackPriceLbl = new javax.swing.JLabel();
        newSnackNameText = new javax.swing.JTextField();
        newSnackQuantityText = new javax.swing.JTextField();
        newSnackPriceText = new javax.swing.JTextField();
        newConfirmBtn = new javax.swing.JButton();
        newResetBtn = new javax.swing.JButton();
        attachImageBtn = new javax.swing.JButton();
        newSnackImage = new javax.swing.JLabel();

        jScrollPane1.setViewportView(jTree1);

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        useVendingMachineBtn.setBackground(new java.awt.Color(102, 102, 255));
        useVendingMachineBtn.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        useVendingMachineBtn.setForeground(new java.awt.Color(255, 255, 255));
        useVendingMachineBtn.setText("Use Vending Machine");
        useVendingMachineBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        useVendingMachineBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        useVendingMachineBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        useVendingMachineBtn.setInheritsPopupMenu(true);
        useVendingMachineBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                useVendingMachineBtnMouseClicked(evt);
            }
        });
        useVendingMachineBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useVendingMachineBtnActionPerformed(evt);
            }
        });

        accountHomePageBtn.setBackground(new java.awt.Color(255, 102, 0));
        accountHomePageBtn.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        accountHomePageBtn.setForeground(new java.awt.Color(255, 255, 255));
        accountHomePageBtn.setText("Account Home Page");
        accountHomePageBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        accountHomePageBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        accountHomePageBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        accountHomePageBtn.setInheritsPopupMenu(true);
        accountHomePageBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountHomePageBtnMouseClicked(evt);
            }
        });
        accountHomePageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountHomePageBtnActionPerformed(evt);
            }
        });

        jPanel15.setBackground(new java.awt.Color(92, 204, 206));
        jPanel15.setPreferredSize(new java.awt.Dimension(908, 20));
        jPanel15.setLayout(new java.awt.GridLayout(1, 0));

        copyrightLbl.setBackground(new java.awt.Color(0, 0, 0));
        copyrightLbl.setFont(new java.awt.Font("Yu Gothic UI Semilight", 3, 14)); // NOI18N
        copyrightLbl.setForeground(new java.awt.Color(255, 255, 255));
        copyrightLbl.setText("               Copyright 2020 Ryan Lim Fang Yung TP055343 ");
        copyrightLbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel15.add(copyrightLbl);

        enquiryLbl.setBackground(new java.awt.Color(0, 0, 0));
        enquiryLbl.setFont(new java.awt.Font("Yu Gothic UI Semilight", 3, 14)); // NOI18N
        enquiryLbl.setForeground(new java.awt.Color(255, 255, 255));
        enquiryLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        enquiryLbl.setText("For Business Enquires: tp055343@mail.apu.edu.my");
        enquiryLbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel15.add(enquiryLbl);

        headerPanel.setBackground(new java.awt.Color(176, 0, 81));
        headerPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headerPanelMouseDragged(evt);
            }
        });
        headerPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headerPanelMousePressed(evt);
            }
        });

        snackNowLbl.setBackground(new java.awt.Color(204, 204, 0));
        snackNowLbl.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        snackNowLbl.setForeground(new java.awt.Color(255, 255, 255));
        snackNowLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        snackNowLbl.setText("<html>SnackNow <br>Vending Machine</html> ");

        welcomeLbl.setBackground(new java.awt.Color(204, 204, 0));
        welcomeLbl.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        welcomeLbl.setForeground(new java.awt.Color(255, 255, 255));
        welcomeLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeLbl.setText("Welcome Home, ");

        usernameLbl.setBackground(new java.awt.Color(204, 204, 0));
        usernameLbl.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        usernameLbl.setForeground(new java.awt.Color(255, 255, 255));
        usernameLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameLbl.setText("Ryan Lim");

        logoutBtn.setBackground(new java.awt.Color(153, 153, 255));
        logoutBtn.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        logoutBtn.setForeground(new java.awt.Color(255, 255, 255));
        logoutBtn.setText("Logout");
        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutBtnMouseClicked(evt);
            }
        });
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(snackNowLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                        .addComponent(welcomeLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usernameLbl)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                        .addComponent(logoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90))))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(snackNowLbl)
                    .addGroup(headerPanelLayout.createSequentialGroup()
                        .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(welcomeLbl)
                            .addComponent(usernameLbl))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutBtn)))
                .addContainerGap())
        );

        customizePanel.setBackground(new java.awt.Color(102, 0, 204));

        existingSnackQuantityText.setBackground(new java.awt.Color(102, 102, 102));
        existingSnackQuantityText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingSnackQuantityText.setForeground(new java.awt.Color(255, 255, 255));
        existingSnackQuantityText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingSnackQuantityTextActionPerformed(evt);
            }
        });

        existingSnackPriceLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        existingSnackPriceLbl.setForeground(new java.awt.Color(255, 255, 255));
        existingSnackPriceLbl.setText("Price per Unit (in RM): ");

        existingSnackPriceText.setBackground(new java.awt.Color(102, 102, 102));
        existingSnackPriceText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingSnackPriceText.setForeground(new java.awt.Color(255, 255, 255));
        existingSnackPriceText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingSnackPriceTextActionPerformed(evt);
            }
        });

        customizeLbl.setBackground(new java.awt.Color(176, 0, 81));
        customizeLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        customizeLbl.setForeground(new java.awt.Color(255, 255, 255));
        customizeLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        customizeLbl.setText("Snack Items Customize Area");
        customizeLbl.setOpaque(true);

        existingSnackNameText.setBackground(new java.awt.Color(102, 102, 102));
        existingSnackNameText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingSnackNameText.setForeground(new java.awt.Color(255, 255, 255));
        existingSnackNameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingSnackNameTextActionPerformed(evt);
            }
        });

        existingSnackQuantityLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        existingSnackQuantityLbl.setForeground(new java.awt.Color(255, 255, 255));
        existingSnackQuantityLbl.setText("Quantity: ");

        snackItemsList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Snack 1", "Snack 2", "Snack 3", "Snack 4", "Snack 5", "Snack 6", "Snack 7", "Snack 8", "Snack 9" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(snackItemsList);

        existingSnackNameLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        existingSnackNameLbl.setForeground(new java.awt.Color(255, 255, 255));
        existingSnackNameLbl.setText("Name: ");

        snackItemsLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        snackItemsLbl.setForeground(new java.awt.Color(255, 255, 255));
        snackItemsLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        snackItemsLbl.setText("Snack Items List");

        existingDeleteBtn.setBackground(new java.awt.Color(255, 51, 0));
        existingDeleteBtn.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        existingDeleteBtn.setForeground(new java.awt.Color(255, 255, 255));
        existingDeleteBtn.setText("Delete");
        existingDeleteBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        existingDeleteBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        existingDeleteBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        existingDeleteBtn.setInheritsPopupMenu(true);
        existingDeleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                existingDeleteBtnMouseClicked(evt);
            }
        });
        existingDeleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingDeleteBtnActionPerformed(evt);
            }
        });

        existingConfirmBtn.setBackground(new java.awt.Color(0, 204, 51));
        existingConfirmBtn.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        existingConfirmBtn.setForeground(new java.awt.Color(255, 255, 255));
        existingConfirmBtn.setText("Confirm");
        existingConfirmBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        existingConfirmBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        existingConfirmBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        existingConfirmBtn.setInheritsPopupMenu(true);
        existingConfirmBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                existingConfirmBtnMouseClicked(evt);
            }
        });
        existingConfirmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingConfirmBtnActionPerformed(evt);
            }
        });

        existingResetBtn.setBackground(new java.awt.Color(204, 102, 0));
        existingResetBtn.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        existingResetBtn.setForeground(new java.awt.Color(255, 255, 255));
        existingResetBtn.setText("Reset");
        existingResetBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        existingResetBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        existingResetBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        existingResetBtn.setInheritsPopupMenu(true);
        existingResetBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                existingResetBtnMouseClicked(evt);
            }
        });
        existingResetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingResetBtnActionPerformed(evt);
            }
        });

        newSnackItemsPanel.setBackground(new java.awt.Color(152, 0, 254));

        newSnackItemsLbl.setBackground(new java.awt.Color(176, 0, 81));
        newSnackItemsLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        newSnackItemsLbl.setForeground(new java.awt.Color(255, 255, 255));
        newSnackItemsLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newSnackItemsLbl.setText("New Snack Items Insertion Area");
        newSnackItemsLbl.setOpaque(true);

        newSnackNameLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        newSnackNameLbl.setForeground(new java.awt.Color(255, 255, 255));
        newSnackNameLbl.setText("Name: ");

        newSnackQuantityLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        newSnackQuantityLbl.setForeground(new java.awt.Color(255, 255, 255));
        newSnackQuantityLbl.setText("Quantity: ");

        newSnackPriceLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        newSnackPriceLbl.setForeground(new java.awt.Color(255, 255, 255));
        newSnackPriceLbl.setText("Price per Unit (in RM): ");

        newSnackNameText.setBackground(new java.awt.Color(102, 102, 102));
        newSnackNameText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newSnackNameText.setForeground(new java.awt.Color(255, 255, 255));
        newSnackNameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newSnackNameTextActionPerformed(evt);
            }
        });

        newSnackQuantityText.setBackground(new java.awt.Color(102, 102, 102));
        newSnackQuantityText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newSnackQuantityText.setForeground(new java.awt.Color(255, 255, 255));
        newSnackQuantityText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newSnackQuantityTextActionPerformed(evt);
            }
        });

        newSnackPriceText.setBackground(new java.awt.Color(102, 102, 102));
        newSnackPriceText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newSnackPriceText.setForeground(new java.awt.Color(255, 255, 255));
        newSnackPriceText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newSnackPriceTextActionPerformed(evt);
            }
        });

        newConfirmBtn.setBackground(new java.awt.Color(0, 204, 51));
        newConfirmBtn.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        newConfirmBtn.setForeground(new java.awt.Color(255, 255, 255));
        newConfirmBtn.setText("Confirm");
        newConfirmBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        newConfirmBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        newConfirmBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newConfirmBtn.setInheritsPopupMenu(true);
        newConfirmBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newConfirmBtnMouseClicked(evt);
            }
        });
        newConfirmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newConfirmBtnActionPerformed(evt);
            }
        });

        newResetBtn.setBackground(new java.awt.Color(204, 102, 0));
        newResetBtn.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        newResetBtn.setForeground(new java.awt.Color(255, 255, 255));
        newResetBtn.setText("Reset");
        newResetBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        newResetBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        newResetBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newResetBtn.setInheritsPopupMenu(true);
        newResetBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newResetBtnMouseClicked(evt);
            }
        });
        newResetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newResetBtnActionPerformed(evt);
            }
        });

        attachImageBtn.setBackground(new java.awt.Color(102, 153, 0));
        attachImageBtn.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        attachImageBtn.setForeground(new java.awt.Color(255, 255, 255));
        attachImageBtn.setText("Attach");
        attachImageBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        attachImageBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        attachImageBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        attachImageBtn.setInheritsPopupMenu(true);
        attachImageBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attachImageBtnMouseClicked(evt);
            }
        });
        attachImageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attachImageBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout newSnackItemsPanelLayout = new javax.swing.GroupLayout(newSnackItemsPanel);
        newSnackItemsPanel.setLayout(newSnackItemsPanelLayout);
        newSnackItemsPanelLayout.setHorizontalGroup(
            newSnackItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newSnackItemsPanelLayout.createSequentialGroup()
                .addComponent(newSnackItemsLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newSnackItemsPanelLayout.createSequentialGroup()
                .addGroup(newSnackItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newSnackItemsPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(newSnackImage, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(newSnackItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(newSnackQuantityLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(newSnackNameLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(newSnackPriceLbl, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(31, 31, 31))
                    .addGroup(newSnackItemsPanelLayout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(attachImageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(newSnackItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(newSnackItemsPanelLayout.createSequentialGroup()
                        .addComponent(newResetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(newConfirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(newSnackItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(newSnackItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(newSnackQuantityText, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(newSnackPriceText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(newSnackNameText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38))
        );
        newSnackItemsPanelLayout.setVerticalGroup(
            newSnackItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newSnackItemsPanelLayout.createSequentialGroup()
                .addComponent(newSnackItemsLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(newSnackItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(newSnackItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(newSnackItemsPanelLayout.createSequentialGroup()
                            .addComponent(newSnackNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(19, 19, 19)
                            .addComponent(newSnackQuantityText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(newSnackPriceText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(newSnackItemsPanelLayout.createSequentialGroup()
                            .addComponent(newSnackNameLbl)
                            .addGap(18, 18, 18)
                            .addComponent(newSnackQuantityLbl)
                            .addGap(18, 18, 18)
                            .addComponent(newSnackPriceLbl)))
                    .addComponent(newSnackImage, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(newSnackItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newConfirmBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newResetBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(attachImageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout customizePanelLayout = new javax.swing.GroupLayout(customizePanel);
        customizePanel.setLayout(customizePanelLayout);
        customizePanelLayout.setHorizontalGroup(
            customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(customizeLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(customizePanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customizePanelLayout.createSequentialGroup()
                        .addComponent(newSnackItemsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 18, Short.MAX_VALUE))
                    .addGroup(customizePanelLayout.createSequentialGroup()
                        .addGroup(customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(snackItemsLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customizePanelLayout.createSequentialGroup()
                                .addGroup(customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(existingSnackQuantityLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(existingSnackNameLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(existingSnackPriceLbl, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(69, 69, 69)
                                .addGroup(customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(existingSnackQuantityText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                                        .addComponent(existingSnackPriceText, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(customizePanelLayout.createSequentialGroup()
                                            .addComponent(existingResetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(existingConfirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(existingSnackNameText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customizePanelLayout.createSequentialGroup()
                                .addComponent(existingDeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(118, 118, 118))))))
        );
        customizePanelLayout.setVerticalGroup(
            customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customizePanelLayout.createSequentialGroup()
                .addComponent(customizeLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customizePanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(snackItemsLbl))
                    .addGroup(customizePanelLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(customizePanelLayout.createSequentialGroup()
                                .addGroup(customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(existingSnackNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(existingSnackNameLbl))
                                .addGap(18, 18, 18)
                                .addGroup(customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(existingSnackQuantityLbl)
                                    .addComponent(existingSnackQuantityText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(existingSnackPriceText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(existingSnackPriceLbl))
                                .addGap(18, 18, 18)
                                .addGroup(customizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(existingConfirmBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(existingResetBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(existingDeleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(18, 18, 18)
                .addComponent(newSnackItemsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(headerPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(accountHomePageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(useVendingMachineBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(customizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(customizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accountHomePageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(useVendingMachineBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void useVendingMachineBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useVendingMachineBtnActionPerformed
        // TODO add your handling code here:
        new VendingMachineForm().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_useVendingMachineBtnActionPerformed

    private void accountHomePageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountHomePageBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accountHomePageBtnActionPerformed

    private void logoutBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBtnMouseClicked
        // TODO add your handling code here:
        if (cacheClear()) {
            JOptionPane.showMessageDialog(null, "Logout Successful! Redirecting to Home Page", "Logout: Success", JOptionPane.INFORMATION_MESSAGE);
            new HomePage().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Logout Failed! An unexpected error has occured", "Logout: Failed", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_logoutBtnMouseClicked

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutBtnActionPerformed

    private void existingConfirmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingConfirmBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_existingConfirmBtnActionPerformed

    private void existingSnackNameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingSnackNameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_existingSnackNameTextActionPerformed

    private void existingSnackPriceTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingSnackPriceTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_existingSnackPriceTextActionPerformed

    private void existingSnackQuantityTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingSnackQuantityTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_existingSnackQuantityTextActionPerformed

    private void existingDeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingDeleteBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_existingDeleteBtnActionPerformed

    private void accountHomePageBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountHomePageBtnMouseClicked
        // TODO add your handling code here:
        new AdminHomePage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_accountHomePageBtnMouseClicked

    private void useVendingMachineBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_useVendingMachineBtnMouseClicked
        // TODO add your handling code here:
        new VendingMachineForm().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_useVendingMachineBtnMouseClicked

    private void existingResetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingResetBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_existingResetBtnActionPerformed

    private void newSnackNameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newSnackNameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newSnackNameTextActionPerformed

    private void newSnackQuantityTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newSnackQuantityTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newSnackQuantityTextActionPerformed

    private void newSnackPriceTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newSnackPriceTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newSnackPriceTextActionPerformed

    private void newConfirmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newConfirmBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newConfirmBtnActionPerformed

    private void newResetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newResetBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newResetBtnActionPerformed

    private void newConfirmBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newConfirmBtnMouseClicked
        // TODO add your handling code here:
        deHighlightEmpty();
        saveDir = System.getProperty("user.dir");
        DecimalFormat dc = new DecimalFormat("000000");
        try {
            snackIncrementor();
            sid = dc.format(newSnackID);
            emptyFields();

            if (imageFileName == null) {
                throw new Exception("An image of the snack must be uploaded!");
            }

            if (snackNameValidator(newSnackNameText)) {
                throw new Exception("Snack name has already been taken!");
            }

            String name = newSnackNameText.getText();
            String quantity = newSnackQuantityText.getText();
            String price = newSnackPriceText.getText();

            String newImageFileName = insertImage(imageFileName);

            try {
                FileWriter ld = new FileWriter(saveDir + "Snacks.txt", true);
                PrintWriter ldp = new PrintWriter(ld);
                ldp.println(snackPrefix + sid + ">"
                        + name + ">"
                        + quantity + ">"
                        + price + ">"
                        + newImageFileName + ">"
                        + "false");
                ldp.close();
                JOptionPane.showMessageDialog(null, "Snack Item has been successfully inserted into database", "Item Insertion: Success", JOptionPane.INFORMATION_MESSAGE);
                //snackIncrementor();

                newSnackNameText.setText("");
                newSnackQuantityText.setText("");
                newSnackPriceText.setText("");
                newSnackImage.setIcon(null);
                displaySnackList();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } catch (Exception e) {
            highlightEmpty();
            if (imageFileName == null) {
                JOptionPane.showMessageDialog(null, "Please upload a image of the snack", "Error: No Image Detected", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (snackNameValidator(newSnackNameText)) {
                JOptionPane.showMessageDialog(null, "Please enter a new snack name", "Error: Snack Name Taken", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "Invalid input! Please check your input again", "Item Insertion: Failed", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_newConfirmBtnMouseClicked

    private void attachImageBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attachImageBtnMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();

        BufferedImage img;
        try {
            img = ImageIO.read(new File(filename));
            if (!filename.toLowerCase().endsWith(".jpg") && !filename.toLowerCase().endsWith(".jpeg") && !filename.toLowerCase().endsWith(".png")) {
                JOptionPane.showMessageDialog(null, "Image Extension must be either .jpg, .jpeg or .png", "Error: Unsupported Image Extension", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Image dimg = img.getScaledInstance(newSnackImage.getWidth(), newSnackImage.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            newSnackImage.setIcon(imageIcon);
            imageFileName = filename;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please upload an image file of the snack item", "Error: Not Image File", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_attachImageBtnMouseClicked

    private void attachImageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachImageBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_attachImageBtnActionPerformed

    private void newResetBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newResetBtnMouseClicked
        // TODO add your handling code here:
        newSnackNameText.setText("");
        newSnackQuantityText.setText("");
        newSnackPriceText.setText("");
        newSnackImage.setIcon(null);
    }//GEN-LAST:event_newResetBtnMouseClicked

    private void existingConfirmBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_existingConfirmBtnMouseClicked

        saveDir = System.getProperty("user.dir") + "/SnacksInfo/";

        try {
            if ("".equals(existingSnackNameText.getText())) {
                throw new Exception("Cannot have empty name");

            } else if ("".equals(existingSnackPriceText.getText())) {
                throw new Exception("Cannot have empty price");

            } else if ("".equals(existingSnackQuantityText.getText())) {
                throw new Exception("Cannot have empty quantity");
            }
            
            InputValidation validate = new InputValidation();
            if(!validate.snackNameValidation((JTextField) existingSnackNameText) || !validate.snackPriceValidation((JTextField) existingSnackPriceText) || !validate.snackQuantityValidation((JTextField) existingSnackQuantityText)){
                return;
            }
            
            String name = existingSnackNameText.getText();
            String quantity = existingSnackQuantityText.getText();
            String price = existingSnackPriceText.getText();
            
            String[] matchedID = null;

            try {
                if (snackNameValidator(existingSnackNameText)) {
                    throw new Exception("Snack name has been taken!");
                }

                File originalSnacks = new File(saveDir + "Snacks.txt");
                File tmpSnacks = new File(saveDir + "tmpSnacks.txt");
                PrintWriter pw = new PrintWriter(new FileWriter(tmpSnacks));
                Scanner inputFile = new Scanner(originalSnacks);

                while (inputFile.hasNext()) {
                    String entry = inputFile.nextLine();
                    matchedID = entry.split(">");

                    if (!(matchedID[1].equals(selectedSnackName))) {
                        name = matchedID[1];
                        quantity = matchedID[2];
                        price = matchedID[3];
                    } else {
                        name = existingSnackNameText.getText();
                        quantity = existingSnackQuantityText.getText();
                        price = existingSnackPriceText.getText();
                    }

                    String snackID = matchedID[0];
                    String snackImage = matchedID[4];
                    String snackStatus = matchedID[5];

                    pw.println(snackID + ">"
                            + name + ">"
                            + quantity + ">"
                            + price + ">"
                            + snackImage + ">"
                            + snackStatus);
                }
                inputFile.close();
                pw.close();

                selectedSnackName = name;

                existingSnackNameText.setText("");
                existingSnackPriceText.setText("");
                existingSnackQuantityText.setText("");

                if (!originalSnacks.delete()) {
                    JOptionPane.showMessageDialog(null, "Could not delete old file", "Error in deleting file", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!tmpSnacks.renameTo(originalSnacks)) {
                    JOptionPane.showMessageDialog(null, "Could not rename new file", "Error in renaming file", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(null, "Details have been updated successfully", "Update Success", JOptionPane.INFORMATION_MESSAGE);
                displaySnackList();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                if (snackNameValidator(existingSnackNameText)) {
                    JOptionPane.showMessageDialog(null, "Please enter a new snack name", "Error: Snack Name Used", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please ensure there are no empty inputs.", "Empty input detected!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_existingConfirmBtnMouseClicked

    private void existingResetBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_existingResetBtnMouseClicked
        // TODO add your handling code here:
        saveDir = System.getProperty("user.dir") + "/SnacksInfo/";
        try {
            File snacks = new File(saveDir + "Snacks.txt");
            if (!snacks.exists()) {
                snacks.createNewFile();
            }

            Scanner inputFile = new Scanner(snacks);
            String[] matchedID = null;
            while (inputFile.hasNext()) {
                String entry = inputFile.nextLine();
                matchedID = entry.split(">");

                if (matchedID[1].equals(selectedSnackName)) {
                    existingSnackNameText.setText(selectedSnackName);
                    existingSnackPriceText.setText(matchedID[3]);
                    existingSnackQuantityText.setText(matchedID[2]);
                    break;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_existingResetBtnMouseClicked

    private void existingDeleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_existingDeleteBtnMouseClicked
        // TODO add your handling code here:
        int selection = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this snack item?", "Delete Snack Item Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (selection == JOptionPane.YES_OPTION) {
            String saveDir = System.getProperty("user.dir") + "/SnacksInfo/";
            File snacks = new File(saveDir + "Snacks.txt");
            File tmpSnacks = new File(saveDir + "tmpSnacks.txt");
            int size = tmpSnackNames.size();
            String name = existingSnackNameText.getText();
            String[] matchedID = null;
            int count = 0;
            String snackList = "";

            try {
                Scanner input = new Scanner(snacks);
                while (input.hasNext()) {
                    count++;
                    String entry = input.nextLine();
                    matchedID = entry.split(">");

                    if (!matchedID[1].equals(name)) {
                        if (count == size) {
                            snackList += entry;
                        } else {
                            snackList += entry + "\n";
                        }
                    }
                }
                input.close();

                PrintWriter pw = new PrintWriter(new FileWriter(tmpSnacks));
                pw.println(snackList);
                pw.close();

                if (!snacks.delete()) {
                    JOptionPane.showMessageDialog(null, "Unable to delete old snack list", "Error: Delete Unsuccessful", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!tmpSnacks.renameTo(snacks)) {
                    JOptionPane.showMessageDialog(null, "Unable to rename new snack list", "Error: Rename Unsuccessful", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(null, "The selected snack item has been deleted successfully", "Success: Delete Successful", JOptionPane.INFORMATION_MESSAGE);
                displaySnackList();

                existingSnackNameText.setText("");
                existingSnackPriceText.setText("");
                existingSnackQuantityText.setText("");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }
    }//GEN-LAST:event_existingDeleteBtnMouseClicked

    private void headerPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerPanelMousePressed
        // TODO add your handling code here:
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_headerPanelMousePressed

    private void headerPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerPanelMouseDragged
        // TODO add your handling code here:
        int coordX = evt.getXOnScreen();
        int coordY = evt.getYOnScreen();

        this.setLocation(coordX - mouseX, coordY - mouseY);
    }//GEN-LAST:event_headerPanelMouseDragged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminUpdateVendingMachineForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminUpdateVendingMachineForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminUpdateVendingMachineForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminUpdateVendingMachineForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUpdateVendingMachineForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accountHomePageBtn;
    private javax.swing.JButton attachImageBtn;
    private javax.swing.JLabel copyrightLbl;
    private javax.swing.JLabel customizeLbl;
    private javax.swing.JPanel customizePanel;
    private javax.swing.JLabel enquiryLbl;
    private javax.swing.JButton existingConfirmBtn;
    private javax.swing.JButton existingDeleteBtn;
    private javax.swing.JButton existingResetBtn;
    private javax.swing.JLabel existingSnackNameLbl;
    private javax.swing.JTextField existingSnackNameText;
    private javax.swing.JLabel existingSnackPriceLbl;
    private javax.swing.JTextField existingSnackPriceText;
    private javax.swing.JLabel existingSnackQuantityLbl;
    private javax.swing.JTextField existingSnackQuantityText;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTree jTree1;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton newConfirmBtn;
    private javax.swing.JButton newResetBtn;
    private javax.swing.JLabel newSnackImage;
    private javax.swing.JLabel newSnackItemsLbl;
    private javax.swing.JPanel newSnackItemsPanel;
    private javax.swing.JLabel newSnackNameLbl;
    private javax.swing.JTextField newSnackNameText;
    private javax.swing.JLabel newSnackPriceLbl;
    private javax.swing.JTextField newSnackPriceText;
    private javax.swing.JLabel newSnackQuantityLbl;
    private javax.swing.JTextField newSnackQuantityText;
    private javax.swing.JLabel snackItemsLbl;
    private javax.swing.JList<String> snackItemsList;
    private javax.swing.JLabel snackNowLbl;
    private javax.swing.JButton useVendingMachineBtn;
    private javax.swing.JLabel usernameLbl;
    private javax.swing.JLabel welcomeLbl;
    // End of variables declaration//GEN-END:variables
}
