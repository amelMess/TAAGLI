(function() {
    'use strict';
    angular
        .module('taagliApp')
        .factory('Adresse', Adresse);

    Adresse.$inject = ['$resource'];

    function Adresse ($resource) {
        var resourceUrl =  'api/adresses/:id';

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
