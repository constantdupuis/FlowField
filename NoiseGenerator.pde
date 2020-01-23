class NoiseGenerator
{
  float noiseScale;
  float forceScale;
  float noiseZInc;
  float noiseZ;
  int noiseOctave;
  float noiseFalloff;

  NoiseGenerator(float noiseScale, float forceScale)
  {
    this.noiseScale = noiseScale;
    this.forceScale = forceScale;
    this.noiseZInc = 0.0;
    this.noiseZ = 0.0; 
    this.noiseOctave = 4;
    this.noiseFalloff = 0.5;
  }

  float getNoiseAt( float x, float y )
  {
    return noise(x*noiseScale, y*noiseScale, noiseZ);
  }

  void setNoiseOctaveParam(int octaves, float fallOff)
  {
    this.noiseOctave = octaves;
    this.noiseFalloff = fallOff;
  }

  void setZNoise( float startZNoise, float zNoiseInc)
  {
    this.noiseZInc = zNoiseInc;
    this.noiseZ = startZNoise;
  }

  PVector getForceAt(float x, float y)
  {
    float n = noise(x * noiseScale, y * noiseScale, noiseZ )*TWO_PI;
    PVector force = PVector.fromAngle(n);
    force = force.mult(forceScale);
    return force;
  }

  void init()
  {
    //println("NoiseGenerator::init - octave "+this.noiseOctave+" falloff "+this.noiseFalloff+"");
    noiseDetail(this.noiseOctave, this.noiseFalloff);
  }

  void update()
  {
    noiseZ += noiseZInc; 
  }
}