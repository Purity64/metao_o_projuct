package items;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import utail.BackgroundPanel;

public class Box_meteorite extends Thread {
    private BackgroundPanel box;
    private BackgroundPanel maiPanel;
    private boolean running = true;
    private boolean isRun = true;
    private boolean isBoom = false;

    private String direction = "diagonal_right";


    private int mainWidth = 100;
    private int mainHeight = 100;
    private int speed = 20;
    private ImageIcon imgBoom ;




    public Box_meteorite(BackgroundPanel maiPanel ,BufferedImage img ){
       box = new BackgroundPanel(img , mainWidth , mainHeight);
       box.setOpaque(false);
       box.setSize(new Dimension(mainWidth , mainHeight));
       this.maiPanel = maiPanel;

       maiPanel.add(box);
    }

    public void updateLocation(int width, int height) {
    int x = box.getX();
    int y = box.getY();

    if (y <= 0) {
        switch (direction) {
            case "diagonal_right":
                direction = "diagonal_right_back"; 
                break;
            case "diagonal_left":
                direction = "diagonal_left_back"; 
                break;
            case "down": 
                direction = randomlocation("up", "diagonal_left", "diagonal_right");
                break;
        }
        setSpeed();
    } 
    else if (y >= height - mainHeight) {
        switch (direction) {
            case "diagonal_right_back":
                direction = "diagonal_right"; 
                break;
            case "diagonal_left_back":
                direction = "diagonal_left"; 
                break;
            case "up":
                direction = randomlocation("down", "diagonal_left_back", "diagonal_right_back");
                break;
        }
        setSpeed();
    }

    if (x <= 0) {
        switch (direction) {
            case "diagonal_left": 
                direction = "diagonal_right";  
                break;
            case "diagonal_left_back":
                direction = "diagonal_right_back"; 
                break;
            case "left":
                direction = randomlocation("right", "diagonal_right", "diagonal_left_back");
                break;
        }
        setSpeed();
    } 
    else if (x >= width - mainWidth) {
        switch (direction) {
            case "diagonal_right":
                direction = "diagonal_left"; 
                break;
            case "diagonal_right_back":
                direction = "diagonal_left_back"; 
                break;
            case "right": 
                direction = randomlocation("left", "diagonal_right_back", "diagonal_left_back");
                break;
        }
        setSpeed();
    }

    switch (direction) {
        case "diagonal_right":
            box.setLocation(x + 5, y - 5); 
            break;
        case "diagonal_right_back":
            box.setLocation(x + 5, y + 5); 
            break;
        case "diagonal_left":
            box.setLocation(x - 5, y - 5); 
            break;
        case "diagonal_left_back":
            box.setLocation(x - 5, y + 5); 
            break;
        case "up": 
            box.setLocation(x, y + 5);
            break;
        case "down":
            box.setLocation(x, y - 5);
            break;
        case "right": 
            box.setLocation(x + 5, y);
            break;
        case "left": 
            box.setLocation(x - 5, y);
            break;
    }
}

    private String randomlocation(String location1 , String location2 , String location3){
        Random ran = new Random();
        int p = ran.nextInt(3);
        if (p == 0) {
            return location1;
        }else if(p == 2){
            return location2;
        }else{
            return location3;
        }
    }

    public void setLocationStart(int start_X , int start_Y , String direction){
        box.setLocation(start_X, start_Y);
        this.direction = direction;
    }
    public void setSpeed(){
        if (speed <= 10) {
            speed = 10;
        }else{
            speed--;
        }
    }

    public Point getLocationsPoint() {
        Point original = box.getLocation();
        return new Point(original); 
    }

    public void kill(){
        this.running = false;
        this.maiPanel.remove(box);
        this.maiPanel.repaint();
        this.maiPanel.revalidate();
    }
    public void Boom(ImageIcon image){
        this.isRun = false;
        this.isBoom = true;
        this.imgBoom = image;
    }

    public boolean isruning(){
        return this.running;
    }

    public boolean isBoom(){
        return this.isBoom;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }


    @Override
    public void run() {
        while (running) {
            int width = maiPanel.getWidth();
            int height = maiPanel.getHeight();

            if (width == 0 || height == 0) {
                try {
                    Thread.sleep(50); 
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }
            if (isRun) {




                SwingUtilities.invokeLater(() -> {
                    updateLocation(width , height);
                    maiPanel.repaint();
                });

            }else{
                if (isBoom) {
                    box.setImage(imgBoom);

                    try {
                        Thread.sleep(2000);
                        kill();
                    } catch (InterruptedException e) {
                        break; 
                    }
                    
                }
            }

            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                break; 
            }
        }
    }
}
