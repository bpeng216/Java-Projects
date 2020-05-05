import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.awt.event.KeyEvent;

public class guesser {
    public static ArrayList<String> words = new ArrayList<>();
    public static HashMap<String, Integer> keys = new HashMap<String, Integer>();
    public static void main (String[] args) {
        keys.put("a", KeyEvent.VK_A);keys.put("b", KeyEvent.VK_B);keys.put("c", KeyEvent.VK_C);keys.put("d", KeyEvent.VK_D);keys.put("e", KeyEvent.VK_E);keys.put("f", KeyEvent.VK_F);keys.put("g", KeyEvent.VK_G);keys.put("h", KeyEvent.VK_H);keys.put("i", KeyEvent.VK_I);keys.put("j", KeyEvent.VK_J);keys.put("k", KeyEvent.VK_K);keys.put("l", KeyEvent.VK_L);keys.put("m", KeyEvent.VK_M);keys.put("n", KeyEvent.VK_N);keys.put("o", KeyEvent.VK_O);keys.put("p", KeyEvent.VK_P);keys.put("q", KeyEvent.VK_Q);keys.put("r", KeyEvent.VK_R);keys.put("s", KeyEvent.VK_S);keys.put("t", KeyEvent.VK_T);keys.put("u", KeyEvent.VK_U);keys.put("v", KeyEvent.VK_V);keys.put("w", KeyEvent.VK_W);keys.put("x", KeyEvent.VK_X);keys.put("y", KeyEvent.VK_Y);keys.put("z", KeyEvent.VK_Z);keys.put(" ", KeyEvent.VK_SPACE);keys.put(".", KeyEvent.VK_PERIOD);keys.put(",", KeyEvent.VK_COMMA);keys.put("'", KeyEvent.VK_QUOTE);
        try {
            Scanner s = new Scanner(new File("words.txt"));
            while (s.hasNextLine()) {
                String word = s.nextLine();
                if (word.matches(".*[',.].*")) {
                    words.add(word.replaceAll("[',.]", "").toLowerCase());
                    words.add(word.replaceAll("[',.]", " ").toLowerCase());
                }
                words.add(word.toLowerCase());
            }
            System.out.println(words);
        } catch (FileNotFoundException f) {
            System.out.print(f);
        }
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        String blanks = null;
        try {
            blanks = (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> res = new ArrayList<>();
        for (String i : words) {
            if (i.length() == blanks.length() && i.indexOf(" ") == blanks.indexOf(" ")) {
                res.add(i);
            }
        }
        Collections.shuffle(res);
        type(res);
    }

    public static void type(ArrayList<String> words) {
        Robot r;
        try {
            r = new Robot();
            int count = 1;
            for (String word:words) {
                for (int i = 0; i<word.length(); i++) {
                    r.keyPress(keys.get(word.substring(i, i+1)));
                    r.keyRelease(keys.get(word.substring(i, i+1)));
                }
                r.keyPress(KeyEvent.VK_ENTER);
                if (count == 4) {
                    Thread.sleep(2005);
                    count = 1;
                } else {
                    Thread.sleep(500);
                    count++;
                }
            }
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
