var app = angular.module("app", ["ngRoute", "ngAnimate"]);

app.config(function($routeProvider) {
    $routeProvider
    .when("/list", {
        templateUrl: "../views/list.html",
        controller: "listCtrl"
    })
    .otherwise({redirectTo: "/"})
})