package dev.sakura.client.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public abstract class HudModule extends Module {
    @Expose
    @SerializedName("hudX")
    protected float x;

    @Expose
    @SerializedName("hudY")
    protected float y;

    protected float width = 50;
    protected float height = 20;

    protected boolean dragging = false;
    protected float dragX, dragY;
    protected DrawContext currentContext;

    @Expose
    @SerializedName("relativeX")
    protected float relativeX = 0f;

    @Expose
    @SerializedName("relativeY")
    protected float relativeY = 0f;

    protected final MinecraftClient mc;

    private final float defaultX;
    private final float defaultY;

    public HudModule(String englishName, @Nullable String chineseName, float defaultX, float defaultY) {
        super(englishName, chineseName, null);
        this.defaultX = defaultX;
        this.defaultY = defaultY;
        this.x = defaultX;
        this.y = defaultY;
        this.relativeX = 0;
        this.relativeY = 0;
        this.mc = MinecraftClient.getInstance();
    }

    /**
     * 子类实现此方法进行绘制
     * 坐标系已自动缩放，直接使用MC逻辑坐标（与mouseX/mouseY一致）
     */
    public abstract void onRenderContent();

    private void onRender(DrawContext context) {
        this.currentContext = context;
        NanoVGRenderer.INSTANCE.draw(vg -> onRenderContent());
    }

    public void renderInEditor(DrawContext context, float mouseX, float mouseY) {
        if (dragging) {
            int gameWidth = mc.getWindow().getScaledWidth();
            int gameHeight = mc.getWindow().getScaledHeight();

            x = Math.max(0, Math.min(mouseX - dragX, gameWidth - width));
            y = Math.max(0, Math.min(mouseY - dragY, gameHeight - height));

            relativeX = x / gameWidth;
            relativeY = y / gameHeight;
        }

        onRender(context);

        NanoVGRenderer.INSTANCE.draw(canvas -> NanoVGHelper.drawRect(x, y, width, height, dragging ? new Color(100, 100, 255, 80) : new Color(0, 0, 0, 50)));
    }

    public void renderInGame(DrawContext context) {
        HudEditor hudEditor = Sakura.MODULES.getModule(HudEditor.class);
        if (hudEditor != null && hudEditor.isEnabled()) {
            return;
        }
        onRender(context);
    }

    @FunctionalInterface
    protected interface PixelDrawer {
        void draw(float pixelX, float pixelY, float pixelW, float pixelH);
    }

    protected DrawContext getContext() {
        return currentContext;
    }

    protected MatrixStack getMatrix() {
        return currentContext.getMatrices();
    }

    @Override
    public void reset() {
        super.reset();
        this.x = defaultX;
        this.y = defaultY;
        this.relativeX = 0;
        this.relativeY = 0;
    }

    public boolean mouseClicked(float mouseX, float mouseY, int button) {
        if (isHovering(mouseX, mouseY) && button == 0) {
            dragging = true;
            dragX = mouseX - x;
            dragY = mouseY - y;
            return true;
        }
        return false;
    }

    public boolean mouseReleased(int button) {
        if (dragging && button == 0) {
            dragging = false;
            return true;
        }
        return false;
    }

    public boolean isHovering(float mouseX, float mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isDragging() {
        return dragging;
    }
}
