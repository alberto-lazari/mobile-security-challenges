# Explicit vs implicit intents
- Explicit is directed to a specific app -> the calling app is sure about who's going to catch it
- Implicit is just sent to the OS, available to anyone that filters for it in its manifest
    - There's a catch: this attack only works with just a single app declaring the intent.
    Otherwise a chooser is displayed

# Solution steps
0. Simple activity receiver: show that victimapp sends the action and how to catch it
0. Content of the activity: show the uri
0. Content resolver: why is it needed? It returns a `FileInpuntStream` -> will be passed to the hashing function
0. Do the hashing:
    - Import the library
    - Call `DigestUtils.sha256Hex` (not actually that, but prof's `org.bouncycastle.util.encoders.Hex`)
0. Set the result
