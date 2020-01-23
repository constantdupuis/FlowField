void sample10()
{
  // gradiant from http://www.gradients.io/
  
  background(255);

  NoiseGenerator ngen = new NoiseGenerator(0.01, 1.0);
  ngen.setNoiseOctaveParam(1, 0.5);
  //ngen.setZNoise( 0.0,0.01);

  colorMode(HSB, 255);

  color colora = color(0, 255, 128);

  runner.sampleName = "sample10";
  runner.addLayer( new ParticlesLayer(2000,500, colora, ngen) {
    public void draw(Particle p)
    {
      float hue = hue(p.c);
      float saturation = saturation(p.c);
      float brightness = brightness(p.c);

      float r = 1.0 - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 5.0;
      hue += random(50);
      //stroke(hue, saturation, brightness, 150);
      //stroke(0+random(100), 255, 128, 20);

      //strokeWeight( r );
      //line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
      noStroke();
      fill(hue, saturation, brightness, 50);
      ellipse(p.prev.x, p.prev.y, r, r);
    }
  });

}