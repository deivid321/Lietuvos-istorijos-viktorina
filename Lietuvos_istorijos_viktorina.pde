import controlP5.*;

int kraunama=-1;
import android.view.KeyEvent;
import apwidgets.*;
import android.text.InputType;
import android.view.inputmethod.EditorInfo;

import controlP5.*;
ControlP5 cp5;
ControlFont font;

ListBox list;

APMediaPlayer menusound, wrong;
Maxim maxim;
AudioPlayer clock;

boolean zaidziama, // ar dabar zaidziama
keisti = true, // ar pasikeite klausimas
rekordaishow = false, 
pertraukashow = false, 
rekordas=false, 
taisyklesshow=false;
float Jaukstis, Jilgis;
int pr, // tarpas width/10
taskai, visoTaskai, //zaidimo taskai
s, ss;  // zaidimo laikas
int pavSk=1, datSk=1;
String[] var;
String[] vardas; // atsakymu variantai ir zaidejo vardas
Button button1, button2, button3, button4, zaistidar, grizti, zaisti, rekordai, testi, baigti, taisykles; // Visi mygtukai
boolean pirmas;  // ar prasidejo naujas etapas
int laikas, // zaidimo laikas
dbr, // dabartinis klausimas
etapoNr; // Etapo numeris
String[] bklausimaiats, variantai; // atsakymai i klausimus, atsakymu variantai
float dezesaukst; // klausimo dezes aukstis
boolean baigta = false; // ar pabaiga zaidimo rodyti paskutini langa
int klausimu; // klausimu skaicius
String[] bklausimai; // visi klausimai
boolean[] buvo, pavbuvo, datosbuvo; // Panaudoti klausimai
int raidm, raidkl, raidvar, Jraid, Jraidt; // paprastos raides dydis
color bspalva1, bspalva2, bspalva3, bspalva4; // mygtuku spalva
PImage graziau, fonas, fonas1, fonas2, fonas3;

APWidgetContainer widgetContainer; 
APButton issaugoti;
APEditText textField;

Table table;
String[] rvardas;
int[] rtaskai;
PFont fontas;

PFont klFont;
PFont varFont;
PFont f;

int sk1=0, sk2=0, sk3=0;
void setup()
{
  orientation(PORTRAIT);

  zaidziama = false;
}

void draw()
{ 
  if (kraunama>1) {
    if (pertraukashow) pertrauka();
    else
      if (zaidziama) {

        zaidimas(); // daromas zaidimas

          if (ss==s) {
          if (etapoNr==1) {
            bklausimai =null;                      
            variantai = null;
            variantai = loadStrings("pavvar.txt");
            bklausimaiats= null;
            bklausimaiats = loadStrings("pavats.txt");
            pavSk = bklausimaiats.length - 2;
            PrintWriter output;
            output = createWriter("//sdcard//1etapas.txt"); 
            for (int i =0;i<=klausimu;i++)
              output.print(buvo[i]);
            output.flush();
            output.close();
          }  
          if (etapoNr==2) {
            bklausimai = loadStrings("datosKlausimai.txt"); 
            variantai = null;
            variantai = loadStrings("datosvar.txt");
            bklausimaiats= null;
            bklausimaiats = loadStrings("datosats.txt"); 
            datSk = bklausimai.length;
          }
          if (etapoNr==3) {
            bklausimai = null;
            variantai = null;
            bklausimaiats = null;
          }
          pertraukashow = true;
          zaidziama = false;
          visoTaskai+=taskai;
          clock.stop();
        }
      }

      else if (baigta) { 
        gameOver();
      }
      else if (rekordaishow) rekordai(); 
      else meniu();

    juosta();
  }
  if (kraunama==1)kraunama = 2;
  if (kraunama==0) { 
    uzkrauna();
    kraunama=1;
  }
  // Tekstas kai kraunama
  if (kraunama==-1) {
    background(253, 185, 19);
    fill(17, 113, 5);
    textSize(width/10);
    textAlign(CENTER);
    text("Kraunama...", width/2, height/2);
    kraunama = 0;
  }
}

void mousePressed()
{ 
  color ne = color(0);
  if (etapoNr==1) ne = color(0, 106, 68, 30);
  else if (etapoNr==2)ne = color(102, 61, 0, 30);
  else if (etapoNr==3) ne = color(245, 195, 0, 30);
  if (zaidziama&&pirmas==false) {
    String str= " ";
    str = bklausimaiats[dbr+1];
    if (button1.mousePressed()&&bspalva1!=ne) {
      if (var[0].contains(str)) {
        menusound.seekTo(0);
        menusound.start();
        keiciaKlausima();
      }
      else {
        wrong.seekTo(0);
        wrong.start();
        bspalva1 = ne;
        taskai-=2;
      }
    }
    if (button2.mousePressed()&&bspalva2!=ne) {
      if (var[1].contains(str)) { 
        menusound.seekTo(0);
        menusound.start();
        keiciaKlausima();
      } 
      else {
        wrong.seekTo(0);
        wrong.start();
        bspalva2 = ne;
        taskai-=2;
      }
    }
    if (button3.mousePressed()&&bspalva3!=ne) {
      if (var[2].contains(str)) { 
        menusound.seekTo(0);
        menusound.start();
        keiciaKlausima();
      } 
      else {
        wrong.seekTo(0);
        wrong.start();
        bspalva3 = ne;
        taskai-=2;
      }
    }
    if (button4.mousePressed()&&bspalva4!=ne) {
      if (var[3].contains(str)) { 
        menusound.seekTo(0);
        menusound.start();
        keiciaKlausima();
      }
      else {
        wrong.seekTo(0);
        wrong.start();
        bspalva4 = ne;
        taskai-=2;
      }
    }
  }
  // code that happens when the mouse button
  // is pressed
}

void mouseReleased()
{
  if (kraunama>1) {
    if (zaidziama==true&&baigta==false&&pertraukashow==false)
      if (baigti.mouseReleased())
      {
        clock.stop();
        zaidziama = false;                 
        paruosia();
      };
    if (pertraukashow)
      if (testi.mouseReleased()) {
        menusound.seekTo(0);
        menusound.start();
        pertraukashow = false;
        zaidziama = true;         // TESTI KAI PERTRAUKA
        taskai = 0;
        etapoNr++;
        pirmas = true;
        keisti=true;
        if (etapoNr==2) {
          dbr = (int) random(0, pavSk);
          bspalva1 = color(102, 61, 0);
          bspalva2 = bspalva1;
          bspalva3 = bspalva1;
          bspalva4 = bspalva1;
        }
        else if (etapoNr==3) {
          dbr = (int) random(0, datSk);
          bspalva1 = color(245, 195, 0);
          bspalva2 = bspalva1;
          bspalva3 = bspalva1;
          bspalva4 = bspalva1;
        }

        if (etapoNr>3) {
          zaidziama = false;
          irasorezultata();
          baigta = true;
        }
      }
    if (pertraukashow==false) {  
      boolean dabar = true;
      if (zaidziama==false&&baigta==false&&rekordaishow==false&&taisyklesshow==false) 
        if (zaisti.mouseReleased()) { 
          menusound.seekTo(0);
          menusound.start();
          zaidziama = true;                // PRADEDA ZAIDIMA
        }
      if (zaidziama==false&&baigta==false&&rekordaishow==false&&dabar&&taisyklesshow==false) 
        if (rekordai.mouseReleased()) { 
          menusound.seekTo(0);
          menusound.start();
          rekordaishow = true;                // RODO REZULTATUS
          ivesta = false;
          widgetContainer.show();
        }
      if (zaidziama==false&&rekordaishow==false&&baigta==false&&rekordaishow==false)
        if (taisykles.mouseReleased()) {
          taisyklesshow=true;
        }   

      // Kai pabaiga paspaudimai
      if (baigta) {
        if (zaistidar.mouseReleased()) {               // ZAISTI DAR
          menusound.seekTo(0);
          menusound.start();
          paruosia();
          zaidziama=true;
        }
        if (grizti.mouseReleased()) {
          menusound.seekTo(0);
          menusound.start();
          paruosia();
          zaidziama = false;                 // GRIZTI
          baigta = false;
          rekordaishow = false;
        }
      }
    }
  }
  // code that happens when the mouse button
  // is released
}

// Kai teisingai atsakai i klausima, pakeicia klausima
void keiciaKlausima() {
  if (etapoNr==1) {
    buvo[dbr] = true;
    int iesko = 1;
    do {
      dbr = (int)random(0, klausimu);
      iesko++;
    }
    while ( ( buvo[dbr]!=false||dbr>=klausimu)&&iesko<=klausimu*5);
    if (sk1>=klausimu) {
      for (int i=0;i<=klausimu;i++)
        buvo[i] = false;
      sk1=0;
      println("Is Naujo");
    }  
    bspalva1 = color(0, 106, 68);
    bspalva2 = bspalva1;
    bspalva3 = bspalva1;
    bspalva4 = bspalva1;
    buvo[dbr] = true;
    taskai+=3;
    keisti = true;
  }
  else if (etapoNr==2) {
    pavbuvo[dbr] = true;
    int iesko = 1;
    do {
      dbr = (int)random(0, pavSk);
      iesko++;
    }
    while ( ( pavbuvo[dbr]!=false||dbr>=pavSk)&&iesko<=pavSk*5);

    bspalva1 = color(102, 61, 0);
    bspalva2 = bspalva1;
    bspalva3 = bspalva1;
    bspalva4 = bspalva1;
    pavbuvo[dbr] = true;
    taskai+=3;
    if (sk2 >= pavSk) {
      for (int i=0;i<=pavSk;i++)
        pavbuvo[i] = false;
      sk2=0;
      println("Is Naujo");
    };
    keisti = true;
  }
  else if (etapoNr==3) {

    datosbuvo[dbr] = true;
    int iesko = 1;
    do {
      dbr = (int)random(0, datSk);
      iesko++;
    }
    while ( ( datosbuvo[dbr]!=false||dbr>=datSk)&&iesko<=datSk*5);

    bspalva1 = color(245, 195, 0);
    bspalva2 = bspalva1;
    bspalva3 = bspalva1;
    bspalva4 = bspalva1;
    datosbuvo[dbr] = true;
    taskai+=3;
    if (sk3>=datSk) {
      for (int i=0;i<=datSk;i++)
        datosbuvo[i] = false; 
      sk3=0;
      println("Is Naujo");
    };
    keisti = true;
  }
} 

// Langas kada zaidimo pabaiga
void gameOver() {
  // Spausdinamas tekstas
  background(253, 185, 19);
  fill(255);
  textAlign(CENTER);
  textSize(width/9);
  text("Iš viso surinkai: ", pr, height/13, width-pr, height/3);
  String tsk = str(visoTaskai);
  textSize(width/4);
  text(tsk, pr, height/5, width-pr-pr, height/4);
  textSize(width/9);
  text("taškų.", pr, height/2.6, width-pr-pr, height/3);
  color spa = color(0, 106, 68);
  textSize(raidm);
  zaistidar.display(spa);
  grizti.display(spa);

  if (rekordas) {
    fill(255, 0, 0, 160);
    String zin = "Rekordas!";
    textSize(width/5);
    text(zin, 0, pr*4, width, pr*7);
  }


  tint(255, 150);
  image(graziau, pr, (int)( height/2), width-pr-pr, height/7);
  image(graziau, pr, (int)( height/2+pr+height/7+pr/2), width-pr-pr, height/7);
  noTint();
}

// Viskas kas yra virsutinej juostoje
void juosta() {
  if (zaidziama&&pertraukashow==false) {
    if (etapoNr==1)fill(0, 106, 68);
    else if (etapoNr==2)fill(102, 61, 0);
    else fill(255, 204, 0);
    stroke(255);
    rect(0, 0, Jilgis, Jaukstis);
    if (etapoNr==1)fill(0, 106, 68);
    else if (etapoNr==2) fill(102, 61, 0);
    else fill(255, 204, 0);
    rect(Jilgis, 0, width, Jaukstis);  
    fill(255);
    if (sriftas) textFont(klFont);
    textSize(Jraid);
    textAlign(CENTER);
    text("Liko laiko: "+laikas, 5, Jaukstis/4.5, Jilgis, Jaukstis);
    fill(255);
    textSize(Jraidt);
    textAlign(CENTER);
    String tsk = str(taskai);
    text(tsk, Jilgis, Jaukstis/4.5, width-Jilgis, Jaukstis);//, width, Jaukstis);
    color cl = color(255, 0, 0);
    baigti.display(cl);
  }
}

// Paruosia kintamus naujam zaidimui
void paruosia() {
  visoTaskai = 0;
  etapoNr = 1;
  taskai = 0;
  pirmas = true;
  baigta=false;
  keisti = true;

  bspalva1 = color(0, 106, 68);
  bspalva2 = bspalva1;
  bspalva3 = bspalva1;
  bspalva4 = bspalva1;
  dbr = (int) random(0, klausimu-1);
  buvo[dbr]=true;
  rekordas=false;
  bklausimai = null;
  bklausimai = loadStrings("bendri klausimai.txt");
  variantai = null;
  variantai = loadStrings("bendri_klausimai_var.txt");
  bklausimaiats = null;
  bklausimaiats = loadStrings("bendri_klausimai_ats.txt");
  klausimu = bklausimai.length;
}

void atnaujinavarda() {
  try {
    vardas = loadStrings("\\sdcard\\vardas.txt");
    textField.setText(vardas[1]);
  }
  catch (NullPointerException e) {
    println("Klaida nuskaitant varda");
    vardas = new String[6];
    vardas[1] = "Žaidėjas";
    textField.setText(vardas[1]);
  }
}

void irasorezultata() {
  int sk = 0, yra;
  String[] asdf = new String[4];
  String[] kkk = new String[1];
  boolean daryt = true;
  boolean pir = false;
  asdf[0] = "1";
  try {
    asdf= loadStrings("\\sdcard\\yra.txt");
  }
  catch(NullPointerException e) {
  };

  if (asdf==null) {

    kkk[0] = "yra";
    saveStrings("\\sdcard\\yra.txt", kkk);    
    table = new Table();
    table.addColumn("vardas");
    table.addColumn("taskai");
    if (visoTaskai>71) {
      TableRow newRow = table.addRow();
      newRow.setString("vardas", vardas[1]);
      newRow.setInt("taskai", visoTaskai);
      TableRow newRow2 = table.addRow();
      newRow2.setString("vardas", "Istorikas");
      newRow2.setInt("taskai", 71);
    } 
    else {
      TableRow newRow2 = table.addRow();
      newRow2.setString("vardas", "Istorikas");
      newRow2.setInt("taskai", 71);
      TableRow newRow = table.addRow();
      newRow.setString("vardas", vardas[1]);
      newRow.setInt("taskai", visoTaskai);
    }

    saveTable(table, "\\sdcard\\rezultatai.csv");
    daryt = false;
  }  
  if (daryt) {

    table = loadTable("\\sdcard\\rezultatai.csv", "header");
    rvardas = new String[table.getRowCount()+1];
    rtaskai = new int[table.getRowCount()+1];
    sk = 0;
    for (TableRow row : table.rows()) {
      rvardas[sk] = row.getString("vardas");
      rtaskai[sk] = row.getInt("taskai");
      sk++;
    }
    boolean idejo = false;
    sk = 0; 
    yra = table.getRowCount() -1;
    while (!idejo&&sk<rtaskai.length) {
      if (visoTaskai>rtaskai[0]) {
        rekordas=true;
      }
      if (visoTaskai>rtaskai[sk]) {
        for (int b = table.getRowCount()-1; b>=sk;b--) {
          rtaskai[b+1] = rtaskai[b];
          rvardas[b+1] = rvardas[b];
        }
        rvardas[sk] = vardas[1];
        rtaskai[sk] = visoTaskai;
        idejo = true;
      }
      if (sk==rtaskai.length-1&&idejo==false) { 
        idejo = true;
        rvardas[sk] = vardas[1];
        rtaskai[sk] = visoTaskai;
      }
      sk++;
    }
    table = new Table();

    table.addColumn("vardas");
    table.addColumn("taskai");
    for (sk=0;sk<=yra+1;sk++) {
      if (sk+1<=20) {
        TableRow newRow = table.addRow();
        newRow.setString("vardas", rvardas[sk]);
        newRow.setInt("taskai", rtaskai[sk]);
      }
    }
    saveTable(table, "\\sdcard\\rezultatai.csv");
  }
}

