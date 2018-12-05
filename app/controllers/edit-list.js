app.controller("editListCtrl", function($scope, $http) {
    console.log(loginForm);

    $scope.login = loginForm;
    $scope.name = nameForm;
    $scope.surname = surnameForm;
    $scope.password = "";
    $scope.confirmPassword = "";
    $scope.city = cityForm;
    $scope.organization = organizationForm;
    $scope.mail = mailForm;
    $scope.mail1 = mailForm1;
    $scope.mail2 = mailForm2;
    $scope.mail3 = mailForm3;

    $http.get("http://accounts.zipper.release11.com/api/cities")
    .then(function(response) {
        $scope.cities = response.data;
    })

    $scope.edit = function() {
        if ($scope.password != $scope.confirmPassword) {
            $scope.passwordErrorMessage = "Please provide matching passwords";
        } else {
            var data = {
                name: $scope.name,
                surname: $scope.surname,
                password: $scope.password,
                city: $scope.city,
                organization: $scope.organization,
                mail: $scope.mail,
                mail1: $scope.mail1,
                mail2: $scope.mail2,
                mail3: $scope.mail3
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