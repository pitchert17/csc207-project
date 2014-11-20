package taojava;

/**
 * The Schedule Interface.
 * @author Ameer Shujjah
 * @author Tommy Pitcher
 * @author Yazan Kittaneh
 */
public interface Schedule
{

  /**
   * Extract information from the school input file
   * @param, schools, a String corresponding to the file name with school data
   * @param, location, a String corresponding to the distances between schools
   * @pre
   *   schools is a valid file name
   */
  public void schoolsInput(String schools, String location);
  
  /**
   * Uses the information extracted in fileInput to create a schedule
   */
  public void algorithm();

  /**
   * Prints the schedule created by algorithm()
   */
  public void output();

}// interface Schedule
