import java.security.MessageDigest;

/* loaded from: classes3.dex */
class PinChecker implements Runnable {
    final int rangeStart;

    PinChecker(int s) {
        rangeStart = s;
    }

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
