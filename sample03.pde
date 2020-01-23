void sample03()
{
  background(0);

  NoiseGenerator ngen = new NoiseGenerator(0.01, 1.0);
  ngen.setNoiseOctaveParam(4, 0.5);

  runner.sampleName = "sample03";
  runner.addLayer( new ParticlesLayer(10000,150, color(80, 0, 0), ngen) {
    public void draw(Particle p)
    {
      strokeWeight(10);
      stroke(p.c, 10);
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  runner.addLayer( new ParticlesLayer(10000,150, color(140, 0, 0), ngen) {
    public void draw(Particle p)
    {
      strokeWeight(5);
      stroke(p.c, 10);
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  runner.addLayer( new ParticlesLayer(10000,250, color(250, 0, 0), ngen) {
    public void draw(Particle p)
    {
      strokeWeight(1);
      stroke(p.c, 10);
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

}