package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.DragonKnightMod;
import dragonknight.cards.BrandCopyCard;

public class IceDevilFormPower extends BasePower {
    public static final String POWER_ID = makeID("IceDevilFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public IceDevilFormPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
            if (card.type.equals(CardType.ATTACK) && !(card.exhaust || card.exhaustOnUseOnce)) {
                if (card instanceof BrandCopyCard) {
                    ((BrandCopyCard) card).brandExhaust = true;
                }
                card.triggerOnExhaust();
                flash();
            }
            if (card.type.equals(CardType.SKILL)) {
                addToBot(new GainEnergyAction(1));
                flash();
            }
        }
    }

}
