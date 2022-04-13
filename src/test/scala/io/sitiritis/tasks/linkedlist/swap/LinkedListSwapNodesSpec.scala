package io.sitiritis.tasks.linkedlist.swap

import io.sitiritis.tasks.linkedlist.swap.LinkedListSwapNodesSpec._
import org.scalacheck.{Arbitrary, Gen}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.Assertion
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

case class LinkedListSwapNodesSpec()
  extends AnyWordSpec
     with ScalaCheckPropertyChecks
     with TypeCheckedTripleEquals
     with Matchers {

  implicit override val generatorDrivenConfig: PropertyCheckConfiguration =
    PropertyCheckConfiguration(sizeRange = 100)

  "swapNodes" should {
    "make k-th element from the left appear on the k-th place from the right and vise versa" when {
      def prop_ElementsAreSwapped(head: ListNode, k: Int): Assertion = {
        val l = head.toList

        val expectedList =
          if (l.isEmpty) {
            List()
          } else if (l.length == 1) {
            List(head.x)
          } else {
            // Array is 1-indexed
            val buffer = head.toList.to(collection.mutable.ArrayBuffer)
            val kIdx = k - 1
            val temp = buffer(kIdx)
            buffer(kIdx) = buffer(buffer.size - kIdx - 1)
            buffer(buffer.size - kIdx - 1) = temp
            buffer.toList
          }

        val actualList = swapNodes(head, k).toList
        actualList should ===(expectedList)
      }

      "k <= list length" in {
        forAll(minSize(1)) { (params: SwapParams) =>
          prop_ElementsAreSwapped(params.node, params.k)
        }
      }

      val regressionCases = Table(
        ("node", "k"),
        (ListNode(1, 2), 1),
        (ListNode(1, 2, 3), 2),
        (ListNode(91, 56, 48, 77, 91, 96, 55, 53, 68), 7)
      )

      "regression cases are given" in {
        forAll(regressionCases) { (node: ListNode, k: Int) =>
          prop_ElementsAreSwapped(node, k)
        }
      }
    }

    "not change the list" when {
      "k > list length" ignore {
        forAll { (params: NotChangeListParams) =>
          val originalList = params.node.toList
          val swappedList = swapNodes(params.node, params.k).toList
          originalList should ===(swappedList)
        }
      }
    }
  }

}

object LinkedListSwapNodesSpec {
  private case class ListNodeWithSize(node: ListNode, size: Int)
  private case class SwapParams(node: ListNode, k: Int)
  private case class NotChangeListParams(node: ListNode, k: Int)

  implicit private val arbitraryListNode: Arbitrary[ListNodeWithSize] =
    Arbitrary(
      for {
        size <- Gen.sized(Gen.chooseNum(1, _))
        list <- Gen.listOfN(size, Arbitrary.arbitrary[Int])
        node = list.foldRight[ListNode](null) { (x, previousNode) =>
          new ListNode(x, previousNode)
        }
      } yield ListNodeWithSize(node, size)
    )

  implicit private val swapParamsArbitrary: Arbitrary[SwapParams] =
    Arbitrary(
      for {
        nodeWithSize <- Gen.resize(1, Arbitrary.arbitrary[ListNodeWithSize])
        k <-
          if (nodeWithSize.size == 0)
            Gen.const(0)
          else
            Gen.chooseNum(1, nodeWithSize.size)
      } yield SwapParams(nodeWithSize.node, k)
    )

  implicit private val notChangeListParamsArbitrary: Arbitrary[NotChangeListParams] =
    Arbitrary(
      for {
        nodeWithSize <- Arbitrary.arbitrary[ListNodeWithSize]
        k <- Gen.oneOf(
          Gen.chooseNum(Int.MinValue, 0),
          Gen.chooseNum(nodeWithSize.size + 1, Int.MaxValue)
        )
      } yield NotChangeListParams(nodeWithSize.node, k)
    )

}
