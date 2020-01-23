color rgbMixRandom(color base, float w) {
  float r, g, b;

  /* Check bounds for weight parameter */
  w = w > 1 ? 1 : w;
  w = w < 0 ? 0 : w;

  /* Generate components for a random RGB color */
  r = random(256);
  g = random(256);
  b = random(256);

  /* Mix user-specified color using given weight */
  r = (1-w) * r + w * red(base);
  g = (1-w) * g + w * green(base);
  b = (1-w) * b + w * blue(base);

  return color(r, g, b);
}

void sample08()
{
  // gradiant from http://www.gradients.io/
  
  background(#14136C);

  color colora = #2120B4;

  NoiseGenerator ngen = new NoiseGenerator(0.01, 1.0);
  ngen.setNoiseOctaveParam(4, 0.5);
  //ngen.setZNoise( 0.0,0.01);

  runner.sampleName = "sample08";
  runner.addLayer( new ParticlesLayer(500,200, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0 - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 40; 
      color tmp = rgbMixRandom(p.c, 0.9);
      stroke(tmp, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  colora = #4D4CB4;

  runner.addLayer( new ParticlesLayer(1000,100, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0 - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 30; 
      color tmp = rgbMixRandom(p.c, 0.9);
      stroke(tmp, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });


  colora = #7978B4;

  runner.addLayer( new ParticlesLayer(1200,50, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0 - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 10; 
      color tmp = rgbMixRandom(p.c, 0.9);
      stroke(tmp, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  colora = #FFFFFF;

  runner.addLayer( new ParticlesLayer(1200,50, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0 - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 5; 
      color tmp = rgbMixRandom(p.c, 0.9);
      stroke(tmp, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  

}