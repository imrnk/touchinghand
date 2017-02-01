(function () {
'use strict';

angular.module('DashboardModule')
.config(RoutesConfig);

RoutesConfig.$inject = ['$stateProvider', '$urlRouterProvider'];
function RoutesConfig($stateProvider, $urlRouterProvider) {
  // Redirect to home page if no other URL matches
  $urlRouterProvider.otherwise('/');
  // *** Set up UI states ***
  $stateProvider
  // // Home page
  // .state('landing', {
  //   absract: true,
  //   templateUrl: '../../static/abstract-dashboard.html'
  // })

  //the dashboard page
  .state('dashboard', {
    url: '/dashboard',
    templateUrl: '../../static/dashboard.html',
    controller: 'DashboardController as dashboardCtrl'
  })

  //dashboard page
  .state('dashboard.main', {
    templateUrl: '../../static/templates/dashboard.main.template.html',
    controller: 'DashboardController as dashboardCtrl'
  })

  //dashboard add client template
  .state('dashboard.main.addclient', {
    url: '/dashboard/add-client',
    templateUrl: '../../static/templates/dashboard.addclient.template.html'
  })

  //dashboard clients template
  .state('dashboard.main.clients', {
    url: '/dashboard/clients',
    templateUrl: '../../static/templates/dashboard.clients.template.html'
  })

  //dashboard history template
  .state('dashboard.main.history', {
    url: '/dashboard/history',
    templateUrl: '../../static/templates/dashboard.clients.template.html'
  })
}
})();
