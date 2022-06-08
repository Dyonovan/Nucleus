package com.pauljoda.nucleus.helper;

import com.google.common.collect.ImmutableMap;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.SimpleModelState;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 6/6/2022
 */
public class ModelHelper {

    public static final SimpleModelState DEFAULT_ITEM_STATE;
    public static final SimpleModelState DEFAULT_TOOL_STATE;
    public static final Transformation BLOCK_THIRD_PERSON_RIGHT;
    public static final Transformation BLOCK_THIRD_PERSON_LEFT;

    private static Transformation get(float tx, float ty, float tz, float ax, float ay, float az, float s) {
        return new Transformation(
                new Vector3f(tx / 16, ty / 16, tz / 16),
                quatFromXYZDegrees(new Vector3f(ax, ay, az)),
                new Vector3f(s, s, s), null);
    }

    public static Quaternion quatFromXYZ(float x, float y, float z) {
        Quaternion ret = new Quaternion(0, 0, 0, 1), t = new Quaternion(0, 0, 0, 0);
        t.set((float)Math.sin(x/2), 0, 0, (float)Math.cos(x/2));
        ret.mul(t);
        t.set(0, (float)Math.sin(y/2), 0, (float)Math.cos(y/2));
        ret.mul(t);
        t.set(0, 0, (float)Math.sin(z/2), (float)Math.cos(z/2));
        ret.mul(t);
        return ret;
    }

    public static Quaternion quatFromXYZDegrees(Vector3f xyz) {
        return quatFromXYZ((float)Math.toRadians(xyz.x()), (float)Math.toRadians(xyz.y()), (float)Math.toRadians(xyz.z()));
    }

    static {
        {
            // equals forge:default-item
            ImmutableMap.Builder<ItemTransforms.TransformType, Transformation> builder = ImmutableMap.builder();
            builder.put(ItemTransforms.TransformType.GROUND, get(0, 2, 0, 0, 0, 0, 0.5f));
            builder.put(ItemTransforms.TransformType.HEAD, get(0, 13, 7, 0, 180, 0, 1));
            builder.put(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, get(0, 3, 1, 0, 0, 0, 0.55f));
            builder.put(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, get(0, 3, 1, 0, 0, 0, 0.55f));
            builder.put(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, get(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f));
            builder.put(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, get(1.13f, 3.2f, 1.13f, 0, 90, -25, 0.68f));
            DEFAULT_ITEM_STATE = new SimpleModelState(builder.build());
        }
        {
            // equals forge:default-tool
            ImmutableMap.Builder<ItemTransforms.TransformType, Transformation> builder = ImmutableMap.builder();
            builder.put(ItemTransforms.TransformType.GROUND, get(0, 2, 0, 0, 0, 0, 0.5f));
            builder.put(ItemTransforms.TransformType.HEAD, get(0, 13, 7, 0, 180, 0, 1));
            builder.put(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, get(0, 4, 0.5f, 0, -90, 55, 0.85f));
            builder.put(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, get(0, 4, 0.5f, 0, 90, -55, 0.85f));
            builder.put(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, get(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f));
            builder.put(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, get(1.13f, 3.2f, 1.13f, 0, 90, -25, 0.68f));
            DEFAULT_TOOL_STATE = new SimpleModelState(builder.build());

        }
        {
            BLOCK_THIRD_PERSON_RIGHT = get(0, 2.5f, 0, 75, 45, 0, 0.375f);
            BLOCK_THIRD_PERSON_LEFT = get(0, 0, 0, 0, 255, 0, 0.4f);
        }
    }
}
