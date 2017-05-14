// http://localhost:8080/web/game.html?gp=1

$(document).ready(function() {
    var parameter = getParameterByName("gp");
    $.ajax({
        // We don't need to specify type:'GET' because it is the default value
        type: 'GET',
        url: '../api/game_view/' + parameter,
        success: function(data) {
            displayGrid();
            var shipArray = data.ships;
            displayAllShips(shipArray);
            showPlayers(data, parameter);
        }
    });
});

// This function creates the main Grid with divs and fills every cell with an image
function displayGrid() {
    grid = $('#grid');
    // Creating all the rows in the grid
    for (var i=-1; i<12; i++) {
        // Getting the letter for the grid
        var char = getLetterFromIndex(i);

        // Creating all the tiles in the rows
        for (var j=-1; j<12; j++) {
            tile = $('<div class="tile"></div>');

            // Setting id's for each tile
            var tileId = char + j;
            tile.attr("id", tileId);

            // Appending each tile into the grid
            grid.append(tile);

            // styling the tiles
            $('.tile').css("box-sizing", "border-box");
            $('.tile').css("width", "32px");
            $('.tile').css("height", "32px");

            // Setting correct tiles for every cell
            var imagePath = getImagePath(getImageForCoord(i, j));
            $('#' + tileId).css("background-image", "url(" + imagePath + ")");

            // Writing coordenates
            if (i == -1 && j > 0 && j < 11) {
                // Writing numbers
                $('#' + tileId).css("line-height", "32px");
                $('#' + tileId).css("vertical-align", "middle");
                $('#' + tileId).append($('<h3>' + j + '</h3>'))
            } else if (j == -1 && i > 0 && i < 11) {
                // Writing letters
                $('#' + tileId).css("line-height", "32px");
                $('#' + tileId).css("vertical-align", "middle");
                $('#' + tileId).append($('<h3>' + char + '</h3>'))
            }
        }

    }
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
function displayAllShips(shipArray) {

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
            var img = "ship-end";
            var imgDiv = $('<img src="assets/images/' + img + '.png">');
            $('#' + coord).append(imgDiv);

            // Rotating the image
            if (j === 0) {
                // Front
                imgDiv.css("transform", "rotate(270deg)");
            } else {
                // End
                imgDiv.css("transform", "rotate(90deg)");
            }

        } else {
            // If we are adding the middle parts of the sip
            var img = "ship-mid";
            var imgDiv = $('<img src="assets/images/' + img + '.png">');
            $('#' + coord).append(imgDiv);
            // Rotating the image
            imgDiv.css("transform", "rotate(90deg)");
        }
    }

}

function displayVerticalShip(locations) {

    for (var j=0; j<locations.length; j++) {
        var coord = locations[j].toLowerCase();
        if (j === 0 || j === locations.length - 1) {
           // If we are adding the ship's front or end
           var img = "ship-end";
           var imgDiv = $('<img src="assets/images/' + img + '.png">');
           $('#' + coord).append(imgDiv);

           // Rotating the image if it's the ship's end
           if (j !== 0) imgDiv.css("transform", "rotate(180deg)");

        } else {
           // If we are adding the middle parts of the sip
           var img = "ship-mid";
           var imgDiv = $('<img src="assets/images/' + img + '.png">');
           $('#' + coord).append(imgDiv);
        }
    }

}

// This function shows the two Players in the game, specifying which one is viewing the page.
function showPlayers(data, participationId) {
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