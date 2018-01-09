package nju.edu.cn;

public class Position {
    private int x,y;
    //private Creature sitter; //上面的葫芦娃
    private boolean issitted; //看是否被占用
    public void sitterin()//(Creature sitter)
    {
        //this.sitter=sitter;
        this.issitted=true;
    }
    //    public Creature getSitter()
//    {
//        return sitter;
//    }
    public void sitterOut(){
        //sitter=null;
        this.issitted=false;
    }
    public boolean isSitted(){
        return this.issitted;
    }

    public void setXY(int x,int y) {
        this.x=x;
        this.y=y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Position(int x,int y)
    {
        super();
        this.issitted=false; //没被占用
        this.x=x;
        this.y=y;
    }
}
