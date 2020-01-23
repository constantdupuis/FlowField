
FlowFieldRunner runner = new FlowFieldRunner();

void setup()
{
  //size(1800, 1000);
  size(1000, 1000);
  sample00();
}

void draw()
{
  runner.update();
}

void keyPressed() {
  println(" key " + key + " pressed");
  
  if( key == 's')
  {
    String fn = "captures/" + runner.sampleName  +".png";
    save(fn);
  }
  
}
