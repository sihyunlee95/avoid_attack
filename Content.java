import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;
import java.util.Random;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import java.awt.image.*;
public class Content extends JPanel implements KeyListener
{    
    BufferedImage fail;
    Student s;
    Credit[] c=new Credit[120];
    int num;//number of balls Initiated
    double lev;//lev corresponds to speed of ball
    int pcount;//counts how many Pattern methods were used
    Teacher t;
    int Stage;//Stage #
    Content(){
        this.addKeyListener(this);
        stage1();
        try{
            fail=ImageIO.read(new File ("fail.jpg"));
        }
        catch (Exception e){
            System.out.println("error");
        }
    }
    //stage x method resets/initiates stage
    public void stage1(){
        s=new Student(50,50,10,330);
        s.grade=100;
        num=75;
        lev=0.8;
        t=new Teacher(300,350,75,100,50,2,1);
        t.Initiate(c,num,lev);
        Stage=1;
    }
    public void stage2(){
        s=new Student(50,50,10,330);
        s.grade=100;
        num=60;
        lev=0.8;
        t=new Teacher(300,350,80,112,50,1,2);
        t.Initiate(c,num,lev);
        Stage=2;
    }
    public void stage3(){
        s=new Student(50,50,10,330);
        s.grade=100;
        num=75;
        lev=0.95;
        t=new Teacher(300,350,93,123,50,1,3);
        t.Initiate(c,num,lev);
        Stage=3;
    }
    public void stage4(){
        s=new Student(50,50,10,350);
        s.grade=100;
        num=75;
        lev=1.25;
        t=new Teacher(300,350,108,132,50,1,4);
        t.Initiate(c,num,lev);
        Stage=4;
    }
    public void stage5(){
        s=new Student(50,50,10,375);
        s.grade=100;
        num=75;
        lev=1.4;
        t=new Teacher(400,500,130,150,50,1,5);
        t.Initiate(c,num,lev);
        Stage=5;
    }
    public void stage6(){
        s=new Student(50,50,10,350);
        s.grade=100;
        num=75;
        lev=1.55;
        t=new Teacher(400,500,119,143,50,1,6);
        t.Initiate(c,num,lev);
        Stage=6;
    }
    public void stage7(){
        s=new Student(50,50,10,375);
        s.grade=100;
        num=75;
        lev=1.7;
        t=new Teacher(400,500,141,162,50,1,7);
        t.Initiate(c,num,lev);
        Stage=7;
    }
    public void stage8(){
        s=new Student(50,50,10,425);
        s.grade=100;
        num=54;
        lev=2.0;
        t=new Teacher(300,300,155,177,50,1,8);
        t.Initiate(c,num,lev);
        Stage=8;
    }
    public void stage9(){
        s=new Student(50,50,10,375);
        s.grade=100;
        num=54;
        lev=2.4;
        t=new Teacher(300,300,180,240,50,1,9);
        t.Initiate(c,num,lev);
        Stage=9;
    }
    public void stage10(){
        s=new Student(50,50,10,375);
        s.grade=100;
        num=0;
        lev=1.5;
        t=new Teacher(300,300,180,240,50,1,10);
        t.Initiate(c,num,lev);
        Stage=10;
    }
    
    public void update(long time){
        this.requestFocus();
        s.update(time);
        t.update(time,s);
        int cempty=0;
        if(s.grade<51 || t.whip<=0){
            for(int i=0;i<c.length;i++){
                c[i].xv=0;
                c[i].yv=0;
                s.xv=0;
                s.yv=0;
            }//stop everything when game over or cleared
        }
        for(int i=0;i<c.length;i++){
            if(!c[i].valid) cempty++;
            c[i].update(time,s);
        }
        if(cempty==c.length) {//if all empty
            if(pcount<12){//the angrier the teacher, the more Patterns used
                if(pcount%2==0) t.PatternA1(c,lev);
                else t.PatternA2(c,lev);
                //alternate between Patterns
                pcount++;
            }
            else{
                pcount=0;
                t.Initiate(c,num,lev); //after enough Patterns, go back to Initiation style
            }
        }
        
    }
    
    public void paint(Graphics g){
        Color k=new Color(0,0,0);
        g.setColor(k);
        g.fillRect(0, 0, 600, 600);//black background
        if(s.grade<51){g.drawImage(fail,0,0,null);}//fail message
        
        t.draw(g);
        for(int i=0;i<c.length;i++) c[i].draw(g);
        
        s.draw(g);
        
        
    }
    
    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){}
    //unnecessary because once Type gives new direction nothing changes
    
    public void keyTyped(KeyEvent e){
        
        //variables for indicating new direction
        int xd=0;
        int yd=0;
        
        if(e.getKeyChar()=='i'||e.getKeyChar()=='e') yd=-1; //up
        if(e.getKeyChar()=='k'||e.getKeyChar()=='d') yd=1; //down
        if(e.getKeyChar()=='j'||e.getKeyChar()=='s') xd=-1; //left
        if(e.getKeyChar()=='l'||e.getKeyChar()=='f')  xd=1; //right
        s.changeDirection(xd,yd);
        
        //stage selection
        if(e.getKeyChar()=='1') stage1();
        if(e.getKeyChar()=='n'){
            if(t.state==2){
                if(Stage==1) stage2();
                else if(Stage==2) stage3();
                else if(Stage==3) stage4();
                else if(Stage==4) stage5();
                else if(Stage==5) stage6();
                else if(Stage==6) stage7();
                else if(Stage==7) stage8();
                else if(Stage==8) stage9();
                else if(Stage==9) stage10();
            }
        }
        if(e.getKeyChar()=='r'){
            if(Stage==1) stage1();
            else if(Stage==2) stage2();
            else if(Stage==3) stage3();
            else if(Stage==4) stage4();
            else if(Stage==5) stage5();
            else if(Stage==6) stage6();
            else if(Stage==7) stage7();
            else if(Stage==8) stage8();
            else if(Stage==9) stage9();
            else if(Stage==10) stage10();
        }
        /*
        if(e.getKeyChar()=='2') stage2();
        if(e.getKeyChar()=='3') stage3();
        if(e.getKeyChar()=='4') stage4();
        if(e.getKeyChar()=='5') stage5();
        if(e.getKeyChar()=='6') stage6();
        if(e.getKeyChar()=='7') stage7();
        if(e.getKeyChar()=='8') stage8();
        if(e.getKeyChar()=='9') stage9();
        if(e.getKeyChar()=='0') stage10();
        */
        
        //cheatkeys, for those interested in how hard the end of each stage is
        if(e.getKeyChar()=='z') s.grade=s.grade+5; //strengthen player
        if(e.getKeyChar()=='x') t.whip=t.whip-5; //weaken boss
    }
    
}