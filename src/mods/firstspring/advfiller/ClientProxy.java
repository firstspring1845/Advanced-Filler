package mods.firstspring.advfiller;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import buildcraft.api.core.Position;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	World getWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
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
	
	@Override
	void registerRenderer(){
		//TileEntitySpecialRendererは範囲外描画をしてくれないのでEntity使用
		//描画用エンティティ（クライアント専用）とそのレンダー登録
		//全体的にスポーンチェッカーとTNTのお世話に
		RenderingRegistry.registerEntityRenderingHandler(EntityRendererFiller.class, new RenderAdvFiller());
	}


}
