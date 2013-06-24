package mods.firstspring.advfiller.pattern;

import mods.firstspring.advfiller.TileAdvFiller;

public interface IPattern
{
	public void init();
	
	public void work();
	
	public String getName();
	
	public int getId();
	
	public IPattern setId(int id);
	
	public IPattern setMachine(TileAdvFiller tile);
}
