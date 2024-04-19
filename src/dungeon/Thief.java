package dungeon;

final class Thief implements Monster {
  @Override
  public StringBuilder getDescription() {
    return new StringBuilder("This monster will steal all your loot. Beware!");
  }
}
