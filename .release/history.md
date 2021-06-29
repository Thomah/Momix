# How these encrypted files were generated

```
openssl req -newkey rsa:2048 -new -nodes -x509 -days 3650 -keyout android-signing.key -out android-signing.pem

gpg --output key.properties.gpg --symmetric key.properties
gpg --output android-signing.key.gpg --symmetric android-signing.key
gpg --output android-signing.pem.gpg --symmetric android-signing.pem
```
