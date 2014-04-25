/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import models.Activity;

/**
 *
 * @author Gennaro
 */
public class AddActivityPanel extends javax.swing.JPanel
{
    
    // This document will only allow integers to be input into the text field.  
    private class IntegerOnlyDocument extends PlainDocument 
    {

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
        {
            // Don't try to check the string if it is null.
            if (str == null) return;

            char[] input = str.toCharArray();
            boolean allNumbers = true;

            for (char c : input)
            {
                try
                {
                    Integer.parseInt(c + "");
                }
                catch (NumberFormatException ex)
                {
                    // If a character is not an integer, return.
                    allNumbers = false;
                    break;
                }
            }

            if (allNumbers) super.insertString(offs, str, a);
        }
    }

    /**
     * Creates new form AddActivityPanel
     */
    public AddActivityPanel()
    {
        initComponents();
              
        hoursTextField.setDocument(new IntegerOnlyDocument());
        minutesTextField.setDocument(new IntegerOnlyDocument());
        secondsTextField.setDocument(new IntegerOnlyDocument());
        pulseTextField.setDocument(new IntegerOnlyDocument());
        bloodPressureTextField1.setDocument(new IntegerOnlyDocument());
        bloodPressureTextField2.setDocument(new IntegerOnlyDocument());
        bloodSugarTextField.setDocument(new IntegerOnlyDocument());
    }
    
    public void addSubmitButtonListener(ActionListener listener)
    {
        submitButton.addActionListener(listener);
    }
    
    public Activity getActivity(Date timeStamp)
    {
        int activity = activityComboBox.getSelectedIndex();
        
        // If any text fields are empty, set them to 0.
        resetIfEmpty(hoursTextField);
        resetIfEmpty(minutesTextField);
        resetIfEmpty(secondsTextField);
        resetIfEmpty(pulseTextField);
        resetIfEmpty(bloodPressureTextField1);
        resetIfEmpty(bloodPressureTextField2);
        resetIfEmpty(bloodSugarTextField);
        
        int hours = Integer.parseInt(hoursTextField.getText());
        int minutes = Integer.parseInt(minutesTextField.getText());
        int seconds = Integer.parseInt(secondsTextField.getText());
        
        int pulse = Integer.parseInt(pulseTextField.getText());
        int bloodPressureS;
        int bloodPressureD;
        if (Integer.parseInt(bloodPressureTextField1.getText()) == 0 || Integer.parseInt(bloodPressureTextField2.getText()) == 0)
        {
            bloodPressureS = 0;
            bloodPressureD = 0;
        }
        else
        {
            bloodPressureS = Integer.parseInt(bloodPressureTextField1.getText());
            bloodPressureD = Integer.parseInt(bloodPressureTextField2.getText());
        }
        int bloodSugar = Integer.parseInt(bloodSugarTextField.getText());
        
        return new Activity(activity, timeStamp, hours, minutes, seconds, pulse, bloodPressureS, bloodPressureD, bloodSugar);
    }
    
    /**
     * Sets value to 0 if the text field is empty.
     * @param textField 
     */
    private void resetIfEmpty(JTextField textField)
    {
        if (textField.getText().trim().equals(""))
        {
            textField.setText("0");
        }
    }
    
    public void resetPanel()
    {
        hoursTextField.setText("");
        minutesTextField.setText("");
        secondsTextField.setText("");
        pulseTextField.setText("");
        bloodPressureTextField1.setText("");
        bloodPressureTextField2.setText("");;
        bloodSugarTextField.setText("");
    }
    
    /**
     * Checks each field. If nothing has been input, return true.
     * @return True if all the fields are empty.
     */
    public boolean allFieldsEmpty(){
        return (hoursTextField.getText().trim().equals("") && minutesTextField.getText().trim().equals("") && secondsTextField.getText().trim().equals("") 
                && pulseTextField.getText().trim().equals("") && bloodPressureTextField1.getText().trim().equals("") 
                && bloodPressureTextField2.getText().trim().equals("") && bloodSugarTextField.getText().trim().equals(""));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        activityComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        secondsTextField = new javax.swing.JTextField();
        minutesTextField = new javax.swing.JTextField();
        hoursTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        pulseTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        bloodSugarTextField = new javax.swing.JTextField();
        bloodPressureTextField1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        submitButton = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        bloodPressureTextField2 = new javax.swing.JTextField();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        LocalDate date = LocalDate.now();
        String today = date.format(DateTimeFormatter.ofPattern("MM/dd"));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("ADD ACTIVITY - " + today);

        activityComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cardio", "Strength", "Sedentary" }));

        jLabel2.setText("Activity:");

        jLabel3.setText("Time:");

        jLabel4.setText("h");

        jLabel5.setText("m");

        jLabel6.setText("s");

        secondsTextField.setText("00");

        minutesTextField.setText("00");

        hoursTextField.setText("00");

        jLabel7.setText("Pulse:");

        jLabel8.setText("bpm");

        jLabel9.setText("Blood Pressure:");

        jLabel10.setText("Blood Sugar:");

        jLabel11.setText("mg/dl");

        jLabel12.setText("Enter desired information and selected an activity.");

        jLabel13.setText("Clear any fields which you would not like to store.");

        submitButton.setText("Submit");

        jLabel14.setText("/");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(activityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hoursTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(minutesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(secondsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(pulseTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bloodPressureTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bloodPressureTextField2))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bloodSugarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11))))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(submitButton)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(activityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(secondsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hoursTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(minutesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(pulseTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(bloodPressureTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(bloodPressureTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(bloodSugarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(54, 54, 54)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(submitButton)
                .addContainerGap(50, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox activityComboBox;
    private javax.swing.JTextField bloodPressureTextField1;
    private javax.swing.JTextField bloodPressureTextField2;
    private javax.swing.JTextField bloodSugarTextField;
    private javax.swing.JTextField hoursTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField minutesTextField;
    private javax.swing.JTextField pulseTextField;
    private javax.swing.JTextField secondsTextField;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables
}
