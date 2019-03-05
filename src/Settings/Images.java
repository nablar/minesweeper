package Settings;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Images {
    private static HashMap<String,Image> imgs = new HashMap<>();

    public static Image getImage(String path){
        Image img = imgs.get(path);
        if(img==null){
            img = loadImage(path);
            imgs.put(path,img);
        }
        return img;
    }

    private static Image loadImage(String path){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
        }
        return img;
    }
}
