(function () {
'use strict';

angular.module('touchinghand')
.service('LoginRegistrationService', LoginRegistrationService);

//Login and Registration Service
LoginRegistrationService.$inject = ['$http', 'ApiBasePath', '$resource', 'Cookies'];
function LoginRegistrationService($http, ApiBasePath, $resource, Cookies) {

  var loginResources = $resource(ApiBasePath + '/login', {}, {
    options: {method: 'OPTIONS', cache: false}
  });

  var logoutResources = $resource(ApiBasePath + '/logout', {}, {
    options: {method: 'OPTIONS', cache: false}
  });

  var registerResources = $resource(ApiBasePath + '/register', {}, {
    options: {method: 'OPTIONS', cache: false}
  });

  /**
	 * Tries to detect whether the response elements returned indicate an invalid or missing CSRF token...
	 */
	var isCSRFTokenInvalidOrMissing = function (data, status) {
		return (status === 403 && data.message && data.message.toLowerCase().indexOf('csrf') > -1)
			|| (status === 0 && data === null);
	};

  var service = this;

  service.gotodashboard = function (dashboardstate, authenticateduser) {

    SharedService.setLoginSuccessUrl(dashboardstate);
    SharedService.setAuthenticatedUser(authenticateduser);

  };

  

  service.login = function(user, successHandler, errorHandler) {

    // Obtain a CSRF token
     loginResources.options().$promise.then(function (response) {
      console.log('Obtained a CSRF token in a cookie', response);

      // Extract the CSRF token
      var csrfToken = Cookies.getFromDocument($http.defaults.xsrfCookieName);
      console.log('Extracted the CSRF token from the cookie', csrfToken);

      // Prepare the headers
      var headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      };
      headers[$http.defaults.xsrfHeaderName] = csrfToken;
      //console.log("Before calling /login, user : ", user);
      // Post the credentials for logging in
      //var credentials = "username="+user.username+"&password="+user.password;
      $http.post(ApiBasePath + '/login', user, {headers: headers})
        .success(successHandler)
        .error(function (data, status, headers, config) {

          if (isCSRFTokenInvalidOrMissing(data, status)) {
            console.error('The obtained CSRF token was either missing or invalid. Have you turned on your cookies?');

          } else {
            // Nope, the error is due to something else. Run the error handler...
            errorHandler(data, status, headers, config);
          }
        });

    }).catch(function(response) {
      console.error('Could not contact the server... is it online? Are we?', response);
    });
  };//login function ends

  service.register = function(user, successHandler, errorHandler) {

    // Obtain a CSRF token
      registerResources.options().$promise.then(function (response) {
      console.log('Obtained a CSRF token in a cookie', response);

      // Extract the CSRF token
      var csrfToken = Cookies.getFromDocument($http.defaults.xsrfCookieName);
      console.log('Extracted the CSRF token from the cookie', csrfToken);

      // Prepare the headers
      var headers = {
        'Content-Type': 'application/json'
      };
      headers[$http.defaults.xsrfHeaderName] = csrfToken;

      // Post the credentials for logging in
      $http.post(ApiBasePath + '/register', user, {headers: headers})
        .success(successHandler)
        .error(function (data, status, headers, config) {

          if (isCSRFTokenInvalidOrMissing(data, status)) {
            console.error('The obtained CSRF token was either missing or invalid. Have you turned on your cookies?');
            errorHandler(data, status, headers, config);
          } else {
            // Nope, the error is due to something else. Run the error handler...
            errorHandler(data, status, headers, config);
          }
        });

    }).catch(function(response) {
      console.error('Could not contact the server... is it online? Are we?', response);
    });
  }; //register function ends

};//service function ends

})();
