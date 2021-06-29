package test2;

public class Room {
	private Long roomId;
	private String roomNumber;
	private int capacity;

	/**Initialize new Room */
	public Room(Long roomId2, String roomNumber, int capacity) {
		this.roomId = roomId2;
		this.roomNumber = roomNumber;
		this.capacity = capacity;
	}

	
	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getRoomId() {
		return this.roomId;
	}

	
	public String getRoomNumber() {
		return this.roomNumber;
	}

	public int getRoomCapacity() {
		return this.capacity;
	}
	
	public Long setRoomId(Long roomId) {
		return this.roomId = roomId;
	}
	public String setRoomNumber(String roomnum) {
		return this.roomNumber = roomnum;
	}

}