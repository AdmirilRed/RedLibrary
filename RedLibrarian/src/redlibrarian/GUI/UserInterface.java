/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.ini4j.Ini;
import static redlibrarian.RedLibrarian.sessionFactory;
import redlibrarian.music.Library;
import redlibrarian.music.Organization;
import redlibrarian.music.Performance;
import redlibrarian.music.Song;
import utility.Search;

/**
 *
 * @author admir
 */
public class UserInterface extends javax.swing.JFrame {

    private final String URL = "jdbc:mysql://redlibrarian.ciwuxwonopze.us-east-1.rds.amazonaws.com:3306/RedLibrarian";
    private final String connectionUsername = "client";
    private final String connectionPassword = "KrNestsS+4_-J+zU";
    
    private Organization currentOrganization;
    private boolean admin;
    
    private Song selectedSong;
    private Performance selectedPerformance;
    
    private Ini ini;
    private URL iniURL;
    //Initializing config elements as default
    String suggestedOrganizationName = "";
    boolean showAdminLogin = true; 
    
    TabbedLibraryPane tabbedLibrary_pane;
    
    /**
     * Creates new form UserInterface
     */
    public UserInterface() {
        initComponents();
        details_panel.setVisible(false);
        
    }

    public UserInterface(URL url) {
        this();
        this.configure(url);
    }

    public boolean load() {
                
        try {
            if (sessionFactory == null) {
                Configuration cfg = new Configuration();
                cfg.configure("hibernate.cfg.xml"); //hibernate config xml file name
                cfg.getProperties().setProperty("hibernate.connection.password", connectionPassword);
                cfg.getProperties().setProperty("hibernate.connection.username", connectionUsername);
                cfg.getProperties().setProperty("hibernate.connection.url", URL);
                sessionFactory = cfg.buildSessionFactory();
            }
        } catch (HibernateException hibernateException) {
            Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, hibernateException);
            Integer errorCode = 997;
            CrashDialogue crash = new CrashDialogue("Database connection could not be established. "
            +"Please ensure that a valid internet connection is available.", errorCode.toString(), this);
            crash.setVisible(true);
            System.exit(errorCode);
            return false;
        }
        System.out.println("SUCCESS");
        
        currentOrganization = this.login(true);
        deleteSong_button.setEnabled(admin);
        editSong_button.setEnabled(admin);
        editPerformance_button.setEnabled(false);
        new_menu.setEnabled(admin);  
                
        this.setTitle("RedLibrarian - "+currentOrganization.getName()+" "+(admin?"(ADMIN)":"(GUEST)"));
        loadLibraries();
        
        return true;
    }
    
    private Organization login(boolean modal) {
        
        LoginForm prompt = new LoginForm(this, modal, sessionFactory);
        prompt.setOrganizationText(suggestedOrganizationName);
        prompt.setShowAdminLogin(showAdminLogin);
        prompt.setVisible(true);
        
        if(!prompt.isLoggedIn()) {
            prompt.dispose();
            System.exit(998);
        }  
            
        Organization org = prompt.getOrganization(); 
        this.admin = prompt.isAdmin();
        
        if(ini != null) {
           ini.put("preferences", "showAdminLogin", !prompt.getGuestLoginState());
           ini.put("preferences", "suggestedOrganizationName", prompt.getOrganization().getName());
            try {
                ini.store(new File(iniURL.toURI()));
            } catch (IOException | URISyntaxException ex) {
                Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        prompt.dispose();
        return org;
    }
    
    private void loadLibraries() {
        System.out.println("loadLibraries()");
        tabbedLibrary_pane = new TabbedLibraryPane(currentOrganization, this);
        tabbedRoot_pane.add("Libraries", tabbedLibrary_pane);
    }
    
    private void loadPerformances(Song song) {
        
        System.out.println("loadPerformances("+song+")");
        
        DefaultTreeModel model = (DefaultTreeModel) performances_tree.getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model.getRoot();
        rootNode.removeAllChildren();
        
        for(Performance perf:currentOrganization.getPerformances()) {
            if(perf.getPlaylist().contains(song)) {
                PerformanceTreeNode parentNode = new PerformanceTreeNode(perf);
                rootNode.add(parentNode);
                for(Song s:perf.getPlaylist()) {
                    SongTreeNode childNode = new SongTreeNode(s);
                    parentNode.add(childNode);
                }
            }
        }
        performances_tree.repaint();
        model.reload();
    }
    
    void updateSelection(Song song) {
        
        System.out.println("updateSelection("+song+")");
        
        this.selectedSong = song;
        
        id_label.setText(song.getPseudoId()+"");
        uid_label.setText("(Unique ID: "+song.getUniqueId()+")");
        title_label.setText(song.getTitle());
        composer_label.setText(song.getComposer());
        date_label.setText(String.format("%tF",song.getDateAdded()));
        library_label.setText(tabbedLibrary_pane.getTitleAt(tabbedLibrary_pane.getSelectedIndex()));
        description_textArea.setText(song.getDescription());
        details_panel.setVisible(true);
        
        this.loadPerformances(song);
        
    }
    
    private void configure(URL url) {
        try {
            this.iniURL = url;
            ini = new Ini(url);
            this.showAdminLogin = ini.get("preferences", "showAdminLogin", boolean.class);
            this.suggestedOrganizationName = ini.get("preferences", "suggestedOrganizationName", String.class);
        } catch (IOException ex) {
            Logger.getLogger(UserInterface.class.getName()).log(Level.CONFIG, null, ex);
        }
    }    
    
    private void search() {
        search_button.setEnabled(false);
        for(int x=0;x<tabbedRoot_pane.getTabCount();x++)
            if(tabbedRoot_pane.getTitleAt(x).equals("Search Results"))
                tabbedRoot_pane.remove(x);
        
        Search s = new Search(search_field.getText(), currentOrganization);
        LibraryPane panel = new LibraryPane(s.getSongResults(), this);
        tabbedRoot_pane.add(panel, "Search Results");
        tabbedRoot_pane.setSelectedComponent(panel);
        
        if(details_panel.isVisible())
            panel.selectSong(0);
        search_button.setEnabled(true);
    }
    
    private void processKeyPress(KeyEvent evt) {
        if (evt.getKeyChar() == (KeyEvent.VK_ENTER)) {
            KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            manager.focusNextComponent();
            
            if(manager.getFocusOwner().equals(search_button))
                search();
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

        jMenuItem1 = new javax.swing.JMenuItem();
        tabbedRoot_pane = new javax.swing.JTabbedPane();
        details_panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        hide_button = new javax.swing.JButton();
        id_label = new javax.swing.JLabel();
        title_label = new javax.swing.JLabel();
        composer_label = new javax.swing.JLabel();
        library_label = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        description_textArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        date_label = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        performances_tree = new javax.swing.JTree();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        deleteSong_button = new javax.swing.JButton();
        editSong_button = new javax.swing.JButton();
        uid_label = new javax.swing.JLabel();
        editPerformance_button = new javax.swing.JButton();
        search_button = new javax.swing.JButton();
        search_field = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        file_menu = new javax.swing.JMenu();
        new_menu = new javax.swing.JMenu();
        newSong_menuItem = new javax.swing.JMenuItem();
        newLibrary_menuItem = new javax.swing.JMenuItem();
        newPerformance_menuItem = new javax.swing.JMenuItem();
        edit_menu = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RedLibrarian");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        details_panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Details");

        hide_button.setText("Hide");
        hide_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hide_buttonActionPerformed(evt);
            }
        });

        id_label.setText("id");

        title_label.setText("title");

        composer_label.setText("composer");

        library_label.setText("library");

        description_textArea.setColumns(20);
        description_textArea.setRows(5);
        jScrollPane1.setViewportView(description_textArea);

        jLabel2.setText("DESCRIPTION:");

        date_label.setText("date");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Performances");
        performances_tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        performances_tree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                performances_treeValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(performances_tree);

        jLabel3.setText("ID:");

        jLabel4.setText("TITLE:");

        jLabel5.setText("COMPOSER:");

        jLabel6.setText("LIBRARY:");

        jLabel7.setText("DATE ADDED:");

        deleteSong_button.setText("Delete Song");
        deleteSong_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSong_buttonActionPerformed(evt);
            }
        });

        editSong_button.setText("Edit Song");
        editSong_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editSong_buttonActionPerformed(evt);
            }
        });

        uid_label.setText("(Unique ID: )");

        editPerformance_button.setText("Edit Performance");

        javax.swing.GroupLayout details_panelLayout = new javax.swing.GroupLayout(details_panel);
        details_panel.setLayout(details_panelLayout);
        details_panelLayout.setHorizontalGroup(
            details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(details_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteSong_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(details_panelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hide_button))
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                    .addComponent(editSong_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(details_panelLayout.createSequentialGroup()
                        .addGroup(details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(details_panelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(title_label))
                            .addGroup(details_panelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(composer_label))
                            .addGroup(details_panelLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(library_label))
                            .addGroup(details_panelLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(date_label))
                            .addGroup(details_panelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(id_label)
                                .addGap(18, 18, 18)
                                .addComponent(uid_label))
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(editPerformance_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        details_panelLayout.setVerticalGroup(
            details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(details_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(hide_button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id_label)
                    .addComponent(jLabel3)
                    .addComponent(uid_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(title_label)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(composer_label)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(library_label)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(date_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editPerformance_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editSong_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteSong_button)
                .addContainerGap())
        );

        search_button.setText("Search");
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });
        search_button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_buttonKeyPressed(evt);
            }
        });

        search_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_fieldKeyPressed(evt);
            }
        });

        file_menu.setText("File");

        new_menu.setText("New");

        newSong_menuItem.setText("Song");
        newSong_menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newSong_menuItemActionPerformed(evt);
            }
        });
        new_menu.add(newSong_menuItem);

        newLibrary_menuItem.setText("Library");
        newLibrary_menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newLibrary_menuItemActionPerformed(evt);
            }
        });
        new_menu.add(newLibrary_menuItem);

        newPerformance_menuItem.setText("Performance");
        newPerformance_menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPerformance_menuItemActionPerformed(evt);
            }
        });
        new_menu.add(newPerformance_menuItem);

        file_menu.add(new_menu);

        jMenuBar1.add(file_menu);

        edit_menu.setText("Edit");
        jMenuBar1.add(edit_menu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(details_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tabbedRoot_pane, javax.swing.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(search_field, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_button)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search_button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbedRoot_pane, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE)
                    .addComponent(details_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hide_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hide_buttonActionPerformed
        details_panel.setVisible(false);
    }//GEN-LAST:event_hide_buttonActionPerformed

    private void deleteSong_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSong_buttonActionPerformed
        if(admin) {
            if(JOptionPane.showConfirmDialog(null,
                "Are you sure you wish to delete "+selectedSong+"?", "Delete "+selectedSong+"?", JOptionPane.YES_NO_OPTION) == 0) {
                LibraryPane pane = (LibraryPane) tabbedLibrary_pane.getSelectedComponent();
                
                Song oldSong = selectedSong;
                Library oldLib = selectedSong.getLibrary();
                    
                currentOrganization.removeSong(selectedSong); 
                pane.removeSong(selectedSong);

                try {
                    
                    Session session = sessionFactory.getCurrentSession();
                    session.beginTransaction();
                    
                    session.update(currentOrganization);
                    session.update(oldLib);

                    session.delete(oldSong);

                    session.getTransaction().commit();
                }
                catch(HibernateException hibernateException) {
                    Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, hibernateException);
                }
            }
        }
    }//GEN-LAST:event_deleteSong_buttonActionPerformed

    private void editSong_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editSong_buttonActionPerformed
        if(admin) {
            SongForm form = new SongForm(selectedSong, currentOrganization.getLibraries(), this, true);
            form.setVisible(true);
            if(form.wasSaved()) {
                LibraryPane pane = (LibraryPane) tabbedLibrary_pane.getSelectedComponent();
                
                Song oldSong = selectedSong;
                Library oldLib = selectedSong.getLibrary();
                    
                currentOrganization.removeSong(selectedSong);
                currentOrganization.addSong(form.getSong());
                
                if(!oldLib.equals(form.getSong().getLibrary())) {
                    tabbedLibrary_pane.refresh(form.getSong().getLibrary());
                    tabbedLibrary_pane.selectSong(form.getSong());
                    tabbedLibrary_pane.refresh(oldLib);
                }
                else
                    pane.updateSong(oldSong, form.getSong());
       
                try {

                    Session session = sessionFactory.getCurrentSession();
                    session.beginTransaction();
                    
                    session.delete(oldSong);
 
                    session.save(form.getSong());
                    
                    session.update(currentOrganization);
                    session.update(form.getSong().getLibrary());
                    session.update(oldLib);

                    session.getTransaction().commit();
                    
                }
                catch(HibernateException hibernateException) {
                    Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, hibernateException);
                }      
                

            }
            form.dispose();
        }
    }//GEN-LAST:event_editSong_buttonActionPerformed

    private void newLibrary_menuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newLibrary_menuItemActionPerformed
        if(admin) {
            LibraryForm form = new LibraryForm(currentOrganization, this, true);
            form.setVisible(true);
            if(form.wasSaved()) {
                Library lib = form.getLibrary();
                tabbedLibrary_pane.addLibrary(lib, new LibraryPane(lib, this));
                currentOrganization.addLibrary(lib);
                try {
                    Session session = sessionFactory.getCurrentSession();
                    session.beginTransaction();

                    session.save(lib);
                    session.saveOrUpdate(currentOrganization);

                    session.getTransaction().commit();
                }
                catch(HibernateException hibernateException) {
                    Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, hibernateException);
                }
            }
            form.dispose();
        }
    }//GEN-LAST:event_newLibrary_menuItemActionPerformed

    private void newSong_menuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newSong_menuItemActionPerformed
        if(admin) {
            SongForm form = new SongForm(currentOrganization.getLibraries(), tabbedLibrary_pane.getCurrentLibrary(), this, true);
            form.setVisible(true);
            if(form.wasSaved()) {
                Song song = form.getSong();
                currentOrganization.addSong(song);

                try {
                    Session session = sessionFactory.getCurrentSession();
                    session.beginTransaction();

                    session.save(song);
                    session.update(currentOrganization);

                    session.getTransaction().commit();
                }
                catch(HibernateException hibernateException) {
                    Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, hibernateException);
                }

                tabbedLibrary_pane.refresh(song.getLibrary());
            }
        }
    }//GEN-LAST:event_newSong_menuItemActionPerformed

    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed
        search();
    }//GEN-LAST:event_search_buttonActionPerformed

    private void search_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_fieldKeyPressed
        processKeyPress(evt); 
    }//GEN-LAST:event_search_fieldKeyPressed

    private void search_buttonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_buttonKeyPressed
        processKeyPress(evt);
    }//GEN-LAST:event_search_buttonKeyPressed

    private void performances_treeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_performances_treeValueChanged
        
        Object obj = performances_tree.getLastSelectedPathComponent();
        
        if(obj != null) {
            
            if(obj.getClass().toString().equals(SongTreeNode.class.toString())) {
            
                Song song = (Song) ((SongTreeNode) obj).getSong();
                tabbedLibrary_pane.setSelectedIndex(tabbedLibrary_pane.getIndex(song.getLibrary()));
                ((LibraryPane) tabbedLibrary_pane.getSelectedComponent()).selectSong(song);
            }
            if(obj.getClass().toString().equals(PerformanceTreeNode.class.toString())) {
                
                Performance perf =  (Performance) ((PerformanceTreeNode) obj).getPerformance();
                this.selectedPerformance = perf;
                
                editPerformance_button.setEnabled(true && admin);
            }
            else
                editPerformance_button.setEnabled(false);
        } 
                
    }//GEN-LAST:event_performances_treeValueChanged

    private void newPerformance_menuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPerformance_menuItemActionPerformed
        if(admin) {
            PerformanceForm form = new PerformanceForm(currentOrganization, this, true);
            form.setVisible(true);
            if(form.wasSaved()) {
                Performance perf = form.getPerformance();
                currentOrganization.addPerformance(perf);
                
                try {
                    Session session = sessionFactory.getCurrentSession();
                    session.beginTransaction();
                    
                    session.save(perf);
                    session.update(currentOrganization);
                    
                    session.getTransaction().commit();
                }
                catch(HibernateException hibernateException) {
                    Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, hibernateException);
                }
            }
        }
    }//GEN-LAST:event_newPerformance_menuItemActionPerformed

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
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new UserInterface().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel composer_label;
    private javax.swing.JLabel date_label;
    private javax.swing.JButton deleteSong_button;
    private javax.swing.JTextArea description_textArea;
    private javax.swing.JPanel details_panel;
    private javax.swing.JButton editPerformance_button;
    private javax.swing.JButton editSong_button;
    private javax.swing.JMenu edit_menu;
    private javax.swing.JMenu file_menu;
    private javax.swing.JButton hide_button;
    private javax.swing.JLabel id_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel library_label;
    private javax.swing.JMenuItem newLibrary_menuItem;
    private javax.swing.JMenuItem newPerformance_menuItem;
    private javax.swing.JMenuItem newSong_menuItem;
    private javax.swing.JMenu new_menu;
    private javax.swing.JTree performances_tree;
    private javax.swing.JButton search_button;
    private javax.swing.JTextField search_field;
    private javax.swing.JTabbedPane tabbedRoot_pane;
    private javax.swing.JLabel title_label;
    private javax.swing.JLabel uid_label;
    // End of variables declaration//GEN-END:variables
   
}
