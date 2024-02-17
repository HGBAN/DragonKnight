package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;
import dragonknight.powers.AbyssalVengeanceDramaPower;

public class AbyssalVengeanceDrama extends CustomCard {
    public static final String ID = makeID("AbyssalVengeanceDrama");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AbyssalVengeanceDrama() {
        super(ID, NAME, imagePath("cards/skill/AbyssalVengeanceDrama.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(DragonKnightMod.Enums.ANTI_BRAND);
        this.tags.add(DragonKnightMod.Enums.EXHAUST);
        this.tags.add(DragonKnightMod.Enums.BE_DRAGON);
        this.initializeDescription();

        this.cardsToPreview = new ApproachingAbyss();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AbyssalVengeanceDramaPower(p)));
        if (DragonKnightMod.beDragonCount >= 3) {
            addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (DragonKnightMod.beDragonCount >= 3)
            this.glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        else
            this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
    }
}
