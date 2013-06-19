package mods.firstspring.advfiller;

import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class AdvFillerInitializeThread implements Runnable
{

	TileAdvFiller tile;

	public AdvFillerInitializeThread(TileAdvFiller tile)
	{
		this.tile = tile;
	}

	@Override
	public void run()
	{
		tile.finished = false;
		if (!tile.doLoop)
		{
			ForgeChunkManager.releaseTicket(tile.chunkTicket);
			tile.chunkTicket = ForgeChunkManager.requestTicket(AdvFiller.instance, tile.worldObj, Type.NORMAL);
			tile.setLoadingChunks();
		}
		tile.init();
		tile.setEnable();
		tile.doLoop = false;
	}
}
