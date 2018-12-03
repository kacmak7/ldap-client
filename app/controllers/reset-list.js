app.controller("resetListCtrl", function($scope, $http) {
    $scope.login = loginForm;

    $scope.resetPassword = function() {
        if ($scope.password != $scope.confirmPassword) {
            $scope.passwordErrorMessage = "Please provide matching passwords";
        } else {

            var data = {
                name: "",
                surname: "",
                password: $scope.password,
                city: "",
                organization: ""
            }

            var config = {
                headers : {
                    'Content-Type': 'application/json'
                }
            };

            $http.put("http://accounts.zipper.release11.com/api/user?uid=" + $scope.login, data, config)
            .then(function(response) {
                console.log("success");
                setTimeout(function(){
                    window.location.href = "#!list"
                }, 1000);
            }, function(response) {
                console.log("error");
                console.log(JSON.stringify(data));
            })
        }
    }
})