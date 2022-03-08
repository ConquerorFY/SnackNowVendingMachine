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
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InputValidation {

    public boolean stringValidation(JTextField txt) {
        String input = txt.getText();
        String regex = "^[a-zA-Z0-9 ]+";
        String msg = "This field can only consist of letters, numbers or spacings only";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Input Validation Error", JOptionPane.WARNING_MESSAGE);
            txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    public boolean usernameValidation(JTextField txt) {
        String input = txt.getText();
        String regex = "^[-a-zA-Z0-9!@#$%^&*()\\{\\}\\[\\]\"\';\\\\/?|.,><~`_+=]+";
        String msg = "Invalid username! Username can only consist of letters, numbers and certain escaped symbols.";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Username Validation Error", JOptionPane.WARNING_MESSAGE);
            txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    public boolean passwordValidation(JTextField txt) {
        String input = txt.getText();
        String regex = "^[-a-zA-Z0-9!@#$%^&*()\\{\\}\\[\\]\"\';\\\\/?|.,><~`_+=]+";
        String msg = "Invalid password! Password can only consist of letters, numbers and certain escaped symbols.";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Password Validation Error", JOptionPane.WARNING_MESSAGE);
            txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    public boolean emailValidation(JTextField txt) {
        String input = txt.getText();
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        String msg = "Invalid email address! Email must follw the format of xxxxx@xxx.xxx";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Email Validation Error", JOptionPane.WARNING_MESSAGE);
            txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    public boolean nameValidation(JTextField txt) {
        String input = txt.getText();
        String regex = "^[a-zA-Z ]+";
        String msg = "Invalid name! Name can only consist of letters and spacing";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Name Validation Error", JOptionPane.WARNING_MESSAGE);
            txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    public boolean addressValidation(JTextArea txt) {
        String input = txt.getText();
        String regex = "^[a-zA-Z0-9()&., ]+";
        String msg = "Invalid address! Address can only consist of letters, numbers, spacing and certain symbols";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Address Validation Error", JOptionPane.WARNING_MESSAGE);
            txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    public boolean dobValidation(JTextField txt) {
        String input = txt.getText();
        String regex = "^[0-9]{2}-[0-9]{2}-[0-9]{4}";
        String msg = "Invalid Date of Birth! Date of Birth must follow the format of dd-mm-yyyy";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Date of Birth Validation Error", JOptionPane.WARNING_MESSAGE);
            txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    public boolean contactValidation(JTextField txt) {
        String input = txt.getText();
        String regex = "^[0-9]+-[0-9]+";
        String msg = "Invalid Contact Number! Contact Number must follow the format of 000-0000000";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Contact Number Validation Error", JOptionPane.WARNING_MESSAGE);
            txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    public boolean genderValidation(JTextField txt) {
        String input = txt.getText();
        String regex = "^male|female$";
        String msg = "Invalid Gender! Gender must be either male or female";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Gender Validation Error", JOptionPane.WARNING_MESSAGE);
            txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    public boolean snackNameValidation(JTextField txt) {
        String input = txt.getText();
        String regex = "^[a-zA-Z ]{1,17}$";
        String msg = "Invalid Snack Name! Snack Name can only consist of letters, spacing and must be at most 17 characters long";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Snack Name Validation Error", JOptionPane.WARNING_MESSAGE);
            txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    public boolean snackQuantityValidation(JTextField txt) {
        try {
            int input = Integer.parseInt(txt.getText());
            if (input <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid Snack Quantity! Snack Quantity cannot be less than or equal to 0", "Eror: Snack Quantity Validation Error", JOptionPane.WARNING_MESSAGE);
                txt.setBackground(new Color(143, 168, 50));
                return false;
            } else if (input > 30) {
                JOptionPane.showMessageDialog(null, "Invalid Snack Quantity! Snack Quantity cannot exceed 30", "Error: Snack Quantity Validation Error", JOptionPane.WARNING_MESSAGE);
                txt.setBackground(new Color(143, 168, 50));
                return false;
            }
            txt.setBackground(new Color(102, 102, 102));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
        return true;
    }

    public boolean snackPriceValidation(JTextField txt) {
        try {
            float input = Float.parseFloat(txt.getText());
            if (input <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid Snack Price! Snack Price cannot be less than or equal to 0", "Error: Snack Price Validation Error", JOptionPane.WARNING_MESSAGE);
                txt.setBackground(new Color(143, 168, 50));
                return false;
            } else if (input > 30) {
                JOptionPane.showMessageDialog(null, "Invalid Snack Price! Snack Price cannot exceed 30", "Error: Snack Price Validation Error", JOptionPane.WARNING_MESSAGE);
                txt.setBackground(new Color(143, 168, 50));
                return false;
            }
            txt.setBackground(new Color(102, 102, 102));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
        return true;
    }
}
