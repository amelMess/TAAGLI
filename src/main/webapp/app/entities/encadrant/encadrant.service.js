(function() {
    'use strict';
    angular
        .module('taagliApp')
        .factory('Encadrant', Encadrant);

    Encadrant.$inject = ['$resource'];

    function Encadrant ($resource) {
        var resourceUrl =  'api/encadrants/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
