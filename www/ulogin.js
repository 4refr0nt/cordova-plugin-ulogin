var exec = require('cordova/exec');

var ulogin = {
    auth: function( successCallback, errorCallback ) {
          cordova.exec(
                  function(params) { successCallback(params) },
                  function(params) { errorCallback  (params) },
                  'uLogin',
                  'auth',
                  [{}]
          );
    }
}

module.exports = ulogin;
