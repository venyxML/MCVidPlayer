package io.github.venyxml.videoplayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class ImageUtil{

    public ArrayList<Color> convertToArr(File file, int nw, int nh) throws IOException {

        File trythis = new File("./plugins/depot/frame.png");
        System.out.println("DOES THE IMAGE STILL EXIST?: " + trythis.exists());

        BufferedImage i = ImageIO.read(file);

        assert i != null : "BufferedImage i is NULL";

        System.out.println("NW: " + nw + ", NH: " + nh);

        if(trythis.exists()){
            //FileInputStream fis = new FileInputStream(trythis);
            BufferedImage bi = resize(i,nw,nh);
            //fis.close();

            int w = bi.getWidth();
            int h = bi.getHeight();

            ArrayList<Color> pixelArray = new ArrayList<>();

            for(int x = 0; x < w; x++) { //width
                for (int y = 0; y < h; y++) { //height

                    int pixel = bi.getRGB(x, y);
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;

                    //Color pixelRGB = new Color(red,green,blue);
                    pixelArray.add(new Color(red,green,blue));

                } //inner for

            } //outer for

            return pixelArray;
        }

        return null;
    } //end convertToArr

    public ArrayList<Color> convertToArr(BufferedImage i, int nw, int nh) throws IOException {

        BufferedImage bi = resize(i,nw,nh);

        //File outputfile = new File("image-resized.jpg");
        //ImageIO.write(bi, "jpg", outputfile);

        int w = bi.getWidth();
        int h = bi.getHeight();

        ArrayList<Color> pixelArray = new ArrayList<>();

        for(int x = 0; x < w; x++) { //width
            for (int y = 0; y < h; y++) { //height

                int pixel = bi.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                //Color pixelRGB = new Color(red,green,blue);
                pixelArray.add(new Color(red,green,blue));

            } //inner for

        } //outer for

        return pixelArray;

    } //end convertToArr

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW+1, newH+1, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW+1, newH+1, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public File saveToImage(String imageUrl, String dest) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(dest);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();

        return new File(dest);
    } //end saveToImage
}
