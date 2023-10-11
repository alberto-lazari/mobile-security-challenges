#import "common.typ": *
#import "@preview/xarrow:0.2.0": xarrow

#show: slides.with(
  authors:        [Alberto Lazari],
  short-authors:  [Alberto Lazari],
  title:          [FileHasher],
  subtitle:       [Mobile security -- Challenge 01],
  short-title:    [#v(.5em) Challenge 01 -- FileHasher],
  date:           [October 13, 2023],
  theme: bristol-theme(
    color:      unipd-red,
    logo:       "/images/unipd-logo.svg",
    // Don't use watermarks, by using a blank image (`none` can't be used)
    watermark:  "/images/blank.png",
    secondlogo: "/images/blank.png",
  )
)

#set text(font: "Arial")

#show raw: itself => {
  set text(font: "Menlo")
  itself
}
// Add background to monospace text
#show raw.where(block: false): box.with(
  fill: luma(220),
  inset: (x: 3pt, y: 0pt),
  outset: (y: 8pt),
  radius: 4pt,
)
#show raw.where(block: true): block.with(
  fill: luma(220),
  inset: 10pt,
  radius: 10pt,
)

#show figure: itself => {
  set text(size: 16pt)
  itself
}

#slide(theme-variant: "title slide")

#new-section[Challenge background]
#slide(title: [Main topics])[
  #align(center, grid(
    columns: (1fr, 1fr),
    [
      === Activities
      #figure(image(width: 80%, "images/activities.png"))
    ],
    uncover(2)[
      === Intents
      #set text(size: .6em)

      #align(horizon)[
        `MainActivity`
        #xarrow(text(size: .7em, font: "Menlo", "com.example.app.OtherActivity"))
        `OtherActivity`
      ]
    ]
  ))
]

#slide(title: [Implicit intents])[
  #only("1-2", align(horizon + center, grid(
    columns: (1fr, 1fr, 1fr),
    gutter: 1em,
    uncover(1, say[Hey, could you find someone that can do this for me?]),
    none,
    uncover(2, say[Sure!]),

    [`AnExampleApp`],
    xarrow(text(size: .7em, font: "Menlo", "com.example.intent.action.OPEN_A_LINK")),
    image(width: 3em, "images/android.png"),
  )))

  #only("3-", align(horizon + center, grid(
    columns: (1fr, 1fr),
    gutter: 1em,
    uncover(3, say[Can anyone do action #text(size: .85em, font: "Menlo", "com.example.intent.action.OPEN_A_LINK")?]),
    uncover(4, say[I can do it!]),

    image(width: 3em, "images/android.png"),
    uncover(4)[`SomeBrowser`],
  )))
]

#new-section[The challenge]
#slide(title: [How does it work?])[
  #align(horizon + center, grid(
    columns: (1fr, 1fr, 1fr),
    gutter: 1em,
    {
      only("1-4", uncover(1, say[Can someone generate the hash of a file for me, please?]))
      only(5, say[Thanks, the flag is #strong[FLAG{...}]])
    },
    none,
    {
      only("1-2", uncover(2, say[Of course!]))
      only(3, text(size: .7em, "*doing stuff*"))
      only(4, say[Here's your hash])
    },

    [`VictimApp`],
    {
      only("1-2", xarrow(text(size: .7em, font: "Menlo", "com.mobiotsec.intent.action.HASH_FILE")))
      only(4, xarrow(
        sym: sym.arrow.l,
        width: 10em,
        text(size: .7em, font: "Menlo", "hash")
      ))
    },
    {
      uncover("2-")[`MaliciousApp`]
    }
  ))
]

#slide(title: "#TODO")[
  #line-by-line(mode: "transparent")[
    1. Catch the intent
    2. Read the file
    3. Hash the file
    4. Return the result
  ]
]

#new-section[Implementation]
#slide(title: [Catch the intent])[
  #align(horizon, grid(
    columns: (2fr, 3fr),
    gutter: 1em,
    {
      set text(size: .9em)
      uncover(mode: "transparent", "1-")[- Create simple activity]
      uncover(mode: "transparent", "2-")[- Create barebones layout]
      uncover(mode: "transparent", "3-")[- Declare intent filter]
      uncover(mode: "transparent", "4-")[- See the result]
    },
    {
      align(center, box(height: 60%, {
        only(1, file(name: "java/com/example/maliciousapp/HashFile.kt")[
          ```kotlin
          class HashFile : AppCompatActivity() {
              override fun onCreate(savedInstanceState: Bundle?) {
                  // Display activity layout
                  super.onCreate(savedInstanceState)
                  setContentView(R.layout.hash_file)
              }
          }
          ```
        ])

        only(2, file(name: "res/layout/hash_file.xml")[
          ```xml
          <RelativeLayout
              xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:gravity="center">

              <TextView
                  android:id="@+id/debug_text"
                  android:layout_width="wrap_content"
                  android:layout_height="wrap_content"
                  android:text="Debug info will appear here"
                  android:textSize="24sp" />
          </RelativeLayout>

          ```
        ])

        only(3, file(name: "AndroidManifest.xml")[
          ```xml
          ...
          <activity android:name=".MainActivity" android:exported="true">
            <intent-filter>
              <category android:name="android.intent.category.LAUNCHER" />
              <action android:name="android.intent.action.MAIN" />
            </intent-filter>
          </activity>
          <activity android:name=".HashFile" android:exported="true">
            <intent-filter>
              <action android:name="com.mobiotsec.intent.action.HASHFILE" />
              <category android:name="android.intent.category.DEFAULT" />
              <!-- Look with `adb logcat` -->
              <data android:mimeType="text/plain" />
            </intent-filter>
          </activity>
          ```
        ])

        only(4, image(height: 90%, "images/activity.png"))
      }))
    }
  ))
]

#slide(title: [Read the file])[
  #align(horizon, grid(
    columns: (2fr, 3fr),
    gutter: 1em,
    {
      set text(size: .9em)
      uncover(mode: "transparent", "1-")[- Get the intent data]
      uncover(mode: "transparent", "2-")[- View it]
      uncover(mode: "transparent", "3-")[- Read the file content]
    },
    {
      align(center, box(height: 60%, {
        only(1, file(name: "java/com/example/maliciousapp/HashFile.kt")[
          ```kotlin
          val debugTextField = findViewById<TextView>(R.id.debug_text)
          debugTextField.text = intent.data?.toString()
          ```
        ])

        only(2, image(height: 90%, "images/file-uri.png"))

        only(3, file(name: "java/com/example/maliciousapp/HashFile.kt")[
          ```kotlin
          val fileUri = intent.data
          if (fileUri != null) {
              val bytes = contentResolver
                  .openInputStream(fileUri)
                  ?.readAllBytes()
          }
          ```
        ])
      }))
    }
  ))
]

#slide(title: [Hash the file])[
  #align(horizon, grid(
    columns: (2fr, 3fr),
    gutter: 1em,
    {
      set text(size: .9em)
      uncover(mode: "transparent", "1-")[- Hash the bytes]
      uncover(mode: "transparent", "2-")[- Get a string representation of the hashed bytes]
      uncover(mode: "transparent", "3-")[- Print the hash #uncover(4)[(looks like a legit hash)]]
    },
    {
      align(center, box(height: 60%, {
        only(1, file(name: "java/com/example/maliciousapp/HashFile.kt")[
          ```kotlin
          val hashedBytes = MessageDigest
              .getInstance("SHA-256")
              .apply { update(bytes) }
              .digest()
          ```
        ])

        only(2, file(name: "java/com/example/maliciousapp/HashFile.kt")[
          ```kotlin
          val hash = Hex.toHexString(hashedBytes)
          debugTextField.text = "The hash is: $hash"
          ```
        ])

        only("3-", image(height: 90%, "images/hash.png"))
      }))
    }
  ))
]

#slide(title: [Return the result])[
  #align(horizon, grid(
    columns: (2fr, 3fr),
    gutter: 1em,
    {
      set text(size: .9em)
      uncover(mode: "transparent", "1-")[- Set the result and finish]
      uncover(mode: "transparent", "2-")[- You can finally get the flag!]
    },
    {
      align(center, box(height: 60%, {
        only(1, file(name: "java/com/example/maliciousapp/HashFile.kt")[
          ```kotlin
          setResult(
              Activity.RESULT_OK,
              Intent().putExtra("hash", hash)
          )
          finish()
          ```
        ])
      }))
    }
  ))
]

#new-section[Security considerations]
#slide(title: [Implicit vs explicit])[
  #line-by-line[
    - Implicit intents: may get caught by anyone, even malicious apps
    - Explicit intents: managed by a known and trusted app (the one intended to receive it)
  ]
]

#slide(title: [When is the attack effective?])[
  #align(horizon, grid(
    columns: (3fr, 2fr),
    gutter: 1em,
    [
      Only a single app able to receive the intent

      #uncover(2)[User less prone to share sensitive information with an unknown app]
    ],
    align(center, image(height: 55%, "images/chooser.png"))
  ))
]

#wake-up[That's all]
