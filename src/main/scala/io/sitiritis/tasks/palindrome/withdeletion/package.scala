package io.sitiritis.tasks.palindrome

package object withdeletion {

  /**
    * Given a string s, return true if the s can be palindrome after deleting at
    * most one character from it.
    * @note
    *   https://leetcode.com/problems/valid-palindrome-ii/
    */
  def validPalindrome(s: String): Boolean = validPalindromeImperative(s)

  def validPalindromeLazyList(s: String): Boolean = {
    val allRemovals = LazyList.unfold(0) { i =>
      if (i < s.length) {
        val cs = s.slice(0, i) ++ s.slice(i + 1, s.length)
        Some((cs, i + 1))
      } else None
    }

    allRemovals.exists(str => str == str.reverse)
  }

  def validPalindromeRecursion(s: String): Boolean = {
    @annotation.tailrec
    def go(i: Int): Boolean = {
      if (i < s.length) {
        val cs = s.slice(0, i) ++ s.slice(i + 1, s.length)
        cs == cs.reverse || go(i + 1)
      } else false
    }

    go(0)
  }

  def validPalindromeStringBuilder(s: String): Boolean = {
    val sb = new StringBuilder(s)

    def isPalindrome(s: scala.collection.mutable.IndexedSeq[Char]): Boolean = {
      val hi = math.ceil(s.length / 2.0).intValue
      val (lh, rh) = s.splitAt(hi)
      lh.zip(rh).forall(cs => cs._1 == cs._2)
    }

    @annotation.tailrec
    def go(i: Int): Boolean = {
      println(s"$i")
      if (i < s.length) {
        val cs = sb.slice(0, i) ++ sb.slice(i + 1, s.length)
        isPalindrome(cs) || go(i + 1)
      } else false
    }

    go(0)
  }

  def validPalindromeImperative(s: String): Boolean = {
    def isPalindrome(s: String): Boolean = {
      @annotation.tailrec
      def go(s: String, i: Int, j: Int): Boolean =
        if (i < j)
          s.charAt(i) == s.charAt(j) && go(s, i + 1, j - 1)
        else
          true

      go(s, 0, s.length - 1)
    }

    @annotation.tailrec
    def go(s: String, i: Int, j: Int): Boolean = {
      if (i < j)
        if (s(i) == s(j))
          go(s, i + 1, j - 1)
        else {
          val leftDeleted = s.slice(i + 1, j + 1)
          val rightDeleted = s.slice(i, j)
          isPalindrome(leftDeleted) || isPalindrome(rightDeleted)
        }
      else
        true
    }

    go(s, 0, s.length - 1)
  }

}
