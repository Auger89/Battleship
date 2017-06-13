
$(document).ready(function() {

    $.ajax({
        type: 'GET',
        url: '/api/games',
        success: function(data) {
            showUser(data);
            displayGameList(data);
        },
        error: function() {
            manageLogin(false);
        }
    });

    $.ajax({
        type: 'GET',
        url: '../api/scores',
        success: function(data) {
            var players = data.players;
            players.sort(sortByTotalScore);
            displayLeaderBoard(players);
        }
    });

    // Listeners to buttons
    $('#login-button').click(function(event) {
        login(event, 'login');
    });

    $('#logout-button').click(function(event) {
        logout(event);
    });

    $('#newgame-button').click(createGame);

    $(document).on('click', '.joingame-button', function(event) {
        joinGame(event);
    });

});

// This function creates a new Game by posting to api/game
function createGame() {
    var user = $('#user').text();

    $.ajax({
        type: 'POST',
        url: '/api/game',
        data: {
            username: user
        },
        success: function(response) {
            location.href = "/web/game.html?part=" + response.participationId;
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("Error: " + jqXHR.responseJSON.error);
        }
    });
}

function joinGame(event) {
    var gameId = event.target.getAttribute('data-gameid');

    $.ajax({
        type: 'POST',
        url: '/api/game/' + gameId + '/players',
        success: function(response) {
            location.href = "/web/game.html?part=" + response.participationId;
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("Error: " + jqXHR.responseJSON.error);
        }
    })
}

// This function creates a table using an array of players
function displayLeaderBoard(players) {
    var lBoard = $('#leaderBoard');

    // Creating the table header
    var table = $('<table></table>');
    var thead = $('<thead></thead>');
    var headRow = $('<tr></tr>');
    headRow.append('<td>User Name</td>');
    headRow.append('<td>Total Score</td>');
    headRow.append('<td>Wins</td>');
    headRow.append('<td>Losses</td>');
    headRow.append('<td>Ties</td>');
    thead.append(headRow);

    // Creating the table body
    var tbody = $('<tbody></tbody>');
    for (var i=0; i<players.length; i++) {
        var row = $('<tr/>');
        row.append('<td>' + players[i].userName + '</td>');
        row.append('<td>' + players[i].totalScore + '</td>');
        row.append('<td>' + players[i].wins + '</td>');
        row.append('<td>' + players[i].losses + '</td>');
        row.append('<td>' + players[i].ties + '</td>');
        tbody.append(row);
    }

    // Appending
    table.append(thead);
    table.append(tbody);
    lBoard.append(table);
}

// This function is used to sort our array of players by their total score.
function sortByTotalScore(a, b) {
    if (a.totalScore < b.totalScore) return 1;
    if (a.totalScore > b.totalScore) return -1;
    return 0;
}

// This function creates a list of all current games
function displayGameList(data) {
    var games = data.games;
    var UserId = data.player.id;

    // Creating html elements
    var $listContainer = $('#game-list');
    var $listOfGames = $('<ul/>');

    $.each(games, function(i, obj) {
        var $game = $('<li/>');
        var gameId = games[i].id;
        var gameDate = games[i].created;
        var participations = games[i].participations;
        var gameDefinition = 'Game ' + gameId + ', ' + gameDate + '. Players: ';
        var userCanAccessGame = false;
        var $linker;

        // If a game has only one player, the User can join it
        if (participations.length < 2) {
            var userCanJoinGame = true;
        } else {
            var userCanJoinGame = false;
        }

        $.each(participations, function(i, obj) {
            var participationId = participations[i].id;
            var player = participations[i].player.email;
            var playerId = participations[i].player.id;

            // Showing player names(emails) in game description
            if (i === 0) {
                gameDefinition += player + ' & ';
            } else {
                gameDefinition += player;
            }

            // Checking if a game is played by the current User
            if (playerId == UserId) {
                $linker = $('<a href="/web/game.html?part=' + participationId + '">');
//                $returnButton = $('<a href="/web/game.html?part=' + participationId + '"><button class="playgame-button">Play Game</button></a>');
                userCanAccessGame = true;
            }

        });
        $game.append(gameDefinition);

        // Assigning a link to the games the User can play or a button to the games the user can join
        if (userCanAccessGame) {
//            $game.append($returnButton);
//            $listOfGames.append($game);
            $linker.append($game);
            $listOfGames.append($linker);
        } else if (userCanJoinGame) {
            $game.append('<button class="joingame-button" data-gameId="' + gameId + '">Join Game</button>')
            $listOfGames.append($game);
        }

    });

    $listContainer.append($listOfGames);
}
