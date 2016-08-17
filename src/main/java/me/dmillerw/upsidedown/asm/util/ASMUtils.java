package me.dmillerw.upsidedown.asm.util;

/**
 * Created by dmillerw
 */
public class ASMUtils {

    public static String buildDescription(String ... args) {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        for (int i = 0; i < args.length - 1; i++) {
            builder.append(formatClassName(args[i]));
        }
        builder.append(')');
        builder.append(formatClassName(args[args.length - 1]));
        return builder.toString();
    }

    private static String formatClassName(String arg) {
        if (arg.length() == 1)
            return arg;

        arg = arg.replace(".", "/");
        arg = 'L' + arg + ';';
        return arg;
    }
}
