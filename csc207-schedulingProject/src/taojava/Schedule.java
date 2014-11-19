package taojava;

public interface Schedule
{

  public void fileInput();
  
  public void algorithm();
  
  public void output();
   
  
  
  /*
   * 
   * //Pseudo Code for Algorithm
   * 
   * 
      History cal = new Calander();
      int setPriority =1;
      
      for(int dateIndex; dateIndex < dates.length(); dateIndex++)
        {
          if(matchCounter <= 5)
            {
             for(int schoolIndex=0; schoolIndex < schools.length(); schoolIndex++)
                {
                  if(hasPlayed(schools[schoolIndex])!= 0 &&
                     schools[schoolIndex].dates[dateIndex].priority = setPriority) //  if the school's priority is at 'must'
                    {
                      for(int i=schoolIndex; i < schools.length(); i++)
                         {
                           if(hasPlayed(schools[schoolIndex])!= 0 &&
                             schools[i].dates[dateIndex].priority = setPriority)
                             {
                               setMatch(schools[schoolIndex], schools[i], dates[dateIndex]); //sets a match, taking in two schools and a date
                             } // if the second school has not played, and the date priority = setPriority
                         }// for: a second available opponent
                    }// if the school has not played, and the date is at priority = setPriority
                  setPriority = 2;
                }//For: a first available school
            }//if: number of matches scheduled for each date is less than or equal to 5
        }
   * 
   * 
   * 
   */
}// itnerface Schedule
