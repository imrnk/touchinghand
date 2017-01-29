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
    LoginRegistrationService.register(registrationCtrl.user).then(handleSuccess, handleError('Error creating user'));

    function handleSuccess(response) {
      return response.data;
    }

    function handleError(error) {
      return function () {
        return { success: false, message: error };
      };
    }
  }
}

})();
