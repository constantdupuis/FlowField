void sample06()
{
  // gradiant from http://www.gradients.io/
  color colorFrom = #3AA17E;
  color colorTo = #00537E;
  background(colorFrom);

  NoiseGenerator ngen = new NoiseGenerator(0.01, 1.0);
  ngen.setNoiseOctaveParam(4, 0.5);
  ngen.setZNoise( 0.0,0.005);

  runner.sampleName = "sample06";
  runner.addLayer( new ParticlesLayer(2000,200, colorFrom, ngen) {
    public void draw(Particle p)
    {
      color colorTo = #00537E;
      //fill( lerpColor(c, target, (float)p.lifeTime/(float)150));
      color lc = lerpColor(p.c, colorTo, 1.0-(float)p.lifeTime/(float)150);
      stroke(lc, 100);
      strokeWeight( 1 + ((float)p.lifeTime/(float)150)*30 );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  

}