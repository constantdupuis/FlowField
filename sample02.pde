void sample02()
{
  background(0, 30, 30);

  NoiseGenerator ngen = new NoiseGenerator(0.01, 1.0);
  ngen.setNoiseOctaveParam(4, 0.5);

  runner.sampleName = "sample02";
  runner.addLayer( new ParticlesLayer(10000,100, color(30, 100, 60), ngen) {
    public void draw(Particle p)
    {
      strokeWeight(6);
      stroke(p.c, 5);
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  ngen = new NoiseGenerator(0.01, 1.0);
  ngen.setNoiseOctaveParam(4, 0.5);

  runner.addLayer( new ParticlesLayer(10000, 150, color(255, 0, 0), ngen) {
    public void draw(Particle p)
    {
      strokeWeight(2);
      stroke(p.c, 10);
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  ngen = new NoiseGenerator(0.01, 1.0);
  ngen.setNoiseOctaveParam(4, 0.5);

  runner.addLayer( new ParticlesLayer(10000, 200, color(255), ngen) {
    public void draw(Particle p)
    {
      strokeWeight(1);
      stroke(p.c, 10);
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });
  
  
}