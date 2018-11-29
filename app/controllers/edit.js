app.controller("editCtrl", function($scope, $http) {
    $scope.passwordCheck = false;
    $scope.login;

    $scope.edit = function() {
        $scope.passwordCheck = true;
        if ($scope.password != $scope.confirmPassword) {
            $scope.passwordErrorMessage = "Please provide matching passwords";
        } else {$scope.passwordErrorMessage = "";}

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
        
        $http.put("http://accounts.zipper.release11.com/api/user?uid=" + $scope.login, data, config)
        .then(function(response) {
            console.log("success");
        }, function(response) {
            console.log("error");
            console.log(JSON.stringify(data));
        })
    }
})