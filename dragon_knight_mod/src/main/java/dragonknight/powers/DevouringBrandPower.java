package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import java.util.HashSet;
import java.util.Set;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class DevouringBrandPower extends BasePower {
    public static final String POWER_ID = makeID("DevouringBrandPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static Set<String> existInExhaustPile = new HashSet<>();

    public DevouringBrandPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onInitialApplication() {
        if (!owner.isPlayer)
            return;
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.exhaustPile.group) {
            existInExhaustPile.add(c.cardID);
        }
    }

    @Override
    public void onExhaust(AbstractCard card) {
        existInExhaustPile.add(card.name);
    }

}
