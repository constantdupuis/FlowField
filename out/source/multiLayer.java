import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class multiLayer extends PApplet {


FlowFieldRunner runner = new FlowFieldRunner();

public void setup()
{
  //size(1800, 1000);
  
  sample10();
}

public void draw()
{
  runner.update();
}

public void keyPressed() {
  println(" key " + key + " pressed");
  
  if( key == 's')
  {
    String fn = "captures/" + runner.sampleName  +".png";
    save(fn);
  }
  
}
class FlowFieldRunner
{
  ArrayList<ParticlesLayer> layers;
  ParticlesLayer currentLayer;
  int currentLayerIndex = -1;
  boolean start = true;
  String sampleName = "<none>";

  FlowFieldRunner()
  {
    layers = new ArrayList<ParticlesLayer>();
  }

  public void addLayer(ParticlesLayer pl)
  {
    if( currentLayerIndex == -1)
    {
      currentLayerIndex = 0;
      currentLayer = pl;
    }

    layers.add(pl);
  }

  public void update()
  {
    if(start)
    {
      this.start = false;
      println("Layer "+(currentLayerIndex+1)+" started");
      currentLayer.init();
    }

    if( currentLayer.dead)
    {
      if( currentLayerIndex < layers.size()-1)
      {
        println("Layer "+(currentLayerIndex+1)+" done");
        currentLayerIndex++;
        println("Layer "+(currentLayerIndex+1)+" started");
        currentLayer = layers.get(currentLayerIndex);
        currentLayer.init();
        //currentLayer.update();
      }
      else
      {
        println("Layer "+(currentLayerIndex+1)+" done");
        noLoop();
        println("All layers drawned :-)");
      }
    }
    else
    {
      // update current layer
      currentLayer.update();
    }
  }

}

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
    this.noiseZInc = 0.0f;
    this.noiseZ = 0.0f; 
    this.noiseOctave = 4;
    this.noiseFalloff = 0.5f;
  }

  public float getNoiseAt( float x, float y )
  {
    return noise(x*noiseScale, y*noiseScale, noiseZ);
  }

  public void setNoiseOctaveParam(int octaves, float fallOff)
  {
    this.noiseOctave = octaves;
    this.noiseFalloff = fallOff;
  }

  public void setZNoise( float startZNoise, float zNoiseInc)
  {
    this.noiseZInc = zNoiseInc;
    this.noiseZ = startZNoise;
  }

  public PVector getForceAt(float x, float y)
  {
    float n = noise(x * noiseScale, y * noiseScale, noiseZ )*TWO_PI;
    PVector force = PVector.fromAngle(n);
    force = force.mult(forceScale);
    return force;
  }

  public void init()
  {
    //println("NoiseGenerator::init - octave "+this.noiseOctave+" falloff "+this.noiseFalloff+"");
    noiseDetail(this.noiseOctave, this.noiseFalloff);
  }

  public void update()
  {
    noiseZ += noiseZInc; 
  }
}
class Particle
{
  PVector pos;
  PVector prev;

  int c;
  int lifeTime;
  int startLifeTime;

  boolean dead;

  NoiseGenerator noiseGenerator;

  Particle(PVector pos, int lifeTime, int c, NoiseGenerator noiseGenerator)
  {
    this.pos = pos;
    this.prev = pos;
    this.lifeTime = lifeTime;
    this.startLifeTime = lifeTime;
    this.c = c;
    this.dead = false;
    this.noiseGenerator = noiseGenerator;
  }

  public void update()
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

class ParticlesLayer
{
  boolean dead = false;
  ArrayList<Particle> particles;
  int deadCount;
  int aliveCount;
  int lifeTime;
  NoiseGenerator noiseGenerator;
  int c;

  ParticlesLayer(int count, int lifeTime, int c, NoiseGenerator noiseGenerator)
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

  public void init()
  {
    this.noiseGenerator.init();
  }

  public void update()
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

  public void draw( Particle p)
  {
    // to be override to customize drawing
  }
}
public void sample00()
{
  background(0, 30, 30);

  NoiseGenerator ngen = new NoiseGenerator(0.01f, 0.5f);
  ngen.setNoiseOctaveParam(3, 0.3f);

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
public void sample01()
{
  background(255);

  NoiseGenerator ngen = new NoiseGenerator(0.01f, 0.2f);
  ngen.setNoiseOctaveParam(4, 0.5f);

  runner.sampleName = "sample01";
  runner.addLayer( new ParticlesLayer(10000,500, color(0, 30, 30), ngen) {
    public void draw(Particle p)
    {
      strokeWeight(1);
      stroke(p.c, 10);
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });
}
public void sample02()
{
  background(0, 30, 30);

  NoiseGenerator ngen = new NoiseGenerator(0.01f, 1.0f);
  ngen.setNoiseOctaveParam(4, 0.5f);

  runner.sampleName = "sample02";
  runner.addLayer( new ParticlesLayer(10000,100, color(30, 100, 60), ngen) {
    public void draw(Particle p)
    {
      strokeWeight(6);
      stroke(p.c, 5);
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  ngen = new NoiseGenerator(0.01f, 1.0f);
  ngen.setNoiseOctaveParam(4, 0.5f);

  runner.addLayer( new ParticlesLayer(10000, 150, color(255, 0, 0), ngen) {
    public void draw(Particle p)
    {
      strokeWeight(2);
      stroke(p.c, 10);
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  ngen = new NoiseGenerator(0.01f, 1.0f);
  ngen.setNoiseOctaveParam(4, 0.5f);

  runner.addLayer( new ParticlesLayer(10000, 200, color(255), ngen) {
    public void draw(Particle p)
    {
      strokeWeight(1);
      stroke(p.c, 10);
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });
  
  
}
public void sample03()
{
  background(0);

  NoiseGenerator ngen = new NoiseGenerator(0.01f, 1.0f);
  ngen.setNoiseOctaveParam(4, 0.5f);

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
public void sample04()
{
  // gradiant from http://www.gradients.io/
  int colorA = 0xff00A8C5;
  int colorB = 0xffFFFF7E;
  background(colorB);

  NoiseGenerator ngen = new NoiseGenerator(0.01f, 1.0f);
  ngen.setNoiseOctaveParam(4, 0.5f);

  runner.sampleName = "sample04";
  runner.addLayer( new ParticlesLayer(4000,150, colorA, ngen) {
    public void draw(Particle p)
    {
      int target = 0xffFFFF7E;
      //fill( lerpColor(c, target, (float)p.lifeTime/(float)150));
      int lc = lerpColor(p.c, target, (float)p.lifeTime/(float)150);
      stroke(lc, 100);
      strokeWeight( 1 + ((float)p.lifeTime/(float)150)*30 );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

}
public void sample05 ()
{
  background(0, 30, 30);

  NoiseGenerator ngen = new NoiseGenerator(0.01f, 0.5f);
  ngen.setNoiseOctaveParam(4, 0.5f);
  ngen.setZNoise( 0.0f,0.001f);

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
public void sample06()
{
  // gradiant from http://www.gradients.io/
  int colorFrom = 0xff3AA17E;
  int colorTo = 0xff00537E;
  background(colorFrom);

  NoiseGenerator ngen = new NoiseGenerator(0.01f, 1.0f);
  ngen.setNoiseOctaveParam(4, 0.5f);
  ngen.setZNoise( 0.0f,0.005f);

  runner.sampleName = "sample06";
  runner.addLayer( new ParticlesLayer(2000,200, colorFrom, ngen) {
    public void draw(Particle p)
    {
      int colorTo = 0xff00537E;
      //fill( lerpColor(c, target, (float)p.lifeTime/(float)150));
      int lc = lerpColor(p.c, colorTo, 1.0f-(float)p.lifeTime/(float)150);
      stroke(lc, 100);
      strokeWeight( 1 + ((float)p.lifeTime/(float)150)*30 );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  

}
public void sample07()
{
  // gradiant from http://www.gradients.io/
  int colora = 0xff8c640e;
  background(0xfff1b637);

  NoiseGenerator ngen = new NoiseGenerator(0.01f, 1.0f);
  ngen.setNoiseOctaveParam(4, 0.5f);
  ngen.setZNoise( 0.0f,0.01f);

  runner.sampleName = "sample07";
  runner.addLayer( new ParticlesLayer(2000,50, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0f - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 10.0f;
      stroke(p.c, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  colora = 0xff8c4d0e;

  runner.addLayer( new ParticlesLayer(2000,50, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0f - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 10.0f;
      stroke(p.c, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });


  colora = 0xffF0F05E;

  runner.addLayer( new ParticlesLayer(2000,50, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0f - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 10.0f;
      stroke(p.c, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  colora = 0xff8BA80E;

  runner.addLayer( new ParticlesLayer(2000,50, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0f - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 10.0f;
      stroke(p.c, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  

}
public int rgbMixRandom(int base, float w) {
  float r, g, b;

  /* Check bounds for weight parameter */
  w = w > 1 ? 1 : w;
  w = w < 0 ? 0 : w;

  /* Generate components for a random RGB color */
  r = random(256);
  g = random(256);
  b = random(256);

  /* Mix user-specified color using given weight */
  r = (1-w) * r + w * red(base);
  g = (1-w) * g + w * green(base);
  b = (1-w) * b + w * blue(base);

  return color(r, g, b);
}

public void sample08()
{
  // gradiant from http://www.gradients.io/
  
  background(0xff14136C);

  int colora = 0xff2120B4;

  NoiseGenerator ngen = new NoiseGenerator(0.01f, 1.0f);
  ngen.setNoiseOctaveParam(4, 0.5f);
  //ngen.setZNoise( 0.0,0.01);

  runner.sampleName = "sample08";
  runner.addLayer( new ParticlesLayer(500,200, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0f - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 40; 
      int tmp = rgbMixRandom(p.c, 0.9f);
      stroke(tmp, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  colora = 0xff4D4CB4;

  runner.addLayer( new ParticlesLayer(1000,100, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0f - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 30; 
      int tmp = rgbMixRandom(p.c, 0.9f);
      stroke(tmp, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });


  colora = 0xff7978B4;

  runner.addLayer( new ParticlesLayer(1200,50, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0f - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 10; 
      int tmp = rgbMixRandom(p.c, 0.9f);
      stroke(tmp, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  colora = 0xffFFFFFF;

  runner.addLayer( new ParticlesLayer(1200,50, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0f - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 5; 
      int tmp = rgbMixRandom(p.c, 0.9f);
      stroke(tmp, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  

}
public void sample09()
{
  // gradiant from http://www.gradients.io/
  
  background(0xff5F7DFF);

  NoiseGenerator ngen = new NoiseGenerator(0.01f, 1.0f);
  ngen.setNoiseOctaveParam(4, 0.5f);
  //ngen.setZNoise( 0.0,0.01);

  int colora = 0xff574AE8;

  runner.sampleName = "sample09";
  runner.addLayer( new ParticlesLayer(2000,200, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0f - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 10.0f;
      stroke(p.c, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  colora = 0xff52CDFF;

  runner.addLayer( new ParticlesLayer(2000,200, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0f - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 10.0f;
      stroke(p.c, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  colora = 0xff4A91E8;

  runner.addLayer( new ParticlesLayer(2000,200, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0f - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 10.0f;
      stroke(p.c, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

 
  colora = 0xff8F52FF;

  runner.addLayer( new ParticlesLayer(2000,150, colora, ngen) {
    public void draw(Particle p)
    {
      float r = 1.0f - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 5.0f;
      stroke(p.c, 50);
      //stroke(#000000, 255);
      strokeWeight( r );
      line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
    }
  });

  

}
public void sample10()
{
  // gradiant from http://www.gradients.io/
  
  background(255);

  NoiseGenerator ngen = new NoiseGenerator(0.01f, 1.0f);
  ngen.setNoiseOctaveParam(1, 0.5f);
  //ngen.setZNoise( 0.0,0.01);

  colorMode(HSB, 255);

  int colora = color(0, 255, 128);

  runner.sampleName = "sample10";
  runner.addLayer( new ParticlesLayer(2000,500, colora, ngen) {
    public void draw(Particle p)
    {
      float hue = hue(p.c);
      float saturation = saturation(p.c);
      float brightness = brightness(p.c);

      float r = 1.0f - ((float)p.lifeTime/(float)p.startLifeTime);
      r *= 5.0f;
      hue += random(50);
      //stroke(hue, saturation, brightness, 150);
      //stroke(0+random(100), 255, 128, 20);

      //strokeWeight( r );
      //line(p.prev.x, p.prev.y, p.pos.x, p.pos.y);
      noStroke();
      fill(hue, saturation, brightness, 50);
      ellipse(p.prev.x, p.prev.y, r, r);
    }
  });

}
  public void settings() {  size(800, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "multiLayer" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
