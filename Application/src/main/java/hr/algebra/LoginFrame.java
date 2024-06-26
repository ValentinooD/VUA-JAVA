package hr.algebra;

import hr.algebra.model.User;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;
import hr.algebra.dal.repos.IUserRepository;
import java.awt.event.KeyEvent;

/**
 *
 * @author User
 */
public class LoginFrame extends javax.swing.JFrame {

    private User user;
    private IUserRepository<User> userRepo;

    private Callback<User> callback;
    
    public LoginFrame(IUserRepository<User> userRepo) {
        this.userRepo = userRepo;
        initComponents();
        
        clearErrors();
        btnLogin.setEnabled(false);
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbUsername = new javax.swing.JLabel();
        lbPassword = new javax.swing.JLabel();
        lbTitle = new javax.swing.JLabel();
        tfUsername = new javax.swing.JTextField();
        lbError = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnCreateAccount = new javax.swing.JButton();
        tfPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lbUsername.setText("Username:");

        lbPassword.setText("Password:");
        lbPassword.setPreferredSize(new java.awt.Dimension(56, 16));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbTitle.setText("Epic java project login panel");

        tfUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                loginInformationKeyReleased(evt);
            }
        });

        lbError.setForeground(new java.awt.Color(255, 0, 0));
        lbError.setText("[ERROR PLACEHOLDER]");

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnCreateAccount.setText("Create account");
        btnCreateAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateAccountActionPerformed(evt);
            }
        });

        tfPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                loginInformationKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbError, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lbUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfUsername))
                                .addComponent(lbTitle))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCreateAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(lbPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbUsername)
                    .addComponent(tfUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(lbError)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnCreateAccount))
                .addGap(35, 35, 35))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String username = tfUsername.getText();
        String password = new String(tfPassword.getPassword());
        
        try {
            Optional<User> optUser = userRepo.authenticate(username, password);

            if (optUser.isEmpty()) {
                setError("Incorrect username or password.");
                return;
            }

            user = optUser.get();
            dispose();
            
            if (callback != null) callback.onFinish(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            
            setError("Error occurred querying the database.");
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void loginInformationKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loginInformationKeyReleased
       btnLogin.setEnabled((!tfUsername.getText().isEmpty()) && (tfPassword.getPassword().length > 0));
       
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
           if (tfUsername.getText().isEmpty()) {
               tfUsername.requestFocus();
               return;
           }
           
           if (tfPassword.getPassword().length == 0) {
               tfPassword.requestFocus();
               return;
           }
           
            btnLogin.doClick();
       }
       
    }//GEN-LAST:event_loginInformationKeyReleased

    private void btnCreateAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateAccountActionPerformed
        String username = tfUsername.getText();
        String password = new String(tfPassword.getPassword());
        
        setEnabled(false);
        
        RegisterFrame frame = new RegisterFrame(userRepo, username.trim(), password);
        frame.setVisible(true);
        
        frame.setCallback(user -> {
            dispose();
            
            if (callback != null) callback.onFinish(user);
        });
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setEnabled(true);
            }
        });
    }//GEN-LAST:event_btnCreateAccountActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateAccount;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel lbError;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JPasswordField tfPassword;
    private javax.swing.JTextField tfUsername;
    // End of variables declaration//GEN-END:variables
    
    private void clearErrors() {
        setError(null);
    }
    
    private void setError(String message) {
        if (message == null) lbError.setVisible(false);
    
        lbError.setText(message);
        lbError.setVisible(true);
    }
    
    public User getUser() {
        return user;
    }

    public void setCallback(Callback<User> callback) {
        this.callback = callback;
    }
}
