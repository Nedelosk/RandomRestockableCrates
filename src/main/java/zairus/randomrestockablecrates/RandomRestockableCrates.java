package zairus.randomrestockablecrates;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import zairus.randomrestockablecrates.block.RRCBlocks;
import zairus.randomrestockablecrates.event.RRCEventHandler;
import zairus.randomrestockablecrates.gui.GuiHandler;
import zairus.randomrestockablecrates.proxy.CommonProxy;
import zairus.randomrestockablecrates.sound.RRCSoundEvents;

@Mod(modid = RRCConstants.MODID, name = RRCConstants.NAME, version = RRCConstants.VERSION)
public class RandomRestockableCrates {
	public static Logger logger;
	
	@SidedProxy(clientSide = RRCConstants.MOD_CLIENT_PROXY, serverSide = RRCConstants.MOD_COMMON_PROXY)
	public static CommonProxy proxy;
	
	@Mod.Instance(RRCConstants.MODID)
	public static RandomRestockableCrates instance;
	
	public static CreativeTabs tabCrates = new CreativeTabs("RandomCrates") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(RRCBlocks.crate);
		}
	};
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		
		RRCConfig.init(event.getSuggestedConfigurationFile());
		
		RRCBlocks.init();
		
		RandomRestockableCrates.proxy.preInit(event);
		RRCBlocks.initModels();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		RRCEventHandler eventHandler = new RRCEventHandler();
		
		RandomRestockableCrates.proxy.init(event);
		
		MinecraftForge.EVENT_BUS.register(eventHandler);
		MinecraftForge.TERRAIN_GEN_BUS.register(eventHandler);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(RandomRestockableCrates.instance, new GuiHandler());
		RRCSoundEvents.registerSounds();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
}
