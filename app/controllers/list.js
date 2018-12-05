var loginForm;
var nameForm;
var surnameForm;
var cityForm;
var organizationForm;
var mailForm;
var mailForm1;
var mailForm2;
var mailForm3;
app.controller("listCtrl", function($scope, $http) {
    loginForm = "";
    nameForm = "";
    surnameForm = "";
    cityForm = "";
    organizationForm = "";
    mailForm = "";
    mailForm1 = "";
    mailForm2 = "";
    mailForm3 = "";

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
        if ($scope.users[no].l) {
            cityForm = $scope.users[no].l[0];
        }
        if ($scope.users[no].o) {
            organizationForm = $scope.users[no].o[0];
        }
        if ($scope.users[no].mail) {
            mailForm = $scope.users[no].mail[0];
        }
        if ($scope.users[no].mail[1]) {
            mailForm1 = $scope.users[no].mail[1];
        }
        if ($scope.users[no].mail[2]) {
            mailForm2 = $scope.users[no].mail[2];
        }
        if ($scope.users[no].mail[3]) {
            mailForm3 = $scope.users[no].mail[3];
        }
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