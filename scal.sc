class n {
  def Nok(n: Int,m: Int, no: Int): Int = {
  
  while( no % n == 0 && no % m == 0){
    no+1
  }
  return no
  } 
}

