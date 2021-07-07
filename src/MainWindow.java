import javax.swing.*;
import java.io.*;
import java.util.Arrays;

public class MainWindow extends JFrame {


    public MainWindow(){
        setTitle("РПГ на все времена");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(336,360);
        setLocation(400,400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args){
        new MainWindow();
    }
    public static void save(Player player) throws IOException {
        Writer writer = new FileWriter("save.txt");
        String sv = player.getHp() + "s" + player.getMaxHp() + "s" + player.getLvl() + "s" + player.getStr() + "s" + player.getDef() + "s" + player.getExp() + "s" + player.getGold();
        writer.write(sv);
        writer.close();
    }
    public static void load(Player player) {
        try(Reader reader = new FileReader("save.txt")) {
            char[] buf = new char[256];
            int c;
            while((c =reader.read(buf))>0) {
                if (c < 256) {
                    buf = Arrays.copyOf(buf, c);
                }
            }
            String x1 = String.valueOf(buf);
            System.out.println(x1);
            String[] param = x1.split("s");
            System.out.println(Integer.parseInt(param[0]));
            player.setHp(Integer.parseInt(param[0]));
            player.setMaxHp(Integer.parseInt(param[1]));
            player.setLvl(Integer.parseInt(param[2]));
            player.setStr(Integer.parseInt(param[3]));
            player.setDef(Integer.parseInt(param[4]));
            player.setExp(Integer.parseInt(param[5]));
            player.setGold(Integer.parseInt(param[6]));



        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

