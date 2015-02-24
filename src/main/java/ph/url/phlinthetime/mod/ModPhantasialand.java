package ph.url.phlinthetime.mod;

import ph.url.phlinthetime.mod.asm.MethodInvokerClass;
import ph.url.phlinthetime.mod.block.BlockKasse;
import ph.url.phlinthetime.mod.block.BlockTicketsystem;
import ph.url.phlinthetime.mod.block.BlockTrack;
import ph.url.phlinthetime.mod.entity.EntityBlackMamba;
import ph.url.phlinthetime.mod.gui.GuiHandlerPhl;
import ph.url.phlinthetime.mod.items.ItemBlackMamba;
import ph.url.phlinthetime.mod.items.ItemChurros;
import ph.url.phlinthetime.mod.items.ItemCreditCard;
import ph.url.phlinthetime.mod.items.ItemRotator;
import ph.url.phlinthetime.mod.items.ItemTicket;
import ph.url.phlinthetime.mod.money.MoneyManager;
import ph.url.phlinthetime.mod.networking.NetworkHandler;
import ph.url.phlinthetime.mod.proxy.CommonProxy;
import ph.url.phlinthetime.mod.tileentity.TileEntityKasse;
import ph.url.phlinthetime.mod.tileentity.TileEntityTicketsystem;
import ph.url.phlinthetime.mod.tileentity.TileEntityTrack;
import net.minecraft.block.Block;
import net.minecraft.command.ServerCommand;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = ModPhantasialand.MODID, name = ModPhantasialand.MODNAME, version="0.1.0 Alpha")
public class ModPhantasialand {

	public static final String MODID = "phlmod";
	public static final String MODNAME = "Phantasialand-Mod";
	
	public static Block blockTrack;
	public static Block blockKasse;
	public static Block blockTicketsystem;
	public static Item itemTicket;
	public static Item itemRotator;
	public static Item itemCreditCard;
	public static Item itemChurros;
	public static Item itemBlackMamba;

	@SidedProxy(clientSide = "ph.url.phlinthetime.mod.proxy.ClientProxy", serverSide = "ph.url.phlinthetime.mod.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabsPhl tabPhl = null;

	@Instance(MODID)
	public static ModPhantasialand INSTANCE = null;

	public static ConfigManager config;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config = new ConfigManager(event.getSuggestedConfigurationFile());
		config.loadConfig();
		System.out.println("Config loaded");
	}

	public void setupBeta() {
		
		int id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityBlackMamba.class, MODID+":bm", id);
		EntityRegistry.registerModEntity(EntityBlackMamba.class, MODID+":bm", id, this, 64, 20, true);
				
		itemBlackMamba = new ItemBlackMamba().setUnlocalizedName(MODID+":bmi").setTextureName("stone").setCreativeTab(tabPhl);
		
		GameRegistry.registerItem(itemBlackMamba, "bmi");
		
		proxy.setupBeta();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException {
		// new MoneyManager();
		System.out.println("Beginning initialisation of PhlMod");
		EventListener evt = new EventListener();
		MinecraftForge.EVENT_BUS.register(evt);
		FMLCommonHandler.instance().bus().register(evt);
		tabPhl = new CreativeTabsPhl(MODNAME);
		itemCreditCard = new ItemCreditCard().setTextureName(MODID + ":cc")
				.setMaxStackSize(1).setUnlocalizedName(MODID + ":cc")
				.setCreativeTab(tabPhl);
		GameRegistry.registerItem(itemCreditCard, "cc", MODID);
		GameRegistry.addShapelessRecipe(new ItemStack(itemCreditCard, 1, 0),
				new Object[] { new ItemStack(Blocks.dirt) });

		itemChurros = new ItemChurros(2, 0.15F, false)
				.setTextureName(MODID + ":churros").setMaxStackSize(64)
				.setCreativeTab(tabPhl).setUnlocalizedName(MODID + ":churros");
		GameRegistry.registerItem(itemChurros, "churros", MODID);

		blockTrack = new BlockTrack().setBlockName(MODID + ":blockTrack")
				.setCreativeTab(tabPhl).setBlockTextureName("stone");
		GameRegistry.registerBlock(blockTrack, "blockTrack");
		GameRegistry.registerTileEntity(TileEntityTrack.class, MODID + ":T");
		itemRotator = new ItemRotator().setTextureName(MODID + ":rotator")
				.setUnlocalizedName(MODID + ":rotator").setCreativeTab(tabPhl).setMaxStackSize(1);
		GameRegistry.registerItem(itemRotator, "rotator", MODID);
		FMLEventChannel net = NetworkRegistry.INSTANCE
				.newEventDrivenChannel(MODID);
		networkCon = net;
		netHandler = new NetworkHandler();
		net.register(netHandler);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerPhl());
		blockKasse = new BlockKasse().setBlockName(MODID + ":blockKasse")
				.setCreativeTab(tabPhl).setBlockTextureName("stone");
		GameRegistry.registerBlock(blockKasse, "blockKasse");
		GameRegistry.registerTileEntity(TileEntityKasse.class, MODID + ":K");
		blockTicketsystem = new BlockTicketsystem().setBlockName(MODID+":blockTicketsys").setCreativeTab(tabPhl);
		GameRegistry.registerBlock(blockTicketsystem, "ticketsys");
		GameRegistry.registerTileEntity(TileEntityTicketsystem.class, MODID+":TS");
		itemTicket = new ItemTicket().setUnlocalizedName(MODID+":ticket").setMaxStackSize(1).setTextureName(MODID+":ticket").setCreativeTab(tabPhl);
		GameRegistry.registerItem(itemTicket, "ticket");
		proxy.setupRendererAndSound();
		if (config.isBeta())
			this.setupBeta();
		if(blockTrack != null&&Item.getIdFromItem(Item.getItemFromBlock(blockTrack))!=-1&&blockKasse != null&&Item.getIdFromItem(Item.getItemFromBlock(blockKasse))!=-1&&blockKasse != null&&Item.getIdFromItem(Item.getItemFromBlock(blockKasse))!=-1){
			System.out.println("Initaliastaion seemed to be ok");
		}else{
			System.out.println("Inintialisation errored!");
			ModContainer moc = FMLCommonHandler.instance().findContainerFor(this);
			moc.setEnabledState(false);
		}
	}

	public static FMLEventChannel networkCon;
	public static NetworkHandler netHandler;

	@EventHandler
	public void serverStop(FMLServerStoppingEvent event) {
		if (MoneyManager.instance != null) {
			MoneyManager.instance.save();
		}
		MoneyManager.instance = null;
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		((ServerCommandManager) (event.getServer().getCommandManager()))
				.registerCommand(new PhlCommand());
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent evt) {
		if (MoneyManager.instance == null) {
			new MoneyManager();
		}
	}

}
