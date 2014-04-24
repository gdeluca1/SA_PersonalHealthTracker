/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import models.Activity;

/**
 *
 * @author Gennaro
 */
public class TrendingPanel extends javax.swing.JPanel
{

    /**
     * Creates new form TrendingPanel
     */
    public TrendingPanel()
    {
        initComponents();
        
        // Put the radio buttons in a button group.
        ButtonGroup group = new ButtonGroup();
        group.add(cardioRadioButton);
        group.add(strengthRadioButton);
        group.add(sedentaryRadioButton);
        
        // Select the cardioRadioButton by default.
        cardioRadioButton.setSelected(true);
    }
    
    public void addBarGraphButtonListener(ActionListener listener)
    {
        barGraphButton.addActionListener(listener);
    }
    
    public void addPieChartButtonListener(ActionListener listener)
    {
        pieChartButton.addActionListener(listener);
    }
    
    public void addLineGraphButtonListener(ActionListener listener)
    {
        lineGraphButton.addActionListener(listener);
    }
    
    public int getSelectedRadioButton()
    {
        if (cardioRadioButton.isSelected())
        {
            return Activity.ActivityType.CARDIO;
        }
        else if (strengthRadioButton.isSelected())
        {
            return Activity.ActivityType.STRENGTH;
        }
        else
        {
            return Activity.ActivityType.SEDENTARY;
        }
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

        barGraphButton = new javax.swing.JButton();
        pieChartButton = new javax.swing.JButton();
        lineGraphButton = new javax.swing.JButton();
        strengthRadioButton = new javax.swing.JRadioButton();
        cardioRadioButton = new javax.swing.JRadioButton();
        sedentaryRadioButton = new javax.swing.JRadioButton();

        barGraphButton.setText("Add Bar Graph");

        pieChartButton.setText("Add Pie Chart");

        lineGraphButton.setText("Add Line Graph");

        strengthRadioButton.setText("Strength Line Graph");

        cardioRadioButton.setText("Cardio Line Graph");

        sedentaryRadioButton.setText("Sedentary Line Graph");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sedentaryRadioButton)
                    .addComponent(cardioRadioButton)
                    .addComponent(lineGraphButton)
                    .addComponent(pieChartButton)
                    .addComponent(barGraphButton)
                    .addComponent(strengthRadioButton))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(barGraphButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pieChartButton)
                .addGap(18, 18, 18)
                .addComponent(lineGraphButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardioRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sedentaryRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(strengthRadioButton)
                .addContainerGap(78, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton barGraphButton;
    private javax.swing.JRadioButton cardioRadioButton;
    private javax.swing.JButton lineGraphButton;
    private javax.swing.JButton pieChartButton;
    private javax.swing.JRadioButton sedentaryRadioButton;
    private javax.swing.JRadioButton strengthRadioButton;
    // End of variables declaration//GEN-END:variables
}
