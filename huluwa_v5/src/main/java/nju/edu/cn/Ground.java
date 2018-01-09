package nju.edu.cn;
import java.util.ArrayList;

public class Ground {
    //static final String PLACE_HOLDER="🈳";
    private int width;
    private int height;
    Position positions[][];
    ArrayList exists; //存在的是两个阵营
    public Ground(int width,int height){
        this.width=width;
        this.height=height;
        positions=new Position[width][height];//直接创建所有的土地以防后面难以找到
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++)
                positions[i][j]=new Position(i,j);
        }
        exists=new ArrayList();
    }
    Position getPosition(int x,int y){
        if(x+60<width&&y+60<height)
            return positions[x][y];
        else
            return null;
    }
    int getWidth(){
        return width;
    }
    int getHeight(){
        return height;
    }
//
//    int getSize(){
//        return  size;
//    }
//
//    public void firstlayout(Creature creature,Position position){ //第一次放置一个生物的position
//        Position pos=creature.getPos();
//        if(!position.isSitted()) //当前位置没有被占用过
//        {
//            creature.setPos(position);
//        }
//        //否则维持原来的位置
//    }
//
//    public void layoutLeader(Creature creature,Position position)throws LayOutException{
//        Position pos=creature.getPos();
//        if(!position.isSitted()) //当前位置没有被占用过
//        {
//            creature.setPos(position);
//        }
//        else{
//            throw new LayOutException("the position had been taken!");
//        }
//
//        exists.add(creature); //放下一个队伍的Leader
//    }
//
//    public void layoutFormation(Formation formation, Position position) throws LayOutException{
//        //判断放下之后是否导致超出给定的边界
//        if ((position.getX() + formation.getWidth() > size)
//                || (position.getY() + formation.getHeight() > size))
//            throw new LayOutException("Out of bounds"); //out of bounds
//
//
//        Position pos = formation.getBeginPosition();
//
//        formation.setBeginPosition(position);
//
//        //放置后可能出现拥挤的情况
//        for (Object exist : exists) {
//            if (exist instanceof Formation&&conflict((Formation) exist, formation)) {
//                formation.setBeginPosition(pos); //放回原位，不允许移动
//                throw new LayOutException("Conflicted");
//            }
//
//        }
//
//
//        exists.add(formation);
//    }
//
//    private boolean conflict(Formation f1, Formation f2) {
//        if (contains(f1, f2))
//            return true;
//
//        if (contains(f2, f1))
//            return true;
//
//        if (overlap(f1, f2))
//            return true;
//
//        if (overlap(f2, f1))
//            return true;
//
//        return false;
//    }
//
//    private boolean contains(Formation f1, Formation f2) {
//        if ((f1.getBeginPosition().getX() >= f2.getBeginPosition().getX())
//                && (f1.getBeginPosition().getY() >= f2.getBeginPosition().getY())
//                && (f1.getBeginPosition().getX() + f1.getWidth() <= f2.getBeginPosition().getX() + f2.getWidth())
//                && (f1.getBeginPosition().getY() + f1.getWidth() <= f2.getBeginPosition().getY() + f2.getHeight())) {
//            return true;
//        }
//        return false;
//    }
//
//    private boolean overlap(Formation f1, Formation f2) {
//
//        if (((f1.getBeginPosition().getX() + f1.getWidth() >= f2.getBeginPosition().getX()))
//                && (f1.getBeginPosition().getY() + f1.getHeight() >= f2.getBeginPosition().getY())
//                && (f1.getBeginPosition().getX() <= f2.getBeginPosition().getX())
//                && (f1.getBeginPosition().getY() <= f2.getBeginPosition().getY()))
//            return true;
//        return false;
//    }
//
//
//
//    void deleteLeader(Creature creature){
//        for(Object o:exists){
//            if(creature==(Creature)(o)){
//                exists.remove(o);
//                break;
//            }
//        }
//    }
//
//    void deleteFormation(Formation formation){
//        for(Object o:exists){
//            if(o instanceof Formation&&formation==(Formation)(o)){
//                exists.remove(o);
//                break;
//            }
//        }
//    }
//
//    @Override
//    public String toString() {
//        String content[][]=new String[size][size];
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                content[i][j] = PLACE_HOLDER;
//            }
//        }
//        for(Object o:exists) {
//            if(o instanceof Creature){ //是一个生物
//                Creature c = (Creature) (o);
//                content[c.getPos().getX()][c.getPos().getY()] = c.getName();
//            }
//            else { //是一个队形
//                Formation f = (Formation) (o);
//                for (int i = 0; i < f.getWidth(); i++) {
//                    for (int j = 0; j < f.getHeight(); j++) {
//                        content[i + f.getBeginPosition().getX()][j + f.getBeginPosition().getY()] = f.getContent()[i][j];
//                    }
//                }
//            }
//        }
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < content.length; i++) {
//            for (int j = 0; j < content[i].length; j++) {
//                sb.append(content[i][j] + " ");
//            }
//            sb.append("\n");
//        }
//
//        return sb.toString();
//    }
}
