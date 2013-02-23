package polarstar.advfiller;

import buildcraft.api.core.Position;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	World getWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	void loadTexture() {
		MinecraftForgeClient.preloadTexture("/polarstar/advfiller/sprite/advfiller.png");
	}

	@Override
	void openGui(int x, int y, int z, int left, int right, int up, int down,
			int forward, int type, boolean loop, boolean iterate, boolean drop) {
		Minecraft.getMinecraft().displayGuiScreen(new GuiScreenAdvFiller(new Position(x,y,z), left, right, up, down, forward, type, loop, iterate, drop));
	}
	
	@Override
	boolean isServer(){
		return false;
	}


}
