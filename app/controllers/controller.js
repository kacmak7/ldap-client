app.controller("ctrl", function($scope, $http) {

    $scope.generateCsv = function() {
        var users;
        $http.get("http://accounts.zipper.release11.com/api/users")
        .then(function(response) {
            console.log("count: " + response.data.count);
            users = response.data;
            delete $scope.users.count;
        });
        var usersJson = JSON.stringify(users);
        var usersCsv = convertToCsv(usersJson);
        console.log(usersCsv);
    }

    function convertToCsv(objArray) {
        var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
        var str = '';

        for (var i = 0; i < array.length; i++) {
            var line = '';
            for (var index in array[i]) {
                if (line != '') line += ','

                line += array[i][index];
            }

            str += line + '\r\n';
        }

        return str;
    }
})