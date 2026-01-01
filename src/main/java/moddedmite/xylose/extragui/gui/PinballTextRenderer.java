package moddedmite.xylose.extragui.gui;

import net.minecraft.Gui;
import net.minecraft.Minecraft;
import net.minecraft.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class PinballTextRenderer extends Gui {
    private final String text = "请勿称MITE为贝爷生存等称呼，二者无任何直接关联";
    private final String text1 = "可称为“MC不可能这么简单”(不可关闭，社区环境所迫)";
    private float textX;
    private float textY;

    private float velocityX;
    private float velocityY;
    private final float minSpeed = 0.4F;

    private long lastUpdate = 0;

    private boolean initialized = false;

    public void renderText(Minecraft mc) {
        ScaledResolution res = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int screenWidth = res.getScaledWidth();
        int screenHeight = res.getScaledHeight();

        if (!initialized) {
            initializePosition(screenWidth * 2, screenHeight * 2);
            initialized = true;
        }

        updatePhysics(screenWidth * 2, screenHeight * 2);

        GL11.glPushMatrix();
        GL11.glScaled(0.5F, 0.5F, 1.0F);
        mc.fontRenderer.drawStringWithShadow(text, (int) textX, (int) textY, 0x44FFFFFF);
        mc.fontRenderer.drawStringWithShadow(text1, (int) textX, (int) textY + 10, 0x44FFFFFF);
        GL11.glPopMatrix();
    }

    private void initializePosition(int screenWidth, int screenHeight) {
        textX = screenWidth / 2.0F;
        textY = screenHeight / 2.0F;

        float angle = (float) (Math.random() * Math.PI * 2);
        float baseSpeed = 1.0F;
        float maxSpeed = 3.0F;
        float speed = baseSpeed + (float) Math.random() * (maxSpeed - minSpeed);

        velocityX = (float) (Math.cos(angle) * speed);
        velocityY = (float) (Math.sin(angle) * speed);
    }

    private void updatePhysics(int screenWidth, int screenHeight) {
        int textWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
        int textHeight = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdate > 50) {
            float randomFactor = 0.05F;
            velocityX += (float) (Math.random() - 0.5) * randomFactor;
            velocityY += (float) (Math.random() - 0.5) * randomFactor;
            lastUpdate = currentTime;
        }

        float friction = 0.98F;
        velocityX *= friction;
        velocityY *= friction;

        float currentSpeed = (float) Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (currentSpeed < minSpeed && currentSpeed > 0) {
            float scale = minSpeed / currentSpeed;
            velocityX *= scale;
            velocityY *= scale;
        }

        textX += velocityX;
        textY += velocityY;

        boolean bounced = false;

        if (textX < 10) {
            textX = 10;
            velocityX = -velocityX * 0.8F;
            bounced = true;
        } else if (textX > screenWidth - textWidth - 10) {
            textX = screenWidth - textWidth - 10;
            velocityX = -velocityX * 0.8F;
            bounced = true;
        }

        if (textY < 10) {
            textY = 10;
            velocityY = -velocityY * 0.8F;
            bounced = true;
        } else if (textY > screenHeight - textHeight - 10) {
            textY = screenHeight - textHeight - 10;
            velocityY = -velocityY * 0.8F;
            bounced = true;
        }

        if (bounced) {
            velocityX += (float) (Math.random() - 0.5) * 0.3F;
            velocityY += (float) (Math.random() - 0.5) * 0.3F;
        }
    }
}
