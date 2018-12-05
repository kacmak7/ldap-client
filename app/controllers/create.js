app.controller("createCtrl", function($scope, $http) {
    $scope.name = "";
    $scope.surname = "";
    $scope.password = "";
    $scope.confirmPassword = "";
    $scope.city = "";
    $scope.organization = "";
    $scope.mail2 = "";
    $scope.mail3 = "";

    $http.get("http://accounts.zipper.release11.com/api/cities")
    .then(function(response) {
        $scope.cities = response.data;
    })
    $http.get("http://accounts.zipper.release11.com/api/organizations")
    .then(function(response) {
        $scope.organizations = response.data;
    })

    $scope.create = function() {
        console.log($scope.mailFields);
        if ($scope.password != $scope.confirmPassword) {
            $scope.passwordErrorMessage = "Please provide matching passwords";
        } else if(  $scope.name == "" ||
                    $scope.surname == "" ||
                    $scope.password == "" ||
                    $scope.confirmPassword == "" ||
                    $scope.city == "" ||
                    $scope.organization == "") {
            $scope.emptyField = true;
            $scope.emptyFieldMessage = "Except mails all fields are required";
        } else {

            var data = {
                name: $scope.name,
                surname: $scope.surname,
                password: $scope.password,
                city: $scope.city,
                organization: $scope.organization,
                mail2: $scope.mail2,
                mail3: $scope.mail3
            }

            var config = {
                headers : {
                    'Content-Type': 'application/json'
                }
            };

            $http.post("http://accounts.zipper.release11.com/api/user", data, config)
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