package com.vrv.ml.innerclass

class GraphA {

  class Node {
    var connectedNodes: List[GraphA#Node] = Nil

    def connectTo(node: GraphA#Node) {
      if (connectedNodes.find(node.equals).isEmpty) {
        connectedNodes = node :: connectedNodes
      }
    }
  }

  var nodes: List[Node] = Nil

  def newNode: Node = {
    val res = new Node
    nodes = res :: nodes
    res
  }
}
