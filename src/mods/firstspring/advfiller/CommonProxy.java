package mods.firstspring.advfiller;

import net.minecraft.world.World;
import cpw.mods.fml.common.SidedProxy;

public class CommonProxy
{
	// これが無いとサーバーが起動しないんでマルチで使えないんです
	@SidedProxy(clientSide = "mods.firstspring.advfiller.ClientProxy", serverSide = "mods.firstspring.advfiller.CommonProxy")
	public static CommonProxy proxy;

	World getWorld()
	{
		return null;
	}

	void openGui(int x, int y, int z, int left, int right, int up, int down, int forward, int type, boolean loop, boolean iterate, boolean drop)
	{
		//何もしない
	}

	boolean isServer()
	{
		return true;
	}

	void registerRenderer()
	{

	}
}
