package dragonknight.cards.special;

import static dragonknight.DragonKnightMod.*;

import java.lang.ref.WeakReference;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;

public class ApproachingAbyss extends CustomCard {
    public static final String ID = makeID("ApproachingAbyss");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 4;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private final Runnable onBeDragon = () -> {
        setCostForTurn(this.cost - DragonKnightMod.beDragonCount);
    };

    public ApproachingAbyss() {
        super(ID, NAME, imagePath("cards/attack/ApproachingAbyss.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(DragonKnightMod.Enums.ANTI_BRAND);
        this.tags.add(DragonKnightMod.Enums.EXHAUST);
        this.initializeDescription();

        this.baseDamage = 20;
        setCostForTurn(this.cost - DragonKnightMod.beDragonCount);

        DragonKnightMod.onBeDragon.add(new WeakReference<Runnable>(onBeDragon));

        this.cardsToPreview = new AbyssalGaze();
    }

    @Override
    public void triggerWhenDrawn() {
        setCostForTurn(this.cost - DragonKnightMod.beDragonCount);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(3);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AttackEffect.FIRE));
    }

    @Override
    public void triggerOnExhaust() {
        if (AbstractDungeon.player.exhaustPile.size() < 25) {
            addToBot(new MakeTempCardInDrawPileAction(this.cardsToPreview.makeStatEquivalentCopy(), 1, true, true));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.exhaustPile.size() < 25)
            this.glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        else
            this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
    }
}
