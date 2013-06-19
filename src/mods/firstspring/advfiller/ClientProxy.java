package mods.firstspring.advfiller;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{

	@Override
	World getWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	void openGui(int x, int y, int z, int left, int right, int up, int down, int forward, int type, boolean loop, boolean iterate, boolean drop)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiScreenAdvFiller(new Position(x, y, z), left, right, up, down, forward, type, loop, iterate, drop));
	}

	@Override
	boolean isServer()
	{
		return false;
	}

	@Override
	void registerRenderer()
	{
		//なんとなく
		ClientRegistry.bindTileEntitySpecialRenderer(TileAdvFiller.class,new RenderAdvFiller());
	}
}
