(function () {
'use strict';

angular.module('DashboardModule', ['ui.router', 'ngCookies','ngResource', 'common', 'security.service'])
.config(['$httpProvider', function ($httpProvider) {

	$httpProvider.defaults.withCredentials = true;

	$httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN'; // The name of the cookie sent by the server
	$httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN'; // The default header name picked up by Spring Security

}])
.constant('ApiBasePath', "http://localhost:8080");

})();
