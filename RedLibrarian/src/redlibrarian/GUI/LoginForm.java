/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import redlibrarian.music.Organization;

/**
 *
 * @author admir
 */
@SuppressWarnings("serial")
public class LoginForm extends javax.swing.JDialog {

    private final SessionFactory sessionFactory;
    private boolean loggedIn = false;
    private boolean isAdmin = false;
    private Organization organization;
    
    /**
     * Creates new form LoginForm
     * @param parent
     * @param modal
     * @param sessionFactory
     */
    public LoginForm(java.awt.Frame parent, boolean modal, SessionFactory sessionFactory) {
        super(parent, modal);
        initComponents();
        loginStatus_label.setVisible(false);
        this.sessionFactory = sessionFactory;
    }
    
    public Organization getOrganization() {
        return organization;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }
    
    public boolean getGuestLoginState() {
        return guest_button.isSelected();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        organization_label = new javax.swing.JLabel();
        organization_field = new javax.swing.JTextField();
        password_label = new javax.swing.JLabel();
        password_field = new javax.swing.JPasswordField();
        login_button = new javax.swing.JButton();
        loginStatus_label = new javax.swing.JLabel();
        guest_button = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        organization_label.setText("Organization");

        password_label.setText("Password");

        login_button.setText("Login");
        login_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_buttonActionPerformed(evt);
            }
        });

        loginStatus_label.setText("LOGIN STATUS");

        guest_button.setText("Login as guest");
        guest_button.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                guest_buttonStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loginStatus_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 262, Short.MAX_VALUE)
                        .addComponent(login_button))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(password_label)
                            .addComponent(guest_button)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(organization_label, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(organization_field, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                        .addComponent(password_field)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(organization_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(organization_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(password_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(password_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(guest_button)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginStatus_label)
                    .addComponent(login_button))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void login_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_buttonActionPerformed
        if(organization == null) {
            
            login_button.setEnabled(false);
            
            try {
                Session session = sessionFactory.getCurrentSession();
                session.beginTransaction();
                
                SQLQuery query = session.createSQLQuery("SELECT * FROM Organization WHERE NAME='"+organization_field.getText()+"'");
                query.addEntity(Organization.class);
                
                Organization remoteOrg = (Organization) query.list().get(0);
                
                if(!guest_button.isSelected()) {
                    if(!remoteOrg.verifyPassword(password_field.getText()))
                        loginStatus_label.setText("Administrator password incorrect.");
                    else {
                        this.organization = remoteOrg;
                        this.loggedIn = true;
                        this.isAdmin = true;
                        this.setVisible(false);
                    }
                        
                } else {
                    this.organization = remoteOrg;
                    this.loggedIn = true;
                    this.setVisible(false);
                }
            } catch(Exception hibernateException) {
                System.out.println("LOGIN: "+hibernateException);
                if(hibernateException.toString().contains("NullPointerException")) {
                    loginStatus_label.setText("Organization does not exist.");
                }
                
                loginStatus_label.setVisible(true);
                    
            } finally {
                login_button.setEnabled(true);
            }
            
                
        }
    }//GEN-LAST:event_login_buttonActionPerformed

    private void guest_buttonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_guest_buttonStateChanged
        password_field.setVisible(!guest_button.isSelected()); 
        password_label.setVisible(!guest_button.isSelected());
    }//GEN-LAST:event_guest_buttonStateChanged

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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            LoginForm dialog = new LoginForm(new javax.swing.JFrame(), true, null);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox guest_button;
    private javax.swing.JLabel loginStatus_label;
    private javax.swing.JButton login_button;
    private javax.swing.JTextField organization_field;
    private javax.swing.JLabel organization_label;
    private javax.swing.JPasswordField password_field;
    private javax.swing.JLabel password_label;
    // End of variables declaration//GEN-END:variables

    /**
     *
     * @param showAdminLogin
     */
    public void setShowAdminLogin(boolean showAdminLogin) {
        guest_button.setSelected(!showAdminLogin);
    }

}
