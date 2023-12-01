package dragonknight.patch;

import static dragonknight.DragonKnightMod.addAntiBrandToCard;
import static dragonknight.DragonKnightMod.logger;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

@SpirePatch(clz = GridCardSelectScreen.class, method = "update")
public class GridCardSelectPatch {
    @SpireInsertPatch(loc = 221)
    public static void Insert(GridCardSelectScreen _instance) {
        ArrayList<AbstractCard> cards = _instance.selectedCards;
        logger.info(cards.size());
        if (cards.size() > 0) {
            addAntiBrandToCard(cards.get(0));
            cards.get(0).initializeDescription();
        }
    }
}
