package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;
import dragonknight.powers.AbyssAwakenPower;
import dragonknight.powers.BlackDragon;

public class AbyssalGaze extends CustomCard {
    public static final String ID = makeID("AbyssalGaze");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AbyssalGaze() {
        super(ID, NAME, imagePath("cards/skill/AbyssalGaze.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(DragonKnightMod.Enums.ANTI_BRAND);
        this.tags.add(DragonKnightMod.Enums.EXHAUST);
        this.initializeDescription();
    }

    @Override
    public void triggerWhenDrawn() {
        setCostForTurn(this.cost - DragonKnightMod.beDragonCount);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.hasPower(BlackDragon.POWER_ID) && DragonKnightMod.exhaustCardsThisTurn.size() >= 7) {
            addToBot(new ApplyPowerAction(p, p, new AbyssAwakenPower(p)));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(BlackDragon.POWER_ID) && DragonKnightMod.exhaustCardsThisTurn.size() >= 7)
            this.glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        else
            this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
    }

}
