# Challenge
Another check flag using a native library

## Flag
The flag is simply found in the `MainActivity` code: `FLAG{omnia_prius_experiri_quam_armis_sapientem_decet}`

## PIN
The pin to get the flag is the actually interesting value and it can be any sequence of five numbers that sums to more than 100, like `100 0 0 0 0`

# Solution
In order to understand what the application does, when checking for the input flag, a decompilation of the two native functions is useful

## Decompilation
- Preprocessing function:
```c
bool preprocessing(char* input) {
    char separator = ' ';
    char* number_string = strtok(input, &separator);
    int pin[5];
    for (int i = 0; number_string != NULL; i++) {
        sscanf(number_string, "%d", pin + i);
        number_string = strtok(NULL, &separator);
    }
    return i == 5 && validate(pin);
}
```

- Validate function:
```c
bool validate(int[5] array) {
    int sum = 0;
    for (int i = 0; i < 5; i++) {
        sum += array[i];
    }
    return sum >= 100;
}
```

## Conclusions
From the decompilation it appears that the flag that is required as input is actually a sequence of five numbers, separated by spaces.
The only check done by the validation is that the sum of the numbers has to be at least 100

When entering a valid pin the application tells the flag
