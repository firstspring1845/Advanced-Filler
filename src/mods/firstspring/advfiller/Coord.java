package mods.firstspring.advfiller;

public class Coord {
	private int x,z;
		
	
	private Coord(){}
	
	public Coord(int x, int z){
		this.x = x;
		this.z = z;
	}

	@Override
	public boolean equals(Object arg0) {
		if(!(arg0 instanceof Coord))
			return false;
		return ((Coord)arg0).x == x && ((Coord)arg0).z == z;
	}

	@Override
	public int hashCode() {
		return x + (z << 4);
	}
}
