(function () {
'use strict';

angular.module('LoginRegistrationModule')
.service('LoginRegistrationService', LoginRegistrationService);

//Login and Registration Service
LoginRegistrationService.$inject = ['$http', 'ApiBasePath'];
function LoginRegistrationService($http, ApiBasePath) {
  var service = this;

  service.login = function(user) {
    var response = $http({
      method: "POST",
      url: (ApiBasePath + "/login")
    });
    return response;
  };

  service.register = function(user) {
    console.log("Inside register of logreg.service.js");
      var response = $http({
        method: "POST",
        url: (ApiBasePath + "/register")
      }, user);
      return response;
    };

  };

})();
