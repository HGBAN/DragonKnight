package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;

public class SurefireForging extends CustomCard {
    public static final String ID = makeID("SurefireForging");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SurefireForging() {
        super(ID, NAME, imagePath("cards/skill/SurefireForging.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(DragonKnightMod.Enums.EXHAUST);
        this.initializeDescription();
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
        List<AbstractCard> cards = p.hand.group.stream().filter(x -> x.hasTag(DragonKnightMod.Enums.ANTI_BRAND))
                .collect(Collectors.toList());

        if (cards.size() > 0) {
            AbstractCard c = cards.get(AbstractDungeon.cardRng.random(cards.size() - 1));
            AbstractCard newCard = c.makeCopy();
            newCard.setCostForTurn(newCard.costForTurn - 1);
            addToBot(new MakeTempCardInHandAction(newCard));
        }
    }
}
