# Challenge
The flag is checked using a native library `libgoingnative.so`

It is required to deduce the flag using the native code

## Flag
The flag is `FLAG{status_1234_quo}`

# Solution
It is useful to look at a decompiled version of the `validate_input()` function, used from the Java `checkFlag` method (using Ghidra for instance):
```c
/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

undefined4 validate_input(char *param_1)

{
  int iVar1;
  int local_2c;
  char *local_24;
  undefined4 local_20;
  undefined2 local_1a;
  int local_18;
  
  local_18 = ___stack_chk_guard;
  local_1a = 0x5f;
  local_24 = strtok(param_1,(char *)&local_1a);
  local_2c = 0;
  do {
    if (local_24 == (char *)0x0) {
      if (local_2c == 0) {
        local_20 = 0xffffffff;
      }
      else {
        local_20 = 0;
      }
LAB_0001076a:
      if (___stack_chk_guard == local_18) {
        return local_20;
      }
                    /* WARNING: Subroutine does not return */
      __stack_chk_fail();
    }
    if (local_2c == 0) {
      iVar1 = strcmp(local_24,"status");
      if (iVar1 != 0) {
        local_20 = 0xffffffff;
        goto LAB_0001076a;
      }
    }
    if (local_2c == 1) {
      iVar1 = strcmp(local_24,"1234");
      if (iVar1 != 0) {
        local_20 = 0xffffffff;
        goto LAB_0001076a;
      }
    }
    if (local_2c == 2) {
      iVar1 = strcmp(local_24,"quo");
      if (iVar1 != 0) {
        local_20 = 0xffffffff;
        goto LAB_0001076a;
      }
    }
    local_24 = strtok((char *)0x0,(char *)&local_1a);
    local_2c = local_2c + 1;
  } while( true );
}
```

The function is a simple unfolded for loop, that checks that every piece (token) of the flag is the correct one. The check is done using plain-text strings, that are, respectively:
- `status`
- `1234`
- `quo`

The tokens are separated using `_`, so the final string can be reconstructed
