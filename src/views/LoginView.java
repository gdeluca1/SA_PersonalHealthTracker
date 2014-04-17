package views;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import personalhealthtracker.PersonalHealthTracker;

public class LoginView extends javax.swing.JFrame
{
    private static final Dimension screenSize;
    
    /**
     * Creates new form LoginView
     */
    public LoginView()
    {
        initComponents();
        
        // Center the frame in the middle of the screen.
        setLocation(screenSize.width/2 - getWidth()/2, screenSize.height/2 - getHeight()/2);
        
        // Bind the "Enter" key to the Login Button.
        getRootPane().setDefaultButton(loginButton);
        
        try
        {
             setIconImage(ImageIO.read(PersonalHealthTracker.class.getResource("librarian.jpg")));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.out.println("Failed to set icon image in login view.");
        }
    }
    
    static 
    {
        // Screen size is a non-changing, static constant. Instatiate it here.
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }
    
    public void addLoginButtonListener(ActionListener listener)
    {
        loginButton.addActionListener(listener);
    }
    
    public void addForgotPasswordButtonListener(ActionListener listener)
    {
        forgotPasswordButton.addActionListener(listener);
    }
    
    public void addCreateAccountButtonListener(ActionListener listener)
    {
        createAccountButton.addActionListener(listener);
    }
    
    public void addDeleteAccountButtonListener(ActionListener listener)
    {
        deleteAccountButton.addActionListener(listener);
    }
    
    public void clearFields()
    {
        usernameField.setText("");
        passwordField.setText("");
    }
    
    public String getUsername()
    {
        return usernameField.getText();
    }
    
    public String getPassword()
    {
        return new String(passwordField.getPassword());
    }

    
    // Code below this line is generated code.
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        userIdLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        usernameField = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        forgotPasswordButton = new javax.swing.JButton();
        createAccountButton = new javax.swing.JButton();
        deleteAccountButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Personal Health Tracker Login");

        userIdLabel.setText("Username:");

        passwordLabel.setText("Password:");

        loginButton.setText("Login");

        forgotPasswordButton.setText("Forgot Password");

        createAccountButton.setText("Create Account");

        deleteAccountButton.setText("Delete Account");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteAccountButton)
                    .addComponent(createAccountButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(passwordLabel)
                                .addComponent(userIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(loginButton)
                                .addGap(9, 9, 9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(forgotPasswordButton))
                .addContainerGap(175, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userIdLabel)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(forgotPasswordButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createAccountButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteAccountButton)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createAccountButton;
    private javax.swing.JButton deleteAccountButton;
    private javax.swing.JButton forgotPasswordButton;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel userIdLabel;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
