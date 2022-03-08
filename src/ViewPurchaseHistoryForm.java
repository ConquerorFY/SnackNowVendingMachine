/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class ViewPurchaseHistoryForm extends javax.swing.JFrame {

    /**
     * Creates new form ViewPurchaseHistoryForm
     */
    private int mouseX, mouseY;

    public ViewPurchaseHistoryForm() {
        initComponents();
        this.setLocationRelativeTo(null);

        String firstSaveDir = System.getProperty("user.dir") + "/StaffInfo/";
        File cache = new File(firstSaveDir + "Cache.txt");
        String[] matchedID = null;
        try {
            Scanner input = new Scanner(cache);
            while (input.hasNext()) {
                String entry = input.nextLine();
                matchedID = entry.split(":");
                usernameLbl.setText(matchedID[1]);
            }
            input.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        String saveDir = System.getProperty("user.dir") + "/SnacksInfo/";
        File history = new File(saveDir + "PurchaseHistory.txt");
        matchedID = null;
        int size = 0;
        int index = 0;
        String[] historyid = null;
        if (!history.exists()) {
            JOptionPane.showMessageDialog(null, "No purchases has been created yet!", "Error: No Purchase History Detected", JOptionPane.WARNING_MESSAGE);
        }
        try {
            Scanner sizeInput = new Scanner(history);
            while (sizeInput.hasNext()) {
                size++;
                sizeInput.nextLine();
            }
            sizeInput.close();

            historyid = new String[size];
            Scanner idInput = new Scanner(history);
            while (idInput.hasNext()) {
                String entry = idInput.nextLine();
                matchedID = entry.split(":");
                historyid[index] = matchedID[0].replace("PH", "Purchase History ID #");
                historySelectionComboBox.addItem(historyid[index]);
                index++;
            }
            idInput.close();

            historySelectionComboBox.addItemListener(
                    new ItemListener() {
                public void itemStateChanged(ItemEvent event) {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        DefaultTableModel previousModel = (DefaultTableModel) purchaseHistoryTable.getModel();
                        previousModel.setRowCount(0);
                        
                        String id = String.valueOf(historySelectionComboBox.getSelectedItem()).replace("Purchase History ID #", "");

                        File history = new File(saveDir + "PurchaseHistory.txt");
                        String[] matchedID = null;
                        try {
                            Scanner input = new Scanner(history);
                            while (input.hasNext()) {
                                String entry = input.nextLine();
                                matchedID = entry.split(":");
                                if (id.equals(matchedID[0].replace("PH", ""))) {
                                    totalAmountPriceLbl.setText(matchedID[matchedID.length - 3]);
                                    amountPaidPriceLbl.setText(matchedID[matchedID.length - 2]);
                                    changePriceLbl.setText(matchedID[matchedID.length - 1]);
                                    purchaseHistoryLbl.setText(matchedID[1] + "'s Purchase History of Order #" + id);
                                    
                                    Object snackData[][] = new Object[(matchedID.length - 5) / 4][5];
                                    int i = 2;
                                    int index = 0;
                                    int no = 1;
                                    while(i < matchedID.length - 3){
                                        snackData[index][0] = no;
                                        snackData[index][1] = matchedID[i];
                                        snackData[index][2] = matchedID[i + 1];
                                        snackData[index][3] = matchedID[i + 2];
                                        snackData[index][4] = matchedID[i + 3];  
                                        
                                        no++;
                                        index++;
                                        i+=4;
                                    }
                                    
                                    DefaultTableModel model = (DefaultTableModel) purchaseHistoryTable.getModel();
                                    for(int j = 0; j < index; j++){
                                        model.addRow(snackData[j]);
                                    }
                                    break;
                                }

                            }
                            
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }

                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private boolean cacheClear() {
        try {
            String saveDir = System.getProperty("user.dir") + "/StaffInfo/";
            File cache = new File(saveDir + "Cache.txt");
            if (cache.exists()) {
                cache.delete();
            }
            if (!cache.exists()) {
                return true;
            }
            return false;
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

        bodyPanel = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        copyrightLbl = new javax.swing.JLabel();
        enquiryLbl = new javax.swing.JLabel();
        headPanel = new javax.swing.JPanel();
        snackNowLbl = new javax.swing.JLabel();
        welcomeLbl = new javax.swing.JLabel();
        usernameLbl = new javax.swing.JLabel();
        logoutBtn = new javax.swing.JButton();
        accountHomePageBtn = new javax.swing.JButton();
        purchaseHistoryLbl = new javax.swing.JLabel();
        vendingMachineBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        purchaseHistoryTable = new javax.swing.JTable();
        historySelectionLbl = new javax.swing.JLabel();
        historySelectionComboBox = new javax.swing.JComboBox<>();
        totalAmountLbl = new javax.swing.JLabel();
        totalAmountPriceLbl = new javax.swing.JLabel();
        amountPaidLbl = new javax.swing.JLabel();
        amountPaidPriceLbl = new javax.swing.JLabel();
        changeLbl = new javax.swing.JLabel();
        changePriceLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        bodyPanel.setBackground(new java.awt.Color(246, 131, 112));

        jLabel27.setBackground(new java.awt.Color(204, 204, 0));
        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));

        jLabel33.setBackground(new java.awt.Color(204, 204, 0));
        jLabel33.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel15.setBackground(new java.awt.Color(92, 204, 206));
        jPanel15.setLayout(new java.awt.GridLayout());

        copyrightLbl.setBackground(new java.awt.Color(0, 0, 0));
        copyrightLbl.setFont(new java.awt.Font("Yu Gothic UI Semilight", 3, 14)); // NOI18N
        copyrightLbl.setForeground(new java.awt.Color(255, 255, 255));
        copyrightLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        copyrightLbl.setText("Copyright 2020 Ryan Lim Fang Yung TP055343 ");
        copyrightLbl.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        copyrightLbl.setAlignmentY(0.0F);
        copyrightLbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        copyrightLbl.setPreferredSize(new java.awt.Dimension(365, 25));
        copyrightLbl.setRequestFocusEnabled(false);
        copyrightLbl.setVerifyInputWhenFocusTarget(false);
        jPanel15.add(copyrightLbl);

        enquiryLbl.setBackground(new java.awt.Color(0, 0, 0));
        enquiryLbl.setFont(new java.awt.Font("Yu Gothic UI Semilight", 3, 14)); // NOI18N
        enquiryLbl.setForeground(new java.awt.Color(255, 255, 255));
        enquiryLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        enquiryLbl.setText("For Business Enquires: tp055343@mail.apu.edu.my");
        enquiryLbl.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        enquiryLbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel15.add(enquiryLbl);

        headPanel.setBackground(new java.awt.Color(176, 0, 81));
        headPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headPanelMouseDragged(evt);
            }
        });
        headPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headPanelMousePressed(evt);
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

        javax.swing.GroupLayout headPanelLayout = new javax.swing.GroupLayout(headPanel);
        headPanel.setLayout(headPanelLayout);
        headPanelLayout.setHorizontalGroup(
            headPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headPanelLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(snackNowLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(headPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headPanelLayout.createSequentialGroup()
                        .addComponent(welcomeLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usernameLbl)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headPanelLayout.createSequentialGroup()
                        .addComponent(logoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90))))
        );
        headPanelLayout.setVerticalGroup(
            headPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(snackNowLbl)
                    .addGroup(headPanelLayout.createSequentialGroup()
                        .addGroup(headPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(welcomeLbl)
                            .addComponent(usernameLbl))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutBtn)))
                .addContainerGap())
        );

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

        purchaseHistoryLbl.setBackground(new java.awt.Color(204, 204, 0));
        purchaseHistoryLbl.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        purchaseHistoryLbl.setForeground(new java.awt.Color(255, 255, 255));
        purchaseHistoryLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        purchaseHistoryLbl.setText("Guests's Purchase History #00");

        vendingMachineBtn.setBackground(new java.awt.Color(102, 102, 255));
        vendingMachineBtn.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        vendingMachineBtn.setForeground(new java.awt.Color(255, 255, 255));
        vendingMachineBtn.setText("Use Vending Machine");
        vendingMachineBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        vendingMachineBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        vendingMachineBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        vendingMachineBtn.setInheritsPopupMenu(true);
        vendingMachineBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendingMachineBtnActionPerformed(evt);
            }
        });

        purchaseHistoryTable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N
        purchaseHistoryTable.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        purchaseHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Quantity", "Price per Unit (in RM)", "Total Snack Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        purchaseHistoryTable.setFillsViewportHeight(true);
        purchaseHistoryTable.setRowHeight(30);
        purchaseHistoryTable.setRowMargin(5);
        purchaseHistoryTable.setShowGrid(true);
        jScrollPane1.setViewportView(purchaseHistoryTable);

        historySelectionLbl.setBackground(new java.awt.Color(204, 204, 0));
        historySelectionLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        historySelectionLbl.setForeground(new java.awt.Color(255, 255, 255));
        historySelectionLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        historySelectionLbl.setText("Please select the desired purchase history to view: ");

        totalAmountLbl.setBackground(new java.awt.Color(204, 204, 0));
        totalAmountLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        totalAmountLbl.setForeground(new java.awt.Color(255, 255, 255));
        totalAmountLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalAmountLbl.setText("Total Amount: ");

        totalAmountPriceLbl.setBackground(new java.awt.Color(204, 204, 0));
        totalAmountPriceLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        totalAmountPriceLbl.setForeground(new java.awt.Color(255, 255, 255));
        totalAmountPriceLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalAmountPriceLbl.setText("RM0.00");

        amountPaidLbl.setBackground(new java.awt.Color(204, 204, 0));
        amountPaidLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        amountPaidLbl.setForeground(new java.awt.Color(255, 255, 255));
        amountPaidLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        amountPaidLbl.setText("Amount Paid:");

        amountPaidPriceLbl.setBackground(new java.awt.Color(204, 204, 0));
        amountPaidPriceLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        amountPaidPriceLbl.setForeground(new java.awt.Color(255, 255, 255));
        amountPaidPriceLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        amountPaidPriceLbl.setText("RM0.00");

        changeLbl.setBackground(new java.awt.Color(204, 204, 0));
        changeLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        changeLbl.setForeground(new java.awt.Color(255, 255, 255));
        changeLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        changeLbl.setText("Change:");

        changePriceLbl.setBackground(new java.awt.Color(204, 204, 0));
        changePriceLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        changePriceLbl.setForeground(new java.awt.Color(255, 255, 255));
        changePriceLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        changePriceLbl.setText("RM0.00");

        javax.swing.GroupLayout bodyPanelLayout = new javax.swing.GroupLayout(bodyPanel);
        bodyPanel.setLayout(bodyPanelLayout);
        bodyPanelLayout.setHorizontalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addContainerGap())
            .addComponent(headPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(purchaseHistoryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(197, 197, 197))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                        .addContainerGap(61, Short.MAX_VALUE)
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(bodyPanelLayout.createSequentialGroup()
                                        .addGap(65, 65, 65)
                                        .addComponent(historySelectionLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15)
                                        .addComponent(historySelectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(bodyPanelLayout.createSequentialGroup()
                                        .addComponent(accountHomePageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(73, 73, 73)
                                        .addComponent(vendingMachineBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(61, 61, 61)
                                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                                .addComponent(totalAmountLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(totalAmountPriceLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                                .addComponent(amountPaidLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(amountPaidPriceLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                                .addComponent(changeLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(changePriceLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(7, 7, 7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 772, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        bodyPanelLayout.setVerticalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addComponent(headPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addComponent(jLabel33)
                        .addGap(313, 313, 313)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(historySelectionLbl)
                            .addComponent(historySelectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(purchaseHistoryLbl)
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(totalAmountLbl)
                                    .addComponent(totalAmountPriceLbl))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(amountPaidLbl)
                                    .addComponent(amountPaidPriceLbl))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(changeLbl)
                                    .addComponent(changePriceLbl)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(vendingMachineBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(accountHomePageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)))
                        .addGap(77, 77, 77)))
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 920, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(bodyPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 789, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(bodyPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void headPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headPanelMouseDragged
        // TODO add your handling code here:
        int coordX = evt.getXOnScreen();
        int coordY = evt.getYOnScreen();

        this.setLocation(coordX - mouseX, coordY - mouseY);
    }//GEN-LAST:event_headPanelMouseDragged

    private void headPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headPanelMousePressed
        // TODO add your handling code here:
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_headPanelMousePressed

    private void vendingMachineBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendingMachineBtnActionPerformed
        // TODO add your handling code here:
        new VendingMachineForm().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_vendingMachineBtnActionPerformed

    private void accountHomePageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountHomePageBtnActionPerformed
        // TODO add your handling code here:
        new AdminHomePage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_accountHomePageBtnActionPerformed

    private void accountHomePageBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountHomePageBtnMouseClicked
        // TODO add your handling code here:
        new AdminHomePage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_accountHomePageBtnMouseClicked

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
            java.util.logging.Logger.getLogger(ViewPurchaseHistoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewPurchaseHistoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewPurchaseHistoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewPurchaseHistoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewPurchaseHistoryForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accountHomePageBtn;
    private javax.swing.JLabel amountPaidLbl;
    private javax.swing.JLabel amountPaidPriceLbl;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JLabel changeLbl;
    private javax.swing.JLabel changePriceLbl;
    private javax.swing.JLabel copyrightLbl;
    private javax.swing.JLabel enquiryLbl;
    private javax.swing.JPanel headPanel;
    private javax.swing.JComboBox<String> historySelectionComboBox;
    private javax.swing.JLabel historySelectionLbl;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JLabel purchaseHistoryLbl;
    private javax.swing.JTable purchaseHistoryTable;
    private javax.swing.JLabel snackNowLbl;
    private javax.swing.JLabel totalAmountLbl;
    private javax.swing.JLabel totalAmountPriceLbl;
    private javax.swing.JLabel usernameLbl;
    private javax.swing.JButton vendingMachineBtn;
    private javax.swing.JLabel welcomeLbl;
    // End of variables declaration//GEN-END:variables
}
