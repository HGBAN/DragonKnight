package dragonknight.cards.common;

import static dragonknight.DragonKnightMod.imagePath;
import static dragonknight.DragonKnightMod.makeID;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;
import dragonknight.character.DragonPrince;

public class DragonScale extends CustomCard {
    public static final String ID = makeID("DragonScale");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public DragonScale() {
        super(ID, NAME, imagePath("cards/skill/DragonScale.png"), COST, DESCRIPTION, TYPE, DragonPrince.Enums.CARD_COLOR,
                RARITY,
                TARGET);
        this.baseBlock = 14;
        this.baseMagicNumber = 8;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
            this.upgradeMagicNumber(4);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.exhaustOnUseOnce)
            this.addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void triggerOnExhaust() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new GainBlockAction(p, p, this.magicNumber));
    }

    @Override
    protected void applyPowersToBlock() {
        super.applyPowersToBlock();

        this.isMagicNumberModified = false;
        float tmp = (float) this.baseMagicNumber;

        Iterator<AbstractPower> var2;
        AbstractPower p;
        for (var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlock(tmp, this)) {
            p = (AbstractPower) var2.next();
        }

        for (var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlockLast(tmp)) {
            p = (AbstractPower) var2.next();
        }

        if (this.baseMagicNumber != MathUtils.floor(tmp)) {
            this.isMagicNumberModified = true;
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        this.magicNumber = MathUtils.floor(tmp);
    }

}
