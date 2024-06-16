package dragonknight.patch;

import static dragonknight.DragonKnightMod.*;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import dragonknight.DragonKnightMod;
import dragonknight.powers.BrandsCallPower;
import dragonknight.powers.DevouringBrandPower;
import dragonknight.powers.GamygynBrandPower;
import dragonknight.powers.HeavenlyLinkPower;
import dragonknight.powers.IceDevilsHeartPower;

public class PlayerPatch {

    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class useCardPatch {
        private static int change = 0;

        public static void Prefix(AbstractPlayer _instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
            AbstractPlayer player = AbstractDungeon.player;
            if (player != null) {
                if (!AbstractDungeon.getCurrRoom().isBattleOver
                        && c.costForTurn > 0) {
                    int tmp = c.costForTurn;
                    if (player.hasPower(makeID("TrueEyePower"))) {
                        c.setCostForTurn(c.costForTurn - 1);
                    }
                    if (player.hasPower(BrandsCallPower.POWER_ID) && isBrandCard(c)) {
                        c.setCostForTurn(c.costForTurn - 1);
                    }
                    if (player.hasPower(IceDevilsHeartPower.POWER_ID)
                            && c.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
                        c.setCostForTurn(c.costForTurn - 1);
                    }
                    if (player.hasPower(HeavenlyLinkPower.POWER_ID)
                            && c.name.contains(DragonKnightMod.cardNameKeywords.TEXT_DICT.get("Heavenly"))) {
                        c.setCostForTurn(c.costForTurn - 1);
                    }
                    if (player.hasPower(makeID("DevouringBrandPower"))) {
                        if (((DevouringBrandPower) player.getPower(makeID("DevouringBrandPower"))).existInExhaustPile
                                .contains(c.cardID)) {
                            c.setCostForTurn(c.costForTurn - 1);
                        }
                    }
                    change = tmp - c.costForTurn;
                }
            }
        }

        public static void Postfix(AbstractPlayer _instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
            if (change > 0) {
                c.setCostForTurn(c.costForTurn + 1);
                if (c.costForTurn == c.cost) {
                    c.isCostModifiedForTurn = false;
                }
                change = 0;
            }
        }

        @SpireInsertPatch(loc = 1707)
        public static void Insert(AbstractPlayer _instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
            DragonKnightMod.cardsUsedThisTurn++;
            AbstractPlayer player = AbstractDungeon.player;
            if (player.hasPower(GamygynBrandPower.POWER_ID)) {
                AbstractPower p = player.getPower(GamygynBrandPower.POWER_ID);
                p.amount = cardsUsedThisTurn;
                if (cardsUsedThisTurn == 5 && c.type.equals(CardType.ATTACK)) {
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            CardGroup group = player.hand;
                            if (player.discardPile.contains(c))
                                group = player.discardPile;
                            else if (player.exhaustPile.contains(c))
                                group = player.exhaustPile;
                            brandCard(c, group);
                            isDone = true;
                        }
                    });
                }
            }
        }
    }
}
