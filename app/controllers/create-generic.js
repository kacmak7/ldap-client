app.controller("createGenericCtrl", function($scope, $http) {
    $scope.name = "";
    $scope.password = "";
    $scope.confirmPassword = "";
    $scope.mail = "";
    $scope.mail1 = "";
    $scope.mail2 = "";
    $scope.mail3 = "";

    $http.get("http://accounts.zipper.release11.com/api/cities")
    .then(function(response) {
        $scope.cities = response.data;
    })
    
    $scope.create = function() {
        if ($scope.password != $scope.confirmPassword) {
            $scope.passwordErrorMessage = "Please provide matching passwords";
        } else if(  $scope.name == "" ||
                    $scope.password == "" ||
                    $scope.confirmPassword == "") {
            $scope.emptyField = true;
            $scope.emptyFieldMessage = "Except mails all fields are required";
        } else {

            var data = {
                name: $scope.name,
                password: $scope.password,
                mail: $scope.mail,
                mail1: $scope.mail1,
                mail2: $scope.mail2,
                mail3: $scope.mail3
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