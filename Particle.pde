class Particle
{
  PVector pos;
  PVector prev;

  color c;
  int lifeTime;
  int startLifeTime;

  boolean dead;

  NoiseGenerator noiseGenerator;

  Particle(PVector pos, int lifeTime, color c, NoiseGenerator noiseGenerator)
  {
    this.pos = pos;
    this.prev = pos;
    this.lifeTime = lifeTime;
    this.startLifeTime = lifeTime;
    this.c = c;
    this.dead = false;
    this.noiseGenerator = noiseGenerator;
  }

  void update()
  {
    this.lifeTime--;
    if( this.lifeTime <= 0)
    {
      this.dead = true;
    }
    else 
    {
      this.prev = this.pos;
      this.pos.add(this.noiseGenerator.getForceAt(this.pos.x, this.pos.y));
    }
  }
}

