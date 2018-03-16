/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orbitfind;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

class OpenFileFilter extends FileFilter {

    String description = "";
    String fileExt = "";

    public OpenFileFilter(String extension) {
        fileExt = extension;
    }

    public OpenFileFilter(String extension, String typeDescription) {
        fileExt = extension;
        this.description = typeDescription;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        return (f.getName().toLowerCase().endsWith(fileExt));
    }

    @Override
    public String getDescription() {
        return description;
    }
}

/**
 *
 * @author Lefos
 */
public class mainFrame extends javax.swing.JFrame {

    /**
     * Creates new form mainFrame
     */
    private List<JTextField> fieldList = new ArrayList<JTextField>();
    public String startHtml = "<html><p>Status: ";
    public String endHtml = "</p></html>";
    private DefaultTableModel olModel=null;

    private File prefsFile = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+"prefs");
    private File satDir = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+"satDir");
    
    private Preferences prefs;
    private String[] columnNames = {"Enable",
                                    "Satellite's Name",
                                    "File",
                                    "Area of site"};
      //private static final Logger LOGGER = Logger.getLogger("Simple Logger");

    /*
     static void log_init() {

     Handler fileHandler  = null;
     try{
     //Creating fileHandler
     fileHandler  = new FileHandler("./logs.log");
			
     //Assigning handlers to LOGGER object
     LOGGER.addHandler(fileHandler);
			
     //Setting levels to handlers and LOGGER
     fileHandler.setLevel(Level.INFO);
     LOGGER.setLevel(Level.INFO);
			
     LOGGER.config("Configuration done.");
			
     //Console handler removed
     //LOGGER.removeHandler(consoleHandler);
			
     LOGGER.log(Level.INFO, "Logger initialized");
     }catch(IOException exception){
     LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", exception);
     }
     LOGGER.info("done");
     }*/
    public mainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        seticon();

    }
    
    private void seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/orbitfind/images/Slide5_new1.png")));
    }
    
    public Double readTxt(String filename, String satName) throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = in.readLine()) != null) {
                String[] arguments = line.split(" ");
                if (arguments[0].equals(satName)) {
                    Double dd = Double.parseDouble(arguments[1]);
                    in.close();
                    return dd;
                }
            }
            in.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0.0;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        final String strURL = "http://senselab.tuc.gr/";
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        prefs = Preferences.userNodeForPackage(jDialog1.getClass());
        jDialog1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog1.setTitle("Preferences");
        jDialog1.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        jDialog1.setPreferredSize(new java.awt.Dimension(652, 306));
        jDialog1.setResizable(false);
        jDialog1.setSize(new java.awt.Dimension(652, 306));
        jDialog1.setType(java.awt.Window.Type.UTILITY);

        jTable1.setColumnSelectionAllowed(false);
        jTable1.getSelectionModel().addListSelectionListener(new RowListener());
        jTable1.setRowSelectionAllowed(true);
        jTable1.setColumnModel(new DefaultTableColumnModel(){
            public void moveColumn(int columnIndex, int newIndex) {

                return; //do nothing
            }
        });
        jTable1.setModel(new MyTableModel());
        jScrollPane5.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orbitfind/images/document_add.png"))); // NOI18N
        jToggleButton1.setToolTipText("Add Satellites");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orbitfind/images/trash.png"))); // NOI18N
        jToggleButton2.setToolTipText("Delete Selected Satellites");
        jToggleButton2.setMaximumSize(new java.awt.Dimension(65, 41));
        jToggleButton2.setMinimumSize(new java.awt.Dimension(65, 41));
        jToggleButton2.setPreferredSize(new java.awt.Dimension(65, 41));
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jButton7.setText("Save ");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton9.setText("Exit");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Save and Exit");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jToggleButton1)
                            .addComponent(jToggleButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jToggleButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jButton7))
                .addGap(20, 20, 20))
        );

        jTabbedPane1.addTab("Satellites-setup", jPanel3);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        jDialog1.getAccessibleContext().setAccessibleName("");
        jDialog1.getAccessibleContext().setAccessibleDescription("");
        jDialog1.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/orbitfind/images/settings.png")));

        jDialog1.addWindowListener(new WindowListener() {
            public void windowActivated(WindowEvent e) {}

            public void windowClosed(WindowEvent e) {
                discardChanges((DefaultTableModel)jTable1.getModel());
            }

            public void windowClosing(WindowEvent e) {}

            public void windowDeactivated(WindowEvent e) {}

            public void windowDeiconified(WindowEvent e) {}

            public void windowIconified(WindowEvent e) {}

            public void windowOpened(WindowEvent e) {}
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SAPL");
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        setForeground(java.awt.Color.white);
        setResizable(false);
        setSize(new java.awt.Dimension(482, 340));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });

        jButton1.setText("Browse");
        jButton1.setToolTipText("Browse .kml files");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("<html><p>Enter polygon pathname or press button to draw on map:</p></html>");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 204, 255));
        jButton3.setText("Compute and Open");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Draw Polygon");
        jButton4.setToolTipText("Show map");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setText("Show List");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orbitfind/images/satellite.jpg"))); // NOI18N
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("<html><a href=\\\"http://senselab.tuc.gr/\\\"> Powered by SenseLab@2016 </a></html>");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("<html><p>Satellite's preferences:</p></html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(70, 70, 70)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
                        .addGap(52, 52, 52)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jButton6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.getAccessibleContext().setAccessibleDescription("");
        jLabel5.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                jLabel5.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent me) {
                jLabel5.setCursor(Cursor.getDefaultCursor());
            }
            public void mouseClicked(MouseEvent e) {

                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        URI uri = new URI("http://www.nationalarchives.gov.uk/PRONOM/Format/proFormatSearch.aspx?status=new");
                        desktop.browse(new URI(strURL));
                    } catch (IOException | URISyntaxException ex ) {
                        // do nothing
                    }
                }
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*private void TextFieldEnablesButton() {
        fieldList.add(jTextField1);
        fieldList.add(jTextField2);
        DocumentListener myDocListener = new MyDocListener();
        for (JTextField field : fieldList) {
            this.add(field);
            field.getDocument().addDocumentListener(myDocListener);
        }

        jButton3.setEnabled(false);
        this.add(jButton3);
    }*/

    private void checkFieldsFull() {
        for (JTextField field : fieldList) {
            if (field.getText().trim().isEmpty()) {
                jButton3.setEnabled(false);
                return;
            }
        }
        jButton3.setEnabled(true);
    }

    private class MyDocListener implements DocumentListener {

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            checkFieldsFull();
        }

        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            checkFieldsFull();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            checkFieldsFull();
        }

    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));

        FileFilter filter = new OpenFileFilter("kml", "KML files");
        chooser.setFileHidingEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.showOpenDialog(null);
        //chooser.setDragEnabled(true);

        File f = chooser.getSelectedFile();

        String filename = f.getPath();
        // inputPol = filename;
        jTextField1.setText(filename);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentMoved

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentHidden

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        JFrame frame = this;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        if(jTextField1.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Please add a polygon .kml file or draw one");
            return;
        }
        int loadStatus = -2;
        if(model!=olModel){
            loadStatus =loadTable(prefsFile);
        } 
        if(loadStatus==0){
            JOptionPane.showMessageDialog(this, "No preferences found,please add some and try again");
        }else{
            File f1 = new File(jTextField1.getText());
            if (!f1.exists()) {
                JOptionPane.showMessageDialog(this, "Please check validity of polygon name");
                return;
            }
            if(loadStatus==-1){
                JOptionPane.showMessageDialog(this, "Error Loading Preferences.Click \"Show List\" to manage satellite preferences");
                return;
            }
            
            final JDialog dialog = new JDialog(this, true); // modal
            
            dialog.setTitle("Computing...");
            dialog.setLocationRelativeTo(getParent());
            dialog.setUndecorated(true);
            JProgressBar bar = new JProgressBar();
            bar.setIndeterminate(true);
            bar.setStringPainted(true);
            bar.setString("Please wait");
            dialog.add(bar);
            dialog.pack();
            SwingWorker<Void, Void> worker;
            worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    try {
                        ReadKMLFiles file = new ReadKMLFiles(mainFrame.this);
                        String in = null;
                        //in = " Reading polygon";
                        //LOGGER.info(in);

                        Polygon polygon = file.createPoly(f1.getPath());
                        ArrayList<Dummy> passes = null;
                        Map<String, Dummy> passes1 = new HashMap<>();
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        for(int i=0;i<model.getRowCount();i++){
                            if((Boolean)model.getValueAt(i, 0)){
                                File f = new File((String)model.getValueAt(i, 2));

                                //in = " Reading " + f.getName() + " satellite's orbit";

                                //Boolean bool = false;
                                Double degrees = (Double) model.getValueAt(i, 3);
                                if (degrees < 0) {
                                    JOptionPane.showMessageDialog(frame, "Value for " + f.getName().replace(".kml", "") + " is negative,making it positive and continuing");
                                    degrees = Math.abs(degrees);
                                }
                                

                                //ArrayList<Node> temp = file.checkCoordinates(f.getPath(), polygon, flag);
                                Dummy temp = file.checkCoordinates(f.getPath(), polygon, degrees);

                                if (temp != null) {
                                    passes1.put((String) model.getValueAt(i, 1), temp);

                                }
                            }
                        }
                        
                        if (!passes1.isEmpty()) {
                            String name = JOptionPane.showInputDialog("Enter name of output file?");
                            name = name.replace(".kml", "");
                            name = name + ".kml";
                            
                            //JOptionPane.showMessageDialog(frame, "Writing to file: " +System.getProperty("user.dir")+name);
                            file.combineFiles(name, f1.getPath(), passes1);

                            if (!Desktop.isDesktopSupported()) {
                                System.out.println("Desktop is not supported");

                            }
                            Desktop desktop = Desktop.getDesktop();

                            desktop.open(new File(name));

                        } else {
                            JOptionPane.showMessageDialog(frame, "None of the satellites passes through polygon");

                        }

                    } catch (HeadlessException | IOException ar) {
                        JOptionPane.showMessageDialog(frame, "Error computing orbits.\nIf this continues to show,open .kml and \"Save as...\" with utf-8 encoding");
                        ar.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void done() {
                    dialog.dispose();
                }
            };
            worker.execute();
            dialog.setVisible(true);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new ShowMap(this, "polygon.kml", this).setVisible(true);
        //readFromFile
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        if(model!=olModel){
            if(loadTable(prefsFile)<0){
                JOptionPane.showMessageDialog(this, "Error loading preferences,loading empty Table...");
            }
        }        
        jDialog1.setLocationRelativeTo(this);
        jDialog1.setVisible(true);
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

        int reply = JOptionPane.showConfirmDialog(jDialog1, "Are you sure you want to exit?","Warning",JOptionPane.YES_NO_OPTION);

        if (reply != JOptionPane.NO_OPTION){
            jDialog1.dispose(); //Dispose the window listener discardes changes made
        }
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        saveChanges(model);
        saveTable(model, prefsFile,satDir);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        
        JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
        FileFilter filter = new OpenFileFilter("kml", "KML files");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setMultiSelectionEnabled(true);

        // chooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "dmp"));
        chooser.showOpenDialog(null);
        File[] f = chooser.getSelectedFiles();
        
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        for (File fileC : f) {
            String filename = fileC.getPath();
            Boolean flagout=true;
            
            for(int x=0 ; x<jTable1.getRowCount();x++){
                if(model.getValueAt(x, 2).equals(filename)){
                    JOptionPane.showMessageDialog(this, filename + " already added on list");
                    flagout=false; 
                };
            }
            if (filename != null && flagout) {
                 String name = fileC.getName();
                try {
                    if(!satDir.exists()){satDir.mkdir();}
                    copyFileUsingFileChannels(fileC,new File(satDir.getPath()+System.getProperty("file.separator")+name));
                } catch (IOException ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                    model.addRow(new Object[]{true, name.replace(".kml", ""), satDir.getPath()+System.getProperty("file.separator")+name, new Double(0)});
                    continue;
                
            }
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        olModel = model;
        int[] sel = jTable1.getSelectedRows();
        
        for(int i= sel[sel.length-1],y = sel.length-1; y>=0 && i>=0 ;i--){
            if(i == sel[y]){
                //System.out.println("Removing row : " + i);
                model.removeRow(i);
                model.fireTableDataChanged();
                y--;
            }
        }
        
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        if(olModel!=model){
            saveChanges(model);
        }
        saveTable(model, prefsFile,satDir);
        jDialog1.dispose(); //Dispose the window listener discardes changes made
    }//GEN-LAST:event_jButton10ActionPerformed
    
    public String[] getTableData(DefaultTableModel model,int row) {
        String[] result = new String[model.getColumnCount()];
        for(int i=0;i<model.getColumnCount();i++){
            model.getValueAt(row, i);
        
        }
        return null;
    }
    
    
     
    private void saveTable(DefaultTableModel model, File prefsFile,File satellitesDir) {
        try {
                prefsFile.createNewFile();
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(prefsFile));
                out.writeObject(model.getDataVector());
                out.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving Preferences");
            }
    }
    
    /* return ,1 for normal load, 0 for empty table and -1 for error*/
    private int loadTable( File prefsFile) {
        try {
            
            ObjectInputStream in = new ObjectInputStream(
            new FileInputStream(prefsFile));
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            //Vector rowData = (Vector)in.readObject();
            Vector rowData = (Vector)in.readObject();

            Iterator itr = rowData.iterator();
            if(itr==null){
                return 0;
            }
            while(itr.hasNext()) {
                model.addRow((Vector) itr.next());
            }
            model.fireTableDataChanged();
            
            /*Iterator itr = rowData.iterator();
            while(itr.hasNext()) {
                model.addRow((Vector) itr.next());
            }*/
            in.close();
            return 1;
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    
    //Discard Changes made
    private void discardChanges(DefaultTableModel newModel){
        DefaultTableModel nmodel = (DefaultTableModel)jTable1.getModel();
        
        
            if(olModel!=null){
                if( olModel!=nmodel ){
                    jTable1.setModel(olModel);
                    olModel.fireTableDataChanged();
                    jDialog1.dispose();
                }
            }else{
                 jTable1.setModel(new MyTableModel());
            }     
    }
    private void saveChanges(DefaultTableModel newModel){
        DefaultTableModel nmodel = (DefaultTableModel)jTable1.getModel();
        olModel=nmodel;
    }
    
    
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
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
          //</editor-fold>
          //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new mainFrame().setVisible(true);
        });
    }

    public javax.swing.JTextField getTextField() {
        return jTextField1;
    }
    
    
    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
         if (event.getValueIsAdjusting()) {
          return;
         }

        }
    }
    
    
 private class ColumnListener implements ListSelectionListener {
  public void valueChanged(ListSelectionEvent event) {
   if (event.getValueIsAdjusting()) {
    return;
   }
   
  }
 }

    private DefaultListModel<String> createListModel(String folderName) {
        DefaultListModel<String> listModel = new DefaultListModel<String>();

        File folder = new File(folderName);
        File[] listFiles = folder.listFiles();

        if (listFiles != null) {
            for (File f : listFiles) {
                if (f.getName() != null && f.getName().endsWith(".kml")) {
                    listModel.addElement(f.getName());
                }
            }
        }
        return listModel;
    }

    public class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
           
            
            
            setComponentOrientation(list.getComponentOrientation());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setSelected(isSelected);
            setEnabled(list.isEnabled());

            setText(value == null ? "" : value.toString());

            return this;
        }
    }

    private static void copyFileUsingFileChannels(File source, File dest)
            throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    /*
     public javax.swing.JFormattedTextField getFormattedText(){
     return jFormattedTextField1;
     }*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    // End of variables declaration//GEN-END:variables
}
