package com.vrv.ml.spark

import com.vrv.ml.WordCount
import org.apache.spark.{HashPartitioner, RangePartitioner, SparkConf, SparkContext}
import org.junit.Assert.assertTrue
import org.junit._

@Test
class sparktest {

  val conf = new SparkConf().setAppName("SparkTest").setMaster("local[2]")
  val sc = new SparkContext(conf)

  @Test
  def testOK() = assertTrue(true)

  @Test
  def spark1 = {
    //  val data = Array(1, 2, 3, 4, 5)
    //  val distData = sc.parallelize(data)
    val lines = sc.textFile("src/main/scala/com/vrv/resources/data.txt")
    //  lines.foreach(println)
    //  lines.collect().foreach(println)
    lines.take(5).foreach(println)
    val lineLengths = lines.map(s => s.length).persist()
    //  val rdd = lineLengths.collect()
    //  lineLengths.foreach(println)
    lineLengths.collect().foreach(println)
    lineLengths.take(5).foreach(println)

    val totalLength = lineLengths.reduce((a, b) => a + b)
    println(totalLength)
  }

  @Test
  def spark2 = {
    val tuple = (1, "2")
    println(tuple.toString())
    //Creates RDD from a textfile
    val lines = sc.textFile("src/main/scala/com/vrv/resources/data.txt")
    //Actions
    lines.collect().foreach(println)
    //Transformations
    val pairs = lines.map(s => (s, 1))
    //Actions
    pairs.collect().foreach(println)
    //Transformations
    val counts = pairs.reduceByKey((a, b) => a + b)
    //Actions
    counts.collect().foreach(println)
    //Transformations
    val counts_1 = counts.sortByKey()
    //Transformations
    val counts_2 = counts_1.filter(t => t._2 > 1)
    //Actions
    counts_2.saveAsTextFile("output")
  }

  object Utils {
    def sumOfEveryPartition(input: Iterator[Int]): Int = {
      var total = 0
      input.foreach(elem => total += elem)
      total
    }
  }

  @Test
  def spark3 = {
    val rdd = sc.parallelize(List(1, 2, 3, 4, 51, 61), 2)
    //语法1
    rdd.map(a => {
      a + 1
    }).collect().foreach(println)
    //语法2，“_”代表迭代中的RDD的当前元素
    rdd.map({
      _.toString + "~"
    }).collect().foreach(println)
    //语法3
    rdd.map {
      _.toString + "~~"
    }.collect().foreach(println)

  }

  @Test
  def spark5 = {
    val rdd = sc.parallelize(List(1, 2, 3, 4, 51, 61), 2)
    val rddOther = sc.parallelize(List(11, 21, 31, 41, 51, 61), 3)
    //Union
    var result = rdd.union(rddOther)
    result.partitions.foreach(partition => println(partition.index))
    //Intersection
    result = rdd.intersection(rddOther)
    result.partitions.foreach(partition => println(partition.index))
    //Distinct
    rdd.distinct().collect().foreach(println)
    result = rdd.intersection(rddOther).mapPartitions(elms => {
      var total = 0
      elms.foreach(elem => total += elem)
      Iterator(total)
    }) //partition是传入的参数，是个list，要求返回也是list，即Iterator(sumOfEveryPartition(partition))
    result.collect.foreach(println)
  }

  @Test
  def spark6 = {
    val rdd = sc.parallelize(List(("A", 1), ("B", 2), ("A", 3), ("B", 5), ("C", 6), ("B", 1)), 2)
    //    //groupByKey
    //    rdd.groupByKey().collect().foreach(println)
    //    //groupByKey
    //    rdd.reduceByKey((a, b) => a + b).collect().foreach(println)
    //aggregateByKey
    val rdd1 = sc.parallelize(List(1, 2, 3, 4, 5, 6), 2)
    println(rdd1.partitions.length)
    println(rdd1.aggregate(0)((a, b) => a + b, (a, b) => a + b))
    rdd.aggregateByKey(1)((a, b) => a + b, (a, b) => a + b).collect().foreach(println)
    //    rdd.aggregateByKey("1")((a, b) => a+"~"+b,(a, b) => a+":"+b).collect().foreach(println)
    //    rdd.aggregateByKey("1")((a, b) => a+b,(a, b) => a+b).collect().foreach(println)
  }

  @Test
  def spark7 = {
    val rdd = sc.parallelize(List(("A", 1), ("B", 2), ("A", 3), ("B", 5), ("C", 6), ("B", 1), ("D", 1)), 2)
    val rdd1 = sc.parallelize(List(("A", 11), ("B", 21), ("A", 31), ("B", 51), ("C", 61), ("B", 11), ("F", 11)), 2)
    val rdd3 = sc.parallelize(List(1, 2, 3, 4, 5, 6), 2)
    val rdd5 = sc.parallelize(List("A", "B", "C", "D", "E", "F"), 2)
    //    rdd.sortByKey().collect().foreach(println)
    //    rdd.fullOuterJoin(rdd1).collect().foreach(println)
    //    rdd.cogroup(rdd1).collect().foreach(println)
    //    rdd.cartesian(rdd1).collect().foreach(println)
    //    println(rdd.partitions.length)
    //    println(rdd.coalesce(1).partitions.length)
    //    rdd5.collect().foreach(println)
    //    rdd5.repartition(1).collect().foreach(println)
    //    rdd5.repartition(2).collect().foreach(println)
    //    rdd5.collect().foreach(println)
    //    rdd5.coalesce(1).collect().foreach(println)
    //    rdd5.coalesce(2, false).collect().foreach(println)
    //    rdd5.coalesce(2, true).collect().foreach(println)
    rdd.repartitionAndSortWithinPartitions(new HashPartitioner(2)).collect().foreach(println)
  }

  @Test
  def spark8 = {
    val rdd = sc.parallelize(List(8, 7, 1, 2, 3, 4, 5, 6), 1)
    val rdd1 = sc.parallelize(List(("A", 3), ("B", 2), ("A", 1), ("B", 5), ("C", 6), ("B", 1), ("D", 1)), 1)
    val rdd2 = sc.parallelize(List(("A", "a1"), ("B", "b1"), ("A", "a2"), ("B", "b2"), ("C", "c1"), ("B", "b3"), ("D", "d1")), 2)
    //    println(rdd.reduce((a, b) => a + b))
    //    println(rdd.count())
    //    println(rdd.first())
    //    rdd.sample(true, 3, 2).collect().foreach(println)
    //    rdd.takeSample(false, 10, 2).foreach(println)
    //    rdd.takeOrdered(3).foreach(println)
    //    rdd.saveAsTextFile("output")
    //    rdd1.saveAsSequenceFile("output")
    //    rdd.saveAsObjectFile("output")
    //    val rdd2 = sc.objectFile("output/part-00000")
    //    rdd2.collect().foreach(println)
    //    rdd.foreach(println)
    //    val list = List(1, 2, 3, 4, 5, 6)
    //    println(list.map("1" + _))
    //    rdd2.reduceByKey((a,b) => a+b).saveAsTextFile("output")
    //    rdd1.sortBy(a => a).collect().foreach(println)
    //    rdd1.sortByKey().collect().foreach(println)
    //    val a = (1,3) + "1"
    //    println(a)

  }

  @Test
  def spark9 = {
    //    val broadcastVar = sc.broadcast(Array(1, 2, 3))
    //    broadcastVar.value.foreach(println)
    val rdd = sc.parallelize(Array(1, 2, 3, 4, 5, 6))
    val accum = sc.accumulator(0, "My Accumulator")
    rdd.foreach(x => accum.add(x))
    rdd.foreach(x => accum += x)
    rdd.map(x => accum += x).take(2)
    println(accum.value)
  }

}
