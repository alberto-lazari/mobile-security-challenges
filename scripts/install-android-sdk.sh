#!/bin/bash

if [[ $(arch) = arm64 ]]; then
    arch=arm64-v8a
    emulator=emulator
else
    arch=x86_64
    emulator=emulator-x86
fi

if which brew &> /dev/null; then
    prefix=$(brew --prefix)/share/android-commandlinetools
    brew install android-commandlinetools
else
    prefix=~/Library/Android/sdk
    [[ -d $prefix ]] || mkdir -p $prefix/cmdline-tools

    # Get from curl
    if ! [[ -d $prefix/cmdline-tools/latest ]]; then
        curl -L 'https://dl.google.com/android/repository/commandlinetools-mac-10406996_latest.zip' | bsdtar -xpf -
        mv cmdline-tools $prefix/cmdline-tools/latest
    fi
fi

"$prefix/cmdline-tools/latest/bin/sdkmanager" --install 'platform-tools' 'platforms;android-33' 'build-tools;33.0.0' "system-images;android-33;google_apis_playstore;$arch"
"$prefix/cmdline-tools/latest/bin/avdmanager" create avd --name mobiotsec -k "system-images;android-33;google_apis_playstore;$arch"

# Download x86 emulator
if [[ $arch = x86_64 ]] && [[ ! -d "$prefix/emulator-x86" ]]; then
    curl -L 'https://redirector.gvt1.com/edgedl/android/repository/emulator-darwin_x64-10696886.zip' | bsdtar -xpf -
    chmod +x emulator/emulator emulator/qemu/darwin-x86_64/qemu-system-x86_64
    mv emulator "$prefix/emulator-x86/"
fi

# Link binaries in a PATH directory
[[ -d ~/bin ]] || mkdir ~/bin
[[ ! -f ~/bin/adb ]] || rm ~/bin/adb
ln -s "$prefix/platform-tools/adb" ~/bin/adb

[[ ! -f ~/bin/emulator ]] || rm ~/bin/emulator
ln -s "$prefix/$emulator/emulator" ~/bin/emulator

# Add ~/bin to PATH
if ! which adb &> /dev/null; then
    echo Looks like ~/bin is not in PATH >&2
    read -p 'Do you want to add `export PATH="$HOME/bin:$PATH"` to your ~/.zprofile? (y/N) ' answer
    [[ $answer != [Yy] ]] || echo 'export PATH="$HOME/bin:$PATH"' >> ~/.zprofile
fi

# Python scripts will need a non-trivial library
pip3 list | grep androguard &> /dev/null || pip3 install androguard

cat << EOF

Run the Android emulator with:
  emulator -avd mobiotsec -no-snapshot -no-boot-anim -wipe-data &> /dev/null &
EOF
