package com.teambr.nucleus.client.gui.misc;

import com.teambr.nucleus.util.RenderUtils;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

/**
 * This file was created for Nucleus
 *
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/13/2017
 */
public class Trackball {
    // Variables
    public Vector3f dragStart;
    public Matrix4f lastTransform = new Matrix4f();

    public Vector3f calculateSpherePoint(float x, float y) {
        Vector3f result = new Vector3f(x, y, 0);

        float sqrZ = 1 - result.dot(result);

        if(sqrZ > 0)
            result.set(result.getX(), result.getY(), (float) Math.sqrt(sqrZ));
        else
            result.normalize();

        return result;
    }

    public Matrix4f getTransform(float mouseX, float mouseY) {
        if(dragStart == null)
            return  lastTransform;

        Vector3f current = calculateSpherePoint(mouseX, mouseY);

        float dot = dragStart.dot(current);

        if(Math.abs(dot- 1) < 0.0001)
            return lastTransform;

        Vector3f axis = new Vector3f(dragStart);
        axis.cross(current, dragStart);

        try {
            axis.normalize();
        } catch (IllegalStateException e){
            return lastTransform;
        } catch (Exception ignored) {}

        float angle = (float) (2 * Math.acos(dot));

        Matrix4f rotation = new Matrix4f();
        RenderUtils.rotateMatrix4f(rotation, angle, axis);
        return RenderUtils.mul(rotation, lastTransform, null);
    }

    public void applyTransform(float mouseX, float mouseY, boolean isDragging) {
        RenderUtils.loadMatrix(isDragging ? getTransform(mouseX, mouseY) : lastTransform);
    }

    public void startDrag(float mouseX, float mouseY) {
        dragStart = calculateSpherePoint(mouseX, mouseY);
    }

    public void endDrag(float mouseX, float mouseY) {
        lastTransform = getTransform(mouseX, mouseY);
    }
}
