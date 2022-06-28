package br.com.kalil.moneyconversor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class GetDolar {

    public static String getPage (URL url) throws IOException {

        String content;

        String value = "";

        BufferedReader page = new BufferedReader(new InputStreamReader(url.openStream()));

        while((content = page.readLine()) != null){

            System.out.println(content);
            if(content.contains("R$")) {

                char valueParts[] = content.toCharArray();
                char igual = 61;
                byte cont=0;
                for(int i=0;i<valueParts.length;i++) {
                    if(igual == valueParts[i]) {
                        cont++;
                        if(cont == 12) {
                            value += valueParts[i+2];
                            value += valueParts[i+3];
                            value += valueParts[i+4];
                            value += valueParts[i+5];
                            return value;
                        }

                    }

                }

            }

        }

        page.close();

        return "5";

    }

}
