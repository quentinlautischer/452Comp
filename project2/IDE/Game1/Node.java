class Node {
  Terrain terrain;
  int track = -1;

  public Node(Terrain terrain) {
    this.terrain = terrain;
  }

  public Terrain getTerrain() {
    return terrain;
  }

  public void setTrack(int i) {
    this.track = i;
  }

}