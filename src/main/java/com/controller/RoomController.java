package com.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Exception.ResourceNotFoundException;
import com.dao.CustomerRepository;
import com.dao.RoomRepository;
import com.entity.Booking;
import com.entity.Customer;
import com.entity.Room;
import com.google.common.base.Optional;
import com.service.RoomService;
@CrossOrigin
@RestController
@RequestMapping("/room")
public class RoomController {
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
//	@Autowired
//	public CustomerService customerService;
	
	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllRoom")
	public List<Room> getAllRoom(){
		
		return roomService.getAllRoom();
	}
	
	//@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
//	@DeleteMapping("/deleteRoomByRoomNo/{id}")
//	public String deleteRoom(@PathVariable("id") int room_no) {
//		
//		return roomService.deleteRoom(room_no);
//	}
	//@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
	@PostMapping("/deleteRoomByRoomNo/{id}")
	public ResponseEntity<JSONObject> deleteRoom(@PathVariable("id") int roomNo) {
	    // Check if the room with the given room number exists in the database
	    Room existingRoom = roomRepository.findByRoomNo(roomNo);
	    JSONObject response = new JSONObject();
	       if (existingRoom != null) {
	        roomRepository.delete(existingRoom);
	        System.out.println("Room Deleted");
	        
	        response.put("message", "Room with room number " + roomNo + " has been deleted successfully.");
	        response.put("status", HttpStatus.OK.value());

	        return ResponseEntity.ok(response);
	    } else {
	        // Room does not exist, return an error message
	        response.put("message", "Room with room number " + roomNo + " does not exist.");
	        response.put("status", HttpStatus.NOT_FOUND.value());

	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	}

	
	//@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
	@PostMapping("/createRoom")
	ResponseEntity<Room> createRoom(@RequestBody Room room){
		
		return new ResponseEntity<Room>(roomService.saveRoom(room),HttpStatus.CREATED);
	}
	//@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
//	 @PostMapping("/createRoom")
//	    public String createRoom(@ModelAttribute("room") Room room, Model model) {
//		 
//	
//		    if (roomRepository.findByRoomNo(room.getRoomNo()) != null) {
//		        model.addAttribute("message", "This Room  is already Saved.");
//		        System.out.println("This Room is Already Register");
//		        return "This Room is Already Register"; // Return to an error page with the error message
//		    }
//
//	        roomRepository.save(room);
//	        System.out.println(room);
//	        
//	        return "Success" ;
//	    }
	
	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/availableRoom")
    public ResponseEntity<List<Room>> getPendingRoom() {
        List<Room> pendingRoom = roomRepository.findpendingRoom();
        //System.out.println("Pending Data "+pendingBookings);
        return new ResponseEntity<>(pendingRoom, HttpStatus.OK);
    }
	
	//@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
	@GetMapping("/bookedRoom")
    public ResponseEntity<List<Room>> getBookedRoom() {
        List<Room> bookedRoom = roomRepository.findbookingRoom();
        //System.out.println("Pending Data "+pendingBookings);
        return new ResponseEntity<>(bookedRoom, HttpStatus.OK);
    }
	
	
	
	//@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
	@GetMapping("/getByRoomNo/{roomNo}")
	public ResponseEntity<?> getByRoomNo(@PathVariable("roomNo") int roomNo) {
	    Room rooms = roomRepository.findByRoomNo(roomNo);
	    if (rooms == null) {
	        String errorMessage = "No rooms available with the specified room number.";
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	    } else {
	        return ResponseEntity.ok(rooms);
	    }
	}
	
	 @PutMapping("/updateRoomByRoomNo/{id}")
	    public String updateRoom(@PathVariable("id") int roomNo, @RequestBody Room room) {
	        Room existingRoom = roomRepository.findById(roomNo)
	                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomNo));

	        // Update the existing room with the new data
	        existingRoom.setBed(room.getBed());
	        existingRoom.setHasGizer(room.isHasGizer());
	        existingRoom.setHasTV(room.isHasTV());
	        existingRoom.setHasPhone(room.isHasPhone());
	        existingRoom.setHasWIFI(room.isHasWIFI());
	        existingRoom.setHasAC(room.isHasAC());
	        existingRoom.setPricePerDay(room.getPricePerDay());

	        // Save the updated room
	        roomRepository.save(existingRoom);
	        return "Updated";
	    }

	 @GetMapping("/getAllCustomer")
	 public List<Customer> getAllCustomer(){
			
			return customerRepository.findAll();
		}
	
}
