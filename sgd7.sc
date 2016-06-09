import scala.collection.mutable.ListBuffer

object sgd7 {
  println("Welcome to the Scala worksheet")
  
  def mulElementsOfList(list: List[Int]): List[Int] = {
    list.filter(x => x % 2 == 0).map(x => x * 3)
  }
  
  
  def isPrime(start: Int): Boolean =
    (start > 1) && (Range(2, Math.sqrt(start).toInt + 1) forall { start % _ != 0 })
    
  def findPrimeinDiap(a: Int, b: Int): List[Int] = {
    val listochek = for (i <- a to b if isPrime(i)) yield i
    listochek.toList
  }
  
   def del(n: Int, list: List[Int]): List[Int] = {
    list.filter { x => x != n } }
  
  class stuhg(name: String) {
  private var ar: Int = 1
  private var Str = new String()
  
  def this (name: String,ar: Int){ 
  this(name)
  this.ar = ar
  }
  
  def this (name: Strring, str :String){ 
  this(name)
  this.a = ar
  }
  
  def
  }
    
    
    
    
    
  
 
}