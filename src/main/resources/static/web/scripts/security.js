function showUser() {
    $.ajax({
        type: 'GET',
        url: '/api/games',
        success: function(data) {
            var email = data.player.email;
            manageLogin(true, email);
        },
        error: function() {
            manageLogin(false);
        }
    });
}

function signUp(event) {
    event.preventDefault();
    var formArray = $('#sign-form').serializeArray();

    $.ajax({
        type: 'POST',
        url: '/api/players',
        data: {
            username: formArray[0].value,
            password: formArray[1].value
        },
        success: function() {
            alert("Signed Up!");
            login(event, 'sign');
            location.href = '/web/games.html';
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("Error: " + jqXHR.responseJSON.error);
        }
    });
}

function login(event, form) {
    event.preventDefault();
    var formArray = $('#' + form + '-form').serializeArray();
    $.ajax({
        type: 'POST',
        url: '/api/login',
        data: {
            username: formArray[0].value,
            password: formArray[1].value
        },
        success: function() {
            alert("Logged In!");
            showUser();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            alert("Invalid Username or Password!");
        }
    });
}

function logout(event) {
    event.preventDefault();
    $.ajax({
        type: 'POST',
        url: '../logout',
        success: function() {
            alert("Logged Out!");
            location.href = '/web/games.html';
        }
    });
}

function manageLogin(logged, email) {
    var $login = $('#login-form');
    var $logout = $('#logout-form');
    var $user = $('#user');
    $user.empty();
    if (email) $user.append('<p>' + email + '</p>');

    if (logged) {
        $login.css("display", "none");
        $user.css("display", "flex");
        $logout.css("display", "block");
    } else {
        $logout.css("display", "none");
        $user.css("display", "none");
        $login.css("display", "flex");
    }
}
