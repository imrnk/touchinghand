(function () {
'use strict';

angular.module('DashboardModule')
.controller('DashboardController', DashboardController);


DashboardController.$inject = ['DashboardService', 'ApiBasePath', '$location', '$state'];
function DashboardController(DashboardService, ApiBasePath, $location, $state) {
  var dashboardCtrl = this;

}

})();
