package dragonknight.potions;

import static dragonknight.DragonKnightMod.makeID;

import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class BrandPotion extends AbstractPotion {
    public static final String ID = makeID("BrandPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    private static final String NAME = potionStrings.NAME;
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BrandPotion() {
        super(NAME, ID, PotionRarity.COMMON, PotionSize.BOTTLE, PotionColor.FAIRY);
        this.isThrown = false;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        description = DESCRIPTIONS[0];
    }

    @Override
    public int getPotency(int ascensionLevel) {
        // com.megacrit.cardcrawl.potions.SkillPotion

        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BrandPotion();
    }

    @Override
    public void use(AbstractCreature target) {
        this.addToBot(new DiscoveryAction(null, this.potency));
    }

}
