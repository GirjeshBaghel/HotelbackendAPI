package com.entity;

public class BookingCustomData {

	
	private Booking booking;
	private Customer customer;
	private Room room;
	
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public BookingCustomData(Booking booking, Customer customer, Room room) {
		super();
		this.booking = booking;
		this.customer = customer;
		this.room = room;
	}
	public BookingCustomData() {
		super();
		// TODO Auto-generated constructor stub
	}
		
}
