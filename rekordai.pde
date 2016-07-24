boolean ivesta = false;

void rekordai() {
  background(253, 185, 19);
  textSize(raidm);
  rodytirekordus();
}
void onClickWidget(APWidget widget) {

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

void rodytirekordus() {
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
  if (ivesta==false) {
    if (asdf!=null) {
      table = loadTable("\\sdcard\\rezultatai.csv", "header");
      rvardas = new String[table.getRowCount()+1];
      rtaskai = new int[table.getRowCount()+1];                       // NUSKAITO DUOMENIS IS FAILO
      sk = 0;
      for (TableRow row : table.rows()) {
        rvardas[sk] = row.getString("vardas");
        rtaskai[sk] = row.getInt("taskai");
        sk++;
      }
    }
    else {
      rvardas = new String[5];
      rvardas[0] = "Istorikas";
      rtaskai = new int[5];
      rtaskai[0] = 71;
    };
    y = pr;
    fill(0);
    textAlign(CENTER);
    textSize(width/18);
    float dyd = width/ 15;
    text("Vardas", pr, pr+dyd/4, width/2-pr, pr);
    text("Taškai", width/2, pr+dyd/4, width/2-pr, pr);
    fill(0, 40);
    stroke(0);
    textSize(dyd);
    if (!ivesta) {

      list.clear();
      if (asdf!=null) {
        for (sk=0;sk<=table.getRowCount()-1;sk++) {                    //IVEDA REZULTATUS I LISTA
          int s = sk +1;
          String str = " "+s+" "+rvardas[sk]+"   "+rtaskai[sk];
          ListBoxItem lbi = list.addItem(str, sk);
          lbi.setColorBackground(color(0, 106, 68));
          //rezlist.add(str);
        }
      }
      else {
        String str = " "+1+" "+rvardas[0]+"   "+rtaskai[0];
        ListBoxItem lbi = list.addItem(str, 0);
        lbi.setColorBackground(color(0, 106, 68));
      }
      ivesta = true;
    }
  }
}

