#!/bin/sh

# To execute this script, we need the following environment variables
# WORKSPACE : location of a this repository's clone
# GPG_DECRYPT_PASSPHRASE : the passphrase used to encrypt *.gpg files
# ANDROID_SIGNING_KEYSTORE_PASS : the password used for the keystore

SCRIPT_LOCATION=$WORKSPACE/.release/

# Decrypt secret files
gpg --quiet --batch --yes --decrypt --passphrase="$GPG_DECRYPT_PASSPHRASE" --output $SCRIPT_LOCATION/android-signing.key $SCRIPT_LOCATION/android-signing.key.gpg
gpg --quiet --batch --yes --decrypt --passphrase="$GPG_DECRYPT_PASSPHRASE" --output $SCRIPT_LOCATION/android-signing.pem $SCRIPT_LOCATION/android-signing.pem.gpg
gpg --quiet --batch --yes --decrypt --passphrase="$GPG_DECRYPT_PASSPHRASE" --output $WORKSPACE/android/key.properties $SCRIPT_LOCATION/key.properties.gpg

# Create a JKS keystore
openssl pkcs12 -export -in $SCRIPT_LOCATION/android-signing.pem -inkey $SCRIPT_LOCATION/android-signing.key -name "thomah" -out $SCRIPT_LOCATION/keystore.p12 -password pass:$ANDROID_SIGNING_KEYSTORE_PASS
keytool -importkeystore -deststorepass $ANDROID_SIGNING_KEYSTORE_PASS -destkeystore $WORKSPACE/keystore.jks -deststoretype JKS -srcstorepass $ANDROID_SIGNING_KEYSTORE_PASS -srckeystore $SCRIPT_LOCATION/keystore.p12 -srcstoretype PKCS12

# Build the release
cd $WORKSPACE
./gradlew :app:assembleRelease
