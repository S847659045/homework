关于效果非常一般的本次大作业
=====
内容
----
1.能够在按下空格键以后启动,按下P键暂停，再按空格键恢复运行，但是只能运行一次<br>
2.没能实现回放，时间太少，我能力有限<br>
3.可能有很多BUG<br>
4.可能出现全部线程僵死<br>
5.可能出现空指针异常<br>
6.战斗随机性很强，有时候葫芦娃大获全胜，有时候邪恶势力吞并世界<br>
7.实现的方式有聚集、继承，Creature类是基类，实现了寻找敌人、攻击敌方、自身性命减值、离开某位置、进入某位置、获得所在位置等方法；
8.同样是爷爷生葫芦娃、蛇精生蝎子、蝎子生小弟<br>
9.Ground类实现了一个功能，那就是获得其上的某一位置（利用横纵坐标x,y），其余基本没有，当然其在初始化时已经把他拥有的position全部创立了。<br>
10.窗口是800*700,调大不会随着尺寸改变。<br>
11.Main类继承窗口JFrame，实现键盘监听事件和时钟监测。<br>
12.尸体可能出现堆在一起的情况，因为我没有让把格子划分出来，一像素就是一个格子<br>
13.判断敌人是找离自己最近的并且在x\y方向上选绝对值大的那一端移动。<br>
14.为了防止线程打乱，一旦开始找敌人便锁定那个正在找敌人的生物的位置，并且在判断的时候锁住当前被判断的敌人的位置和是否死亡状态<br>
15.大概可以同时被打和打别人。<br>
16.初始化葫芦娃为长蛇，邪恶势力为鹤翼对阵。<br>
17.Test了Formation类的setForamtion方法。<br>
18.用了异常处理、集合框架、最简单的注解@override，生物体Creature实现了Runnable接口的run函数。框架Main类有一个线程池。框架Main内储存了所有的图片。<br>
19.葫芦娃死后变成对应颜色的葫芦，爷爷死后变成坟墓，妖怪死后变成鬼<br>
20.应该没了，毕竟简陋。<br>

最后
----
附带之前交的还没被收的全部作业<br>
望曹老板原谅我这一个也许连mvn test都弄不起来的简陋代码，新年快乐。
