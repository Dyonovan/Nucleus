package com.pauljoda.nucleus.helper;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ProjectionHelper {

    private IntBuffer viewport = GLAllocation.createDirectByteBuffer(16).asIntBuffer();

    private FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);

    private FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);

    private FloatBuffer objectCoords = GLAllocation.createDirectFloatBuffer(3);

    private FloatBuffer winCoords = GLAllocation.createDirectFloatBuffer(3);

    public void updateMatrices() {
        GL11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, modelview);
        GL11.glGetFloatv(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetIntegerv(GL11.GL_VIEWPORT, viewport);
    }

    public Vector3d unproject(double winX, double winY, float winZ) {
       // GLU.gluUnProject(winX, winY, winZ, modelview, projection, viewport, objectCoords);

        float objectX = objectCoords.get(0);
        float objectY = objectCoords.get(1);
        float objectZ = objectCoords.get(2);

        return new Vector3d(objectX, objectY, objectZ);
    }

    public Vector3d project(float objX, float objY, float objZ) {
     //   GLU.gluProject(objX, objY, objZ, modelview, projection, viewport, winCoords);

        float winX = winCoords.get(0);
        float winY = winCoords.get(1);
        float winZ = winCoords.get(2);

        return new Vector3d(winX, winY, winZ);
    }
}