import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.FileChooserUI;
import javax.swing.table.DefaultTableModel;

public class Draw extends JFrame implements ActionListener {
    JButton openEna, openWgs,histo,skew,db;
    protected static JTextArea output;
    protected static JTable dbContent;
    JFileChooser fileChooser;
    JScrollPane scrollPane1,scrollPane2;
    JLabel dbString,outStr ;
    String file_dir_wgs;
    String [][]rowData ;
    public Draw(){
        // initialize the group layout : we have to configure both horizontal as well as
        // vertical layout for group layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        db = new JButton("View Database");
        db.addActionListener(this);
        histo = new JButton("Show Histogram");
        histo.addActionListener(this);
        skew = new JButton("Show Skewed graph");
        skew.addActionListener(this);
        fileChooser = new JFileChooser();
        openEna = new JButton("Open an .ena file...",createImageIcon("images/Open16.gif"));
        openEna.addActionListener(this);
        openWgs = new JButton("Open a .wgs file...",createImageIcon("images/Open16.gif"));
        openWgs.addActionListener(this);

        output = new JTextArea(5,20);
        outStr = new JLabel("OutPut: ");
        dbString = new JLabel("Database");

        String []columnName = {"GEN_NAME","GEN_SEQ","GEN_SEQ_LEN","AA_SEQ","AA_SEQ_LEN","REMARKS" };

        DefaultTableModel model = new DefaultTableModel();
        for (String s : columnName) model.addColumn(s);
        dbContent = new JTable(model);
        scrollPane1 = new JScrollPane(output);
        scrollPane2 = new JScrollPane(dbContent);
        dbContent.setFillsViewportHeight(true);


        // configuring horizontal layout
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(openEna)
                        .addComponent(histo))
                        .addComponent(outStr)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(scrollPane1))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(openWgs)
                        .addComponent(skew))
                        .addComponent(dbString)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollPane2)

                )
                .addGroup(layout.createParallelGroup()
                .addComponent(db))
        );
        // configuring vertical layout
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(openEna)
                .addComponent(openWgs)
                .addComponent(db))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(histo)
                .addComponent(skew))
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(outStr)
                                .addComponent(scrollPane1)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(dbString)
                                .addComponent(scrollPane2))
                )
        );
        setTitle("Codon GUI");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == openEna){
            int result = fileChooser.showOpenDialog(Draw.this);
            if(result == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();

                String file_dir = file.getAbsolutePath();
                try {
                    data_base_generator.db_gen(file_dir);
                } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                }
            }else{
                output.append("Open cancelled!\n");
            }
        }else if(e.getSource() == openWgs){
            int result = fileChooser.showOpenDialog(Draw.this);
            if(result == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                file_dir_wgs = file.getAbsolutePath();

            }else{
                output.append("Open cancelled!\n");
            }
        }else if(e.getSource() == histo){
            bar_chart_creator chart = new bar_chart_creator("GCCD",
                    "Percentage of each AA one letter code",data_base_generator.freq);
            chart.pack();
            RefineryUtilities.centerFrameOnScreen( chart );
            chart.setVisible( true );
        }else if(e.getSource() == skew){
            try {
                wga.handle_wga_file(file_dir_wgs);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else if(e.getSource() == db){
            // show the database content
            Connection connection = DBConnect.connect();// establishes a connection to the database
            PreparedStatement ps = null;
            ResultSet resultSet = null;
            try{
                String sql = "SELECT * FROM GeneInfo";
                ps = connection.prepareStatement(sql);
                resultSet = ps.executeQuery();
                if(resultSet==null){
                    // infom that db is empty
                    output.append("No record found in database, please select .ena file first");
                    System.err.println("No records found");
                }else {
                    while (resultSet.next()) {
                        // read all the data
                        String genName = resultSet.getString("GEN_NAME");
                        String genSeq = resultSet.getString("GEN_SEQ");
                        Long genSeqLen = resultSet.getLong("GEN_SEQ_LEN");
                        String aaSeq = resultSet.getString("AA_SEQ");
                        Long aaSeqLen = resultSet.getLong("AA_SEQ_LEN");
                        String remarks = resultSet.getString("REMARKS");

                        // add this row to the table
                        DefaultTableModel model = (DefaultTableModel) dbContent.getModel(); // get the model of the table
                        model.addRow(new Object[]{genName,genSeq,genSeqLen,aaSeq,aaSeqLen,remarks});

                    }
                }
            }catch (SQLException exc){
                exc.printStackTrace();
            }
        }
        output.setCaretPosition(output.getDocument().getLength()); // sets the vertical scrollbar at the end.
    }

    protected static ImageIcon createImageIcon(String path){
        java.net.URL imgURL = Draw.class.getResource(path);
        if(imgURL != null){
            return new ImageIcon(imgURL);
        }else{
            System.err.println("Couldn't find a file: "+path);
            return null;
        }
    }
}
