var app = angular.module("app", ["ngRoute", "ngAnimate"]);

app.config(function($routeProvider) {
    $routeProvider
    .when("/", {redirectTo: "/list"})
    .when("/list", {
        templateUrl: "../views/list.html",
        controller: "listCtrl"
    })
    .when("/create", {
        templateUrl: "../views/create.html",
        controller: "createCtrl"
    })
    .when("/create-generic", {
        templateUrl: "../views/create-generic.html",
        controller: "createGenericCtrl"
    })
    .when("/edit", {
        templateUrl: "../views/edit.html",
        controller: "editCtrl"
    })
    .when("/delete", {
        templateUrl: "../views/delete.html",
        controller: "deleteCtrl"
    })
    .when("/list/edit", {
        templateUrl: "../views/edit.html",
        controller: "editListCtrl"
    })
    .when("/list/reset-password", {
        templateUrl: "../views/reset-password.html",
        controller: "resetListCtrl"
    })
    .when("/list/delete", {
        templateUrl: "../views/delete.html",
        controller: "deleteListCtrl"
    })
    .otherwise({redirectTo: "/error"})
})