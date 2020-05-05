import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static ArrayList<String> links = new ArrayList<String>();
    public static ArrayList<String> names = new ArrayList<>();
    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(new File("base.txt"));
            int num = 1;
            while (s.hasNextLine()) {
                String trial = s.nextLine();
                if (trial.indexOf("http://") != -1) links.add(trial.substring(trial.indexOf("http://")));
                if (trial.indexOf(num + "") == 0) {
                    if (trial.indexOf("http://") != -1) {
                        names.add(trial.substring((num + "").length(), trial.indexOf("http://")).trim());
                        if (trial.substring((num + "").length(), trial.indexOf("http://")).trim().equals(" ") || trial.substring((num + "").length(), trial.indexOf("http://")).trim().equals("")) {
                            System.out.println(num);
                        }
                    }
                    else {
                        if (trial.substring((num + "").length()).equals(" ") || trial.substring((num + "").length()).trim().equals("")) {
                            System.out.println(num);
                        }
                        names.add(trial.substring((num + "").length()));
                    }
                    num++;
                }
            }
            for (int i = 0; i < names.size(); i++) {
                if (names.get(i).contains(",")) {
                    names.set(i, names.get(i).substring(0, names.get(i).indexOf(",") + 1));
                }
            }
            //Collections.sort(names);
            //System.out.println(names);
            //System.out.println(names.size());
            //System.out.println(links);
            //System.out.println(links.size());
            /*for (int i = 0; i < names.size(); i++) {
                File f = new File("Downloads\\" + names.get(i).trim() + ".pdf");
                if(f.exists()){
                    //System.out.println(names.get(i).trim() + " success");
                }
                else{
                    System.out.println(names.get(i).trim() + " fail");
                }
            }
*/
            //downloading

            for (int i = 350; i < links.size(); i++) {
                URL url = new URL(links.get(i).replaceAll("http", "https"));
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setInstanceFollowRedirects(false);
                c.connect();
                int responseCode = c.getResponseCode();
                //System.out.println( responseCode );
                String location = c.getHeaderField( "Location" );
                links.set(i, location );
            }
            for (int i = 350; i < links.size(); i++) {
                URL url = new URL(links.get(i));
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setInstanceFollowRedirects(false);
                c.connect();
                int responseCode = c.getResponseCode();
                String location = c.getHeaderField( "Location" );


                String finalLink = "https://link.springer.com/content/pdf/" + location.substring(location.indexOf("/book/") + 6);
                //System.out.println(finalLink );
                //c = (HttpURLConnection) new URL(finalLink).openConnection();
                BufferedInputStream in = new BufferedInputStream(new URL(finalLink).openStream());
                System.out.println("Downloading... ("+(i+1) + "/408) " + names.get(i).trim() + ".pdf");
                FileOutputStream fileOutputStream = new FileOutputStream("Downloads\\" + names.get(i).trim() + ".pdf");
                    byte dataBuffer[] = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            }

        } catch (FileNotFoundException /*| MalformedURLException*/ e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
