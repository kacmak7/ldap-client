var nameForm;
var surnameForm;
app.controller("listCtrl", function($scope, $http) {
    $scope.active = true;

    $http.get("http://accounts.zipper.release11.com/api/users")
    .then(function(response) {
        console.log("getUsers()");
        $scope.users = response.data;
    });

    $scope.mails = 

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

    function isNumeric(n) {
        return !isNaN(parseFloat(n)) && isFinite(n);
      }
    
})