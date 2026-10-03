import javax.swing.*;

import utail.BackgroundPanel;
import items.*;
import java.awt.*;
import java.awt.event.ComponentAdapter; 
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Random;

public class Mainframe extends JFrame {
    Config cf = new Config();
    BackgroundPanel mainPanel = new BackgroundPanel(cf.getGalaxy());
    ArrayList <Box_meteorite> array_Box_meteorite = new ArrayList<>();
    Thread t = null;
    Mainframe() {
        setTitle("purity_dust");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        setMinimumSize(new Dimension(1000, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH); 

        setLayout(new BorderLayout());

        
        mainPanel.setLayout(null);
        JPanel menuPanel = new JPanel();
       

        mainPanel.setBackground(Color.BLACK);
        menuPanel.setBackground(Color.WHITE);
        JTextField input_num = new JTextField(20);
        JButton btn_add = new JButton("setting");
        input_num.setHorizontalAlignment(JTextField.CENTER); 

        
        

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int menuWidth = (int) (getContentPane().getWidth() * 0.25);
                menuPanel.setPreferredSize(new Dimension( menuWidth, 0));

                input_num.setPreferredSize(new Dimension(menuPanel.getWidth() - 50 , 50));
                btn_add.setPreferredSize(new Dimension(menuPanel.getWidth() - 50 , 50));

                mainPanel.revalidate();
                
            }
        });

        input_num.setPreferredSize(new Dimension(200, 50));
        menuPanel.setPreferredSize(new Dimension(250, 0));


        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        btn_add.setPreferredSize(new Dimension(200 , 50));
        
        btn_add.addActionListener(e -> {
            try {
                String num = input_num.getText();
                int count = Integer.parseInt(num);
                if (count > 0) {
                    CreateBox(count);
                }
            } catch (NumberFormatException ex) {
                System.out.print("not int ");
            }

        });

        menuPanel.add(input_num);
        menuPanel.add(btn_add);

        add(mainPanel, BorderLayout.CENTER);
        add(menuPanel, BorderLayout.EAST);
    }

    public void CreateBox(int count) {
        if (t != null && t.isAlive()) {
            t.interrupt(); 
        }

        for (Box_meteorite box : array_Box_meteorite) {
            box.kill(); 
        }
        mainPanel.removeAll();
        if (array_Box_meteorite.size() > 0) array_Box_meteorite.clear();
        for (int i = 0; i < count; i++) {
            Box_meteorite box1 = new Box_meteorite(mainPanel, cf.getMeteor());
            Random rand = new Random();
            int start_x = rand.nextInt(mainPanel.getWidth() - 100);
            int start_y = rand.nextInt(mainPanel.getHeight() - 100);
            int direction_int = rand.nextInt(9);
            String direction = "diagonal_left_back";

            switch (direction_int) {
                case 0: direction = "diagonal_left_back"; break;
                case 1: direction = "diagonal_left"; break;
                case 3: direction = "diagonal_right"; break;
                case 4: direction = "diagonal_right_back"; break;
                case 5: direction = "up"; break;
                case 6: direction = "down"; break;
                case 7: direction = "right"; break;
                case 8: direction = "left"; break;
            }
            box1.setLocationStart(start_x, start_y , direction);
            array_Box_meteorite.add(box1);
            box1.setSpeed(rand.nextInt(20) + 10 );
            box1.start();
        }

        mainPanel.revalidate();
        mainPanel.repaint();

        t = new Thread() {         
            public void run() {             
                while (!Thread.currentThread().isInterrupted()) {
                    Check_hit();
                    try {
                        Thread.sleep(10); 
                    } catch (InterruptedException ex) {
                        break; 
                    }
                }
            }
        };
        t.start();
    }




    public void Check_hit(){

        for (int i = 0; i < array_Box_meteorite.size(); i++) {
            Box_meteorite box1 = array_Box_meteorite.get(i);
            if (!box1.isruning()) {
                continue;
            }
            Point box1_xy = box1.getLocationsPoint();
            int box1_start_x = box1_xy.x + 50;
            int box1_start_y = box1_xy.y + 50;

            int box1_end_x = box1_xy.x  + 100;            
            int box1_end_y = box1_xy.y + 100;

            for (int j = 0; j < array_Box_meteorite.size(); j++) {

                Box_meteorite box2 = array_Box_meteorite.get(j);
                if (i == j || box1.isBoom() || box2.isBoom()) {
                    continue;
                }
                Point box2_xy = box2.getLocationsPoint();
                int box2_start_x = box2_xy.x + 25;
                int box2_end_x = box2_xy.x  + 100;
                int box2_start_y = box2_xy.y + 25;
                int box2_end_y = box2_xy.y + 100;

                
                if (box1_start_x < box2_end_x && box1_end_x > box2_start_x  && box1_start_y < box2_end_y && box1_end_y > box2_start_y) {
                    box2.Boom(cf.getBoom());
                }
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Mainframe mf = new Mainframe();
            mf.setVisible(true);
        });
    }
}