package nju.edu.cn;//package test;
import java.util.*;

import nju.edu.cn.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.rules.Timeout;

/**
* Formation Tester.
*
* @author <Authors name>
* @since <pre>һ�� 8, 2018</pre>
* @version 1.0
*/
public class FormationTest {

    Formation f;
    Ground ground;
    ArrayList<Creature> creatures;
    int x;
    int y;
@Before
public void before() throws Exception {

    f=new LongSnakeFormation();
    ground=new Ground(800,700);
    creatures=new ArrayList<Creature> ();
    for(int i=0;i<8;i++){
        creatures.add(new Snake());
    }

    x=40;
    y=40;
}

@After
public void after() throws Exception {

}

/**
*
* Method: setFormation(Ground g, ArrayList<Creature> creatures, int x, int y)
*
*/
@Test
public void testSetFormation() throws Exception {
//TODO: Test goes here...
    f.setFormation(ground,creatures,x,y);
    assert (ground.getPosition(x,y)!=null);
    assert (creatures.get(0).getPos().getX()==40);
}


}
