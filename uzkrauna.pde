boolean sriftas = true;
void uzkrauna() {
  Jaukstis = height / 13;
  Jilgis = width / 1.3;
  pr = width/10;
  dezesaukst = height/12;

  raidm = width /13;
  raidkl = width / 15;
  raidvar = width /20;
  Jraid = width / 16;
  Jraidt = width / 15;
  try {
    klFont = createFont("Serif", width/8, true);
    varFont = createFont("Arial", width/8, true);
    f = createFont("Serif-Bold", width/6.5, true);
  } 
  catch(NullPointerException e) {

    sriftas = false;
  }

  button1 = new Button("sd", (int)pr, (int)height/2, width-pr-pr, (int)dezesaukst);
  button2 = new Button("sd", (int)pr, (int)height/2+(int) (dezesaukst/ 1.5*2), (int)width-pr-pr, (int)dezesaukst);
  button3 = new Button("sd", pr, (int)height/2+(int)( dezesaukst/ 1.5*4), width-pr-pr, (int)dezesaukst);                 //Sukuriami vis mygtukai
  button4 = new Button("sd", pr, (int)height/2+(int)(dezesaukst/ 1.5*6), width-pr-pr, (int)dezesaukst);
  zaistidar = new Button("Žaisti dar kartą", pr, (int)height/2, width-pr-pr, height/7);
  grizti = new Button("Grįžti", pr, (int)( height/2+pr+height/7+pr/2), width-pr-pr, height/7);
  zaisti = new Button("Žaisti", pr, (int)( height/2.2), width-pr-pr, height/7);
  rekordai = new Button("Rezultatai", pr, (int)( height/2.2+height/7+pr), width-pr-pr, height/7);
  testi = new Button("Tęsti", pr, (int)( pr), width-pr-pr, pr);
  baigti = new Button("X", 0, 0, pr, (int) Jaukstis);
  taisykles = new Button("Taisyklės", 2*pr, height-pr, width-4*pr, pr); 

  var = new String[6];
  variantai = loadStrings("bendri_klausimai_var.txt");
  bklausimaiats = loadStrings("bendri_klausimai_ats.txt");

  bklausimai = loadStrings("bendri klausimai.txt");
  klausimu = bklausimai.length;

  buvo = new boolean[500];
  pavbuvo = new boolean[100];
  datosbuvo = new boolean[500];

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
  issaugoti = new APButton(width/3, (int)( height/1.32+pr/3+height/9), width/3, (int)( height/2+pr+height/7+pr/2)-(int)( height/2+height/7-pr*1.8+height/9+2)-height/25, "Išsaugoti"); //create new button from x- and y-pos. and label. size determined by text content
  textField = new APEditText(pr, (int)( height/1.32), width-pr-pr, height/9); //create a textfield from x- and y-pos., width and height
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
  menusound.setVolume(1.0, 1.0); //Set left and right volumes. Range is from 0.0 to 1.0

    wrong = new APMediaPlayer(this); //create new APMediaPlayer
  wrong.setMediaFile("ne.wav"); //set the file (files are in data folder)
  wrong.setLooping(false); //restart playback end reached
  wrong.setVolume(1.0, 1.0); //Set left and right volumes. Range is from 0.0 to 1.0   

    try {
    cp5 = new ControlP5(this);
    cp5.setControlFont(new ControlFont(createFont("Arial", 40), width/14));
    int ilg = (int)(height/2+height/5); 
    list = cp5.addListBox("myList");
    list.setPosition(pr, pr);
    list.setSize(width-pr-pr, ilg);
    list.setItemHeight(height/13);
    list.setBarHeight(height/13);
    list.setColorBackground(color(0, 255, 68));
    list.setColorActive(color(30, 106, 68));
    list.setColorForeground(color(0, 106, 68));
    list.setScrollbarWidth(int(width/14));
  }
  catch(NullPointerException e) {
  }

  try {
    list.hideBar();
    list.hide();
  }
  catch(NullPointerException e) {
  };
  println("CIA GERAI");
}

