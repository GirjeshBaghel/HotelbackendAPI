package com.controller;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.BookingRepository;
import com.dao.CustomerRepository;
import com.dao.RoomRepository;
import com.entity.Booking;
import com.entity.BookingCustomData;
import com.entity.BookingRequest;
import com.entity.Customer;
import com.entity.Room;
import com.service.BookingService;

@CrossOrigin
@RequestMapping("/booking")
@RestController
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	 private CustomerRepository cusRepository;
	
	@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')" )
	@PostMapping("/updatebooking/{bookingId}")
	public ResponseEntity<String> updateBooking(@PathVariable("bookingId") Long bookingId,
	                                            @RequestBody Booking updatedBooking) {
	    return bookingService.updateBooking(bookingId, updatedBooking);
	}
 	 
	@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')" )
		  @PostMapping("/accept/{bookingId}")
	    public ResponseEntity<String> acceptBooking(@PathVariable Long bookingId) {
	        bookingService.acceptBooking(bookingId);
	        String message = "Booking accepted";
	        return ResponseEntity.ok(message);
	    }
	 
	  // this is using for sending a request
	
		@PostMapping("/request/{roomNumber}")
	  public ResponseEntity<String> handleBookingRequest(@PathVariable("roomNumber") int roomNumber, @RequestBody Customer customer) {
	      bookingService.bookingRequest(roomNumber, customer);
	      String message = "Request Sent Successfully";
	      return ResponseEntity.ok(message);
	  }
	 
	  // this method is used to search all the pending request
		@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
		@GetMapping("/pendingBooking")
	    public ResponseEntity<List<Booking>> getPendingBookings() {
	        List<Booking> pendingBookings = bookingService.getPendingBookings();
	        //System.out.println("Pending Data "+pendingBookings);
	        return new ResponseEntity<>(pendingBookings, HttpStatus.OK);
	    }
		@GetMapping("/confirmBooking")
	    public ResponseEntity<List<Booking>> getConfirmBookings() {
	        List<Booking> confirmBookings = bookingService.getConfirmBookings();
	       // System.out.println("Confrim Booking "+confirmBookings);
	        return new ResponseEntity<>(confirmBookings, HttpStatus.OK);
	    }
		
	 
		@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
	 @PostMapping("/createBooking")
	public ResponseEntity<String> bookingCreate(@RequestBody BookingRequest bookingRequest )
	{
		 	Booking booking = bookingRequest.getBooking();
		    Customer customer = bookingRequest.getCustomer();
		    bookingService.createBooking(booking, customer);
		    return ResponseEntity.ok("Booking Successfully");
	}
		
//		

//	    @PostMapping("/createBooking")
//	    public String createBooking(@RequestParam String cusName, @RequestParam String cusEmail,@RequestParam String bookingType 
//	    		,@RequestParam int roomNo,@RequestParam String cusPhone,@RequestParam String address
//	    		,@RequestParam String city,@RequestParam String roomType,@RequestParam int totalguest,@RequestParam int adults,
//	    		@RequestParam int children,@RequestParam String decumentId,@RequestParam boolean Active ,@ModelAttribute("booking") Booking booking)  {
//	        // Create a new Booking object with the form data
////	        Booking booking = new Booking();
//	        Customer customer = new Customer();
//	        Room room = new Room();
//	        //,@RequestParam String  checkInDateTime ,@RequestParam String  checkOutDateTime,
//	        customer.setCusName(cusName);
//	        booking.setCusName(cusName);
//	        customer.setCusEmail(cusEmail);
//	        booking.setCusEmail(cusEmail);
//	        customer.setCusPhone(cusPhone);
//	        customer.setAddress(address);
//	        room.setRoomNo(roomNo);
//	        booking.setRoomNo(roomNo);
//	        customer.setCity(city);
//	        customer.setRoomType(roomType);
//	        customer.setTotalguest(totalguest);
//	        customer.setAdults(adults);
//	        customer.setChildren(children);
//	        customer.setDecumentId(decumentId);
//	        customer.setActive(Active);
//	        System.out.println(cusName);
//	       
//	        System.out.println(cusEmail);
//	       
//	        booking.setBookingType(bookingType);
//
//	        cusRepository.save(customer);
//	        // Pass the Booking object to the service for further processing
//	        bookingRepository.save(booking);
//	        System.out.println(booking);
//	        
//	        return "bookingConfirmation"; // Return the HTML template name (bookingConfirmation.html)
//	    }

	 
	 	@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
		@GetMapping("/getAllBooking")
		public List<Booking> getAllBooking(){
			
			return bookingService.checkAllBooking();
		}
		@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')" )
		@DeleteMapping("/deleteAllBooking")
		public String deletebooking()
		{
			cusRepository.deleteAll();
			return "Successfully Deleted";
		}
		
	  

}

