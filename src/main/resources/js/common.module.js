(function() {
"use strict";
/**
 * THe common module for Shared Service
 */
angular.module('common', ['security.service'])
.service('SharedService', SharedService);

SharedService.$inject = [];
function SharedService() {
  var service = this;

  service.setLoginSuccessUrl = function (state) {
    service.state = state;
  };

  service.getLoginSuccessUrl = function() {
    return service.state;
  };

  service.setAuthenticatedUser = function(user){
    service.authenticateduser = user;
  };

  service.getAuthenticatedUser = function(){
    return service.authenticateduser;
  };


};


})();
