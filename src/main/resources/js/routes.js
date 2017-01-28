(function () {
'use strict';

angular.module('LoginRegistrationModule')
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
}
})();
