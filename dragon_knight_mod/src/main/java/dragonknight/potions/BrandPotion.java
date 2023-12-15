package dragonknight.potions;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import dragonknight.DragonKnightMod;
import dragonknight.actions.DiscoveryBrandCardsAction;

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
        // com.megacrit.cardcrawl.potions.SkillPotion
        // logger.info(description);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(DragonKnightMod.keywords.get("Brand").PROPER_NAME),
                DragonKnightMod.keywords.get("Brand").DESCRIPTION));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BrandPotion();
    }

    @Override
    public void use(AbstractCreature target) {
        this.addToBot(new DiscoveryBrandCardsAction(this.potency));
    }

}
