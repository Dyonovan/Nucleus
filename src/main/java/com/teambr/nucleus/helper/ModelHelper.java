package com.teambr.nucleus.client;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.SimpleModelTransform;

/**
 * This file was created for Nucleus
 *
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 *
 * Adapted from Tinker's Construct Model Helper https://github.com/SlimeKnights/TinkersConstruct/blob/master/src/main/java/slimeknights/tconstruct/library/client/model/ModelHelper.java
 *
 * @author Paul Davis - pauljoda
 * @since 2/13/2017
 */
public class ModelHelper {

    public static final SimpleModelTransform DEFAULT_ITEM_STATE;
    public static final SimpleModelTransform DEFAULT_TOOL_STATE;
    public static final TransformationMatrix BLOCK_THIRD_PERSON_RIGHT;
    public static final TransformationMatrix BLOCK_THIRD_PERSON_LEFT;

    public static TextureAtlasSprite getTextureFromBlockstate(BlockState state) {
        return Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
    }

    private static TransformationMatrix get(float tx, float ty, float tz, float ax, float ay, float az, float s) {
        return new TransformationMatrix(
                new Vector3f(tx / 16, ty / 16, tz / 16),
                quatFromXYZDegrees(new Vector3f(ax, ay, az)),
                new Vector3f(s, s, s), null);
    }

    public static Quaternion quatFromXYZ(float x, float y, float z) {
        Quaternion ret = new Quaternion(0, 0, 0, 1), t = new Quaternion(0, 0, 0, 0);
        t.set((float)Math.sin(x/2), 0, 0, (float)Math.cos(x/2));
        ret.multiply(t);
        t.set(0, (float)Math.sin(y/2), 0, (float)Math.cos(y/2));
        ret.multiply(t);
        t.set(0, 0, (float)Math.sin(z/2), (float)Math.cos(z/2));
        ret.multiply(t);
        return ret;
    }

    public static Quaternion quatFromXYZDegrees(Vector3f xyz) {
        return quatFromXYZ((float)Math.toRadians(xyz.getX()), (float)Math.toRadians(xyz.getY()), (float)Math.toRadians(xyz.getZ()));
    }

    static {
        {
            // equals forge:default-item
            ImmutableMap.Builder<ItemCameraTransforms.TransformType, TransformationMatrix> builder = ImmutableMap.builder();
            builder.put(ItemCameraTransforms.TransformType.GROUND, get(0, 2, 0, 0, 0, 0, 0.5f));
            builder.put(ItemCameraTransforms.TransformType.HEAD, get(0, 13, 7, 0, 180, 0, 1));
            builder.put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, get(0, 3, 1, 0, 0, 0, 0.55f));
            builder.put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, get(0, 3, 1, 0, 0, 0, 0.55f));
            builder.put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, get(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f));
            builder.put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, get(1.13f, 3.2f, 1.13f, 0, 90, -25, 0.68f));
            DEFAULT_ITEM_STATE = new SimpleModelTransform(builder.build());
        }
        {
            // equals forge:default-tool
            ImmutableMap.Builder<ItemCameraTransforms.TransformType, TransformationMatrix> builder = ImmutableMap.builder();
            builder.put(ItemCameraTransforms.TransformType.GROUND, get(0, 2, 0, 0, 0, 0, 0.5f));
            builder.put(ItemCameraTransforms.TransformType.HEAD, get(0, 13, 7, 0, 180, 0, 1));
            builder.put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, get(0, 4, 0.5f, 0, -90, 55, 0.85f));
            builder.put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, get(0, 4, 0.5f, 0, 90, -55, 0.85f));
            builder.put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, get(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f));
            builder.put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, get(1.13f, 3.2f, 1.13f, 0, 90, -25, 0.68f));
            DEFAULT_TOOL_STATE = new SimpleModelTransform(builder.build());

        }
        {
            BLOCK_THIRD_PERSON_RIGHT = get(0, 2.5f, 0, 75, 45, 0, 0.375f);
            BLOCK_THIRD_PERSON_LEFT = get(0, 0, 0, 0, 255, 0, 0.4f);
        }
    }
}