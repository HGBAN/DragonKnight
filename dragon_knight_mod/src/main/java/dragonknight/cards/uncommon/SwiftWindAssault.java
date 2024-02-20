package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import java.util.List;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.actions.DiscardAntiBrandCardAction;
import dragonknight.character.DragonPrince;

public class SwiftWindAssault extends CustomCard {
    public static final String ID = makeID("SwiftWindAssault");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public SwiftWindAssault() {
        super(ID, NAME, imagePath("cards/attack/SwiftWindAssault.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.baseDamage = 8;
        this.baseMagicNumber = 8;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeMagicNumber(2);
        }
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster m, float tmp) {
        int c = checkHand();
        if (c >= 2) {
            tmp += c * this.baseMagicNumber;
        }
        return tmp;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // ArrayList<AbstractCard> cards = new ArrayList<>();
        if (checkHand() >= 2) {
            // for (AbstractCard c : cards) {
            // addToBot(new DiscardSpecificCardAction(c));
            // }
            addToBot(new DiscardAntiBrandCardAction());
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AttackEffect.LIGHTNING));
    }

    @Override
    public void triggerOnGlowCheck() {
        if (checkHand() >= 2)
            this.glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        else
            this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
    }

    private int checkHand(List<AbstractCard> cards) {
        AbstractPlayer p = AbstractDungeon.player;
        int count = 0;
        for (AbstractCard c : p.hand.group) {
            if (c.hasTag(DragonKnightMod.Enums.ANTI_BRAND) && !c.equals(this)) {
                count++;
                if (cards != null)
                    cards.add(c);
            }
        }
        return count;
    }

    private int checkHand() {
        return checkHand(null);
    }
}
