package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.cards.IBrandDifferentCard;
import dragonknight.character.DragonPrince;
import dragonknight.powers.BlackDragonAwakeningPower;
import dragonknight.powers.TrueDragon;

public class AbyssalGuidance extends CustomCard implements IBrandDifferentCard {
    public static final String ID = makeID("AbyssalGuidance");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private boolean branded = false;

    public AbyssalGuidance() {
        super(ID, NAME, imagePath("cards/skill/default.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!branded) {
            if (DragonKnightMod.beDragonCount < 2) {
                addToBot(new ApplyPowerAction(p, p, new TrueDragon(p)));
            }
        } else {
            addToBot(new ApplyPowerAction(p, p, new BlackDragonAwakeningPower(p)));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (DragonKnightMod.beDragonCount < 2) {
            this.glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void setBranded(boolean branded) {
        this.branded = branded;
    }
}
