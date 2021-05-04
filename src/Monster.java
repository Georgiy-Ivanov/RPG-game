public class Monster{
    private int hp;
    private int def;
    private int str;
    public boolean isAlive;

    Monster() {
        this.hp = 100;
        this.str = 10;
        this.def = 5;
        this.isAlive = true;
    }

    public void giveHp(int hp){
        this.hp += hp;
        if(this.hp <= 0) {isAlive = false;}
    }
    public int getHp(){return hp;}
    public int getStr(){return str;}
    public int getDef(){return def;}
    public void setlvl(int i) {
        this.hp = 100 + (i * 2);
        this.str = 10 + (i * 5);
        this.def = 5 + (i * 5);
    }
}
