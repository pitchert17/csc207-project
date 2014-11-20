package taojava;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class IO
{

  public static void date(String str, int priority, ArrayList<Dates> dates)
  {
    // System.out.println("String = " + str);
    // Initialize variables to split the string into day,month and year
    int[] date = new int[3];
    char tmp;
    String buffer = "";
    int iterator = 0;
    for (int i = 0; i < str.length(); i++)
      {
        tmp = str.charAt(i);
        if (tmp == '/')
          {
            date[iterator] = Integer.parseInt(buffer);
            buffer = "";
            iterator++;
          } // if, a '/' then empty the buffer
        else if (iterator == 2)
          {
            date[iterator] = Integer.parseInt(str.substring(i));
            break;
          }// else if, add the year
        else
          {
            buffer += tmp;
          } // else, add to the buffer
      }// for, all the characters in the string

    //  System.out.println("| Day = " + date[0] + "| Month = " + date[1]
    //                  + "| Year = " + date[2] + "| Priority = " + priority);
    dates.add(new Dates(date[0], date[1], date[2], priority));
  } //date(String, int)

  public static void main(String args[])
    throws Exception
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    //An Array of Schools
    School[] colleges = new School[10];
    ArrayList<Dates> dateTmp = new ArrayList<Dates>();
    //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    //Date d = sdf.parse("12-12-2012");
    //System.out.println(d.toString());
    //Using a Scanner to read the file
    FileInputStream fis = new FileInputStream("data.txt");
    Scanner scanner = new Scanner(fis);

    //reading file line by line using Scanner in Java
    System.out.println("Reading file line by line in Java using Scanner");
    String College = "";
    String Initials = "";
    String line;
    int iterator = 0;
    while (scanner.hasNextLine())
      {
        line = scanner.nextLine();
        Scanner linebreaker;
        //Update the College Name
        if (line.contains("College:"))
          {
            College = line.substring(9);
            colleges[iterator] = new School(College);
          }// if College name
        if (line.contains("Initials:"))
          {
            Initials = line.substring(9);
            colleges[iterator].setInitials(Initials);
          }// if College's Initials
        //Add all the dates
        else if (line.contains("Available:"))
          {
            //Break the line into another Scanner Object.
            linebreaker = new Scanner(line.substring(10));
            // System.out.println("Printing all the Available dates:");
            while (linebreaker.hasNext())
              {
                //Split the date into day, month and year
                date(linebreaker.next(), 0, dateTmp);
              }//while, there are more dates
          } // else if, for available dates 
        else if (line.contains("Must:"))
          {
            linebreaker = new Scanner(line.substring(5));
            //System.out.println("Printing all Priority Dates:");
            while (linebreaker.hasNext())
              {
                //Split the date into it day, month and year
                date(linebreaker.next(), 1, dateTmp);
              }//while, there are more dates
          } // else if, for must dates
        else if (line.contains("Cant:"))
          {
            linebreaker = new Scanner(line.substring(5));
            //System.out.println("Printing all UnAvailable Dates:");
            while (linebreaker.hasNext())
              {
                date(linebreaker.next(), 2, dateTmp);
              }//while, there are more dates
          } // else if, for all the unavailable dates 
        else if (line.compareTo("//End") == 0)
          {
            //System.out.println("Now Update our objects with the new Information.");
            colleges[iterator].setDates((ArrayList<Dates>) dateTmp.clone());
            dateTmp.clear();
            colleges[iterator].print(pen);
            pen.println();
            iterator++;
          }// else if, the end of a College
        //System.out.println(line);
      }// while, there are unseen lines in the data 
    //System.out.println("College = " + College);

    scanner.close();

    FileInputStream fil = new FileInputStream("distance.txt");
    scanner = new Scanner(fil);

    String[] collegeInitials = new String[10];
    line = scanner.nextLine();
    iterator = 0;
    Scanner linebreaker = new Scanner(line);
    while (linebreaker.hasNext())
      {
        collegeInitials[iterator] = linebreaker.next();
        iterator++;
      }// while
iterator = 0; 
    while (scanner.hasNextLine())
      {

      }// while

  }// Main
} //Class IO
