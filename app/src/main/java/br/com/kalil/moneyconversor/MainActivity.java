package br.com.kalil.moneyconversor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static URL url = null;
    static double precoDolar = 1;

    //método ViewHolder criado
    private ViewHolder mViewHolder = new ViewHolder();

    private GetDolar getDolar = new GetDolar();

    public void main(String[]args){
        try {

            url = new URL("https://dolarhoje.com/");
            precoDolar = Double.parseDouble(this.getDolar.getPage(url));

        }catch(MalformedURLException e) {
            System.out.println("ERRO DE URL!");
        }catch (Exception e) {
            System.out.println("ERRO");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instaciando a class ViewHolder
        this.mViewHolder.editValue = findViewById(R.id.editValor);
        this.mViewHolder.textDolar = findViewById(R.id.textDolar);
        this.mViewHolder.textEuro = findViewById(R.id.textEuro);
        this.mViewHolder.calculate = findViewById(R.id.button);

        /* Método para escutar os botões que podem ser chamados - OPÇÃO INTERESSANTE MAS CÓDIGO EXTENSO
        this.mViewHolder.calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
         */

        //Transforma a MainActivity em um "observador" de todos os botões - MELHOR OPÇÃO
        this.mViewHolder.calculate.setOnClickListener(this);

        //caso tivesse outro botão, por exemplo:
        //this.mViewHolder.textDolar.setOnClickListener(this);

    }

    private void clearValues(){

        this.mViewHolder.textDolar.setText("");
        this.mViewHolder.textEuro.setText("");

    }

    //função criada para executar o código caso um botão seja chamado - MELHOR OPÇÃO
    @Override
    public void onClick(View view) {
        //condição para executar o código dependendo do botão que foi clicado
        if(view.getId() == R.id.button){
            //código a ser realizado se o "button" ou "calculate" for instanciado

            //Capturar a String do EditText
            String value = mViewHolder.editValue.getText().toString();
            if("".equals(value)){
                //cria balão para comunicar com usuário alguma coisa
                Toast.makeText(this, this.getString(R.string.error_null), Toast.LENGTH_LONG).show();
            }else{
                //converter String para double
                Double real = Double.valueOf(value);

                this.mViewHolder.textDolar.setText(String.format("%.2f",real/precoDolar));
                this.mViewHolder.textEuro.setText(String.format("%.2f",real/5.26));
            }

        }
    }

    /* Função criada para o button na Activity.xml quando fosse chamada (android:onClick="teste") - CÓDIGO CURTO, MAS DE DÍFICIL IDENTIFICAÇÃO DO BOTÃO
    public void teste(View view) {

    }
     */

    //static class ViewHolder para instaciar apenas uma vez todos os elementos da interface, sem necessitar ficar repetindo o chamado
    private static class ViewHolder{

        EditText editValue;
        TextView textDolar;
        TextView textEuro;
        Button calculate;

    }

    public static class GetDolar {

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

}