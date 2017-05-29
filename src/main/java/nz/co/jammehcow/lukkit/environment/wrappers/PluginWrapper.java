package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.Main;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * The plugin wrapper providing access to plugin functions.
 *
 * @author jammehcow
 */
public class PluginWrapper extends LuaTable {
    private LukkitPlugin plugin;

    /**
     * Creates a new plugin wrapper.
     *
     * @param plugin the Lukkit plugin
     */
    public PluginWrapper(final LukkitPlugin plugin) {
        this.plugin = plugin;

        set("onLoad", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue callback) {
                if (callback.isfunction()) {
                    plugin.setLoadCB(callback.checkfunction());
                } else {
                    Main.instance.getLogger().warning("The plugin " + plugin.getName() + " tried to add an onLoad callback but provided a " + callback.typename() + " instead of a function.");
                }
                return LuaValue.NIL;
            }
        });

        set("onEnable", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue callback) {
                if (callback.isfunction()) {
                    plugin.setEnableCB(callback.checkfunction());
                } else {
                    Main.instance.getLogger().warning("The plugin " + plugin.getName() + " tried to add an onEnable callback but provided a " + callback.typename() + " instead of a function.");
                }
                return LuaValue.NIL;
            }
        });

        set("onDisable", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue callback) {
                if (callback.isfunction()) {
                    plugin.setDisableCB(callback.checkfunction());
                } else {
                    Main.instance.getLogger().warning("The plugin " + plugin.getName() + " tried to add an onDisable callback but provided a " + callback.typename() + " instead of a function.");
                }
                return LuaValue.NIL;
            }
        });

        set("addCommand", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue arg1, LuaValue arg2) {
                if (!arg1.isstring() || !arg2.isfunction() || arg2.checkfunction().narg() != 4) {
                    // TODO
                    plugin.getLogger().severe("I tried to register a command but there was an issue doing so. Check that the command registration conforms to the layout here: ");
                } else {
                    plugin.addCommand(arg1.checkjstring(), arg2.checkfunction());
                }
                return LuaValue.NIL;
            }
        });
    }
}
