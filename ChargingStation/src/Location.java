//Location.java
public class Location {
	
	private boolean occupied;

	public Location() {
		this.occupied = false;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void occupy() {
		occupied = true;
	}

	public void release() {
		occupied = false;
	}
	
}
