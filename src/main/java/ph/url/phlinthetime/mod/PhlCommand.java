package ph.url.phlinthetime.mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import ph.url.phlinthetime.mod.money.MoneyManager;
import ph.url.phlinthetime.mod.money.PlayerInfo;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class PhlCommand extends CommandBase {

	
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "phl";
	}

	
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.canCommandSenderUseCommand(0, this.getCommandName());
    }
	
	public int getRequiredPermissionLevel() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	

	@Override
	public String getCommandUsage(ICommandSender var1) {
		// TODO Auto-generated method stub
		return "/phl [cc [get <Player>|give <Player> <Money>|set <Player> <Money>]|lock|unlock] ";
	}

	public EntityPlayer getPlayerForUsername(String username){
		Iterator i = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
		while(i.hasNext()){
			EntityPlayer p = (EntityPlayer) i.next();
			if(p.getCommandSenderName().equals(username)){
				return p;
			}
		}
		return null;
	}
	
	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		boolean isCorrect = true;
		final ICommandSender var1_ = var1;
		if(var2.length>=2&&var2[0].equals("cc")){
			String pn = var2.length>=3?var2[2]:"";
			if(pn.startsWith("@p")){
				var1.addChatMessage(new ChatComponentText("Der Selektor @p... wird derzeit noch nicht unterstuetzt!"));
				return;
			}
			EntityPlayer p = getPlayerForUsername(pn);
			PlayerInfo info = (PlayerInfo) MoneyManager.instance.hashmap.get(p==null?"":p.getUniqueID().toString().replace("-", ""));
			if(var2.length==4&&var2[1].equals("give")&&var1.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName())){
				if(p==null){
					var1.addChatMessage(new ChatComponentText("Der Spieler "+var2[2]+" ist nicht online!"));
					return;
					}
				if(info == null){
					MoneyManager.instance.addNewPlayer(p.getUniqueID().toString().replace("-", ""));
					info = (PlayerInfo) MoneyManager.instance.hashmap.get(p.getUniqueID().toString().replace("-", ""));
				}	
				try{
					int oldMoney=info.getMoney();
					
					info.setMoney(oldMoney+Integer.valueOf(var2[3]));

					}catch(NumberFormatException e){
						isCorrect=false;
					}
					
			}else if(var2.length==4&&var2[1].equals("set")&&var1.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName())){
				if(p==null){
					var1.addChatMessage(new ChatComponentText("Der Spieler "+var2[2]+" ist nicht online!"));
					return;
					}
				if(info == null){
					MoneyManager.instance.addNewPlayer(p.getUniqueID().toString().replace("-", ""));
					info = (PlayerInfo) MoneyManager.instance.hashmap.get(p.getUniqueID().toString().replace("-", ""));
					
				}
				try{
					int oldMoney=info.getMoney();
					info.setMoney(Integer.valueOf(var2[3]));

					}catch(NumberFormatException e){
						isCorrect = false;
					}
			}else if(var2[1].equals("get")&&var2.length==3&&var1.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName())){
				if(p==null){
					var1.addChatMessage(new ChatComponentText("Der Spieler "+var2[2]+" ist nicht online!"));
					return;
					}
				if(info == null){
					var1.addChatMessage(new ChatComponentText("Der Spieler existiert zwar, hat aber noch  nie vom Geldsystem Gebrauch gemacht."));				
					return;
				}
				var1.addChatMessage(new ChatComponentText("Der Spieler "+var2[2]+" hat "+info.getMoney()+" Euros Guthaben"));
				
				
					
			}else if(var2[1].equals("lock")&&var2.length==2&&var1 instanceof EntityPlayer){
				p = (EntityPlayer) var1;
				if(MoneyManager.instance.hashmap.keySet().contains(p.getUniqueID().toString().replace("-", "")))
				info = (PlayerInfo) MoneyManager.instance.hashmap.get(p.getUniqueID().toString().replace("-", ""));
				info.setLocked(true);
				return;
			}else if(var2[1].equals("unlock")&&var2.length==2&&var1 instanceof EntityPlayer){
				p = (EntityPlayer) var1;
				if(MoneyManager.instance.hashmap.keySet().contains(p.getUniqueID().toString().replace("-", "")))
				info = (PlayerInfo) MoneyManager.instance.hashmap.get(p.getUniqueID().toString().replace("-", ""));
				info.setLocked(false);		
				info.setLastUnlock(System.currentTimeMillis());
				return;
			}else{
				isCorrect = false;
			}
			System.out.println(getPlayerForUsername(var2[2]).getUniqueID().toString().replace("-", ""));
			if(getPlayerForUsername(var2[2]).getUniqueID().toString().replace("-", "")!=null)MoneyManager.instance.hashmap.put(getPlayerForUsername(var2[2]).getUniqueID().toString().replace("-", ""), info);
			
		}else{
			isCorrect = false;
		}
		if(!isCorrect){
			var1.addChatMessage(new ChatComponentText(getCommandUsage(null)));
		}

	}
	
	public final List suboptions = Arrays.asList(new String[]{"cc"});
	public final List suboptionscc = Arrays.asList(new String[]{"get", "give", "lock", "set", "unlock"});

	@Override
	public List addTabCompletionOptions(ICommandSender var1, String[] var2) {
		// TODO Auto-generated method stub
		return (var2.length==1?CommandBase.getListOfStringsFromIterableMatchingLastWord(var2, suboptions):((var2.length==2&&var2[0].equals("cc")?CommandBase.getListOfStringsFromIterableMatchingLastWord(var2, suboptionscc):(var2.length==3&&var2[0].equals("cc")&&(var2[1].equals("get")||var2[1].equals("set")||var2[1].equals("give")))?CommandBase.getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames()):null)));
	}

	@Override
	public boolean isUsernameIndex(String[] var1, int var2) {
		// TODO Auto-generated method stub
		if(var1.length>=1&&var1[0].equals("cc")){
			return var2 == 2;
		}
		
		return false;//var1.length==2&&var1[0].equals("cc")&&var2==1;
	}

	public int compareTo(ICommand par1ICommand)
    {
        return this.getCommandName().compareTo(par1ICommand.getCommandName());
    }

	@Override
	public List getCommandAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Object o) {
		 return this.compareTo((ICommand)o);
	}

	

}
