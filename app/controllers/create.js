app.controller("createCtrl", function($scope, $http) {
    $scope.name = "";
    $scope.surname = "";
    $scope.password = "";
    $scope.confirmPassword = "";
    $scope.city = "";
    $scope.organization = "";

    // mail fields
    $scope.mailFields = [   {id: 'mail1', name: 'mail1'}, 
                            {id: 'mail2', name: 'mail2'}, 
                            {id: 'mail3', name: 'mail3'}];
    $scope.addMailField = function() {
        var newMailNo = $scope.mailFields.length+1;
        $scope.mailFields.push({'id': 'mail' + newMailNo, 'name': 'mail' + newMailNo});
    };
    $scope.removeMailField = function() {
        var newMailNo = $scope.mailFields.length-1;
        if (newMailNo !==-1) {
            $scope.mailFields.pop();
        }
    };

    $scope.create = function() {
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