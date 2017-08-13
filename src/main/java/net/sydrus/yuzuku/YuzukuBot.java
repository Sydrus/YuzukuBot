package net.sydrus.yuzuku;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.sydrus.yuzuku.FileReader.BCRItem;
import net.sydrus.yuzuku.FileReader.ConfigReader;
import net.sydrus.yuzuku.FileReader.BCRItem.Type;
import net.sydrus.yuzuku.Listeners.DiscordListener;
import net.sydrus.yuzuku.Managers.CommandManager;
import net.sydrus.yuzuku.Managers.GuildManager;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Console;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
import net.sydrus.yuzuku.audio.MusicManager;
import net.sydrus.yuzuku.plugin.Manager.PluginManager;
import net.sydrus.yuzuku.plugin.Manager.PluginsLoader;
import net.sydrus.yuzuku.plugin.PluginCommand;

public class YuzukuBot {
    public static final MusicManager MusicManager = new MusicManager();;
    private JDA _JDA;
    private static YuzukuBot _Instance;
    public static Color defaultMessageColor = Color.GREEN;
    public final CommandManager _CommandManager;
    private static PluginsLoader admanager;
    public String Version = "2.5.7/PluginsCore_DC_3.0.4.17";
    public String Status = "Running";
    public int subCommands = 12;
    public String settingsFolder = "Settings";
    private String botDataOfUsersAndText = "Data";
    public ConfigReader settingsData;
    private ConfigReader users;
    public int botsvErros = 0;
    public int botErrors = 0;
    public Console console = new Console();
    public static GuildManager guildManager = null;
    public static String botName = "Yuzuku Bot";

    @SuppressWarnings("deprecation")
    public YuzukuBot() {
        _Instance = this;
        try {
            _JDA = new JDABuilder(AccountType.BOT)
                    .setToken("")
                    .addListener(new DiscordListener()).setGame(Game.of("Changing Game")).buildBlocking();
            settingsData = new ConfigReader(settingsFolder, botDataOfUsersAndText);
            settingsData.createFile();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        _CommandManager = new CommandManager();
        try {
            users = new ConfigReader("Settings", "users.data", "57894561857282254862552u", 84678942);
        } catch (Exception e1) {
            net.sydrus.yuzuku.Managers.ConsoleCommand.Type.messageType(MessageType.Error,
                    "the file 'users.data' is corrupted");
            botErrors++;
        }

        try {
            settingsData.reload();
            if (!settingsData.contains(BCRItem.getItem(Type.Developers))) {
                List<String> item = new ArrayList<String>();
                item.add("");
                settingsData.set(BCRItem.getItem(Type.Developers), item);
            }

            if (!settingsData.contains(BCRItem.getItem(Type.Administrators))) {
                List<String> item = new ArrayList<String>();

                // item.add("127445104721002496");
                settingsData.set(BCRItem.getItem(Type.Administrators), item);
            }
            settingsData.save();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            botErrors++;
        }
        net.sydrus.yuzuku.Managers.ConsoleCommand.Type.messageType(MessageType.Info, "Loading plugins...");
        try {
            admanager = new PluginsLoader();
        } catch (Throwable e) {
            botErrors++;
            e.printStackTrace();
        }
        createEmpty();
        console.start();
        guildManager = new GuildManager(_JDA);
        guildManager.CheckAllConfigs();
        guildManager.registerTracks();
    }

    private void createEmpty() {
        if (!settingsData.contains("ServerPort")) {
            settingsData.set("ServerPort", 4343);
        }
        if (!settingsData.contains("UseServer")) {
            settingsData.set("UseServer", false);
        }
        if (!settingsData.contains("MaxServerConnections")) {
            settingsData.set("MaxServerConnections", "infinity");
        }
        if (!settingsData.contains("UserServerPass")) {
            settingsData.set("UserServerPass", false);
        }
        if (!settingsData.contains("ServerPass")) {
            settingsData.set("ServerPass", "000");
        }
        if (!settingsData.contains("ConsoleCommands")) {
            settingsData.set("ConsoleCommands", false);
        }
        if (!settingsData.contains("UseServerCommandInConsole")) {
            settingsData.set("UseServerCommandInConsole", false);
        }
        if (!settingsData.contains("ServerJoinClient")) {
            settingsData.set("ServerJoinClient", "[{clientHostName}/{ClientIP}] Connect");
        }
        if (!settingsData.contains("TimeToLock")) {
            settingsData.set("TimeToLock", "1 m");
        }
        settingsData.save();
    }

    public int getErrors() {
        return botsvErros;
    }

    public static Map<String, PluginCommand> getAddonCommand() {
        return PluginsLoader.getInstance().addoncommand;
    }

    public static PluginManager getAddonsManager() {
        return admanager.addonsmanager;
    }

    public static YuzukuBot getInstance() {
        return _Instance;
    }

    public Boolean isDeveloper(String id) {
        try {
            settingsData.reload();
            if (settingsData.getList(BCRItem.getItem(Type.Developers)).contains(id)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public final ConfigReader getusers(/*Class<?> clas*/) {
        return this.users;
    }

    public CommandManager getCommandManager(/*Class<?> clas*/) {

        return this._CommandManager;
    }

    public boolean isTextBanned(String message) throws IOException {
        settingsData.reload();
        List<Object> bannedText = settingsData.getList(BCRItem.getItem(Type.BannedTexts));
        for (Object banned : bannedText) {
            if (message.toLowerCase().contains(banned.toString().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("resource")
    public List<String> readFile(File file) throws IOException {
        List<String> text = new ArrayList<String>();
        String Textbfl = "";
        FileReader filerd;
        BufferedReader buff;
        filerd = new FileReader(file);
        buff = new BufferedReader(filerd);
        while ((Textbfl = buff.readLine()) != null) {
            text.add(Textbfl);
        }
        return text;
    }

    public JDA getJDA() {
        return this._JDA;
    }
}
