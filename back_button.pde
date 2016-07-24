boolean surfaceKeyDown(int code, KeyEvent event) {
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
    if (taisyklesshow){
      paruosia();
      zaidziama = false;              // GRIZTI KAI REKORDAI
      baigta = false;
      rekordaishow = false;                  
      ivesta = false;
      taisyklesshow = false;
      return false;  
    }
    list.clear();
  }
  return super.surfaceKeyDown(code, event);
}

boolean surfaceKeyUp(int code, KeyEvent event) {
  return super.surfaceKeyDown(code, event);
}

