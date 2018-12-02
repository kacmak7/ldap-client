var login;
var nameForm;
var surnameForm;
app.controller("listCtrl", function($scope, $http) {
    $scope.active = true;

    $http.get("http://accounts.zipper.release11.com/api/users")
    .then(function(response) {
        
        console.log("getUsers()");
        $scope.users = response.data;
        delete $scope.users.count;
        }); 

    $scope.edit = function(no) {
        login = "";

        //nameForm = $scope.accounts[no].firstname;
        //surnameForm = $scope.accounts[no].lastname;
        
        console.log(login);
    };

    $scope.resetPassword = function(no) {
        login = "";
    }

    /*$scope.delete = function(no) {
        nameForm = $scope.accounts[no].uid[0];
        //surnameForm = $scope.accounts[no].lastname;
        console.log(nameForm);
    }*/
})