(function () {
'use strict';

angular.module('DashboardModule')
.service('DashboardService', DashboardService);

//Login and Registration Service
DashboardService.$inject = ['$http', 'ApiBasePath', '$resource', 'Cookies'];
function DashboardService($http, ApiBasePath, $resource, Cookies) {
 console.log("Inside DashboardService");
}
})();
