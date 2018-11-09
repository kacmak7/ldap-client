app.controller("ctrl", function($scope, $http) {
    
    // Get the modal
    var modal = document.getElementById('logoutModal');

    /*// Get the button that opens the modal
    var btn = document.getElementsByClassName("icon-logout");

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // When the user clicks the button, open the modal 
    btn.onclick = function() {
        modal.style.display = "block";
    }

    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }*/

    $scope.open = function() {
        console.log("OPEN()");
        //document.getElementById('logoutPopup').style.display = "block";
        document.getElementById('logoutModal').style.display = "block";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
})