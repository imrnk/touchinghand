(function () {
'use strict';

angular.module('touchinghand')
.config(RoutesConfig);

RoutesConfig.$inject = ['$stateProvider', '$urlRouterProvider'];
function RoutesConfig($stateProvider, $urlRouterProvider) {
  // Redirect to home page if no other URL matches
  $urlRouterProvider.otherwise('/');
  // *** Set up UI states ***
  $stateProvider
  // Home page
  .state('home', {
    absract: true,
    templateUrl: '../static/home.html'
  })
  // login  page
  .state('home.login', {
    url: '/',
    templateUrl: '../static/templates/login.template.html',
    controller: 'LoginController as loginCtrl'
  })
  // register  page
  .state('home.register', {
    url: '/register',
    templateUrl: '../static/templates/registration.template.html',
    controller: 'RegistrationController as regCtrl'
  })
  //the dashboard page
  .state('home.dashboard', {
    url: '/dashboard',
    templateUrl: '../static/templates/dashboard.main.template.html',
    controller: 'DashboardController as dashboardCtrl',
    resolve: {
      currentUser: ['security', function(security) {
        return security.requestCurrentUser();
      }]
    }
  })

  // //dashboard page
  // .state('home.dashboard.main', {
  //   templateUrl: '../static/templates/dashboard.main.template.html'
  //   //controller: 'DashboardController as dashboardCtrl'
  // })

  //dashboard add client template
  .state('home.dashboard.addclient', {
    url: '/dashboard/add-client',
    templateUrl: '../static/templates/dashboard.addclient.template.html',
    controller: 'AddClientController as addClientCtrl'
  })

  // //dashboard clients template
  // .state('home.dashboard.main.clients', {
  //   url: '/dashboard/clients',
  //   templateUrl: '../../static/templates/dashboard.clients.template.html'
  // })
  //
  // //dashboard history template
  // .state('home.dashboard.main.history', {
  //   url: '/dashboard/history',
  //   templateUrl: '../../static/templates/dashboard.clients.template.html'
  // })
}
})();
