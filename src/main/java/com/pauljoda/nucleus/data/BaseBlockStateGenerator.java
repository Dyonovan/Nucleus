package com.pauljoda.nucleus.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pauljoda.nucleus.connected.ConnectedModelInfo;
import com.pauljoda.nucleus.connected.ConnectedTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.HashMap;

public abstract class BaseBlockStateGenerator extends BlockStateProvider {


    public BaseBlockStateGenerator(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    // region Helpers

    /**
     * Builds a direction face for a given direction.
     *
     * @param direction   The direction for which to build the face.
     * @param faceBuilder The builder for the face.
     */
    private static void buildDirectionFaces(Direction direction, ModelBuilder<BlockModelBuilder>.ElementBuilder.FaceBuilder faceBuilder) {
        switch (direction) {
            case UP -> faceBuilder.texture("#up").cullface(Direction.UP);
            case DOWN -> faceBuilder.texture("#down").cullface(Direction.DOWN);
            case NORTH -> faceBuilder.texture("#north").cullface(Direction.NORTH);
            case SOUTH -> faceBuilder.texture("#south").cullface(Direction.SOUTH);
            case EAST -> faceBuilder.texture("#east").cullface(Direction.EAST);
            case WEST -> faceBuilder.texture("#west").cullface(Direction.WEST);
        }
    }
    // endregion

    /**
     * Adds connected texture models for a given block.
     *
     * @param block The block to add connected texture models for.
     * @param modID The mod ID of the block's mod.
     */
    // region Connected Textures
    public void addConnectedTextureModels(Block block, String modID) {
        // Must be the proper type
        if (block instanceof ConnectedTexture) {
            // Create BlockState JSON Template
            var basePath = String.format("block/%s", BuiltInRegistries.BLOCK.getKey(block).getPath());
            var modelPath = String.format("%s:%s/", modID, basePath);

            // Create Models
            var modelMap = new HashMap<String, ModelFile>();

            float offset = 0.005F;

            // 0c
            var c0 = models().getBuilder(String.format("%s/c0", basePath))
                    .parent(models().getExistingFile(mcLoc("block")))
                    .element().from(-offset, -offset, -offset).to(16 + offset, 16 + offset, 16 + offset)
                    .allFaces(((direction, faceBuilder) -> {
                        faceBuilder.texture("#all");
                    }))
                    .end()
                    .texture("all", new ResourceLocation(modID, String.format("%s/0c", basePath))).renderType("translucent");
            modelMap.put(String.format("%sc0", modelPath), c0);

            // c1
            var c1 = models().getBuilder(String.format("%s/c1", basePath))
                    .parent(models().getExistingFile(mcLoc("block")))
                    .element().from(-offset, -offset, -offset).to(16 + offset, 16 + offset, 16 + offset)
                    .allFaces(BaseBlockStateGenerator::buildDirectionFaces)
                    .end()
                    .texture("particle", new ResourceLocation(modID, String.format("%s/0c", basePath)))
                    .texture("down", new ResourceLocation(modID, String.format("%s/0c", basePath)))
                    .texture("up", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("north", new ResourceLocation(modID, String.format("%s/1c_u", basePath)))
                    .texture("south", new ResourceLocation(modID, String.format("%s/1c_u", basePath)))
                    .texture("west", new ResourceLocation(modID, String.format("%s/1c_u", basePath)))
                    .texture("east", new ResourceLocation(modID, String.format("%s/1c_u", basePath))).renderType("translucent");
            modelMap.put(String.format("%sc1", modelPath), c1);

            // c2
            var c2 = models().getBuilder(String.format("%s/c2", basePath))
                    .parent(models().getExistingFile(mcLoc("block")))
                    .element().from(-offset, -offset, -offset).to(16 + offset, 16 + offset, 16 + offset)
                    .allFaces(BaseBlockStateGenerator::buildDirectionFaces)
                    .end()
                    .texture("particle", new ResourceLocation(modID, String.format("%s/0c", basePath)))
                    .texture("down", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("up", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("north", new ResourceLocation(modID, String.format("%s/2c_ud", basePath)))
                    .texture("south", new ResourceLocation(modID, String.format("%s/2c_ud", basePath)))
                    .texture("west", new ResourceLocation(modID, String.format("%s/2c_ud", basePath)))
                    .texture("east", new ResourceLocation(modID, String.format("%s/2c_ud", basePath))).renderType("translucent");
            modelMap.put(String.format("%sc2", modelPath), c2);

            // c2 Angle
            var c2angle = models().getBuilder(String.format("%s/c2angle", basePath))
                    .parent(models().getExistingFile(mcLoc("block")))
                    .element().from(-offset, -offset, -offset).to(16 + offset, 16 + offset, 16 + offset)
                    .allFaces(BaseBlockStateGenerator::buildDirectionFaces)
                    .end()
                    .texture("particle", new ResourceLocation(modID, String.format("%s/0c", basePath)))
                    .texture("down", new ResourceLocation(modID, String.format("%s/1c_e", basePath)))
                    .texture("up", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("north", new ResourceLocation(modID, String.format("%s/2c_uw", basePath)))
                    .texture("south", new ResourceLocation(modID, String.format("%s/2c_ue", basePath)))
                    .texture("west", new ResourceLocation(modID, String.format("%s/1c_u", basePath)))
                    .texture("east", new ResourceLocation(modID, String.format("%s/4c", basePath))).renderType("translucent");
            modelMap.put(String.format("%sc2angle", modelPath), c2angle);

            // c3T1
            var c3t1 = models().getBuilder(String.format("%s/c3t1", basePath))
                    .parent(models().getExistingFile(mcLoc("block")))
                    .element().from(-offset, -offset, -offset).to(16 + offset, 16 + offset, 16 + offset)
                    .allFaces(BaseBlockStateGenerator::buildDirectionFaces)
                    .end()
                    .texture("particle", new ResourceLocation(modID, String.format("%s/0c", basePath)))
                    .texture("down", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("up", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("north", new ResourceLocation(modID, String.format("%s/3c_ued", basePath)))
                    .texture("south", new ResourceLocation(modID, String.format("%s/3c_uwd", basePath)))
                    .texture("west", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("east", new ResourceLocation(modID, String.format("%s/2c_ud", basePath))).renderType("translucent");
            modelMap.put(String.format("%sc3t1", modelPath), c3t1);

            // c3t2
            var c3t2 = models().getBuilder(String.format("%s/c3t2", basePath))
                    .parent(models().getExistingFile(mcLoc("block")))
                    .element().from(-offset, -offset, -offset).to(16 + offset, 16 + offset, 16 + offset)
                    .allFaces(BaseBlockStateGenerator::buildDirectionFaces)
                    .end()
                    .texture("particle", new ResourceLocation(modID, String.format("%s/0c", basePath)))
                    .texture("down", new ResourceLocation(modID, String.format("%s/2c_we", basePath)))
                    .texture("up", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("north", new ResourceLocation(modID, String.format("%s/3c_uwe", basePath)))
                    .texture("south", new ResourceLocation(modID, String.format("%s/3c_uwe", basePath)))
                    .texture("west", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("east", new ResourceLocation(modID, String.format("%s/4c", basePath))).renderType("translucent");
            modelMap.put(String.format("%sc3t2", modelPath), c3t2);

            // c3angle
            var c3angle = models().getBuilder(String.format("%s/c3angle", basePath))
                    .parent(models().getExistingFile(mcLoc("block")))
                    .element().from(-offset, -offset, -offset).to(16 + offset, 16 + offset, 16 + offset)
                    .allFaces(BaseBlockStateGenerator::buildDirectionFaces)
                    .end()
                    .texture("particle", new ResourceLocation(modID, String.format("%s/0c", basePath)))
                    .texture("down", new ResourceLocation(modID, String.format("%s/2c_ue", basePath)))
                    .texture("up", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("north", new ResourceLocation(modID, String.format("%s/2c_uw", basePath)))
                    .texture("south", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("west", new ResourceLocation(modID, String.format("%s/2c_ue", basePath)))
                    .texture("east", new ResourceLocation(modID, String.format("%s/4c", basePath))).renderType("translucent");
            modelMap.put(String.format("%sc3angle", modelPath), c3angle);

            // c4x
            var c4x = models().getBuilder(String.format("%s/c4x", basePath))
                    .parent(models().getExistingFile(mcLoc("block")))
                    .element().from(-offset, -offset, -offset).to(16 + offset, 16 + offset, 16 + offset)
                    .allFaces(BaseBlockStateGenerator::buildDirectionFaces)
                    .end()
                    .texture("particle", new ResourceLocation(modID, String.format("%s/0c", basePath)))
                    .texture("down", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("up", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("north", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("south", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("west", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("east", new ResourceLocation(modID, String.format("%s/4c", basePath))).renderType("translucent");
            modelMap.put(String.format("%sc4x", modelPath), c4x);

            // c4angle
            var c4angle = models().getBuilder(String.format("%s/c4angle", basePath))
                    .parent(models().getExistingFile(mcLoc("block")))
                    .element().from(-offset, -offset, -offset).to(16 + offset, 16 + offset, 16 + offset)
                    .allFaces(BaseBlockStateGenerator::buildDirectionFaces)
                    .end()
                    .texture("particle", new ResourceLocation(modID, String.format("%s/0c", basePath)))
                    .texture("down", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("up", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("north", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("south", new ResourceLocation(modID, String.format("%s/3c_uwd", basePath)))
                    .texture("west", new ResourceLocation(modID, String.format("%s/4c", basePath)))
                    .texture("east", new ResourceLocation(modID, String.format("%s/3c_ued", basePath))).renderType("translucent");
            modelMap.put(String.format("%sc4angle", modelPath), c4angle);

            // middle
            var middle = models().getBuilder(String.format("%s/middle", basePath))
                    .parent(models().getExistingFile(mcLoc("block")))
                    .element().from(-offset, -offset, -offset).to(16 + offset, 16 + offset, 16 + offset)
                    .allFaces(((direction, faceBuilder) -> {
                        faceBuilder.texture("#all");
                    }))
                    .end()
                    .texture("all", new ResourceLocation(modID, String.format("%s/4c", basePath))).renderType("translucent");
            modelMap.put(String.format("%smiddle", modelPath), middle);

            // region JSON
            var jsonString = String.format(
                    "{\n" +
                            "    \"variants\": {\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=false,connected_south=false,connected_up=false,connected_west=false\": { \"model\": \"%sc0\" },\n" +
                            "\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=false,connected_south=false,connected_up=true,connected_west=false\": { \"model\": \"%sc1\" },\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=false,connected_south=false,connected_up=false,connected_west=false\": { \"model\": \"%sc1\", \"x\": 180 },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=true,connected_south=false,connected_up=false,connected_west=false\": { \"model\": \"%sc1\", \"x\": 90 },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=false,connected_south=true,connected_up=false,connected_west=false\": { \"model\": \"%sc1\", \"x\": -90 },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=false,connected_south=false,connected_up=false,connected_west=true\": { \"model\": \"%sc1\", \"x\": 90, \"y\": -90 },\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=false,connected_south=false,connected_up=false,connected_west=false\": { \"model\": \"%sc1\", \"x\": 90, \"y\": 90 },\n" +
                            "\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=false,connected_south=false,connected_up=true,connected_west=false\": { \"model\": \"%sc2angle\" },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=false,connected_south=false,connected_up=true,connected_west=true\": { \"model\": \"%sc2angle\", \"y\": 180 },\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=false,connected_south=false,connected_up=false,connected_west=false\": { \"model\": \"%sc2angle\", \"x\": 180 },\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=false,connected_south=false,connected_up=false,connected_west=true\": { \"model\": \"%sc2angle\", \"x\": 180, \"y\": 180 },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=true,connected_south=false,connected_up=true,connected_west=false\": { \"model\": \"%sc2angle\", \"y\": -90 },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=false,connected_south=true,connected_up=true,connected_west=false\": { \"model\": \"%sc2angle\", \"y\": 90 },\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=true,connected_south=false,connected_up=false,connected_west=false\": { \"model\": \"%sc2angle\", \"x\": 180, \"y\": -90 },\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=false,connected_south=true,connected_up=false,connected_west=false\": { \"model\": \"%sc2angle\", \"x\": 180, \"y\": 90 },\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=true,connected_south=false,connected_up=false,connected_west=false\": { \"model\": \"%sc2angle\", \"x\": 90 },\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=false,connected_south=true,connected_up=false,connected_west=false\": { \"model\": \"%sc2angle\", \"x\": -90 },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=true,connected_south=false,connected_up=false,connected_west=true\": { \"model\": \"%sc2angle\", \"x\": 90, \"y\": -90 },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=false,connected_south=true,connected_up=false,connected_west=true\": { \"model\": \"%sc2angle\", \"x\": -90, \"y\": 90 },\n" +
                            "\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=false,connected_south=false,connected_up=true,connected_west=false\": { \"model\": \"%sc2\" },\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=false,connected_south=false,connected_up=false,connected_west=true\": { \"model\": \"%sc2\", \"x\": -90, \"y\": 90  },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=true,connected_south=true,connected_up=false,connected_west=false\": { \"model\": \"%sc2\", \"x\": -90 },\n" +
                            "\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=false,connected_south=false,connected_up=true,connected_west=true\": { \"model\": \"%sc3t1\" },\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=false,connected_south=true,connected_up=true,connected_west=false\": { \"model\": \"%sc3t1\", \"y\": -90 },\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=false,connected_south=false,connected_up=true,connected_west=false\": { \"model\": \"%sc3t1\", \"y\": -180 },\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=true,connected_south=false,connected_up=true,connected_west=false\": { \"model\": \"%sc3t1\", \"y\": 90 },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=true,connected_south=true,connected_up=false,connected_west=true\": { \"model\": \"%sc3t1\", \"x\": -90 },\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=true,connected_south=true,connected_up=false,connected_west=false\": { \"model\": \"%sc3t1\", \"x\": -90, \"y\": 180 },\n" +
                            "\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=false,connected_south=false,connected_up=true,connected_west=true\": { \"model\": \"%sc3t2\" },\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=false,connected_south=true,connected_up=false,connected_west=true\": { \"model\": \"%sc3t2\", \"x\": -90 },\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=false,connected_south=false,connected_up=false,connected_west=true\": { \"model\": \"%sc3t2\", \"x\": 180 },\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=true,connected_south=false,connected_up=false,connected_west=true\": { \"model\": \"%sc3t2\", \"x\": 90 },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=true,connected_south=true,connected_up=true,connected_west=false\": { \"model\": \"%sc3t2\", \"y\": 90 },\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=true,connected_south=true,connected_up=false,connected_west=false\": { \"model\": \"%sc3t2\", \"x\": 180, \"y\": 90 },\n" +
                            "\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=false,connected_south=true,connected_up=true,connected_west=false\": { \"model\": \"%sc3angle\" },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=false,connected_south=true,connected_up=true,connected_west=true\": { \"model\": \"%sc3angle\", \"y\": 90 },\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=true,connected_south=false,connected_up=true,connected_west=false\": { \"model\": \"%sc3angle\", \"x\": 90 },\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=true,connected_south=false,connected_up=false,connected_west=false\": { \"model\": \"%sc3angle\", \"x\": 180 },\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=false,connected_south=true,connected_up=false,connected_west=false\": { \"model\": \"%sc3angle\", \"x\": 270 },\n" +
                            "\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=false,connected_south=true,connected_up=false,connected_west=true\": { \"model\": \"%sc3angle\", \"x\": 180, \"y\": -180 },\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=true,connected_south=false,connected_up=false,connected_west=true\": { \"model\": \"%sc3angle\", \"x\": -90, \"y\": -180 },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=true,connected_south=false,connected_up=true,connected_west=true\": { \"model\": \"%sc3angle\", \"x\": 90, \"y\": -90 },\n" +
                            "\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=false,connected_south=false,connected_up=true,connected_west=true\": { \"model\": \"%sc4x\" },\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=true,connected_south=true,connected_up=false,connected_west=true\": { \"model\": \"%sc4x\", \"x\": 90 },\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=true,connected_south=true,connected_up=true,connected_west=false\": { \"model\": \"%sc4x\", \"y\": 90 },\n" +
                            "\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=true,connected_south=false,connected_up=true,connected_west=true\": { \"model\": \"%sc4angle\" },\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=true,connected_south=true,connected_up=false,connected_west=true\": { \"model\": \"%sc4angle\", \"x\": 90 },\n" +
                            "        \"connected_down=false,connected_east=false,connected_north=true,connected_south=true,connected_up=true,connected_west=true\": { \"model\": \"%sc4angle\", \"x\": -90 },\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=false,connected_south=true,connected_up=true,connected_west=true\": { \"model\": \"%sc4angle\", \"y\": -90 },\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=true,connected_south=false,connected_up=true,connected_west=false\": { \"model\": \"%sc4angle\", \"y\": 90 },\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=false,connected_south=true,connected_up=true,connected_west=false\": { \"model\": \"%sc4angle\", \"y\": 180 },\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=true,connected_south=true,connected_up=true,connected_west=false\": { \"model\": \"%sc4angle\", \"x\": -90, \"y\": 180 },\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=true,connected_south=true,connected_up=false,connected_west=false\": { \"model\": \"%sc4angle\", \"x\": 90, \"y\": 180 },\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=false,connected_south=true,connected_up=true,connected_west=true\": { \"model\": \"%sc4angle\", \"x\": -90, \"y\": -90 },\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=false,connected_south=true,connected_up=false,connected_west=true\": { \"model\": \"%sc4angle\", \"x\": -270, \"y\": -90 },\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=true,connected_south=false,connected_up=true,connected_west=true\": { \"model\": \"%sc4angle\", \"x\": -90, \"y\": 90 },\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=true,connected_south=false,connected_up=false,connected_west=true\": { \"model\": \"%sc4angle\", \"x\": -270, \"y\": -270 },\n" +
                            "\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=true,connected_south=true,connected_up=false,connected_west=true\": { \"model\": \"%smiddle\" },\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=false,connected_south=true,connected_up=true,connected_west=true\": { \"model\": \"%smiddle\" },\n" +
                            "        \"connected_down=true,connected_east=false,connected_north=true,connected_south=true,connected_up=true,connected_west=true\": { \"model\": \"%smiddle\" },\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=true,connected_south=true,connected_up=true,connected_west=false\": { \"model\": \"%smiddle\" },\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=true,connected_south=false,connected_up=true,connected_west=true\": { \"model\": \"%smiddle\" },\n" +
                            "        \"connected_down=false,connected_east=true,connected_north=true,connected_south=true,connected_up=true,connected_west=true\": { \"model\": \"%smiddle\" },\n" +
                            "\n" +
                            "        \"connected_down=true,connected_east=true,connected_north=true,connected_south=true,connected_up=true,connected_west=true\": { \"model\": \"%smiddle\" }\n" +
                            "    }\n" +
                            "}",
                    modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath,
                    modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath,
                    modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath,
                    modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath,
                    modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath,
                    modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath,
                    modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath,
                    modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath, modelPath
            );
            // endregion

            // Build the blockstate file
            getVariantBuilder(block)
                    .forAllStates(state -> {
                        var modelVariant = getModelVariant(state, jsonString);
                        return ConfiguredModel.builder()
                                .modelFile(modelMap.get(modelVariant.getModel()))
                                .rotationY(modelVariant.getY())
                                .rotationX(modelVariant.getX())
                                .build();
                    });
        }
    }

    /**
     * Returns the model variant based on the connected states and input JSON object.
     *
     * @return The model variant object
     */
    public static ConnectedModelInfo getModelVariant(BlockState state, String jsonString) {
        return getModelVariant(state.getValue(ConnectedTexture.CONNECTED_DOWN),
                state.getValue(ConnectedTexture.CONNECTED_EAST),
                state.getValue(ConnectedTexture.CONNECTED_NORTH),
                state.getValue(ConnectedTexture.CONNECTED_SOUTH),
                state.getValue(ConnectedTexture.CONNECTED_UP),
                state.getValue(ConnectedTexture.CONNECTED_WEST),
                JsonParser.parseString(jsonString).getAsJsonObject());
    }

    /**
     * Retrieves the model variant based on the Boolean states of the connected sides and the input JSON object.
     *
     * @param connected_down  true if the down side is connected, false otherwise
     * @param connected_east  true if the east side is connected, false otherwise
     * @param connected_north true if the north side is connected, false otherwise
     * @param connected_south true if the south side is connected, false otherwise
     * @param connected_up    true if the up side is connected, false otherwise
     * @param connected_west  true if the west side is connected, false otherwise
     * @param input           the input JSON object containing the variants
     * @return the model variant object based on the provided Boolean states
     */
    public static ConnectedModelInfo getModelVariant(boolean connected_down, boolean connected_east,
                                                     boolean connected_north, boolean connected_south,
                                                     boolean connected_up, boolean connected_west,
                                                     JsonObject input) {
        // Gets the variants property from the input object
        JsonObject variants = input.getAsJsonObject("variants");

        // Creates a string builder to store the key
        StringBuilder key = new StringBuilder();

        // Appends the Boolean states to the key
        key.append("connected_down=" + connected_down + ",");
        key.append("connected_east=" + connected_east + ",");
        key.append("connected_north=" + connected_north + ",");
        key.append("connected_south=" + connected_south + ",");
        key.append("connected_up=" + connected_up + ",");
        key.append("connected_west=" + connected_west);

        // Gets the value of the key, which is another JSON object
        JsonObject value = variants.getAsJsonObject(key.toString());

        // Creates a model variant object from the value
        ConnectedModelInfo modelVariant = new ConnectedModelInfo(value);

        // Returns the model variant object
        return modelVariant;
    }
}
