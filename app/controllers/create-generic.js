app.controller("createGenericCtrl", function($scope, $http) {
    $scope.name = "";
    $scope.password = "";
    $scope.confirmPassword = "";

    $scope.create = function() {
        if ($scope.password != $scope.confirmPassword) {
            $scope.passwordErrorMessage = "Please provide matching passwords";
        } else if(  $scope.name == "" ||
                    $scope.password == "" ||
                    $scope.confirmPassword == "") {
            $scope.emptyField = true;
            $scope.emptyFieldMessage = "All fields are required";
        } else {

            var data = {
                name: $scope.name,
                password: $scope.password
            }

            var config = {
                headers : {
                    'Content-Type': 'application/json'
                }
            };

            $http.post("http://accounts.zipper.release11.com/api/user-generic", data, config)
            .then(function(response) {
                console.log("success");
                console.log(response);
                setTimeout(function(){
                    window.location.href = "#!list"
                }, 1000);
            }, function(response) {
                console.log("error");
                console.log(response);
                console.log(JSON.stringify(data));
            })
        }
    }
})