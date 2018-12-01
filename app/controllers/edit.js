app.controller("editCtrl", function($scope, $http) {
    $scope.name = "";
    $scope.surname = "";
    $scope.password = "";
    $scope.confirmPassword = "";
    $scope.city = "";
    $scope.organization = "";

    $scope.edit = function() {
        if ($scope.password != $scope.confirmPassword) {
            $scope.passwordErrorMessage = "Please provide matching passwords";
        } else if(  $scope.name == "" ||
                    $scope.surname == "" ||
                    $scope.password == "" ||
                    $scope.confirmPassword == "" ||
                    $scope.city == "" ||
                    $scope.organization == "") {
                        $scope.emptyField = true;
                        $scope.emptyFieldMessage = "All fields are required";
        } else {

            var data = {
                name: $scope.name,
                surname: $scope.surname,
                password: $scope.password,
                city: $scope.city,
                organization: $scope.organization
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