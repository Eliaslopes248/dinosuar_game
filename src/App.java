import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
       
        //dimensions
        int bordderwidth = 750;
        int bordderheight = 250;

        //frame object
        JFrame frame = new JFrame("Chrome Dinosaur");

        frame.setSize(bordderwidth, bordderheight);

        //set location to middle
        frame.setLocationRelativeTo(null);
       
        
        //dont allow resizing
        frame.setResizable(false);
        

        //default end program on exit
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //get JPanel
        Chromedinosaur chromedinosaur = new Chromedinosaur();
        frame.add(chromedinosaur);
        frame.pack();
        chromedinosaur.requestFocus();
        frame.setVisible(true);
   


    }

}
