/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import models.Activity;

/**
 *
 * @author Gennaro
 */
public class ActivityPanel extends javax.swing.JPanel
{

    /**
     * Creates new form ActivityPanel
     */
    public ActivityPanel(Activity activityModel)
    {
        initComponents();
        
        String time = new SimpleDateFormat("H:mm").format(activityModel.getTimeStamp());
        
        switch (activityModel.getActivity())
        {
            case Activity.ActivityType.CARDIO:
                activityLabel.setText("Cardio @ "  + time);
                break;
            case Activity.ActivityType.SEDENTARY:
                activityLabel.setText("Sedentary @ " + time);
                break;
            case Activity.ActivityType.STRENGTH:
                activityLabel.setText("Strength @ " + time);
                break;
            default:
                activityLabel.setText("Error");
        }
        
        durationLabel.setText(activityModel.getTimeSpent());
        pulseLabel.setText(activityModel.getPulse() + " bpm");
        bloodPressureLabel.setText(activityModel.getBloodPressure());
        bloodSugarLabel.setText(activityModel.getBloodSugar() + " mg/dl");
        dateLabel.setText(new SimpleDateFormat("MM/dd/yyyy").format(activityModel.getTimeStamp()));
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        activityLabel = new javax.swing.JLabel();
        durationLabel = new javax.swing.JLabel();
        pulseLabel = new javax.swing.JLabel();
        bloodPressureLabel = new javax.swing.JLabel();
        bloodSugarLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();

        jLabel1.setText("Activity:");

        jLabel2.setText("Duration:");

        jLabel3.setText("Pulse:");

        jLabel4.setText("BP:");

        jLabel5.setText("BS:");

        activityLabel.setText("jLabel6");

        durationLabel.setText("jLabel7");

        pulseLabel.setText("jLabel8");

        bloodPressureLabel.setText("jLabel9");

        bloodSugarLabel.setText("jLabel10");

        jLabel6.setText("Date:");

        dateLabel.setText("jLabel7");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(activityLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(durationLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pulseLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bloodPressureLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bloodSugarLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateLabel)))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(activityLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(durationLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pulseLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(bloodPressureLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(bloodSugarLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(dateLabel))
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel activityLabel;
    private javax.swing.JLabel bloodPressureLabel;
    private javax.swing.JLabel bloodSugarLabel;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel durationLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel pulseLabel;
    // End of variables declaration//GEN-END:variables
}
