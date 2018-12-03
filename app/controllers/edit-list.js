app.controller("editListCtrl", function($scope, $http) {
    console.log(loginForm);

    $scope.login = loginForm;
    $scope.name = nameForm;
    $scope.surname = surnameForm;
    $scope.password = "";
    $scope.confirmPassword = "";
    $scope.city = cityForm;
    $scope.organization = organizationForm;

    $scope.edit = function() {
        if ($scope.password != $scope.confirmPassword) {
            $scope.passwordErrorMessage = "Please provide matching passwords";
        } else {
            var data = {
                name: $scope.name,
                surname: $scope.surname,
                password: $scope.password,
                city: $scope.city,
                organization: $scope.organization
            }
            console.log(data);

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