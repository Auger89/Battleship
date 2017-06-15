// GLOBAL VARIABLES
var global = {
    participationId: getParameterByName("part"),
    ships: [],
    shotsFired: 1,
    salvo: {
        turnNumber: 0,
        locations: []
    }
}

$(document).ready(function() {

    // Showing logged user
    $.ajax({
        type: 'GET',
        url: '/api/games',
        success: function(data) {
            showUser(data);
        },
        error: function() {
            manageLogin(false);
        }
    });

    var parameter = global.participationId;
    // Displaying the entire page
    $.ajax({
        // We don't need to specify type:'GET' because it is the default value
        type: 'GET',
        url: '/api/game_view/' + parameter,
        success: function(data) {
            showPlayers(data, parameter);
            manageGameZone(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("Error: " + jqXHR.responseJSON.error);
            location.href = "/web/games.html";
        }
    });

    // Listening to buttons
    $('#logout-button').click(function(event) {
            logout(event);
     });

    $('#restart').click(reload);

    $('#send').click(function() {
        if (global.ships.length == 5) {
            placeShips(global.participationId);
        } else {
            alert("You need to place 5 ships!!");
        }
    });

    $('#send-salvos').click(sendSalvos);
});

// This function creates the main Grid with divs and fills every cell with an image
function displayGrid(type) {
    grid = $('#' + type + '-grid');
    // Creating all the rows in the grid
    for (var i=-1; i<12; i++) {
        // Getting the letter for the grid
        var char = getLetterFromIndex(i);

        // Creating all the tiles in the rows
        for (var j=-1; j<12; j++) {

            // Setting the droppable tiles
            if (type == "ship") {
                if ( (j<=0 || i<=0) || (j>10 || i>10) ) {
                    var tile = $('<div class="tile"></div>');
                } else {
                    var tile = $('<div class="tile"></div>')
                        .droppable({
                            accept: '.ship',
                            hoverClass: 'hovered',
                            tolerance: 'pointer',
                            drop: dropShip
                        });
                }
            } else {
                if ( (j<=0 || i<=0) || (j>10 || i>10) ) {
                    var tile = $('<div class="tile"></div>');
                } else {
                    var tile = $('<div class="tile salvo"></div>');
                }
            }

            // Setting id's for each tile
            if (type == "ship") {
                var tileId = char + j;
            } else if (type == "salvo") {
                var tileId = 's' + char + j;
            }
            tile.attr("id", tileId);

            // Appending each tile into the grid
            grid.append(tile);

            // Setting correct tiles for every cell
            var imagePath = getImagePath(getImageForCoord(i, j));
            $('#' + tileId).css("background-image", "url(" + imagePath + ")");

            // Displaying grid coordenates (1..10) and (A..J)
            if (i == -1 && j > 0 && j < 11) {
                // Writing numbers
                displayGridCoords(tileId, j);
            } else if (j == -1 && i > 0 && i < 11) {
                // Writing letters
                displayGridCoords(tileId, char);
            }
        }

    }
}

function manageGameZone(data) {
    displayGrid('ship');
    displayGrid('salvo');
    var shipsArePlaced = (data.ships.length > 0) ? true : false;

    // Setting the Salvo Turn
    global.salvo.turnNumber = data.salvoes.length + 1;

    if (shipsArePlaced) {
        $('#ships-container').hide();
        $('#send-salvos').show();
        displayAllShips(data);
        displaySalvoes(data, global.participationId);
        allowSalvoes();
    } else {
        $('#salvo-grid').hide();
        $('#send-salvos').hide();
        makeShipsDraggable();
    }
}

function displayGridCoords(id, alphanumeric) {
    $('#' + id).css("line-height", "32px");
    $('#' + id).css("vertical-align", "middle");
    $('#' + id).append($('<h3>' + alphanumeric + '</h3>'));
}

function getLetterFromIndex(index) {
    if (index == -1) return "number";
    var lettersArray = ["edge", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"];
    return lettersArray[index];
}

function getImagePath(image) {
    return "assets/images/" + image + ".png";
}

// This function manages the tile display of the grid
function getImageForCoord(x, y) {
    var img = "";
    // Outland
    if (x == -1 || y == -1) return getRandomImg('grass');

    // Corners
    if (x == 0 && y == 0) return "t-upleft";
    if (x == 0 && y == 11) return "t-upright";
    if (x == 11 && y == 0) return "t-botleft";
    if (x == 11 && y == 11) return "t-botright";

    // Edges
    if (x == 0) return "t-up";
    if (x == 11) return "t-bot";
    if (y == 0) return "t-midleft";
    if (y == 11) return "t-midright";

    // Middle Space
    return getRandomImg('water');
}

// This function returns a random image from a specific type
function getRandomImg(type) {
    num = Math.floor(Math.random()*3);
    img = "";
    // Water case
    if (type === "water") {
        switch (num) {
            case 0:
                img = "t-water1";
                break;
            case 1:
                img = "t-water2";
                break;
            case 2:
                img = "t-water3";
                break;
        }
    }
    // Grass case
    if (type === "grass") {
        switch (num) {
            case 0:
                img = "t-grass1";
                break;
            case 1:
                img = "t-grass2";
                break;
            case 2:
                img = "t-grass3";
                break;
        }
    }
    return img;
}

// This function adds ship images to every tile according to every ship locations
function displayAllShips(data) {
    var shipArray = data.ships;

    for (var i=0; i<shipArray.length; i++) {
        var shipType = shipArray[i].type;
        // Getting the array of locations
        var shipLoc = shipArray[i].location;
        var shipPosition = getShipPosition(shipLoc);

        // Case horizontal ship
        if (shipPosition === "horizontal") {
            // displaying every image
            displayHorizontalShip(shipLoc);
        }
        // Case vertical ship
        if (shipPosition === "vertical") {
           // displaying every image
           displayVerticalShip(shipLoc);
        }
    }

}

// This function returns the position (horizontal or vertical) of the ship by its first two coordinates
function getShipPosition(array) {
    // Getting the coordinates
    firstCoord = array[0];
    secondCoord = array[1];
    x1 = firstCoord[0];
    y1 = firstCoord[1];
    x2 = secondCoord[0];
    y2 = secondCoord[1];
    if (x1 == x2) return "horizontal";
    if (y1 == y2) return "vertical";
}

function displayHorizontalShip(locations) {

    for (var j=0; j<locations.length; j++) {
        var coord = locations[j].toLowerCase();
        if (j === 0 || j === locations.length - 1) {
            // If we are adding the ship's front or end
            var img = "ship-end-hor";
            var imgDiv = $('<img src="assets/images/' + img + '.png">');
            $('#' + coord).append(imgDiv);

            // Rotating the image if it's the ship's end
            if (j != 0) imgDiv.css("transform", "rotate(180deg)");

        } else {
            // If we are adding the middle parts of the sip
            var img = "ship-mid-hor";
            var imgDiv = $('<img src="assets/images/' + img + '.png">');
            $('#' + coord).append(imgDiv);
        }
    }

}

function displayVerticalShip(locations) {

    for (var j=0; j<locations.length; j++) {
        var coord = locations[j].toLowerCase();
        if (j === 0 || j === locations.length - 1) {
           // If we are adding the ship's front or end
           var img = "ship-end-ver";
           var imgDiv = $('<img src="assets/images/' + img + '.png">');
           $('#' + coord).append(imgDiv);

           // Rotating the image if it's the ship's end
           if (j !== 0) imgDiv.css("transform", "rotate(180deg)");

        } else {
           // If we are adding the middle parts of the sip
           var img = "ship-mid-ver";
           var imgDiv = $('<img src="assets/images/' + img + '.png">');
           $('#' + coord).append(imgDiv);
        }
    }

}

// This function shows the two Players in the game, specifying which one is viewing the page.
function showPlayers(data, participationId) {
    // Considering the case of just one player in a game
    if (data.participations[1] == null) {
        $('#players').append(data.participations[0].player.email);
        return;
    }
    // Accessing data
    var participation1 = data.participations[0];
    var participation2 = data.participations[1];
    var player1 = participation1.player.email;
    var player2 = participation2.player.email;
    // Determining who is viewing the page
    if (participation1.id == participationId) player1 += " (you)";
    if (participation2.id == participationId) player2 += " (you)";
    // Displaying the players into the page
    var players = player1 + " vs " + player2;
    $('#players').append(players);
}

function displaySalvoes(data, participationId) {
    var salvoes = data.salvoes;
    var explosionImage = '<img class="salvo-img" src="assets/images/salvo1.png">';
    var PlayerShipsArray = getPlayerShips(data);
    var PlayerSalvoesArray = [];

    // Iterating through salvos array
    for (var i=0; i<salvoes.length; i++) {
        var salvoLocations = salvoes[i].locations;
        var turnNumber = salvoes[i].turn;

        // Detecting the Player viewing the page
        if (salvoes[i].player == participationId) {
            // Iterating through locations array
            for (var j=0; j<salvoLocations.length; j++) {
                var coord = salvoLocations[j].toLowerCase();
                // Showing salvo (explosion image) in the right tile if there's no salvo already
                // children() function returns an array of elements inside the element which function is called
                if ( PlayerSalvoesArray.indexOf(coord) == -1 ) {
                    // Showing the salvo and the turn number
                    $('#s' + coord).append(explosionImage);
                    $('#s' + coord).append('<p class="salvo-turn">' + turnNumber + '</p>');

                    // Disabling the tile from being fired again
                    $('#s' + coord).removeClass("salvo");

                    PlayerSalvoesArray.push(coord);
                }
            }
        }
        // Detecting the Opponent
        else {
            // Iterating through locations array
            for (var j=0; j<salvoLocations.length; j++) {
                var coord = salvoLocations[j].toLowerCase();
                // If there is not any ship in the location
                if (PlayerShipsArray.indexOf(coord) == -1) {
                    $('#' + coord).append(explosionImage);
                }
                // If there is a ship in the location
                else if (PlayerShipsArray.indexOf(coord) != -1) {
//                    $('#' + coord).empty();
                    $('#' + coord).append(explosionImage);
                    $('#' + coord).append('<p class="salvo-turn">' + turnNumber + '</p>');
                }
            }
        }
    }
}

function getPlayerShips(data) {
    var playerShips = [];
    var ships = data.ships;
    for (var i=0; i<ships.length; i++) {
        var locationsArray = ships[i].location;
        for (var j=0; j<locationsArray.length; j++) {
            playerShips.push(locationsArray[j].toLowerCase());
        }
    }
    return playerShips;
}

// This function retrieves query string values from the current URL
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function placeShips(participationId) {
    $.ajax({
        type: 'POST',
        url: '/api/games/players/' + participationId + '/ships',
        data: JSON.stringify(global.ships),
        contentType: "application/json",
        success: function(response) {
            alert(response.status);
            location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("Error: " + jqXHR.responseJSON.error);
        }
    });
}

// This function enables firing salvos when the user has placed his ships
function allowSalvoes() {
    $salvo = $('.salvo');

    $salvo.hover(function() {
        $(this).addClass('hovered');
    }, function() {
        $(this).removeClass('hovered');
    });

    $salvo.click(function() {
        var tileIsFired = $(this).data('isfired');

        if (tileIsFired) {
            // Removing the targets (tiles to fire) and enabling another shot
            $(this).empty();
            global.shotsFired --;

            // Remove the location from our salvo object
            var id = $(this).attr("id");
            var location = id.substring(1);
            var index = global.salvo.locations.indexOf(location);
            global.salvo.locations.splice(index, 1);

            // Setting this tile as an unfired fired tile
            $(this).data('isfired', false);
            console.log(global.salvo.locations);

        } else if ( (!tileIsFired) && (global.shotsFired <= 5) ) {
            // Showing the targets (tiles to fire) and preventing to fire more than 5 shots
            $(this).append($('<img src="/web/assets/images/target2.png">'));
            global.shotsFired ++;

            // Save the locations in our salvo object
            var id = $(this).attr("id");
            var location = id.substring(1);
            global.salvo.locations.push(location);

            // Setting this tile as a fired tile
            $(this).data('isfired', true);
            console.log(global.salvo.locations);
        }
    });
}

function sendSalvos() {
    $.ajax({
        type: 'POST',
        url: '/api/games/players/' + global.participationId + '/salvos',
        data: JSON.stringify(global.salvo),
        contentType: "application/json",
        success: function(response) {
            alert(response.status);
            location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("Error: " + jqXHR.responseJSON.error);
        }
    });
}

// ----- JQUERY UI DRAG & DROP SECTION -----

function makeShipsDraggable() {

	$('.ship').draggable({
	  	// containment set the limit zone which the draggable element can't leave
		containment: '.dragzone',
	  	// cursor option changes the cursor display when dragging
	  	cursor: 'move',
		// Brings dragged item to the front
		stack: '.ships-container',
	  	// revert true returns the draggable element to his initial 'div' when incorrect dropping
	  	revert: true
	});

}

function dropShip(event, ui) {

	// getting ship data
	var dShip = ui.draggable;
	var position = dShip.data("position");
	var length = dShip.data("length");
	var shipType = dShip.data("type");

	// getting tile ID
	var tileId = $(this).attr("id");

	if (shipCanBeDropped(tileId, position, length)){
		// Making ship undraggable and tile undroppable
		dShip.draggable('disable');
		$(this).droppable('disable');

		// positioning the ship
		dShip.position( { of: $(this), my: 'left top', at: 'left top' } );


		makeTilesUndroppable(tileId, position, length);

		// this line drops the element to the current droppable element
		dShip.draggable('option', 'revert', 'false');

		// eliminating other direction of the ship
		removeShip(dShip);

		// Adding ships to the Ships Object
		var locationsArray = getShipLocations(tileId, position, length);
		var actualShip = {
			type: shipType,
			locations: locationsArray
		}
		global.ships.push(actualShip);
	}

}

function shipCanBeDropped(tileId, position, length) {

	var num = parseInt(tileId.substring(1));
	var char = tileId.substring(0,1);

	if (position == "hor") {
		for (var i=0; i<length - 1; i++) {
			num += 1;
			var id = char + num;

			var isDroppable = $('#' + id).hasClass('ui-droppable');
			var droppableDisabled = $('#' + id).hasClass('ui-droppable-disabled');

			if (!isDroppable || droppableDisabled) return false;
		}
	} else if (position == "ver") {
		for (var i=0; i<length - 1; i++) {
			char = nextChar(char);
			var id = char + num;

			var isDroppable = $('#' + id).hasClass('ui-droppable');
			var droppableDisabled = $('#' + id).hasClass('ui-droppable-disabled');

			if (!isDroppable || droppableDisabled) return false;
		}
	}

	return true;
}

function makeTilesUndroppable(tileId, position, length) {

	var num = parseInt(tileId.substring(1));
	var char = tileId.substring(0,1);

	if (position == "hor") {
		for (var i=0; i<length - 1; i++) {
			num += 1;
			var id = char + num;
			$('#' + id).droppable('disable');
		}
	} else if (position == "ver") {
		for (var i=0; i<length - 1; i++) {
			char = nextChar(char);
			var id = char + num;
			$('#' + id).droppable('disable');
		}
	}

}

function getShipLocations(id, position, length) {
	var num = parseInt(id.substring(1));
	var char = id.substring(0,1);
	var locations = [];

	if (position == "hor") {
		for (var i=0; i<length; i++) {
			var id = char + num;
			locations.push(id);
			num += 1;
		}
	} else if (position == "ver") {
		for (var i=0; i<length; i++) {
			var id = char + num;
			locations.push(id);
			char = nextChar(char);
		}
	}
	return locations;
}

function removeShip(ship) {
	var shipId = ship.attr('id');
	var direction = shipId.substring(shipId.length - 1, shipId.length);
	var simpleId = shipId.substring(0, shipId.length - 1);

	if (direction == "v") {
		$('#' + simpleId + "h").hide();
	} else {
		$('#' + simpleId + "v").hide();
	}
}

function reload() {
	location.reload();
}

function nextChar(c) {
    return String.fromCharCode(c.charCodeAt(0) + 1);
}
