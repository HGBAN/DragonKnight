package dragonknight.potions;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import dragonknight.DragonKnightMod;

public class HeavenlyPotion extends AbstractPotion {
    public static final String ID = makeID("HeavenlyPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    private static final String NAME = potionStrings.NAME;
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public HeavenlyPotion() {
        super(NAME, ID, PotionRarity.COMMON, PotionSize.BOTTLE, PotionColor.SNECKO);
        isThrown = false;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        description = DESCRIPTIONS[0];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.EXHAUST.NAMES[0]),
                (String) GameDictionary.keywords.get(GameDictionary.EXHAUST.NAMES[0])));
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.ETHEREAL.NAMES[0]),
                (String) GameDictionary.keywords.get(GameDictionary.ETHEREAL.NAMES[0])));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new HeavenlyPotion();
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer player = AbstractDungeon.player;
        for (AbstractCard c : player.hand.group) {
            if (c.type.equals(CardType.ATTACK)) {
                c.exhaust = true;
                c.isEthereal = true;
                c.tags.add(DragonKnightMod.Enums.EXHAUST);
                c.tags.add(DragonKnightMod.Enums.ETHEREAL);
                c.initializeDescription();
            }
        }
    }

}
