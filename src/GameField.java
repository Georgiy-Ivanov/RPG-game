import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;


public class GameField extends JPanel implements ActionListener {
    public static int status = 1; //переменная которая говорит какое поле игры отрисовывать в данный момент
    public static boolean isAlive = true; //жив ли игрк, хотя думается нужно это перенести в другой класс
    private static final Random rand = new Random();
    private static final Monster monster = new Monster();

    private final int DOT_SIZE = 16; //размеры клетки игровой клетки
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image field;
    private Image monster1;
    private Image shop;
    private Image princess;
    private int monsterX;//координаты монстра
    private int monsterY;
    private final int[] x = new int[ALL_DOTS];//создаем массивы из игровых клеток
    private final int[] y = new int[ALL_DOTS];
    ImageIcon iid = new ImageIcon("dot.png");
    ImageIcon fia = new ImageIcon("field.png");
    ImageIcon fia2 = new ImageIcon("monster.png");
    ImageIcon fia3 = new ImageIcon("shop.png");
    ImageIcon fia4 = new ImageIcon("princess.png");
    Player player = new Player();


    public GameField() {
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    public void initGame() {//помещаем игрока на игровое поле и сразу создаем монстра
        x[0] = 160;
        y[0] = 160;
        createMonster(new Random().nextInt(18) * DOT_SIZE, ((new Random().nextInt(4))+1) * DOT_SIZE);

    }

    public void createMonster(int x, int y) {
        monsterX = x;
        monsterY = y;
    }

    public void loadImages() {
        dot = iid.getImage();
        field = fia.getImage();
        monster1 = fia2.getImage();
        shop = fia3.getImage();
        princess = fia4.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {//отрисовка игровых полей в зависимости от значения переменной status
        super.paintComponent(g);
        if (status == 1) {//на улице
            g.drawImage(field, 0, 0, this);
            g.drawImage(shop, 0, 128, this);
            g.drawImage(princess, 272 - 4 * DOT_SIZE, 176 - 3 * DOT_SIZE, this);
            g.drawImage(dot, x[0], y[0] - 16, this);
            g.setColor(Color.white);
            //g.drawString("x: " + x[0] + " ," + "y: " + y[0], 0, 224);
            g.drawString("Gold: " + player.getGold(), 0, 318);
            g.drawString("ESC: save / load", 0, 302);
            g.drawString("Hp: " + player.getHp() + "/" + player.getMaxHp(), 64, 318);
            g.drawString("Exp: " + player.getExp(), 137, 318);
            g.drawString("lvl: " + player.getLvl(), 192, 318);
            g.drawString("STR: " + player.getStr(), 224, 318);
            g.drawString("DEF: " + player.getDef(), 272, 318);
            g.drawImage(monster1, monsterX, monsterY - 16, this);
            g.drawString("HP: 132", 272, 208);
            g.drawString("HP: " + monster.getHp(), monsterX, monsterY + 16);
        }
        if (status == 2) { //в магазине
            g.setColor(Color.WHITE);
            g.drawString("Ваше золото:" + player.getGold(), 32, 160);
            g.drawString("1.50 золота: Баночку хп(восстанавливает HP)", 32, 176);
            g.drawString("2.50 золота: Баночку опыта(дает 100 опыта)", 32, 192);
            if (!player.getHasSword()) {
                g.drawString("3.125 золота: Меч(+ 50 к атаке)", 32, 208);
            }
            if (!player.getHasShield()) {
                g.drawString("4.125 золота: Щит(+25 к броне)", 32, 224);
            }
            g.drawString("5.Выйти", 32, 240);
        }
        if (status == 3) { //здох браток
                g.drawString("YOU DIED", 64, 176);
            }
        if (status == 4) { //lvlup
            g.setColor(Color.WHITE);
            g.drawString("LVL UP!", 32, 160);
            g.drawString("7.Максимальный жизненный запас = " + player.getMaxHp(), 32, 176);
            g.drawString("8.Сила = " + player.getStr(), 32, 192);
            g.drawString("9.Защита = " + player.getDef(), 32, 208);
            g.drawString("Что желаете прокачать на 10?(Введите цифру)", 32, 224);
        }
        if (status == 5) {
            g.drawString("YOU WIN!", 64, 176);
        }
        if (status == 6) {//saveload
            g.setColor(Color.WHITE);
            g.drawString("1.Сохранить", 32, 176);
            g.drawString("2.Загрузить", 32, 192);
            g.drawString("3.Отмена", 32, 208);
        }
    }

    public void move(int i){//движение в зависимости от нажатой кнопки
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
        if (x[0] == 208 && y[0] == 176){//событие если игрок встает на клетку дракона
            createMonster(208, 176);
            monster.setlvl(16);
            try {
                fight(player);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isAlive) {
                status = 5;
            }
            pressed();
            createMonster(new Random().nextInt(18)*DOT_SIZE, ((new Random().nextInt(4))+1) * DOT_SIZE);
        }
        if (x[0] == monsterX && y[0] == monsterY){//событие если игрок встает на клетку монстра
            try {
                fight(player);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            monster.setlvl(player.getLvl());
            createMonster(new Random().nextInt(18)*DOT_SIZE, ((new Random().nextInt(4))+1) * DOT_SIZE);
        }
        if (x[0] == 32 && y[0] == 176){//магазин
            status = 2;
        }

    }

    public void movement(int i) {
    move(i);
    repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (status == 1) {
                if (key == KeyEvent.VK_LEFT) {
                    if (x[0] != 0) {
                        movement(1);
                    }
                }
                if (key == KeyEvent.VK_RIGHT) {
                    if (x[0] != 304) {
                        movement(3);
                    }
                }
                if (key == KeyEvent.VK_UP) {
                    if (x[0] == 160 && y[0] == 112) {
                        movement(2);
                    }
                    if (!(y[0] == 112 || y[0] == 0)) {
                        movement(2);
                    }
                }
                if (key == KeyEvent.VK_DOWN) {
                    if (x[0] == 160 && y[0] == 80) {
                        movement(4);
                    }
                    if (!(y[0] == 80 || y[0] == 304)) {
                        movement(4);
                    }
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    status = 6;
                    pressed();
                }
            } //поле
            if (status == 2) {
                if (key == KeyEvent.VK_NUMPAD1) {

                    if (player.getGold() >= 50) {
                        player.giveGold(-50);
                        player.giveHp(player.getMaxHp());
                        pressed();
                    }

                }
                if (key == KeyEvent.VK_NUMPAD2) {

                    if (player.getGold() >= 50) {
                        player.giveGold(-50);
                        player.giveExp(player, 100);
                        newLvl();
                        pressed();
                    }

                }
                if (key == KeyEvent.VK_NUMPAD3) {

                    if (player.getGold() >= 125 && !player.getHasSword()) {
                        player.giveGold(-125);
                        player.setHasSword(true);
                        player.giveStr(50);
                        pressed();
                    }

                }
                if (key == KeyEvent.VK_NUMPAD4) {
                    if (player.getGold() >= 125 && !player.getHasShield()) {
                        player.giveGold(-125);
                        player.setHasShield(true);
                        player.giveDef(25);
                        pressed();
                    }

                }
                if (key == KeyEvent.VK_NUMPAD5) {

                    exitShop();

                }
            } //магазин
            if (status == 4) {
                if (key == KeyEvent.VK_NUMPAD7) {
                    player.giveMaxHp(10);
                    exitShop();

                }
                if (key == KeyEvent.VK_NUMPAD8) {
                    player.giveStr(10);
                    exitShop();
                }
                if (key == KeyEvent.VK_NUMPAD9) {
                    player.giveDef(10);
                    exitShop();
                }
            } //lvlup
                if (status == 6) {//сохранить
                if (key == KeyEvent.VK_NUMPAD1) {
                    try {
                        MainWindow.save(player);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    exitShop();
                }
                if (key == KeyEvent.VK_NUMPAD2) {
                    MainWindow.load(player);
                    exitShop();
                }
                if (key == KeyEvent.VK_NUMPAD3) {
                    exitShop();
                }
            }
        }
    }
        public void exitShop(){
            status = 1;
            paintComponent(this.getGraphics());
        }
        public void pressed(){
            paintComponent(this.getGraphics());
        }



    public void fight(Player player) throws InterruptedException {
        monster.isAlive = true;
        while (true){
            if (player.getStr() > monster.getDef()/2) {//если игрок сильнее чем защита монстра то он наносит урон. если нет - урона нет
                if (rand.nextInt(4) == 0) {//если вдруг удар критический то наносится двойной урон
                    monster.giveHp(((player.getStr() * 2) - monster.getDef()/2)* -1);
                } else {
                    monster.giveHp((player.getStr() - monster.getDef()/2)* -1);
                }
            }
            if (!monster.isAlive) {
                player.giveExp(player, 500);
                newLvl();
                player.giveGold(100);
                break;
            }
            if (monster.getStr() > player.getDef()/2) {
                player.giveHp((monster.getStr() - player.getDef()/2)*-1);
            }
            if (player.getHp() < 1) {
                status = 3;
                isAlive = false;
                break;
            }
            paintComponent(this.getGraphics());
            Thread.sleep(250);
        }
    }
    public void newLvl(){
        if (player.getExp() >= 1000){
            player.giveExp(player, -1000);
            status = 4;
            paintComponent(this.getGraphics());
        }
    }
}


