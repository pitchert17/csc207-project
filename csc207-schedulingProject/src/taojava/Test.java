package taojava;

public class Test
{

  public static void main(String[] args)
  {
    Dates test = new Dates(3,6,15,1);
    Dates test2 = new Dates(3,7,18,1);
    System.out.println(" yeah = " + test.compareTo(test2));
    test.getDay();
  }//main(String[])

}// Test
