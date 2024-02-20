package dragonknight.screens;

import static dragonknight.DragonKnightMod.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import basemod.abstracts.CustomScreen;
import dragonknight.DragonKnightMod;
import dragonknight.powers.BlackDragon;
import dragonknight.powers.WhiteDragon;
import dragonknight.ui.Button1;

public class SelectDragonScreen extends CustomScreen {
    private Button1 blackDragon = new Button1(Settings.WIDTH / 2 - 562 * Settings.scale, Settings.HEIGHT / 2);
    private Button1 whiteDragon = new Button1(Settings.WIDTH / 2 + 214 * Settings.scale, Settings.HEIGHT / 2);
    private AbstractPlayer player;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("SelectDragonScreen"));

    public SelectDragonScreen() {
        blackDragon.buttonText = uiStrings.TEXT_DICT.get("BlackDragon");
        blackDragon.tipHeader = DragonKnightMod.keywords.get("BlackDragon").PROPER_NAME;
        blackDragon.tipText = DragonKnightMod.keywords.get("BlackDragon").DESCRIPTION;
        whiteDragon.buttonText = uiStrings.TEXT_DICT.get("WhiteDragon");
        whiteDragon.tipHeader = DragonKnightMod.keywords.get("WhiteDragon").PROPER_NAME;
        whiteDragon.tipText = DragonKnightMod.keywords.get("WhiteDragon").DESCRIPTION;
    }

    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen SELECT_DRAGON_SCREEN;
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        // com.megacrit.cardcrawl.ui.buttons.Button

        return Enum.SELECT_DRAGON_SCREEN;
    }

    @Override
    public void close() {
        AbstractDungeon.overlayMenu.hideBlackScreen();
        AbstractDungeon.isScreenUp = false;
    }

    @SuppressWarnings("unused")
    private void open() {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;

        this.player = AbstractDungeon.player;
        reopen();
    }

    @Override
    public void openingSettings() {
        AbstractDungeon.previousScreen = curScreen();
    }

    @Override
    public void render(SpriteBatch sb) {
        blackDragon.render(sb);
        whiteDragon.render(sb);
        FontHelper.charDescFont.draw(sb,
                uiStrings.TEXT_DICT.get("Illustration"),
                Settings.WIDTH / 2 - 400 * Settings.scale, Settings.HEIGHT / 2 - 240 * Settings.scale,
                800 * Settings.scale, -1, true);
    }

    @Override
    public void reopen() {
        AbstractDungeon.overlayMenu.showBlackScreen();
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;
    }

    @Override
    public void update() {
        blackDragon.update();
        if (blackDragon.pressed) {
            if (!player.hasPower(makeID("BlackDragon")))
                AbstractDungeon.actionManager
                        .addToBottom(new ApplyPowerAction(player, player, new BlackDragon(player), 0));
            else
                AbstractDungeon.actionManager
                        .addToBottom(new GainEnergyAction(1));

            AbstractDungeon.closeCurrentScreen();
            blackDragon.pressed = false;
        }
        whiteDragon.update();
        if (whiteDragon.pressed) {
            if (!player.hasPower(makeID("WhiteDragon")))
                AbstractDungeon.actionManager
                        .addToBottom(new ApplyPowerAction(player, player, new WhiteDragon(player), 0));
            else
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));

            AbstractDungeon.closeCurrentScreen();
            whiteDragon.pressed = false;
        }
    }
}
