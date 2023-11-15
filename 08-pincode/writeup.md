# Challenge
It is required to find a six-digits PIN to unlock the flag.
Since it is checked by comparing its MD5 encoding (multiple times) there's no other way other than try and brute-force it

## Result
- FLAG: `FLAG{in_vino_veritas}`
- PIN: `703958`

# Solution
The actual decompiled code to check the PIN can be used, after a couple small adjustments:
```java
public static boolean checkPin(String pin) {
    if (pin.length() != 6) {
        return false;
    }
    try {
        byte[] pinBytes = pin.getBytes();
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 400; j++) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(pinBytes);
                pinBytes = (byte[]) md.digest().clone();
            }
        }
        final String hexString = toHexString(pinBytes);
        System.out.println("PIN: " + pin + "    HASH: " + hexString);
        return hexString.equals("d04988522ddfed3133cc24fb6924eae9");
    } catch (Exception e) {
        return false;
    }
}

public static String toHexString(byte[] bytes) {
    StringBuilder hexString = new StringBuilder();
    for (byte b : bytes) {
        String hex = Integer.toHexString(b & 255);
        if (hex.length() == 1) {
            hexString.append('0');
        }
        hexString.append(hex);
    }
    return hexString.toString();
}
```

Most notably, logging has been added, for debugging and to catch the PIN

Now that code can be iterated for all the possible PINs.
It is suggested to do it in a multi-threaded way, to check multiple PINs at the same time:
```java
class PinChecker implements Runnable {
    final int rangeStart;

    PinChecker(int s) {
        rangeStart = s;
    }

    @Override
    public void run() {
        final int rangeEnd = rangeStart + 100000;
        for (int i = rangeStart; i < rangeEnd; i++) {
            final String pin = String.format("%06d", i);
            if (checkPin(pin)) {
                throw new RuntimeException("The PIN is: " + pin);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println("Starting thread " + i);
            new Thread(new PinChecker(i * 100000)).start();
        }
    }
}
```

Then the PIN can be found by piping the (extremely long and dense) output to `grep`:
```bash
javac bruteforce.java && java PinChecker 2>&1 |
    grep -o 'The PIN is: .*\|PIN: .* d04988522ddfed3133cc24fb6924eae9'
```

## FLAG
The flag is much easier to find, since it can actually be found as plain text in the decompiled source code of the application
