import java.io.*;
import java.util.Scanner;

public class Main
{

    // helper function /////////
    static void error_exit(String error)
    {
        // TODO: Create appropriate for error in UI
        System.out.println("ERROR:"+error);
        System.exit(1);
    }
    /////////////////////////////// main function //////////////////

    public static void main(String[] args) throws IOException {
        // intialzing scanner
        Scanner sc= new Scanner(System.in);

        //TODO: add UI
        // UI runs in this loop
        int choice=-999;
        File file;
        while (true)
        {
            System.out.println("Enter 0 to exit\n1)to select ena file\n2)for to select wgs file");
            choice=sc.nextInt();

            // if choice is 0 exit
            if(choice==0)
                break;

            // else if choice is 1 then input file and
            //  generate AA seq and other info.
            //  and store AA seq and oter info in .db file
            else if (choice==1)
            {
                // file path here from GUI
                // right now default is set here
                //TODO:
                // either collect file name by str or File type from GUI
                // and send the file name str or file to the below
                // called function
                // by default i am using string here
                   String file_dir = "/home/pdawg/PROJECTS/GCCD/src/Ecol_K12_MG1655_.ena";
                //String file_dir = "/home/pdawg/PROJECTS/GCCD/src/hey.wgs";

                // handle ena file with the following
                data_base_generator.db_gen(file_dir);

            }
            else if (choice==2)
            {
                // file path here from GUI
                // right now default is set here
                //TODO:
                // either collect file name by str or File type from GUI
                // and send the file name str or file to the below
                // called function
                // by default i am using string here
                String file_dir = "/home/pdawg/PROJECTS/GCCD/src/Ecol_K12_MG1655_.wgs";
                //String file_dir = "/home/pdawg/PROJECTS/GCCD/src/hey.wgs";
                // handle wga file _ generate skew graph
                wga.handle_wga_file(file_dir);
            }
        }
    }
    //////////////////////MAIN FUNCTION ENDS ////////////////////////////
}
