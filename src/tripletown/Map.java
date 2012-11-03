/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tripletown;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 *
 * @author Eric Hsueh
 */
public class Map extends JFrame {

    /**
     * Creates new form Map
     */
    public Map() {
        initComponents();
        basicFrame.setSize(530, 660);
        basicFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        basicFrame.getContentPane().setLayout(new FlowLayout());
        for (int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                Button b = new Button(this,i,j);
                setImg(b, 0);
                b.setPreferredSize(new Dimension(80, 80));
                buttonArr[i][j] = b;
                basicFrame.add(b);
            }
        }
        randNext();
        basicFrame.add(new JLabel("Next : "));
        
        basicFrame.add(nextOne);
        basicFrame.add(new JLabel("Stored : "));
        storage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(isFirstStore) {
                    setImg(storage, nextOne.getImgInt());
                    randNext();
                    isFirstStore = false;
                }
                else {
                    int nextImg = nextOne.getImgInt();
                    setImg(nextOne, storage.getImgInt());
                    setImg(storage, nextImg);
                }
            }
        });
        basicFrame.setVisible(true);
    }

    public void setImg(Button b, int imgNum) {
        String imgName = "";
        if(imgNum == 0) {
            imgName = "ground";
        }
        else if(imgNum == 1) {
            imgName = "grass";
        }
        else if(imgNum == 2) {
            imgName = "bush";
        }
        else if(imgNum == 3) {
            imgName = "tree";
        }
        else if(imgNum == 4) {
            imgName = "hut";
        }
        else if(imgNum == 5) {
            imgName = "house";
        }
        else if(imgNum == 6) {
            imgName = "mension";
        }
        else if(imgNum == 7) {
            imgName = "castle";
        }
        else if(imgNum == 8) {
            imgName = "floatingcastle";
        }
        else if(imgNum == 9) {
            imgName = "triplecastle";
        }
        else if(imgNum == 10) {
            imgName = "bear";
        }
        else if(imgNum == 11) {
            imgName = "tomb";
        }
        else if(imgNum == 12) {
            imgName = "church";
        }
        else if(imgNum == 13) {
            imgName = "cathedral";
        }
        else if(imgNum == 14 || imgNum == 19) {
            imgName = "treasure";
        }
        else if(imgNum == 15 || imgNum == 20) {
            imgName = "bigtreasure";
        }
        else if(imgNum == 16) {
            imgName = "ninja";
        }
        else if(imgNum == 17) {
            imgName = "rock";
        }
        else if(imgNum == 18) {
            imgName = "mountain";
        }
        else if(imgNum == 21) {
            imgName = "crystal";
        }
        else if(imgNum == 22) {
            imgName = "robot";
        }
        
        
        try {
            Image img = ImageIO.read(getClass().getResource("../pic/" + imgName + ".png"));
            b.setIcon(new ImageIcon(img));
            b.setImgInt(imgNum);
        }
        catch (IOException ex) {
            System.out.println("Error setting Image to button");
        }
    }
    
    //randomly generate next object when the program and after each available click
    public void randNext() {
        Random generator = new Random();
        int roll = generator.nextInt(100) + 1;
        //grass 60%, bush 20%, tree 5%, hut 3%, bear 5%, ninja 2%, robot 3%, crystal 2% 
        
        if(roll < 60) {
            setImg(nextOne, grass);
        }
        /*
        else if(roll >= 60 && roll < 80) {
            setImg(nextOne, bush);
        }
        */
        /*
        else if(roll >= 80 && roll < 85) {
            setImg(nextOne, tree);
        }
        else if(roll >= 85 && roll < 88 ) {
            setImg(nextOne, hut);
        }
        else if(roll >= 88 && roll <= 100 ) {
            setImg(nextOne, bear);
        }
        */
        /////////////////////////////////////////////////////////////////////////////
        //ignore ninja, robot, crystal.                                            //
        //once implemented, remember to change the upper bound of bear back to 93  //
        /////////////////////////////////////////////////////////////////////////////
        /*
        else if(roll >= 93 && roll < 95 ) {
            setImg(nextOne, ninja);
        }
        
        else if(roll >= 80 && roll < 90 ) {
            setImg(nextOne, robot);
        }
        */
        else if(roll >= 60 && roll <= 100 ) {
            setImg(nextOne, crystal);
        }
        
    }
    
    public boolean search(Button b) {
       
        Vector<Button> vec = new Vector<Button>();
        Vector<Button> visited = new Vector<Button>();
        Vector<Button> connectButton = new Vector<Button>();
        int count = 0;
        vec.add(b);
        //normal building
        if((b.getImgInt() < triplecastle) || (b.getImgInt() >= tomb && b.getImgInt() < bigtreasure)
                || (b.getImgInt() >= rock && b.getImgInt() < 20)) {
            while(!vec.isEmpty()) {
                Button but = vec.firstElement();
                vec.remove(0);
                if(!visited.contains(but)) {
                    visited.add(but);
                    int r = but.getRow();
                    int c = but.getColumn();
                    int imgInt = but.getImgInt();
                    if(b.getImgInt() == imgInt) {
                        count++;
                        connectButton.add(but);
                        //up button
                        if(c - 1 >= 0) {
                            vec.add(buttonArr[r][c-1]);
                        }
                        //left button
                        if(r - 1 >= 0) {
                            vec.add(buttonArr[r-1][c]);                      
                        }
                        //down button
                        if(c + 1 < 6) {
                           vec.add(buttonArr[r][c+1]);                      
                        }
                        //right button
                        if(r + 1 < 6) {
                            vec.add(buttonArr[r+1][c]);                      
                        }
                    }
                }
            }
            if(count >= 3 && b.getImgInt() != triplecastle && b.getImgInt() != bigtreasure && b.getImgInt() != 21) {
                int newImgInt = b.getImgInt() + 1;
                for(int i = 0; i < connectButton.size(); i++) {
                    setImg(connectButton.get(i), ground);
                }
                setImg(b, newImgInt);
                return true;
            }
            else return false;
        }
        System.err.println("this type of img cant be search");
        return false;
    }
    
    //check if the bears cant move anymore
    public Vector<Button> EnoughSpace(Button b) {
        Vector<Button> vec = new Vector<Button>();
        Vector<Button> visited = new Vector<Button>();
        Vector<Button> connectButton = new Vector<Button>();
        int spaceNum = 0;
        int bearNum = 0;
        vec.add(b);
        while(!vec.isEmpty()) {
            Button but = vec.firstElement();
            vec.remove(0);
            if(!visited.contains(but)) {
                visited.add(but);
                int r = but.getRow();
                int c = but.getColumn();
                int imgInt = but.getImgInt();
                //System.err.println("check space");
                //System.err.println("(r,c) = (" + r + "," + c + ")  " + imgInt);
                if(imgInt == bear) {
                    bearNum++;
                }
                if(imgInt == ground || imgInt == bear) {
                    spaceNum++;
                    connectButton.add(but);
                    //up button
                    if(c - 1 >= 0) {
                        vec.add(buttonArr[r][c-1]);
                    }
                    //left button
                    if(r - 1 >= 0) {
                        vec.add(buttonArr[r-1][c]);                      
                    }
                    //down button
                    if(c + 1 < 6) {
                       vec.add(buttonArr[r][c+1]);                      
                    }
                    //right button
                    if(r + 1 < 6) {
                        vec.add(buttonArr[r+1][c]);                      
                    }
                }
            }
        }
        //System.err.println("spaceNum = " + spaceNum);
        //System.err.println("bearNum = " + bearNum);
        if(spaceNum > bearNum) {
            return (null);
        }
        else {
            return connectButton;
        }
    }
    
    public void killBears(Vector<Button> bearsToTomb, Button now) {
        if(bearsToTomb.size() >= 3) {
            Button biggest = new Button(this, 7, 7);
            int temp = 0;
            for(int i = 0; i < bearsToTomb.size(); i++) {
                if(bearsToTomb.get(i).bearCount > temp) {
                    temp = bearsToTomb.get(i).bearCount;
                    biggest = bearsToTomb.get(i);
                }
            }
            for(int i = 0; i < bearsToTomb.size(); i++) {
                setImg(bearsToTomb.get(i), ground);
            }
            setImg(biggest, church);
            while(search(biggest));    
        }
        else {
            for(int i = 0; i < bearsToTomb.size(); i++) {
                setImg(bearsToTomb.get(i), tomb);
            }
            while(search(now));
        }
    }
    
    
    
    public void checkBear(Button b) {
        //after a building has been placed, checking if there is any bear that become tomb
        int r = b.getRow();
        int c = b.getColumn();
        if(c - 1 >= 0) {
            if(buttonArr[r][c-1].getImgInt() == bear) {
                Vector<Button> bearsToTomb;
                bearsToTomb = EnoughSpace(buttonArr[r][c-1]);  
                if(bearsToTomb != null) {
                    killBears(bearsToTomb, buttonArr[r][c-1]);
                }
            }
        }
        if(r - 1 >= 0) {                    
            if(buttonArr[r-1][c].getImgInt() == bear) {
                Vector<Button> bearsToTomb;
                bearsToTomb = EnoughSpace(buttonArr[r-1][c]);  
                if(bearsToTomb != null) {
                    killBears(bearsToTomb, buttonArr[r-1][c]);
                }
            }
        }
        if(c + 1 < 6) {                     
            if(buttonArr[r][c+1].getImgInt() == bear) {
                Vector<Button> bearsToTomb;
                bearsToTomb = EnoughSpace(buttonArr[r][c+1]);  
                if(bearsToTomb != null) {
                    killBears(bearsToTomb, buttonArr[r][c+1]);
                }
            }
        }
        if(r + 1 < 6) {
            if(buttonArr[r+1][c].getImgInt() == bear) {
                Vector<Button> bearsToTomb;
                bearsToTomb = EnoughSpace(buttonArr[r+1][c]);  
                if(bearsToTomb != null) {
                    killBears(bearsToTomb, buttonArr[r+1][c]);
                }
            }
        }
        
    }
    
    public Vector<Button> getBears() {
        Vector<Button> bears = new Vector<Button>();
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                if(buttonArr[i][j].getImgInt() == bear) {
                    //System.err.println("bear added");
                    bears.add(buttonArr[i][j]);
                }
            }
        }
        return bears;
    }

    public void bearMove(Vector<Button> bv) {
        //use bears vector to get the bears move one by one
        //each randomly choose a new spot if the is any "ground" space
        for(int i = 0; i < bv.size(); i++) {
            bearMoveToEmpty(bv.get(i));
        }
    }
    
    public void bearMoveToEmpty(Button b) {
        int r = b.getRow();
        int c = b.getColumn();
        Vector<Button> empty = new Vector<Button>();
        
        if(c - 1 >= 0) {
            if(buttonArr[r][c-1].getImgInt() == ground) {
                empty.add(buttonArr[r][c-1]);
            }
        }
        if(r - 1 >= 0) {                    
            if(buttonArr[r-1][c].getImgInt() == ground) {
                empty.add(buttonArr[r-1][c]);
            }
        }
        if(c + 1 < 6) {                     
            if(buttonArr[r][c+1].getImgInt() == ground) {
                empty.add(buttonArr[r][c+1]);
            }
        }
        if(r + 1 < 6) {
            if(buttonArr[r+1][c].getImgInt() == ground) {
                empty.add(buttonArr[r+1][c]);
            }
        }
        Random generator = new Random();
        if(!empty.isEmpty()) {
            int emp = generator.nextInt(empty.size());
            setImg(empty.get(emp), bear);
            empty.get(emp).setBearCount(bearCount);
            bearCount++;
            setImg(b, ground);
        }
    }
    
    public boolean putCrystal(Button b) {
        
        Comparator<Button> comp = new Comparator<Button>() {
            public int compare(Button but1, Button but2){
                if(but1.getImgInt() > but2.getImgInt()) {
                    return 1;
                }
                else if(but1.getImgInt() < but2.getImgInt()) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        };
        PriorityQueue<Button> buildingQueue = new PriorityQueue<Button>(4,comp);
        PriorityQueue<Button> churchQueue = new PriorityQueue<Button>(4,comp);
        PriorityQueue<Button> stoneQueue = new PriorityQueue<Button>(4,comp);
        
        int r = b.getRow();
        int c = b.getColumn();
        if(c - 1 >= 0) {
            if(buttonArr[r][c-1].getImgInt() > ground && buttonArr[r][c-1].getImgInt() < triplecastle) {
                buildingQueue.add(buttonArr[r][c-1]);
            }
            else if(buttonArr[r][c-1].getImgInt() >= tomb && buttonArr[r][c-1].getImgInt() < bigtreasure) {
                churchQueue.add(buttonArr[r][c-1]);
            }
            else if(buttonArr[r][c-1].getImgInt() == rock || buttonArr[r][c-1].getImgInt() == mountain) {
                stoneQueue.add(buttonArr[r][c-1]);
            }
        }
        if(r - 1 >= 0) {                    
            if(buttonArr[r-1][c].getImgInt() > ground && buttonArr[r-1][c].getImgInt() < triplecastle) {
                buildingQueue.add(buttonArr[r-1][c]);
            }
            else if(buttonArr[r-1][c].getImgInt() >= tomb && buttonArr[r-1][c].getImgInt() < bigtreasure) {
                churchQueue.add(buttonArr[r-1][c]);
            }
            else if(buttonArr[r-1][c].getImgInt() == rock || buttonArr[r-1][c].getImgInt() == mountain) {
                stoneQueue.add(buttonArr[r-1][c]);
            }
        }
        if(c + 1 < 6) {                     
            if(buttonArr[r][c+1].getImgInt() > ground && buttonArr[r][c+1].getImgInt() < triplecastle) {
                buildingQueue.add(buttonArr[r][c+1]);
            }
            else if(buttonArr[r][c+1].getImgInt() >= tomb && buttonArr[r][c+1].getImgInt() < bigtreasure) {
                churchQueue.add(buttonArr[r][c+1]);
            }
            else if(buttonArr[r][c+1].getImgInt() == rock || buttonArr[r][c+1].getImgInt() == mountain) {
                stoneQueue.add(buttonArr[r][c+1]);
            }
        }
        if(r + 1 < 6) {
            if(buttonArr[r+1][c].getImgInt() > ground && buttonArr[r+1][c].getImgInt() < triplecastle) {
                buildingQueue.add(buttonArr[r+1][c]);
            }
            else if(buttonArr[r+1][c].getImgInt() >= tomb && buttonArr[r+1][c].getImgInt() < bigtreasure) {
                churchQueue.add(buttonArr[r+1][c]);
            }
            else if(buttonArr[r+1][c].getImgInt() >= rock && buttonArr[r+1][c].getImgInt() < 20) {
                stoneQueue.add(buttonArr[r+1][c]);
            }
        }
        while(!buildingQueue.isEmpty()) {
            setImg(b, buildingQueue.poll().getImgInt());
            if(search(b)) {
                while(search(b));
                System.err.println("building");
                return true;
            }
        }
        while(!churchQueue.isEmpty()) {
            setImg(b, churchQueue.poll().getImgInt());
            if(search(b)) {
                while(search(b));
                System.err.println("church");
                return true;
            }
        }
        while(!stoneQueue.isEmpty()) {
            setImg(b, stoneQueue.poll().getImgInt());
            if(search(b)) {
                while(search(b));
                System.err.println("stone");
                return true;
            }
        }
        setImg(b, rock);
        return true;

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    int ground = 0, grass = 1, bush = 2, tree = 3, hut = 4, house = 5,
            mension = 6, castle = 7, floatingcastle = 8, triplecastle = 9,
            bear = 10, tomb = 11, church = 12, cathedral = 13, treasure = 14,
            bigtreasure = 15, ninja = 16, rock = 17, mountain = 18, crystal = 21, robot = 22;
    Button nextOne = new Button(this,6,6);
    Button storage = new Button(this,7,7);
    static Button[][] buttonArr = new Button[6][6];
    int bearCount = 1;
    boolean isFirstStore = true;
    //Vector<Button> bears = new Vector<Button>();
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        basicFrame = new javax.swing.JFrame();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        basicFrame.setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout basicFrameLayout = new org.jdesktop.layout.GroupLayout(basicFrame.getContentPane());
        basicFrame.getContentPane().setLayout(basicFrameLayout);
        basicFrameLayout.setHorizontalGroup(
            basicFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 533, Short.MAX_VALUE)
        );
        basicFrameLayout.setVerticalGroup(
            basicFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 474, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame basicFrame;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
