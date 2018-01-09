package nju.edu.cn;
import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

enum DIRECTION{
    right,left,down,up
}

//所有生物的抽象基类
public class Creature implements Runnable {
    private String name;
    private Position position; //每个生物自出生起总要占位
    int life;
    int force;
    boolean islive;
    int speed;
    Image image;//每个生物都有一样的图片
    Image dieimage; //死了的样子
    static final int imagesize=65;
    ArrayList<Creature>enemy;
    Ground g;


    public Creature(String name,int life,int force,int speed){
        this.name=name;
        this.life=life;
        this.force=force;
        islive=true;
        position=null; //出生时不知道自己要站哪里
        this.speed=speed;
        this.image=image;
    }
    String getname(){
        return name;
    }
    public Position getPos()
    {
        return  position;
    }
    public void setPos(Position position)
    {
        if(position==null){ //还没占位或者叫其去空值站
            return;
        }
        this.position = position;
        // this.position.setSitter(this); //在这里同步pos和葫芦娃的动作！！
    }

    void setEnemy(ArrayList<Creature>enemy){
        this.enemy=enemy;
    }

    void setGround(Ground g)
    {
        this.g=g;
    }

    boolean getIslive(){
        return islive;
    }

    public void leavePos(){
        if(this.position!=null)
            this.position.sitterOut();
        this.position=null;
    }
    public boolean subLife(int force){
        if(islive&&life-force>0)
            life-=force;
        else
            islive=false;
        return islive;
    }
    public void go(Ground g,DIRECTION d){
        synchronized (this){
            if(!islive)
                return;
        }
        int x,y;
        synchronized (this.position) {
            x=this.getPos().getX();
            y = this.getPos().getY();
            switch (d) {
                case right:
                    x += speed;
                    break;
                case left:
                    x -= speed;
                    break;
                case up:
                    y -= speed;
                    break;
                case down:
                    y += speed;
                    break;
            }
            if (x < 0 || x + imagesize >= g.getWidth() || y < 35 || y + imagesize >= g.getHeight()) //非法前进
                return;
        }
        synchronized (g.getPosition(x, y)) {
            if (!g.getPosition(x, y).isSitted()) { //要去的位置没被占用
                synchronized (this.position) {
                    leavePos();
                }
                synchronized (g.getPosition(x, y)) {
                    setPos(g.getPosition(x, y));
                }
//            System.out.println(x+" ss"+y);
            }
        }
    }

    void findNearestEnemy(){
        synchronized (this) {
            if (!islive)//死了
                return;
        }
        Position pos;
        int nearx=800;
        int neary=700;
        int index=-1; //最近的那个小怪
        synchronized (this.position) {
            synchronized (position) {
                synchronized (enemy) {
                for(int i=0;i<8;i++) {
                    boolean theisLive = false;
                     //只允许一个人判断死活
                        theisLive = enemy.get(i).getIslive();
                        if (!theisLive)
                            continue;
                        //涉及位置判断要小心
                        Position pos1 = enemy.get(i).getPos();
                        if(pos1==null)
                            continue;
                        if(Math.abs(pos1.getY()-position.getY())<10){
                            nearx = pos1.getX() - position.getX();
                            neary = pos1.getY() - position.getY();
                            index = i;
                            break;
                        }
                        if (Math.abs(pos1.getX() - position.getX()) + Math.abs(pos1.getY() - position.getY()) < Math.abs(nearx) + Math.abs(neary)) {
                            nearx = pos1.getX() - position.getX();
                            neary = pos1.getY() - position.getY();
                            pos = pos1;
                            index = i;
                        }
                    }
                }
            }

            if(index==-1){ //对方死光了
                return;
            }

            if(Math.abs(nearx)<imagesize-10&&Math.abs(neary)<imagesize-10){ //撞上了，战斗
                synchronized (enemy.get(index)) {
                    attackOther(enemy.get(index));
                }
                return;
            }
            synchronized (enemy.get(index)) {
                if (nearx >= 0 && neary >= 0) {
                    if (nearx > neary){// && nearx > speed || neary < speed) {
                        go(g, DIRECTION.right);
                    } else
                        go(g, DIRECTION.down);
                }
                if (nearx < 0 && neary >= 0) {
                    if (-nearx > neary){ //&& -nearx > speed || neary < speed) {
                        go(g, DIRECTION.left);
                    } else {
                        go(g, DIRECTION.down);
                    }
                }
                if (nearx < 0 && neary < 0) {
                    if (-nearx > -neary ){//&& -nearx > speed || -neary < speed) {
                        go(g, DIRECTION.left);
                    } else {
                        go(g, DIRECTION.up);
                    }
                }
                if (nearx >= 0 && neary < 0) {
                    if (nearx> -neary){// && nearx > speed || -neary < speed) {
                        go(g, DIRECTION.right);
                    } else {
                        go(g, DIRECTION.up);
                    }
                }
            }
        }
    }

    public void attackOther(Creature c){}
    void setImage(Image image){
        this.image=image;
    }

    void setDieimage(Image image){
        dieimage=image;
    }

    void drawImage(Graphics g){
        if(islive)
            g.drawImage(image,getPos().getX(),getPos().getY(),imagesize,imagesize,null);
        else
            g.drawImage(dieimage,getPos().getX(),getPos().getY(),imagesize,imagesize,null);
    }

    public void run(){
        //while (true)//!Thread.interrupted())
        {
           // synchronized (this)
            {
                findNearestEnemy();
                //System.out.println(111);
//                try {
////                    Thread.yield();
////                    notifyAll();
//                    //TimeUnit.MILLISECONDS.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                   // break;
//                }
            }
        }
    }

}

class Snake extends Creature{
    public Snake(){
        super("蛇",200,100,5);
    }
    //蛇精生出蝎子精
    public Scorpion giveBirth(){
        Scorpion scorpion=new Scorpion();
        return scorpion;
    }
    @Override
    public void attackOther(Creature c){//主动攻击对方
        Random random=new Random();
        int rightnowforce=(int)(force*0.8+force*random.nextDouble());//随机获得不同的攻击力
        c.subLife(rightnowforce);
    }
}

class Scorpion extends Creature{
    public Scorpion(){
        super("蝎",150,70,5);
    }
    //蝎子精生出一群小弟
    ArrayList<Minion> giveBirth(){
        ArrayList<Minion> minions=new ArrayList<Minion>();
        for(int i=0;i<7;i++){ //连续来7个小弟
            minions.add(new Minion());
        }
        return minions;
    }
    @Override
    public void attackOther(Creature c){//主动攻击对方
        Random random=new Random();
        int rightnowforce=(int)(force*0.6+force*random.nextDouble());//随机获得不同的攻击力
        c.subLife(rightnowforce);
    }
}

class Minion extends Creature{
    public Minion(){
        super("喽",130,50,7);
    }
    @Override
    public void attackOther(Creature c){//主动攻击对方
        Random random=new Random();
        int rightnowforce=(int)(force*0.5+force*random.nextDouble());//随机获得不同的攻击力
        c.subLife(rightnowforce);
    }
}

 class Grandpa extends Creature {
    public Grandpa(){
        super("爷",100,50,4);
    }
    //爷爷精心照料让某个葫芦娃出生，这一切在上帝控制下进行
    Huluwa giveBirth(int i){
        Random random=new Random();
        int sp=7;
        sp=2+(int)(sp*random.nextDouble());
        Huluwa huluwa=new Huluwa(COLOR.values()[i],SENIORTY.values()[i],sp);
        return huluwa;
    }
    @Override
    public void attackOther(Creature c){//主动攻击对方
        Random random=new Random();
        int rightnowforce=(int)(force*0.63+force*random.nextDouble());//随机获得不同的攻击力
        c.subLife(rightnowforce);
    }
}

enum COLOR
{
    RED,ORANGLE,YELLOW,GREEN,LITTLEGREEN,BLUE,PURPLE//,橙,黄,绿,青,蓝,紫
}

enum SENIORTY
{
    fir,sec,thi,four,fif,six,sev//老大,老二,老三,老四,老五,老六,老七
}

class Huluwa extends Creature {
    public COLOR color;
    private SENIORTY seniorty;
    public Huluwa(COLOR color,SENIORTY seniorty,int speed)
    {
        super("娃",130,60,speed);
        this.color=color;
        this.seniorty=seniorty;
    }
    @Override
    String getname(){ //葫芦娃报的是自己更精细的属性
        return color.toString();
    }
    @Override
    public void attackOther(Creature c){//主动攻击对方
        Random random=new Random();
        int rightnowforce=(int)(force*0.7+force*random.nextDouble());//随机获得不同的攻击力
        c.subLife(rightnowforce);
    }
//    public boolean isBiggerThan(Comparable other)
//    {
//        if(other instanceof Huluwa) //可能会有别的类型要和葫芦娃比较,数值小的辈分大
//        {
//            //  System.out.print(myseniorty.ordinal()+" "+((Huluwa) other).myseniorty.ordinal());
//            return (seniorty.ordinal()) > (((Huluwa) other).seniorty.ordinal());
//        }
//        else
//            return false;
//    }
}
