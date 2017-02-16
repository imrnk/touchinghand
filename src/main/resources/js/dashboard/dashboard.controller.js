(function () {
'use strict';

angular.module('touchinghand')
.controller('DashboardController', DashboardController);


DashboardController.$inject = ['DashboardService', 'ApiBasePath', '$location', '$state', 'currentUser', 'security'];
function DashboardController(DashboardService, ApiBasePath, $location, $state, currentUser, security) {
  var dashboardCtrl = this;
  dashboardCtrl.currentUser = currentUser;

  dashboardCtrl.logout = function () {
      security.logout(ApiBasePath + "/logout");
  };


}

})();
