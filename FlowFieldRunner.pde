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

  void addLayer(ParticlesLayer pl)
  {
    if( currentLayerIndex == -1)
    {
      currentLayerIndex = 0;
      currentLayer = pl;
    }

    layers.add(pl);
  }

  void update()
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

