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
        controller: "editCtrl"
    })
    .when("/list/reset-password", {
        templateUrl: "../views/reset-password.html",
        controller: "resetCtrl"
    })
    .when("/list/delete", {
        templateUrl: "../views/delete.html",
        controller: "deleteCtrl"
    })
    .otherwise({redirectTo: "/list"})
})