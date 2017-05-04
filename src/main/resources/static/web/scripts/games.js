$(document).ready(function() {
    var gameList = $('#game-list');

    $.ajax({
        // We don't need to specify type:'GET' because is the default value
        type: 'GET',
        url: '/api/games',
        success: function(data) {
            $.each(data, function(i, obj) {
                // Creating Game
                var listOfGames = $('<li>Game ' + (i+1) + ': </li>');
                var gameAttributes = $('<ul></ul>');

                // Creating list elements inside Game List
                var gameId = $('<li>Game ID: ' + obj.id + '</li>');
                var gameCreation = $('<li>Creation Date: ' + obj.created + '</li>');
                var gameParticipations = $('<li>Participations: </li>');

                $.each(obj.participations, function(i, obj) {
                    // Creating list containing our participations
                    var participationAttributes = $('<ul></ul>');

                    // Creating list elements inside Participations
                    var participationId = $('<li>Participation ID: ' + obj.id + '</li>');
                    var participationPlayer = $('<li>Player: </li>');

                    // Creating list containing our Player attributes
                    var playerAttributes = $('<ul></ul>');
                    var playerId = $('<li>Player ID: ' + obj.player.id + '</li>');
                    var playerEmail = $('<li>Player Email: ' + obj.player.email + '</li>');

                    // Appending player attributes to Player List
                    playerAttributes.append(playerId);
                    playerAttributes.append(playerEmail);

                    // Appending Player List to Player Value inside Participation
                    participationPlayer.append(playerAttributes);

                    // Appending Participation attributes to Participation List
                    participationAttributes.append(participationId);
                    participationAttributes.append(participationPlayer);

                    //Appending Participation Lists to Participations Value
                    gameParticipations.append(participationAttributes);
                });

                // Appending game attributes inside our Game List
                gameAttributes.append(gameId);
                gameAttributes.append(gameCreation);
                gameAttributes.append(gameParticipations);

                // Appending
                listOfGames.append(gameAttributes);
                gameList.append(listOfGames);
            });
        }
    });
});