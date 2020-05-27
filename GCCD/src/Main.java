import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Main
{

    // helper function /////////
    protected static void error_exit(String error)
    {
        Draw.output.append("ERROR : "+error+".\n");
        Draw.output.setCaretPosition(Draw.output.getDocument().getLength());
        System.exit(1);
    }
    /////////////////////////////// main function //////////////////

    public static void main(String[] args) throws IOException {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                Draw.createAndShowGUI();
                try{
                    UIManager.setLookAndFeel(
                            "javax.swing.plaf.metal.MetalLookAndFeel");
                }catch (Exception e){
                    e.printStackTrace();
                }
                new Draw();
            }
        });

    }
    //////////////////////MAIN FUNCTION ENDS ////////////////////////////
}
