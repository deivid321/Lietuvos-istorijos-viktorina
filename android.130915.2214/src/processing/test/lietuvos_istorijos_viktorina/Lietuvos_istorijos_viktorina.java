package processing.test.lietuvos_istorijos_viktorina;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import android.view.KeyEvent; 
import apwidgets.*; 
import android.text.InputType; 
import android.view.inputmethod.EditorInfo; 
import controlP5.*; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.io.BufferedInputStream; 
import java.net.MalformedURLException; 
import java.net.URL; 
import android.app.Activity; 
import android.os.Bundle; 
import android.media.*; 
import android.media.audiofx.Visualizer; 
import android.content.res.AssetFileDescriptor; 
import android.hardware.*; 

import ketai.net.nfc.record.*; 
import ketai.camera.*; 
import ketai.net.*; 
import ketai.ui.*; 
import ketai.cv.facedetector.*; 
import controlP5.*; 
import ketai.sensors.*; 
import ketai.net.nfc.*; 
import ketai.data.*; 
import ketai.net.bluetooth.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Lietuvos_istorijos_viktorina extends PApplet {

int kraunama=-1;





//import ketai.ui.*;
//KetaiList selectionlist;
//KetaiVibrate vibe;
//ArrayList<String> rezlist = new ArrayList<String>();

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
rekordas=false;
float Jaukstis, Jilgis;
int pr, // tarpas width/10
taskai, visoTaskai, //zaidimo taskai
s, ss;  // zaidimo laikas
int pavSk=1, datSk=1;
String[] var, pavvar, pavats, datosvar, datosats;
String[] vardas; // atsakymu variantai ir zaidejo vardas
Button button1, button2, button3, button4, zaistidar, grizti, zaisti, rekordai, testi, baigti; // Visi mygtukai
boolean pirmas;  // ar prasidejo naujas etapas
int laikas, // zaidimo laikas
dbr, // dabartinis klausimas
etapoNr; // Etapo numeris
String[] bklausimaiats, variantai; // atsakymai i klausimus, atsakymu variantai
float dezesaukst; // klausimo dezes aukstis
boolean baigta = false; // ar pabaiga zaidimo rodyti paskutini langa
int klausimu; // klausimu skaicius
String[] bklausimai, dklausimai; // visi klausimai
boolean[] buvo, pavbuvo, datosbuvo; // Panaudoti klausimai
int raidm, raidkl, raidvar, Jraid, Jraidt; // paprastos raides dydis
int bspalva1, bspalva2, bspalva3, bspalva4; // mygtuku spalva
PImage graziau, fonas, fonas1, fonas2, fonas3;
PImage[] etapo2Pav; 

APWidgetContainer widgetContainer; 
APButton issaugoti;
APEditText textField;

Table table;
String[] rvardas;
int[] rtaskai;
PFont fontas;

PFont klFont;
PFont varFont;

public void setup()
{
  orientation(PORTRAIT);

  zaidziama = false;
}

public void draw()
{ 
  if (kraunama>1) {
    if (pertraukashow) pertrauka();
    else
      if (zaidziama) {

        zaidimas(); // daromas zaidimas

          if (ss==s) {
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

public void mousePressed()
{ 
  int ne = color(0);
  if (etapoNr==1) ne = color(0, 106, 68, 30);
  else if (etapoNr==2)ne = color(102, 61, 0, 30);
  else if (etapoNr==3) ne = color(255, 204, 0, 30);
  if (zaidziama&&pirmas==false) {
    String str= " ";
    if (etapoNr==1)
      str = bklausimaiats[dbr+1];
    else if (etapoNr==2)
      str = pavats[dbr+1];
    else if (etapoNr==3)
      str = datosats[dbr+1];
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

public void mouseReleased()
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
          bspalva1 = color(255, 204, 0);
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
      if (zaidziama==false&&baigta==false&&rekordaishow==false) 
        if (zaisti.mouseReleased()) { 
          menusound.seekTo(0);
          menusound.start();
          zaidziama = true;                // PRADEDA ZAIDIMA
        }
      if (zaidziama==false&&baigta==false&&rekordaishow==false&&dabar) 
        if (rekordai.mouseReleased()) { 
          menusound.seekTo(0);
          menusound.start();
          rekordaishow = true;                // RODO REZULTATUS
          ivesta = false;
          widgetContainer.show();
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
public void keiciaKlausima() {
  if (etapoNr==1) {
    buvo[dbr] = true;
    int iesko = 1;
    do {
      dbr = (int)random(0, klausimu);
      iesko++;
    }
    while ( ( buvo[dbr]!=false||dbr>=klausimu)&&iesko<=klausimu*5);

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

    bspalva1 = color(255, 204, 0);
    bspalva2 = bspalva1;
    bspalva3 = bspalva1;
    bspalva4 = bspalva1;
    datosbuvo[dbr] = true;
    taskai+=3;
    keisti = true;
  }
} 

// Langas kada zaidimo pabaiga
public void gameOver() {
  // Spausdinamas tekstas
  background(253, 185, 19);
  fill(255);
  textAlign(CENTER);
  textSize(width/9);
  text("I\u0161 viso surinkai: ", pr, height/13, width-pr, height/3);
  String tsk = str(visoTaskai);
  textSize(width/4);
  text(tsk, pr, height/5, width-pr-pr, height/4);
  textSize(width/9);
  text("ta\u0161k\u0173.", pr, height/2.6f, width-pr-pr, height/3);
  int spa = color(0, 106, 68);
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
public void juosta() {
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
    textSize(Jraid);
    textAlign(CENTER);
    text("Liko laiko: "+laikas, 5, 5, Jilgis, Jaukstis);
    fill(255);
    textSize(Jraidt);
    textAlign(CENTER);
    String tsk = str(taskai);
    text(tsk, Jilgis, 5, width-Jilgis, Jaukstis);//, width, Jaukstis);
    int cl = color(255, 0, 0);
    baigti.display(cl);
  }
}

// Paruosia kintamus naujam zaidimui
public void paruosia() {
  visoTaskai = 0;
  etapoNr = 1;
  taskai = 0;
  pirmas = true;
  baigta=false;
  keisti = true;
  for (int i=0;i<=klausimu;i++)
    buvo[i] = false;
  for (int i=0;i<=pavSk;i++)
    pavbuvo[i] = false;
  for (int i=0;i<=datSk;i++)
    datosbuvo[i] = false;   
  bspalva1 = color(0, 106, 68);
  bspalva2 = bspalva1;
  bspalva3 = bspalva1;
  bspalva4 = bspalva1;
  dbr = (int) random(0, klausimu-1);
  buvo[dbr]=true;
  rekordas=false;
}

public void atnaujinavarda() {
  try {
    vardas = loadStrings("\\sdcard\\vardas.txt");
    textField.setText(vardas[1]);
  }
  catch (NullPointerException e) {
    println("Klaida nuskaitant varda");
    vardas = new String[6];
    vardas[1] = "Istorikas";
    textField.setText(vardas[1]);
  }
}

public void irasorezultata() {
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

    TableRow newRow = table.addRow();
    newRow.setString("vardas", vardas[1]);
    newRow.setInt("taskai", visoTaskai);

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


int HORIZONTAL = 0;
int VERTICAL   = 1;
int UPWARDS    = 2;
int DOWNWARDS  = 3;

class Widget
{

  
  PVector pos;
  PVector extents;
  String name;

  int inactiveColor = color(60, 60, 100);
  int activeColor = color(100, 100, 160);
  int bgColor = inactiveColor;
  int lineColor = color(255);
  
  
  
  public void setInactiveColor(int c)
  {
    inactiveColor = c;
    bgColor = inactiveColor;
  }
  
  public int getInactiveColor()
  {
    return inactiveColor;
  }
  
  public void setActiveColor(int c)
  {
    activeColor = c;
  }
  
  public int getActiveColor()
  {
    return activeColor;
  }
  
  public void setLineColor(int c)
  {
    lineColor = c;
  }
  
  public int getLineColor()
  {
    return lineColor;
  }
  
  public String getName()
  {
    return name;
  }
  
  public void setName(String nm)
  {
    name = nm;
  }


  Widget(String t, int x, int y, int w, int h)
  {
    pos = new PVector(x, y);
    extents = new PVector (w, h);
    name = t;
    //registerMethod("mouseEvent", this);
  }

  public void display()
  {
  }

  public boolean isClicked()
  {
    
    if (mouseX > pos.x && mouseX < pos.x+extents.x 
      && mouseY > pos.y && mouseY < pos.y+extents.y)
    {
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public void mouseEvent(MouseEvent event)
  {
    //if (event.getFlavor() == MouseEvent.PRESS)
    //{
    //  mousePressed();
    //}
  }
  
  
  public boolean mousePressed()
  {
    return isClicked();
  }
  
  public boolean mouseDragged()
  {
    return isClicked();
  }
  
  
  public boolean mouseReleased()
  {
    return isClicked();
  }
}

class Button extends Widget
{
  PImage activeImage = null;
  PImage inactiveImage = null;
  PImage currentImage = null;
  int imageTint = color(255);
  
  Button(String nm, int x, int y, int w, int h)
  {
    super(nm, x, y, w, h);
  }
  
  public void setImage(PImage img)
  {
    setInactiveImage(img);
    setActiveImage(img);
  }
  
  public void setInactiveImage(PImage img)
  {
    if(currentImage == inactiveImage || currentImage == null)
    {
      inactiveImage = img;
      currentImage = inactiveImage;
    }
    else
    {
      inactiveImage = img;
    }
  }
  
  public void setActiveImage(PImage img)
  {
    if(currentImage == activeImage || currentImage == null)
    {
      activeImage = img;
      currentImage = activeImage;
    }
    else
    {
      activeImage = img;
    }
  }
  
  public void setImageTint(float r, float g, float b)
  {
    imageTint = color(r,g,b);
  }

  public void display(int sp)
  {
    if(currentImage != null)
    {
      //float imgHeight = (extents.x*currentImage.height)/currentImage.width;
      float imgWidth = (extents.y*currentImage.width)/currentImage.height;
      
      
      pushStyle();
      imageMode(CORNER);
      tint(imageTint);
      image(currentImage, pos.x, pos.y, imgWidth, extents.y);
      stroke(bgColor);
      noFill();
      rect(pos.x, pos.y, imgWidth,  extents.y);
      noTint();
      popStyle();
      
    }
    else
    {
      pushStyle();
      stroke(lineColor);
      fill(sp);
      rect(pos.x, pos.y, extents.x, extents.y,6,6,6,6);
  
      fill(lineColor);
      textAlign(CENTER, CENTER);
      text(name, pos.x + 0.5f*extents.x, pos.y + 0.5f* extents.y);
      popStyle();
    }
  }
  
  public boolean mousePressed()
  {
    if (super.mousePressed())
    {
      if (bgColor == activeColor)
      {
        bgColor = inactiveColor;
      } else
      {
        bgColor = activeColor;
      }
      
      if(activeImage != null)
        currentImage = activeImage;
      return true;
    }
    return false;
  }
  
  public boolean mouseReleased()
  {
    if (super.mouseReleased())
    {
      bgColor = inactiveColor;
      if(inactiveImage != null)
        currentImage = inactiveImage;
      return true;
    }
    return false;
  }
}

class Toggle extends Button
{
  boolean on = false;

  Toggle(String nm, int x, int y, int w, int h)
  {
    super(nm, x, y, w, h);
  }


  public boolean get()
  {
    return on;
  }

  public void set(boolean val)
  {
    on = val;
    if (on)
    {
      bgColor = activeColor;
      if(activeImage != null)
        currentImage = activeImage;
    }
    else
    {
      bgColor = inactiveColor;
      if(inactiveImage != null)
        currentImage = inactiveImage;
    }
  }

  public void toggle()
  {
    set(!on);
  }

  
  public boolean mousePressed()
  {
    return super.isClicked();
  }

  public boolean mouseReleased()
  {
    if (super.mouseReleased())
    {
      toggle();
      return true;
    }
    return false;
  }
}

class RadioButtons extends Widget
{
  public Toggle [] buttons;
  
  RadioButtons (String [] names,int numButtons, int x, int y, int w, int h, int orientation)
  {
    super("", x, y, w*numButtons, h);
    buttons = new Toggle[numButtons];
    for (int i = 0; i < buttons.length; i++)
    {
      int bx, by;
      if(orientation == HORIZONTAL)
      {
        bx = x+i*(w+5);
        by = y;
      }
      else
      {
        bx = x;
        by = y+i*(h+5);
      }
      buttons[i] = new Toggle(names[i], bx, by, w, h);
    }
  }
  
  public void setNames(String [] names)
  {
    for (int i = 0; i < buttons.length; i++)
    {
      if(i >= names.length)
        break;
      buttons[i].setName(names[i]);
    }
  }
  
  public void setImage(int i, PImage img)
  {
    setInactiveImage(i, img);
    setActiveImage(i, img);
  }
  
  public void setAllImages(PImage [] img)
  {
    setAllInactiveImages(img);
    setAllActiveImages(img);
  }
  
  public void setInactiveImage(int i, PImage img)
  {
    buttons[i].setInactiveImage(img);
  }

  
  public void setAllInactiveImages(PImage [] img)
  {
    for (int i = 0; i < buttons.length; i++)
    {
      buttons[i].setInactiveImage(img[i]);
    }
  }
  
  public void setActiveImage(int i, PImage img)
  {
    
    buttons[i].setActiveImage(img);
  }
  
  
  
  public void setAllActiveImages(PImage [] img)
  {
    for (int i = 0; i < buttons.length; i++)
    {
      buttons[i].setActiveImage(img[i]);
    }
  }

  public void set(String buttonName)
  {
    for (int i = 0; i < buttons.length; i++)
    {
      if(buttons[i].getName().equals(buttonName))
      {
        buttons[i].set(true);
      }
      else
      {
        buttons[i].set(false);
      }
    }
  }
  
  public int get()
  {
    for (int i = 0; i < buttons.length; i++)
    {
      if(buttons[i].get())
      {
        return i;
      }
    }
    return -1;
  }
  
  public String getString()
  {
    for (int i = 0; i < buttons.length; i++)
    {
      if(buttons[i].get())
      {
        return buttons[i].getName();
      }
    }
    return "";
  }

  public void display()
  {
    for (int i = 0; i < buttons.length; i++)
    {
      buttons[i].display();
    }
  }

  public boolean mousePressed()
  {
    for (int i = 0; i < buttons.length; i++)
    {
      if(buttons[i].mousePressed())
      {
        return true;
      }
    }
    return false;
  }
  
  public boolean mouseDragged()
  {
    for (int i = 0; i < buttons.length; i++)
    {
      if(buttons[i].mouseDragged())
      {
        return true;
      }
    }
    return false;
  }

  public boolean mouseReleased()
  {
    for (int i = 0; i < buttons.length; i++)
    {
      if(buttons[i].mouseReleased())
      {
        for(int j = 0; j < buttons.length; j++)
        {
          if(i != j)
            buttons[j].set(false);
        }
        //buttons[i].set(true);
        return true;
      }
    }
    return false;
  }
}

class Slider extends Widget
{
  float minimum;
  float maximum;
  float val;
  int textWidth = 60;
  int orientation = HORIZONTAL;

  Slider(String nm, float v, float min, float max, int x, int y, int w, int h, int ori)
  {
    super(nm, x, y, w, h);
    val = v;
    minimum = min;
    maximum = max;
    orientation = ori;
    if(orientation == HORIZONTAL)
      textWidth = 60;
    else
      textWidth = 20;
    
  }

  public float get()
  {
    return val;
  }

  public void set(float v)
  {
    val = v;
    val = constrain(val, minimum, maximum);
  }

  public void display()
  {
    
    float textW = textWidth;
    if(name == "")
      textW = 0;
    pushStyle();
    textAlign(LEFT, TOP);
    fill(lineColor);
    text(name, pos.x, pos.y);
    stroke(lineColor);
    noFill();
    if(orientation ==  HORIZONTAL){
      rect(pos.x+textW, pos.y, extents.x-textWidth, extents.y);
    } else {
      rect(pos.x, pos.y+textW, extents.x, extents.y-textW);
    }
    noStroke();
    fill(bgColor);
    float sliderPos; 
    if(orientation ==  HORIZONTAL){
        sliderPos = map(val, minimum, maximum, 0, extents.x-textW-4); 
        rect(pos.x+textW+2, pos.y+2, sliderPos, extents.y-4);
    } else if(orientation ==  VERTICAL || orientation == DOWNWARDS){
        sliderPos = map(val, minimum, maximum, 0, extents.y-textW-4); 
        rect(pos.x+2, pos.y+textW+2, extents.x-4, sliderPos);
    } else if(orientation == UPWARDS){
        sliderPos = map(val, minimum, maximum, 0, extents.y-textW-4); 
        rect(pos.x+2, pos.y+textW+2 + (extents.y-textW-4-sliderPos), extents.x-4, sliderPos);
    };
    popStyle();
  }

  
  public boolean mouseDragged()
  {
    if (super.mouseDragged())
    {
      float textW = textWidth;
      if(name == "")
        textW = 0;
      if(orientation ==  HORIZONTAL){
        set(map(mouseX, pos.x+textW, pos.x+extents.x-4, minimum, maximum));
      } else if(orientation ==  VERTICAL || orientation == DOWNWARDS){
        set(map(mouseY, pos.y+textW, pos.y+extents.y-4, minimum, maximum));
      } else if(orientation == UPWARDS){
        set(map(mouseY, pos.y+textW, pos.y+extents.y-4, maximum, minimum));
      };
      return true;
    }
    return false;
  }

  public boolean mouseReleased()
  {
    if (super.mouseReleased())
    {
      float textW = textWidth;
      if(name == "")
        textW = 0;
      if(orientation ==  HORIZONTAL){
        set(map(mouseX, pos.x+textW, pos.x+extents.x-10, minimum, maximum));
      } else if(orientation ==  VERTICAL || orientation == DOWNWARDS){
        set(map(mouseY, pos.y+textW, pos.y+extents.y-10, minimum, maximum));
      } else if(orientation == UPWARDS){
        set(map(mouseY, pos.y+textW, pos.y+extents.y-10, maximum, minimum));
      };
      return true;
    }
    return false;
  }
}

class MultiSlider extends Widget
{
  Slider [] sliders;
  /*
  MultiSlider(String [] nm, float min, float max, int x, int y, int w, int h, int orientation)
  {
    super(nm[0], x, y, w, h*nm.length);
    sliders = new Slider[nm.length];
    for (int i = 0; i < sliders.length; i++)
    {
      int bx, by;
      if(orientation == HORIZONTAL)
      {
        bx = x;
        by = y+i*h;
      }
      else
      {
        bx = x+i*w;
        by = y;
      }
      sliders[i] = new Slider(nm[i], 0, min, max, bx, by, w, h, orientation);
    }
  }
  */
  MultiSlider(int numSliders, float min, float max, int x, int y, int w, int h, int orientation)
  {
    super("", x, y, w, h*numSliders);
    sliders = new Slider[numSliders];
    for (int i = 0; i < sliders.length; i++)
    {
      int bx, by;
      if(orientation == HORIZONTAL)
      {
        bx = x;
        by = y+i*h;
      }
      else
      {
        bx = x+i*w;
        by = y;
      }
      sliders[i] = new Slider("", 0, min, max, bx, by, w, h, orientation);
    }
  }
  
  public void setNames(String [] names)
  {
    for (int i = 0; i < sliders.length; i++)
    {
      if(i >= names.length)
        break;
      sliders[i].setName(names[i]);
    }
  }

  public void set(int i, float v)
  {
    if(i >= 0 && i < sliders.length)
    {
      sliders[i].set(v);
    }
  }
  
  public float get(int i)
  {
    if(i >= 0 && i < sliders.length)
    {
      return sliders[i].get();
    }
    else
    {
      return -1;
    }
    
  }

  public void display()
  {
    for (int i = 0; i < sliders.length; i++)
    {
      sliders[i].display();
    }
  }

  
  public boolean mouseDragged()
  {
    for (int i = 0; i < sliders.length; i++)
    {
      if(sliders[i].mouseDragged())
      {
        return true;
      }
    }
    return false;
  }

  public boolean mouseReleased()
  {
    for (int i = 0; i < sliders.length; i++)
    {
      if(sliders[i].mouseReleased())
      {
        return true;
      }
    }
    return false;
  }
}

/*
The MIT License (MIT)
 
 Copyright (c) 2013 Mick Grierson, Matthew Yee-King, Marco Gillies
 
 Permission is hereby granted, free of charge, to any person obtaining a copy\u2028of 
 this software and associated documentation files (the "Software"), to 
 deal\u2028in the Software without restriction, including without limitation 
 the rights\u2028to use, copy, modify, merge, publish, distribute, sublicense, 
 and/or sell\u2028copies of the Software, and to permit persons to whom the 
 Software is\u2028furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included 
 in \u2028all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\u2028IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\u2028FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\u2028AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\u2028LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\u2028OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN\u2028THE SOFTWARE.
 */

// putting this up in global scope for consistency with maxim.js
// eventually, this should be inside Maxim in all versions of the library...
float[] mtof = {
    0f, 8.661957f, 9.177024f, 9.722718f, 10.3f, 10.913383f, 11.562325f, 12.25f, 12.978271f, 13.75f, 14.567617f, 15.433853f, 16.351599f, 17.323914f, 18.354048f, 19.445436f, 20.601723f, 21.826765f, 23.124651f, 24.5f, 25.956543f, 27.5f, 29.135235f, 30.867706f, 32.703197f, 34.647827f, 36.708096f, 38.890873f, 41.203445f, 43.65353f, 46.249302f, 49.f, 51.913086f, 55.f, 58.27047f, 61.735413f, 65.406395f, 69.295654f, 73.416191f, 77.781746f, 82.406891f, 87.30706f, 92.498604f, 97.998856f, 103.826172f, 110.f, 116.540939f, 123.470825f, 130.81279f, 138.591309f, 146.832382f, 155.563492f, 164.813782f, 174.61412f, 184.997208f, 195.997711f, 207.652344f, 220.f, 233.081879f, 246.94165f, 261.62558f, 277.182617f, 293.664764f, 311.126984f, 329.627563f, 349.228241f, 369.994415f, 391.995422f, 415.304688f, 440.f, 466.163757f, 493.883301f, 523.25116f, 554.365234f, 587.329529f, 622.253967f, 659.255127f, 698.456482f, 739.988831f, 783.990845f, 830.609375f, 880.f, 932.327515f, 987.766602f, 1046.502319f, 1108.730469f, 1174.659058f, 1244.507935f, 1318.510254f, 1396.912964f, 1479.977661f, 1567.981689f, 1661.21875f, 1760.f, 1864.655029f, 1975.533203f, 2093.004639f, 2217.460938f, 2349.318115f, 2489.015869f, 2637.020508f, 2793.825928f, 2959.955322f, 3135.963379f, 3322.4375f, 3520.f, 3729.31f, 3951.066406f, 4186.009277f, 4434.921875f, 4698.63623f, 4978.031738f, 5274.041016f, 5587.651855f, 5919.910645f, 6271.926758f, 6644.875f, 7040.f, 7458.620117f, 7902.132812f, 8372.018555f, 8869.84375f, 9397.272461f, 9956.063477f, 10548.082031f, 11175.303711f, 11839.821289f, 12543.853516f, 13289.75f
  };








//import android.content.res.Resources;
 
 






public class Maxim {

  private float sampleRate = 44100;

  public final float[] mtof = {
    0, 8.661957f, 9.177024f, 9.722718f, 10.3f, 10.913383f, 11.562325f, 12.25f, 12.978271f, 13.75f, 14.567617f, 15.433853f, 16.351599f, 17.323914f, 18.354048f, 19.445436f, 20.601723f, 21.826765f, 23.124651f, 24.5f, 25.956543f, 27.5f, 29.135235f, 30.867706f, 32.703197f, 34.647827f, 36.708096f, 38.890873f, 41.203445f, 43.65353f, 46.249302f, 49.f, 51.913086f, 55.f, 58.27047f, 61.735413f, 65.406395f, 69.295654f, 73.416191f, 77.781746f, 82.406891f, 87.30706f, 92.498604f, 97.998856f, 103.826172f, 110.f, 116.540939f, 123.470825f, 130.81279f, 138.591309f, 146.832382f, 155.563492f, 164.813782f, 174.61412f, 184.997208f, 195.997711f, 207.652344f, 220.f, 233.081879f, 246.94165f, 261.62558f, 277.182617f, 293.664764f, 311.126984f, 329.627563f, 349.228241f, 369.994415f, 391.995422f, 415.304688f, 440.f, 466.163757f, 493.883301f, 523.25116f, 554.365234f, 587.329529f, 622.253967f, 659.255127f, 698.456482f, 739.988831f, 783.990845f, 830.609375f, 880.f, 932.327515f, 987.766602f, 1046.502319f, 1108.730469f, 1174.659058f, 1244.507935f, 1318.510254f, 1396.912964f, 1479.977661f, 1567.981689f, 1661.21875f, 1760.f, 1864.655029f, 1975.533203f, 2093.004639f, 2217.460938f, 2349.318115f, 2489.015869f, 2637.020508f, 2793.825928f, 2959.955322f, 3135.963379f, 3322.4375f, 3520.f, 3729.31f, 3951.066406f, 4186.009277f, 4434.921875f, 4698.63623f, 4978.031738f, 5274.041016f, 5587.651855f, 5919.910645f, 6271.926758f, 6644.875f, 7040.f, 7458.620117f, 7902.132812f, 8372.018555f, 8869.84375f, 9397.272461f, 9956.063477f, 10548.082031f, 11175.303711f, 11839.821289f, 12543.853516f, 13289.75f
  };

  private AndroidAudioThread audioThread;

  public Maxim (PApplet app) {
    audioThread = new AndroidAudioThread(sampleRate, 256, false);
    audioThread.start();
  }

  public float[] getPowerSpectrum() {
    return audioThread.getPowerSpectrum();
  }

  /** 
   *  load the sent file into an audio player and return it. Use
   *  this if your audio file is not too long want precision control
   *  over looping and play head position
   * @param String filename - the file to load
   * @return AudioPlayer - an audio player which can play the file
   */
  public AudioPlayer loadFile(String filename) {
    // this will load the complete audio file into memory
    AudioPlayer ap = new AudioPlayer(filename, sampleRate);
    audioThread.addAudioGenerator(ap);
    // now we need to tell the audiothread
    // to ask the audioplayer for samples
    return ap;
  }

  /**
   * Create a wavetable player object with a wavetable of the sent
   * size. Small wavetables (<128) make for a 'nastier' sound!
   * 
   */
  public WavetableSynth createWavetableSynth(int size) {
    // this will load the complete audio file into memory
    WavetableSynth ap = new WavetableSynth(size, sampleRate);
    audioThread.addAudioGenerator(ap);
    // now we need to tell the audiothread
    // to ask the audioplayer for samples
    return ap;
  }
  /**
   * Create an AudioStreamPlayer which can stream audio from the
   * internet as well as local files.  Does not provide precise
   * control over looping and playhead like AudioPlayer does.  Use this for
   * longer audio files and audio from the internet.
   */
  public AudioStreamPlayer createAudioStreamPlayer(String url) {
    AudioStreamPlayer asp = new AudioStreamPlayer(url);
    return asp;
  }
}



/**
 * This class can play audio files and includes an fx chain 
 */
public class AudioPlayer implements Synth, AudioGenerator {
  private FXChain fxChain;
  private boolean isPlaying;
  private boolean isLooping;
  private boolean analysing;
  private FFT fft;
  private int fftInd;
  private float[] fftFrame;
  private float[] powerSpectrum;

  //private float startTimeSecs;
  //private float speed;
  private int length;
  private short[] audioData;
  private float startPos;
  private float readHead;
  private float dReadHead;
  private float sampleRate;
  private float masterVolume;

  float x1, x2, y1, y2, x3, y3;

  public AudioPlayer(float sampleRate) {
    fxChain = new FXChain(sampleRate);
    this.dReadHead = 1;
    this.sampleRate = sampleRate;
    this.masterVolume = 1;
  }

  public AudioPlayer (String filename, float sampleRate) {
    //super(filename);
    this(sampleRate);
    try {
      // how long is the file in bytes?
      long byteCount = getAssets().openFd(filename).getLength();
      //System.out.println("bytes in "+filename+" "+byteCount);

      // check the format of the audio file first!
      // only accept mono 16 bit wavs
      InputStream is = getAssets().open(filename); 
      BufferedInputStream bis = new BufferedInputStream(is);

      // chop!!

      int bitDepth;
      int channels;
      boolean isPCM;
      // allows us to read up to 4 bytes at a time 
      byte[] byteBuff = new byte[4];

      // skip 20 bytes to get file format
      // (1 byte)
      bis.skip(20);
      bis.read(byteBuff, 0, 2); // read 2 so we are at 22 now
      isPCM = ((short)byteBuff[0]) == 1 ? true:false; 
      //System.out.println("File isPCM "+isPCM);

      // skip 22 bytes to get # channels
      // (1 byte)
      bis.read(byteBuff, 0, 2);// read 2 so we are at 24 now
      channels = (short)byteBuff[0];
      //System.out.println("#channels "+channels+" "+byteBuff[0]);
      // skip 24 bytes to get sampleRate
      // (32 bit int)
      bis.read(byteBuff, 0, 4); // read 4 so now we are at 28
      sampleRate = bytesToInt(byteBuff, 4);
      //System.out.println("Sample rate "+sampleRate);
      // skip 34 bytes to get bits per sample
      // (1 byte)
      bis.skip(6); // we were at 28...
      bis.read(byteBuff, 0, 2);// read 2 so we are at 36 now
      bitDepth = (short)byteBuff[0];
      //System.out.println("bit depth "+bitDepth);
      // convert to word count...
      bitDepth /= 8;
      // now start processing the raw data
      // data starts at byte 36
      int sampleCount = (int) ((byteCount - 36) / (bitDepth * channels));
      audioData = new short[sampleCount];
      int skip = (channels -1) * bitDepth;
      int sample = 0;
      // skip a few sample as it sounds like shit
      bis.skip(bitDepth * 4);
      while (bis.available () >= (bitDepth+skip)) {
        bis.read(byteBuff, 0, bitDepth);// read 2 so we are at 36 now
        //int val = bytesToInt(byteBuff, bitDepth);
        // resample to 16 bit by casting to a short
        audioData[sample] = (short) bytesToInt(byteBuff, bitDepth);
        bis.skip(skip);
        sample ++;
      }

      float secs = (float)sample / (float)sampleRate;
      //System.out.println("Read "+sample+" samples expected "+sampleCount+" time "+secs+" secs ");      
      bis.close();


      // unchop
      readHead = 0;
      startPos = 0;
      // default to 1 sample shift per tick
      dReadHead = 1;
      isPlaying = false;
      isLooping = true;
      masterVolume = 1;
    } 
    catch (FileNotFoundException e) {

      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setAnalysing(boolean analysing_) {
    this.analysing = analysing_;
    if (analysing) {// initialise the fft
      fft = new FFT();
      fftInd = 0;
      fftFrame = new float[1024];
      powerSpectrum = new float[fftFrame.length/2];
    }
  }

  public float getAveragePower() {
    if (analysing) {
      // calc the average
      float sum = 0;
      for (int i=0;i<powerSpectrum.length;i++) {
        sum += powerSpectrum[i];
      }
      sum /= powerSpectrum.length;
      return sum;
    }
    else {
      System.out.println("call setAnalysing to enable power analysis");
      return 0;
    }
  }
  public float[] getPowerSpectrum() {
    if (analysing) {
      return powerSpectrum;
    }
    else {
      System.out.println("call setAnalysing to enable power analysis");
      return null;
    }
  }

  /** 
   *convert the sent byte array into an int. Assumes little endian byte ordering. 
   *@param bytes - the byte array containing the data
   *@param wordSizeBytes - the number of bytes to read from bytes array
   *@return int - the byte array as an int
   */
  private int bytesToInt(byte[] bytes, int wordSizeBytes) {
    int val = 0;
    for (int i=wordSizeBytes-1; i>=0; i--) {
      val <<= 8;
      val |= (int)bytes[i] & 0xFF;
    }
    return val;
  }

  /**
   * Test if this audioplayer is playing right now
   * @return true if it is playing, false otherwise
   */
  public boolean isPlaying() {
    return isPlaying;
  }

  /**
   * Set the loop mode for this audio player
   * @param looping 
   */
  public void setLooping(boolean looping) {
    isLooping = looping;
  }

  /**
   * Move the start pointer of the audio player to the sent time in ms
   * @param timeMs - the time in ms
   */
  public void cue(int timeMs) {
    //startPos = ((timeMs / 1000) * sampleRate) % audioData.length;
    //readHead = startPos;
    //System.out.println("AudioPlayer Cueing to "+timeMs);
    if (timeMs >= 0) {// ignore crazy values
      readHead = (((float)timeMs / 1000f) * sampleRate) % audioData.length;
      //System.out.println("Read head went to "+readHead);
    }
  }

  /**
   *  Set the playback speed,
   * @param speed - playback speed where 1 is normal speed, 2 is double speed
   */
  public void speed(float speed) {
    //System.out.println("setting speed to "+speed);
    dReadHead = speed;
  }

  /**
   * Set the master volume of the AudioPlayer
   */

  public void volume(float volume) {
    masterVolume = volume;
  }

  /**
   * Get the length of the audio file in samples
   * @return int - the  length of the audio file in samples
   */
  public int getLength() {
    return audioData.length;
  }
  /**
   * Get the length of the sound in ms, suitable for sending to 'cue'
   */
  public float getLengthMs() {
    return ((float) audioData.length / sampleRate * 1000f);
  }

  /**
   * Start playing the sound. 
   */
  public void play() {
    isPlaying = true;
  }

  /**
   * Stop playing the sound
   */
  public void stop() {
    isPlaying = false;
  }

  /**
   * implementation of the AudioGenerator interface
   */
  public short getSample() {
    if (!isPlaying) {
      return 0;
    }
    else {
      short sample;
      readHead += dReadHead;
      if (readHead > (audioData.length - 1)) {// got to the end
        //% (float)audioData.length;
        if (isLooping) {// back to the start for loop mode
          readHead = readHead % (float)audioData.length;
        }
        else {
          readHead = 0;
          isPlaying = false;
        }
      }

      // linear interpolation here
      // declaring these at the top...
      // easy to understand version...
      //      float x1, x2, y1, y2, x3, y3;
      x1 = floor(readHead);
      x2 = x1 + 1;
      y1 = audioData[(int)x1];
      y2 = audioData[(int) (x2 % audioData.length)];
      x3 = readHead;
      // calc 
      y3 =  y1 + ((x3 - x1) * (y2 - y1));
      y3 *= masterVolume;
      sample = fxChain.getSample((short) y3);
      if (analysing) {
        // accumulate samples for the fft
        fftFrame[fftInd] = (float)sample / 32768f;
        fftInd ++;
        if (fftInd == fftFrame.length - 1) {// got a frame
          powerSpectrum = fft.process(fftFrame, true);
          fftInd = 0;
        }
      }
      // println(audioData[(int)x1]);
      return sample;
      //return (short)y3;
      //return audioData[(int)x1];
    }
  }

  public void setAudioData(short[] audioData) {
    //println(audioData[100]);
    this.audioData = audioData;
  }

  public short[] getAudioData() {
    return audioData;
  }

  public void setDReadHead(float dReadHead) {
    this.dReadHead = dReadHead;
  }

  ///
  //the synth interface
  // 

  public void ramp(float val, float timeMs) {
    fxChain.ramp(val, timeMs);
  } 



  public void setDelayTime(float delayMs) {
    fxChain.setDelayTime( delayMs);
  }

  public void setDelayFeedback(float fb) {
    fxChain.setDelayFeedback(fb);
  }

  public void setFilter(float cutoff, float resonance) {
    fxChain.setFilter( cutoff, resonance);
  }
}

/**
 * This class can play wavetables and includes an fx chain
 */
public class WavetableSynth extends AudioPlayer {

  private short[] sine;
  private short[] saw;
  private short[] wavetable;
  private float sampleRate;

  public WavetableSynth(int size, float sampleRate) {
    super(sampleRate);
    sine = new short[size];
    for (float i = 0; i < sine.length; i++) {
      float phase;
      phase = TWO_PI / size * i;
      sine[(int)i] = (short) (sin(phase) * 32768);
    }
    saw = new short[size];
    for (float i = 0; i<saw.length; i++) {
      saw[(int)i] = (short) (i / (float)saw.length *32768);
    }

    this.sampleRate = sampleRate;
    setAudioData(saw);
    setLooping(true);
  }
  //    public short getSample() {
  //      return (short) random(0, 65536);
  //    }


  public void setFrequency(float freq) {
    if (freq > 0) {
      //System.out.println("freq freq "+freq);
      setDReadHead((float)getAudioData().length / sampleRate * freq);
    }
  }

  /** for consistency with maxim.js */
  public void waveTableSize(int size){
  }
  
  /** alias to loadWaveForm for consistency with maxim.js*/
  public void loadWaveTable(float[] wavetable_){
    loadWaveForm(wavetable_);
  }

  public void loadWaveForm(float[] wavetable_) {
    if (wavetable == null || wavetable_.length != wavetable.length) {
      // only reallocate if there is a change in length
      wavetable = new short[wavetable_.length];
    }
    for (int i=0;i<wavetable.length;i++) {
      wavetable[i] = (short) (wavetable_[i] * 32768);
    }
    setAudioData(wavetable);
  }
}

public interface Synth {
  public void volume(float volume);
  public void ramp(float val, float timeMs);  
  public void setDelayTime(float delayMs);  
  public void setDelayFeedback(float fb);  
  public void setFilter(float cutoff, float resonance);
  public void setAnalysing(boolean analysing);
  public float getAveragePower();
  public float[] getPowerSpectrum();
}

public class AndroidAudioThread extends Thread
{
  private int minSize;
  private AudioTrack track;
  private short[] bufferS;
  private float[] bufferF;
  private ArrayList audioGens;
  private boolean running;

  private FFT fft;
  private float[] fftFrame;


  public AndroidAudioThread(float samplingRate, int bufferLength) {
    this(samplingRate, bufferLength, false);
  }

  public AndroidAudioThread(float samplingRate, int bufferLength, boolean enableFFT)
  {
    audioGens = new ArrayList();
    minSize =AudioTrack.getMinBufferSize( (int)samplingRate, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT );        
    //println();
    // note that we set the buffer just to something small
    // not to the minSize
    // setting to minSize seems to cause glitches on the delivery of audio 
    // to the sound card (i.e. ireegular delivery rate)
    bufferS = new short[bufferLength];
    bufferF = new float[bufferLength];

    track = new AudioTrack( AudioManager.STREAM_MUSIC, (int)samplingRate, 
    AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, 
    minSize, AudioTrack.MODE_STREAM);
    track.play();

    if (enableFFT) {
      try {
        fft = new FFT();
      }
      catch(Exception e) {
        println("Error setting up the audio analyzer");
        e.printStackTrace();
      }
    }
  }

  /**
   * Returns a recent snapshot of the power spectrum as 8 bit values
   */
  public float[] getPowerSpectrum() {
    // process the last buffer that was calculated
    if (fftFrame == null) {
      fftFrame = new float[bufferS.length];
    }
    for (int i=0;i<fftFrame.length;i++) {
      fftFrame[i] = ((float) bufferS[i] / 32768f);
    }
    return fft.process(fftFrame, true);
    //return powerSpectrum;
  }

  // overidden from Thread
  public void run() {
    running = true;
    while (running) {
      //System.out.println("AudioThread : ags  "+audioGens.size());
      for (int i=0;i<bufferS.length;i++) {
        // we add up using a 32bit int
        // to prevent clipping
        int val = 0;
        if (audioGens.size() > 0) {
          for (int j=0;j<audioGens.size(); j++) {
            AudioGenerator ag = (AudioGenerator)audioGens.get(j);
            val += ag.getSample();
          }
          val /= audioGens.size();
        }
        bufferS[i] = (short) val;
      }
      // send it to the audio device!
      track.write( bufferS, 0, bufferS.length );
    }
  }

  public void addAudioGenerator(AudioGenerator ag) {
    audioGens.add(ag);
  }
}

/**
 * Implement this interface so the AudioThread can request samples from you
 */
public interface AudioGenerator {
  /** AudioThread calls this when it wants a sample */
  public short getSample();
}


public class FXChain {
  private float currentAmp;
  private float dAmp;
  private float targetAmp;
  private boolean goingUp;
  private Filter filter;

  private float[] dLine;   

  private float sampleRate;

  public FXChain(float sampleRate_) {
    sampleRate = sampleRate_;
    currentAmp = 1;
    dAmp = 0;
    // filter = new MickFilter(sampleRate);
    filter = new RLPF(sampleRate);

    filter.setFilter(sampleRate, 0.5f);
  }

  public void ramp(float val, float timeMs) {
    // calc the dAmp;
    // - change per ms
    targetAmp = val;
    dAmp = (targetAmp - currentAmp) / (timeMs / 1000 * sampleRate);
    if (targetAmp > currentAmp) {
      goingUp = true;
    }
    else {
      goingUp = false;
    }
  }


  public void setDelayTime(float delayMs) {
  }

  public void setDelayFeedback(float fb) {
  }

  public void volume(float volume) {
  }


  public short getSample(short input) {
    float in;
    in = (float) input / 32768;// -1 to 1

    in =  filter.applyFilter(in);
    if (goingUp && currentAmp < targetAmp) {
      currentAmp += dAmp;
    }
    else if (!goingUp && currentAmp > targetAmp) {
      currentAmp += dAmp;
    }  

    if (currentAmp > 1) {
      currentAmp = 1;
    }
    if (currentAmp < 0) {
      currentAmp = 0;
    }  
    in *= currentAmp;  
    return (short) (in * 32768);
  }

  public void setFilter(float f, float r) {
    filter.setFilter(f, r);
  }
}


/**
 * Represents an audio source is streamed as opposed to being completely loaded (as WavSource is)
 */
public class AudioStreamPlayer {
  /** a class from the android API*/
  private MediaPlayer mediaPlayer;
  /** a class from the android API*/
  private Visualizer viz; 
  private byte[] waveformBuffer;
  private byte[] fftBuffer;
  private byte[] powerSpectrum;

  /**
   * create a stream source from the sent url 
   */
  public AudioStreamPlayer(String url) {
    try {
      mediaPlayer = new MediaPlayer();
      //mp.setAuxEffectSendLevel(1);
      mediaPlayer.setLooping(true);

      // try to parse the URL... if that fails, we assume it
      // is a local file in the assets folder
      try {
        URL uRL = new URL(url);
        mediaPlayer.setDataSource(url);
      }
      catch (MalformedURLException eek) {
        // couldn't parse the url, assume its a local file
        AssetFileDescriptor afd = getAssets().openFd(url);
        //mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
        mediaPlayer.setDataSource(afd.getFileDescriptor());
        afd.close();
      }

      mediaPlayer.prepare();
      //mediaPlayer.start();
      //println("Created audio with id "+mediaPlayer.getAudioSessionId());
      viz = new Visualizer(mediaPlayer.getAudioSessionId());
      viz.setEnabled(true);
      waveformBuffer = new byte[viz.getCaptureSize()];
      fftBuffer = new byte[viz.getCaptureSize()/2];
      powerSpectrum = new byte[viz.getCaptureSize()/2];
    }
    catch (Exception e) {
      println("StreamSource could not be initialised. Check url... "+url+ " and that you have added the permission INTERNET, RECORD_AUDIO and MODIFY_AUDIO_SETTINGS to the manifest,");
      e.printStackTrace();
    }
  }

  public void play() {
    mediaPlayer.start();
  }

  public int getLengthMs() {
    return mediaPlayer.getDuration();
  }

  public void cue(float timeMs) {
    if (timeMs >= 0 && timeMs < getLengthMs()) {// ignore crazy values
      mediaPlayer.seekTo((int)timeMs);
    }
  }

  /**
   * Returns a recent snapshot of the power spectrum as 8 bit values
   */
  public byte[] getPowerSpectrum() {
    // calculate the spectrum
    viz.getFft(fftBuffer);
    short real, imag;
    for (int i=2;i<fftBuffer.length;i+=2) {
      real = (short) fftBuffer[i];
      imag = (short) fftBuffer[i+1];
      powerSpectrum[i/2] = (byte) ((real * real)  + (imag * imag));
    }
    return powerSpectrum;
  }

  /**
   * Returns a recent snapshot of the waveform being played 
   */
  public byte[] getWaveForm() {
    // retrieve the waveform
    viz.getWaveForm(waveformBuffer);
    return waveformBuffer;
  }
} 

/**
 * Use this class to retrieve data about the movement of the device
 */
public class Accelerometer implements SensorEventListener {
  private SensorManager sensorManager;
  private Sensor accelerometer;
  private float[] values;

  public Accelerometer() {
    sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    values = new float[3];
  }


  public float[] getValues() {
    return values;
  }

  public float getX() {
    return values[0];
  }

  public float getY() {
    return values[1];
  }

  public float getZ() {
    return values[2];
  }

  /**
   * SensorEventListener interace
   */
  public void onSensorChanged(SensorEvent event) {
    values = event.values;
    //float[] vals = event.values;
    //for (int i=0; i<vals.length;i++){
    //  println(" sensor! "+vals[i]);
    //}
  }

  /**
   * SensorEventListener interace
   */
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }
}

public interface Filter {
  public void setFilter(float f, float r);
  public float applyFilter(float in);
}

/** https://github.com/supercollider/supercollider/blob/master/server/plugins/FilterUGens.cpp */

public class RLPF implements Filter {
  float a0, b1, b2, y1, y2;
  float freq;
  float reson;
  float sampleRate;
  boolean changed;

  public RLPF(float sampleRate_) {
    this.sampleRate = sampleRate_;
    reset();
    this.setFilter(sampleRate / 4, 0.01f);
  }
  private void reset() {
    a0 = 0.f;
    b1 = 0.f;
    b2 = 0.f;
    y1 = 0.f;
    y2 = 0.f;
  }
  /** f is in the range 0-sampleRate/2 */
  public void setFilter(float f, float r) {
    // constrain 
    // limit to 0-1 
    f = constrain(f, 0, sampleRate/4);
    r = constrain(r, 0, 1);
    // invert so high r -> high resonance!
    r = 1-r;
    // remap to appropriate ranges
    f = map(f, 0, sampleRate/4, 30, sampleRate / 4);
    r = map(r, 0, 1, 0.005f, 2);

   // println("rlpf: f "+f+" r "+r);

    this.freq = f * TWO_PI / sampleRate;
    this.reson = r;
    changed = true;
  }

  public float applyFilter(float in) {
    float y0;
    if (changed) {
      float D = tan(freq * reson * 0.5f);
      float C = ((1.f-D)/(1.f+D));
      float cosf = cos(freq);
      b1 = (1.f + C) * cosf;
      b2 = -C;
      a0 = (1.f + C - b1) * .25f;
      changed = false;
    }
    y0 = a0 * in + b1 * y1 + b2 * y2;
    y2 = y1;
    y1 = y0;
    if (Float.isNaN(y0)) {
      reset();
    }
    return y0;
  }
}

/** https://github.com/micknoise/Maximilian/blob/master/maximilian.cpp */

class MickFilter implements Filter {

  private float f, res;
  private float cutoff, z, c, x, y, out;
  private float sampleRate;

  MickFilter(float sampleRate) {
    this.sampleRate = sampleRate;
  }

  public void setFilter(float f, float r) {
    f = constrain(f, 0, 1);
    res = constrain(r, 0, 1);
    f = map(f, 0, 1, 25, sampleRate / 4);
    r = map(r, 0, 1, 1, 25);
    this.f = f;
    this.res = r;    

    //println("mickF: f "+f+" r "+r);
  }
  public float applyFilter(float in) {
    return lores(in, f, res);
  }

  public float lores(float input, float cutoff1, float resonance) {
    //cutoff=cutoff1*0.5;
    //if (cutoff<10) cutoff=10;
    //if (cutoff>(sampleRate*0.5)) cutoff=(sampleRate*0.5);
    //if (resonance<1.) resonance = 1.;

    //if (resonance>2.4) resonance = 2.4;
    z=cos(TWO_PI*cutoff/sampleRate);
    c=2-2*z;
    float r=(sqrt(2.0f)*sqrt(-pow((z-1.0f), 3.0f))+resonance*(z-1))/(resonance*(z-1));
    x=x+(input-y)*c;
    y=y+x;
    x=x*r;
    out=y;
    return out;
  }
}


/*
 * This file is part of Beads. See http://www.beadsproject.net for all information.
 * CREDIT: This class uses portions of code taken from MPEG7AudioEnc. See readme/CREDITS.txt.
 */

/**
 * FFT performs a Fast Fourier Transform and forwards the complex data to any listeners. 
 * The complex data is a float of the form float[2][frameSize], with real and imaginary 
 * parts stored respectively.
 * 
 * @beads.category analysis
 */
public class FFT {

  /** The real part. */
  protected float[] fftReal;

  /** The imaginary part. */
  protected float[] fftImag;

  private float[] dataCopy = null;
  private float[][] features;
  private float[] powers;
  private int numFeatures;

  /**
   * Instantiates a new FFT.
   */
  public FFT() {
    features = new float[2][];
  }

  /* (non-Javadoc)
   * @see com.olliebown.beads.core.UGen#calculateBuffer()
   */
  public float[] process(float[] data, boolean direction) {
    if (powers == null) powers = new float[data.length/2];
    if (dataCopy==null || dataCopy.length!=data.length)
      dataCopy = new float[data.length];
    System.arraycopy(data, 0, dataCopy, 0, data.length);

    fft(dataCopy, dataCopy.length, direction);
    numFeatures = dataCopy.length;
    fftReal = calculateReal(dataCopy, dataCopy.length);
    fftImag = calculateImaginary(dataCopy, dataCopy.length);
    features[0] = fftReal;
    features[1] = fftImag;
    // now calc the powers
    return specToPowers(fftReal, fftImag, powers);
  }

  public float[] specToPowers(float[] real, float[] imag, float[] powers) {
    float re, im;
    double pow;
    for (int i=0;i<powers.length;i++) {
      //real = spectrum[i][j].re();
      //imag = spectrum[i][j].im();
      re = real[i];
      im = imag[i];
      powers[i] = (re*re + im * im);
      powers[i] = (float) Math.sqrt(powers[i]) / 10;
      // convert to dB
      pow = (double) powers[i];
      powers[i] = (float)(10 *  Math.log10(pow * pow)); // (-100 - 100)
      powers[i] = (powers[i] + 100) * 0.005f; // 0-1
    }
    return powers;
  }

  /**
   * The frequency corresponding to a specific bin 
   * 
   * @param samplingFrequency The Sampling Frequency of the AudioContext
   * @param blockSize The size of the block analysed
   * @param binNumber 
   */
  public  float binFrequency(float samplingFrequency, int blockSize, float binNumber)
  {    
    return binNumber*samplingFrequency/blockSize;
  }

  /**
   * Returns the average bin number corresponding to a particular frequency.
   * Note: This function returns a float. Take the Math.round() of the returned value to get an integral bin number. 
   * 
   * @param samplingFrequency The Sampling Frequency of the AudioContext
   * @param blockSize The size of the fft block
   * @param freq  The frequency
   */

  public  float binNumber(float samplingFrequency, int blockSize, float freq)
  {
    return blockSize*freq/samplingFrequency;
  }

  /** The nyquist frequency for this samplingFrequency 
   * 
   * @params samplingFrequency the sample
   */
  public  float nyquist(float samplingFrequency)
  {
    return samplingFrequency/2;
  }

  /*
   * All of the code below this line is taken from Holger Crysandt's MPEG7AudioEnc project.
   * See http://mpeg7audioenc.sourceforge.net/copyright.html for license and copyright.
   */

  /**
   * Gets the real part from the complex spectrum.
   * 
   * @param spectrum
   *            complex spectrum.
   * @param length 
   *       length of data to use.
   * 
   * @return real part of given length of complex spectrum.
   */
  protected  float[] calculateReal(float[] spectrum, int length) {
    float[] real = new float[length];
    real[0] = spectrum[0];
    real[real.length/2] = spectrum[1];
    for (int i=1, j=real.length-1; i<j; ++i, --j)
      real[j] = real[i] = spectrum[2*i];
    return real;
  }

  /**
   * Gets the imaginary part from the complex spectrum.
   * 
   * @param spectrum
   *            complex spectrum.
   * @param length 
   *       length of data to use.
   * 
   * @return imaginary part of given length of complex spectrum.
   */
  protected  float[] calculateImaginary(float[] spectrum, int length) {
    float[] imag = new float[length];
    for (int i=1, j=imag.length-1; i<j; ++i, --j)
      imag[i] = -(imag[j] = spectrum[2*i+1]);
    return imag;
  }

  /**
   * Perform FFT on data with given length, regular or inverse.
   * 
   * @param data the data
   * @param n the length
   * @param isign true for regular, false for inverse.
   */
  protected  void fft(float[] data, int n, boolean isign) {
    float c1 = 0.5f; 
    float c2, h1r, h1i, h2r, h2i;
    double wr, wi, wpr, wpi, wtemp;
    double theta = 3.141592653589793f/(n>>1);
    if (isign) {
      c2 = -.5f;
      four1(data, n>>1, true);
    } 
    else {
      c2 = .5f;
      theta = -theta;
    }
    wtemp = Math.sin(.5f*theta);
    wpr = -2.f*wtemp*wtemp;
    wpi = Math.sin(theta);
    wr = 1.f + wpr;
    wi = wpi;
    int np3 = n + 3;
    for (int i=2,imax = n >> 2, i1, i2, i3, i4; i <= imax; ++i) {
      /** @TODO this can be optimized */
      i4 = 1 + (i3 = np3 - (i2 = 1 + (i1 = i + i - 1)));
      --i4; 
      --i2; 
      --i3; 
      --i1; 
      h1i =  c1*(data[i2] - data[i4]);
      h2r = -c2*(data[i2] + data[i4]);
      h1r =  c1*(data[i1] + data[i3]);
      h2i =  c2*(data[i1] - data[i3]);
      data[i1] = (float) ( h1r + wr*h2r - wi*h2i);
      data[i2] = (float) ( h1i + wr*h2i + wi*h2r);
      data[i3] = (float) ( h1r - wr*h2r + wi*h2i);
      data[i4] = (float) (-h1i + wr*h2i + wi*h2r);
      wr = (wtemp=wr)*wpr - wi*wpi + wr;
      wi = wi*wpr + wtemp*wpi + wi;
    }
    if (isign) {
      float tmp = data[0]; 
      data[0] += data[1];
      data[1] = tmp - data[1];
    } 
    else {
      float tmp = data[0];
      data[0] = c1 * (tmp + data[1]);
      data[1] = c1 * (tmp - data[1]);
      four1(data, n>>1, false);
    }
  }

  /**
   * four1 algorithm.
   * 
   * @param data
   *            the data.
   * @param nn
   *            the nn.
   * @param isign
   *            regular or inverse.
   */
  private  void four1(float data[], int nn, boolean isign) {
    int n, mmax, istep;
    double wtemp, wr, wpr, wpi, wi, theta;
    float tempr, tempi;

    n = nn << 1;        
    for (int i = 1, j = 1; i < n; i += 2) {
      if (j > i) {
        // SWAP(data[j], data[i]);
        float swap = data[j-1];
        data[j-1] = data[i-1];
        data[i-1] = swap;
        // SWAP(data[j+1], data[i+1]);
        swap = data[j];
        data[j] = data[i]; 
        data[i] = swap;
      }      
      int m = n >> 1;
      while (m >= 2 && j > m) {
        j -= m;
        m >>= 1;
      }
      j += m;
    }
    mmax = 2;
    while (n > mmax) {
      istep = mmax << 1;
      theta = 6.28318530717959f / mmax;
      if (!isign)
        theta = -theta;
      wtemp = Math.sin(0.5f * theta);
      wpr = -2.0f * wtemp * wtemp;
      wpi = Math.sin(theta);
      wr = 1.0f;
      wi = 0.0f;
      for (int m = 1; m < mmax; m += 2) {
        for (int i = m; i <= n; i += istep) {
          int j = i + mmax;
          tempr = (float) (wr * data[j-1] - wi * data[j]);  
          tempi = (float) (wr * data[j]   + wi * data[j-1]);  
          data[j-1] = data[i-1] - tempr;
          data[j]   = data[i] - tempi;
          data[i-1] += tempr;
          data[i]   += tempi;
        }
        wr = (wtemp = wr) * wpr - wi * wpi + wr;
        wi = wi * wpr + wtemp * wpi + wi;
      }
      mmax = istep;
    }
  }
}

public boolean surfaceKeyDown(int code, KeyEvent event) {
  if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
    if (pertraukashow){  
      return false;       //Jeigu pertrauka
    }
    else if (zaidziama==true&&baigta==false&&pertraukashow==false) {
      zaidziama = false;                 
      paruosia();
      clock.stop();
      return false; 
    };
    if (baigta) {
      paruosia();
      zaidziama = false;                 // GRIZTI
      baigta = false;
      rekordaishow = false;
      return false;
    }
    if (rekordaishow) {
      paruosia();
      zaidziama = false;              // GRIZTI KAI REKORDAI
      baigta = false;
      rekordaishow = false;                  
      //dabar=false;
      atnaujinavarda();
      widgetContainer.hide(); 
      list.hide();
      ivesta = false;
      return false;
    }
  }
  return super.surfaceKeyDown(code, event);
}

public boolean surfaceKeyUp(int code, KeyEvent event) {
  return super.surfaceKeyDown(code, event);
}

float sp=0.00001f;
// 1 etapas bendri klausimai
public void etapas1() {
  s = second();
  if (pirmas) { 
    if (etapoNr<=2)ss = s+45;//45
    else ss = s +30;  //30
    ss = ss % 60;
    taskai = 0;
  } 
  while (ss<s) ss+=60;
  laikas  = ss-s;
  ss = ss % 60;
  float ll = 50 - laikas;
  sp = map(ll, 0, 47.0f, 000.1f, 0.5f);
  clock.speed(sp); 
  clock.play();  
  imageMode(CORNER);
  image(fonas1, 0, 0, width, height); //Uzkrauna fona
  textAlign(CENTER);

  fill(255);
  stroke(255);
  rect(pr, height/2-height/2.7f, width-pr-pr, height/2.7f-dezesaukst, 3, 3, 3, 3);  // Uzkrauna langa kur klausimas
  tint(255, 150);
  image(graziau, pr, height/2-height/2.7f, width-pr-pr, height/2.7f-dezesaukst);
  noTint();

  textFont(klFont);
  fill(0, 31, 20); // Rodo klausima
  textSize(raidkl);
  textAlign(CENTER);
  text(bklausimai[dbr], pr, height/2-height/2.7f, width-pr-pr, height/2.7f-dezesaukst);

  if (keisti) {        //Sugeneruoja variantus
    int yra1=-1, yra2=-1, yra3=-1, yra4=-1;
    yra1 = (int) random(0, 3.9f);
    var[yra1] = variantai[dbr*4+1];
    yra2 = yra1;
    while (yra2==yra1)
      yra2 = (int) random(0, 3.9f);
    var[yra2] = variantai[dbr*4+2];
    yra3 = yra1;
    while (yra3==yra1||yra3==yra2)
      yra3 = (int) random(0, 3.9f);
    var[yra3] = variantai[dbr*4+3];
    yra4=yra1;
    while (yra4==yra1||yra4==yra2||yra4==yra3)
      yra4 = (int) random(0, 3.9f);
    var[yra4] = variantai[dbr*4+4];
  };
  if (keisti) {                  // Atnaujina mygtukus
    button1 = new Button(var[0], (int)pr, (int)height/2, width-pr-pr, (int)dezesaukst);
    button2 = new Button(var[1], (int)pr, (int)height/2+(int) (dezesaukst/ 1.5f*2), (int)width-pr-pr, (int)dezesaukst);
    button3 = new Button(var[2], pr, (int)height/2+(int)( dezesaukst/ 1.5f*4), width-pr-pr, (int)dezesaukst);
    button4 = new Button(var[3], pr, (int)height/2+(int)(dezesaukst/ 1.5f*6), width-pr-pr, (int)dezesaukst);
    keisti = false;
  }
  textFont(varFont);
  textSize(raidvar);
  button1.display(bspalva1); // Rodo mygtukus
  button2.display(bspalva2);
  button3.display(bspalva3);
  button4.display(bspalva4);
  tint(255, 70);
  image(graziau, (int)pr, (int)height/2, width-pr-pr, (int)dezesaukst);       // Pagrazina mygtukus
  image(graziau, (int)pr, (int)height/2+(int) (dezesaukst/ 1.5f*2), (int)width-pr-pr, (int)dezesaukst);
  image(graziau, pr, (int)height/2+(int)( dezesaukst/ 1.5f*4), width-pr-pr, (int)dezesaukst);
  image(graziau, pr, (int)height/2+(int)(dezesaukst/ 1.5f*6), width-pr-pr, (int)dezesaukst);
  noTint();
  pirmas = false;
  textFont(createFont("Serif",width/8,true));
}

public void etapas2() {
  s = second();
  if (pirmas) { 
    if (etapoNr<=2)ss = s+45;//45
    else ss = s +30;  //30
    ss = ss % 60;
    taskai = 0;
  } 
  while (ss<s) ss+=60;
  laikas  = ss-s;
  ss = ss % 60;
  float ll = 45 - laikas;
  sp = map(ll, 0, 47.0f, 000.1f, 0.5f);
  clock.speed(sp);
  clock.play();  //
  imageMode(CORNER);
  image(fonas2, 0, 0, width, height); //Uzkrauna fona
  textAlign(CENTER);
  imageMode(CENTER);
  float aukst = height/6*2 + etapo2Pav[dbr].height/2, dyd = etapo2Pav[dbr].height;
  float buv=1;
  // if (aukst>(height/2-height/2.4)){
  buv = dyd;
  dyd = ((int)height/2-4.0f)-(height/2-height/2.4f+4.0f);
  buv = dyd / buv;
  // }
  image(etapo2Pav[dbr], width/2, height/6*2, etapo2Pav[dbr].width*buv, dyd);
  imageMode(CORNER);



  fill(0); // Rodo klausima
  fill(51, 31, 0);
  textFont(klFont);
  textSize(raidkl);
  textAlign(CENTER);
  text("Kas pavaizduota paveiksle?", pr/2, height/2-height/2.4f, width-pr, height/2.7f-dezesaukst);

  if (keisti) {        //Sugeneruoja variantus
    int yra1=-1, yra2=-1, yra3=-1, yra4=-1;
    yra1 = (int) random(0, 3.9f);
    var[yra1] = pavvar[dbr*4+1];
    yra2 = yra1;
    while (yra2==yra1)
      yra2 = (int) random(0, 3.9f);
    var[yra2] = pavvar[dbr*4+2];
    yra3 = yra1;
    while (yra3==yra1||yra3==yra2)
      yra3 = (int) random(0, 3.9f);
    var[yra3] = pavvar[dbr*4+3];
    yra4=yra1;
    while (yra4==yra1||yra4==yra2||yra4==yra3)
      yra4 = (int) random(0, 3.9f);
    var[yra4] = pavvar[dbr*4+4];
  };
  if (keisti) {                  // Atnaujina mygtukus
    button1 = new Button(var[0], (int)pr, (int)height/2, width-pr-pr, (int)dezesaukst);
    button2 = new Button(var[1], (int)pr, (int)height/2+(int) (dezesaukst/ 1.5f*2), (int)width-pr-pr, (int)dezesaukst);
    button3 = new Button(var[2], pr, (int)height/2+(int)( dezesaukst/ 1.5f*4), width-pr-pr, (int)dezesaukst);
    button4 = new Button(var[3], pr, (int)height/2+(int)(dezesaukst/ 1.5f*6), width-pr-pr, (int)dezesaukst);
    keisti = false;
  }
  textFont(varFont);
  textSize(raidvar);
  button1.display(bspalva1); // Rodo mygtukus
  button2.display(bspalva2);
  button3.display(bspalva3);
  button4.display(bspalva4);
  tint(255, 70);
  image(graziau, (int)pr, (int)height/2, width-pr-pr, (int)dezesaukst);       // Pagrazina mygtukus
  image(graziau, (int)pr, (int)height/2+(int) (dezesaukst/ 1.5f*2), (int)width-pr-pr, (int)dezesaukst);
  image(graziau, pr, (int)height/2+(int)( dezesaukst/ 1.5f*4), width-pr-pr, (int)dezesaukst);
  image(graziau, pr, (int)height/2+(int)(dezesaukst/ 1.5f*6), width-pr-pr, (int)dezesaukst);
  noTint();

  pirmas = false;
}

public void etapas3() {
  s = second();
  if (pirmas) { 
    if (etapoNr<=2)ss = s+45;//45
    else ss = s +30;  //30
    ss = ss % 60;
    taskai = 0;
  } 
  while (ss<s) ss+=60;
  laikas  = ss-s;
  ss = ss % 60;
  float ll = 40 - laikas;
  sp = map(ll, 0, 42.0f, 000.1f, 0.5f);
  clock.speed(sp);
  clock.play();  //
  imageMode(CORNER);
  image(fonas3, 0, 0, width, height); //Uzkrauna fona
  textAlign(CENTER);

  fill(255);
  stroke(255);
  rect(pr, height/2-height/2.7f, width-pr-pr, height/2.7f-dezesaukst, 3, 3, 3, 3);  // Uzkrauna langa kur klausimas
  tint(255, 150);
  image(graziau, pr, height/2-height/2.7f, width-pr-pr, height/2.7f-dezesaukst);
  noTint();

  fill(0, 31, 20); // Rodo klausima
  textFont(klFont);
  textSize(raidkl);
  textAlign(CENTER);
  text(dklausimai[dbr], pr, height/2-height/2.7f, width-pr-pr, height/2.7f-dezesaukst);

  if (keisti) {        //Sugeneruoja variantus
    int yra1=-1, yra2=-1, yra3=-1, yra4=-1;
    yra1 = (int) random(0, 3.9f);
    var[yra1] = datosvar[dbr*4+1];
    yra2 = yra1;
    while (yra2==yra1)
      yra2 = (int) random(0, 3.9f);
    var[yra2] = datosvar[dbr*4+2];
    yra3 = yra1;
    while (yra3==yra1||yra3==yra2)
      yra3 = (int) random(0, 3.9f);
    var[yra3] = datosvar[dbr*4+3];
    yra4=yra1;
    while (yra4==yra1||yra4==yra2||yra4==yra3)
      yra4 = (int) random(0, 3.9f);
    var[yra4] = datosvar[dbr*4+4];
  };
  if (keisti) {                  // Atnaujina mygtukus
    button1 = new Button(var[0], (int)pr, (int)height/2, width-pr-pr, (int)dezesaukst);
    button2 = new Button(var[1], (int)pr, (int)height/2+(int) (dezesaukst/ 1.5f*2), (int)width-pr-pr, (int)dezesaukst);
    button3 = new Button(var[2], pr, (int)height/2+(int)( dezesaukst/ 1.5f*4), width-pr-pr, (int)dezesaukst);
    button4 = new Button(var[3], pr, (int)height/2+(int)(dezesaukst/ 1.5f*6), width-pr-pr, (int)dezesaukst);
    keisti = false;
  }
  textFont(varFont);
  textSize(raidvar);
  button1.display(bspalva1); // Rodo mygtukus
  button2.display(bspalva2);
  button3.display(bspalva3);
  button4.display(bspalva4);
  tint(255, 70);
  image(graziau, (int)pr, (int)height/2, width-pr-pr, (int)dezesaukst);       // Pagrazina mygtukus
  image(graziau, (int)pr, (int)height/2+(int) (dezesaukst/ 1.5f*2), (int)width-pr-pr, (int)dezesaukst);
  image(graziau, pr, (int)height/2+(int)( dezesaukst/ 1.5f*4), width-pr-pr, (int)dezesaukst);
  image(graziau, pr, (int)height/2+(int)(dezesaukst/ 1.5f*6), width-pr-pr, (int)dezesaukst);
  noTint();

  pirmas = false;
}


public PImage [] loadImages(String stub, String extension, int numImages)
{
  PImage [] images = new PImage[0];
  for(int i =1; i <= numImages; i++)
  {
    PImage img = loadImage(stub+i+extension);
    if(img != null)
    {
      images = (PImage [])append(images,img);
    }
    else
    {
      break;
    }
  }
  return images;
}

public void meniu()
{
  imageMode(CORNER);
  image(fonas,0,0,width,height);
  int spa = color(0,106,68);
  textFont(createFont("Serif",width/6.5f,true));
  textSize(raidm);
  zaisti.display(spa);
  rekordai.display(spa);
  tint(255,150);
  image(graziau,pr,(int)( height/2.2f),width-pr-pr,height/7);
  image(graziau,pr,(int)( height/2.2f+height/7+pr),width-pr-pr,height/7);
  noTint();
  zodziai();
  textFont(createFont("Serif-Bold",width/8,true));
}
public void zodziai(){
  int geltona = color(253,185,19);
  fill(geltona);
  PFont f;
  f = createFont("Serif-Bold",width/6.5f,true);
  textFont(f);
  textAlign(RECT);
  text("Lietuvos",pr,height/13,width-pr,height/8.3f);
  fill(0,106,68);
  text("istorijos",width/2-pr-pr/1.5f,height/6,width-pr/2,height/4);
  fill(193,39,45);
  text("viktorina",pr,height/4,width-pr,height/3); 
}




boolean ivesta = false;

public void rekordai() {
  background(253, 185, 19);
  textSize(raidm);
  rodytirekordus();
}
public void onClickWidget(APWidget widget) {

  if (widget == issaugoti) { 
    String[] vard = new String[6];
    vard[0] = " ";
    vard[1] = textField.getText();
    saveStrings("\\sdcard\\vardas.txt", vard);
    paruosia();
    zaidziama = false;              // GRIZTI KAI REKORDAI
    baigta = false;
    rekordaishow = false;                  
    //dabar=false;
    atnaujinavarda();
    widgetContainer.hide(); 
    list.hide();
    ivesta = false;
  }
}

public void rodytirekordus() {
  list.show();
  String[] asdf = new String[4];
  asdf[0] = "1";
  try {
    asdf= loadStrings("\\sdcard\\yra.txt");
  }
  catch(NullPointerException e) {
  };
  fill(255);
  textAlign(CENTER);
  text("Rezultatai", pr, 0+width/18/15+6, width-pr-pr, pr);
  int sk = 0;
  float y;
  if (asdf!=null&&ivesta==false) {
    table = loadTable("\\sdcard\\rezultatai.csv", "header");
    rvardas = new String[table.getRowCount()+1];
    rtaskai = new int[table.getRowCount()+1];                       // NUSKAITO DUOMENIS IS FAILO
    sk = 0;
    for (TableRow row : table.rows()) {
      rvardas[sk] = row.getString("vardas");
      rtaskai[sk] = row.getInt("taskai");
      sk++;
    }
    y = pr;
    fill(0);
    textAlign(CENTER);
    textSize(width/18);
    float dyd = width/ 15;
    text("Vardas", pr, pr+dyd/4, width/2-pr, pr);
    text("Ta\u0161kai", width/2, pr+dyd/4, width/2-pr, pr);
    fill(0, 40);
    stroke(0);
    textSize(dyd);
    if (!ivesta) {
      list.clear();  
      for (sk=0;sk<=table.getRowCount()-1;sk++) {                    //IVEDA REZULTATUS I LISTA
        int s = sk +1;
        String str = " "+s+" "+rvardas[sk]+"   "+rtaskai[sk];
        ListBoxItem lbi = list.addItem(str, sk);
        lbi.setColorBackground(color(0, 106, 68));
        //rezlist.add(str);
      }
      //println(rezlist);
      //selectionlist = new KetaiList(this,"Rezultatai", rezlist);
      ivesta = true;
    }
  }
}


public void uzkrauna() {
  //fontas = createFont("Arial", width/12.5, true);
  Jaukstis = height / 13;
  Jilgis = width / 1.3f;
  pr = width/10;
  dezesaukst = height/12;

  raidm = width /13;
  raidkl = width / 15;
  raidvar = width /20;
  Jraid = width / 14;
  Jraidt = width / 12;
  klFont = createFont("Serif",width/8,true);
  varFont = createFont("Arial",width/8,true); 
  
  button1 = new Button("sd", (int)pr, (int)height/2, width-pr-pr, (int)dezesaukst);
  button2 = new Button("sd", (int)pr, (int)height/2+(int) (dezesaukst/ 1.5f*2), (int)width-pr-pr, (int)dezesaukst);
  button3 = new Button("sd", pr, (int)height/2+(int)( dezesaukst/ 1.5f*4), width-pr-pr, (int)dezesaukst);                 //Sukuriami vis mygtukai
  button4 = new Button("sd", pr, (int)height/2+(int)(dezesaukst/ 1.5f*6), width-pr-pr, (int)dezesaukst);
  zaistidar = new Button("\u017daisti dar kart\u0105", pr, (int)height/2, width-pr-pr, height/7);
  grizti = new Button("Gr\u012f\u017eti", pr, (int)( height/2+pr+height/7+pr/2), width-pr-pr, height/7);
  zaisti = new Button("\u017daisti", pr, (int)( height/2.2f), width-pr-pr, height/7);
  rekordai = new Button("Rezultatai", pr, (int)( height/2.2f+height/7+pr), width-pr-pr, height/7);
  testi = new Button("T\u0119sti", pr, (int)( pr), width-pr-pr, pr);
  baigti = new Button("X", 0, 0, pr, (int) Jaukstis);

  var = new String[6];
  variantai = loadStrings("bendri_klausimai_var.txt");
  bklausimaiats = loadStrings("bendri_klausimai_ats.txt");
  bklausimai = loadStrings("bendri klausimai.txt");
  klausimu = bklausimai.length;
  pavvar = loadStrings("pavvar.txt");
  pavats = loadStrings("pavats.txt");
  pavSk = pavats.length - 2;
  datosvar = loadStrings("datosvar.txt");
  datosats = loadStrings("datosats.txt");
  dklausimai = loadStrings("datosKlausimai.txt");
  datSk = dklausimai.length;
  etapo2Pav = loadImages("e", ".jpg", pavSk);

  buvo = new boolean[999];
  pavbuvo = new boolean[999];
  datosbuvo = new boolean[999];
  dbr = (int) random(0, klausimu-1);
  buvo[dbr]=true;

  bspalva1 = color(127, 255, 0);
  bspalva2 = bspalva1;  //Sukuriamos mygtuku spalvos
  bspalva3 = bspalva1;
  bspalva4 = bspalva1;
  fonas = loadImage("fonas0.jpg"); // Uzkraunami fonai
  graziau = loadImage("graz.png");
  fonas1 = loadImage("fonas1.png");
  fonas2 = loadImage("2etapoback.jpg");
  fonas3 = loadImage("fonas3.jpg");

  paruosia();

  widgetContainer = new APWidgetContainer(this); //create new container for widgets
  issaugoti = new APButton(width/3, (int)( height/1.32f+pr/3+height/9), width/3, (int)( height/2+pr+height/7+pr/2)-(int)( height/2+height/7-pr*1.8f+height/9+2)-height/25, "I\u0161saugoti"); //create new button from x- and y-pos. and label. size determined by text content
  textField = new APEditText(pr, (int)( height/1.32f), width-pr-pr, height/9); //create a textfield from x- and y-pos., width and height
  widgetContainer.addWidget(textField); //place textField in container
  textField.setCloseImeOnDone(true);
  textField.setInputType(InputType.TYPE_CLASS_TEXT);
  textField.setImeOptions(EditorInfo.IME_ACTION_DONE);
  widgetContainer.addWidget(issaugoti); //place button in container
  widgetContainer.hide();

  vardas = new String[6];
  vardas[1] = "Vardenis";
  atnaujinavarda();
  maxim = new Maxim(this); //create new APMediaPlayer
  clock = maxim.loadFile("clockk.wav"); //set the file (files are in data folder)
  clock.setLooping(false); //restart playback end reached

  menusound = new APMediaPlayer(this); //create new APMediaPlayer
  menusound.setMediaFile("menusound.wav"); //set the file (files are in data folder)
  menusound.setLooping(false); //restart playback end reached
  menusound.setVolume(1.0f, 1.0f); //Set left and right volumes. Range is from 0.0 to 1.0

    wrong = new APMediaPlayer(this); //create new APMediaPlayer
  wrong.setMediaFile("ne.wav"); //set the file (files are in data folder)
  wrong.setLooping(false); //restart playback end reached
  wrong.setVolume(1.0f, 1.0f); //Set left and right volumes. Range is from 0.0 to 1.0

    ControlP5.printPublicMethodsFor(ListBox.class);

  cp5 = new ControlP5(this);
   cp5.setControlFont(new ControlFont(createFont("Arial", 40), width/14));
  int ilg = (int)(height/2+height/5); 
  list = cp5.addListBox("myList")
    .setPosition(pr, pr)
      .setSize(width-pr-pr, ilg)
        .setItemHeight(height/13)
          .setBarHeight(height/13)
            .setColorBackground(color(0, 255, 68))
              .setColorActive(color(30, 106, 68))
                .setColorForeground(color(0, 106, 68))
                  .setScrollbarWidth(PApplet.parseInt(width/14))
                    ;

 // list.captionLabel().toUpperCase(true);
 // list.captionLabel().set("Rezultatai");
 // list.captionLabel().setColor(color(0, 0, 255));
 // list.captionLabel().style().marginTop = 6;
  //list.valueLabel().style().marginTop = 6;

  list.hideBar();
  list.hide();

}



public void zaidimas() {
  if (etapoNr==1) etapas1();
  else if (etapoNr==2) etapas2();
    else if (etapoNr==3) etapas3();
}
public void pertrauka() {
  clock.stop();
  background(253, 185, 19);
  fill(255);
  textAlign(CENTER);
  textSize(width/9);
  if (etapoNr==1)
    text("Bendr\u0173 klausim\u0173 etape surinkai: ", pr, height/4, width-pr, height/3);
  else if (etapoNr==2)
    text("Paveiksl\u0173 etape surinkai: ", pr, height/4, width-pr, height/3);
      else text("Dat\u0173 etape surinkai: ", pr, height/4, width-pr, height/3);
  String tsk = str(taskai);
  textSize(width/4);
  text(tsk, pr, height/2+pr/2, width-pr-pr, height/4);
  textSize(width/9);
  text("ta\u0161k\u0173.", pr, height/2+pr/2+height/4+pr/3, width-pr-pr, height/3);
  int j = color(0, 106, 68);
  textSize(raidm);
  testi.display(j);
}


}
