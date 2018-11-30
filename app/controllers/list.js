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

    $scope.mails = 

    $scope.edit = function(no) {
        login = $scope.users[no].uid[0];

        //nameForm = $scope.accounts[no].firstname;
        //surnameForm = $scope.accounts[no].lastname;
        
        console.log(login);
    }

    /*$scope.delete = function(no) {
        nameForm = $scope.accounts[no].uid[0];
        //surnameForm = $scope.accounts[no].lastname;
        console.log(nameForm);
    }*/
})