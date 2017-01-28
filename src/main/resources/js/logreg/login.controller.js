(function () {
'use strict';

angular.module('LoginRegistrationModule')
.controller('LoginController', LoginController);


LoginController.$inject = ['LoginRegistrationService', 'ApiBasePath', '$location'];
function LoginController(LoginRegistrationService, ApiBasePath, $location) {
  var loginCtrl = this;

  loginCtrl.submit = function () {

  };

}

})();
