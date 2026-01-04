package dev.sakura.client.gui.mainmenu;

import dev.sakura.client.Sakura;
import dev.sakura.client.gui.account.AccountSelectorScreen;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.MainMenuShader;
import dev.sakura.client.shaders.SplashShader;
import dev.sakura.client.utils.render.Shader2DUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static dev.sakura.client.Sakura.mc;

public class MainMenuScreen extends Screen {
    private final List<MenuButton> buttons = new ArrayList<>();
    private int iconImage = -1;
    private int iconWidth, iconHeight;

    private long initTime = 0;
    private static final long FADE_DURATION = 800;
    private static final long BUTTON_STAGGER = 80;

    private static final int BASE_WIDTH = 1280;
    private static final int BASE_HEIGHT = 720;
    private static final float MIN_SCALE = 0.8f;

    public MainMenuScreen() {
        super(Text.of("MainMenuScreen"));
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    protected void init() {
        ClickGui clickGui = Sakura.MODULES.getModule(ClickGui.class);
        if (clickGui != null && clickGui.getKey() == -1) {
            mc.setScreen(new WelcomeScreen());
            return;
        }

        if (initTime == 0) {
            initTime = System.currentTimeMillis();
        }

        updateLayout();
        loadIcon();
    }

    private void updateLayout() {
        buttons.clear();

        float scaleX = (float) this.width / BASE_WIDTH;
        float scaleY = (float) this.height / BASE_HEIGHT;
        float scale = Math.min(scaleX, scaleY);
        scale = Math.max(MIN_SCALE, scale);
        int centerX = this.width / 2;
        int startY = this.height / 2 - (int) (40 * scale);
        int buttonWidth = (int) (200 * scale);
        int buttonHeight = (int) (20 * scale);
        int spacing = (int) (24 * scale);

        buttonHeight = Math.max(buttonHeight, 16);
        buttonWidth = Math.max(buttonWidth, 120);

        buttons.add(new MenuButton(
                centerX - buttonWidth / 2, startY,
                buttonWidth, buttonHeight,
                I18n.translate("menu.singleplayer"),
                () -> mc.setScreen(new SelectWorldScreen(this))
        ));

        buttons.add(new MenuButton(
                centerX - buttonWidth / 2, startY + spacing,
                buttonWidth, buttonHeight,
                I18n.translate("menu.multiplayer"),
                () -> mc.setScreen(new MultiplayerScreen(this)),
                !isMultiplayerDisabled()
        ));

        buttons.add(new MenuButton(
                centerX - buttonWidth / 2, startY + spacing * 2,
                buttonWidth, buttonHeight,
                "Alt Manager",
                () -> mc.setScreen(new AccountSelectorScreen(this))
        ));

        int bottomY = startY + spacing * 3 + (int) (4 * scale);

        buttons.add(new MenuButton(
                centerX - buttonWidth / 2 - (int) (2 * scale), bottomY,
                buttonWidth / 2, buttonHeight,
                I18n.translate("menu.options"),
                () -> mc.setScreen(new OptionsScreen(this, mc.options))
        ));

        buttons.add(new MenuButton(
                centerX + (int) (2 * scale), bottomY,
                buttonWidth / 2, buttonHeight,
                I18n.translate("menu.quit"),
                mc::scheduleStop
        ));

        buttons.add(new ShaderButton(
                centerX - buttonWidth / 2, this.height - (int) (30 * scale),
                buttonWidth, buttonHeight
        ));
    }

    private void loadIcon() {
        if (iconImage != -1) return;

        iconImage = NanoVGHelper.loadTexture("/assets/sakura/icons/icon.png");
        if (iconImage != -1) {
            iconWidth = 2334;
            iconHeight = 860;
        }
    }

    private boolean isMultiplayerDisabled() {
        if (mc.isMultiplayerEnabled()) {
            return false;
        }
        if (mc.isUsernameBanned()) {
            return true;
        }
        return mc.getMultiplayerBanDetails() != null;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        float transitionProgress = 1.0f;

        try {
            SplashShader splash = SplashShader.getInstance();
            if (splash != null && splash.isTransitionStarted() && !splash.isTransitionComplete()) {
                transitionProgress = splash.getTransitionProgress();
            }
        } catch (Exception ignored) {
        }

        final float finalTransitionProgress = transitionProgress;

        MainMenuShader.getSharedInstance().render(this.width, this.height, finalTransitionProgress);

        renderButtonBlurs(context, finalTransitionProgress);

        NanoVGRenderer.INSTANCE.draw(vg -> {
            renderIcon(finalTransitionProgress);
            renderButtons(mouseX, mouseY, finalTransitionProgress);

            if (mc.getSession() != null) {
                String loggedInText = "Logged in as: " + mc.getSession().getUsername();
                NanoVGHelper.drawText(loggedInText, this.width - 5, 5, FontLoader.regular(18), 18,
                        NanoVG.NVG_ALIGN_RIGHT | NanoVG.NVG_ALIGN_TOP, new Color(255, 255, 255, (int) (255 * finalTransitionProgress)));
            }

            //renderVersionText(finalTransitionProgress);
            //renderCopyright(finalTransitionProgress);
        });
    }

    private void renderButtonBlurs(DrawContext context, float transitionProgress) {
        long elapsed = System.currentTimeMillis() - initTime;

        for (int i = 0; i < buttons.size(); i++) {
            MenuButton button = buttons.get(i);

            long staggeredTime = elapsed - (i * BUTTON_STAGGER);
            float fadeProgress = 1.0f;

            if (staggeredTime > 0 && staggeredTime < FADE_DURATION) {
                fadeProgress = staggeredTime / (float) FADE_DURATION;
                fadeProgress = 1.0f - (1.0f - fadeProgress) * (1.0f - fadeProgress);
            } else if (staggeredTime <= 0) {
                fadeProgress = 0.0f;
            }

            float finalAlpha = fadeProgress * transitionProgress;
            if (finalAlpha <= 0) continue;

            float buttonScale = 0.8f + fadeProgress * 0.2f;
            float centerX = button.x + button.width / 2f;
            float centerY = button.y + button.height / 2f;

            float scaledX = centerX - (button.width / 2f) * buttonScale;
            float scaledY = centerY - (button.height / 2f) * buttonScale;
            float scaledW = button.width * buttonScale;
            float scaledH = button.height * buttonScale;

            Shader2DUtil.drawRoundedBlur(context.getMatrices(), scaledX, scaledY, scaledW, scaledH, 4 * buttonScale, new Color(0, 0, 0, 0), 20f, finalAlpha);
        }
    }

    private void renderIcon(float transitionProgress) {
        if (iconImage == -1) return;

        float scaleX = (float) this.width / BASE_WIDTH;
        float scaleY = (float) this.height / BASE_HEIGHT;
        float scale = Math.min(scaleX, scaleY);
        scale = Math.max(MIN_SCALE, scale);

        float iconScaleFactor = Math.max(0.4f, scale * 0.7f);
        float displayScale = 0.15f * iconScaleFactor;
        float displayWidth = iconWidth * displayScale;
        float displayHeight = iconHeight * displayScale;

        float x = (width - displayWidth) / 2f;
        float y = height / 5f - displayHeight / 2f;

        float easeT = 1.0f - (float) Math.pow(1.0f - transitionProgress, 3.0);
        float iconScale = 0.5f + easeT * 0.5f;

        NanoVGHelper.drawTexture(iconImage, x, y, displayWidth, displayHeight, transitionProgress, iconScale, iconScale, 0);
    }

    private void renderButtons(int mouseX, int mouseY, float transitionProgress) {
        long elapsed = System.currentTimeMillis() - initTime;

        for (int i = 0; i < buttons.size(); i++) {
            MenuButton button = buttons.get(i);

            long staggeredTime = elapsed - (i * BUTTON_STAGGER);
            float fadeProgress = 1.0f;

            if (staggeredTime > 0 && staggeredTime < FADE_DURATION) {
                fadeProgress = staggeredTime / (float) FADE_DURATION;
                fadeProgress = 1.0f - (1.0f - fadeProgress) * (1.0f - fadeProgress);
            } else if (staggeredTime <= 0) {
                fadeProgress = 0.0f;
            }

            float finalAlpha = fadeProgress * transitionProgress;
            float buttonScale = 0.8f + fadeProgress * 0.2f;

            boolean hovered = mouseX >= button.x && mouseX <= button.x + button.width && mouseY >= button.y && mouseY <= button.y + button.height;

            button.render(hovered, finalAlpha, buttonScale);
        }
    }

/*    private void renderVersionText(float transitionProgress) {
        String version = "Minecraft " + SharedConstants.getGameVersion().getName();
        if (mc.isDemo()) {
            version += " Demo";
        } else {
            String versionType = mc.getVersionType();
            if (!"release".equalsIgnoreCase(versionType)) {
                version += "/" + versionType;
            }
        }

        if (MinecraftClient.getModStatus().isModded()) {
            version += I18n.translate("menu.modded");
        }

        int font = FontLoader.regular(12);
        int alpha = (int) (255 * transitionProgress);
        Color color = new Color(255, 255, 255, alpha);

        NanoVGHelper.drawString(version, 2, height - 2, font, 12, color);
    }*/

/*    private void renderCopyright(float transitionProgress) {
        String copyright = "Copyright© Sakura2025.";
        int font = FontLoader.regular(12);
        float textWidth = NanoVGHelper.getTextWidth(copyright, font, 12);

        int alpha = (int) (255 * transitionProgress);
        Color color = new Color(255, 255, 255, alpha);

        NanoVGHelper.drawString(copyright, width - textWidth - 2, height - 2, font, 12, color);
    }*/

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (MenuButton menuButton : buttons) {
            if (menuButton.isHovered((int) mouseX, (int) mouseY) && menuButton.enabled) {
                if (menuButton instanceof ShaderButton shaderButton) {
                    if (button == 0) {
                        shaderButton.nextShader();
                        return true;
                    } else if (button == 1) {
                        shaderButton.previousShader();
                        return true;
                    }
                } else if (button == 0) {
                    menuButton.onClick();
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void removed() {
        super.removed();
        if (iconImage != -1) {
            NanoVGHelper.deleteTexture(iconImage);
            iconImage = -1;
        }
    }
}