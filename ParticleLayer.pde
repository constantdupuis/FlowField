class ParticlesLayer
{
  boolean dead = false;
  ArrayList<Particle> particles;
  int deadCount;
  int aliveCount;
  int lifeTime;
  NoiseGenerator noiseGenerator;
  color c;

  ParticlesLayer(int count, int lifeTime, color c, NoiseGenerator noiseGenerator)
  {
    this.deadCount = 0;
    this.aliveCount = count;
    this.noiseGenerator = noiseGenerator;
    this.lifeTime = lifeTime;
    this.c = c;

    particles = new ArrayList<Particle>();
    for( int x = 0; x < count; x++)
    {
      particles.add( new Particle( new PVector(-100+random(width+200), -100+random(height+200)) , this.lifeTime, this.c, this.noiseGenerator));
    }
  }

  void init()
  {
    this.noiseGenerator.init();
  }

  void update()
  {
    if(dead) return;

    for( int x = 0; x < aliveCount; x++)
    {
      Particle p = particles.get(x);
      if( !p.dead)
      {
        p.update();
        this.draw(p);
        if( p.dead)
        {
          deadCount++;
        }
      }
    }
    
    if( deadCount == aliveCount)
    {
      //println("All particles of this layer are dead");
      dead = true;
    }
    
    noiseGenerator.update();
  }

  void draw( Particle p)
  {
    // to be override to customize drawing
  }
}