import java.util.Random;
import java.util.Scanner;

public class Main {
    private static boolean hasSword = false;
    private static boolean hasShield = false;
    private static boolean isTrue = true;
    private static String msg;
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rand = new Random();
    private static final Monster dragon = new Monster();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Салют всем кто проверяет этот код. Думаю ты знаешь что произойдет дальше. ");
        System.out.println("Введите имя персонажа");
        Player player = new Player();
        while (player.isAlive) {
            lines();
            System.out.println("Приветствую тебя, " + player.getName() + ". Чего делаем?(введите цифру)");
            System.out.println("1.Идем на арену драться.");
            System.out.println("2.Идем в магазин закупаться.");
            System.out.println("3.Узнаем о себе всякое.");
            System.out.println("4.Спасти принцессу");
            System.out.println("5.Убить себя");
            isTrue = true;
            label:
            while (isTrue) {
                msg = sc.nextLine();
                switch (msg) {
                    case "1":
                        fight(player);
                        isTrue = false;
                        break;
                    case "2":
                        shop(player);
                        isTrue = false;
                        break;
                    case "3":
                        info(player);
                        isTrue = false;
                        break;
                    case "4":
                        savePrincess(player);
                        isTrue = false;
                        break;
                    case "5":
                        player.isAlive = false;
                        break label;
                    default:
                        System.out.println("Что то на эльфийском. Не понятно.");
                        break;
                }
            }
        }
        System.out.println("Игра окончена.");
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

    static void fight(Player player) throws InterruptedException {
        Monster monster = new Monster();//создаем монстра для битвы в соответствии с уровнем игрока
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
        Thread.sleep(1000);
        }
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




