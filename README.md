# Low Level System Design - Ticket Booking Application Like BookMyShow, TicketMaster, etc. 

### Problem Statement
[Check here](problem-statement.md)

### Video Explanation
[https://www.youtube.com/playlist?list=PL564gOx0bCLpAL7yMJqOuK3_hBuLkyRhn](https://www.youtube.com/playlist?list=PL564gOx0bCLpAL7yMJqOuK3_hBuLkyRhn)

### Further Improvements
* Seat type: Silver, Gold, Diamond
    * Different pricing for different types of seats.
* Validating payment in payment success flow. 
* Checking if show creation is allowed. 
    * This mainly include implementing method `checkIfShowCreationAllowed` in `ShowService`.
* Handling different types of movies like 3D, 7D, etc.
