import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.awt.Image;

public class Pixel extends JFrame {
    static boolean black = true;
    static BufferedImage img;
    //private int randomNum = 40;
    static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    static String path;
    static JFileChooser fc = new JFileChooser();
    static int blackAndWhite, acidify, pixelated, red, green, blue, ascii = 0;
    static boolean line, spookify, greyed = false;

    public static void main(String[] args) {
        //Canvas c = new Pixel();
        //c.setSize(img.getWidth(), img.getHeight());
        Pixel pix = new Pixel();
    }

    public Pixel () {

        JButton openMenu = new JButton("Open settings");

        JButton bw = new JButton("Black and white = OFF");
        bw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                blackAndWhite += 15;
                if (blackAndWhite >= 255) blackAndWhite = 0;
                if (blackAndWhite > 0) bw.setText("Black and white = " + blackAndWhite);
                else bw.setText("Black and white = OFF");
            }
        });

        JButton ac = new JButton("I have no idea = OFF");
        ac.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                acidify += 20;
                if (acidify > 255) acidify = 0;
                if (acidify > 0) ac.setText("I have no idea = " + acidify);
                else ac.setText("I have no idea = OFF");
            }
        });

        JButton pi = new JButton("Pixelate = OFF");
        pi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                pixelated += 2;
                if (pixelated > 30) pixelated = 0;
                if (pixelated > 0)pi.setText("Pixelate = " + pixelated);
                else pi.setText("Pixelate = OFF");
            }
        });

        JButton gr = new JButton("Greyscale = OFF");
        gr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (greyed) greyed = false;
                else greyed = true;
                if (greyed) gr.setText("Greyscale = ON");
                else gr.setText("Greyscale = OFF");
            }
        });

        JButton cR = new JButton("Colorify red = OFF");
        cR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                red += 15;
                if (red > 150) red = -150;
                if (red != 0) cR.setText("Colorify red = " + red);
                else cR.setText("Colorify red = OFF");
            }
        });
        JButton cG = new JButton("Colorify green = OFF");
        cG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                green += 15;
                if (green > 150) green = -150;
                if (green != 0) cG.setText("Colorify green = " + green);
                else cG.setText("Colorify green = OFF");
            }
        });
        JButton cB = new JButton("Colorify blue = OFF");
        cB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                blue += 15;
                if (blue > 150) blue = -150;
                if (blue != 0) cB.setText("Colorify blue = " + blue);
                else cB.setText("Colorify blue = OFF");
            }
        });

        JButton sp = new JButton("Sp00kify = OFF");
        sp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (spookify) spookify = false;
                else spookify = true;
                if (spookify) sp.setText("Sp00kify = ON");
                else sp.setText("Sp00kify = OFF");
            }
        });

        JButton li = new JButton("Line art = OFF");
        li.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (line) line = false;
                else line = true;
                if (line) li.setText("Line art = ON");
                else li.setText("Line art = OFF");
            }
        });

        JButton as = new JButton("Ascii art = OFF");
        as.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                ascii += 3;
                if (ascii > 30) ascii = 0;
                if (ascii > 0) as.setText("Ascii art = " + ascii);
                else as.setText("Ascii art = OFF");
            }
        });

        JButton draw = new JButton("Draw");
        JButton save = new JButton("Save image");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                loadImage();
                if (ascii > 0) {
                    img = drawASCII(img, ascii);
                }
                if (line) img = toLineArt(img);
                if (blackAndWhite > 0) {
                    img = toBlackAndWhite(img, blackAndWhite);
                }
                if (pixelated > 0) {
                    img = createPixelated(img, pixelated);
                }
                if (acidify > 0) {
                    img = acid(img, acidify);
                }
                if (spookify) {
                    img = toSpooky(img);
                }
                if (greyed) {
                    img = toGreyscale(img);
                }
                img = colorify(img, red, green, blue);
                int aoifn = 1;
                if (greyed) aoifn++;
                if (spookify) aoifn++;
                saveImage(img, ((ascii+ blackAndWhite+ pixelated+ acidify+ green+ blue+ red) * aoifn) + ".png");
            }
        });

        JButton newimg = new JButton("Choose new image");

        JButton[] menu = {bw, ac, pi, gr, cR, cG, cB, sp, li, as, draw, save, newimg};

        draw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openMenu.setVisible(true);
                for (int i = 0; i < menu.length; i++) menu[i].setVisible(false);
                repaint();
            }
        });
        newimg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = fc.showOpenDialog(newimg);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    path = file.getPath();
                } else {
                    System.out.println("pick a file, nitwit");
                }
                loadImage();
                setSize(img.getWidth(), img.getHeight());
                for (int i = 0; i < menu.length; i++) menu[i].setVisible(false);
                openMenu.setVisible(true);
                repaint();
            }
        });
        openMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                openMenu.setVisible(false);
                for (int i = 0; i < menu.length; i++) menu[i].setVisible(true);
            }
        });


        int ret = fc.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            path = file.getPath();
        } else {
            System.out.println("pick a file, nitwit");
        }

        loadImage();

        //adding buttons
        setSize(img.getWidth(), img.getHeight());
        for (int i = 0; i < menu.length; i++) {
            menu[i].setBounds(0, i * 30, 200, 30);
            menu[i].setBorderPainted(false);
            menu[i].setVisible(false);
            add(menu[i]);
        }
        openMenu.setBounds(0,0,80, 30);
        openMenu.setBorderPainted(false);
        openMenu.setVisible(true);
        add(openMenu);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(null);
        setVisible(true);


    }

    public void paint (Graphics g) {
        //super.paint(g);
        loadImage();
        if (ascii > 0) {
            img = drawASCII(img, ascii);
        }
        if (line) img = toLineArt(img);
        if (blackAndWhite > 0) {
            img = toBlackAndWhite(img, blackAndWhite);
        }
        if (pixelated > 0) {
            img = createPixelated(img, pixelated);
        }
        if (acidify > 0) {
            img = acid(img, acidify);
        }
        if (spookify) {
            img = toSpooky(img);
        }
        if (greyed) {
            img = toGreyscale(img);
        }
        img = colorify(img, red, green, blue);

        g.drawImage(img, 0, 0, null);

        //saveImage( i,  fileName.substring(0, fileName.indexOf(".")) + ".png");
        g.dispose();
    }

    public void loadImage() {
        try {
            img = ImageIO.read(new File(path));
            double scale = screen.getHeight() / img.getHeight() * 2;
            Image temp = img.getScaledInstance((int) (img.getWidth()*scale/2), (int) (img.getHeight()*scale/2), Image.SCALE_FAST);
            BufferedImage buffered = new BufferedImage((int) (img.getWidth()*scale/2), (int) (img.getHeight()*scale/2), BufferedImage.TYPE_INT_ARGB);
            buffered.getGraphics().drawImage(temp, 0, 0 , null);
            img = buffered;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveImage(BufferedImage img, String file) {
        try {
            File output = new File(file);
            ImageIO.write(img, "png", output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage toLineArt(BufferedImage img) {
        BufferedImage newImgH = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        BufferedImage newImgV = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D gH = newImgH.createGraphics();
        Graphics2D gV = newImgV.createGraphics();
        gH.setColor(Color.WHITE);
        gH.fillRect(0, 0, img.getWidth(), img.getHeight());
        gV.setColor(Color.WHITE);
        gV.fillRect(0, 0, img.getWidth(), img.getHeight());
        gH.dispose();
        gV.dispose();

        for (int i = img.getWidth() - 1; i > 2; i--) {
            int RGB;
            for (int v = 0; v <img.getHeight() - 1; v++) {
                RGB = img.getRGB(i, v);
                int R1 = (RGB >> 16) & 0xff;
                int G1 = (RGB >> 8) & 0xff;
                int B1 = RGB & 0xff;
                int grey = (R1 + G1 + B1) / 3;
                int RGB2 = img.getRGB(i, v + 1);
                int R2 = (RGB2 >> 16) & 0xff;
                int G2 = (RGB2 >> 8) & 0xff;
                int B2 = RGB2 & 0xff;
                int change = (int) (( (10.0) /255) * grey) + 15;

                if ((Math.abs(R1 - R2) > change || Math.abs(G1 - G2) > change || Math.abs(B1 - B2) > change)) newImgV.setRGB(i, v, -16777216);
            }
        }
        for (int i = img.getHeight() - 1; i >= 0; i--) {
            int RGB;
            for (int v = 0; v <img.getWidth() - 1; v++) {
                RGB = img.getRGB(v, i);
                int R1 = (RGB >> 16) & 0xff;
                int G1 = (RGB >> 8) & 0xff;
                int B1 = RGB & 0xff;
                int grey = (R1 + G1 + B1) / 3;
                int RGB2 = img.getRGB(v + 1, i);
                int R2 = (RGB2 >> 16) & 0xff;
                int G2 = (RGB2 >> 8) & 0xff;
                int B2 = RGB2 & 0xff;
                int change = (int) (( (10.0) /255) * grey) + 15;

                if ((Math.abs(R1 - R2) > change || Math.abs(G1 - G2) > change || Math.abs(B1 - B2) > change)) newImgH.setRGB(v, i, -16777216);
            }
        }
        for (int i = 0; i < img.getWidth(); i++) {
            for (int v = 0; v < img.getHeight(); v++) {
                if (newImgH.getRGB(i, v) == -16777216) newImgV.setRGB(i, v, -16777216);
            }
        }
        return newImgV;
    }

    public BufferedImage drawASCII(BufferedImage img, int letterSize) {
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = newImage.createGraphics();

        g.setFont(new Font("Dialog", Font.BOLD, letterSize));

        if (black) g.setColor(Color.BLACK);
        else g.setColor(Color.WHITE);
        g.fillRect(0,0,this.getWidth(),this.getHeight());

        if (black) g.setColor(Color.WHITE);
        else g.setColor(Color.BLACK);

        String[] ascii = createASCII(img, letterSize);
        for (int i = 0; i < ascii.length; i++) {
            for (int o = 0; o < ascii[i].length(); o++) {
                g.drawString(ascii[i].substring(o, o + 1), o * letterSize + 5, i * letterSize);
            }
        }
        return newImage;
    }

    public BufferedImage toBlackAndWhite(BufferedImage img, int threshold) {
        BufferedImage newImg = img;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int v = 0; v < img.getHeight(); v++) {
                int RGB = img.getRGB(i, v);
                int grey = (   ((RGB>>16) & 0xff) + ((RGB>>8) & 0xff) + (RGB & 0xff)   ) / 3;
                if (grey <= threshold) grey = 0;
                else grey = 255;
                Color c = new Color(grey,grey,grey);
                newImg.setRGB(i, v, c.getRGB());
            }
        }
        return newImg;
    }

    public BufferedImage acid(BufferedImage img, int magnitude) {
        BufferedImage newImg = img;
        int R, G, B;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int v = 0; v < img.getHeight(); v++) {
                int RGB = img.getRGB(i, v);
                R = ((RGB>>16) & 0xff);
                G = ((RGB>>8) & 0xff);
                B = (RGB & 0xff);
                if (R > G && R > B) R += magnitude;
                else if (G > R && G > B) G += magnitude;
                else if (B > R && B > G) B += magnitude;

                if (R > 255) R = 255;
                if (G > 255) G = 255;
                if (B > 255) B = 255;
                if (R < 0) R = 0;
                if (G < 0) G = 0;
                if (B < 0) B = 0;
                Color c = new Color(R,G,B);
                newImg.setRGB(i,v,c.getRGB());
            }
        }
        return newImg;
    }

    public BufferedImage colorify(BufferedImage img, int r, int g, int b) {
        BufferedImage newImg = img;
        int R, G, B;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int v = 0; v < img.getHeight(); v++) {
                int RGB = img.getRGB(i, v);
                R = ((RGB>>16) & 0xff) + r;
                G = ((RGB>>8) & 0xff) + g;
                B = (RGB & 0xff) + b;
                if (R > 255) R = 255;
                if (G > 255) G = 255;
                if (B > 255) B = 255;
                if (R < 0) R = 0;
                if (G < 0) G = 0;
                if (B < 0) B = 0;

                Color c = new Color(R,G,B);
                newImg.setRGB(i,v,c.getRGB());
            }
        }
        return newImg;
    }
    public BufferedImage toSpooky (BufferedImage img) {
        BufferedImage newImage = img;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int v = 0; v < img.getHeight(); v++) {
                int RGB = img.getRGB(i, v);
                int grey = ( (RGB>>16) & 0xff + (RGB>>8) & 0xff + RGB & 0xff );
                Color c = new Color(grey,grey,grey);
                newImage.setRGB(i, v, c.getRGB());
            }
        }
        return newImage;
    }

    public BufferedImage toGreyscale (BufferedImage img) {
        BufferedImage newImage = img;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int v = 0; v < img.getHeight(); v++) {
                int RGB = img.getRGB(i, v);
                double Y = (((RGB>>16) & 0xff) * .2126 + ((RGB>>8) & 0xff) * .7152 + (RGB & 0xff) * .0722);
                int grey = (int) (Y);
                Color c = new Color(grey,grey,grey);
                newImage.setRGB(i, v, c.getRGB());
            }
        }
        return newImage;
    }

    public String[] createASCII (BufferedImage imag, int letterSize) {
        String ret[] = new String[imag.getHeight() / letterSize];
        Arrays.fill(ret, "");
        int div = letterSize * letterSize;

        int i = 0;
        int width = imag.getWidth();
        int height = imag.getHeight();
        int RGB, R = 0, G = 0, B = 0, fina;
        for (int h = 0; h < height - letterSize; h += letterSize) {
            for (int w = 0; w < width - letterSize; w += letterSize) {
                //goes through each section
                for (int y = h; y < h + letterSize; y++) {
                    for (int x = w; x < w + letterSize; x++) {
                        RGB = imag.getRGB(x, y);
                        R += (RGB>>16) & 0xff;
                        G += (RGB>>8) & 0xff;
                        B += RGB & 0xff;
                    }
                }
                fina = (R/div + G/div + B/div)/3; //add 255 - if it is a white screen
                if (!black) fina = 255 - fina;
                ret[i] += chooseLetter(fina);

                R = 0; G = 0; B = 0;
            }
            i++;
        }
        return ret;
    }

    public BufferedImage createPixelated (BufferedImage imag, int pixelSize) {
        if (pixelSize <= 1) return imag;

        BufferedImage fin = img;
        int div = pixelSize * pixelSize;
        int width = imag.getWidth();
        int height = imag.getHeight();
        int RGB, R,G,B;
        for (int h = 0; h < height - pixelSize; h += pixelSize) {
            for (int w = 0; w < width - pixelSize; w += pixelSize) {
                //goes through each section
                R = 0; G = 0; B = 0;
                for (int y = h; y < h + pixelSize; y++) {
                    for (int x = w; x < w + pixelSize; x++) {
                        RGB = imag.getRGB(x, y);
                        R += (RGB>>16) & 0xff;
                        G += (RGB>>8) & 0xff;
                        B += RGB & 0xff;

                    }

                }
                Color c = new Color(R/div,G/div,B/div);
                for (int y = h; y <= h + pixelSize; y++) {
                    for (int x = w; x <= w + pixelSize; x++) {
                        fin.setRGB(x, y, c.getRGB());
                    }
                }


            }
        }
        return fin;
    }

    public String chooseLetter (int fin) {
        if (fin < 17) {
            return " ";
        } else if (fin < 34) {
            return ".";
        } else if (fin < 51) {
            return ",";
        } else if (fin < 68) {
            return ":";
        } else if (fin < 85) {
            return ";";
        } else if (fin < 102) {
            return "!";
        } else if (fin < 119) {
            return "i";
        } else if (fin < 136) {
            return "L";
        } else if (fin < 153) {
            return "I";
        } else if (fin < 170) {
            return "Y";
        } else if (fin < 187) {
            return "V";
        } else if (fin < 204) {
            return "X";
        } else if (fin < 221) {
            return "K";
        } else if (fin < 248) {
            return "N";
        } else if (fin >= 248) {
            return "M";
        }
        return " what ";
    }
}
