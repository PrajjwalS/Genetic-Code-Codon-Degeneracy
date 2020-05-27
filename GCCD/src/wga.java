import org.jfree.ui.RefineryUtilities;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class wga
{
    protected static Vector skew_GC_cumm;
   static long frame_size=100;


    protected static void handle_wga_file(String file_path) throws IOException {
        File file = new File(file_path);

        BufferedReader br = new BufferedReader(new FileReader(file));

        long G=0,C=0;
        double prev_sum=0;
        String geneSeqName="";

        int c = 0;
        // ofsetting to first '>'
        while(true)         //Read char by Char
        {
            c = br.read();

            if(c==-1)
                Main.error_exit("Incorrect Formatting");
            else if((char)c=='>')
                break;
        }
        // collecting name
        while(true)         //Read char by Char
        {

            c = br.read();

            if(c==-1)
                Main.error_exit("Incorrect Formatting");
            else if((char)c=='\n')
                break;
            else
                geneSeqName+=(char)c;
        }
        // reading seq  frame_len per time
        long count=0;
        skew_GC_cumm= new Vector();
        while((c=br.read())!=-1)
        {
            count++;
            if(count % frame_size == 0)
            {
                count=0;
                double new_val=((double)G-(double)C)/((double)G+(double)C);
                skew_GC_cumm.add(prev_sum+new_val);
                prev_sum+=new_val;
                G=0;
                C=0;
            }
            if((char)c=='g' || (char)c=='G')
                G++;
            else if((char)c=='c' || (char)c=='C')
                C++;
        }

        XY_Plot chart = new XY_Plot("GCCD",
                "GC SKEW GRAPH",skew_GC_cumm);
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );



    }
}
