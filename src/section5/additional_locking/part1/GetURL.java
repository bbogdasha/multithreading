package section5.additional_locking.part1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GetURL {
    public static String getUrlContent(String urlAddress) {

        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String str;

            while ((str = br.readLine()) != null) {
                content.append(str).append("\n");
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return content.toString();
    }
}
