void sample04()
{
  // gradiant from http://www.gradients.io/
  color colorA = #00A8C5;
  color colorB = #FFFF7E;
  background(colorB);

  NoiseGenerator ngen = new NoiseGenerator(0.01, 1.0);
  ngen.setNoiseOctaveParam(4, 0.5);

  runner.sampleName = "sample04";
  runner.addLayer( new ParticlesLayer(4000,150, colorA, ngen) {
    public void draw(Particle p)
    {
      color target = #FFFF7E;
      //fill( lerpColor(c, target, (float)p.lifeTime/(float)150));
      color lc = lerpColor(p.c, target, (float)p.lifeTime/(float)150);
      stroke(lc, 100);
      strokeWeight( 1 + ((float)p.lifeTime/(float)150)*30 );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

}