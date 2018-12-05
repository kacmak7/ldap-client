var loginForm;
var nameForm;
var surnameForm;
var cityForm;
var organizationForm;
var mail;
var mail1;
var mail2;
var mail3;
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
        delete $scope.users.count; // to hide an empty entry
        }); 

    $scope.edit = function(no) {
        loginForm = $scope.users[no].uid[0];
        nameForm = $scope.users[no].givenname[0];
        surnameForm =  $scope.users[no].sn[0];
        cityForm = $scope.users[no].l[0];
        organizationForm = $scope.users[no].o[0];
        mail = $scope.users[no].mail[0];
        mail1 = $scope.users[no].mail[1];
        mail2 = $scope.users[no].mail[2];
        mail3 = $scope.users[no].mail[3];
    };

    $scope.resetPassword = function(no) {
        loginForm = $scope.users[no].uid[0];
    };

    $scope.delete = function(no) {
        loginForm = $scope.users[no].uid[0];
        nameForm = $scope.users[no].givenname[0];
        surnameForm =  $scope.users[no].sn[0];
        cityForm = $scope.users[no].l[0];
        organizationForm = $scope.users[no].o[0];
    };
})