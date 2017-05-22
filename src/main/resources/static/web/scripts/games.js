$(document).ready(function() {

    $.ajax({
        type: 'GET',
        url: '../api/scores',
        success: function(data) {
            var players = data.players;
            players.sort(sortByTotalScore);

            displayLeaderBoard(players);
        }
    });

});

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