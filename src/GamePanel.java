import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
 
 
public class GamePanel extends JFrame {
	   GamePanel gamep = this;
	   Integer xRow;
	   Integer yRow;
	   int levelNumber;
	   boolean normalModel;
	   boolean playerModel;
	   public String userName;
	   public int userScore;
	   
       public GamePanel(LoginPanel log){
    	
    	xRow = log.xRow; 
    	yRow = log.yRow;
    	normalModel = log.normalModel;
    	playerModel = log.playerModel;
    	levelNumber = log.levelNumber;
        GameBody gamebody=new GameBody();
        gamebody.setBounds(2,10,500,600);  //
        gamebody.setOpaque(false);
        gamebody.setLayout(null);
        addKeyListener(gamebody);
        add(gamebody);
         

        int w=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height;
        ((JPanel)getContentPane()).setOpaque(false);
 

 
        final JMenuItem pauselogin=new JMenuItem("Pause");
        pauselogin.setAccelerator(KeyStroke.getKeyStroke('P'));
        pauselogin.setBounds(xRow*24,370,100,30);
        pauselogin.setBorder(BorderFactory.createRaisedBevelBorder());
        gamebody.add(pauselogin);
        pauselogin.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
                if(pauselogin.getText().equals("Pause")) {
                	gamebody.setStart(true);   //将自动下落线程关闭
                    pauselogin.setText("Countinue");
                }else {
                	gamebody.setStart(false);   //唤醒自动下落线程
                    requestFocus(true);
                    pauselogin.setText("Pause");
                }           

        	}
            
        });
 
        final JMenuItem overlogin=new JMenuItem("Exit");   //退出的按钮
        overlogin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        overlogin.setBorder(BorderFactory.createRaisedBevelBorder());
        overlogin.setBounds(xRow*25,420,80,30);
        gamebody.add(overlogin);
 
        overlogin.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
        	   gamebody.setStart(true);
        	   int result= JOptionPane.showConfirmDialog(null, "Do you want to go back to the menu? ", "Confirm Dialog", JOptionPane.YES_NO_OPTION);
        	   if(result == JOptionPane.OK_OPTION){
        		   dispose();
        		   LoginPanel login = new LoginPanel();
               }else {
            	   gamebody.setStart(false);
               }
           }
        });
         
        setTitle("Tetris");
        setResizable(false);
        setFocusable(true);
        setBounds((w-500)/2,(h-600)/2,xRow*33,yRow*26);
        setLayout(null);
 
        setVisible(true);
        setDefaultCloseOperation(3);
        
    }

    //创建需要定义的局部变量和游戏类
     class GameBody extends JPanel implements KeyListener{
        private int shapeType=-1;  //定义方块的类型  定义的为7中
        private int shapeState=-1; //定义方块为何种状态，每种都有四种状态
        private int nextshapeType=-1;  //定义下一块产生的类型
        private int nextshapeState=-1;  //定义下一块的方块的状态
        private final int CELL=25;   //定义方格的大小
        private int score=0;    //定义显示的成绩
        private int line=0;
        private int left;       //定义初始图形与两边的墙的距离
        private int top;        //定义初始图形与上下墙的距离
        private int i=0;        //表示列
        private int j=0;        //表示行
        public int flag=0;
        public  volatile boolean start=false;  //暂停的判断条件，为轻量锁，保持同步的
        //Timer t;
        Random random=new Random();
        //
        //定义地图的大小，创建二位的数组
        int[][] map=new int[xRow][yRow];
 
        //初始化地图
        public void resetMap(){
          for(i=0;i<xRow-1;i++){
             for(j=0;j<yRow-1;j++){  //遍历的范围不能小
              map[i][j]=0;
            }
          }
        }
 
        //画围墙的方法
        public void drawWall(){
          for(j=0;j<yRow-1;j++)  //0到21行
          {
            map[0][j]=2;
            map[xRow-2][j]=2;    //第0行和第11行为墙
          }
          for(i=0;i<xRow-1;i++){  //0到11列
            map[i][yRow-2]=2;    //第21行划墙
          }
        }
 
 
        //定义随机的图形种类
        private final int[][][] shapes=new int[][][]{
            // i
              {       { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                      { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
                      { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                      { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 } },
              // s
              {     { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                      { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                      { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                      { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
              // z
              {     { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                      { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                      { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                      { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
              // J
              {     { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
                      { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                      { 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                      { 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
              // o
              {     { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                      { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                      { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                      { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
              // L
              {     { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
                      { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                      { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                      { 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
              // t
              {     { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                      { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                      { 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                      { 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 } }
        };
 
 
        //产生新图形的方法
        public void createshape(){
            if(shapeType==-1&&shapeState==-1){
              shapeType = random.nextInt(shapes.length);
              shapeState = random.nextInt(shapes[0].length);
            }else{
              shapeType=nextshapeType;
              shapeState=nextshapeState;
            }
              nextshapeType = random.nextInt(shapes.length);
              nextshapeState = random.nextInt(shapes[0].length);
            //shapeType=(int)(Math.random()*1000)%7;   //在7中类型中随机选取
            //shapeState=(int)(Math.random()*1000)%4;  //在四种状态中随机选取 
            left=4; top=0;  //图形产生的初始位置为（4,0）\
            
           
        }
 
        //遍历[4][4]数组产生的方块并判断状态
        public int judgeState(int left,int top,int shapeType,int shapeState){
            for(int a=0;a<4;a++){
              for(int b=0;b<4;b++){
                if(((shapes[shapeType][shapeState][a*4+b]==1 &&   //遍历数组中为1的个数，即判断是否有图形
                    map[left+b+1][top+a]==1))||                   //判断地图中是否还有障碍物
                    ((shapes[shapeType][shapeState][a*4+b]==1 &&   //遍历数组中为1的个数，即判断是否有图形
                    map[left+b+1][top+a]==2))){                   //判断是否撞墙
                          return 0;    //表明无法不能正常运行
                    }                 
              }
            }
            return 1;
        }
 
 
        //创建键盘事件监听
        public void keyPressed(KeyEvent e){
        //if (start){
          switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
            leftMove();//调用左移的方法
            repaint();
            break;
            case KeyEvent.VK_RIGHT:
            rightMove();//调用右移的方法
            repaint();
            break;
            case KeyEvent.VK_DOWN:
            downMove();//调用左移的方法
            repaint();
            break;
            case KeyEvent.VK_UP:
            turnShape();//调用变形的方法
            repaint();
            break;
          }
        // }
        }
      public void keyReleased(KeyEvent e) {
      }
      public void keyTyped(KeyEvent e) {
      }
 
 
 
      //创建左移的方法
        public void leftMove(){
            if(judgeState(left-1,top,shapeType,shapeState)==1){
                left-=1;
                }
        }
        //创建右移的方法
      public void rightMove(){
            if(judgeState(left+1,top,shapeType,shapeState)==1){
                left+=1;
                };
             
        }
        //创建下移的方法
        public void downMove(){
            if(judgeState(left,top+1,shapeType,shapeState)==1){  //判断有图形
                top+=1;
                deleteLine();   //判断下移后是否有满行
                }
            if(judgeState(left,top+1,shapeType,shapeState)==0){   //判断没有图形
                 
                addshape(left,top,shapeType,shapeState);
                createshape();
                deleteLine();
            }
            
        }
 
 
        //创建旋转变形的方法
        public void turnShape(){
          int tempshape=shapeState;
          shapeState=(shapeState+1)%4; //在四中的状态中选取
          if(judgeState(left,top,shapeType,shapeState)==1){
               
          }
          if(judgeState(left,top,shapeType,shapeState)==0){
                shapeState=tempshape;   //没有图形，不能进行旋转，还原原来状态
          }
                repaint();
        }
 
        //绘图方法
        public void paintComponent(Graphics g){
           super.paintComponent(g);
            
          //绘制围墙
           for(j=0;j<yRow-1;j++){
             for(i=0;i<xRow-1;i++){
               if(map[i][j]==2){//判断是否为墙并绘制
                 g.setColor(Color.blue);
                 g.fill3DRect(i*CELL,j*CELL,CELL,CELL,true);
               }
               if(map[i][j]==0){//判断是否为墙并绘制
                 g.setColor(Color.red);
                 g.drawRoundRect(i*CELL,j*CELL,CELL,CELL,6,6);}
             }
           }
            
           //绘制正在下落的图形
           for(int k=0;k<16;k++){
             if(shapes[shapeType][shapeState][k]==1){
               g.setColor(Color.red);
               g.fill3DRect((left+k%4+1)*CELL,(top+k/4)*CELL,CELL,CELL,true);  //left\top为左上角的坐标
             }
           }
 
           //绘制落下的图形
           for(j=0;j<yRow-1;j++){  
             for(i=0;i<xRow-1;i++){
               if(map[i][j]==1){
                 g.setColor(Color.green);
                 g.fill3DRect(i*CELL,j*CELL,CELL,CELL,true);
               }
             }
           }   
 
           //显示右边预览图形
           for(int i = 0; i < 4; i++) {
             for(int j = 0; j < 4; j++){
                if(shapes[nextshapeType][nextshapeState][i*4+j] == 1) {
                    g.setColor(Color.red);
                    g.fill3DRect(xRow*25+(j*(CELL-10)),190+(i*(CELL-10)), CELL-10, CELL-10,true);
                    }
                 }
            }
            //添加右边预览图形方格
            for(int i = 0; i < 5; i++) {
             for(int j = 0; j < 5; j++){
                    g.setColor(Color.blue);
                    g.drawRoundRect(xRow*24+(j*(CELL-10)),175+(i*(CELL-10)),CELL-10, CELL-10,3,3);
                 }
            }
            
           g.drawString("GP:", xRow*24, 30);
           g.drawString("32", xRow*27, 30); 
           g.drawString("Score:", xRow*24, 50);
           g.drawString(score+"  ", xRow*27, 50);
           g.drawString("E-Line:", xRow*24, 70);
           g.drawString(line+"  ", xRow*27, 70);
           g.drawString("Level:", xRow*24, 90);
           g.drawString(levelNumber+"  ", xRow*27, 90);
           if(playerModel == true) {
        	   g.drawString("Model:", xRow*24, 110);
               g.drawString("Player  ", xRow*27, 110);
           }else {
        	   g.drawString("Model:", xRow*24, 110);
               g.drawString("AI  ", xRow*27, 110);
           }
           if(normalModel == true) {
        	   g.drawString("Model:", xRow*24, 130);
               g.drawString("Normal  ", xRow*27, 130);
           }else {
        	   g.drawString("Model:", xRow*24, 130);
               g.drawString("Extend  ", xRow*27, 130);
           }


 
        }
 
 
    //创建添加新图形到地图的方法、
        public void addshape(int left,int top,int shapeType,int shapeState){
         int temp=0;
           for(int a=0;a<4;a++){
             for(int b=0;b<4;b++){   //对存储方块队的[4][4]数组遍历
                if(map[left+b+1][top+a]==0){ //表明[4][4]数组没有方块
                     
                    map[left+b+1][top+a]=shapes[shapeType][shapeState][temp];
                }
                temp++;
             }   
           }
        }
 
 
       
 
        //创建消行的方法，即将满行以上的部分整体下移
      public void deleteLine(){
          int tempscore=0;      //定义满行的列个数满足1
          int fullLine = 0;
          for(int a=0;a<yRow-1;a++){   //对地图进行遍历
            for(int b=0;b<xRow-1;b++){
              if(map[b][a]==1){    //表示找到满行
                 tempscore++;     // 记录一行有多少个1
                 if(tempscore==xRow-3){
                	 fullLine+=1;
                 for(int k=a;k>0;k--){     //从满行开始回历
                   for(int c=1;c<xRow-1;c++){
                      map[c][k]=map[c][k-1];  //将图形整体下移一行
                   }
                 }
                }
            }
          }
            tempscore=0;
          }
          line+=fullLine;
          if(fullLine == 1)
          {
        	  score+=100; 
          }else if(fullLine == 2) {
        	  score+=300;
          }else if(fullLine == 3) {
        	  score+=600;
          }else if(fullLine == 4){
        	  score+=1000;
          }
          
          /*if(score >= 1000 && score < 2000) {
        	  levelNumber =2;
          }else if(score >= 2000 && score < 4000) {
        	  levelNumber =3;
          }else if(score >= 4000 && score < 8000) {
        	  levelNumber =4;
          }else if(score >= 8000) {
        	  levelNumber =5;
          }*/
        }
 
 
 
      //判断游戏结束，1、判断新块的状态是否不存在，即judgeState()==0
        //2、判断初始产生的位置是否一直为1；
        public int gameOver(int left,int top){
          if(judgeState(left,top,shapeType,shapeState)==0){
             return 1;
          }
          return 0;
        }
 
        //创建构造方法
        public GameBody(){
            resetMap();
            drawWall();
            createshape();
            Thread timer=new Thread(new TimeListener());
            timer.start();
        }
 
        public void setStart(boolean start){   //改变start值的方法
            this.start=start;
        }
 
        //创建定时下落的监听器
        class TimeListener implements Runnable{
          public void run(){
              while(true){
                if(!start){
              try{
                repaint();
                if(judgeState(left,top+1,shapeType,shapeState)==1){
                      top+=1;
                      deleteLine();}
                if(judgeState(left,top+1,shapeType,shapeState)==0){
                    if(flag==1){
                      addshape(left,top,shapeType,shapeState);
                      deleteLine();
                      createshape();
                      flag=0;
                    }
                    flag=1;
                }
                if(gameOver(left,top)==1){ 
                    //判断游戏
              	  if(userName == null) {
                    if(score > 2000) {
                    	String str03 = (String)JOptionPane.showInputDialog(null, "You are the Top 10 Right now,input your name.", "Congratulations",JOptionPane.DEFAULT_OPTION);
                    	if(str03 != null) {
                    		gamep.dispose();
                    		dispose();
                    		userName = str03.toString();
                    		userScore = score;
                    		score=0;
                    		LoginPanel log = new LoginPanel(gamep);
                    	}else {
                    	  resetMap();
                          drawWall();
                          score=0;
                          line=0;  
                          levelNumber = 1;
                    	}     	
                    }else {
                      int i = JOptionPane.showConfirmDialog(null, "Best Try", "Game Over", JOptionPane.DEFAULT_OPTION);
                      if(i==JOptionPane.OK_OPTION) {
                    	  resetMap();
                          drawWall();
                          score=0;
                          line=0;
                          levelNumber = 1; 
                      }
                    }
                    }
                }
                //Set Level
                if(levelNumber == 1) {
                	Thread.sleep(800);
                }else if(levelNumber == 2) {
                	Thread.sleep(400);
                }else if(levelNumber == 3) {
                	Thread.sleep(200);
                }else if(levelNumber == 4) {
                	Thread.sleep(100);
                }else {
                	Thread.sleep(50);
                }
                
          }catch(Exception e){
            e.getMessage();
               }
             } 
            }
          }
        }   
   }   
} //外层