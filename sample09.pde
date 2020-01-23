void sample09()
{
  // gradiant from http://www.gradients.io/
  
  background(#5F7DFF);

  NoiseGenerator ngen = new NoiseGenerator(0.01, 1.0);
  ngen.setNoiseOctaveParam(4, 0.5);
  //ngen.setZNoise( 0.0,0.01);

  color colora = #574AE8;

  runner.sampleName = "sample09";
  runner.addLayer( new ParticlesLayer(2000,200, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0 - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 10.0;
      stroke(p.c, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  colora = #52CDFF;

  runner.addLayer( new ParticlesLayer(2000,200, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0 - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 10.0;
      stroke(p.c, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  colora = #4A91E8;

  runner.addLayer( new ParticlesLayer(2000,200, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0 - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 10.0;
      stroke(p.c, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

 
  colora = #8F52FF;

  runner.addLayer( new ParticlesLayer(2000,150, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0 - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 5.0;
      stroke(p.c, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  

}