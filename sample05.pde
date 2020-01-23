void sample05 ()
{
  background(0, 30, 30);

  NoiseGenerator ngen = new NoiseGenerator(0.01, 0.5);
  ngen.setNoiseOctaveParam(4, 0.5);
  ngen.setZNoise( 0.0,0.001);

  runner.sampleName = "sample05";
  runner.addLayer( new ParticlesLayer(1000,2000, color(255), ngen) {
    public void draw(Particle p)
    {
      strokeWeight(1);
      stroke(p.c, 10);
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });
}