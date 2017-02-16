(function () {
'use strict';

angular.module('touchinghand')
.controller('LoginController', LoginController);


LoginController.$inject = ['LoginRegistrationService', 'ApiBasePath', '$location', '$state', 'security'];
function LoginController(LoginRegistrationService, ApiBasePath, $location, $state, security) {
  var loginCtrl = this;

  loginCtrl.submit = function () {
      console.log("Inside LoginController submit", loginCtrl.user.userName);
        LoginRegistrationService.login(loginCtrl.user, function (data, status, headers, config) {
  			// Success handler
  			console.info('The user has been successfully logged in! ', status);
        security.requestCurrentUser();
        $state.go('home.dashboard');
        //$state.go('');
  		}, function(data, status, headers, config) {
  			// Failure handler
  			console.error('Something went wrong while trying to login... ', data, status, headers, config);
  		});
  };

}

})();
