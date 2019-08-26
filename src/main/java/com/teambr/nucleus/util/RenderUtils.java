package com.teambr.nucleus.util;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.nio.FloatBuffer;

/**
 * This file was created for Nucleus - Java
 *
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
public class RenderUtils {
    // Resource Locations
    public static final ResourceLocation MC_BLOCKS_RESOURCE_LOCATION =
            AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    public static final ResourceLocation MC_ITEMS_RESOURCE_LOCATION =
            new ResourceLocation("textures/atlas/items.png");

    /*******************************************************************************************************************
     * Render Helpers                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Used to bind a texture to the render manager
     * @param resource The resource to bind
     */
    public static void bindTexture(ResourceLocation resource) {
        Minecraft.getInstance().getTextureManager().bindTexture(resource);
    }

    /**
     * Used to bind the MC item sheet
     */
    public static void bindMinecraftItemSheet() {
        bindTexture(MC_ITEMS_RESOURCE_LOCATION);
    }

    /**
     * Used to bind the MC blocks sheet
     */
    public static void bindMinecraftBlockSheet() {
        bindTexture(MC_BLOCKS_RESOURCE_LOCATION);
    }
    
    /**
     * Set the GL color. You should probably reset it after this
     *
     * @param color The color to set
     */
    public static void setColor(Color color) {
        GlStateManager.color4f(color.getRed() / 255F, color.getGreen() / 255F,
                color.getBlue() / 255F, color.getAlpha() / 255F);
    }

    /**
     * Sets the color back to full white (normal)
     */
    public static void restoreColor() {
        setColor(new Color(255, 255, 255));
    }

    /**
     * Used to prepare the rendering state. For basic stuff that you want things to behave on
     */
    public static void prepareRenderState() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

    /**
     * Un-does the prepare state
     */
    public static void restoreRenderState() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }

    /*******************************************************************************************************************
     * Matrix Helpers                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Loads the matrix
     * @param transform The transform
     */
    public static void loadMatrix(Matrix4f transform) {
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        store(transform, matrixBuffer);
        matrixBuffer.flip();
        GL11.glMultMatrixf(matrixBuffer);
    }

    /**
     * Creates a rotation matrix for the entity
     * @param entity The looking entity
     * @return A rotation matrix representing what the entity sees
     */
    public static Matrix4f createEntityRotateMatrix(Entity entity) {
        Matrix4f rotationMatrix = new Matrix4f();
        rotateMatrix4f(rotationMatrix, entity.rotationPitch, new Vector3f(1, 0, 0));
        rotateMatrix4f(rotationMatrix, entity.rotationYaw - 180,   new Vector3f(0, 1, 0));
        return rotationMatrix;
    }

    /*******************************************************************************************************************
     * Rendering math helpers                                                                                          *
     *******************************************************************************************************************/

    /**
     * Creates a rotation matrix. Similar to
     * <code>glRotate(angle, x, y, z)</code>.
     *
     * Credit to https://github.com/SilverTiger/lwjgl3-tutorial/blob/master/src/silvertiger/tutorial/lwjgl/math/Matrix4f.java
     *
     * @param angle Angle of rotation in degrees
     *
     */
    public static void rotateMatrix4f(Matrix4f rotation, float angle, Vector3f vector) {
        float c = (float) Math.cos(Math.toRadians(angle));
        float s = (float) Math.sin(Math.toRadians(angle));
        Vector3f vec = new Vector3f(vector.x, vector.y, vector.z);
        if (vec.length() != 1f) {
            vec.normalize();
            vector.x = vec.x;
            vector.y = vec.y;
            vector.z = vec.z;
        }

        rotation.m00 = vector.x * vector.x * (1f - c) + c;
        rotation.m10 = vector.y * vector.x * (1f - c) + vector.z * s;
        rotation.m20 = vector.x * vector.z * (1f - c) - vector.y * s;
        rotation.m01 = vector.x * vector.y * (1f - c) - vector.z * s;
        rotation.m11 = vector.y * vector.y * (1f - c) + c;
        rotation.m21 = vector.y * vector.z * (1f - c) + vector.x * s;
        rotation.m02 = vector.x * vector.z * (1f - c) + vector.y * s;
        rotation.m12 = vector.y * vector.z * (1f - c) - vector.x * s;
        rotation.m22 = vector.z * vector.z * (1f - c) + c;
    }

    /**
     * Store this matrix in a float buffer. The matrix is stored in column
     * major (openGL) order.
     *
     * Ported from older opengl
     *
     * @param buf The buffer to store this matrix in
     */
    public static void store(Matrix4f vec, FloatBuffer buf) {
        buf.put(vec.m00);
        buf.put(vec.m01);
        buf.put(vec.m02);
        buf.put(vec.m03);
        buf.put(vec.m10);
        buf.put(vec.m11);
        buf.put(vec.m12);
        buf.put(vec.m13);
        buf.put(vec.m20);
        buf.put(vec.m21);
        buf.put(vec.m22);
        buf.put(vec.m23);
        buf.put(vec.m30);
        buf.put(vec.m31);
        buf.put(vec.m32);
        buf.put(vec.m33);
    }

    /**
     * Multiply the right matrix by the left and place the result in a third matrix.
     *
     * Pulled from older opengl
     *
     * @param left The left source matrix
     * @param right The right source matrix
     * @param dest The destination matrix, or null if a new one is to be created
     * @return the destination matrix
     */
    public static Matrix4f mul(Matrix4f left, Matrix4f right, Matrix4f dest) {
        if (dest == null)
            dest = new Matrix4f();

        float m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02 + left.m30 * right.m03;
        float m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02 + left.m31 * right.m03;
        float m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02 + left.m32 * right.m03;
        float m03 = left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02 + left.m33 * right.m03;
        float m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12 + left.m30 * right.m13;
        float m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12 + left.m31 * right.m13;
        float m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12 + left.m32 * right.m13;
        float m13 = left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12 + left.m33 * right.m13;
        float m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22 + left.m30 * right.m23;
        float m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22 + left.m31 * right.m23;
        float m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 + left.m32 * right.m23;
        float m23 = left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22 + left.m33 * right.m23;
        float m30 = left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30 * right.m33;
        float m31 = left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31 * right.m33;
        float m32 = left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32 * right.m33;
        float m33 = left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33 * right.m33;

        dest.m00 = m00;
        dest.m01 = m01;
        dest.m02 = m02;
        dest.m03 = m03;
        dest.m10 = m10;
        dest.m11 = m11;
        dest.m12 = m12;
        dest.m13 = m13;
        dest.m20 = m20;
        dest.m21 = m21;
        dest.m22 = m22;
        dest.m23 = m23;
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;

        return dest;
    }

    /*******************************************************************************************************************
     * Shape Rendering                                                                                                 *
     *******************************************************************************************************************/

    /**
     * Used to draw a 3d cube, provide opposite corners
     *
     * @param x1 First X Position
     * @param y1 First Y Position
     * @param z1 First Z Position
     * @param x2 Second X Position
     * @param y2 Second Y Position
     * @param z2 Second Z Position
     * @param u Min U
     * @param v Min V
     * @param u1 Max U
     * @param v1 Max V
     */
    public static void renderCubeWithTexture(double x1, double y1, double z1, double x2, double y2, double z2,
                                 double u, double v, double u1, double v1) {
        BufferBuilder tes = Tessellator.getInstance().getBuffer();

        VertexFormat POSITION_TEX_NORMALF = new VertexFormat();
        VertexFormatElement NORMAL_3F =
                new VertexFormatElement(0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.NORMAL, 3);
        POSITION_TEX_NORMALF.addElement(DefaultVertexFormats.POSITION_3F);
        POSITION_TEX_NORMALF.addElement(DefaultVertexFormats.TEX_2F);
        POSITION_TEX_NORMALF.addElement(NORMAL_3F);

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF);
        tes.pos(x1, y1, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z1).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z1).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z1).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF);
        tes.pos(x1, y1, z2).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z2).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z2).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF);
        tes.pos(x1, y1, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x1, y1, z2).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z1).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF);
        tes.pos(x2, y1, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z1).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z2).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF);
        tes.pos(x1, y1, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z1).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x1, y1, z2).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF);
        tes.pos(x1, y2, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z2).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z1).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();
    }
}
