package nju.edu.cn;
import java.util.ArrayList;

abstract public class Formation {
    static final int size=70;
    void setFormation(Ground g, ArrayList<Creature> creatures,int x,int y){//x和y是阵型的起点
    }
}
class LongSnakeFormation extends Formation{
    @Override
    void setFormation(Ground g, ArrayList<Creature> creatures,int x,int y){//x和y是阵型的起点
            for(int i=0;i<8;i++){
                creatures.get(i).leavePos();
            }
            creatures.get(0).setPos(g.getPosition(x,y));
            for(int i=1;i<8;i++){
                creatures.get(i).setPos(g.getPosition(x,y+size*i));
            }
    }
}

class CraneWingFormation extends Formation{
    @Override
    void setFormation(Ground g, ArrayList<Creature> creatures,int x,int y){//x和y是阵型的起点
        //之前的位置退出
        for(int i=0;i<8;i++){
            creatures.get(i).leavePos();
        }

        creatures.get(0).setPos(g.getPosition(x+3*size,y));
        creatures.get(1).setPos(g.getPosition(x,y+4*size));
        for(int i=2;i<5;i++){
            creatures.get(i).setPos(g.getPosition(x+(i-1)*size,y+size*(5-i)));
        }
        for(int i=5;i<8;i++){
            creatures.get(i).setPos(g.getPosition(x+(i-4)*size,y+size*i));
        }
    }
}