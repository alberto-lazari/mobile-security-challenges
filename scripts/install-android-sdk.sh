#!/bin/bash

script_dir="$(dirname "$(readlink -f "$BASH_SOURCE")")"

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

"$prefix/cmdline-tools/latest/bin/sdkmanager" --install \
    'platform-tools' \
    'platforms;android-33' \
    'build-tools;33.0.0' \
    "system-images;android-33;google_apis_playstore;$arch"

echo Creating virtual device...
echo no | "$prefix/cmdline-tools/latest/bin/avdmanager" create avd --force \
    -n mobiotsec \
    -k "system-images;android-33;google_apis_playstore;$arch"

# Enable buttons
sed -i'' -e 's/^hw\.keyboard=no/hw.keyboard=yes/' ~/.android/avd/mobiotsec.avd/config.ini

# Download x86 emulator
if [[ $arch = x86_64 ]] && [[ ! -d "$prefix/emulator-x86" ]]; then
    curl -L 'https://redirector.gvt1.com/edgedl/android/repository/emulator-darwin_x64-10696886.zip' | bsdtar -xpf -
    chmod +x emulator/emulator emulator/qemu/darwin-x86_64/qemu-system-x86_64
    mv emulator "$prefix/emulator-x86/"
fi

# Link useful binaries and scripts in a PATH directory
[[ -d ~/bin ]] || mkdir ~/bin
for link in ~/bin/{adb,emulator,make-app,build-app}; do
    [[ ! -f $link ]] || rm $link
done
ln -s "$prefix/platform-tools/adb" ~/bin/adb
ln -s "$prefix/$emulator/emulator" ~/bin/emulator
ln -s "$script_dir/make-app" ~/bin/make-app
ln -s "$script_dir/build-app" ~/bin/build-app

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
  emulator -avd mobiotsec -no-boot-anim -no-audio -accel on -gpu swiftshader_indirect &> ~/.android/emulator.log &
EOF
