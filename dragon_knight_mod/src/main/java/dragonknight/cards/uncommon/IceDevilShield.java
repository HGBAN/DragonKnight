package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;
import dragonknight.patch.CardPatch;

public class IceDevilShield extends CustomCard {
    public static final String ID = makeID("IceDevilShield");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public IceDevilShield() {
        super(ID, NAME, imagePath("cards/skill/IceDevilShield.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.baseBlock = 15;
        this.tags.add(DragonKnightMod.Enums.ANTI_BRAND);
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBlock(5);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : p.hand.group) {
                    if (!c.hasTag(DragonKnightMod.Enums.BRAND) && !c.hasTag(DragonKnightMod.Enums.BRAND2)
                            && !c.hasTag(DragonKnightMod.Enums.TEMP_BRAND)) {
                        if (!c.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
                            c.tags.add(DragonKnightMod.Enums.ANTI_BRAND);
                            c.initializeDescription();
                        }
                        if (IceDevilShield.this.upgraded) {
                            AbstractCard masterCard = CardPatch.Field.masterCard.get(c);
                            if (masterCard != null) {
                                masterCard.tags.add(DragonKnightMod.Enums.ANTI_BRAND);
                                masterCard.initializeDescription();
                                DragonKnightMod.antiBrandCards.add(masterCard);
                            }
                        }
                    }
                }
                isDone = true;
            }
        });
    }

}
