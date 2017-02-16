// Based loosely around work by Witold Szczerba - https://github.com/witoldsz/angular-http-auth
angular.module('security.service', [])
.constant('ApiBasePath', "http://localhost:8080")
.factory('security', ['$http', '$q', '$location', 'ApiBasePath', function($http, $q, $location, ApiBasePath) {

  // Redirect to the given url (defaults to '/')
  function redirect(url) {
    url = url || '/';
    $location.path(url);
  }


  // The public API of the service
  var service = {

    // Logout the current user and redirect
    logout: function(redirectTo) {
      $http.post(ApiBasePath + '/logout').then(function() {
        service.currentUser = null;
        redirect(redirectTo);
      });
    },

    // Ask the backend to see if a user is already authenticated - this may be from a previous session.
    requestCurrentUser: function() {
      if ( service.isAuthenticated() ) {
        return $q.when(service.currentUser);
      } else {
        return $http.get(ApiBasePath + '/current-user').then(function(response) {
          console.log(response.data.authorities[0].authority === 'ROLE_ADMIN');
          service.currentUser = response.data;
          console.log("Inside security service, current user: ",service.currentUser);
          return service.currentUser;
        });
      }
    },

    // Information about the current user
    currentUser: null,

    // Is the current user authenticated?
    isAuthenticated: function(){
      return !!service.currentUser;
    },

    // Is the current user an adminstrator?
    isAdmin: function() {
      return !!(service.currentUser && service.currentUser.authorities[0].authority === 'ROLE_ADMIN');
    }
  };
  return service;

}]);
