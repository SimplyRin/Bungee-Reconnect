package eu.the5zig.reconnect.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import eu.the5zig.reconnect.Reconnect;
import eu.the5zig.reconnect.util.CmdUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class CommandReconnect extends Command implements TabExecutor {
	
	private final Reconnect instance;
	
	private static final BaseComponent[] cmdSubNotFound = new ComponentBuilder().color(ChatColor.RED).append("Subcommand not found").create();
	
	private static final BaseComponent[] cmdFeedbackReloadAttempt = new ComponentBuilder().color(ChatColor.GOLD).append("Reloading...").create();
	private static final BaseComponent[] cmdFeedbackReloadError = new ComponentBuilder().color(ChatColor.RED).append("Reload errored; Check console!").create();
	private static final BaseComponent[] cmdFeedbackReloadComplete = new ComponentBuilder().color(ChatColor.GREEN).append("Reload complete.").create();

	public CommandReconnect(Reconnect instance) {
		super("reconnect", "reconnect.command", new String[] {"reeeeeconnect"});
		this.instance = instance;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length > 0) {
			switch (args[0].toLowerCase()) {
			case "reload":
				sender.sendMessage(cmdFeedbackReloadAttempt);
				if (instance.loadConfig()) {
					sender.sendMessage(cmdFeedbackReloadComplete);
				} else {
					sender.sendMessage(cmdFeedbackReloadError);
				}
				break;

			default:
				sender.sendMessage(cmdSubNotFound);
				break;
			}
		}
	}
	
	private static final List<String> baseComplete = Collections.unmodifiableList(Arrays.asList("reload"));

	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
		if (args.length > 0) {
			if (args.length > 1) {
				return new ArrayList<String>();
			} else {
				return CmdUtil.copyPartialMatches(baseComplete, args[0]);
			}
		}
		return baseComplete;
	}
	
}