package edu.icesi.implementacion;

import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.*;

import javax.imageio.ImageIO;
import org.osoa.sca.annotations.*;

import edu.icesi.interfaces.IRotacion;

/**
 * ClientRotacion
 */
@Service(Runnable.class)
public class ClientRotacion implements Runnable {

    private IRotacion rotacion;
    @Property(name = "hostR")
    private String hostR;
    @Property(name = "portR")
    private int port;
    @Property(name = "pathI")
    private String pathImage;
    @Property(name = "grados")
    private double grados;


    @Override
    public void run() {
        try {
            rotacion = (IRotacion) Naming.lookup(hostR);
            processImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processImage() throws Exception {
        File src=new File(pathImage);
        BufferedImage image=ImageIO.read(src);
        int w=image.getWidth();
        int h=image.getHeight();
        double[][] ima=new double[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                ima[i][j]=image.getRGB(i, j);
            }
        }
        double[][] res=rotacion.rotar(ima, grados);
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                image.setRGB(i, j, (int)res[i][j]);
            }
        }
        ImageIO.write(image, "jpg", new File(src.getParent()+"/"+src.getName()+grados+".jpg"));
    }

    
}