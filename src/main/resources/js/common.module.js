(function() {
"use strict";
/**
 * THe common module for Shared Service
 */
var common = angular.module('common', []);

common.service('SharedService', SharedService);

SharedService.$inject = [];
function SharedService() {
  var service = this;

  service.setLoginSuccessUrl = function (url) {
    service.url = url;
  };

  service.getLoginSuccessUrl = function() {
    return service.url;
  }
};


})();
