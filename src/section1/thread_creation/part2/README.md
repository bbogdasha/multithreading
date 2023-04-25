In this example, the work with threads was demonstrated. 

Two hacker thread classes `AscendingHackerThread` and `DescendingHackerThread` are inherited from the abstract class `HackerThread`, 
which try to guess the password to `Vault` class in parallel in a brute force way. 

This is given 10 seconds until the class `PoliceThread`, also in parallel, finishes the countdown and catches the intruders.
