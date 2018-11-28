app.controller("createCtrl", function($scope, $http) {
    $scope.active = true;

    // TODO: comparing password fields

    //$scope.error = 's';
    //$scope.password = 'sa';
    //$scope.confirmPassword = 'sa';

    $scope.create = function() {
        var data = {
            name: $scope.name,
            surname: $scope.surname,
            password: $scope.password,
            city: $scope.city,
            mobile: $scope.mobile
        }
        var config = {
            headers : {
                'Content-Type': 'application/json'
            }
        };
        
        $http.post("http://accounts.zipper.release11.com/api/user", data, config)
        .then(function(response) {
            console.log("success");
        }, function(response) {
            console.log("error");
            console.log(JSON.stringify(data));
        })
    }
})