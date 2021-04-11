package com.gsmmoz.maguandza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gsmmoz.maguandza.database.DadosOpenHelper;
import com.gsmmoz.maguandza.dominio.repositorio.DataActualRepositorio;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

public class HomeActivity extends AppCompatActivity  {

    CalendarView calendarView;
    EditText campoImei;
    String validacao, deviceID;
    //String ficheiro = "SystemTime.txt";  // Inicializacao do Ficheiros de texto
    TextView textView;
    Dialog myDialog;
    Button calcular;
    int arrayImei[], dia_Actual, mes_Actual, ano_Actual;
    TextView viewCode;

    private SQLiteDatabase conexao;
    private DadosOpenHelper dadosOpenHelper;
    private DataActualRepositorio dataActualRepositorio;

    private LinearLayout layoutContentMain2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        myDialog = new Dialog(this);
        campoImei = findViewById(R.id.campoImei);
        campoImei = (EditText) findViewById(R.id.campoImei);

        viewCode = findViewById(R.id.viewCode);
        textView = (TextView) findViewById(R.id.textView);
        calcular = findViewById(R.id.calcular);
        validacao = "8677900";
        deviceID = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
        arrayImei= new int[15];

        layoutContentMain2 = (LinearLayout) findViewById(R.id.layoutContentMain2);


        campoImei.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = campoImei.getText().toString();
                int symbols = text.length();
                textView.setText(symbols + "/15");
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    //____________________________________________ DATABASE _______________________________________________________________

   /* private void criarConexao(){

        try {
            dadosOpenHelper = new DadosOpenHelper(HomeActivity.this);

            conexao = dadosOpenHelper.getWritableDatabase();

            Snackbar.make(layoutContentMain2, R.string.message_conexao_criada_sucesso, Snackbar.LENGTH_LONG)
                    .setAction("OK", null).show();
        } catch (SQLException e) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.erro);
            dlg.setMessage(e.getMessage());
            dlg.setNeutralButton("OK", null );
            dlg.show();
        }


    }*/

    //_____________________________________________ EXECUCAO DO CALCULO TAPE 3 ____________________________________________
    public void calcular(View view){
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obterDataActual();
               // criarConexao();
                DataLocal dataLocal;
                try {
                    dataLocal = new DataLocal(-1,convertData(obterDataActual()));
                    //Toast.makeText(HomeActivity.this, dataLocal.toString(), Toast.LENGTH_SHORT).show();

                }catch (Exception ex){
                    //Toast.makeText(HomeActivity.this, "ERROR CREATING", Toast.LENGTH_SHORT).show();
                    dataLocal = new DataLocal(-1, 0 );
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(HomeActivity.this);
                boolean success = dataBaseHelper.addOne(dataLocal);
                //Toast.makeText(HomeActivity.this, "Success", Toast.LENGTH_SHORT).show();

                //gravarFicheiro(obterDataActual(), ficheiro);    // Chama o metodo gravarFicheiro e preenche com parametros
                //int a = convertData(ficheiro);
                int a = convertData(obterDataActual());
                validacao(a);                                    // obterDataActual que busca a data do cell e o ficheiro onde sera armazenado a dataActual
                }
            });
    }
    /*public int horas(){
        int b = convertData(ficheiro);
        return b;
    }*/
    //_____________________________________________ Algoritmo de Calculo ____________________________________________

    public void decrypt(String imei) {
        String code = "";
        for (int i = 0; i < 7; i++) {
            code += (arrayImei[i * 2] + arrayImei[i * 2 + 1]) % 10;
            if (i == 6) {
                code += imei.charAt(0);
                viewCode.setText(code);
            }
        }
    }
     //_____________________________________________Metodo de validacao de dados  ____________________________________________
     public void validacao(int mes) {
         DataBaseHelper dataBaseHelper = new DataBaseHelper(HomeActivity.this);

         int l = mes;
         String imei = campoImei.getText().toString();
         List<DataLocal> everyone = dataBaseHelper.getEveryone();
         // DataLocal[] verificacao = new DataLocal[mesUsados.size()];
         // verificacao = mesUsados.toArray(verificacao);
         int s[] = new int[everyone.size()];
         for (int a = 0; a < everyone.size(); a++) {

             s[a] = everyone.get(a).getMes();
         }

         //int ver[ ] = new int[verificacao.length];

         for (int posicao = 0; posicao < everyone.size(); posicao++) {
             if (!(deviceID.equals("b6bc1d1593fd22a0"))) {
                 campoImei.setError("Device nao Configurado"  + " "+ deviceID);
              } else
                  if (s[posicao] != (4)) {
                      campoImei.setText("APP EXPIROU");
             } else if (imei.length() < 15) {
                 campoImei.setError("Imei Inválido" );
             } else {
                 for (int i = 0; i < imei.length(); i++) {
                     arrayImei[i] = Character.digit(imei.charAt(i), 10);
                     decrypt(imei);
                 }
             }
         }
     }
     public String obterDataActual() {

             String dataF;

             Date currentTime = Calendar.getInstance().getTime();
             dataF = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(currentTime);

             return dataF;
         }

    //_____________________________________________  Leitura do Ficheiro txt com DATA ACTUALIZADA __________________________________________
    public int convertData(String data){
        String horaFicha = "";                                                      /////  hORA LIDA NO FICHEIRO 2, fichHORA
        //horaFicha = lerFich(data);                                              // T[3]

        horaFicha = obterDataActual();
        String n = horaFicha.replace("/", "");
        String m = n.concat("");

        int l[] = new int[m.length()];
        for (int x = 0; x < m.length(); x++) {
            l[x] = Character.digit(m.charAt(x), 10);
        }
        return l[3];
    }

    /*public String lerFich(String fich){

        FileInputStream fileInputStream = null;
       // String dataActualizada = "";
        String b = "";

        try {
            fileInputStream = openFileInput(fich);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();

            while ((fich = br.readLine()) != null){
                sb.append(fich).append("\n");
            }

            b = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
                    //___________________________________________ Mostrar no campo do imei a hora actual
        return b;
    }*/
    //________________________________________________________________________ OBTER DATA ACTUAL _____________________________________________________
    //

    //__________________________________________________  GRAVAR NO FICHEIRO TXT _____________________________________________________
    /*public void gravarFicheiro(String horaLocal, String fichTxt){

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(fichTxt, Context.MODE_PRIVATE);
            fileOutputStream.write(horaLocal.getBytes());
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + fichTxt, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }//
    }*/
    //__________________________________________________ REDIRECIONAMENTO DOS BOTOES _____________________________________________________

    public void mobicel(View view){
        TextView txtClose2;
        myDialog.setContentView(R.layout.popupmobicel);
        txtClose2 = (TextView) myDialog.findViewById(R.id.txtClose2);
        txtClose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
    public void info(View view){
        TextView txtClose;
        myDialog.setContentView(R.layout.popupinfo);
        txtClose = (TextView) myDialog.findViewById(R.id.txtClose);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    public void movitel(View view){
        TextView txtClose3;
        myDialog.setContentView(R.layout.popupmovitel);
        txtClose3  = (TextView) myDialog.findViewById(R.id.txtClose3);
        txtClose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
    public void kicka(View view){
        TextView txtClose4;
        myDialog.setContentView(R.layout.popupkicka);
        txtClose4 = (TextView) myDialog.findViewById(R.id.txtClose4);
        txtClose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
    public void facebook(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.facebook.com/groups/1030517087444723"));
        startActivity(browserIntent);

    }
    public void whatsapp(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chat.whatsapp.com/LPjfAo08kIW1ZFIC4Jb5Gy"));
        startActivity(browserIntent);


    }
    public void telegram(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/joinchat/FaTZY1MoHcx8srWS"));
        startActivity(browserIntent);


    }
    public void youtube(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC-2r2fo9jpArtG078Gg6AwA"));
        startActivity(browserIntent);
    }
}