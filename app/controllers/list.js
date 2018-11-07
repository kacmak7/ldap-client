app.controller("listCtrl", function($scope, $http) {
    $scope.active = true;

    $http.get("http://localhost:8080/authors")
    .then(function(response) {
        console.log("getAuthors()");
        $scope.accounts = response.data.authors;
    });
    
    $scope.clicked = "click!!!"

    $scope.change = function() {
        $scope.clicked = $scope.account.lastname;
        console.log($scope.clicked);
    }

})