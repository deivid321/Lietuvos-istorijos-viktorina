PImage img;
void etapas2() {
  s = second();
  if (pirmas) { 
    if (etapoNr<=2)ss = s+45;//45
    ss = ss % 60;
    taskai = 0;
  } 
  while (ss<s) ss+=60;
  laikas  = ss-s;
  ss = ss % 60;
  float ll = 45 - laikas;
  sp = map(ll, 0, 47.0, 000.1, 0.5);
  clock.speed(sp);
  clock.play();  
  imageMode(CORNER);
  image(fonas2, 0, 0, width, height); //Uzkrauna fona
  textAlign(CENTER);
  imageMode(CENTER);
  if (keisti) { 
    try {
      println(dbr);
      String a = "e"+String.valueOf(dbr+1)+".jpg";
      println(a);
      img = loadImage(a);
    } 
    catch(NullPointerException e) {

      println("SHITTT");
    }
  }

  float aukst = height/6*2 + img.height/2, dyd = img.height;
  float buv=1;
  // if (aukst>(height/2-height/2.4)){
  buv = dyd;
  dyd = ((int)height/2-4.0)-(height/2-height/2.4+4.0);
  buv = dyd / buv;
  // }
  try {
    image(img, width/2, height/6*2, img.width*buv, dyd);
  }
  catch(NullPointerException e) {

    println("SHITTT");
  }

  imageMode(CORNER);

  fill(0); // Rodo klausima
  fill(51, 31, 0);
  if (sriftas) textFont(klFont);
  textSize(raidkl);
  textAlign(CENTER);
  text("Kas pavaizduota paveiksle?", pr/2, height/2-height/2.4, width-pr, height/2.7-dezesaukst);

  if (keisti) {        //Sugeneruoja variantus
    int yra1=-1, yra2=-1, yra3=-1, yra4=-1;
    yra1 = (int) random(0, 3.9);
    var[yra1] = variantai[dbr*4+1];
    yra2 = yra1;
    while (yra2==yra1)
      yra2 = (int) random(0, 3.9);
    var[yra2] = variantai[dbr*4+2];
    yra3 = yra1;
    while (yra3==yra1||yra3==yra2)
      yra3 = (int) random(0, 3.9);
    var[yra3] = variantai[dbr*4+3];
    yra4=yra1;
    while (yra4==yra1||yra4==yra2||yra4==yra3)
      yra4 = (int) random(0, 3.9);
    var[yra4] = variantai[dbr*4+4];
  };
  if (keisti) {                  // Atnaujina mygtukus
    button1 = new Button(var[0], (int)pr, (int)height/2, width-pr-pr, (int)dezesaukst);
    button2 = new Button(var[1], (int)pr, (int)height/2+(int) (dezesaukst/ 1.5*2), (int)width-pr-pr, (int)dezesaukst);
    button3 = new Button(var[2], pr, (int)height/2+(int)( dezesaukst/ 1.5*4), width-pr-pr, (int)dezesaukst);
    button4 = new Button(var[3], pr, (int)height/2+(int)(dezesaukst/ 1.5*6), width-pr-pr, (int)dezesaukst);
    siz1 = (width-pr-pr)/var[0].length()*2;
    if (siz1>width/13) siz1 = width/13;
    siz2 = (width-pr-pr)/var[1].length()*2;
    if (siz2>width/13) siz2 = width/13;
    siz3 = (width-pr-pr)/var[2].length()*2;
    if (siz3>width/13) siz3 = width/13;
    siz4 = (width-pr-pr)/var[3].length()*2;
    if (siz4>width/13) siz4 = width/13;
    sk2++;
    keisti = false;
  }
  if (sriftas) textFont(varFont);
  //textSize(raidvar);

  textSize(siz1);
  button1.display(bspalva1); // Rodo mygtukus
  textSize(siz2);
  button2.display(bspalva2);
  textSize(siz3);
  button3.display(bspalva3);
  textSize(siz4);
  button4.display(bspalva4);
  tint(255, 70);
  image(graziau, (int)pr, (int)height/2, width-pr-pr, (int)dezesaukst);       // Pagrazina mygtukus
  image(graziau, (int)pr, (int)height/2+(int) (dezesaukst/ 1.5*2), (int)width-pr-pr, (int)dezesaukst);
  image(graziau, pr, (int)height/2+(int)( dezesaukst/ 1.5*4), width-pr-pr, (int)dezesaukst);
  image(graziau, pr, (int)height/2+(int)(dezesaukst/ 1.5*6), width-pr-pr, (int)dezesaukst);
  noTint();

  pirmas = false;
}

