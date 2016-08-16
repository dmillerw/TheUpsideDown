package me.dmillerw.upsidedown.asm.transform;

/**
 * Created by dmillerw
 */
public interface ITransformer {

    public String[] getClasses();
    public byte[] transform(String name, byte[] data);
}
