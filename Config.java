import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Config {
    private BufferedImage galaxy = LoadingImg("galaxy.jpg");
    private BufferedImage meteor1 = LoadingImg("ped1.png");
    private BufferedImage meteor1_1 = LoadingImg("1.png");
    private BufferedImage meteor2 = this.LoadingImg("2.png");
    private BufferedImage meteor3 = this.LoadingImg("3.png");
    private BufferedImage meteor4 = this.LoadingImg("4.png");
    private BufferedImage meteor5 = this.LoadingImg("5.png");
    private BufferedImage meteor6 = this.LoadingImg("6.png");
    private BufferedImage meteor7 = this.LoadingImg("7.png");
    private BufferedImage meteor8 = this.LoadingImg("8.png");
    private BufferedImage meteor9 = this.LoadingImg("9.png");
    private BufferedImage meteor10 = this.LoadingImg("10.png");

    ImageIcon boomIcon = new ImageIcon(getClass().getResource("/images/bomb.gif"));


    public BufferedImage getMeteor() {
        Random ran = new Random();
        int r = ran.nextInt(11) ;
        switch (r) {
            case 0:
                return meteor1;
            case 1 : return meteor1_1;
            case 2 : return meteor2;
            case 3 : return meteor3;
            case 4 : return meteor4;
            case 5 : return meteor5;
            case 6 : return meteor6;
            case 7 : return meteor7;
            case 8 : return meteor8;
            case 9 : return meteor9;
            case 10 : return meteor10;
        }
        return meteor1;
    }
    public ImageIcon getBoom() {
        return boomIcon;
    }
    public BufferedImage getGalaxy() {
        return galaxy;
    }
    private BufferedImage LoadingImg(String pathImage){
        pathImage = "/images/" + pathImage;
        try {
            URL imgUrl = getClass().getResource(pathImage);
            if (imgUrl != null) {
                BufferedImage Image = ImageIO.read(imgUrl);
                return Image;
            } else {
                System.err.println("ไม่พบไฟล์รูปภาพใน Path: " + pathImage);
            }
        } catch (IOException e) {
            System.out.print("ไม่สามารถโหลด : " + pathImage);
        }

        return null;
    }
}
