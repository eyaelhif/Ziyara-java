package org.example;

import java.time.LocalDateTime;
import entities.Guide;
import entities.Pack;
import entities.ReservationPack;
import entities.Transport;
import entities.User;
import java.util.Date;
import java.util.List;
import services.PackService;
import services.ReservationPackService;
public class Main {
    public static void main(String[] args) {

        PackService packService = new PackService();
        ReservationPackService reservationPackService = new ReservationPackService();

        // Creating a new Transport object
        Transport transport = new Transport();
        transport.setId(6L);

        // Creating a new Guide object
        Guide guide = new Guide();
        guide.setId(6L); // Assuming you have an existing guide with ID 1

        // Creating a new Pack object
        Pack newPack = new Pack();
        newPack.setTitrePack("Test Pack");
        newPack.setDescriptionPack("This is a test pack");
        newPack.setPrixPack(100);
        newPack.setImagePack("test.jpg");

// Setting the departure date to a specific date
        String strDeparture = "2022-08-10"; // Format: "yyyy-MM-dd"
        java.sql.Date departureDate = java.sql.Date.valueOf(strDeparture);
        newPack.setDateDepartPack(departureDate);

// Setting the arrival date to a specific date
        String strArrival = "2022-06-10"; // Format: "yyyy-MM-dd"
        java.sql.Date arrivalDate = java.sql.Date.valueOf(strArrival);
        newPack.setDateArrivePack(arrivalDate);

        newPack.setNbvue(0);
        newPack.setTransports(transport); // Set the transport
        newPack.setGuide(guide); // Set the guide


// Creating the pack in the database
       //packService.create(newPack);


        // Example: Getting a pack by ID
        /*Pack pack = packService.getById(14);
        if (pack != null) {
            pack.setTitrePack("Discover Tunisia");
            pack.setDescriptionPack("Welcome to Tunisia");
            packService.update(pack);
        } else {
            System.out.println("Pack n existe  " );
        }*/
// Example: Updating a pack




        // Test delete method
       //packService.delete(14);

      /*  List<Pack> allPacks = packService.getAll();
    for (Pack pack : allPacks) {
        System.out.println(pack); // This will print each pack retrieved by the getAll method
    }*/

/**********************************crud reservation*********************************************************/
        Pack packkk = packService.getById(3);


        User user = new User();
        user.setId(1L); // Example: Set user ID
        user.setEmail("onlyrayen@gmail.com"); // Example: Set user email
        // Similarly, set other properties as needed

        // Create a new ReservationPack instance with necessary parameters
        ReservationPack newReservationPack = new ReservationPack();
        newReservationPack.setDateReservationPack(new Date()); // Set the reservation date
        newReservationPack.setNbrParticipantPack(10); // Set the number of participants
        newReservationPack.setPack(packkk); // Set the pack object
        newReservationPack.setUser(user); // Set the user object
        newReservationPack.setLikes(11); // Set initial likes count
        newReservationPack.setDislikes(15); // Set initial dislikes count

        // Add the new ReservationPack
          //reservationPackService.create(newReservationPack);

        int reservationPackId = 14; // Assuming the ID is known

// Retrieve the reservation pack by ID
        ReservationPack retrievedReservationPack = reservationPackService.getById(reservationPackId);

//Affichage Reservation


/*if (retrievedReservationPack != null) {
  System.out.println("Retrieved Reservation Pack: " + retrievedReservationPack);
} else {
    System.out.println("No reservation pack found with ID: " + reservationPackId);
}*/

//UPDATE RESERVATION


   /*if (retrievedReservationPack != null) {
            retrievedReservationPack.setLikes(111); // Set updated likes count
        reservationPackService.update(retrievedReservationPack);
      } else {
      System.out.println("No reservation pack found with ID: " + reservationPackId);
        }*/


//DELETE
        //reservationPackService.delete(14);





//
        int limit = 2; // You can adjust this limit as needed
//
//        // Retrieve the top reserved packs with likes
     // List<Pack> topReservedPacks = reservationPackService.getTopReservedPacksWithLikes(limit);
//
//        // Print the titles of the top reserved packs along with their total likes
       // System.out.println("Top Reserved Packs:");
      // for (Pack topReservedPack : topReservedPacks) {
       //     System.out.println("Titre du pack: " + topReservedPack.getTitrePack());
          //  System.out.println(); // Add a newline for separation
        //     }
//



       /* List<Pack> mostLikedPacks = packService.getMostLikedPacks();

        System.out.println("Most Liked Packs:");
        for (Pack packK : mostLikedPacks) {
            System.out.println("Title: " + packK.getTitrePack());

        }*/

    }
}
