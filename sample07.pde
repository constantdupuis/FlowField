void sample07()
{
  // gradiant from http://www.gradients.io/
  color colora = #8c640e;
  background(#f1b637);

  NoiseGenerator ngen = new NoiseGenerator(0.01, 1.0);
  ngen.setNoiseOctaveParam(4, 0.5);
  ngen.setZNoise( 0.0,0.01);

  runner.sampleName = "sample07";
  runner.addLayer( new ParticlesLayer(2000,50, colora, ngen) {
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

  colora = #8c4d0e;

  runner.addLayer( new ParticlesLayer(2000,50, colora, ngen) {
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


  colora = #F0F05E;

  runner.addLayer( new ParticlesLayer(2000,50, colora, ngen) {
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

  colora = #8BA80E;

  runner.addLayer( new ParticlesLayer(2000,50, colora, ngen) {
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

  

}