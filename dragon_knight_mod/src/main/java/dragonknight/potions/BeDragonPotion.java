package dragonknight.potions;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import dragonknight.DragonKnightMod;

public class BeDragonPotion extends AbstractPotion {
    public static final String ID = makeID("BeDragonPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    private static final String NAME = potionStrings.NAME;
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BeDragonPotion() {
        super(NAME, ID, PotionRarity.COMMON, PotionSize.JAR, PotionColor.FRUIT);
        this.isThrown = false;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        description = DESCRIPTIONS[0];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(DragonKnightMod.keywords.get("BeDragon").PROPER_NAME),
                DragonKnightMod.keywords.get("BeDragon").DESCRIPTION));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BeDragonPotion();
    }

    @Override
    public void use(AbstractCreature target) {
        DragonKnightMod.beDragon();
    }

}
