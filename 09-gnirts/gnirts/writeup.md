# Challenge
A checker application is given. In order to deduce the flag the apk can be decompiled to reverse the flag checking process, done by the `checkFlag()` method of the `FlagChecker` class

## Flag
The flag is `FLAG{sic-parvis-MAGNA-28jAn1596}`

# Solution
An annotated version of the function is given, showing the deduction steps.

At a high level the early checks give hints on the structure and format of the flag, while the actual content is revealed by the `me` function calls.
Those log what they are doing, probably comparing strings, but it doesn't matter. The second logged hash `s2` is the MD5 of the flag part, which can be easily reversed with a third party tool

## Annotated function
```
    public static boolean checkFlag(Context ctx, String flag) {
        if (!flag.startsWith("FLAG{") || !flag.endsWith("}")) {
            return false;
        }
        try {
            String core = flag.substring(5, 31);
            // String[] ps = core.split(foo());
            String[] ps = core.split('-');
            // FLAG{...-...-...-...}
            if (ps.length != 4) {
                return false;
            }
            // FLAG{aaa-...-AAA-[aAn]}
            if (!bim(ps[0]) || !bum(ps[2]) || !bam(ps[3])) {
            } else if (
                // FLAG{aaa-aaa-AAA-nnaAannnn}
                !core.replaceAll("[A-Z]", "X").replaceAll("[a-z]", "x").replaceAll("[0-9]", " ").matches(
                    "[a-z]+.[a-z]+.[X ]+.  xXx    +"
                )
            ) {
                return false;
            }
            char[] syms = new char[3];
            int[] idxs = {8, 15, 21};
            Set<Character> chars = new HashSet<>();
            for (int i = 0; i < syms.length; i++) {
                syms[i] = flag.charAt(idxs[i]);
                chars.add(Character.valueOf(syms[i]));
            }
            int sum = 0;
            for (char c : syms) {
                sum += c;
            }
            // FLAG{aaa-aaaaaa-AAAAA-nnaAannnn}
            if (sum == 135 && chars.size() == 1) {
                if (
                    // FLAG{sic-aaaaaa-AAAAA-nnaAannnn}
                    me(ctx,
                        dh(gs(
                                ctx.getString(R.string.ct1),
                                ctx.getString(R.string.k1)
                            ),
                            ps[0]
                        ),
                        ctx.getString(R.string.t1)
                    ) &&
                    // FLAG{sic-parvis-AAAAA-nnaAannnn}
                    me(ctx,
                        dh(gs(ctx.getString(R.string.ct2), ctx.getString(R.string.k2)), ps[1]),
                        ctx.getString(R.string.t2)
                    ) &&
                    // FLAG{sic-parvis-MAGNA-28jAn1596}
                    me(ctx,
                        dh(gs(ctx.getString(R.string.ct3), ctx.getString(R.string.k3)), ps[2]),
                        ctx.getString(R.string.t3)
                    ) &&
                    me(ctx,
                        dh(gs(ctx.getString(R.string.ct4), ctx.getString(R.string.k4)), ps[3]),
                        ctx.getString(R.string.t4)
                    )
                ) {
                    if (me(ctx, dh(gs(ctx.getString(R.string.ct5), ctx.getString(R.string.k5)), flag), ctx.getString(R.string.t5))) {
                        return true;
                    }
                } else {
                }
            } else {
            }
            return false;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
```
