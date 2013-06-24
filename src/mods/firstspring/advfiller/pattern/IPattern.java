package mods.firstspring.advfiller.pattern;

public interface IPattern
{
	public void init();
	
	public void work();
	
	public String getName();
	
	public int getId();
	
	public void setId(int id);

}
