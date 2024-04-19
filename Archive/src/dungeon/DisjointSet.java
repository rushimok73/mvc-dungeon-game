package dungeon;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used in the implementation for kruskals algorithm.
 * Citation - https://www.youtube.com/watch?v=fAuF0EuZVCk .
 */
final class DisjointSet {

  private final Map<Integer, Node> map = new HashMap<>();

  /**
   * Node class.
   */
  class Node {
    int data;
    Node parent;
    int rank;
  }

  /**
   * Makes a set with single element.
   * @param data data to be added.
   */
  void makeSet(int data) {
    Node node = new Node();
    node.data = data;
    node.parent = node;
    node.rank = 0;
    map.put(data, node);
  }

  /**
   * Performs union operation of disjoint sets.
   * @param data1 item1.
   * @param data2 item2.
   * @return true on success false on failure.
   */
  boolean union(int data1, int data2) {
    Node node1 = map.get(data1);
    Node node2 = map.get(data2);

    Node parent1 = findSet(node1);
    Node parent2 = findSet(node2);

    if (parent1.data == parent2.data) {
      return false;
    }

    if (parent1.rank >= parent2.rank) {
      parent1.rank = (parent1.rank == parent2.rank) ? parent1.rank + 1 : parent1.rank;
      parent2.parent = parent1;
    } else {
      parent1.parent = parent2;
    }
    return true;
  }

  /**
   * Finds corresponding set using element.
   * @param data data to be searched for.
   * @return data of set.
   */
  int findSet(int data) {
    return findSet(map.get(data)).data;
  }

  private Node findSet(Node node) {
    Node parent = node.parent;
    if (parent == node) {
      return parent;
    }
    node.parent = findSet(node.parent);
    return node.parent;
  }
}