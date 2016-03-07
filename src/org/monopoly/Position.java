package org.monopoly;

public class Position {
  int x, y;
  
  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }
  public void setX(int i) {
    x = i;
  }
  public void setY(int j) {
    y = j;
  }
  public int getX() {
    return x;
  }
  public int getY() {
    return y;
  }
}
