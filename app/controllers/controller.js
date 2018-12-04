app.controller("ctrl", function($scope, $http) {
    $scope.generateCsv = function() {
        $http.get("http://accounts.zipper.release11.com/api/users")
        .then(function(response) {
            console.log("count: " + response.data.count);
            var users = response.data;
            delete $scope.users.count;
        });
        var usersJson = JSON.stringify(users);
    }
})