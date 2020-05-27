import org.jfree.ui.RefineryUtilities;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//import java.sql.*;

public class data_base_generator
{


    static int binding(char c)
     {
        if (c == 'a' || c == 'A')
            return 2;
        else if (c == 'u' || c == 'U' || c == 't' || c == 'T')
            return 0;
        else if (c == 'c' || c == 'C')
            return 1;
        else if (c == 'g' || c == 'G')
            return 3;
        else
            return -999;
    }
    ///////////////////////////
    // setting an instance codon_map for this operation as variable cm
    static  codon_map cm = new codon_map();

    // Map for storing the frequency of each AA letter code occurred
    protected static final Map<Character, Long> freq = new HashMap<>();


    ///////////////
    // all the different parameters of a single DB record goes here
    // objects holds one record, and is to be sent to DB
    static class data_base_record
    {
        String geneName;
        String geneSeq;
        long geneSeqLength;
        String AASeq;
        long AASeqLength;
        String remark;
        boolean valid;
        //default constructor for record
        public data_base_record()
        {
            this.geneName = "";
            this.geneSeq = "";
            this.geneSeqLength = 0;
            this.AASeq = "";
            this.AASeqLength = 0;
            this.remark = "";
            this.valid = true;
//            insert_record_in_DB();
            DBConnect.connect(); // create the connection to the database
        }
    }

    // this function reads the ena file and generates the .db file
   static public void  db_gen(String filepath) throws IOException, SQLException {
        // initiazling the freq map
        freq.put('A', 0L);
        freq.put('B', 0L);
        freq.put('R', 0L);
        freq.put('N', 0L);
        freq.put('D', 0L);
        freq.put('C', 0L);
        freq.put('E', 0L);
        freq.put('Q', 0L);
        freq.put('G', 0L);
        freq.put('H', 0L);
        freq.put('I', 0L);
        freq.put('L', 0L);
        freq.put('K', 0L);
        freq.put('M', 0L);
        freq.put('F', 0L);
        freq.put('P', 0L);
        freq.put('S', 0L);
        freq.put('T', 0L);
        freq.put('W', 0L);
        freq.put('Y', 0L);
        freq.put('V', 0L);
        freq.put('*', 0L);

        // opening ena file
        File file = new File(filepath);

        BufferedReader br = new BufferedReader(new FileReader(file)); // exception FileNotFoundException handles errors

        // reading the ena file to fill up a new record, and sending the record to the Database

            // initially offsetting to find the first '>' symbol
            int c = 0;
            while(true)         //Read char by Char
            {
                c = br.read();

                if(c==-1)
                    Main.error_exit("Incorrect Formatting");
                else if((char)c=='>')
                    break;
            }
            data_base_record record = null;
        // now starting the loop
            while(true)
            {
                if(c==-1)
                {
                    break;
                }
                // don't care about previous record instances, garbage collector will take care of it
                 record = new data_base_record();

                // collecting name
                while(true)         //Read char by Char
                {

                    c = br.read();

                    if(c==-1)
                        Main.error_exit("Incorrect Formatting");
                    else if((char)c=='\n')
                        break;
                    else
                        record.geneName+=(char)c;
                }

                // collecting gene sequence and generating AAseq and inserting into db
                while(true)
                {
                    c = br.read();


                    if((char)c=='\n')
                        continue;
                    else if((char)c=='>'|| c==-1)
                    {
                        // geneSeq is read now :
                        // check length of gene seq
                        if(record.geneSeqLength%3!=0)
                        {
                            record.valid = false;
                            record.remark+="Incorrect Length";
                        }
                        // check if record is valid
                        if(record.valid==true)
                        {
                            record.remark="GOOD";
                            for(int i=0;i<=record.geneSeqLength-3;i+=3)
                            {
                                int x=binding(record.geneSeq.charAt(i));
                                int y=binding(record.geneSeq.charAt(i+1));
                                int z=binding(record.geneSeq.charAt(i+2));
                                record.AASeq += cm.mapping[x][y][z];
                                // Calculating AA code freq
                                long newval=0;
                                try {
                                    newval = freq.get(cm.mapping[x][y][z])+1;

                                }catch (Exception e){
                                    System.out.println("Hash Map Freq missed:"+cm.mapping[x][y][z]);
                                }

                                freq.replace(cm.mapping[x][y][z],newval);
                            }
                            record.AASeqLength=record.AASeq.length();
                        }
                        // adding the record to the database

                        Draw.output.append("GENESEQ:"+record.geneSeq+"\n");
                        Draw.output.append("GENlen:"+record.geneSeqLength+"\n");
                        Draw.output.append("AASEQ:"+record.AASeq+"\n");
                        Draw.output.append("AASEQLEN:"+record.AASeqLength+"\n");
                        Draw.output.append("REMARK:"+record.remark+"\n");

//                        //insert_record_in_DB();
//                        DBConnect.connect(); // create the connection to the database
                        // now add the entry
//                        DBConnect.addEntry(record.geneName,record.geneSeq,record.geneSeqLength,record.AASeq,record.AASeqLength,record.remark);
                        System.out.println("Currently not inserting...unComment to Insert!");
                        break;
                    }
                    else
                    {
                        record.geneSeqLength++;
                        record.geneSeq+=(char)c;
                        if(binding((char)(c)) ==-999)
                        {
                            record.valid=false;
                            record.remark+=("Incorrect Gene at:"+record.geneSeqLength+"\n");
                        }
                    }
                }
            }


            // asking if to show bar chart or not
//            System.out.println("Enter 1 if you would like to see AA-Code Bar Chart\nEnter 2 to see previous menu");
//            Scanner sc=new Scanner(System.in);
//            int k=sc.nextInt();
//            if(k==1)
//            {
//                // showing bar chart with the freq map
//                bar_chart_creator chart = new bar_chart_creator("GCCD",
//                        "Percentage of each AA one letter code",freq);
//                chart.pack( );
//                RefineryUtilities.centerFrameOnScreen( chart );
//                chart.setVisible( true );
//            }



    }
}
