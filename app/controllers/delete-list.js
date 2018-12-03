app.controller("deleteListCtrl", function($scope, $http) {
    $scope.login = loginForm;
    $scope.name = nameForm;
    $scope.surname = surnameForm;
    $scope.city = cityForm;
    $scope.organization = organizationForm;

    $scope.delete = function() {
        $http.delete("http://accounts.zipper.release11.com/api/user?uid=" + $scope.login)
            .then(function(response) {
                console.log("success");
                setTimeout(function(){
                    window.location.href = "#!list"
                }, 1000);
            }, function(response) {
                console.log("error");
                console.log(response);
            })      
    }
})