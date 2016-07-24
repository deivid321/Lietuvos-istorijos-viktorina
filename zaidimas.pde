

void zaidimas() {
  if (etapoNr==1) etapas1();
  else if (etapoNr==2) etapas2();
    else if (etapoNr==3) etapas3();
}
void pertrauka() {
  clock.stop();
  background(253, 185, 19);
  fill(255);
  textAlign(CENTER);
  textSize(width/9);
  if (etapoNr==1)
    text("Bendrų klausimų etape surinkai: ", pr, height/4, width-pr, height/3);
  else if (etapoNr==2)
    text("Paveikslų etape surinkai: ", pr, height/4, width-pr, height/3);
      else text("Datų etape surinkai: ", pr, height/4, width-pr, height/3);
  String tsk = str(taskai);
  textSize(width/4);
  text(tsk, pr, height/2+pr/2, width-pr-pr, height/4);
  textSize(width/9);
  text("taškų.", pr, height/2+pr/2+height/4+pr/3, width-pr-pr, height/3);
  color j = color(0, 106, 68);
  textSize(raidm);
  testi.display(j);
}

