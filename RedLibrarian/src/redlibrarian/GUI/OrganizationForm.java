/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI;

import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.InputVerifier;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import redlibrarian.GUI.textVerification.ContiguityVerifier;
import redlibrarian.GUI.textVerification.EmailVerifier;
import redlibrarian.GUI.textVerification.PasswordVerifier;
import redlibrarian.RedLibrarian;
import redlibrarian.login.ContactDetails;
import redlibrarian.music.Organization;

/**
 *
 * @author admir
 */
public class OrganizationForm extends javax.swing.JDialog {

    private final SessionFactory sessionFactory;
    
    private boolean saved;
    private boolean checkMatch;
    private boolean match;
    
    private String orgName;
    private String name;
    private String email;
    private String password;
    
    private int index;
    
    private final String [] labels = {"Organization Name","Your Name","Email Address","Password","Confirm Password"};
    private final String [] tip_labels = {"Organization name should have no spaces.","","Ex. - John@example.com","Password should be 4 or more characters.","Passwords should match exactly."};
    private final String [] button_labels = {"Next >>","Next >>","Next >>","Next >>","Create Account"};
    private final InputVerifier [] verifiers = {new ContiguityVerifier(), null, new EmailVerifier(), new PasswordVerifier(), new PasswordVerifier()};
    private final String [] text = new String [5];
    /**
     * Creates new form OrganizationForm
     * @param sessionFactory
     * @param parent
     * @param modal
     */
    public OrganizationForm(SessionFactory sessionFactory,java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setIconImage(RedLibrarian.icon);
        this.sessionFactory = sessionFactory;
        
        availability_label.setVisible(false);
        tip_label.setForeground(Color.GRAY);
        
        password_field.setVisible(false);
        password_field.setInputVerifier(new PasswordVerifier());
        
        index = 0;
        
        this.changeIndex(0);
    }
    
    private void changeIndex(int diff) {
        if(index >= labels.length-2)
            text[index] = password_field.getText();
        else
            text[index] = field.getText();
        
        index+=diff;
        
        if(index < 0)
            index=0;
        if(index > labels.length-1)
            index=labels.length-1;
        
        field.setText(text[index]);
        password_field.setText(text[index]);
        
        if(index == 0)
            back_button.setVisible(false);
        else
            back_button.setVisible(true);
            
        
        if(index >= labels.length-2) {
            password_field.setVisible(true);
            field.setVisible(false);
        }
        else {
            password_field.setVisible(false);
            field.setVisible(true);
        }
        
        checkMatch = index == labels.length-1;  
        
        if(text[0]!=null)
            orgName = text[0].trim();
        if(text[1]!=null)
            name = text[1].trim();
        if(text[2]!=null)
            email = text[2].trim();
        if(text[3]!=null)
            password = text[3].trim();
                
        prompt_label.setText(labels[index]);
        tip_label.setText(tip_labels[index]);
        forward_button.setText(button_labels[index]);
        field.setInputVerifier(verifiers[index]);
    }
    
    public Organization getOrganization(){
        return new Organization(orgName, password);
    }
    
    public ContactDetails getContact() {
        return new ContactDetails(name, email);
    }
    
    public boolean wasSaved() {
        return saved;
    }

    private void processKeyPress(java.awt.event.KeyEvent evt) {
        if (evt.getKeyChar() == (KeyEvent.VK_ENTER)) {
            KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            
            if(manager.getFocusOwner().equals(forward_button))
                forwardButton();
            else
                manager.focusNextComponent();
        }
    }
    
    private void forwardButton() {
        
        this.changeIndex(0);
        availability_label.setVisible(false);
                
        if(index == labels.length-1) {
            saved = true;
            this.setVisible(false);
        }
        else if(index != 0)
            this.changeIndex(1);
        else {
            availability_label.setText("Checking availability...");
            availability_label.setForeground(Color.BLACK);
            availability_label.setVisible(true);
            
            boolean available = false;
            
            try {
                
                Session session = sessionFactory.getCurrentSession();
                session.beginTransaction();

                SQLQuery query = session.createSQLQuery("SELECT * FROM Organization WHERE NAME='"+orgName+"'");
                query.addEntity(Organization.class);

                Organization testOrg = (Organization) query.list().get(0);
                
            } catch(Exception hibernateException) {
                System.out.println(hibernateException);
                if(hibernateException.toString().contains("IndexOutOfBoundsException")) {
                    availability_label.setVisible(false);
                    available = true;
                }                    
            }
            
            if(available)
                this.changeIndex(1);
            else {
                availability_label.setText("Name is not available!");    
                availability_label.setForeground(Color.RED);
            }
                
            
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

        prompt_label = new javax.swing.JLabel();
        field = new javax.swing.JTextField();
        forward_button = new javax.swing.JButton();
        back_button = new javax.swing.JButton();
        tip_label = new javax.swing.JLabel();
        password_field = new javax.swing.JPasswordField();
        availability_label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        prompt_label.setText("LABEL");

        field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                fieldCaretUpdate(evt);
            }
        });
        field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fieldKeyPressed(evt);
            }
        });

        forward_button.setText("Next >>");
        forward_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forward_buttonActionPerformed(evt);
            }
        });
        forward_button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                forward_buttonKeyPressed(evt);
            }
        });

        back_button.setText("<< Back");
        back_button.setFocusable(false);
        back_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_buttonActionPerformed(evt);
            }
        });

        tip_label.setText("TOOLTIP");

        password_field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                password_fieldCaretUpdate(evt);
            }
        });
        password_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                password_fieldKeyPressed(evt);
            }
        });

        availability_label.setText("AVAILABILITY");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(back_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(forward_button))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(prompt_label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(availability_label))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(field, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tip_label)
                            .addComponent(password_field, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prompt_label)
                    .addComponent(availability_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(password_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tip_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(forward_button)
                    .addComponent(back_button))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void forward_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forward_buttonActionPerformed
        forwardButton();
    }//GEN-LAST:event_forward_buttonActionPerformed

    private void fieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_fieldCaretUpdate
        System.out.println(field.getText()+" "+(field.getText() != null)+" && "+(field.getText().length() > 0));
        if(field.getText() != null && field.getText().length() > 0)
            forward_button.setEnabled(field.getInputVerifier() == null || field.getInputVerifier().verify(field));
        else
            forward_button.setEnabled(false);      
    }//GEN-LAST:event_fieldCaretUpdate

    private void back_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_buttonActionPerformed
        this.changeIndex(-1);
    }//GEN-LAST:event_back_buttonActionPerformed

    private void password_fieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_password_fieldCaretUpdate
        System.out.println(text[labels.length-2]+" match "+password_field.getText());
        if(checkMatch) {
            match = text[labels.length-2].equals(password_field.getText()) ;
            forward_button.setEnabled((password_field.getInputVerifier() == null || password_field.getInputVerifier().verify(password_field)) && match);
        } 
        else {
            if(password_field.getText() != null && password_field.getText().length() > 0)
                forward_button.setEnabled(password_field.getInputVerifier() == null || password_field.getInputVerifier().verify(password_field));
            else
                forward_button.setEnabled(false);
        }
    }//GEN-LAST:event_password_fieldCaretUpdate

    private void fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldKeyPressed
        processKeyPress(evt);
    }//GEN-LAST:event_fieldKeyPressed

    private void password_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_password_fieldKeyPressed
        processKeyPress(evt);
    }//GEN-LAST:event_password_fieldKeyPressed

    private void forward_buttonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_forward_buttonKeyPressed
        processKeyPress(evt);
    }//GEN-LAST:event_forward_buttonKeyPressed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrganizationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            OrganizationForm dialog = new OrganizationForm(null, new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel availability_label;
    private javax.swing.JButton back_button;
    private javax.swing.JTextField field;
    private javax.swing.JButton forward_button;
    private javax.swing.JPasswordField password_field;
    private javax.swing.JLabel prompt_label;
    private javax.swing.JLabel tip_label;
    // End of variables declaration//GEN-END:variables

}
