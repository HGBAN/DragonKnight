package dragonknight.patch;

import static dragonknight.DragonKnightMod.*;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import dragonknight.DragonKnightMod;

@SpirePatch(clz = GridCardSelectScreen.class, method = "update")
public class GridCardSelectPatch {
    @SpireInsertPatch(loc = 221)
    public static void Insert(GridCardSelectScreen _instance) {
        if (DragonKnightMod.antiBrandSet) {
            ArrayList<AbstractCard> cards = _instance.selectedCards;
            if (cards.size() > 0) {
                addAntiBrandToCard(cards.get(0));
                cards.get(0).initializeDescription();
                cards.get(0).stopGlowing();
            }
            DragonKnightMod.antiBrandSet = false;
            _instance.selectedCards.clear();
        }
    }
}
