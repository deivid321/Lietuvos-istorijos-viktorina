
void meniu()
{ 
  imageMode(CORNER);
  image(fonas,0,0,width,height);
  if (taisyklesshow==false){
  color spa = color(0,106,68);
  if (sriftas) textFont(klFont);
  textSize(raidm);
  zaisti.display(spa);
  rekordai.display(spa);
  textSize(raidvar);
  taisykles.display(color(253,185,19,145));
  tint(255,150);
  image(graziau,pr,(int)( height/2.2),width-pr-pr,height/7);
  image(graziau,pr,(int)( height/2.2+height/7+pr),width-pr-pr,height/7);
  noTint();
  zodziai();
  } else{
   textAlign(CENTER);
   if (sriftas) textFont(f);
   textSize(raidm);
   fill(253,185,19);
   text("Taisyklės",0,pr,width,2*pr);
   text("Žaidimą sudaro trys etapai: Bendrų klausimų, paveikslų ir datų etapai. Žaidimo tikslas per ribotą laiką atsakyti į, kuo daugiau klausimų iš Lietuvos istorijos. Už teisingą atsakymą skiriami 3 taškai, už padarytą klaidą atimami 2 taškai. Sėkmės!",pr/2,2*pr,width-pr/2,height-pr);
    
  } 
}
void zodziai(){
  color geltona = color(253,185,19);
  fill(geltona);
  if (sriftas) textFont(f);
  textAlign(RECT);
  text("Lietuvos",pr,height/13,width-pr,height/8.3);
  fill(0,106,68);
  text("istorijos",width/2-pr-pr/1.5,height/6,width-pr/2,height/4);
  fill(193,39,45);
  text("viktorina",pr,height/4,width-pr,height/3); 
}




