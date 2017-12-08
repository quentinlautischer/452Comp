class Tile {
  TileType type = TileType.EMPTY;

  public Tile(){}

  public Tile(TileType type) {
    this.type = type;
  }

  public TileType getTileType() {
    return type;
  }

  public void setType(TileType type) {
    this.type = type;
  }

  public TileType getType(){
    return this.type;
  }
}