public class Player {
    private int lvl;
    private int hp;
    private int maxHp;
    private int exp;
    private int gold;
    private int str;
    private int def;
    private static boolean hasSword;
    private static boolean hasShield;
    public boolean isAlive;

    Player(){
        this.lvl = 0;
        this.hp = 100;
        this.maxHp = 100;
        this.exp = 0;
        this.isAlive = true;
        this.str = 10;
        this.def = 5;
        this.gold = 0;
        this.hasSword = false;
        this.hasShield = false;

    }


    public void giveExp(Player player, int exp) {//даем экспы и если экспы больше 1000 то вычитается 1000 из экспы и добавляется уровень
        this.exp += exp;
        if (this.exp >= 1000){
            giveLvl(player);
        }
    }
    private void giveLvl(Player player){
        this.lvl += 1;
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
    }
    public void giveMaxHp(int hp) {this.maxHp += hp;}
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
    public int getExp() {
        return exp;
    }
    public boolean getHasSword() {return hasSword;}
    public boolean getHasShield() {return hasShield;}
    public void setMaxHp(int hp) {this.maxHp = hp;}
    public void setHp(int hp) {this.hp = hp;}
    public void setLvl(int lvl) {this.lvl = lvl;}
    public void setStr(int str) {this.str = str;}
    public void setDef(int def) {this.def = def;}
    public void setExp(int exp) {this.exp = exp;}
    public void setGold(int gold) {this.gold = gold;}
    public void setHasSword(Boolean hasSword) {this.hasSword = hasSword;}
    public void setHasShield(Boolean hasShield) {this.hasShield = hasShield;}

}
