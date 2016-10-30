(function() {
    'use strict';
    angular
        .module('taagliApp')
        .factory('Formation', Formation);

    Formation.$inject = ['$resource'];

    function Formation ($resource) {
        var resourceUrl =  'api/formations/:id';

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
