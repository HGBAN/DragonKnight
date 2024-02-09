package dragonknight.potions;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class IceDevilPotion extends AbstractPotion {
    public static final String ID = makeID("IceDevilPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    private static final String NAME = potionStrings.NAME;
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public IceDevilPotion() {
        super(NAME, ID, PotionRarity.RARE, PotionSize.BOLT, PotionColor.BLUE);
        isThrown = false;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        description = DESCRIPTIONS[0];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.EXHAUST.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.EXHAUST.NAMES[0])));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new IceDevilPotion();
    }

    @Override
    public void use(AbstractCreature target) {
        this.addToBot(
                new DamageAllEnemiesAction(AbstractDungeon.player, AbstractDungeon.player.exhaustPile.size(),
                        DamageType.NORMAL, AttackEffect.SLASH_DIAGONAL));
    }

}
