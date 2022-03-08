/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class VendingMachineForm extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form VendingMachineForm
     */
    private int size;

    private int mouseX;
    private int mouseY;
    
    private String originalTotalAmount;

    public VendingMachineForm() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(246, 131, 112));
        this.scrollPanel.setLayout(new GridLayout(0, 3, 100, 100));
        this.scrollPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        try {
            String saveDir = System.getProperty("user.dir") + "/SnacksInfo/";
            File snacks = new File(saveDir + "Snacks.txt");

            Scanner inputFile = new Scanner(snacks);
            String[] matchedID = null;
            ArrayList<Component> items = new ArrayList<Component>();

            while (inputFile.hasNext()) {
                String entry = inputFile.nextLine();
                matchedID = entry.split(">");
                int id = Integer.parseInt(matchedID[0].replace("Sn", ""));
                String name = matchedID[1];
                String picLocation = matchedID[4];
                String quantity = matchedID[2];
                String price = matchedID[3];

                JPanel panel = new JPanel();
                JLabel nameLbl = new JLabel(name);
                JLabel picLbl = new JLabel();
                String[] options = {"0", "1", "2", "3"};
                JLabel quantityLbl = new JLabel("Quantity: ");
                JLabel priceLbl = new JLabel("Price: ");
                JLabel showPriceLbl = new JLabel("RM0.00");
                JComboBox comboBox = new JComboBox(options);
                JButton chooseBtn = new JButton("Choose Item");
                JLabel outOfStock = new JLabel("Out of Stock");

                BufferedImage img;
                try {
                    img = ImageIO.read(new File(picLocation));
                    Image dimg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(dimg);
                    picLbl.setIcon(imageIcon);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                nameLbl.setForeground(new Color(255, 255, 255));
                nameLbl.setFont(new Font("Times New Roman", Font.BOLD, 15));

                quantityLbl.setForeground(new Color(255, 255, 255));
                quantityLbl.setFont(new Font("Times New Roman", Font.BOLD, 15));

                priceLbl.setForeground(new Color(255, 255, 255));
                priceLbl.setFont(new Font("Times New Roman", Font.BOLD, 15));

                showPriceLbl.setForeground(new Color(255, 255, 255));
                showPriceLbl.setFont(new Font("Times New Roman", Font.BOLD, 15));

                outOfStock.setForeground(new Color(255, 255, 255));
                outOfStock.setFont(new Font("Times New Roman", Font.BOLD, 15));

                comboBox.setForeground(new Color(255, 255, 255));
                comboBox.setFont(new Font("Times New Roman", Font.BOLD, 15));

                chooseBtn.setBackground(Color.red);
                chooseBtn.setFont(new Font("Times New Roman", Font.BOLD, 15));
                chooseBtn.setForeground(new Color(255, 255, 255));

                panel.setLayout(new GridBagLayout());
                panel.setBackground(new Color(255, 102, 102));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 5, 5, 5);

                gbc.gridwidth = 5;
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(picLbl, gbc);

                gbc.gridwidth = 5;
                gbc.gridx = 1;
                gbc.gridy = 1;
                nameLbl.setHorizontalAlignment(JLabel.CENTER);
                panel.add(nameLbl, gbc);

                gbc.gridwidth = 2;
                gbc.gridx = 0;
                gbc.gridy = 2;
                panel.add(quantityLbl, gbc);

                gbc.gridwidth = 2;
                gbc.gridx = 2;
                gbc.gridy = 2;
                panel.add(comboBox, gbc);

                gbc.gridwidth = 2;
                gbc.gridx = 0;
                gbc.gridy = 3;
                panel.add(priceLbl, gbc);

                gbc.gridwidth = 2;
                gbc.gridx = 2;
                gbc.gridy = 3;
                panel.add(showPriceLbl, gbc);

                if (Integer.parseInt(quantity) > 3) {
                    gbc.gridwidth = 2;
                    gbc.gridx = 2;
                    gbc.gridy = 4;
                    panel.add(chooseBtn, gbc);
                } else {
                    gbc.gridwidth = 2;
                    gbc.gridx = 2;
                    gbc.gridy = 4;
                    panel.add(outOfStock, gbc);
                }

                items.add(panel);

                DefaultTableModel model = (DefaultTableModel) paymentTable.getModel();
                model.insertRow(id, new Object[]{name, 0, price, 0});

                comboBox.addItemListener(
                        new ItemListener() {
                    public void itemStateChanged(ItemEvent event) {
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            showPriceLbl.setText("RM" + String.format("%.02f", (Integer.parseInt(options[comboBox.getSelectedIndex()]) * Float.parseFloat(price))));
                        }
                    }
                });

                chooseBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String snackItemTotalPrice = showPriceLbl.getText().replace("RM", "");
                        DefaultTableModel model = (DefaultTableModel) paymentTable.getModel();
                        Float oldSnackItemTotalPrice = Float.parseFloat(model.getValueAt(id, 3).toString());
                        model.setValueAt(options[comboBox.getSelectedIndex()], id, 1);
                        model.setValueAt(snackItemTotalPrice, id, 3);

                        Float newTotalPrice = Float.parseFloat(totalAmountLbl.getText().replace("RM", "")) - oldSnackItemTotalPrice + Float.parseFloat(snackItemTotalPrice);
                        totalAmountLbl.setText("RM" + String.format("%.02f", newTotalPrice));
                        originalTotalAmount = totalAmountLbl.getText();
                    }
                });

            }
            inputFile.close();

            for (int i = 0; i < items.size(); i++) {
                scrollPanel.add(items.get(i));
            }

            size = items.size();

            resetBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    DefaultTableModel model = (DefaultTableModel) paymentTable.getModel();
                    for (int s = 0; s < items.size(); s++) {
                        model.setValueAt(0, s, 1);
                        model.setValueAt(0, s, 3);

                        JPanel panel = (JPanel) items.get(s);
                        JComboBox combo = (JComboBox) panel.getComponent(3);
                        combo.setSelectedIndex(0);
                    }

                    totalAmountLbl.setText("RM0.00");
                }
            });

            TableColumnModel columnModel = paymentTable.getColumnModel();
            for (int column = 0; column < paymentTable.getColumnCount(); column++) {
                int width = 15; //Min width
                for (int row = 0; row < paymentTable.getRowCount(); row++) {
                    TableCellRenderer renderer = paymentTable.getCellRenderer(row, column);
                    Component comp = paymentTable.prepareRenderer(renderer, row, column);
                    width = Math.max(comp.getPreferredSize().width + 1, width);
                }
                if (width > 300) {
                    width = 300;
                }
                columnModel.getColumn(column).setPreferredWidth(width);
            }

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            ((DefaultTableCellRenderer) paymentTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
            for (int column = 0; column < paymentTable.getColumnCount(); column++) {
                columnModel.getColumn(column).setCellRenderer(centerRenderer);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        Thread t = new Thread(this);
        t.start();

    }

    public void run() {
        LocalDate date = LocalDate.now();
        try {
            while (true) {
                Calendar cal = Calendar.getInstance();
                Date time = cal.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                int hours = cal.get(Calendar.HOUR_OF_DAY);
                String indicator;
                if (hours > 12) {
                    hours -= 12;
                    indicator = "PM";
                } else {
                    indicator = "AM";
                }

                String timeString = date + " " + formatter.format(time) + " " + indicator;
                dateTimeLbl.setText(timeString);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void addPurchaseHistory(String totalPrice, String paid, String change) {
        String saveDir = System.getProperty("user.dir") + "/SnacksInfo/";
        File historytxt = new File(saveDir + "PurchaseHistory.txt");
        saveDir = System.getProperty("user.dir") + "/StaffInfo/";
        File cache = new File(saveDir + "Cache.txt");

        String historyID;
        String consumer = "Guest";
        String name;
        String quantity;
        String unitPrice;
        String price;

        String[] names = new String[size];
        String[] quantities = new String[size];
        String[] unitPrices = new String[size];
        String[] prices = new String[size];

        String orderList = "";

        try {
            if (!historytxt.exists()) {
                historytxt.createNewFile();
            }
            PrintWriter pw = new PrintWriter(new FileWriter(historytxt, true));
            historyID = "PH" + historyIncrementor();

            if (cache.exists()) {
                Scanner input = new Scanner(cache);
                while (input.hasNext()) {
                    String entry = input.nextLine();
                    consumer = entry.split(":")[1];
                }
                input.close();
            }

            DefaultTableModel model = (DefaultTableModel) paymentTable.getModel();
            for (int i = 0; i < size; i++) {
                name = model.getValueAt(i, 0).toString();
                quantity = model.getValueAt(i, 1).toString();
                unitPrice = model.getValueAt(i, 2).toString();
                price = model.getValueAt(i, 3).toString();

                names[i] = name;
                quantities[i] = quantity;
                unitPrices[i] = unitPrice;
                prices[i] = price;
            }

            for (int i = 0; i < size; i++) {
                orderList += names[i] + ":";
                orderList += quantities[i] + ":";
                orderList += unitPrices[i] + ":";
                orderList += prices[i] + ":";
            }

            pw.println(historyID + ":"
                    + consumer + ":"
                    + orderList
                    + totalPrice + ":"
                    + paid + ":"
                    + change);
            pw.close();

            JOptionPane.showMessageDialog(null, "Purchase history has been inserted into database!", "Success: Insert Purchase History", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private int historyIncrementor() {
        String[] matchedID = null;
        boolean hasRecord = false;
        int newHistoryID = 0;
        try {
            String saveDir = System.getProperty("user.dir") + "/SnacksInfo/";
            File historytxt = new File(saveDir + "PurchaseHistory.txt");
            if (!historytxt.exists()) {
                historytxt.createNewFile();
            }
            Scanner inputFile;
            try {
                inputFile = new Scanner(historytxt);
                while (inputFile.hasNext()) {
                    String entry = inputFile.nextLine();
                    matchedID = entry.split(":");
                    matchedID[0] = matchedID[0].replace("PH", "");
                    hasRecord = true;
                }
                inputFile.close();
                if (!hasRecord) {
                    newHistoryID = 1;
                } else {
                    newHistoryID = Integer.parseInt(matchedID[0]) + 1;
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            JOptionPane.showMessageDialog(null, "Invalid input! Purchase History ID can only consist of numbers", "Invalid input type!", JOptionPane.ERROR_MESSAGE);
        }
        return newHistoryID;
    }

    private void updateSnackTxt() {
        String saveDir = System.getProperty("user.dir") + "/SnacksInfo/";
        File snacks = new File(saveDir + "Snacks.txt");
        File tmp = new File(saveDir + "tmpSnacks.txt");
        DefaultTableModel model = (DefaultTableModel) paymentTable.getModel();
        Scanner input;
        String[] matchedID = null;
        String snackDetails = "";

        try {
            Scanner sizeInput = new Scanner(snacks);
            int size = 0;
            while (sizeInput.hasNext()) {
                sizeInput.nextLine();
                size++;
            }
            sizeInput.close();

            input = new Scanner(snacks);
            int i = 0;
            int count = 0;
            while (input.hasNext()) {
                String entry = input.nextLine();
                matchedID = entry.split(">");

                int usedQuantity = Integer.parseInt(model.getValueAt(i, 1).toString());
                int newQuantity = Integer.parseInt(matchedID[2]) - usedQuantity;
                matchedID[2] = String.valueOf(newQuantity);

                String matchedID_String = String.join(">", matchedID);
                i++;
                count++;
                if (count >= size) {
                    snackDetails += matchedID_String;
                } else {
                    snackDetails += matchedID_String + "\n";
                }
            }
            input.close();

            PrintWriter pw = new PrintWriter(new FileWriter(tmp));
            pw.println(snackDetails);
            pw.close();

            if (!snacks.delete()) {
                JOptionPane.showMessageDialog(null, "Cannot delete old snacks file", "Error: Deletion Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!tmp.renameTo(snacks)) {
                JOptionPane.showMessageDialog(null, "Cannot rename new snacks file", "Error: Rename Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

        jPanel15 = new javax.swing.JPanel();
        copyrightLbl = new javax.swing.JLabel();
        enquiryLbl = new javax.swing.JLabel();
        headerPanel = new javax.swing.JPanel();
        snackNowLbl = new javax.swing.JLabel();
        backBtn = new javax.swing.JButton();
        dateTimeLbl = new javax.swing.JLabel();
        paymentPanel = new javax.swing.JPanel();
        paymentHeaderLbl = new javax.swing.JLabel();
        payBtn = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        paymentTable = new javax.swing.JTable();
        totalLbl = new javax.swing.JLabel();
        totalAmountLbl = new javax.swing.JLabel();
        notesLbl = new javax.swing.JLabel();
        centesLbl = new javax.swing.JLabel();
        notesComboBox = new javax.swing.JComboBox<>();
        centsComboBox = new javax.swing.JComboBox<>();
        receiptCheckBox = new javax.swing.JCheckBox();
        resetBtn = new javax.swing.JToggleButton();
        changeLbl = new javax.swing.JLabel();
        changeAmountLbl = new javax.swing.JLabel();
        vendingMachineScrollPanel = new javax.swing.JScrollPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        scrollPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel15.setBackground(new java.awt.Color(92, 204, 206));
        jPanel15.setLayout(new java.awt.GridLayout(1, 0));

        copyrightLbl.setBackground(new java.awt.Color(0, 0, 0));
        copyrightLbl.setFont(new java.awt.Font("Yu Gothic UI Semilight", 3, 14)); // NOI18N
        copyrightLbl.setForeground(new java.awt.Color(255, 255, 255));
        copyrightLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        copyrightLbl.setText("Copyright 2020 Ryan Lim Fang Yung TP055343 ");
        jPanel15.add(copyrightLbl);

        enquiryLbl.setBackground(new java.awt.Color(0, 0, 0));
        enquiryLbl.setFont(new java.awt.Font("Yu Gothic UI Semilight", 3, 14)); // NOI18N
        enquiryLbl.setForeground(new java.awt.Color(255, 255, 255));
        enquiryLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        enquiryLbl.setText("For Business Enquires: tp055343@mail.apu.edu.my");
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

        backBtn.setBackground(new java.awt.Color(255, 102, 0));
        backBtn.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        backBtn.setForeground(new java.awt.Color(255, 255, 255));
        backBtn.setText("Back ");
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backBtnMouseClicked(evt);
            }
        });
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        dateTimeLbl.setBackground(new java.awt.Color(204, 204, 0));
        dateTimeLbl.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        dateTimeLbl.setForeground(new java.awt.Color(255, 255, 255));
        dateTimeLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateTimeLbl.setText("2021-03-21 03:30 A.M.");
        dateTimeLbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(snackNowLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dateTimeLbl)
                .addGap(306, 306, 306)
                .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(snackNowLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(headerPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dateTimeLbl)
                            .addComponent(backBtn))))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        paymentPanel.setBackground(new java.awt.Color(103, 124, 138));
        paymentPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        paymentPanel.setForeground(new java.awt.Color(204, 204, 204));

        paymentHeaderLbl.setBackground(new java.awt.Color(255, 51, 0));
        paymentHeaderLbl.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        paymentHeaderLbl.setForeground(new java.awt.Color(255, 255, 255));
        paymentHeaderLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        paymentHeaderLbl.setText("Payment Area");

        payBtn.setBackground(new java.awt.Color(0, 204, 0));
        payBtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        payBtn.setForeground(new java.awt.Color(255, 255, 255));
        payBtn.setText("Pay ");
        payBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        payBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                payBtnMouseClicked(evt);
            }
        });

        paymentTable.setBackground(new java.awt.Color(63, 100, 126));
        paymentTable.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        paymentTable.setForeground(new java.awt.Color(255, 255, 255));
        paymentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Snack Item", "Quantity", "Price per Unit (in RM)", "Total Price (in RM)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        paymentTable.setShowGrid(true);
        jScrollPane2.setViewportView(paymentTable);

        totalLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        totalLbl.setForeground(new java.awt.Color(255, 255, 255));
        totalLbl.setText("Total: ");

        totalAmountLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        totalAmountLbl.setForeground(new java.awt.Color(255, 255, 255));
        totalAmountLbl.setText("RM0.00");

        notesLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        notesLbl.setForeground(new java.awt.Color(255, 255, 255));
        notesLbl.setText("Notes (in RM)");

        centesLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        centesLbl.setForeground(new java.awt.Color(255, 255, 255));
        centesLbl.setText("Cents (in Sen)");

        notesComboBox.setBackground(new java.awt.Color(102, 102, 255));
        notesComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        notesComboBox.setForeground(new java.awt.Color(255, 255, 255));
        notesComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RM0", "RM1", "RM5", "RM10", "RM50" }));
        notesComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        centsComboBox.setBackground(new java.awt.Color(102, 102, 255));
        centsComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        centsComboBox.setForeground(new java.awt.Color(255, 255, 255));
        centsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "10", "20", "50" }));
        centsComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        receiptCheckBox.setBackground(new java.awt.Color(255, 153, 51));
        receiptCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        receiptCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        receiptCheckBox.setText("Receipt");

        resetBtn.setBackground(new java.awt.Color(255, 51, 51));
        resetBtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        resetBtn.setForeground(new java.awt.Color(255, 255, 255));
        resetBtn.setText("Reset");
        resetBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        changeLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        changeLbl.setForeground(new java.awt.Color(255, 255, 255));
        changeLbl.setText("Change: ");

        changeAmountLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        changeAmountLbl.setForeground(new java.awt.Color(255, 255, 255));
        changeAmountLbl.setText("RM0.00");

        javax.swing.GroupLayout paymentPanelLayout = new javax.swing.GroupLayout(paymentPanel);
        paymentPanel.setLayout(paymentPanelLayout);
        paymentPanelLayout.setHorizontalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paymentHeaderLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paymentPanelLayout.createSequentialGroup()
                        .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(notesLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(notesComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(52, 52, 52)
                        .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(centsComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(centesLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(264, 264, 264)
                        .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(resetBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(changeLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(payBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                            .addComponent(totalAmountLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(changeAmountLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paymentPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(receiptCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
        );
        paymentPanelLayout.setVerticalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addComponent(paymentHeaderLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalAmountLbl)
                    .addComponent(totalLbl)
                    .addComponent(notesLbl)
                    .addComponent(centesLbl))
                .addGap(1, 1, 1)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(changeLbl)
                    .addComponent(changeAmountLbl)
                    .addComponent(notesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(centsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(payBtn)
                    .addComponent(resetBtn))
                .addGap(7, 7, 7)
                .addComponent(receiptCheckBox)
                .addContainerGap())
        );

        scrollPanel.setBackground(new java.awt.Color(0, 176, 178));

        javax.swing.GroupLayout scrollPanelLayout = new javax.swing.GroupLayout(scrollPanel);
        scrollPanel.setLayout(scrollPanelLayout);
        scrollPanelLayout.setHorizontalGroup(
            scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1165, Short.MAX_VALUE)
        );
        scrollPanelLayout.setVerticalGroup(
            scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 671, Short.MAX_VALUE)
        );

        jScrollPane5.setViewportView(scrollPanel);

        vendingMachineScrollPanel.setViewportView(jScrollPane5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(vendingMachineScrollPanel)
            .addComponent(paymentPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1179, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(vendingMachineScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paymentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backBtnMouseClicked
        // TODO add your handling code here:
        try {
            String saveDir = System.getProperty("user.dir") + "/StaffInfo/";
            File cache = new File(saveDir + "Cache.txt");

            if (cache.exists()) {
                new AdminHomePage().setVisible(true);
                this.dispose();
            } else {
                new HomePage().setVisible(true);
                this.dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_backBtnMouseClicked

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_backBtnActionPerformed

    private void payBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_payBtnMouseClicked
        // TODO add your handling code here:
        if (Float.parseFloat(changeAmountLbl.getText().replace("RM", "")) > 0) {
            return;
        }

        if (totalAmountLbl.getText() == "RM0.00") {
            JOptionPane.showMessageDialog(null, "Please order snack items first before paying!", "Error: No snack items ordered", JOptionPane.WARNING_MESSAGE);
        } else {
            String total = totalAmountLbl.getText();
            Float totalFloat = Float.parseFloat(total.replace("RM", ""));

            if (notesComboBox.getSelectedIndex() == 0 && centsComboBox.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Please select the amount of money at the Combo Box provided to pay", "Error: No money selected", JOptionPane.WARNING_MESSAGE);
            } else {
                Float notes = Float.parseFloat(notesComboBox.getSelectedItem().toString().replace("RM", ""));
                Float cent = Float.parseFloat(centsComboBox.getSelectedItem().toString());

                Float sum = notes + (cent / 100);
                Float remain = Float.parseFloat(totalAmountLbl.getText().replace("RM", "")) - sum;

                if (remain <= 0) {
                    changeAmountLbl.setText("RM" + String.format("%.02f", Math.abs(remain)));

                    Float paid = Float.parseFloat(originalTotalAmount.replace("RM", "")) - remain;

                    addPurchaseHistory(originalTotalAmount, ("RM" + String.format("%.02f", paid)), ("RM" + String.format("%.02f", Math.abs(remain))));
                    updateSnackTxt();

                    if (receiptCheckBox.isSelected()) {
                        String saveDir = System.getProperty("user.dir") + "/SnacksInfo/";
                        File historytxt = new File(saveDir + "PurchaseHistory.txt");
                        String[] matchedID = null;

                        try {
                            Scanner inputFile = new Scanner(historytxt);
                            while (inputFile.hasNext()) {
                                String entry = inputFile.nextLine();
                                matchedID = entry.split(":");
                            }
                            inputFile.close();

                            String id = matchedID[0].replace("PH", "Receipt Order #");
                            String consumer = matchedID[1];
                            String totalPrice = matchedID[matchedID.length - 3];
                            String paidAmount = matchedID[matchedID.length - 2];
                            String changeAmount = matchedID[matchedID.length - 1];

                            File receipt = new File(saveDir + id + ".txt");
                            PrintWriter pw = new PrintWriter(new FileWriter(receipt, true));
                            int i = 2;
                            String orderList = "";
                            LocalDate date = LocalDate.now();
                            String time = String.valueOf(LocalTime.now()).substring(0, 8);

                            while (i < matchedID.length - 3) {
                                orderList += matchedID[i] + "\t\t\t" + "          ";
                                orderList += matchedID[i + 1] + "\t\t\t\t" + "       ";
                                orderList += "RM" + matchedID[i + 2] + "\t\t\t" + "            ";
                                orderList += "RM" + matchedID[i + 3] + "\n";

                                i += 4;
                            }

                            pw.println(id + "\n\n"
                                    + "Consumer: " + consumer + "\n"
                                    + "Date: " + date + "\n"
                                    + "Time: " + time + "\n\n\n"
                                    + "Purchased Snack Items: \n\n"
                                    + "Snack Name\t\t\t" + "Snack Quantity\t\t\t" + "Snack Unit Price\t\t\t" + "Snack Total Price:\t\t\t" + "\n"
                                    + orderList + "\n"
                                    + "Total Price:\t" + totalPrice + "\n"
                                    + "Paid Amount:\t" + paidAmount + "\n"
                                    + "Change Amount:\t" + changeAmount + "\n\n\n\n"
                                    + "All rights reserved by owner of SnackNow Vending Machine, Ryan Lim Fang Yung\n"
                                    + "For more enquiries, contact me at tp055343@mail.apu.edu.my");
                            pw.close();

                            JOptionPane.showMessageDialog(null, "Receipt has been successfully created!", "Success: Receipt Created", JOptionPane.INFORMATION_MESSAGE);

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Thanks for your purchase. Here's your change.", "Success: Payment Success", JOptionPane.INFORMATION_MESSAGE);
                    int selection = JOptionPane.showConfirmDialog(null, "Do you want to continue ordering?", "Continue Ordering?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (selection == JOptionPane.YES_OPTION) {
                        new VendingMachineForm().setVisible(true);
                        this.dispose();
                    }
                } else {
                    totalAmountLbl.setText("RM" + String.format("%.02f", remain));
                }

            }
        }
    }//GEN-LAST:event_payBtnMouseClicked

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
            java.util.logging.Logger.getLogger(VendingMachineForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VendingMachineForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VendingMachineForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VendingMachineForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VendingMachineForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel centesLbl;
    private javax.swing.JComboBox<String> centsComboBox;
    private javax.swing.JLabel changeAmountLbl;
    private javax.swing.JLabel changeLbl;
    private javax.swing.JLabel copyrightLbl;
    private javax.swing.JLabel dateTimeLbl;
    private javax.swing.JLabel enquiryLbl;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JComboBox<String> notesComboBox;
    private javax.swing.JLabel notesLbl;
    private javax.swing.JToggleButton payBtn;
    private javax.swing.JLabel paymentHeaderLbl;
    private javax.swing.JPanel paymentPanel;
    private javax.swing.JTable paymentTable;
    private javax.swing.JCheckBox receiptCheckBox;
    private javax.swing.JToggleButton resetBtn;
    private javax.swing.JPanel scrollPanel;
    private javax.swing.JLabel snackNowLbl;
    private javax.swing.JLabel totalAmountLbl;
    private javax.swing.JLabel totalLbl;
    private javax.swing.JScrollPane vendingMachineScrollPanel;
    // End of variables declaration//GEN-END:variables
}
