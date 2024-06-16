package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.cards.special.BathinBrand;
import dragonknight.cards.special.BelialBrand;
import dragonknight.cards.special.BerithBrand;
import dragonknight.cards.special.GamygynBrand;
import dragonknight.cards.special.OriasBrand;
import dragonknight.cards.special.ShaxBrand;

public class GoetiaBrandPower extends BasePower {
    public static final String POWER_ID = makeID("GoetiaBrandPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final ArrayList<AbstractCard> cards = new ArrayList<>();
    private ArrayList<AbstractCard> cardsCombat;

    static {
        cards.add(new BerithBrand());
        cards.add(new BathinBrand());
        cards.add(new GamygynBrand());
        cards.add(new OriasBrand());
        cards.add(new BelialBrand());
        cards.add(new ShaxBrand());
    }

    public GoetiaBrandPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.DEBUFF, false, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onInitialApplication() {
        cardsCombat = new ArrayList<>(cards);
    }

    public void handle() {
        if (cardsCombat.size() == 0)
            return;
        flash();
        int r = AbstractDungeon.cardRng.random(cardsCombat.size() - 1);
        addToBot(new MakeTempCardInDrawPileAction(cardsCombat.remove(r).makeCopy(), 1, true,
                true));
    }
}
