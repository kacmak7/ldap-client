app.controller("editCtrl", function($scope) {
    $scope.name = nameForm;
    console.log("$scope.name: " + $scope.name);
    console.log("nameForm: " + nameForm);
    $scope.surname = surnameForm;
})