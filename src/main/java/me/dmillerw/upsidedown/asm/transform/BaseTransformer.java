package me.dmillerw.upsidedown.asm.transform;

import org.objectweb.asm.Opcodes;

/**
 * Created by dmillerw
 */
public abstract class BaseTransformer implements Opcodes {

    public abstract String[] getClasses();
    public abstract byte[] transform(String name, byte[] data);
}
