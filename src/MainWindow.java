import javax.swing.*;
import java.io.*;
import java.util.Arrays;
/*
 Краткий гайд:
 MainWindow - Создание игрового поля, сохранение игры, загрузка игры.
 GameField - Все веселье происходит там.
 Monster и Player - думаю тут понятно.
*/
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
        int shield = 0;
        int sword = 0;
        if (player.getHasSword()) {
            sword = 1;
        }
        if (player.getHasShield()){
            shield = 1;
        }
        String sv = player.getHp() + "s" + player.getMaxHp() + "s" + player.getLvl() + "s" + player.getStr() + "s" + player.getDef() + "s" + player.getExp() + "s" + player.getGold() + "s" + shield + "s" + sword;
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
            player.setHp(Integer.parseInt(param[0]));
            player.setMaxHp(Integer.parseInt(param[1]));
            player.setLvl(Integer.parseInt(param[2]));
            player.setStr(Integer.parseInt(param[3]));
            player.setDef(Integer.parseInt(param[4]));
            player.setExp(Integer.parseInt(param[5]));
            player.setGold(Integer.parseInt(param[6]));
            if (Integer.parseInt(param[7]) == 1){
                player.setHasShield(true);
            }if (Integer.parseInt(param[8]) == 1){
                player.setHasSword(true);
            }



        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

