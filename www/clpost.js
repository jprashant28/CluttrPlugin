var clpost =  {
    postItem: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'CLPostPlugin', // mapped to our native Java class called "CLPostPlugin"
            'POST2CL', // with this action name
            [{                  // and this array of custom arguments to create our entry
            }]
        );
    }
}
module.exports = clpost;
