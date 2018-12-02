app.controller("resetListCtrl", function($scope) {
    $scope.resetPassword = function() {
        if ($scope.password != $scope.confirmPassword) {
            $scope.passwordErrorMessage = "Please provide matching passwords";
        } else {

            var data = {
                password: $scope.password
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