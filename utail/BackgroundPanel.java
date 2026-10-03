package utail;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
    private BufferedImage img;
    private ImageIcon img_boom;
    private int w ;
    private int h;

    public BackgroundPanel(BufferedImage img ) {
        this.img = img;
    }

    public BackgroundPanel(BufferedImage img , int w , int h ) {
        this(img);
        this.w = w;
        this.h = h;
    }

    public void setImage(ImageIcon img){
        this.img_boom = img;
        repaint();
        revalidate();
    }

    @Override 
    protected void paintComponent(Graphics g ) {
        super.paintComponent(g);
       if (img_boom != null) {
            img_boom.paintIcon(this, g, 0, 0);
        } else if (img != null) {
            
            g.drawImage(img, 0, 0, (w > 0) ? w : getWidth(), (h > 0) ? h : getHeight(), this);
        }
    }
}