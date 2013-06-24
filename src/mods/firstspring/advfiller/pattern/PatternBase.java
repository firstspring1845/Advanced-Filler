package mods.firstspring.advfiller.pattern;

import mods.firstspring.advfiller.TileAdvFiller;

public abstract class PatternBase implements IPattern
{
	int id;
	TileAdvFiller tile;

	@Override
	public abstract void init();

	@Override
	public abstract void work();

	@Override
	public abstract String getName();

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public IPattern setId(int id)
	{
		this.id = id;
		return this;
	}

	@Override
	public IPattern setMachine(TileAdvFiller tile) {
		this.tile = tile;
		return this;
	}
}
