package ImpatientScala

import java.awt.datatransfer.{DataFlavor, SystemFlavorMap}
import java.util.TimeZone

import scala.collection.immutable.Seq
import scala.collection.{JavaConverters, mutable}

/**
  * Working with Arrays - Chapter 3
  */
object Chapter3Main extends App {

  println("Prelims.")
  // Multi-dimensional arrays
  val matrix = Array.ofDim[Double](3, 4)
  // Ragged Arrays
  val triangle = new Array[Array[Int]](10)
  for (i <- triangle.indices)
    triangle(i) = new Array[Int](i + 1)

  println(
    "1. Write a code snippet that sets a to an array of n random integers" +
      " between 0 (inclusive) and n (exclusive).")
  val n: Int = 20
  val a1: Array[Int] = new Array[Int](n)
  for (i <- a1.indices) a1(i) = scala.util.Random.nextInt(n)
  println(a1.mkString("< ", ", ", " >"))

  println(
    "2. Write a loop that swaps adjacent elements of an array of " +
      "integers. For example Array(1,2,3,4,5) becomes Array(2,1,4,3,5).")
  val a2: Array[Int] = a1
  for (i <- 1 until (a2.length, 2)) {
    val t = a2(i - 1)
    a2(i - 1) = a2(i)
    a2(i) = t
  }
  println(a2.mkString("< ", ", ", " >"))

  println(
    "3. Repeat the preceding assignment, but produce a new array " +
      "with the swapped values. Use for yield.")
  def swapAdj(a: Array[Int]): Seq[Int] = {
    for (i <- a.indices)
      yield
        if (i % 2 == 0)
          if (i < a.length - 1)
            a(i + 1)
          else
            a(i)
        else a(i - 1)
  }
  val a3: Seq[Int] = swapAdj(a1)
  println(a3.mkString("< ", ", ", " >"))

  println(
    "4. Given an array of integers, produce a new array that contains " +
      "all positive values of the original array, in their original order, followed " +
      "by all values that are zero or negative, in their original order")
  val a4 = Array(1, 2, 4, 5, -1, 0, -13, -4, -2)
  def positiveNegativeChanged(a: Array[Int]): Array[Int] = {
    a.filter(_ > 0).sortWith(_ > _) ++ a.filter(_ <= 0).sortWith(_ > _)
  }
  def positiveNegativeUnChanged(a: Array[Int]): Array[Int] = {
    a.filter(_ > 0) ++ a.filter(_ <= 0)
  }
  println(
    s"Changed: ${positiveNegativeChanged(a4).mkString("< ", ", ", " >")}")
  println(
    s"UnChanged: ${positiveNegativeUnChanged(a4).mkString("< ", ", ", " >")}")

  println(
    "5. How do you compute the average of an Array[Double] ?"
  )
  val a5: Array[Double] = Array(1.0, 4.2, 1.1231, 99.1231)
  println(s"Mean: ${a5.sum / a5.length}")

  println(
    "6. How do you rearrange the elements of an Array[Int] so that they appear in reverse sorted order? " +
      "How do you do the same with an ArrayBuffer[Int]?"
  )
  val a6: Array[Int] = Array(3, 2, 99, 4 ,5 ,6)
  // Quicksort the Array
  util.Sorting.quickSort(a6)
  val rev6 = a6.reverse
  println(s"Reversed Order: ${rev6.mkString("< ", ", ", " >")}")
  val a6_2 = a6.toBuffer.sortWith(_ < _)
  println(s"With Buffer: $a6_2")

  println(
    "7. Write a code snippet that produces all values from an array with duplicates removed?"
  )
  val a7_1 = Array(1, 1, 1, 2, 2, 4)
  println(s"Original Array ${a7_1.mkString("< ", ", ", " >")} duplicates removed " +
    s"${a7_1.distinct.mkString("< ", ", ", " >")}")

  println(
    "8. Rewrite the example at the end of Section 3.4, 'Transforming Arrays,' on Page 32. Collect indexes of the " +
      "negative elements, reverse the sequence, drop the last index, and call a.remove(i) for each. Compare the " +
      "efficiency this approach with the two approaches in Section 3.4"
  )
  //  for (j <- 0 until indexes.length) a(j) = a(indexes(j))
  //  a.trimEnd(a.length - indexes.length)
  val a8 = Array(1, 2, -3, -4, -99, 1, 12).toBuffer
  val a8_1: Seq[Int] = for (i <- a8.indices if i <= a8.indexWhere(_ < 0) || a8(i) > 0) yield a8(i)
  val a8_2_a: Seq[Int] = a8.indices.filter(a8(_)<0).drop(1)
  for (i <- a8_2_a.indices) a8.remove(a8_2_a(i) - i)
  println(s"Solution 1: $a8_1 Solution 2: $a8")

  println(
    "9. Make a collection of all the timezones returned by java.util.TimeZone.getAvailableIDs that are in America " +
      "Strip off the 'America/' prefix and sort the result."
  )
  val a9: Array[String] = TimeZone.getAvailableIDs.filter(_.startsWith("America")).map(_.stripPrefix("America/")).sorted
  println(s"American TimeZones: ${a9.take(4).mkString("< ", ", ", " >")}")

  println(
    "10. Import java.awt.datatransfer._ and make an object of type SystemFlavorMap with the call" +
      "   'val flavors = SystemFlavorMap.getDefaultFlavorMap().asInstanceOf[SystemFlavorMap]' " +
      "Then call the getNativesForFlavor method with parameter DataFlavor.imageFlavor and get the return" +
      " value as a Scala Buffer. " +
      "(Why this obscure class? It’s hard to find uses of java.util.List in the standard Java library.)"
  )
  val flavors: SystemFlavorMap = SystemFlavorMap.getDefaultFlavorMap.asInstanceOf[SystemFlavorMap]
  val flavorBuffer: mutable.Buffer[String] = JavaConverters.asScalaBufferConverter(
    flavors.getNativesForFlavor(DataFlavor.imageFlavor)).asScala
  println(s"First four flavors: ${flavorBuffer.take(4)}")

}
