import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

public class Config {
    private BufferedImage galaxy = LoadingImg("galaxy.jpg");
    private BufferedImage meteor1 = LoadingImg("ped1.png");
    private BufferedImage meteor2 = this.LoadingImg("2.png");
    private BufferedImage meteor3 = this.LoadingImg("3.png");
    private BufferedImage meteor4 = this.LoadingImg("4.png");
    private BufferedImage meteor5 = this.LoadingImg("5.png");


    public BufferedImage getMeteor() {
        Random ran = new Random();
        int r = ran.nextInt(6) ;
        switch (r) {
            case 0:
                return meteor1;
            case 1 : return meteor2;
            case 2 : return meteor3;
            case 3 : return meteor4;
            case 4 : return meteor5;
        }
        return meteor1;
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
