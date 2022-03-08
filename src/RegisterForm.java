/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentEvent;

public class RegisterForm extends javax.swing.JFrame {

    /**
     * Creates new form RegisterForm
     */
    private String staffID, saveDir;
    private int newStaffID;
    private final String idprefix = "St";
    private Color fgtxt = new Color(255, 255, 255);
    
    private int mouseX;
    private int mouseY;
    

    public RegisterForm() {
        initComponents();
        this.setLocationRelativeTo(null);

        firstNameText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().nameValidation(((JTextField) input));
            }
        });
        lastNameText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().nameValidation(((JTextField) input));
            }
        });
        genderText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().genderValidation(((JTextField) input));
            }
        });
        addressText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().addressValidation(((JTextArea) input));
            }
        });
        emailText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().emailValidation(((JTextField) input));
            }
        });
        contactText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().contactValidation(((JTextField) input));
            }
        });
        dobText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().dobValidation(((JTextField) input));
            }
        });
        usernameText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().usernameValidation(((JTextField) input));
            }
        });
        passwordText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().passwordValidation(((JTextField) input));
            }
        });
    }

    private void emptyFields() throws Exception {
        if ("".equals(firstNameText.getText())) {
            throw new Exception("Empty First Name");
        }
        if ("".equals(lastNameText.getText())) {
            throw new Exception("Empty Last Name");
        }
        if ("".equals(addressText.getText())) {
            throw new Exception("Empty Address");
        }
        if ("".equals(emailText.getText())) {
            throw new Exception("Empty Address");
        }
        if ("".equals(contactText.getText())) {
            throw new Exception("Empty Contact Number");
        }
        if ("".equals(dobText.getText())) {
            throw new Exception("Empty Date of Birth");
        }
        if ("".equals(genderText.getText())) {
            throw new Exception("Empty Gender");
        }
        if ("".equals(usernameText.getText())) {
            throw new Exception("Empty Username");
        }
        if ("".equals(String.valueOf(passwordText.getPassword()))) {
            throw new Exception("Empty Password");
        }
        if ("".equals(String.valueOf(repasswordText.getPassword()))) {
            throw new Exception("Empty Retype Password");
        }
    }

    private boolean similarPassword() {
        boolean isSimilar = false;
        String currPass = String.valueOf(passwordText.getPassword());
        String comparePass = String.valueOf(repasswordText.getPassword());
        if ("".equals(currPass) || "".equals(comparePass)) {
            isSimilar = false;
        } else if (currPass.equals(comparePass)) {
            isSimilar = true;
        }
        return isSimilar;
    }

    private void clearInput() {
        firstNameText.setText("");
        lastNameText.setText("");
        addressText.setText("");
        emailText.setText("");
        dobText.setText("");
        genderText.setText("");
        contactText.setText("");
        usernameText.setText("");
        passwordText.setText("");
        repasswordText.setText("");
    }

    private void staffIncrementor() {
        String[] matchedID = null;
        boolean hasRecord = false;
        try {
            saveDir = System.getProperty("user.dir") + "/StaffInfo/";
            File stafftxt = new File(saveDir + "Staff.txt");
            if (!stafftxt.exists()) {
                stafftxt.createNewFile();
            }
            Scanner inputFile;
            try {
                inputFile = new Scanner(stafftxt);
                while (inputFile.hasNext()) {
                    String entry = inputFile.nextLine();
                    matchedID = entry.split(":");
                    matchedID[0] = matchedID[0].replace("St", "");
                    hasRecord = true;
                }
                inputFile.close();
                if (!hasRecord) {
                    JOptionPane.showMessageDialog(null, "No staff(s) record was found! Restarting database entry.", "Staff database is empty!", JOptionPane.ERROR_MESSAGE);
                    newStaffID = 1;
                } else {
                    newStaffID = Integer.parseInt(matchedID[0]) + 1;
                }
            } catch (FileNotFoundException ex) {

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            JOptionPane.showMessageDialog(null, "Invalid input! Staff ID can only consist of numbers", "Invalid input type!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void highlightEmpty() {
        if ("".equals(firstNameText.getText())) {
            firstNameLbl.setForeground(Color.yellow);
        }
        if ("".equals(lastNameText.getText())) {
            lastNameLbl.setForeground(Color.yellow);
        }
        if ("".equals(addressText.getText())) {
            addressLbl.setForeground(Color.yellow);
        }
        if ("".equals(emailText.getText())) {
            emailLbl.setForeground(Color.yellow);
        }
        if ("".equals(contactText.getText())) {
            contactLbl.setForeground(Color.yellow);
        }
        if ("".equals(dobText.getText())) {
            dobLbl.setForeground(Color.yellow);
        }
        if ("".equals(genderText.getText())) {
            genderLbl.setForeground(Color.yellow);
        }
        if ("".equals(usernameText.getText())) {
            usernameLbl.setForeground(Color.yellow);
        }
        if ("".equals(String.valueOf(passwordText.getPassword()))) {
            passwordLbl.setForeground(Color.yellow);
        }
        if ("".equals(String.valueOf(repasswordText.getPassword()))) {
            repasswordLbl.setForeground(Color.yellow);
        }
    }

    private void deHighlightEmpty() {
        firstNameLbl.setForeground(fgtxt);
        lastNameLbl.setForeground(fgtxt);
        addressLbl.setForeground(fgtxt);
        emailLbl.setForeground(fgtxt);
        contactLbl.setForeground(fgtxt);
        dobLbl.setForeground(fgtxt);
        genderLbl.setForeground(fgtxt);
        usernameLbl.setForeground(fgtxt);
        passwordLbl.setForeground(fgtxt);
        repasswordLbl.setForeground(fgtxt);
    }

    private boolean usernameValidator() {
        String userTmp = usernameText.getText();
        String[] matchedID = null;
        boolean notAvailable = false;

        saveDir = System.getProperty("user.dir") + "/StaffInfo/";
        File staffAccTxt = new File(saveDir + "Staff.txt");
        Scanner inputFile;
        try {
            if (!staffAccTxt.exists()) {
                staffAccTxt.createNewFile();
            }

            inputFile = new Scanner(staffAccTxt);
            while (inputFile.hasNext()) {
                String entry = inputFile.nextLine();
                matchedID = entry.split(":");
                if (userTmp.equals(matchedID[1])) {
                    notAvailable = true;
                }
            }
            inputFile.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return notAvailable;
    }

    private void addUserInformation() {
        saveDir = System.getProperty("user.dir");
        DecimalFormat dc = new DecimalFormat("000000");
        try {
            staffIncrementor();
            staffID = dc.format(newStaffID);
            emptyFields();

            if (usernameValidator()) {
                throw new Exception("Username Taken");
            }

            if (!similarPassword()) {
                throw new Exception("Password Mismatch");
            }

            String firstname = firstNameText.getText();
            String lastname = lastNameText.getText();
            String address = addressText.getText();
            String email = emailText.getText();
            String contact = contactText.getText();
            String dob = dobText.getText();
            String gender = genderText.getText();
            String username = usernameText.getText();
            String password = String.valueOf(passwordText.getPassword());
            String repassword = String.valueOf(repasswordText.getPassword());
            try {
                FileWriter ld = new FileWriter(saveDir + "Staff.txt", true);
                PrintWriter ldp = new PrintWriter(ld);
                ldp.println(idprefix + staffID + ":"
                        + username + ":"
                        + password + ":"
                        + firstname + ":"
                        + lastname + ":"
                        + gender + ":"
                        + address + ":"
                        + email + ":"
                        + dob + ":"
                        + contact + ":"
                        + "false");
                ldp.close();
                JOptionPane.showMessageDialog(null, "You have been successfully registered as a staff! Press OK to return to staff registration form.", "Account creation succeeded!", JOptionPane.INFORMATION_MESSAGE);
                new AdminHomePage().setVisible(true);
                this.dispose();
            } catch (IOException e) {

            }
        } catch (Exception e) {
            highlightEmpty();
            if (usernameValidator()) {
                JOptionPane.showMessageDialog(null, "Username is already taken!", "Username taken", JOptionPane.WARNING_MESSAGE);
            }
            if (!similarPassword()) {
                JOptionPane.showMessageDialog(null, "Password is not matching!", "Password mismatch!", JOptionPane.WARNING_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Invalid input! Please check your input to proceed.", "Invalid insertion detected!", JOptionPane.WARNING_MESSAGE);

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

        headerPanel = new javax.swing.JPanel();
        registerAccontLbl = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        passwordLbl = new javax.swing.JLabel();
        usernameLbl = new javax.swing.JLabel();
        usernameText = new javax.swing.JTextField();
        passwordText = new javax.swing.JPasswordField();
        cancelBtn = new javax.swing.JButton();
        registerBtn = new javax.swing.JButton();
        lastNameLbl = new javax.swing.JLabel();
        lastNameText = new javax.swing.JTextField();
        firstNameLbl = new javax.swing.JLabel();
        firstNameText = new javax.swing.JTextField();
        addressLbl = new javax.swing.JLabel();
        emailLbl = new javax.swing.JLabel();
        emailText = new javax.swing.JTextField();
        contactLbl = new javax.swing.JLabel();
        dobText = new javax.swing.JTextField();
        repasswordLbl = new javax.swing.JLabel();
        repasswordText = new javax.swing.JPasswordField();
        dobLbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        addressText = new javax.swing.JTextArea();
        contactText = new javax.swing.JTextField();
        genderLbl = new javax.swing.JLabel();
        genderText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(516, 648));
        getContentPane().setLayout(null);

        headerPanel.setBackground(new java.awt.Color(176, 0, 81));
        headerPanel.setToolTipText("");
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

        registerAccontLbl.setBackground(new java.awt.Color(204, 204, 0));
        registerAccontLbl.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        registerAccontLbl.setForeground(new java.awt.Color(255, 255, 255));
        registerAccontLbl.setText("Register Account");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(registerAccontLbl)
                .addContainerGap(266, Short.MAX_VALUE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(registerAccontLbl)
                .addGap(21, 21, 21))
        );

        getContentPane().add(headerPanel);
        headerPanel.setBounds(0, 0, 520, 70);

        bodyPanel.setBackground(new java.awt.Color(246, 131, 112));

        passwordLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        passwordLbl.setForeground(new java.awt.Color(255, 255, 255));
        passwordLbl.setText("Password:");

        usernameLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        usernameLbl.setForeground(new java.awt.Color(255, 255, 255));
        usernameLbl.setText("Username: ");

        usernameText.setBackground(new java.awt.Color(102, 102, 102));
        usernameText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        usernameText.setForeground(new java.awt.Color(255, 255, 255));
        usernameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextActionPerformed(evt);
            }
        });

        passwordText.setBackground(new java.awt.Color(102, 102, 102));
        passwordText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        passwordText.setForeground(new java.awt.Color(255, 255, 255));

        cancelBtn.setBackground(new java.awt.Color(255, 102, 102));
        cancelBtn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        cancelBtn.setForeground(new java.awt.Color(255, 255, 255));
        cancelBtn.setText("Cancel");
        cancelBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelBtnMouseClicked(evt);
            }
        });
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        registerBtn.setBackground(new java.awt.Color(34, 167, 240));
        registerBtn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        registerBtn.setForeground(new java.awt.Color(255, 255, 255));
        registerBtn.setText("Register");
        registerBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        lastNameLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lastNameLbl.setForeground(new java.awt.Color(255, 255, 255));
        lastNameLbl.setText("Last Name:");

        lastNameText.setBackground(new java.awt.Color(102, 102, 102));
        lastNameText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lastNameText.setForeground(new java.awt.Color(255, 255, 255));
        lastNameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameTextActionPerformed(evt);
            }
        });

        firstNameLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        firstNameLbl.setForeground(new java.awt.Color(255, 255, 255));
        firstNameLbl.setText("First Name:");

        firstNameText.setBackground(new java.awt.Color(102, 102, 102));
        firstNameText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        firstNameText.setForeground(new java.awt.Color(255, 255, 255));
        firstNameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameTextActionPerformed(evt);
            }
        });

        addressLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        addressLbl.setForeground(new java.awt.Color(255, 255, 255));
        addressLbl.setText("Address:");

        emailLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        emailLbl.setForeground(new java.awt.Color(255, 255, 255));
        emailLbl.setText("Email:");

        emailText.setBackground(new java.awt.Color(102, 102, 102));
        emailText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        emailText.setForeground(new java.awt.Color(255, 255, 255));
        emailText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                emailTextFocusLost(evt);
            }
        });
        emailText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTextActionPerformed(evt);
            }
        });

        contactLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        contactLbl.setForeground(new java.awt.Color(255, 255, 255));
        contactLbl.setText("Contact No.:");

        dobText.setBackground(new java.awt.Color(102, 102, 102));
        dobText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        dobText.setForeground(new java.awt.Color(255, 255, 255));
        dobText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dobTextActionPerformed(evt);
            }
        });

        repasswordLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        repasswordLbl.setForeground(new java.awt.Color(255, 255, 255));
        repasswordLbl.setText("Retype Password:");

        repasswordText.setBackground(new java.awt.Color(102, 102, 102));
        repasswordText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        repasswordText.setForeground(new java.awt.Color(255, 255, 255));

        dobLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        dobLbl.setForeground(new java.awt.Color(255, 255, 255));
        dobLbl.setText("Birth Date(dd-mm-yy):");

        addressText.setBackground(new java.awt.Color(102, 102, 102));
        addressText.setColumns(20);
        addressText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        addressText.setForeground(new java.awt.Color(255, 255, 255));
        addressText.setRows(5);
        addressText.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.setViewportView(addressText);

        contactText.setBackground(new java.awt.Color(102, 102, 102));
        contactText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        contactText.setForeground(new java.awt.Color(255, 255, 255));
        contactText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactTextActionPerformed(evt);
            }
        });

        genderLbl.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        genderLbl.setForeground(new java.awt.Color(255, 255, 255));
        genderLbl.setText("Gender:");

        genderText.setBackground(new java.awt.Color(102, 102, 102));
        genderText.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        genderText.setForeground(new java.awt.Color(255, 255, 255));
        genderText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genderTextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bodyPanelLayout = new javax.swing.GroupLayout(bodyPanel);
        bodyPanel.setLayout(bodyPanelLayout);
        bodyPanelLayout.setHorizontalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(repasswordLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addGap(0, 96, Short.MAX_VALUE)
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(passwordLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lastNameLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(firstNameLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(genderLbl, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(60, 60, 60)
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(firstNameText, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(passwordText, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(usernameText, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(lastNameText, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(repasswordText, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(genderText, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dobLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(contactLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(emailLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(addressLbl, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(60, 60, 60)
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(emailText, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                                .addComponent(dobText)
                                .addComponent(contactText, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(50, 50, 50))
        );
        bodyPanelLayout.setVerticalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameLbl)
                    .addComponent(firstNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastNameLbl)
                    .addComponent(lastNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genderLbl)
                    .addComponent(genderText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addressLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLbl)
                    .addComponent(emailText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactLbl)
                    .addComponent(contactText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dobLbl)
                    .addComponent(dobText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLbl)
                    .addComponent(usernameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLbl)
                    .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(repasswordLbl)
                    .addComponent(repasswordText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );

        getContentPane().add(bodyPanel);
        bodyPanel.setBounds(0, 70, 520, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTextActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        // TODO add your handling code here:
        int selection = JOptionPane.showConfirmDialog(null, "Proceed in cancelling the registration process?", "Back to Login Screen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (selection == JOptionPane.YES_OPTION) {
            new AdminHomePage().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        // TODO add your handling code here:
        deHighlightEmpty();
        addUserInformation();
    }//GEN-LAST:event_registerBtnActionPerformed

    private void lastNameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameTextActionPerformed

    private void firstNameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameTextActionPerformed

    private void emailTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextActionPerformed

    private void dobTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dobTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dobTextActionPerformed

    private void emailTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailTextFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_emailTextFocusLost

    private void contactTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactTextActionPerformed

    private void genderTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genderTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_genderTextActionPerformed

    private void cancelBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelBtnMouseClicked

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
            java.util.logging.Logger.getLogger(RegisterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLbl;
    private javax.swing.JTextArea addressText;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel contactLbl;
    private javax.swing.JTextField contactText;
    private javax.swing.JLabel dobLbl;
    private javax.swing.JTextField dobText;
    private javax.swing.JLabel emailLbl;
    private javax.swing.JTextField emailText;
    private javax.swing.JLabel firstNameLbl;
    private javax.swing.JTextField firstNameText;
    private javax.swing.JLabel genderLbl;
    private javax.swing.JTextField genderText;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lastNameLbl;
    private javax.swing.JTextField lastNameText;
    private javax.swing.JLabel passwordLbl;
    private javax.swing.JPasswordField passwordText;
    private javax.swing.JLabel registerAccontLbl;
    private javax.swing.JButton registerBtn;
    private javax.swing.JLabel repasswordLbl;
    private javax.swing.JPasswordField repasswordText;
    private javax.swing.JLabel usernameLbl;
    private javax.swing.JTextField usernameText;
    // End of variables declaration//GEN-END:variables
}
