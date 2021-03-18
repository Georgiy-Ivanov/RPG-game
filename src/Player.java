public class Player {
    private int lvl;
    private int hp;
    private int maxHp;
    private int exp;
    private int gold;
    private int str;
    private int def;
    public boolean isAlive;
    private String name;

    public Player() {
    }


    Player(String name){
        this.lvl = 0;
        this.hp = 100;
        this.maxHp = 100;
        this.exp = 0;
        this.isAlive = true;
        this.name = name;
        this.str = 10;
        this.def = 5;
        this.gold = 0;
    }


    public void giveExp(Player player, int exp) {//даем экспы и если экспы больше 1000 то вычитается 1000 из экспы и добавляется уровень
        this.exp += exp;
        if (this.exp >= 1000){
            this.exp -= 1000;
            giveLvl(player);
        }
    }
    private void giveLvl(Player player){
        this.lvl += 1;
        Main.lvlUp(player);//отправляем выводить сообщения и повышать характеристики
    }

    //большая куча геттеров, сеттеров и гивверов
    public int getGold() {
        return gold;
    }

    public void giveGold(int gold) {
        this.gold += gold;
    }

    public void giveHp(int hp) {
        this.hp += hp;
        if (this.hp > this.maxHp) this.hp = this.maxHp;
        if (this.hp <= 0) this.isAlive = false;
    }
    public void giveMaxHp(int hp) {this.maxHp += hp;}
    public void setHp(int hp) {this.maxHp = hp;}
    public void giveStr(int str) {
        this.str += str;
    }
    public int getMaxHp() {
        return maxHp;
    }
    public void giveDef(int def) {
        this.def += def;
    }
    public int getLvl() {
        return lvl;
    }
    public int getHp() {
        return hp;
    }
    public int getStr() {
        return str;
    }
    public int getDef() {
        return def;
    }
    public String getName() {
        return name;
    }
    public int getExp() {
        return exp;
    }
}
