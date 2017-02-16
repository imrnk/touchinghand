(function () {
'use strict';

angular.module('touchinghand')
.controller('RegistrationController', RegistrationController);

RegistrationController.$inject = ['LoginRegistrationService', 'ApiBasePath', '$state'];

function RegistrationController(LoginRegistrationService, ApiBasePath, $state) {
  var registrationCtrl = this;
  registrationCtrl.user = {};
  registrationCtrl.error = "";
  registrationCtrl.regex = '[\w\s]+';
  registrationCtrl.regSuccess = false;
  registrationCtrl.submit = function(){
    console.log("Inside RegistrationController submit", registrationCtrl.user);
      LoginRegistrationService.register(registrationCtrl.user, function (data, status, headers, config) {
			// Success handler
      registrationCtrl.regSuccess = true;
      console.info('The user has been successfully registered! ', data, status, headers, config);
      $state.go('home.register');
		}, function(data, status, headers, config) {
			// Failure handler
			console.error('Something went wrong while trying to register... ', data, status, headers, config);
      registrationCtrl.error = "Something went wrong while trying to register";
		});
  };

}

})();
