var app = angular.module("app", ["ngRoute", "ngAnimate"]);

app.config(function($routeProvider) {
    $routeProvider
    .when("/list", {
        templateUrl: "../views/list.html",
        controller: "listCtrl"
    })
    .when("/create", {
        templateUrl: "../views/create.html",
        controller: "createCtrl"
    })
    .when("/list/edit", {
        templateUrl: "../views/edit.html",
        controller: "listCtrl"
    })
    .when("/list/reset-password", {
        templateUrl: "../views/reset-password.html",
        controller: "listCtrl"
    })
    .otherwise({redirectTo: "/list"})
})