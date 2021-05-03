import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by infuntis on 15/01/17.
 */
public class GameField extends JPanel implements ActionListener{
    public static boolean isFighting = false;
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rand = new Random();
    private static final Monster dragon = new Monster();
    private static Monster monster = new Monster();
    private static boolean hasSword = false;
    private static boolean hasShield = false;
    private static boolean isTrue = true;
    private static String msg;
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private Image field;
    private Image monster1;
    private int monsterX;
    private int monsterY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private int inGame = 1;
    ImageIcon iia = new ImageIcon("test1.gif");
    ImageIcon iid = new ImageIcon("dot.png");
    ImageIcon fia = new ImageIcon("field.png");
    ImageIcon fia2 = new ImageIcon("monster.png");
    Player player = new Player();


    public GameField(){
        dragon.setlvl(16);
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 160;
            y[i] = 160;
        }
        //timer = new Timer(250,this);
       // timer.start();
        createMonster();
        monster.setlvl(player.getLvl());
    }

    public void createMonster(){
        monsterX = new Random().nextInt(20)*DOT_SIZE;
        monsterY = new Random().nextInt(6)*DOT_SIZE;
    }

    public void loadImages(){
        apple = iia.getImage();
        dot = iid.getImage();
        field = fia.getImage();
        monster1 = fia2.getImage();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame == 1){
            g.drawImage(field, 0, 0,this);
            g.drawImage(monster1, monsterX,monsterY-16,this);
            g.drawImage(dot,x[0],y[0]-16,this);
            g.setColor(Color.white);
            g.drawString("x: "+ x[0] + " ," + "y: "+y[0], 0, 224);
            g.drawString("Gold: "+ player.getGold(), 0, 240);
            g.drawString("Hp: " + player.getHp() + "/"+ player.getMaxHp(), 0 , 256);
            g.drawString("Exp: " + player.getExp(), 0, 272);
            g.drawString("lvl: " + player.getLvl(), 0 , 288);
            g.drawString("STR: " + player.getStr(), 0, 304);
            g.drawString("DEF: " + player.getDef(), 0, 320);
            if (isFighting) g.drawString("HP: " + monster.getHp(), monsterX-0, monsterY-32);
            }
        }


    public void move(int i){

        if(i == 1){
            x[0] -= DOT_SIZE;
        }
        if(i == 3){
            x[0] += DOT_SIZE;
        } if(i == 2){
            y[0] -= DOT_SIZE;
        } if(i == 4){
            y[0] += DOT_SIZE;
        }
        if (x[0] == monsterX && y[0] == monsterY){

            try {
                fight(player);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createMonster();
        }
    }



    public void movenment(int i) {
    move(i);
    repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT){
                if(x[0] != 0) {
                    movenment(1);
                }
            }
            if(key == KeyEvent.VK_RIGHT){
                if (x[0] != 304) {
                    movenment(3);
                }
            }
            if(key == KeyEvent.VK_UP){
                if (x[0] == 160 && y[0] == 112) {
                    movenment(2);
                }
                if(!(y[0] == 112 || y[0] == 0)) {
                    movenment(2);
                }
            }
            if(key == KeyEvent.VK_DOWN){
                if (x[0] == 160 && y[0] == 80) {
                    movenment(4);
                }
                if(!(y[0] == 80 || y[0] == 304)) {
                    movenment(4);
                }
            }
        }
    }

    static void info(Player player){
        lines();
        System.out.println("Ваш уровень: " + player.getLvl());
        System.out.println("Ваш опыт: " + player.getExp());
        System.out.println("Ваше здоровье: " + player.getHp());
        System.out.println("Ваша сила: " + player.getStr());
        System.out.println("Ваш защита: " + player.getDef());
        System.out.println("Ваше золото: " + player.getGold());
        lines();
    }

    static void savePrincess(Player player) throws InterruptedException {
        lines();
        System.out.println("Вы стоите перед башней. Табличка на входе гласит: На верхнем этаже ждет принцесса, что бы её спасти нужно убить дракона. Дракон сильный капец, 500 жизней, 310 атака и 305 защита.");
        System.out.println("Уверены что хотите войти? Y/N");
        String msg = sc.nextLine();
        if (msg.equals("Y")) {
            while (true){
                if (player.getStr() >= dragon.getDef()) {
                    if (rand.nextInt(4) == 0) {
                        System.out.print("Критический удар! Вы нанесли монстру " + ((player.getStr() * 2) - dragon.getDef()) + " урона!");
                        dragon.giveHp(((player.getStr() * 2) - dragon.getDef())* -1);
                    } else {
                        System.out.print("Вы нанесли монстру " + (player.getStr() - dragon.getDef()) + "урона!");
                        dragon.giveHp((player.getStr() - dragon.getDef())* -1);
                    }
                } else {
                    System.out.print("Вы слишком слабы что бы наносить урон. Монстр смеется над вами.");
                }
                if (!dragon.isAlive) {
                    System.out.println("Монстр побежден! Вы получаете 500 опыта и 100 золота!");
                    player.giveExp(player, 500);
                    player.giveGold(100);
                    break;
                }
                if (dragon.getStr() >= player.getDef()) {
                    System.out.println("| Монстр нанес вам " + (dragon.getStr() - player.getDef()) + "урона!");
                    player.giveHp((dragon.getStr() - player.getDef())*-1);
                } else {
                    System.out.print("Вы слишком сильный что бы монстр вас хотя бы поцарапал.");
                }
                System.out.println("Ваши хп " + player.getHp() + "|" + "Хп врага " + dragon.getHp());
                if (!player.isAlive) {
                    System.out.println("YOU DIED");
                    break;
                }
            }
            Thread.sleep(1000);
        }
    }

    public void fight(Player player) throws InterruptedException {
        isFighting = true;
        repaint();
        while (true){
            if (player.getStr() >= monster.getDef()) {//если игрок сильнее чем защита монстра то он наносит урон. если нет - урона нет
                if (rand.nextInt(4) == 0) {//если вдруг удар критический то наносится двойной урон
                    System.out.print("Критический удар! Вы нанесли монстру " + ((player.getStr() * 2) - monster.getDef()) + " урона!");
                    monster.giveHp(((player.getStr() * 2) - monster.getDef())* -1);
                } else {
                    System.out.print("Вы нанесли монстру " + (player.getStr() - monster.getDef()) + "урона!");
                    monster.giveHp((player.getStr() - monster.getDef())* -1);
                }
            } else {
                System.out.println("Вы слишком слабы что бы наносить урон. Монстр смеется над вами.");
            }
            if (!monster.isAlive) {
                System.out.println("Монстр побежден! Вы получаете 500 опыта и 100 золота!");
                player.giveExp(player, 500);
                player.giveGold(100);
                break;
            }
            if (monster.getStr() >= player.getDef()) {
                System.out.println("| Монстр нанес вам " + (monster.getStr() - player.getDef()) + "урона!");
                player.giveHp((monster.getStr() - player.getDef())*-1);
            } else {
                System.out.println("Вы слишком сильный что бы монстр вас хотя бы поцарапал.");
            }
            System.out.println("Ваши хп " + player.getHp() + "|" + "Хп врага " + monster.getHp());
            if (!player.isAlive) {
                System.out.println("YOU DIED");
                break;
            }
            repaint();
            Thread.sleep(250);
        }
        isFighting = false;
    }

    static void shop(Player player){
        lines();//добавляем линии перед сообщениями
        System.out.println("Чё нада?");
        String msg;
        while (true) {
            System.out.println("Ваше золото:" + player.getGold());
            lines();
            System.out.println("1.50 золота: Баночку хп(восстанавливает все жизни)");
            System.out.println("2.50 золота: Баночку опыта(дает 100 опыта)");
            System.out.println("3.125 золота: Меч(+ 50 к атаке)");
            System.out.println("4.125 золота: Щит(+25 к броне)");
            System.out.println("5.Выйти");
            msg = sc.nextLine();
            if (msg.equals("1")) {
                if (player.getGold() >= 50) {
                    player.giveGold(-50);
                    player.giveHp(player.getMaxHp());
                    System.out.println("Вы полностью восстановили своё здоровье. Текущее здоровье: " + player.getHp());
                } else {
                    System.out.println("Недостаточно золота");
                }
            } else if (msg.equals("2")){
                if (player.getGold() >= 50) {
                    player.giveGold(-50);
                    player.giveExp(player, 100);
                    System.out.println("Вы получили 100 опыта. Текущее количество опыта: " + player.getExp());
                } else {
                    System.out.println("Недостаточно золота");
                }
            } else if (msg.equals("3")){
                if (player.getGold() >= 125 && !hasSword) {
                    player.giveGold(-125);
                    hasSword = true;
                    player.giveStr(50);
                    System.out.println("Теперь ты вооружен, но помни:");
                } else {
                    System.out.println("Недостаточно золота");
                }
                System.out.println("Меч может быть только один!");
            } else if (msg.equals("4")){
                if (player.getGold() >= 125 && !hasShield) {
                    player.giveGold(-125);
                    hasShield = true;
                    player.giveDef(25);
                    System.out.println("Теперь у тебя есть щит, но помни:");
                } else {
                    System.out.println("Недостаточно золота");
                }
                System.out.println("Щит может быть только один!");
            } else if (msg.equals("5")){
                break;
            } else {
                System.out.println("Что то на эльфийком. Не разобрать");
            }
        }
    }


    static void lvlUp(Player player){
        System.out.println("Ура! " + player.getName() + " получил уровень! Теперь он уровень "+ player.getLvl() +" Вот твои характеристики:");
        System.out.println("1.Максимальный жизненный запас = " + player.getMaxHp());
        System.out.println("2.Сила = " + player.getStr());
        System.out.println("3.Защита = " + player.getDef());
        System.out.println("Что желаете прокачать на 10?(Введите цифру)");
        isTrue = true;
        while (isTrue){
            msg = sc.nextLine();
            switch (msg) {
                case "1":
                    player.giveMaxHp(10);
                    System.out.println("Теперь твой максимальный жизненный запас: " + player.getMaxHp());
                    lines();
                    isTrue = false;
                    break;
                case "2":
                    player.giveStr(10);
                    System.out.println("Теперь твоя сила: " + player.getMaxHp());
                    lines();
                    isTrue = false;
                    break;
                case "3":
                    player.giveDef(10);
                    System.out.println("Теперь твоя защита: " + player.getDef());
                    lines();
                    isTrue = false;
                    break;
                default:
                    System.out.println("Что то на эльфийком. Не разобрать");
                    break;
            }
        }


    }
    static void lines(){
        System.out.println("_____________________________________________________________________");
    }
}


