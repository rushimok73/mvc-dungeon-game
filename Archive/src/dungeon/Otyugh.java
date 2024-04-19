package dungeon;

final class Otyugh implements Monster {
  private int hp;
  private boolean isDead;

  Otyugh() {
    this.hp = 2;
    this.isDead = false;
  }

  int getHp() {
    return this.hp;
  }

  void reduceHp() {
    if (!isDead) {
      this.hp--;
      if (this.hp == 0) {
        this.isDead = true;
      }
    }
  }

  boolean isDead() {
    return this.isDead;
  }


  @Override
  public StringBuilder getDescription() {
    return new StringBuilder("This monster will eat you up!");
  }
}
