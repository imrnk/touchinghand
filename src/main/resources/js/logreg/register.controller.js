(function () {
'use strict';

angular.module('LoginRegistrationModule')
.controller('RegistrationController', RegistrationController);

RegistrationController.$inject = ['LoginRegistrationService', 'ApiBasePath'];

function RegistrationController(LoginRegistrationService, ApiBasePath) {
  var registrationCtrl = this;
  registrationCtrl.user = {};

  registrationCtrl.regex = '[\w\s]+';

  registrationCtrl.submit = function(){
    console.log("Inside RegistrationController submit");
      LoginRegistrationService.register(registrationCtrl.user, function (data, status, headers, config) {
			// Success handler
			console.info('The user has been successfully registered! ', data, status, headers, config);
		}, function(data, status, headers, config) {
			// Failure handler
			console.error('Something went wrong while trying to register... ', data, status, headers, config);
		});
  };

}

})();
