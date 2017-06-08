
function showUser(data) {
    var email = data.player.email;
    manageLogin(true, email);
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
        success: function(jqXHR) {
            alert("Logged In!");
            location.href = '/web/games.html';
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
    var $userContent = $('#user-content');
    var $signBtn = $('#sign-btn');
    $user.empty();

    if (email) $user.append('<p>' + email + '</p>');

    if (logged) {
        $login.css("display", "none");
        $signBtn.css("display", "none");
        $user.css("display", "flex");
        $logout.css("display", "block");
        $userContent.css("display", "flex");
    } else {
        $logout.css("display", "none");
        $user.css("display", "none");
        $userContent.css("display", "none");
        $login.css("display", "flex");
        $signBtn.css("display", "flex");
    }
}