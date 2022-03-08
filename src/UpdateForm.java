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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentEvent;

public class UpdateForm extends javax.swing.JFrame {

    /**
     * Creates new form UpdateForm
     */
    private String staffID, saveDir;
    private final String idprefix = "St";
    private Color fgtxt = new Color(255, 255, 255);
    
    private int mouseX;
    private int mouseY;

    public UpdateForm() {
        initComponents();
        this.setLocationRelativeTo(null);

        try {
            saveDir = System.getProperty("user.dir") + "/StaffInfo/";
            File cache = new File(saveDir + "Cache.txt");
            Scanner inputFile = new Scanner(cache);
            while (inputFile.hasNext()) {
                String entry = inputFile.nextLine();
                String[] matchedID = entry.split(":");
                staffID = matchedID[0];
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        firstNameText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().nameValidation((JTextField) input);
            }
        });
        lastNameText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().nameValidation((JTextField) input);
            }
        });
        genderText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().genderValidation((JTextField) input);
            }
        });
        addressText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().addressValidation((JTextArea) input);
            }
        });
        emailText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().emailValidation((JTextField) input);
            }
        });
        contactText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().contactValidation((JTextField) input);
            }
        });
        dobText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().dobValidation((JTextField) input);
            }
        });
        usernameText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().usernameValidation((JTextField) input);
            }
        });
        passwordText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return new InputValidation().passwordValidation((JTextField) input);
            }
        });
    }

    private void allEmptyFields() throws Exception {
        if ("".equals(firstNameText.getText()) && "".equals(lastNameText.getText())
                && "".equals(addressText.getText()) && "".equals(emailText.getText())
                && "".equals(contactText.getText()) && "".equals(dobText.getText())
                && "".equals(genderText.getText()) && "".equals(usernameText.getText())
                && "".equals(String.valueOf(passwordText.getPassword()))
                && "".equals(String.valueOf(repasswordText.getPassword()))) {

            throw new Exception("Empty Details");
        }
    }

    private boolean similarPassword() {
        boolean isSimilar = false;
        String currPass = String.valueOf(passwordText.getPassword());
        String comparePass = String.valueOf(repasswordText.getPassword());
        if ("".equals(currPass) || "".equals(comparePass)) {
            if ("".equals(currPass) && "".equals(comparePass)) {
                isSimilar = true;
            } else {
                isSimilar = false;
            }
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

    private void highlightEmpty() {
        firstNameLbl.setForeground(Color.yellow);
        lastNameLbl.setForeground(Color.yellow);
        addressLbl.setForeground(Color.yellow);
        emailLbl.setForeground(Color.yellow);
        contactLbl.setForeground(Color.yellow);
        dobLbl.setForeground(Color.yellow);
        genderLbl.setForeground(Color.yellow);
        usernameLbl.setForeground(Color.yellow);
        passwordLbl.setForeground(Color.yellow);
        repasswordLbl.setForeground(Color.yellow);
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

    private void updateStaffInformation() {
        saveDir = System.getProperty("user.dir") + "/StaffInfo/";
        DecimalFormat dc = new DecimalFormat("000000");
        try {
            staffID = dc.format(Integer.parseInt(staffID));
            allEmptyFields();

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

            File originalFile = new File(saveDir + "Staff.txt");
            File tmpFile = new File(saveDir + "tmp.txt");
            PrintWriter pw = new PrintWriter(new FileWriter(tmpFile));

            Scanner inputFile = new Scanner(originalFile);

            while (inputFile.hasNext()) {
                String entry = inputFile.nextLine();
                String[] matchedID = entry.split(":");
                String staffid = matchedID[0];
                String oldFirstName = matchedID[3];
                String oldLastName = matchedID[4];
                String oldAddress = matchedID[6];
                String oldEmail = matchedID[7];
                String oldContact = matchedID[9];
                String oldDob = matchedID[8];
                String oldGender = matchedID[5];
                String oldUsername = matchedID[1];
                String oldPassword = matchedID[2];
                String oldStatus = matchedID[10];

                if (matchedID[0].equals((idprefix + staffID))) {
                    if (!("".equals(firstname))) {
                        oldFirstName = firstname;
                    }
                    if (!("".equals(lastname))) {
                        oldLastName = lastname;
                    }
                    if (!("".equals(address))) {
                        oldAddress = address;
                    }
                    if (!("".equals(email))) {
                        oldEmail = email;
                    }
                    if (!("".equals(contact))) {
                        oldContact = contact;
                    }
                    if (!("".equals(dob))) {
                        oldDob = dob;
                    }
                    if (!("".equals(gender))) {
                        oldGender = gender;
                    }
                    if (!("".equals(username))) {
                        oldUsername = username;
                    }
                    if (!("".equals(password))) {
                        oldPassword = password;
                    }
                }

                pw.println(staffid + ":"
                        + oldUsername + ":"
                        + oldPassword + ":"
                        + oldFirstName + ":"
                        + oldLastName + ":"
                        + oldGender + ":"
                        + oldAddress + ":"
                        + oldEmail + ":"
                        + oldDob + ":"
                        + oldContact + ":"
                        + oldStatus);
            }
            inputFile.close();
            pw.close();

            if (!originalFile.delete()) {
                JOptionPane.showMessageDialog(null, "Could not delete old file", "Error in deleting file", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            if (!tmpFile.renameTo(originalFile)) {
                JOptionPane.showMessageDialog(null, "Could not rename new file", "Error in renaming file", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, "Details have been updated successfully", "Update Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            highlightEmpty();
            if (usernameValidator()) {
                JOptionPane.showMessageDialog(null, "Username is already taken!", "Username taken", JOptionPane.WARNING_MESSAGE);
            }
            if (!similarPassword()) {
                JOptionPane.showMessageDialog(null, "Password is not matching!", "Password mismatch!", JOptionPane.WARNING_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, e);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        updateAccontLbl = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        passwordLbl = new javax.swing.JLabel();
        usernameLbl = new javax.swing.JLabel();
        usernameText = new javax.swing.JTextField();
        passwordText = new javax.swing.JPasswordField();
        cancelBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
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
        deleteBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

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

        updateAccontLbl.setBackground(new java.awt.Color(204, 204, 0));
        updateAccontLbl.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        updateAccontLbl.setForeground(new java.awt.Color(255, 255, 255));
        updateAccontLbl.setText("Update Account");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(updateAccontLbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(updateAccontLbl)
                .addGap(21, 21, 21))
        );

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
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        updateBtn.setBackground(new java.awt.Color(34, 167, 240));
        updateBtn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        updateBtn.setForeground(new java.awt.Color(255, 255, 255));
        updateBtn.setText("Update");
        updateBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        updateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateBtnMouseClicked(evt);
            }
        });
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
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

        deleteBtn.setBackground(new java.awt.Color(255, 51, 51));
        deleteBtn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        deleteBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteBtn.setText("Delete");
        deleteBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteBtnMouseClicked(evt);
            }
        });
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
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
                        .addGap(0, 0, Short.MAX_VALUE)
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(firstNameText)
                            .addComponent(passwordText)
                            .addComponent(usernameText)
                            .addComponent(lastNameText)
                            .addComponent(repasswordText)
                            .addComponent(genderText, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dobLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
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
                    .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bodyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(bodyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTextActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        // TODO add your handling code here:
        int selection = JOptionPane.showConfirmDialog(null, "Proceed in cancelling the update process?", "Back to Staff Home Page", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (selection == JOptionPane.YES_OPTION) {
            new AdminHomePage().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        // TODO add your handling code here:
        deHighlightEmpty();
        updateStaffInformation();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void lastNameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameTextActionPerformed

    private void firstNameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameTextActionPerformed

    private void emailTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailTextFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextFocusLost

    private void emailTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextActionPerformed

    private void dobTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dobTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dobTextActionPerformed

    private void contactTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactTextActionPerformed

    private void genderTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genderTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_genderTextActionPerformed

    private void updateBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseClicked
        // TODO add your handling code here:
        deHighlightEmpty();
        updateStaffInformation();
    }//GEN-LAST:event_updateBtnMouseClicked

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void deleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseClicked
        // TODO add your handling code here:
        int selection = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your staff account?", "Delete Account Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (selection == JOptionPane.YES_OPTION) {
            String saveDir = System.getProperty("user.dir") + "/StaffInfo/";
            File stafftxt = new File(saveDir + "Staff.txt");
            File cache = new File(saveDir + "Cache.txt");
            File tmpFile = new File(saveDir + "tmpStaff.txt");
            String deleteID = null;
            String[] matchedID = null;
            String staffInfo = "";
            int filesize = 0;
            int counter = 0;

            try {
                Scanner input = new Scanner(stafftxt);
                while (input.hasNext()) {
                    filesize++;
                    input.nextLine();
                }
                input.close();

                Scanner cacheInput = new Scanner(cache);
                while (cacheInput.hasNext()) {
                    String entry = cacheInput.nextLine();
                    matchedID = entry.split(":");
                }
                cacheInput.close();
                deleteID = "St" + matchedID[0];

                Scanner fileInput = new Scanner(stafftxt);
                while (fileInput.hasNext()) {
                    counter++;
                    String entry = fileInput.nextLine();
                    matchedID = entry.split(":");
                    String id = matchedID[0];

                    if (!deleteID.equals(id)) {
                        if (counter == filesize) {
                            staffInfo += entry;
                        } else {
                            staffInfo += entry + "\n";
                        }
                    }

                }
                fileInput.close();

                PrintWriter pw = new PrintWriter(new FileWriter(tmpFile));
                pw.println(staffInfo);
                pw.close();

                if (!stafftxt.delete()) {
                    JOptionPane.showMessageDialog(null, "Unable to delete the old staff list!", "Error: Deletion Unsuccessful", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!tmpFile.renameTo(stafftxt)) {
                    JOptionPane.showMessageDialog(null, "Unable to rename the new staff list!", "Error: Rename Unsuccessful", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(null, "Your account has been deleted successfully!", "Success: Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
                cacheClear();
                new HomePage().setVisible(true);
                this.dispose();
                
            
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }
    }//GEN-LAST:event_deleteBtnMouseClicked

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
            java.util.logging.Logger.getLogger(UpdateForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateForm().setVisible(true);
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
    private javax.swing.JButton deleteBtn;
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
    private javax.swing.JLabel repasswordLbl;
    private javax.swing.JPasswordField repasswordText;
    private javax.swing.JLabel updateAccontLbl;
    private javax.swing.JButton updateBtn;
    private javax.swing.JLabel usernameLbl;
    private javax.swing.JTextField usernameText;
    // End of variables declaration//GEN-END:variables
}
