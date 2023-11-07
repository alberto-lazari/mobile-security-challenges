package com.mobiotsec.babyrev;

import android.content.Context;
/* loaded from: classes3.dex */
public class FlagChecker {
    // FLAG{ScIeNtIa_pOtEnTiA_EsT}
    public static boolean checkFlag(Context ctx, String flag) {
        return
            flag.startsWith("FLAG{") &&
            new StringBuilder(flag)
                .reverse()
                .toString()
                .charAt(0) == '}' &&
            flag.length() == 27 &&
            flag.toLowerCase()
                .substring(5)
                .startsWith("scientia") &&
            new StringBuilder(flag)
                .reverse()
                .toString()
                .toLowerCase()
                .substring(1)
                .startsWith(
                    // tse (from `res/values/strings.xml`)
                    ctx.getString(R.string.last_part)
                ) &&
            // Useless stuff
            flag.toLowerCase().charAt(12) == 'a' &&
            // Separators
            flag.charAt(13) == '_' &&
            flag.charAt(22) == '_' &&
            // Other useless stuff
            flag.toLowerCase().charAt(12) == 'a' &&
            flag.toLowerCase().charAt(12) == flag.toLowerCase().charAt(21) &&
            // Same information as above: 12 = 21
            flag.toLowerCase().charAt(
                    // 12
                    (int) ( Math.pow((double) getX(), (double) getX()) + Math.pow((double) getX(), (double) getY()))
                ) == flag.toLowerCase().charAt(
                    // 21
                    (int) (Math.pow((double) getZ(), (double) getX()) + 5.0d)
                ) &&
            // bam("cBgRaGvN") = "pOtEnTiA"
            bam(flag.substring(
                // 14
                (int) (Math.pow((double) getZ(), (double) getX()) - 2.0d),
                // 22
                ((int) Math.pow((double) getX(), (double) (getX() + getY()))) + (-10))
            ).equals("cBgRaGvN") &&
            // Case of inner flag is AbCdE...
            flag.substring(5, flag.length() - 1).matches(getR());
    }

    private static int getX() {
        return 2;
    }

    private static int getY() {
        return 3;
    }

    private static int getZ() {
        return 4;
    }

    // Swap a-m n-z
    // just use it again to obtain original string
    private static String bam(String s) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'm') {
                c = (char) (c + '\r');
            } else if (c >= 'A' && c <= 'M') {
                c = (char) (c + '\r');
            } else if (c >= 'n' && c <= 'z') {
                c = (char) (c - '\r');
            } else if (c >= 'N' && c <= 'Z') {
                c = (char) (c - '\r');
            }
            out.append(c);
        }
        return out.toString();
    }

    public static String getR() {
        StringBuilder r = new StringBuilder();
        boolean upper = true;
        for (int i = 0; i < 21; i++) {
            if (upper) {
                r.append("[A-Z_]");
            } else {
                r.append("[a-z_]");
            }
            upper = !upper;
        }
        return r.toString();
    }
}
