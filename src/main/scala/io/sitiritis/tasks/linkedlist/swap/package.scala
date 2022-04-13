package io.sitiritis.tasks.linkedlist

import scala.collection.mutable

package object swap {

  class ListNode(_x: Int = 0, _next: ListNode = null) {
    var next: ListNode = _next
    var x: Int = _x

    override def toString: String = this.toList.toString
  }

  object ListNode {

    def apply(list: Int*): ListNode =
      list.foldRight[ListNode](null) { (x, node) =>
        new ListNode(x, node)
      }

  }

  /**
    * Swaps k-th node from the left with k-th node from the right. The function
    * mutates the original list.
    * @param head
    *   Head of the single linked list to swap
    * @param k
    *   k-th node from the left will be swapped with k-th node from the right. 1
    *   <= k <= list.length
    * @return
    *   Head of the original liked list with the swapped nodes
    */
  def swapNodes(head: ListNode, k: Int): ListNode = {
    var i = 1
    var currentNode = head
    var kthNodeFromLeft: ListNode = null
    while (currentNode != null) {
      if (i == k)
        kthNodeFromLeft = currentNode

      i += 1
      currentNode = currentNode.next
    }
    val length = i - 1
    // The list cannot consist of only 1 element here, otherwise k == 1
    val halfLength = math.floor(length / 2.0).toInt

    if (k <= halfLength) {
      i = k - 1
      currentNode = kthNodeFromLeft

      while (i < (length - k)) {
        currentNode = currentNode.next
        i += 1
      }
      swapNodesValues(kthNodeFromLeft, currentNode)
    } else {
      if (k == length) {
        swapNodesValues(head, kthNodeFromLeft)
      } else {
        i = 1
        currentNode = head

        while (i <= (length - k)) {
          currentNode = currentNode.next
          i += 1
        }
        swapNodesValues(kthNodeFromLeft, currentNode)
      }
    }

    head
  }

  private def swapNodesValues(left: ListNode, right: ListNode): Unit = {
    if (left != null && right != null) {
      val x = left.x
      left.x = right.x
      right.x = x
    }
  }

  implicit class ListNodeOps(private val ln: ListNode) extends AnyVal {

    def toList: List[Int] = {
      val result = mutable.ArrayBuffer[Int]()
      var node = ln
      while (node != null) {
        result += node.x
        node = node.next
      }
      result.toList
    }

  }

}
