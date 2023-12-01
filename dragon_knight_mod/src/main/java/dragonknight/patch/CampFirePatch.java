package dragonknight.patch;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import dragonknight.ui.AntiBrandOption;

//@SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
public class CampFirePatch {
    @SuppressWarnings("unchecked")
    @SpireInsertPatch(rloc = 4)
    public static void Insert(CampfireUI _instance) {
        try {
            Field buttonsField = _instance.getClass().getDeclaredField("buttons");
            buttonsField.setAccessible(true);
            ArrayList<AbstractCampfireOption> buttons = (ArrayList<AbstractCampfireOption>) buttonsField.get(_instance);
            buttons.add(new AntiBrandOption());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
