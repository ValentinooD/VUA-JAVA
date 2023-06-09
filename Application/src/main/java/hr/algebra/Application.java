package hr.algebra;

import com.formdev.flatlaf.FlatDarkLaf;
import hr.algebra.dal.DatabaseFactory;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import hr.algebra.model.MoviesTableModel;
import hr.algebra.model.Role;
import hr.algebra.model.User;
import hr.algebra.rss.RSSParser;
import hr.algebra.utilities.IconUtils;
import hr.algebra.utilities.MessageUtils;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import hr.algebra.dal.IDatabase;
import hr.algebra.dal.repos.IUserRepository;
import hr.algebra.utilities.FileUtils;
import hr.algebra.utilities.JAXBUtils;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Collection;
import java.util.Optional;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

public class Application extends javax.swing.JFrame {

    private User user;
    private final IDatabase database;
    
    private MoviesTableModel tblModel;
    private List<Movie> movies;
    
    private Movie selectedMovie;
    private DefaultListModel<Actor> listModelActors;
    private DefaultListModel<Director> listModelDirectors;
    
    public Application(User user) {
        this.user = user;
        this.database = DatabaseFactory.getDatabase();
        this.listModelActors = new DefaultListModel<>();
        this.listModelDirectors = new DefaultListModel<>();
        this.tblModel = new MoviesTableModel(new ArrayList<>());
        
        initComponents();
        
        this.tblMovies.setModel(tblModel);
        this.lsActors.setModel(listModelActors);
        this.lsDirectors.setModel(listModelDirectors);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfTitle = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taDescription = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMovies = new javax.swing.JTable();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        tfPicturePath = new javax.swing.JTextField();
        btnIconPath = new javax.swing.JButton();
        lbBanner = new javax.swing.JLabel();
        btnAddPerson = new javax.swing.JButton();
        btnRemovePerson = new javax.swing.JButton();
        tpPeople = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        lsActors = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        lsDirectors = new javax.swing.JList<>();
        btnClear = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tfLink = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();
        mFile = new javax.swing.JMenu();
        miLogout = new javax.swing.JMenuItem();
        miExportToXML = new javax.swing.JMenuItem();
        miExit = new javax.swing.JMenuItem();
        mAdmin = new javax.swing.JMenu();
        miUserMng = new javax.swing.JMenuItem();
        miFillDatabase = new javax.swing.JMenuItem();
        miClearDatabase = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java project");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Title:");

        taDescription.setColumns(20);
        taDescription.setLineWrap(true);
        taDescription.setRows(5);
        jScrollPane2.setViewportView(taDescription);

        jLabel2.setText("Description:");

        tblMovies.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblMovies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMoviesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMovies);

        btnSave.setBackground(new java.awt.Color(0, 102, 0));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 102, 102));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        tfPicturePath.setEditable(false);

        btnIconPath.setText("...");
        btnIconPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIconPathActionPerformed(evt);
            }
        });

        lbBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/no_icon.png"))); // NOI18N

        btnAddPerson.setText("+");
        btnAddPerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPersonActionPerformed(evt);
            }
        });

        btnRemovePerson.setText("-");
        btnRemovePerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemovePersonActionPerformed(evt);
            }
        });

        lsActors.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(lsActors);

        tpPeople.addTab("Actors", jScrollPane4);

        jScrollPane5.setViewportView(lsDirectors);

        tpPeople.addTab("Directors", jScrollPane5);

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jLabel3.setText("Link:");

        mFile.setText("File");

        miLogout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        miLogout.setText("Logout");
        miLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miLogoutActionPerformed(evt);
            }
        });
        mFile.add(miLogout);

        miExportToXML.setText("Export to XML");
        miExportToXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExportToXMLActionPerformed(evt);
            }
        });
        mFile.add(miExportToXML);

        miExit.setText("Exit");
        miExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExitActionPerformed(evt);
            }
        });
        mFile.add(miExit);

        menuBar.add(mFile);

        mAdmin.setText("Admin");

        miUserMng.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        miUserMng.setText("User manaer");
        miUserMng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miUserMngActionPerformed(evt);
            }
        });
        mAdmin.add(miUserMng);

        miFillDatabase.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        miFillDatabase.setText("Fill database");
        miFillDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFillDatabaseActionPerformed(evt);
            }
        });
        mAdmin.add(miFillDatabase);

        miClearDatabase.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        miClearDatabase.setText("Clear database");
        miClearDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miClearDatabaseActionPerformed(evt);
            }
        });
        mAdmin.add(miClearDatabase);

        menuBar.add(mAdmin);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tpPeople)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfTitle, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAddPerson)
                                    .addComponent(btnRemovePerson))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(tfPicturePath)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnIconPath))
                                    .addComponent(lbBanner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(171, 171, 171)
                                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(tfLink))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAddPerson)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRemovePerson))
                            .addComponent(tpPeople, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbBanner)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnIconPath)
                            .addComponent(tfPicturePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnSave)
                    .addComponent(btnClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (user.getRole() == Role.ADMIN) {
            mAdmin.setVisible(true);
        } else {
            mAdmin.setVisible(false);
        }

        // Make it not resizable cause it keeps messing stuff up
        tfPicturePath.setMinimumSize(tfPicturePath.getSize());
        tfPicturePath.setMaximumSize(tfPicturePath.getSize());

        pack();
        setMinimumSize(getSize());
        
        initPicture();
        
        new Thread(() -> {
            setLoading(true);
            loadTable();
            loadActorList();
            loadDirectorList();
            setLoading(false);
        }).start();
    }//GEN-LAST:event_formWindowOpened

    private void miLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miLogoutActionPerformed
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dispose();
        
        user = null;
        
        handleLogin();
    }//GEN-LAST:event_miLogoutActionPerformed

    private void miExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExitActionPerformed
        dispose();
    }//GEN-LAST:event_miExitActionPerformed

    private void miUserMngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miUserMngActionPerformed
        UserManagementForm form = new UserManagementForm(user, (IUserRepository<User>) database.getRepository(User.class));
        
        form.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setEnabled(true);
                requestFocus();
            }
        });
        setEnabled(false);
        form.setVisible(true);
    }//GEN-LAST:event_miUserMngActionPerformed

    private void miFillDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFillDatabaseActionPerformed
        new Thread(() -> {
            try {
                setLoading(true);
                
                for (Movie movie : RSSParser.getItems()) {
                    database.getRepository(Movie.class).create(movie);
                }
                
                movies = new ArrayList<>(database.getRepository(Movie.class).selectMultiple());
                
                loadTable();
                setLoading(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                MessageUtils.showErrorMessage("Repository error", "Failed to load movies");
                setLoading(false);
            }
        }).start();
    }//GEN-LAST:event_miFillDatabaseActionPerformed

    private void miClearDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miClearDatabaseActionPerformed
        try {
            database.clearDatabase();
            
            loadTable();
            selectMovie(null);
        } catch (Exception ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_miClearDatabaseActionPerformed

    private void tblMoviesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMoviesMouseClicked
        
        if (tblModel == null || tblMovies.getSelectedRow() == -1)
            return;
        
        selectMovie(tblModel.getMovies().get(tblMovies.getSelectedRow()));
    }//GEN-LAST:event_tblMoviesMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        if (selectedMovie == null) return;
        
        selectedMovie.setTitle(tfTitle.getText());
        selectedMovie.setDescription(taDescription.getText());
        selectedMovie.setBannerPath(tfPicturePath.getText());
        selectedMovie.setLink(tfLink.getText());
        
        try {
            database.getRepository(Movie.class).update(selectedMovie.getId(), selectedMovie);
            selectMovie(selectedMovie); // refresh
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        if (selectedMovie == null) return;
        
        if (!MessageUtils.showConfirmDialog("Delete?", "Are you sure you want to delete this movie?")) {
            return;
        }
        
        try {
            database.getRepository(Movie.class).delete(selectedMovie.getId());
            
            tblModel.getMovies().remove(selectedMovie);
            
            tblModel.fireTableDataChanged();
            tblMovies.setModel(tblModel);
            
            selectMovie(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddPersonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPersonActionPerformed
        
        if (selectedMovie == null) return;
        
        setEnabled(false);
        CreatePersonForm form = new CreatePersonForm() {
            @Override
            public void create(String firstName, String lastName) {
                if (tpPeople.getSelectedIndex() == 0) {
                    selectedMovie.getActors().add(new Actor(firstName, lastName));
                } else {
                    selectedMovie.getDirectors().add(new Director(firstName, lastName));
                }
                
                loadActorList();
                loadDirectorList();
            }
        };

        form.setVisible(true);

        form.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setEnabled(true);
                
                requestFocus();
            }
        });
    }//GEN-LAST:event_btnAddPersonActionPerformed

    private void btnRemovePersonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemovePersonActionPerformed
        
        if (tpPeople.getSelectedIndex() == 0) {
            if (lsActors.getSelectedValue() == null) return;
            
            selectedMovie.getActors().remove(lsActors.getSelectedValue());
        } else {
            if (lsDirectors.getSelectedValue() == null) return;
            
            selectedMovie.getDirectors().remove(lsDirectors.getSelectedValue());
        }
        
        loadActorList();
        loadDirectorList();
    }//GEN-LAST:event_btnRemovePersonActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        selectMovie(selectedMovie);
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnIconPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIconPathActionPerformed
        if (selectedMovie == null) return;
        
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.showOpenDialog(this);
        
        if (chooser.getSelectedFile() == null) return;
        
        try {
            selectedMovie.setBannerPath(chooser.getSelectedFile().getAbsolutePath());
            
            tfPicturePath.setText(selectedMovie.getBannerPath());
            lbBanner.setIcon(
                            IconUtils.createIcon(new File(selectedMovie.getBannerPath()), 
                                    lbBanner.getPreferredSize().width,
                                    lbBanner.getPreferredSize().height));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnIconPathActionPerformed

    private void miExportToXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExportToXMLActionPerformed
        try {
            final Optional<File> file = FileUtils.saveFileDialog();

            if (file.isEmpty()) return;

            setLoading(true);

            new Thread(() -> {
                try {
                    Collection<Movie> movies = database.getRepository(Movie.class).selectMultiple();

                    EncapsulatedCollection e = new EncapsulatedCollection(movies);
                    JAXBUtils.save(e, file.get().getAbsolutePath());
                    
                    setLoading(false);

                    Runtime.getRuntime().exec("explorer.exe /select," + file.get().getAbsolutePath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    MessageUtils.showErrorMessage("Error saving file", "File couldn't be saved");
                    setLoading(false);
                }
            }).start();
        
        } catch (Exception ex) {
            ex.printStackTrace();
            MessageUtils.showErrorMessage("Error saving file", "File couldn't be saved");
            setLoading(false);
        }
    }//GEN-LAST:event_miExportToXMLActionPerformed

    private static boolean checkConnection(IDatabase repository) {
        if (!repository.isConnected()) {
            MessageUtils.showErrorMessage("Connection Error", "A connection to the database could not be established.");
            
            return false;
        }
        return true; 
   }
    
    private static void handleLogin() {
        IDatabase repository = DatabaseFactory.getDatabase();
        
        if (!checkConnection(repository)) {
            System.exit(1);
            return;
        }
        
        LoginFrame loginFrame = new LoginFrame((IUserRepository<User>) repository.getRepository(User.class));
        loginFrame.setVisible(true);
        
        loginFrame.setCallback(user -> {
            if (user == null) {
                MessageUtils.showErrorMessage("Login error", "You must login to use this application.");
                return;
            }
            
            new Application(user).setVisible(true);
        });
    }
    
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                handleLogin();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPerson;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnIconPath;
    private javax.swing.JButton btnRemovePerson;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbBanner;
    private javax.swing.JList<Actor> lsActors;
    private javax.swing.JList<Director> lsDirectors;
    private javax.swing.JMenu mAdmin;
    private javax.swing.JMenu mFile;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem miClearDatabase;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miExportToXML;
    private javax.swing.JMenuItem miFillDatabase;
    private javax.swing.JMenuItem miLogout;
    private javax.swing.JMenuItem miUserMng;
    private javax.swing.JTextArea taDescription;
    private javax.swing.JTable tblMovies;
    private javax.swing.JTextField tfLink;
    private javax.swing.JTextField tfPicturePath;
    private javax.swing.JTextField tfTitle;
    private javax.swing.JTabbedPane tpPeople;
    // End of variables declaration//GEN-END:variables

    private void loadTable() {
        try {
            movies = new ArrayList<>(database.getRepository(Movie.class).selectMultiple());
            
            tblModel = new MoviesTableModel(movies);
            
            tblMovies.setModel(tblModel);
            tblMovies.setColumnSelectionAllowed(false);
            tblMovies.setDragEnabled(false);
            tblMovies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            if (movies.isEmpty()) {
                selectMovie(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadActorList() {
        listModelActors.clear();
        
        if (selectedMovie != null) {
            selectedMovie.getActors().stream().forEach(listModelActors::addElement);
        }
        
        lsActors.setModel(listModelActors);
    }

    private void loadDirectorList() {
        listModelDirectors.clear();
        
        if (selectedMovie != null) {
            selectedMovie.getDirectors().stream().forEach(listModelDirectors::addElement);
        }
        
        lsDirectors.setModel(listModelDirectors);
    }
    
    private void initPicture() {
        try {
            File file = new File(getClass().getResource("/assets/no_icon.png").toURI());
            
            lbBanner.setIcon(
                    IconUtils.createIcon(file, 
                            lbBanner.getPreferredSize().width,
                            lbBanner.getPreferredSize().height));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Container defaultContainer;
    private void setLoading(boolean state) {
        if (state) {
            // only set first time
            if (defaultContainer == null) 
                defaultContainer = getContentPane();
            
            JPanel pnl = new JPanel();
            JLabel lb = new JLabel("Loading...");
            lb.setFont(new Font("Arial", Font.BOLD, 24));
            pnl.add(lb, BorderLayout.CENTER);
            
            setContentPane(pnl);
            
            menuBar.setVisible(false);
        } else {
            setContentPane(defaultContainer);
            menuBar.setVisible(true);
        }
        
        revalidate();
        repaint();
    }

    private void selectMovie(Movie selectedMovie) {
        if (selectedMovie == null) {
            this.selectedMovie = null;
            
            tfTitle.setText("");
            taDescription.setText("");
            tfPicturePath.setText("");
            tfLink.setText("");
            initPicture();
            
            loadActorList();
            loadDirectorList();
            return;
        }
        
        try {
            // refresh with new info from database
            this.selectedMovie = database.getRepository(Movie.class).select(selectedMovie.getId()).get();

            tfTitle.setText(selectedMovie.getTitle());
            taDescription.setText(selectedMovie.getDescription());
            tfPicturePath.setText(selectedMovie.getBannerPath());
            tfLink.setText(selectedMovie.getLink());

            try {
                lbBanner.setIcon(
                            IconUtils.createIcon(new File(selectedMovie.getBannerPath()), 
                                    lbBanner.getPreferredSize().width,
                                    lbBanner.getPreferredSize().height));
            } catch (Exception ex) {
                ex.printStackTrace();
                initPicture(); // set default
            }

            loadActorList();
            loadDirectorList();
        } catch (Exception ex) {
            ex.printStackTrace();
            
            selectMovie(null);
        }
    }
       
    @XmlRootElement(name = "list")
    private static class EncapsulatedCollection {
        @XmlElementWrapper
        @XmlElement(name = "movies")
        private Collection<Movie> collection;

        public EncapsulatedCollection() {
        }
        
        public EncapsulatedCollection(Collection<Movie> collection) {
            this.collection = collection;
        }

        public Collection<Movie> getCollection() {
            return collection;
        }
    }
}
