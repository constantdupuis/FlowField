void sample00()
{
  background(0, 30, 30);

  NoiseGenerator ngen = new NoiseGenerator(0.01, 0.5);
  ngen.setNoiseOctaveParam(3, 0.3);

  runner.sampleName = "sample00";
  runner.addLayer( new ParticlesLayer(10000,600, color(255), ngen) {
    public void draw(Particle p)
    {
      strokeWeight(1);
      stroke(p.c, 5);
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });
}