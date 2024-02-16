package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.patch.CardPatch;

public class AbyssalBeastFormPower extends BasePower {
    public static final String POWER_ID = makeID("AbyssalBeastFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // private List<AbstractCard> cards = new ArrayList<>();

    public AbyssalBeastFormPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        flash();
        CardPatch.randoms.clear();
    }

    
}
