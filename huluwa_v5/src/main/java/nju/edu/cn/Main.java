package nju.edu.cn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

enum STATE{ //游戏状态
    on,off,pause
}

public class Main extends JFrame {
    STATE state;
    Ground ground;
    ArrayList<Creature> justicecamp; //正义联盟
    ArrayList<Creature> evilcamp; //邪恶者

    Image bgpic;
    ArrayList<Image> huluwapic;//葫芦娃的图片
    ArrayList<Image> huluwadie;
    Image grandpapic,grandpadie;
    Image evildie;
    Image minionpic;
    Image snakepic;
    Image scorpic;

    private Image bufferimage; //用于双缓冲的储存


    Main(){
        state=STATE.off;
        ground=new Ground(800,700);
        justicecamp=new ArrayList<Creature>();
        evilcamp=new ArrayList<Creature>();
        try {
            FileInputStream file = new FileInputStream("src/main/java/nju/edu/cn/Images/bg.png");
        }catch (IOException e){
            e.printStackTrace();
        }
        bgpic=new ImageIcon("Images/bg.png").getImage();
        //bgpic=GameUtil.getImage("Images/bg.png");
        grandpapic =new ImageIcon("Images/grandpa.png").getImage();// GameUtil.getImage("Images/grandpa.png");
        minionpic= new ImageIcon("Images/minion.png").getImage();//GameUtil.getImage("Images/minion.png");
        snakepic= new ImageIcon("Images/snake.png").getImage(); //GameUtil.getImage("Images/snake.png");
        scorpic= new ImageIcon("Images/scorpion.png").getImage();//GameUtil.getImage("Images/scorpion.png");
        evildie=new ImageIcon("Images/evildie.png").getImage();//GameUtil.getImage("Images/evildie.png");
        grandpadie=new ImageIcon("Images/grandpadie.png").getImage();//GameUtil.getImage("Images/grandpadie.png");
        huluwapic=new ArrayList<Image>();
        huluwadie=new ArrayList<Image>();
        String name1="Images/wa";//"Images/wa";
        String name2="Images/lu";//"Images/lu";
        String name3=".png";
        for(int i=0;i<7;i++){
            huluwapic.add(new ImageIcon(name1+(i+1)+name3).getImage());//GameUtil.getImage(name1+(i+1)+name3));
            huluwadie.add(new ImageIcon( name2+(i+1)+name3).getImage());//(GameUtil.getImage(name2+(i+1)+name3));
        }
        initialjusticecamp();
    }
    void initialjusticecamp(){
        Grandpa grandpa=new Grandpa();
        grandpa.setImage(grandpapic);
        grandpa.setDieimage(grandpadie);
        justicecamp.add(grandpa);
        for(int i=0;i<7;i++) {
            Huluwa hu=grandpa.giveBirth(i);
            hu.setImage(huluwapic.get(i));
            hu.setDieimage(huluwadie.get(i));
            justicecamp.add(hu);
            allthreads.add(new Thread(justicecamp.get(i)));
        }
        Formation f=new LongSnakeFormation();
        f.setFormation(ground,justicecamp,10,40);

    }
    void initialevilcamp(){
        Snake snake=new Snake();
        evilcamp.add(snake);
        snake.setImage(snakepic);
        snake.setDieimage(evildie);
        Scorpion scorpion=snake.giveBirth();
        evilcamp.add(scorpion);
        scorpion.setImage(scorpic);
        scorpion.setDieimage(evildie);
        ArrayList<Minion>ms=new ArrayList<Minion>();
        ms=scorpion.giveBirth();
        for(int i=0;i<6;i++){
            ms.get(i).setImage(minionpic);
            ms.get(i).setDieimage(evildie);
            evilcamp.add(ms.get(i));
            allthreads.add(new Thread(evilcamp.get(i)));
        }
        Formation f=new CraneWingFormation();
        f.setFormation(ground,evilcamp,520,40);
    }

    void addEnemy(){
        for(int i=0;i<8;i++){
            justicecamp.get(i).setEnemy(evilcamp);
            justicecamp.get(i).setGround(ground);
            evilcamp.get(i).setEnemy(justicecamp);
            evilcamp.get(i).setGround(ground);
        }
    }


    void paintAllCreature(Graphics g){
        g.drawImage(bgpic,0,0,800,700,null);
        for(int i=0;i<8;i++){
            justicecamp.get(i).drawImage(g);
            evilcamp.get(i).drawImage(g);
        }
    }

    void setstate(STATE state){
        this.state=state;
    }

    STATE getstate(){
        return  this.state;
    }

    public void launchFrame() {
        setSize(800, 700);
        setLocation(200, 50);
        setVisible(true);
        setTitle("HuluwaLegion");
        setBackground(Color.WHITE);

        addWindowListener(new WindowAdapter() {
            // 单击右键选择“source”中的“override/implement
            // methods”，frame里面勾选“windowClosed”点击“OK”
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void paint(Graphics g) {
        drawBufferedImage();
        g.drawImage(bufferimage, 0, 0, this);
    }

    private void drawBufferedImage() {
        // 创建缓冲区对象
        bufferimage = createImage(this.getWidth(), this.getHeight());
        // 获取图像上下文对象
        Graphics g = bufferimage.getGraphics();
        paintAllCreature(g);
    }

    void allMoves(){
        for(int i=0;i<8;i++){
            justicecamp.get(i).go(ground,DIRECTION.right);
            evilcamp.get(i).go(ground,DIRECTION.left);
        }
    }

    ExecutorService exec = Executors.newCachedThreadPool();
    ArrayList<Thread>allthreads=new  ArrayList<Thread>();

    public static void main(String args[]){
        Main main=new Main();
        main.launchFrame();
        main.initialjusticecamp();
        main.initialevilcamp();
        main.addEnemy();
        KeyListen keyListen=new KeyListen();
        keyListen.setMain(main);
        main.addKeyListener(keyListen);

        java.util.Timer timer = new Timer();
        MyTask myTask=new MyTask();
        myTask.setMain(main);
        timer.schedule(myTask, 0, 100);
    }
}
class KeyListen implements KeyListener{
    Main main;
    void setMain(Main main){
        this.main=main;
    }
    public void keyPressed(KeyEvent e){
        int t = e.getKeyCode();
        if(t==KeyEvent.VK_SPACE){//&&t<=KeyEvent.VK_Z){
            main.setstate(STATE.on);
        }
        if(t==KeyEvent.VK_P){
            main.setstate(STATE.pause);
        }
        if(t==KeyEvent.VK_L){
            main.setstate(STATE.off);

        }
    }

    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}
}
class MyTask extends TimerTask {
    Main main;
    void setMain(Main main){
        this.main=main;
    }
    @Override
    public void run() {
        if(main.getstate()==STATE.on) {
            for (int i = 0; i < 8; i++) {
                main.exec.execute(main.justicecamp.get(i));
            }
            for (int i = 0; i < 8; i++) {
                main.exec.execute(main.evilcamp.get(i));
            }
        main.repaint();
       }
    }
}