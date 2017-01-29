(function () {
'use strict';

angular.module('LoginRegistrationModule')
.controller('LoginController', LoginController);


LoginController.$inject = ['LoginRegistrationService', 'ApiBasePath', '$location'];
function LoginController(LoginRegistrationService, ApiBasePath, $location) {
  var loginCtrl = this;

  loginCtrl.submit = function () {
      console.log("Inside LoginController submit");
        LoginRegistrationService.login(loginCtrl.user, function (data, status, headers, config) {
  			// Success handler
  			console.info('The user has been successfully logged in! ', data, status, headers, config);
  		}, function(data, status, headers, config) {
  			// Failure handler
  			console.error('Something went wrong while trying to login... ', data, status, headers, config);
  		});
  };

}

})();
