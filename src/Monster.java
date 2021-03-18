public class Monster{
    private int hp;
    private int def;
    private int str;
    public boolean isAlive;

    Monster(int lvl) {
        this.hp = 100 + (lvl * 4);
        this.str = 10 + (lvl * 3);
        this.def = 5 + (lvl * 3);
        this.isAlive = true;
    }

    public void giveHp(int hp){
        this.hp += hp;
        if(this.hp <= 0) isAlive = false;
    }
    public int getHp(){return hp;}
    public int getStr(){return str;}
    public int getDef(){return def;}
}
