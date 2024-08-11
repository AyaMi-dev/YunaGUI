package kr.yuna;

import kr.yuna.Party.PartyListener;
import kr.yuna.Party.PartySystem;
import kr.yuna.Party.util.command;
import kr.yuna.Party.util.tabcompleter;
import kr.yuna.commands.*;
import kr.yuna.event.LobbyListener;
import kr.yuna.event.NoBlockExplodeListener;
import kr.yuna.event.NoExplodeListener;
import kr.yuna.managers.GUImanager;
import kr.yuna.guis.users.MainGUI;
import kr.yuna.guis.users.Turtorials.BattleGUI;
import kr.yuna.guis.users.Turtorials.DecorationGUI;
import kr.yuna.guis.users.Turtorials.DungeonGUI;
import kr.yuna.guis.users.TutorialGUI;
import kr.yuna.managers.SpawnManager;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {

    private GUImanager guImanager;
    private Plugin plugin;
    private World w;

    private TutorialGUI tutorialGUI;
    private BattleGUI battleGUI;
    private DecorationGUI decorationGUI;
    private DungeonGUI dungeonGUI;
    private PartySystem partySystem;



    public GUImanager getGuImanager() {
        return guImanager;
    }

    public void setGuImanager(GUImanager guImanager) {
        this.guImanager = guImanager;
    }

    @Override
    public void onEnable() {
        getLogger().info("YP GUI Enabled.");

        // 인스턴스 생성
        SpawnManager spawnManager = new SpawnManager(this);
        day day = new day(this,w);
        MainGUI mainGUI = new MainGUI(this,tutorialGUI);
        TutorialGUI tutorialgui = new TutorialGUI(this,tutorialGUI,battleGUI,decorationGUI,dungeonGUI);
        BattleGUI battleGUI1 = new BattleGUI(this,tutorialGUI);
        DecorationGUI decorationGUI1 = new DecorationGUI(this,decorationGUI);
        DungeonGUI dungeonGUI1 = new DungeonGUI(this,dungeonGUI);
        GUImanager guiManager = new GUImanager(this,battleGUI,dungeonGUI,decorationGUI,tutorialGUI);
        partySystem = new PartySystem();
        JoinForceTel joinForceTel = new JoinForceTel(this);


        // 객체
        tutorialGUI = new TutorialGUI(this,tutorialGUI,battleGUI,decorationGUI,dungeonGUI); // TutorialGUI 객체 생성
        mainGUI = new MainGUI(this, tutorialGUI); // MainGUI 객체 생성 시 TutorialGUI 객체 전달


        //이벤트 처리

        getServer().getPluginManager().registerEvents(new NoExplodeListener(), (this));
        getServer().getPluginManager().registerEvents(new NoBlockExplodeListener(), (this));
        getServer().getPluginManager().registerEvents(new MainGUI(this,tutorialGUI),this);
        getServer().getPluginManager().registerEvents(new TutorialGUI(this,tutorialGUI,battleGUI,decorationGUI,dungeonGUI),this);
        getServer().getPluginManager().registerEvents(new BattleGUI(this,tutorialGUI),this);
        getServer().getPluginManager().registerEvents(new PartyListener(partySystem), this);
        getServer().getPluginManager().registerEvents(new LobbyListener(this),this);
        getServer().getPluginManager().registerEvents(new LobbyListener(this), this);

        // 플레이어 숨기기 getServer().getPluginManager().registerEvents(new HideNameTag(this), (this));


        // 명령여 등록

        getCommand("메뉴").setExecutor(guiManager);
        getCommand("gmc").setExecutor(new gmc());
        getCommand("gms").setExecutor(new gms());
        getCommand("gma").setExecutor(new gma());
        getCommand("gmsp").setExecutor(new gmsp());
        getCommand("아침").setExecutor(new day(this,w));
        getCommand("스폰").setExecutor(new spawn(this));
        getCommand("셋스폰").setExecutor(new SetSpawn(this));
        getCommand("파티").setExecutor(new command(partySystem));
        getCommand("파티").setTabCompleter(new tabcompleter());
        getCommand("리롯").setExecutor(new rl(spawnManager));
    }




    @Override
    public void onDisable() {
        getLogger().info("YP GUI Disabled.");

    }
}