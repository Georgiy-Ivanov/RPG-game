
import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow(){
        setTitle("РПГ на все времена");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(336,360);
        setLocation(400,400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
