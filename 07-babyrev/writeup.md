# Challenge
It is requested to deduce the flag by decompiling the code from the given `.apk`

The flag is `FLAG{ScIeNtIa_pOtEnTiA_EsT}`

# Solution
The `checkFlag(ctx, flag)` of the `FlagChecker` class provides all the necessary information to reconstruct the flag.
It consists of a single `return` statement with multiple checks

## Flag structure
The structure is in the form `FLAG{  _  _  }`: three parts, separated by underscores

### FLAG{...}
This peace of code tells that the flag is in the usual `FLAG{...}` form and it is 27 characters long (characters in positions 0-26)
```java
flag.startsWith("FLAG{") &&
    new StringBuilder(flag)
        .reverse()
        .toString()
        .charAt(0) == '}' &&
    flag.length() == 27
```

### Separators
There are two underscores, that separates three parts.
This can be deduced from
```java
flag.charAt(13) == '_' &&
flag.charAt(22) == '_'
```

### Case
The line `flag.substring(5, flag.length() - 1).matches(getR())` tells that the inner flag casing is alternating on upper and lower-case characters (or underscore), because it has to match the regex `/[A-Z_][a-z_][A-Z_][a-z_].../`

## Fist part
This tells that the inner flag starts with "scientia" (ignoring case)
```java
flag.toLowerCase()
    .substring(5)
    .startsWith("scientia")
```

## Middle part
This tells that the middle part (characters in positions 14-22) is "pOtEnTiA"

To decode the part it's sufficient to re-encode the string that the code is trying to match, because what the function `bam(s)` is doing is just swapping characters in a-m to the same relative position in n-z (and opposite, also on uppercase characters)
```java
// bam("cBgRaGvN") = "pOtEnTiA"
bam(flag.substring(
    // 14
    (int) (Math.pow((double) getZ(), (double) getX()) - 2.0d),
    // 22
    ((int) Math.pow((double) getX(), (double) (getX() + getY()))) + (-10))
).equals("cBgRaGvN")
```

## Last part
This tells that the last part of the flag is "est" (ignoring case), because the string `last_part` found in `res/values/strings.xml` is "tse" and it is checked in reverse
```java
new StringBuilder(flag)
    .reverse()
    .toString()
    .toLowerCase()
    .substring(1)
    .startsWith(
        // tse (from `res/values/strings.xml`)
        ctx.getString(R.string.last_part)
    )
```

# Useless code
The code contains lines of code that provide redundant, but barely useful, information:
```java
// Useless stuff
flag.toLowerCase().charAt(12) == 'a' &&
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
    )
```
