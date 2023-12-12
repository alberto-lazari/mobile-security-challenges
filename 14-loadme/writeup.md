# Challenge
It's required to find a flag, although it's not clear where it's checked

## Flag
The flag is `FLAG{memores_acti_prudentes_futuri}`

# Solution
## APK
By decompiling the `.apk`, it appears that the `DoStuff` class has many obscured methods.
Of these, one is used to retrieve some code via HTTP.
The URL is obtained by the `gu()` function, that returns `https://www.math.unipd.it/~elosiouk/test.dex`

## Remote code
The file can be downloaded and decompiled.
The class `LoadImage` also loads some code from a file

By decrypting the strings used to dynamically load the code it appears that a `.png` file (in the `assets` directory of the apk) is used as a `.dex` file.
The image is a `logo.png` file

## Logo
By unzipping the apk and retrieving the logo file it's possible to decompile it (it being actually a `.dex` file)

Finally, there, by looking into the `Check` class, the flag can be found

# Decrypting
It's possible to slightly edit the decompiled code itself to decrypt the obscured strings.
Here's a possible solver:
```java
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class Solve {
    private static byte[] initVector = {-34, -83, -66, -17, -34, -83, -66, -17, -34, -83, -66, -17, -34, -83, -66, -17};
    private String flag;

    private String gu() {
        return ds("Bj9yLW24l0OpvkoxoPXLb+UqJGp1t1slVcl/aTlHM+iolk4i083NV8E1LNJj/6w1");
    }

    private String ds(String enc) {
        try {
            byte[] ciphertext = Base64.getDecoder().decode(enc.getBytes());
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec("mobiotseccomkey!".getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, skeySpec, iv);
            String decoded = new String(cipher.doFinal(ciphertext));
            return decoded;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String generateClass() {
        return decryptString("zbTHGeQeUUxj3dJ43fDwkcKmk4erD60GZXReeWl3ITA=");
    }

    private static String generateMethod() {
        return decryptString("LlzQOUB3opWgJZeFNI/Jsg==");
    }

    private static String getAssetsName() {
        return decryptString("oxTrCOohrr2fAZfJZAjcNA==");
    }

    private static String getCodeName() {
        return decryptString("FxojiPxNKXdtYiY65LK1CA==");
    }

    private static String decryptString(String s) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec("mobiotseccomkey!".getBytes("UTF-8"), "AES");
            IvParameterSpec iv = new IvParameterSpec(initVector);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, skeySpec, iv);
            String decoded = new String(cipher.doFinal(Base64.getDecoder().decode(s.getBytes())));
            return decoded;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // https://www.math.unipd.it/~elosiouk/test.dex
        final String gu = new Solve().gu();
        System.out.println("Class loaded from " + gu);

        // There's a .png that is actually a .dex
        // there you can find the necessary classes
        System.out.println(generateClass());
        System.out.println(generateMethod());
        System.out.println(getAssetsName());
        System.out.println(getCodeName());
    }
}
```
