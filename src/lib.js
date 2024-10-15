function chooseRandCountryKey(keys) {
    return $jsapi.random(keys.length);
}

function findByName(city, keys, cities) {
    var response = 0
    var i = 0

    keys.forEach(function(elem) {
        if (cities[elem].value.name == city) {
            response = i
        }
        i++
    })

    return response
}

function checkCity(parseTree, keys, cities) {
    var city = parseTree._City.name
    var response = false

    keys.forEach(function(elem) {
        if (cities[elem].value.name == city) {
            response = true
        }
    })
    return response;
}