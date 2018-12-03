var loginForm;
var nameForm;
var surnameForm;
var cityForm;
var organizationForm;
app.controller("listCtrl", function($scope, $http) {
    loginForm = "";
    nameForm = "";
    surnameForm = "";
    cityForm = "";
    organizationForm = "";

    $scope.active = true;

    $http.get("http://accounts.zipper.release11.com/api/users")
    .then(function(response) {
        
        console.log("count: " + response.data.count);
        $scope.users = response.data;
        delete $scope.users.count; // to not to show an empty entry
        }); 

    $scope.edit = function(no) {
        loginForm = $scope.users[no].uid[0];
        nameForm = $scope.users[no].givenname[0];
        surnameForm =  $scope.users[no].sn[0]; // TODO: fetch surname from ldap entry
        cityForm = $scope.users[no].l[0];
        organizationForm = $scope.users[no].

        //nameForm = $scope.accounts[no].firstname;
        //surnameForm = $scope.accounts[no].lastname;
        
        console.log(loginForm);
        console.log(cityForm);
    };

    $scope.resetPassword = function(no) {
        loginForm = $scope.users[no].uid[0];
    }

    /*$scope.delete = function(no) {
        nameForm = $scope.accounts[no].uid[0];
        //surnameForm = $scope.accounts[no].lastname;
        console.log(nameForm);
    }*/
})