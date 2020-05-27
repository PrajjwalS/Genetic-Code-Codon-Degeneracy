import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.FileChooserUI;

public class Draw extends JFrame implements ActionListener {
    JButton openEna, openWgs,histo,skew;
    protected static JTextArea dbContent,output;
    JFileChooser fileChooser;
    JScrollPane scrollPane1,scrollPane2;
    JLabel dbString,outStr ;
    String file_dir_wgs;
    public Draw(){
        // initialize the group layout : we have to configure both horizontal as well as
        // vertical layout for group layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

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

        dbContent = new JTextArea(10,20);
        scrollPane1 = new JScrollPane(output);
        scrollPane2 = new JScrollPane(dbContent);

//        JScrollBar vertical = scrollPane1.getVerticalScrollBar();
//        vertical.setValue(vertical.getMaximum());
//        scrollPane1.setVerticalScrollBar(vertical);
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
        );
        // configuring vertical layout
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(openEna)
                .addComponent(openWgs))
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
                } catch (IOException ex) {
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
            chart.pack( );
            RefineryUtilities.centerFrameOnScreen( chart );
            chart.setVisible( true );
        }else if(e.getSource() == skew){
            try {
                wga.handle_wga_file(file_dir_wgs);
            } catch (IOException ex) {
                ex.printStackTrace();
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
