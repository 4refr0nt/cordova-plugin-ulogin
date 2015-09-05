<!--
# license: MIT
-->

# cordova-plugin-ulogin

Social auth via uLogin SDK

* VK
* Odnoklassniki
* Mail.ru
* Facebook
* Twitter
* Google
* Yandex
* Live Journal
* OpenID
* Flickr
* Last.FM
* LinkedIn
* Live ID
* SoundCloud
* Steam
* uID
* WebMoney
* YouTube
* foursquare
* tumblr
* Google+
* dudu
* Vimeo
* Instagram

# Based on
uLogin Android SDK 
https://github.com/ulogin/Android/tree/AndroidStudio/
http://u-login.com/
http://ulogin.ru/

## Supported locales

en, ru, uk, fr, de

## Installation

    ```cordova plugin add cordova-plugin-ulogin```

or with Ionic Framework

    ```ionic plugin add cordova-plugin-ulogin```


### Supported Platforms

- Android
- iOS -> TODO

### Example

```
        navigator.ulogin.auth( function success(result) {
             console.log("success", result);
        }, function error(err) {
             console.log("error", err);
        });
```
