var nameForm;
var surnameForm;
app.controller("listCtrl", function($scope, $http) {
    $scope.active = true;

    $http.get("http://accounts.zipper.release11.com/api/users")
    .then(function(response) {
        console.log("getUsers()");
        $scope.users = response.data;
    });

    $scope.emails = [   "example4452@marol.com.pl",
                        "test1@chatapolska.pl",
                        /*"case0@marol.com.pl",
                        "test321@marol.com.pl",
                        "example21@chatapolska.pl"*/];

    $scope.edit = function(no) {
        nameForm = $scope.accounts[no].firstname;
        surnameForm = $scope.accounts[no].lastname;
        console.log(nameForm);
    }

    $scope.delete = function(no) {
        nameForm = $scope.accounts[no].firstname;
        surnameForm = $scope.accounts[no].lastname;
        console.log(nameForm);
    }
    
})